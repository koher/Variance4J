package org.koherent.variance.test;

import java.io.File;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;

import org.koherent.variance.In;
import org.koherent.variance.Out;

public class ExtensionNGTest extends VarianceProcessorTest {
	@Override
	protected Iterable<? extends File> getFiles() {
		return Collections.singleton(new File(
				"test/org/koherent/variance/test/ExtensionNGTest.java"));
	}

	@AnnotationProcessorTest(error = true)
	private static interface Foo<@Out T> {
		public Iterator<T> foo();
	}

	@AnnotationProcessorTest(error = true)
	private static interface Bar<@Out T> {
		public void bar(Comparator<T> comparator);
	}

	@AnnotationProcessorTest(error = true)
	private static interface Baz<@In T> {
		public void baz(Iterator<T> iterator);
	}

	@AnnotationProcessorTest(error = true)
	private static interface Qux<@In T> {
		public Comparator<T> qux();
	}
}
