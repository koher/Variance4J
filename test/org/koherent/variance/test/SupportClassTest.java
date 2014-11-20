package org.koherent.variance.test;

import org.junit.Test;
import org.koherent.variance.IllegalVarianceException;
import org.koherent.variance.VarianceChecker;

public class SupportClassTest {
	@Test
	public void testOK() throws IllegalVarianceException {
		VarianceChecker checker = new VarianceChecker();

		checker.check(Supplier.class);
		checker.check(Consumer.class);
		checker.check(Function.class);
	}
}
