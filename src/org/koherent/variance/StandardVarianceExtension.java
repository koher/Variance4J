package org.koherent.variance;

import static org.koherent.variance.Variance.CONTRAVARIANT;
import static org.koherent.variance.Variance.COVARIANT;
import static org.koherent.variance.Variance.INVARIANT;

import java.lang.reflect.Method;
import java.lang.reflect.TypeVariable;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class StandardVarianceExtension implements VarianceExtension {
	private static final StandardVarianceExtension INSTANCE = new StandardVarianceExtension();

	private final Map<TypeVariable<?>, Variance> variances;
	private final Set<Method> ignoredMethods;

	private StandardVarianceExtension() {
		variances = new HashMap<TypeVariable<?>, Variance>();
		ignoredMethods = new HashSet<Method>();

		variances.put(Iterable.class.getTypeParameters()[0], COVARIANT);

		variances.put(Iterator.class.getTypeParameters()[0], COVARIANT);

		variances.put(Comparable.class.getTypeParameters()[0], CONTRAVARIANT);

		variances.put(Comparator.class.getTypeParameters()[0], CONTRAVARIANT);
		try { // Java 8
			ignoredMethods.add(Comparator.class.getMethod("thenComparing",
					Class.forName("java.util.function.Function")));
			ignoredMethods.add(Comparator.class.getMethod("thenComparing",
					Class.forName("java.util.function.Function"),
					Comparator.class));
			ignoredMethods.add(Comparator.class.getMethod("thenComparing",
					Comparator.class));
			ignoredMethods.add(Comparator.class.getMethod("thenComparingInt",
					Class.forName("java.util.function.ToIntFunction")));
			ignoredMethods.add(Comparator.class.getMethod("thenComparingLong",
					Class.forName("java.util.function.ToLongFunction")));
			ignoredMethods.add(Comparator.class.getMethod(
					"thenComparingDouble",
					Class.forName("java.util.function.ToDoubleFunction")));
		} catch (NoSuchMethodException | SecurityException
				| ClassNotFoundException e) {
		}

		variances.put(Map.Entry.class.getTypeParameters()[0], COVARIANT); // K

		try { // Java 8
			Class<?> clazz = Class.forName("java.util.function.Supplier");

			variances.put(clazz.getTypeParameters()[0], COVARIANT);
		} catch (ClassNotFoundException e) {
		}

		try { // Java 8
			Class<?> clazz = Class.forName("java.util.function.Consumer");

			variances.put(clazz.getTypeParameters()[0], CONTRAVARIANT);
			try {
				ignoredMethods.add(clazz.getMethod("andThen", clazz));
			} catch (NoSuchMethodException | SecurityException e) {
				throw new Error("Never reaches here.", e);
			}
		} catch (ClassNotFoundException e) {
		}

		try { // Java 8
			Class<?> clazz = Class.forName("java.util.function.Predicate");

			variances.put(clazz.getTypeParameters()[0], CONTRAVARIANT);
			try {
				ignoredMethods.add(clazz.getMethod("and", clazz));
				ignoredMethods.add(clazz.getMethod("or", clazz));
			} catch (NoSuchMethodException | SecurityException e) {
				throw new Error("Never reaches here.", e);
			}
		} catch (ClassNotFoundException e) {
		}

		try { // Java 8
			Class<?> clazz = Class.forName("java.util.function.Function");

			variances.put(clazz.getTypeParameters()[0], CONTRAVARIANT); // T
			variances.put(clazz.getTypeParameters()[1], COVARIANT); // R
		} catch (ClassNotFoundException e) {
		}

		try { // Java 8
			Class<?> clazz = Class.forName("java.util.function.ToIntFunction");

			variances.put(clazz.getTypeParameters()[0], CONTRAVARIANT);
		} catch (ClassNotFoundException e) {
		}

		try { // Java 8
			Class<?> clazz = Class.forName("java.util.function.ToLongFunction");

			variances.put(clazz.getTypeParameters()[0], CONTRAVARIANT);
		} catch (ClassNotFoundException e) {
		}

		try { // Java 8
			Class<?> clazz = Class
					.forName("java.util.function.ToDoubleFunction");

			variances.put(clazz.getTypeParameters()[0], CONTRAVARIANT);
		} catch (ClassNotFoundException e) {
		}

		try { // Java 8
			Class<?> clazz = Class.forName("java.util.function.BiConsumer");

			variances.put(clazz.getTypeParameters()[0], CONTRAVARIANT); // T
			variances.put(clazz.getTypeParameters()[1], CONTRAVARIANT); // U
			try {
				ignoredMethods.add(clazz.getMethod("andThen", clazz));
			} catch (NoSuchMethodException | SecurityException e) {
				throw new Error("Never reaches here.", e);
			}
		} catch (ClassNotFoundException e) {
		}

		try { // Java 8
			Class<?> clazz = Class.forName("java.util.function.BiPredicate");

			variances.put(clazz.getTypeParameters()[0], CONTRAVARIANT); // T
			variances.put(clazz.getTypeParameters()[1], CONTRAVARIANT); // U
			try {
				ignoredMethods.add(clazz.getMethod("and", clazz));
				ignoredMethods.add(clazz.getMethod("or", clazz));
			} catch (NoSuchMethodException | SecurityException e) {
				throw new Error("Never reaches here.", e);
			}
		} catch (ClassNotFoundException e) {
		}

		try { // Java 8
			Class<?> clazz = Class.forName("java.util.function.BiFunction");

			variances.put(clazz.getTypeParameters()[0], CONTRAVARIANT); // T
			variances.put(clazz.getTypeParameters()[1], CONTRAVARIANT); // U
			variances.put(clazz.getTypeParameters()[2], COVARIANT); // R
		} catch (ClassNotFoundException e) {
		}

		try { // Java 8
			Class<?> clazz = Class
					.forName("java.util.function.ToIntBiFunction");

			variances.put(clazz.getTypeParameters()[0], CONTRAVARIANT); // T
			variances.put(clazz.getTypeParameters()[1], CONTRAVARIANT); // U
		} catch (ClassNotFoundException e) {
		}

		try { // Java 8
			Class<?> clazz = Class
					.forName("java.util.function.ToLongBiFunction");

			variances.put(clazz.getTypeParameters()[0], CONTRAVARIANT); // T
			variances.put(clazz.getTypeParameters()[1], CONTRAVARIANT); // U
		} catch (ClassNotFoundException e) {
		}

		try { // Java 8
			Class<?> clazz = Class
					.forName("java.util.function.ToDoubleBiFunction");

			variances.put(clazz.getTypeParameters()[0], CONTRAVARIANT); // T
			variances.put(clazz.getTypeParameters()[1], CONTRAVARIANT); // U
		} catch (ClassNotFoundException e) {
		}

		try { // Java 8
			Class<?> clazz = Class.forName("java.util.Optional");

			variances.put(clazz.getTypeParameters()[0], COVARIANT);
			try {
				ignoredMethods.add(clazz.getMethod("orElse", Object.class));
				ignoredMethods.add(clazz.getMethod("orElseGet",
						Class.forName("java.util.function.Supplier")));
			} catch (NoSuchMethodException | SecurityException e) {
				throw new Error("Never reaches here.", e);
			}
		} catch (ClassNotFoundException e) {
		}

		try { // Java 8
			Class<?> clazz = Class.forName("java.util.stream.Stream");
			Class<?> binaryOperatorClass = Class
					.forName("java.util.function.BinaryOperator");

			variances.put(clazz.getTypeParameters()[0], COVARIANT);
			try {
				ignoredMethods.add(clazz.getMethod("reduce",
						binaryOperatorClass));
				ignoredMethods.add(clazz.getMethod("reduce", Object.class,
						binaryOperatorClass));
			} catch (NoSuchMethodException | SecurityException e) {
				throw new Error("Never reaches here.", e);
			}
		} catch (ClassNotFoundException e) {
		}

		try { // Java 8
			Class<?> clazz = Class.forName("java.util.stream.Collector");

			variances.put(clazz.getTypeParameters()[0], CONTRAVARIANT); // T
			variances.put(clazz.getTypeParameters()[2], COVARIANT); // R
		} catch (ClassNotFoundException e) {
		}

		try { // Java 8
			Class<?> clazz = Class.forName("java.util.Spliterator");

			variances.put(clazz.getTypeParameters()[0], COVARIANT);
			try {
				ignoredMethods.add(clazz.getMethod("getComparator"));
			} catch (NoSuchMethodException | SecurityException e) {
				throw new Error("Never reaches here.", e);
			}
		} catch (ClassNotFoundException e) {
		}
	}

	public static StandardVarianceExtension getInstance() {
		return INSTANCE;
	}

	@Override
	public Variance getVariance(TypeVariable<?> typeVariable) {
		Variance variance = variances.get(typeVariable);

		return variance == null ? INVARIANT : variance;
	}

	@Override
	public boolean ignoresMethod(Method method) {
		return ignoredMethods.contains(method);
	}
}
