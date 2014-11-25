package org.koherent.variance.test;

import org.koherent.variance.Out;

@AnnotationProcessorTest(error = false)
public interface Supplier<@Out T> {
	T get();
}
