package org.koherent.variance.test;

import java.io.File;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.Map;

import org.koherent.variance.In;
import org.koherent.variance.Out;

public class ExtensionOKTest extends VarianceProcessorTest {
	@Override
	protected Iterable<? extends File> getFiles() {
		return Collections.singleton(new File(
				"test/org/koherent/variance/test/ExtensionOKTest.java"));
	}

	@Override
	protected Map<String, String> getOptions() {
		return Collections.singletonMap("extension",
				"org.koherent.variance.StandardVarianceExtension");
	}

	@AnnotationProcessorTest(error = false)
	private static interface Foo<@Out T> {
		public Iterator<T> foo();
	}

	@AnnotationProcessorTest(error = false)
	private static interface Bar<@Out T> {
		public void bar(Comparator<T> comparator);
	}

	@AnnotationProcessorTest(error = false)
	private static interface Baz<@In T> {
		public void baz(Iterator<T> iterator);
	}

	@AnnotationProcessorTest(error = false)
	private static interface Qux<@In T> {
		public Comparator<T> qux();
	}
}
