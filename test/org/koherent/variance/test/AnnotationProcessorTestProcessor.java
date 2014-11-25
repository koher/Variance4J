package org.koherent.variance.test;

import java.util.HashSet;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;

@SupportedSourceVersion(SourceVersion.RELEASE_8)
@SupportedAnnotationTypes({ "org.koherent.variance.test.AnnotationProcessorTest" })
public class AnnotationProcessorTestProcessor extends AbstractProcessor {
	private Delegate delegate;

	public AnnotationProcessorTestProcessor(Delegate delegate) {
		if (delegate == null) {
			throw new IllegalArgumentException("'delegate' cannot be null.");
		}

		this.delegate = delegate;
	}

	@Override
	public boolean process(Set<? extends TypeElement> annotations,
			RoundEnvironment roundEnv) {
		Set<Element> successElements = new HashSet<Element>();
		Set<Element> failuerElements = new HashSet<Element>();

		for (TypeElement annotation : annotations) {
			for (Element element : roundEnv
					.getElementsAnnotatedWith(annotation)) {
				if (element.getAnnotation(AnnotationProcessorTest.class)
						.error()) {
					failuerElements.add(element);
				} else {
					successElements.add(element);
				}
			}
		}

		delegate.onFinishTest(successElements, failuerElements);

		return true;
	}

	public static interface Delegate {
		void onFinishTest(Set<? extends Element> successElements,
				Set<? extends Element> failuerElements);
	}
}
