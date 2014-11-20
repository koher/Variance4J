package org.koherent.variance.test;

import org.junit.Test;
import org.koherent.variance.IllegalVarianceException;
import org.koherent.variance.In;
import org.koherent.variance.VarianceChecker;

public class ContravarianceTest {
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

	@Test(expected = IllegalVarianceException.class)
	public void testNG1() throws IllegalVarianceException {
		new VarianceChecker().check(NG1.class);
	}

	@Test(expected = IllegalVarianceException.class)
	public void testNG2() throws IllegalVarianceException {
		new VarianceChecker().check(NG2.class);
	}

	@Test(expected = IllegalVarianceException.class)
	public void testNG3() throws IllegalVarianceException {
		new VarianceChecker().check(NG3.class);
	}

	@Test(expected = IllegalVarianceException.class)
	public void testNG4() throws IllegalVarianceException {
		new VarianceChecker().check(NG4.class);
	}

	@Test(expected = IllegalVarianceException.class)
	public void testNG5() throws IllegalVarianceException {
		new VarianceChecker().check(NG5.class);
	}

	private static interface OK0<@In T> {
		public void foo();
	}

	private static interface OK1<@In T> {
		public void foo(T t);
	}

	private static interface OK2<@In T> {
		public Consumer<T> foo();
	}

	private static interface OK3<@In T> {
		public Consumer<? super T> foo();
	}

	private static interface OK4<@In T> {
		public void foo(Supplier<T> t);
	}

	private static interface OK5<@In T> {
		public void foo(Supplier<? extends T> t);
	}

	private static interface OK6<@In T> {
		public Supplier<? super T> foo();
	}

	private static interface OK7<@In T> {
		public void foo(Supplier<? super T> t);
	}

	private static interface OK8<@In T> {
		public Consumer<? extends T> foo();
	}

	private static interface OK9<@In T> {
		public void foo(Consumer<? extends T> t);
	}

	private static interface NG1<@In T> {
		public T foo();
	}

	private static interface NG2<@In T> {
		public Supplier<T> foo();
	}

	private static interface NG3<@In T> {
		public Supplier<? extends T> foo();
	}

	private static interface NG4<@In T> {
		public void foo(Consumer<T> t);
	}

	private static interface NG5<@In T> {
		public void foo(Consumer<? super T> t);
	}
}
