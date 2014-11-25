package org.koherent.variance;

import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeParameterElement;

public interface VarianceExtension {
	void initialize(ProcessingEnvironment processingEnvironment);

	Variance getVariance(TypeParameterElement typeParameter);

	boolean ignoresMethod(ExecutableElement method);

	public static interface Factory {
		VarianceExtension create(ProcessingEnvironment processingEnvironment);
	}
}
