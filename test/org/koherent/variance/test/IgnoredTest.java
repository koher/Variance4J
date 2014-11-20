package org.koherent.variance.test;

import org.junit.Test;
import org.koherent.variance.Ignored;
import org.koherent.variance.IllegalVarianceException;
import org.koherent.variance.Out;
import org.koherent.variance.VarianceChecker;

public class IgnoredTest {
	@Test
	public void testOK() throws IllegalVarianceException {
		new VarianceChecker().check(OK.class);
	}

	@Test(expected = IllegalVarianceException.class)
	public void testNG() throws IllegalVarianceException {
		new VarianceChecker().check(NG.class);
	}

	private static interface OK<@Out T> {
		T foo();

		@Ignored
		void bar(T t);
	}

	private static interface NG<@Out T> {
		T foo();

		void bar(T t);
	}
}
