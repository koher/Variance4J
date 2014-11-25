package org.koherent.variance.test;

import java.io.File;
import java.util.Arrays;

public class SupportClassTest extends VarianceProcessorTest {
	@Override
	protected Iterable<? extends File> getFiles() {
		return Arrays.asList(new File(
				"test/org/koherent/variance/test/Supplier.java"), new File(
				"test/org/koherent/variance/test/Consumer.java"), new File(
				"test/org/koherent/variance/test/Function.java"));
	}
}
