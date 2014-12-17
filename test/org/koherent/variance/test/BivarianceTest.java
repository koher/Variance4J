package org.koherent.variance.test;

import java.io.File;
import java.util.Collections;

import org.koherent.variance.In;
import org.koherent.variance.Out;

public class BivarianceTest extends VarianceProcessorTest {
	@Override
	protected Iterable<? extends File> getFiles() {
		return Collections.singleton(new File(
				"test/org/koherent/variance/test/BivarianceTest.java"));
	}

	@AnnotationProcessorTest(error = false)
	private static interface OK0<@In @Out T> {
		public void foo();
	}

	@AnnotationProcessorTest(error = false)
	private static interface OK1<@In @Out T> {
		public Supplier<? super T> foo();
	}

	@AnnotationProcessorTest(error = false)
	private static interface OK2<@In @Out T> {
		public void foo(Supplier<? super T> t);
	}

	@AnnotationProcessorTest(error = false)
	private static interface OK3<@In @Out T> {
		public Consumer<? extends T> foo();
	}

	@AnnotationProcessorTest(error = false)
	private static interface OK4<@In @Out T> {
		public void foo(Consumer<? extends T> t);
	}

	@AnnotationProcessorTest(error = true)
	private static interface NG1<@In @Out T> {
		public T foo();
	}

	@AnnotationProcessorTest(error = true)
	private static interface NG2<@In @Out T> {
		public void foo(T t);
	}

	@AnnotationProcessorTest(error = true)
	private static interface NG3<@In @Out T> {
		public Supplier<T> foo();
	}

	@AnnotationProcessorTest(error = true)
	private static interface NG4<@In @Out T> {
		public Supplier<? extends T> foo();
	}

	@AnnotationProcessorTest(error = true)
	private static interface NG5<@In @Out T> {
		public void foo(Supplier<T> t);
	}

	@AnnotationProcessorTest(error = true)
	private static interface NG6<@In @Out T> {
		public void foo(Supplier<? extends T> t);
	}

	@AnnotationProcessorTest(error = true)
	private static interface NG7<@In @Out T> {
		public Consumer<T> foo();
	}

	@AnnotationProcessorTest(error = true)
	private static interface NG8<@In @Out T> {
		public Consumer<? super T> foo();
	}

	@AnnotationProcessorTest(error = true)
	private static interface NG9<@In @Out T> {
		public void foo(Consumer<T> t);
	}

	@AnnotationProcessorTest(error = true)
	private static interface NG10<@In @Out T> {
		public void foo(Consumer<? super T> t);
	}

	@AnnotationProcessorTest(error = true)
	private static abstract class NG11<@In @Out T> {
		@SuppressWarnings("unused")
		public final T t = null;
	}

	@AnnotationProcessorTest(error = true)
	private static abstract class NG12<@In @Out T> {
		@SuppressWarnings("unused")
		public T t;
	}

	@AnnotationProcessorTest(error = true)
	private static interface NG13<@In @Out X extends Throwable> {
		public void foo() throws X;
	}
}
