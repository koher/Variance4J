package org.koherent.variance;

import javax.lang.model.element.TypeParameterElement;

public enum Variance {
	INVARIANT, COVARIANT, CONTRAVARIANT, BIVARIANT;

	private boolean covariant;
	private boolean contravariant;

	static {
		COVARIANT.covariant = true;

		CONTRAVARIANT.contravariant = true;

		BIVARIANT.covariant = true;
		BIVARIANT.contravariant = true;
	}

	public static Variance of(boolean covariant, boolean contravariant) {
		return covariant ? (contravariant ? BIVARIANT : COVARIANT)
				: (contravariant ? CONTRAVARIANT : INVARIANT);
	}

	public static Variance of(TypeParameterElement typeParameter)
			throws IllegalArgumentException {
		if (typeParameter == null) {
			throw new IllegalArgumentException(
					"'typeParameter' cannot be null.");
		}

		return of(typeParameter.getAnnotation(Out.class) != null,
				typeParameter.getAnnotation(In.class) != null);
	}

	public boolean isCovariant() {
		return covariant;
	}

	public boolean isContravariant() {
		return contravariant;
	}

	public boolean isValidCovariantly() {
		return !contravariant;
	}

	public boolean isValidContravariantly() {
		return !covariant;
	}

	public Variance transpose() {
		return of(contravariant, covariant);
	}

	public Variance not() {
		return of(!covariant, !contravariant);
	}

	public static Variance and(Variance left, Variance right) {
		return Variance.of(left.covariant && right.covariant,
				left.contravariant && right.contravariant);
	}

	public static Variance or(Variance left, Variance right) {
		return Variance.of(left.covariant || right.covariant,
				left.contravariant || right.contravariant);
	}
}
