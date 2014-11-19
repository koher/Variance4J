package org.koherent.variance.test;

import org.koherent.variance.In;
import org.koherent.variance.Out;

public interface Function<@In T, @Out R> {
	R apply(T t);
}
