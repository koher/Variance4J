package org.koherent.variance.test;

import static org.junit.Assert.fail;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.tools.Diagnostic;
import javax.tools.DiagnosticCollector;
import javax.tools.JavaCompiler;
import javax.tools.JavaCompiler.CompilationTask;
import javax.tools.JavaFileObject;
import javax.tools.StandardJavaFileManager;
import javax.tools.ToolProvider;

import org.junit.Before;
import org.junit.Test;
import org.koherent.variance.VarianceProcessor;

public abstract class VarianceProcessorTest {
	private JavaCompiler compiler;

	@Before
	public void setUp() {
		compiler = ToolProvider.getSystemJavaCompiler();
	}

	protected abstract Iterable<? extends File> getFiles();

	protected Map<String, String> getOptions() {
		return Collections.emptyMap();
	}

	@Test
	public void test() {
		Iterable<? extends File> files = getFiles();

		final Set<String> successTypeNames = new HashSet<String>();
		final Set<String> failuerTypeNames = new HashSet<String>();
		{
			StandardJavaFileManager fileManager = compiler
					.getStandardFileManager(null, null, null);
			CompilationTask task = compiler.getTask(null, fileManager, null,
					null, null, fileManager.getJavaFileObjectsFromFiles(files));
			task.setProcessors(Collections
					.singleton(new AnnotationProcessorTestProcessor(
							new AnnotationProcessorTestProcessor.Delegate() {
								@Override
								public void onFinishTest(
										Set<? extends Element> successElements,
										Set<? extends Element> failuerElements) {
									for (Element typeElement : successElements) {
										successTypeNames
												.add(((TypeElement) typeElement)
														.getQualifiedName()
														.toString());
									}
									for (Element typeElement : failuerElements) {
										failuerTypeNames
												.add(((TypeElement) typeElement)
														.getQualifiedName()
														.toString());
									}
								}
							})));
			task.call();

			try {
				fileManager.close();
			} catch (IOException e) {
			}
		}

		List<String> options = new ArrayList<String>();
		options.add("-proc:only");
		for (Map.Entry<String, String> entry : getOptions().entrySet()) {
			options.add("-A" + entry.getKey() + "=" + entry.getValue());
		}

		DiagnosticCollector<JavaFileObject> diagnosticCollector = new DiagnosticCollector<JavaFileObject>();
		StandardJavaFileManager fileManager = compiler.getStandardFileManager(
				diagnosticCollector, null, null);
		CompilationTask task = compiler.getTask(null, fileManager,
				diagnosticCollector, options, null,
				fileManager.getJavaFileObjectsFromFiles(files));
		task.setProcessors(Collections.singleton(new VarianceProcessor()));
		task.call();

		try {
			fileManager.close();
		} catch (IOException e) {
		}

		Pattern pattern = Pattern
				.compile("The (return type|parameter type) .+ of (.+)#(.+) is illegal\\.");
		Set<String> errorTypeNames = new HashSet<String>();
		Map<String, List<String>> typeNameToErrorMessages = new HashMap<String, List<String>>();
		for (Diagnostic<? extends JavaFileObject> diagnostic : diagnosticCollector
				.getDiagnostics()) {
			switch (diagnostic.getKind()) {
			case ERROR: {
				String message = diagnostic.getMessage(null);
				Matcher matcher = pattern.matcher(message);
				if (matcher.find()) {
					String typeName = matcher.group(2);
					errorTypeNames.add(typeName);

					List<String> errorMessages = typeNameToErrorMessages
							.get(typeName);
					if (errorMessages == null) {
						errorMessages = new ArrayList<String>();
						typeNameToErrorMessages.put(typeName, errorMessages);
					}
					errorMessages.add(message);
				} else {
					throw new Error("Never reaches here.");
				}
				break;
			}
			default: {
				break;
			}
			}
		}

		StringWriter errorWriter = new StringWriter();
		PrintWriter errorPrintWriter = new PrintWriter(errorWriter, true);

		Set<String> unexpectedFiluerTypeNames = new HashSet<String>(
				successTypeNames);
		unexpectedFiluerTypeNames.retainAll(errorTypeNames);
		if (unexpectedFiluerTypeNames.size() > 0) {
			errorPrintWriter.println("Unexpected failuers:");
			for (String typeName : unexpectedFiluerTypeNames) {
				errorPrintWriter.print("    ");
				errorPrintWriter.println(typeName);

				for (String message : typeNameToErrorMessages.get(typeName)) {
					errorPrintWriter.print("        ");
					errorPrintWriter.println(message);
				}
			}
			errorPrintWriter.println();
		}

		Set<String> unexpectedSuccessTypeNames = new HashSet<String>(
				failuerTypeNames);
		unexpectedSuccessTypeNames.removeAll(errorTypeNames);
		if (unexpectedSuccessTypeNames.size() > 0) {
			errorPrintWriter.println("Unexpected successes:");
			for (String typeName : unexpectedSuccessTypeNames) {
				errorPrintWriter.print("    ");
				errorPrintWriter.println(typeName);
			}
			errorPrintWriter.println();
		}

		errorTypeNames.removeAll(successTypeNames);
		errorTypeNames.removeAll(failuerTypeNames);
		if (errorTypeNames.size() > 0) {
			errorPrintWriter.println("Unexpected errors:");
			for (String typeName : errorTypeNames) {
				errorPrintWriter.print("    ");
				errorPrintWriter.println(typeName);

				for (String message : typeNameToErrorMessages.get(typeName)) {
					errorPrintWriter.print("        ");
					errorPrintWriter.println(message);
				}
			}
			errorPrintWriter.println();
		}

		String errorMessage = errorWriter.toString();
		if (errorMessage.length() > 0) {
			fail(errorMessage);
		}
	}
}
