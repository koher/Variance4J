package org.koherent.variance.test;

import java.io.File;
import java.util.Collections;

import org.koherent.variance.In;

public class ContravarianceTest extends VarianceProcessorTest {
	@Override
	protected Iterable<? extends File> getFiles() {
		return Collections.singleton(new File(
				"test/org/koherent/variance/test/ContravarianceTest.java"));
	}

	@AnnotationProcessorTest(error = false)
	private static interface OK0<@In T> {
		public void foo();
	}

	@AnnotationProcessorTest(error = false)
	private static interface OK1<@In T> {
		public void foo(T t);
	}

	@AnnotationProcessorTest(error = false)
	private static interface OK2<@In T> {
		public Consumer<T> foo();
	}

	@AnnotationProcessorTest(error = false)
	private static interface OK3<@In T> {
		public Consumer<? super T> foo();
	}

	@AnnotationProcessorTest(error = false)
	private static interface OK4<@In T> {
		public void foo(Supplier<T> t);
	}

	@AnnotationProcessorTest(error = false)
	private static interface OK5<@In T> {
		public void foo(Supplier<? extends T> t);
	}

	@AnnotationProcessorTest(error = false)
	private static interface OK6<@In T> {
		public Supplier<? super T> foo();
	}

	@AnnotationProcessorTest(error = false)
	private static interface OK7<@In T> {
		public void foo(Supplier<? super T> t);
	}

	@AnnotationProcessorTest(error = false)
	private static interface OK8<@In T> {
		public Consumer<? extends T> foo();
	}

	@AnnotationProcessorTest(error = false)
	private static interface OK9<@In T> {
		public void foo(Consumer<? extends T> t);
	}

	@AnnotationProcessorTest(error = true)
	private static interface NG1<@In T> {
		public T foo();
	}

	@AnnotationProcessorTest(error = true)
	private static interface NG2<@In T> {
		public Supplier<T> foo();
	}

	@AnnotationProcessorTest(error = true)
	private static interface NG3<@In T> {
		public Supplier<? extends T> foo();
	}

	@AnnotationProcessorTest(error = true)
	private static interface NG4<@In T> {
		public void foo(Consumer<T> t);
	}

	@AnnotationProcessorTest(error = true)
	private static interface NG5<@In T> {
		public void foo(Consumer<? super T> t);
	}
}
