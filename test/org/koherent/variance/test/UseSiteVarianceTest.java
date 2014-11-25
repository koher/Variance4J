package org.koherent.variance.test;

import java.io.File;
import java.util.Arrays;
import java.util.List;

import org.koherent.variance.In;
import org.koherent.variance.Out;

public class UseSiteVarianceTest extends VarianceProcessorTest {
	@Override
	protected Iterable<? extends File> getFiles() {
		return Arrays.asList(new File(
				"test/org/koherent/variance/test/UseSiteVarianceTest.java"));
	}

	@AnnotationProcessorTest(error = false)
	private static interface OutCoReturn<@Out T> {
		public List<? extends T> foo();
	}

	@AnnotationProcessorTest(error = true)
	private static interface OutCoParam<@Out T> {
		public void foo(List<? extends T> list);
	}

	@AnnotationProcessorTest(error = true)
	private static interface OutContraReturn<@Out T> {
		public List<? super T> foo();
	}

	@AnnotationProcessorTest(error = false)
	private static interface OutContraParam<@Out T> {
		public void foo(List<? super T> list);
	}

	@AnnotationProcessorTest(error = true)
	private static interface InCoReturn<@In T> {
		public List<? extends T> foo();
	}

	@AnnotationProcessorTest(error = false)
	private static interface InCoParam<@In T> {
		public void foo(List<? extends T> list);
	}

	@AnnotationProcessorTest(error = false)
	private static interface InContraReturn<@In T> {
		public List<? super T> foo();
	}

	@AnnotationProcessorTest(error = true)
	private static interface InContraParam<@In T> {
		public void foo(List<? super T> list);
	}
}
