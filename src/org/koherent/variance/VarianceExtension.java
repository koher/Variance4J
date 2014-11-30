package org.koherent.variance;

import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeParameterElement;

public interface VarianceExtension {
	Variance getVariance(TypeParameterElement typeParameter);

	default void initialize(ProcessingEnvironment processingEnvironment) {
	}

	default boolean ignoresMethod(ExecutableElement method) {
		return false;
	}
}
