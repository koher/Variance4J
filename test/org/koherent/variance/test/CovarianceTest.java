package org.koherent.variance.test;

import java.io.File;
import java.util.Collections;

import org.koherent.variance.Out;

public class CovarianceTest extends VarianceProcessorTest {
	@Override
	protected Iterable<? extends File> getFiles() {
		return Collections.singleton(new File(
				"test/org/koherent/variance/test/CovarianceTest.java"));
	}

	@AnnotationProcessorTest(error = false)
	private static interface OK0<@Out T> {
		public void foo();
	}

	@AnnotationProcessorTest(error = false)
	private static interface OK1<@Out T> {
		public T foo();
	}

	@AnnotationProcessorTest(error = false)
	private static interface OK2<@Out T> {
		public Supplier<T> foo();
	}

	@AnnotationProcessorTest(error = false)
	private static interface OK3<@Out T> {
		public Supplier<? extends T> foo();
	}

	@AnnotationProcessorTest(error = false)
	private static interface OK4<@Out T> {
		public void foo(Consumer<T> t);
	}

	@AnnotationProcessorTest(error = false)
	private static interface OK5<@Out T> {
		public void foo(Consumer<? super T> t);
	}

	@AnnotationProcessorTest(error = false)
	private static interface OK6<@Out T> {
		public Supplier<? super T> foo();
	}

	@AnnotationProcessorTest(error = false)
	private static interface OK7<@Out T> {
		public void foo(Supplier<? super T> t);
	}

	@AnnotationProcessorTest(error = false)
	private static interface OK8<@Out T> {
		public Consumer<? extends T> foo();
	}

	@AnnotationProcessorTest(error = false)
	private static interface OK9<@Out T> {
		public void foo(Consumer<? extends T> t);
	}

	@AnnotationProcessorTest(error = true)
	private static interface NG1<@Out T> {
		public void foo(T t);
	}

	@AnnotationProcessorTest(error = true)
	private static interface NG2<@Out T> {
		public Consumer<T> foo();
	}

	@AnnotationProcessorTest(error = true)
	private static interface NG3<@Out T> {
		public Consumer<? super T> foo();
	}

	@AnnotationProcessorTest(error = true)
	private static interface NG4<@Out T> {
		public void foo(Supplier<T> t);
	}

	@AnnotationProcessorTest(error = true)
	private static interface NG5<@Out T> {
		public void foo(Supplier<? extends T> t);
	}
}
