package org.koherent.variance;

import java.lang.reflect.Method;
import java.lang.reflect.TypeVariable;

public interface VarianceExtension {
	Variance getVariance(TypeVariable<?> typeVariable);

	boolean ignoresMethod(Method method);
}
