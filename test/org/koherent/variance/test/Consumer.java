package org.koherent.variance.test;

import org.koherent.variance.In;

@AnnotationProcessorTest(error = false)
public interface Consumer<@In T> {
	void accept(T t);
}
