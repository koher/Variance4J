package org.koherent.variance.test;

import java.util.Comparator;
import java.util.Iterator;
import java.util.Map;

import org.junit.Test;
import org.koherent.variance.IllegalVarianceException;
import org.koherent.variance.StandardVarianceExtension;
import org.koherent.variance.VarianceChecker;

public class StandardExtensionTest {
	@Test
	public void testOK() throws IllegalVarianceException {
		VarianceChecker checker = new VarianceChecker(
				StandardVarianceExtension.getInstance());

		checker.check(Iterable.class);
		checker.check(Iterator.class);

		checker.check(Comparable.class);
		checker.check(Comparator.class);

		checker.check(Map.Entry.class);
	}
}
