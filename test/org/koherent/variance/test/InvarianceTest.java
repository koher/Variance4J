package org.koherent.variance.test;

import org.junit.Test;
import org.koherent.variance.IllegalVarianceException;
import org.koherent.variance.VarianceChecker;

public class InvarianceTest {
	@Test
	public void testOK0() throws IllegalVarianceException {
		new VarianceChecker().check(OK0.class);
	}

	@Test
	public void testOK1() throws IllegalVarianceException {
		new VarianceChecker().check(OK1.class);
	}

	@Test
	public void testOK2() throws IllegalVarianceException {
		new VarianceChecker().check(OK2.class);
	}

	@Test
	public void testOK3() throws IllegalVarianceException {
		new VarianceChecker().check(OK3.class);
	}

	@Test
	public void testOK4() throws IllegalVarianceException {
		new VarianceChecker().check(OK4.class);
	}

	@Test
	public void testOK5() throws IllegalVarianceException {
		new VarianceChecker().check(OK5.class);
	}

	@Test
	public void testOK6() throws IllegalVarianceException {
		new VarianceChecker().check(OK6.class);
	}

	@Test
	public void testOK7() throws IllegalVarianceException {
		new VarianceChecker().check(OK7.class);
	}

	@Test
	public void testOK8() throws IllegalVarianceException {
		new VarianceChecker().check(OK8.class);
	}

	@Test
	public void testOK9() throws IllegalVarianceException {
		new VarianceChecker().check(OK9.class);
	}

	@Test
	public void testOK10() throws IllegalVarianceException {
		new VarianceChecker().check(OK10.class);
	}

	@Test
	public void testOK11() throws IllegalVarianceException {
		new VarianceChecker().check(OK11.class);
	}

	@Test
	public void testOK12() throws IllegalVarianceException {
		new VarianceChecker().check(OK12.class);
	}

	@Test
	public void testOK13() throws IllegalVarianceException {
		new VarianceChecker().check(OK13.class);
	}

	@Test
	public void testOK14() throws IllegalVarianceException {
		new VarianceChecker().check(OK14.class);
	}

	private static interface OK0<T> {
		public void foo();
	}

	private static interface OK1<T> {
		public T foo();
	}

	private static interface OK2<T> {
		public void foo(T t);
	}

	private static interface OK3<T> {
		public Supplier<T> foo();
	}

	private static interface OK4<T> {
		public Supplier<? extends T> foo();
	}

	private static interface OK5<T> {
		public void foo(Supplier<T> t);
	}

	private static interface OK6<T> {
		public void foo(Supplier<? extends T> t);
	}

	private static interface OK7<T> {
		public Consumer<T> foo();
	}

	private static interface OK8<T> {
		public Consumer<? extends T> foo();
	}

	private static interface OK9<T> {
		public void foo(Consumer<T> t);
	}

	private static interface OK10<T> {
		public void foo(Consumer<? extends T> t);
	}

	private static interface OK11<T> {
		public Supplier<? super T> foo();
	}

	private static interface OK12<T> {
		public void foo(Supplier<? super T> t);
	}

	private static interface OK13<T> {
		public Consumer<? extends T> foo();
	}

	private static interface OK14<T> {
		public void foo(Consumer<? extends T> t);
	}

}
