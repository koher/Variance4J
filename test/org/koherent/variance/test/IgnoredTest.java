package org.koherent.variance.test;

import java.io.File;
import java.util.Collections;

import org.koherent.variance.Ignored;
import org.koherent.variance.Out;

public class IgnoredTest extends VarianceProcessorTest {
	@Override
	protected Iterable<? extends File> getFiles() {
		return Collections.singleton(new File(
				"test/org/koherent/variance/test/IgnoredTest.java"));
	}

	@AnnotationProcessorTest(error = false)
	private static interface OK<@Out T> {
		T foo();

		@Ignored
		void bar(T t);
	}

	@AnnotationProcessorTest(error = true)
	private static interface NG<@Out T> {
		T foo();

		void bar(T t);
	}
}
