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
		return !isContravariant();
	}

	public boolean isValidContravariantly() {
		return !isCovariant();
	}
}
