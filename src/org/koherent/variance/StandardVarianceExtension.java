package org.koherent.variance;

import static org.koherent.variance.Variance.CONTRAVARIANT;
import static org.koherent.variance.Variance.COVARIANT;
import static org.koherent.variance.Variance.INVARIANT;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.TypeParameterElement;
import javax.lang.model.util.Elements;

@VarianceExtending({ "java.lang.Iterable", "java.util.Iterator",
		"java.lang.Comparable", "java.util.Comparator", "java.util.Map.Entry",
		"java.util.function.Supplier", "java.util.function.Consumer",
		"java.util.function.Predicate", "java.util.function.Function",
		"java.util.function.ToIntFunction",
		"java.util.function.ToLongFunction",
		"java.util.function.ToDoubleFunction", "java.util.function.BiConsumer",
		"java.util.function.BiPredicate", "java.util.function.BiFunction",
		"java.util.function.ToIntBiFunction",
		"java.util.function.ToLongBiFunction",
		"java.util.function.ToDoubleBiFunction", "java.util.Optional",
		"java.util.stream.Stream", "java.util.stream.Collector",
		"java.util.Spliterator" })
public class StandardVarianceExtension implements VarianceExtension {
	private final Map<TypeParameterElement, Variance> variances;
	private final Set<ExecutableElement> ignoredMethods;

	public StandardVarianceExtension() {
		variances = new HashMap<TypeParameterElement, Variance>();
		ignoredMethods = new HashSet<ExecutableElement>();
	}

	@Override
	public void initialize(ProcessingEnvironment processingEnvironment) {
		Elements elementUtils = processingEnvironment.getElementUtils();

		variances.clear();
		ignoredMethods.clear();

		{
			TypeElement type = elementUtils
					.getTypeElement("java.lang.Iterable");
			variances.put(type.getTypeParameters().get(0), COVARIANT);
		}

		{
			TypeElement type = elementUtils
					.getTypeElement("java.util.Iterator");
			variances.put(type.getTypeParameters().get(0), COVARIANT);
		}

		{
			TypeElement type = elementUtils
					.getTypeElement("java.lang.Comparable");
			variances.put(type.getTypeParameters().get(0), CONTRAVARIANT);
		}

		{
			TypeElement type = elementUtils
					.getTypeElement("java.util.Comparator");
			variances.put(type.getTypeParameters().get(0), CONTRAVARIANT);
			ignoreMethods(
					elementUtils,
					type,
					new HashSet<String>(
							Arrays.asList(
									"thenComparing(java.util.Comparator<? super T>)",
									"<U>thenComparing(java.util.function.Function<? super T,? extends U>,java.util.Comparator<? super U>)",
									"<U>thenComparing(java.util.function.Function<? super T,? extends U>)",
									"thenComparingInt(java.util.function.ToIntFunction<? super T>)",
									"thenComparingLong(java.util.function.ToLongFunction<? super T>)",
									"thenComparingDouble(java.util.function.ToDoubleFunction<? super T>)")));
		}

		{
			TypeElement type = elementUtils
					.getTypeElement("java.util.Map.Entry");
			variances.put(type.getTypeParameters().get(0), COVARIANT); // K
		}

		{
			TypeElement type = elementUtils
					.getTypeElement("java.util.function.Supplier");
			if (type != null) {
				variances.put(type.getTypeParameters().get(0), COVARIANT);
			}
		}

		{
			TypeElement type = elementUtils
					.getTypeElement("java.util.function.Consumer");
			if (type != null) {
				variances.put(type.getTypeParameters().get(0), CONTRAVARIANT);
				ignoreMethods(
						elementUtils,
						type,
						new HashSet<String>(
								Arrays.asList("andThen(java.util.function.Consumer<? super T>)")));
			}
		}

		{
			TypeElement type = elementUtils
					.getTypeElement("java.util.function.Predicate");
			if (type != null) {
				variances.put(type.getTypeParameters().get(0), CONTRAVARIANT);
				ignoreMethods(
						elementUtils,
						type,
						new HashSet<String>(Arrays.asList(
								"and(java.util.function.Predicate<? super T>)",
								"or(java.util.function.Predicate<? super T>)")));
			}
		}

		{
			TypeElement type = elementUtils
					.getTypeElement("java.util.function.Function");
			if (type != null) {
				variances.put(type.getTypeParameters().get(0), CONTRAVARIANT); // T
				variances.put(type.getTypeParameters().get(1), COVARIANT); // R
			}
		}

		{
			TypeElement type = elementUtils
					.getTypeElement("java.util.function.ToIntFunction");
			if (type != null) {
				variances.put(type.getTypeParameters().get(0), CONTRAVARIANT);
			}
		}

		{
			TypeElement type = elementUtils
					.getTypeElement("java.util.function.ToLongFunction");
			if (type != null) {
				variances.put(type.getTypeParameters().get(0), CONTRAVARIANT);
			}
		}

		{
			TypeElement type = elementUtils
					.getTypeElement("java.util.function.ToDoubleFunction");
			if (type != null) {
				variances.put(type.getTypeParameters().get(0), CONTRAVARIANT);
			}
		}

		{
			TypeElement type = elementUtils
					.getTypeElement("java.util.function.BiConsumer");
			if (type != null) {
				variances.put(type.getTypeParameters().get(0), CONTRAVARIANT); // T
				variances.put(type.getTypeParameters().get(1), CONTRAVARIANT); // U
				ignoreMethods(
						elementUtils,
						type,
						new HashSet<String>(
								Arrays.asList("andThen(java.util.function.BiConsumer<? super T,? super U>)")));
			}
		}

		{
			TypeElement type = elementUtils
					.getTypeElement("java.util.function.BiPredicate");
			if (type != null) {
				variances.put(type.getTypeParameters().get(0), CONTRAVARIANT); // T
				variances.put(type.getTypeParameters().get(1), CONTRAVARIANT); // U
				ignoreMethods(
						elementUtils,
						type,
						new HashSet<String>(
								Arrays.asList(
										"and(java.util.function.BiPredicate<? super T,? super U>)",
										"or(java.util.function.BiPredicate<? super T,? super U>)")));
			}
		}

		{
			TypeElement type = elementUtils
					.getTypeElement("java.util.function.BiFunction");
			if (type != null) {
				variances.put(type.getTypeParameters().get(0), CONTRAVARIANT); // T
				variances.put(type.getTypeParameters().get(1), CONTRAVARIANT); // U
				variances.put(type.getTypeParameters().get(2), COVARIANT); // R
			}
		}

		{
			TypeElement type = elementUtils
					.getTypeElement("java.util.function.ToIntBiFunction");
			if (type != null) {
				variances.put(type.getTypeParameters().get(0), CONTRAVARIANT); // T
				variances.put(type.getTypeParameters().get(1), CONTRAVARIANT); // U
			}
		}

		{
			TypeElement type = elementUtils
					.getTypeElement("java.util.function.ToLongBiFunction");
			if (type != null) {
				variances.put(type.getTypeParameters().get(0), CONTRAVARIANT); // T
				variances.put(type.getTypeParameters().get(1), CONTRAVARIANT); // U
			}
		}

		{
			TypeElement type = elementUtils
					.getTypeElement("java.util.function.ToDoubleBiFunction");
			if (type != null) {
				variances.put(type.getTypeParameters().get(0), CONTRAVARIANT); // T
				variances.put(type.getTypeParameters().get(1), CONTRAVARIANT); // U
			}
		}

		{
			TypeElement type = elementUtils
					.getTypeElement("java.util.Optional");
			if (type != null) {
				variances.put(type.getTypeParameters().get(0), COVARIANT);
				ignoreMethods(
						elementUtils,
						type,
						new HashSet<String>(
								Arrays.asList("orElse(T)",
										"orElseGet(java.util.function.Supplier<? extends T>)")));
			}
		}

		{
			TypeElement type = elementUtils
					.getTypeElement("java.util.stream.Stream");
			if (type != null) {
				variances.put(type.getTypeParameters().get(0), COVARIANT);
				ignoreMethods(
						elementUtils,
						type,
						new HashSet<String>(
								Arrays.asList(
										"reduce(T,java.util.function.BinaryOperator<T>)",
										"reduce(T,java.util.function.BinaryOperator<T>)",
										"reduce(java.util.function.BinaryOperator<T>)")));
			}
		}

		{
			TypeElement type = elementUtils
					.getTypeElement("java.util.stream.Collector");
			if (type != null) {
				variances.put(type.getTypeParameters().get(0), CONTRAVARIANT); // T
				variances.put(type.getTypeParameters().get(2), COVARIANT); // R
			}
		}

		{
			TypeElement type = elementUtils
					.getTypeElement("java.util.Spliterator");
			if (type != null) {
				variances.put(type.getTypeParameters().get(0), COVARIANT);
				ignoreMethods(elementUtils, type,
						new HashSet<String>(Arrays.asList("getComparator()")));
			}
		}
	}

	private void ignoreMethods(Elements elementUtils, TypeElement type,
			Set<String> methodNames) {
		for (Element member : elementUtils.getAllMembers(type)) {
			if (member.getKind() == ElementKind.METHOD) {
				ExecutableElement method = (ExecutableElement) member;
				if (methodNames.contains(method.toString())) {
					ignoredMethods.add(method);
				}
			}
		}

	}

	@Override
	public Variance getVariance(TypeParameterElement typeParameter) {
		Variance variance = variances.get(typeParameter);

		return variance == null ? INVARIANT : variance;
	}

	@Override
	public boolean ignoresMethod(ExecutableElement method) {
		return ignoredMethods.contains(method);
	}
}
