package org.koherent.variance;

import java.lang.reflect.GenericArrayType;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.lang.reflect.WildcardType;

public class VarianceChecker {
	public void check(Class<?> clazz) throws IllegalVarianceException {
		for (Method method : clazz.getMethods()) {
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
			return Variance.INVARIANT;
		} else if (type instanceof GenericArrayType) {
			GenericArrayType genericArrayType = (GenericArrayType) type;

			return getValidity(genericArrayType.getGenericComponentType());
		} else if (type instanceof ParameterizedType) {
			ParameterizedType parameterizedType = (ParameterizedType) type;

			Class<?> rawType = (Class<?>) parameterizedType.getRawType();
			TypeVariable<?>[] typeParameters = rawType.getTypeParameters();
			Type[] typeArguments = parameterizedType.getActualTypeArguments();

			boolean validCovariantly = true;
			boolean validContravariantly = true;

			int numberOfParameters = typeParameters.length;
			for (int parameterIndex = 0; parameterIndex < numberOfParameters; parameterIndex++) {
				Variance parameterVariance = Variance
						.of(typeParameters[parameterIndex]);
				Variance argumentValidity = getValidity(typeArguments[parameterIndex]);

				if (parameterVariance.isValidCovariantly()) {
					validCovariantly &= argumentValidity.isValidCovariantly();
					validContravariantly &= argumentValidity
							.isValidContravariantly();
				}

				if (parameterVariance.isValidContravariantly()) {
					validCovariantly &= argumentValidity
							.isValidContravariantly();
					validContravariantly &= argumentValidity
							.isValidCovariantly();
				}
			}

			return Variance.of(!validContravariantly, !validCovariantly);
		} else if (type instanceof TypeVariable) {
			TypeVariable<?> typeVariable = (TypeVariable<?>) type;

			return Variance.of(typeVariable);
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
}
