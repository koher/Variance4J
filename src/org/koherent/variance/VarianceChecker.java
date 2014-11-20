package org.koherent.variance;

import static org.koherent.variance.Variance.COVARIANT;
import static org.koherent.variance.Variance.CONTRAVARIANT;
import static org.koherent.variance.Variance.INVARIANT;

import java.lang.reflect.GenericArrayType;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.lang.reflect.WildcardType;

public class VarianceChecker {
	private static final VarianceExtension EMPTY_EXTENSION = new VarianceExtension() {
		@Override
		public Variance getVariance(TypeVariable<?> typeVariable) {
			return INVARIANT;
		}

		@Override
		public boolean ignoresMethod(Method method) {
			return false;
		}
	};

	private VarianceExtension extension;

	public VarianceChecker() {
		this(EMPTY_EXTENSION);
	}

	public VarianceChecker(VarianceExtension extension) {
		this.extension = extension;
	}

	public void check(Class<?> clazz) throws IllegalVarianceException {
		for (Method method : clazz.getMethods()) {
			if (method.getAnnotation(Ignored.class) != null
					|| extension.ignoresMethod(method)) {
				continue;
			}

			Type returnType = method.getGenericReturnType();
			if (!getValidity(returnType).isValidCovariantly()) {
				throw new IllegalVarianceException(method, returnType, false);
			}

			for (Type parameterType : method.getGenericParameterTypes()) {
				if (!getValidity(parameterType).isValidContravariantly()) {
					throw new IllegalVarianceException(method, parameterType,
							true);
				}
			}
		}
	}

	private Variance getValidity(Type type) {
		if (type instanceof Class) {
			return INVARIANT;
		} else if (type instanceof GenericArrayType) {
			GenericArrayType genericArrayType = (GenericArrayType) type;

			return getValidity(genericArrayType.getGenericComponentType());
		} else if (type instanceof ParameterizedType) {
			ParameterizedType parameterizedType = (ParameterizedType) type;

			Class<?> rawType = (Class<?>) parameterizedType.getRawType();
			TypeVariable<?>[] typeParameters = rawType.getTypeParameters();
			Type[] typeArguments = parameterizedType.getActualTypeArguments();

			Variance validity = INVARIANT;

			int numberOfParameters = typeParameters.length;
			for (int parameterIndex = 0; parameterIndex < numberOfParameters; parameterIndex++) {
				Type typeArgument = typeArguments[parameterIndex];
				Variance argumentValidity = getValidity(typeArgument);
				Variance parameterVariance = getExtendedVariance(typeParameters[parameterIndex]);
				if (typeArgument instanceof WildcardType) {
					if (((WildcardType) typeArgument).getLowerBounds().length > 0) {
						parameterVariance = Variance.or(parameterVariance,
								CONTRAVARIANT);
					} else {
						parameterVariance = Variance.or(parameterVariance,
								COVARIANT);
					}
				}

				if (parameterVariance.isValidCovariantly()) {
					validity = Variance.or(validity, argumentValidity);
				}

				if (parameterVariance.isValidContravariantly()) {
					validity = Variance.or(validity,
							argumentValidity.transpose());
				}
			}

			return validity;
		} else if (type instanceof TypeVariable) {
			TypeVariable<?> typeVariable = (TypeVariable<?>) type;

			return getExtendedVariance(typeVariable);
		} else if (type instanceof WildcardType) {
			WildcardType wildcardType = (WildcardType) type;

			Type[] lowerBounds = wildcardType.getLowerBounds();
			if (lowerBounds.length > 0) {
				return getValidity(lowerBounds[0]);
			} else {
				return getValidity(wildcardType.getUpperBounds()[0]);
			}
		}

		throw new IllegalArgumentException(type.getTypeName());
	}

	private Variance getExtendedVariance(TypeVariable<?> typeVariable) {
		return Variance.or(Variance.of(typeVariable),
				extension.getVariance(typeVariable));
	}
}
