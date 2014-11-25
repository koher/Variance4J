package org.koherent.variance.test;

import java.io.File;
import java.util.Collections;
import java.util.Map;

public class StandardExtensionTest extends VarianceProcessorTest {
	@Override
	protected Iterable<? extends File> getFiles() {
		return Collections.singleton(new File(
				"src/org/koherent/variance/StandardVarianceExtension.java"));
	}

	@Override
	protected Map<String, String> getOptions() {
		return Collections.singletonMap("extension",
				"org.koherent.variance.StandardVarianceExtension");
	}
}
