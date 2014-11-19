package org.koherent.variance.test;

import org.koherent.variance.Out;

public interface Supplier<@Out T> {
	T get();
}
