package org.koherent.variance;

import java.lang.reflect.Method;
import java.lang.reflect.Type;

public class IllegalVarianceException extends Exception {
	private static final long serialVersionUID = 1L;

	private final Method method;
	private final Type type;
	private final boolean forParameter;

	private static String createMessage(Method method, Type type,
			boolean forParameter) {
		StringBuilder messageBuilder = new StringBuilder();

		messageBuilder.append(forParameter ? "An parameter type "
				: "The return type ");
		messageBuilder.append(type.getTypeName());
		messageBuilder.append(" of '");
		messageBuilder.append(method.getGenericReturnType().getTypeName());
		messageBuilder.append(" ");
		messageBuilder.append(method.getDeclaringClass().getName());
		messageBuilder.append("#");
		messageBuilder.append(method.getName());
		messageBuilder.append("(");

		boolean first = true;
		for (Type parameterType : method.getGenericParameterTypes()) {
			if (first) {
				first = false;
			} else {
				messageBuilder.append(", ");
			}

			messageBuilder.append(parameterType.getTypeName());
		}

		messageBuilder.append(")' is illegal.");

		return messageBuilder.toString();
	}

	public IllegalVarianceException(Method method, Type type,
			boolean forParameter) {
		super(createMessage(method, type, forParameter));

		this.method = method;
		this.type = type;
		this.forParameter = forParameter;
	}

	public Method getMethod() {
		return method;
	}

	public Type getType() {
		return type;
	}

	public boolean isForParameter() {
		return forParameter;
	}
}
