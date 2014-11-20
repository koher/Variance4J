package org.koherent.variance;

import java.lang.reflect.TypeVariable;

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

	public static Variance of(TypeVariable<?> typeVariable)
			throws IllegalArgumentException {
		if (typeVariable == null) {
			throw new IllegalArgumentException("'typeVariable' cannot be null.");
		}

		return of(typeVariable.getAnnotation(Out.class) != null,
				typeVariable.getAnnotation(In.class) != null);
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
