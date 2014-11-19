package org.koherent.variance.test;

import org.junit.Test;
import org.koherent.variance.IllegalVarianceException;
import org.koherent.variance.In;
import org.koherent.variance.Out;
import org.koherent.variance.VarianceChecker;

public class VarianceCheckerTest {
	@Test
	public void testSupportClasses() throws IllegalVarianceException {
		VarianceChecker checker = new VarianceChecker();

		checker.check(Supplier.class);
		checker.check(Consumer.class);
		checker.check(Function.class);
	}

	@Test
	public void testInvarianceOK0() throws IllegalVarianceException {
		new VarianceChecker().check(InvarianceOK0.class);
	}

	@Test
	public void testInvarianceOK1() throws IllegalVarianceException {
		new VarianceChecker().check(InvarianceOK1.class);
	}

	@Test
	public void testInvarianceOK2() throws IllegalVarianceException {
		new VarianceChecker().check(InvarianceOK2.class);
	}

	@Test
	public void testInvarianceOK3() throws IllegalVarianceException {
		new VarianceChecker().check(InvarianceOK3.class);
	}

	@Test
	public void testInvarianceOK4() throws IllegalVarianceException {
		new VarianceChecker().check(InvarianceOK4.class);
	}

	@Test
	public void testInvarianceOK5() throws IllegalVarianceException {
		new VarianceChecker().check(InvarianceOK5.class);
	}

	@Test
	public void testInvarianceOK6() throws IllegalVarianceException {
		new VarianceChecker().check(InvarianceOK6.class);
	}

	@Test
	public void testInvarianceOK7() throws IllegalVarianceException {
		new VarianceChecker().check(InvarianceOK7.class);
	}

	@Test
	public void testInvarianceOK8() throws IllegalVarianceException {
		new VarianceChecker().check(InvarianceOK8.class);
	}

	@Test
	public void testInvarianceOK9() throws IllegalVarianceException {
		new VarianceChecker().check(InvarianceOK9.class);
	}

	@Test
	public void testInvarianceOK10() throws IllegalVarianceException {
		new VarianceChecker().check(InvarianceOK10.class);
	}

	@Test
	public void testCovarianceOK0() throws IllegalVarianceException {
		new VarianceChecker().check(CovarianceOK0.class);
	}

	@Test
	public void testCovarianceOK1() throws IllegalVarianceException {
		new VarianceChecker().check(CovarianceOK1.class);
	}

	@Test
	public void testCovarianceOK2() throws IllegalVarianceException {
		new VarianceChecker().check(CovarianceOK2.class);
	}

	@Test
	public void testCovarianceOK3() throws IllegalVarianceException {
		new VarianceChecker().check(CovarianceOK3.class);
	}

	@Test
	public void testCovarianceOK4() throws IllegalVarianceException {
		new VarianceChecker().check(CovarianceOK4.class);
	}

	@Test
	public void testCovarianceOK5() throws IllegalVarianceException {
		new VarianceChecker().check(CovarianceOK5.class);
	}

	@Test(expected = IllegalVarianceException.class)
	public void testCovarianceNG1() throws IllegalVarianceException {
		new VarianceChecker().check(CovarianceNG1.class);
	}

	@Test(expected = IllegalVarianceException.class)
	public void testCovarianceNG2() throws IllegalVarianceException {
		new VarianceChecker().check(CovarianceNG2.class);
	}

	@Test(expected = IllegalVarianceException.class)
	public void testCovarianceNG3() throws IllegalVarianceException {
		new VarianceChecker().check(CovarianceNG3.class);
	}

	@Test(expected = IllegalVarianceException.class)
	public void testCovarianceNG4() throws IllegalVarianceException {
		new VarianceChecker().check(CovarianceNG4.class);
	}

	@Test(expected = IllegalVarianceException.class)
	public void testCovarianceNG5() throws IllegalVarianceException {
		new VarianceChecker().check(CovarianceNG5.class);
	}

	@Test
	public void testContravarianceOK0() throws IllegalVarianceException {
		new VarianceChecker().check(ContravarianceOK0.class);
	}

	@Test
	public void testContravarianceOK1() throws IllegalVarianceException {
		new VarianceChecker().check(ContravarianceOK1.class);
	}

	@Test
	public void testContravarianceOK2() throws IllegalVarianceException {
		new VarianceChecker().check(ContravarianceOK2.class);
	}

	@Test
	public void testContravarianceOK3() throws IllegalVarianceException {
		new VarianceChecker().check(ContravarianceOK3.class);
	}

	@Test
	public void testContravarianceOK4() throws IllegalVarianceException {
		new VarianceChecker().check(ContravarianceOK4.class);
	}

	@Test
	public void testContravarianceOK5() throws IllegalVarianceException {
		new VarianceChecker().check(ContravarianceOK5.class);
	}

	@Test(expected = IllegalVarianceException.class)
	public void testContravarianceNG1() throws IllegalVarianceException {
		new VarianceChecker().check(ContravarianceNG1.class);
	}

	@Test(expected = IllegalVarianceException.class)
	public void testContravarianceNG2() throws IllegalVarianceException {
		new VarianceChecker().check(ContravarianceNG2.class);
	}

	@Test(expected = IllegalVarianceException.class)
	public void testContravarianceNG3() throws IllegalVarianceException {
		new VarianceChecker().check(ContravarianceNG3.class);
	}

	@Test(expected = IllegalVarianceException.class)
	public void testContravarianceNG4() throws IllegalVarianceException {
		new VarianceChecker().check(ContravarianceNG4.class);
	}

	@Test(expected = IllegalVarianceException.class)
	public void testContravarianceNG5() throws IllegalVarianceException {
		new VarianceChecker().check(ContravarianceNG5.class);
	}

	@Test
	public void testBivarianceOK0() throws IllegalVarianceException {
		new VarianceChecker().check(BivarianceOK0.class);
	}

	@Test(expected = IllegalVarianceException.class)
	public void testBivarianceNG1() throws IllegalVarianceException {
		new VarianceChecker().check(BivarianceNG1.class);
	}

	@Test(expected = IllegalVarianceException.class)
	public void testBivarianceNG2() throws IllegalVarianceException {
		new VarianceChecker().check(BivarianceNG2.class);
	}

	@Test(expected = IllegalVarianceException.class)
	public void testBivarianceNG3() throws IllegalVarianceException {
		new VarianceChecker().check(BivarianceNG3.class);
	}

	@Test(expected = IllegalVarianceException.class)
	public void testBivarianceNG4() throws IllegalVarianceException {
		new VarianceChecker().check(BivarianceNG4.class);
	}

	@Test(expected = IllegalVarianceException.class)
	public void testBivarianceNG5() throws IllegalVarianceException {
		new VarianceChecker().check(BivarianceNG5.class);
	}

	@Test(expected = IllegalVarianceException.class)
	public void testBivarianceNG6() throws IllegalVarianceException {
		new VarianceChecker().check(BivarianceNG6.class);
	}

	@Test(expected = IllegalVarianceException.class)
	public void testBivarianceNG7() throws IllegalVarianceException {
		new VarianceChecker().check(BivarianceNG7.class);
	}

	@Test(expected = IllegalVarianceException.class)
	public void testBivarianceNG8() throws IllegalVarianceException {
		new VarianceChecker().check(BivarianceNG8.class);
	}

	@Test(expected = IllegalVarianceException.class)
	public void testBivarianceNG9() throws IllegalVarianceException {
		new VarianceChecker().check(BivarianceNG9.class);
	}

	@Test(expected = IllegalVarianceException.class)
	public void testBivarianceNG10() throws IllegalVarianceException {
		new VarianceChecker().check(BivarianceNG10.class);
	}

	private static interface InvarianceOK0<T> {
		public void foo();
	}

	private static interface InvarianceOK1<T> {
		public T foo();
	}

	private static interface InvarianceOK2<T> {
		public void foo(T t);
	}

	private static interface InvarianceOK3<T> {
		public Supplier<T> foo();
	}

	private static interface InvarianceOK4<T> {
		public Supplier<? extends T> foo();
	}

	private static interface InvarianceOK5<T> {
		public void foo(Supplier<T> t);
	}

	private static interface InvarianceOK6<T> {
		public void foo(Supplier<? extends T> t);
	}

	private static interface InvarianceOK7<T> {
		public Consumer<T> foo();
	}

	private static interface InvarianceOK8<T> {
		public Consumer<? extends T> foo();
	}

	private static interface InvarianceOK9<T> {
		public void foo(Consumer<T> t);
	}

	private static interface InvarianceOK10<T> {
		public void foo(Consumer<? extends T> t);
	}

	private static interface CovarianceOK0<@Out T> {
		public void foo();
	}

	private static interface CovarianceOK1<@Out T> {
		public T foo();
	}

	private static interface CovarianceOK2<@Out T> {
		public Supplier<T> foo();
	}

	private static interface CovarianceOK3<@Out T> {
		public Supplier<? extends T> foo();
	}

	private static interface CovarianceOK4<@Out T> {
		public void foo(Consumer<T> t);
	}

	private static interface CovarianceOK5<@Out T> {
		public void foo(Consumer<? super T> t);
	}

	private static interface CovarianceNG1<@Out T> {
		public void foo(T t);
	}

	private static interface CovarianceNG2<@Out T> {
		public Consumer<T> foo();
	}

	private static interface CovarianceNG3<@Out T> {
		public Consumer<? super T> foo();
	}

	private static interface CovarianceNG4<@Out T> {
		public void foo(Supplier<T> t);
	}

	private static interface CovarianceNG5<@Out T> {
		public void foo(Supplier<? extends T> t);
	}

	private static interface ContravarianceOK0<@In T> {
		public void foo();
	}

	private static interface ContravarianceOK1<@In T> {
		public void foo(T t);
	}

	private static interface ContravarianceOK2<@In T> {
		public Consumer<T> foo();
	}

	private static interface ContravarianceOK3<@In T> {
		public Consumer<? super T> foo();
	}

	private static interface ContravarianceOK4<@In T> {
		public void foo(Supplier<T> t);
	}

	private static interface ContravarianceOK5<@In T> {
		public void foo(Supplier<? extends T> t);
	}

	private static interface ContravarianceNG1<@In T> {
		public T foo();
	}

	private static interface ContravarianceNG2<@In T> {
		public Supplier<T> foo();
	}

	private static interface ContravarianceNG3<@In T> {
		public Supplier<? extends T> foo();
	}

	private static interface ContravarianceNG4<@In T> {
		public void foo(Consumer<T> t);
	}

	private static interface ContravarianceNG5<@In T> {
		public void foo(Consumer<? super T> t);
	}

	private static interface BivarianceOK0<@In @Out T> {
		public void foo();
	}

	private static interface BivarianceNG1<@In @Out T> {
		public T foo();
	}

	private static interface BivarianceNG2<@In @Out T> {
		public void foo(T t);
	}

	private static interface BivarianceNG3<@In @Out T> {
		public Supplier<T> foo();
	}

	private static interface BivarianceNG4<@In @Out T> {
		public Supplier<? extends T> foo();
	}

	private static interface BivarianceNG5<@In @Out T> {
		public void foo(Supplier<T> t);
	}

	private static interface BivarianceNG6<@In @Out T> {
		public void foo(Supplier<? extends T> t);
	}

	private static interface BivarianceNG7<@In @Out T> {
		public Consumer<T> foo();
	}

	private static interface BivarianceNG8<@In @Out T> {
		public Consumer<? extends T> foo();
	}

	private static interface BivarianceNG9<@In @Out T> {
		public void foo(Consumer<T> t);
	}

	private static interface BivarianceNG10<@In @Out T> {
		public void foo(Consumer<? extends T> t);
	}
}
