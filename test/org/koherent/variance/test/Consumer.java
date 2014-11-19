package org.koherent.variance.test;

import org.koherent.variance.In;

public interface Consumer<@In T> {
	void accept(T t);
}
