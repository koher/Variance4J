package org.koherent.variance.test;

import java.util.List;

import org.junit.Test;
import org.koherent.variance.IllegalVarianceException;
import org.koherent.variance.In;
import org.koherent.variance.Out;
import org.koherent.variance.VarianceChecker;

public class UseSiteVarianceTest {
	@Test
	public void testOK() throws IllegalVarianceException {
		VarianceChecker checker = new VarianceChecker();

		checker.check(OutCoReturn.class);
		checker.check(OutContraParam.class);
		checker.check(InCoParam.class);
		checker.check(InContraReturn.class);
	}

	@Test(expected = IllegalVarianceException.class)
	public void testOutCoParamNG() throws IllegalVarianceException {
		new VarianceChecker().check(OutCoParam.class);
	}

	@Test(expected = IllegalVarianceException.class)
	public void testOutContraReturnNG() throws IllegalVarianceException {
		new VarianceChecker().check(OutContraReturn.class);
	}

	@Test(expected = IllegalVarianceException.class)
	public void testInCoReturnNG() throws IllegalVarianceException {
		new VarianceChecker().check(InCoReturn.class);
	}

	@Test(expected = IllegalVarianceException.class)
	public void testInContraParamNG() throws IllegalVarianceException {
		new VarianceChecker().check(InContraParam.class);
	}

	private static interface OutCoReturn<@Out T> {
		public List<? extends T> foo();
	}

	private static interface OutCoParam<@Out T> {
		public void foo(List<? extends T> list);
	}

	private static interface OutContraReturn<@Out T> {
		public List<? super T> foo();
	}

	private static interface OutContraParam<@Out T> {
		public void foo(List<? super T> list);
	}

	private static interface InCoReturn<@In T> {
		public List<? extends T> foo();
	}

	private static interface InCoParam<@In T> {
		public void foo(List<? extends T> list);
	}

	private static interface InContraReturn<@In T> {
		public List<? super T> foo();
	}

	private static interface InContraParam<@In T> {
		public void foo(List<? super T> list);
	}
}
