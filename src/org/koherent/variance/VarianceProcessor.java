package org.koherent.variance;

import static org.koherent.variance.Variance.CONTRAVARIANT;
import static org.koherent.variance.Variance.COVARIANT;
import static org.koherent.variance.Variance.INVARIANT;

import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.TypeParameterElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.type.ArrayType;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.type.TypeVariable;
import javax.lang.model.type.WildcardType;
import javax.lang.model.util.Elements;
import javax.tools.Diagnostic.Kind;

@SupportedSourceVersion(SourceVersion.RELEASE_8)
@SupportedAnnotationTypes({ "org.koherent.variance.In",
		"org.koherent.variance.Out", "org.koherent.variance.VarianceExtending" })
public class VarianceProcessor extends AbstractProcessor {
	@Override
	public boolean process(Set<? extends TypeElement> annotations,
			RoundEnvironment roundEnv) {
		VarianceExtension extension = Optional
				.ofNullable(processingEnv.getOptions().get("extension"))
				.flatMap(
						extensionName -> {
							try {
								return Optional.of((VarianceExtension) Class
										.forName(extensionName)
										.getConstructor().newInstance());
							} catch (InstantiationException
									| IllegalAccessException
									| IllegalArgumentException
									| InvocationTargetException
									| NoSuchMethodException | SecurityException
									| ClassNotFoundException e) {
								return Optional.empty();
							}
						}).orElseGet(() -> new VarianceExtension() {
					@Override
					public void initialize(
							ProcessingEnvironment processingEnvironment) {
					}

					@Override
					public Variance getVariance(
							TypeParameterElement typeParameter) {
						return INVARIANT;
					}

					@Override
					public boolean ignoresMethod(ExecutableElement method) {
						return false;
					}
				});
		extension.initialize(processingEnv);

		Messager messager = processingEnv.getMessager();
		Elements elementUtils = processingEnv.getElementUtils();

		Set<TypeElement> types = new HashSet<TypeElement>();
		annotations.forEach(annotation -> {
			if (In.class.getName().equals(
					annotation.getQualifiedName().toString())
					|| Out.class.getName().equals(
							annotation.getQualifiedName().toString())) {
				types.addAll(roundEnv
						.getElementsAnnotatedWith(annotation)
						.stream()
						.map(typeParameter -> (TypeElement) typeParameter
								.getEnclosingElement())
						.collect(Collectors.toSet()));
			} else if (VarianceExtending.class.getName().equals(
					annotation.getQualifiedName().toString())) {
				types.addAll(roundEnv
						.getElementsAnnotatedWith(annotation)
						.stream()
						.map(checkedType -> Arrays
								.stream(checkedType.getAnnotation(
										VarianceExtending.class).value()))
						.reduce(Stream.empty(), Stream::concat)
						.map(typeName -> elementUtils.getTypeElement(typeName))
						.collect(Collectors.toSet()));
			} else {
				throw new Error("Never reaches here.");
			}
		});

		types.forEach(type -> {
			elementUtils
					.getAllMembers(type)
					.forEach(
							member -> {
								Set<Modifier> modifiers = member.getModifiers();
								if (modifiers.contains(Modifier.PRIVATE)
										|| modifiers.contains(Modifier.STATIC)) {
									return;
								}

								if (member.getKind() == ElementKind.FIELD) {
									VariableElement field = (VariableElement) member;

									TypeMirror fieldType = field.asType();
									if (!getValidity(extension, fieldType)
											.isValidCovariantly()
											|| (!modifiers
													.contains(Modifier.FINAL) && !getValidity(
													extension, fieldType)
													.isValidContravariantly())) {
										messager.printMessage(
												Kind.ERROR,
												"The type "
														+ fieldType
														+ " of "
														+ type.getQualifiedName()
														+ "#" + field
														+ " is illegal.", field);
									}
								} else if (member.getKind() == ElementKind.METHOD) {
									ExecutableElement method = (ExecutableElement) member;
									String methodName = method.getSimpleName()
											+ "("
											+ String.join(
													", ",
													method.getParameters()
															.stream()
															.map(variableElement -> variableElement
																	.asType()
																	.toString())
															.collect(
																	Collectors
																			.toList()))
											+ ")";

									if (method.getAnnotation(Ignored.class) != null
											|| extension.ignoresMethod(method)) {
										return;
									}

									TypeMirror returnType = method
											.getReturnType();
									if (!getValidity(extension, returnType)
											.isValidCovariantly()) {
										messager.printMessage(
												Kind.ERROR,
												"The return type "
														+ returnType
														+ " of "
														+ type.getQualifiedName()
														+ "#" + methodName
														+ " is illegal.",
												method);
									}

									method.getParameters()
											.forEach(
													parameter -> {
														TypeMirror parameterType = parameter
																.asType();
														if (!getValidity(
																extension,
																parameterType)
																.isValidContravariantly()) {
															messager.printMessage(
																	Kind.ERROR,
																	"The parameter type "
																			+ parameterType
																			+ " of "
																			+ type.getQualifiedName()
																			+ "#"
																			+ methodName
																			+ " is illegal.",
																	method);
														}
													});

									method.getThrownTypes()
											.forEach(
													thrownType -> {
														if (!getValidity(
																extension,
																thrownType)
																.isValidCovariantly()) {
															messager.printMessage(
																	Kind.ERROR,
																	"The thrown type "
																			+ thrownType
																			+ " of "
																			+ type.getQualifiedName()
																			+ "#"
																			+ methodName
																			+ " is illegal.",
																	method);
														}
													});
								}
							});
		});

		return true;
	}

	private Variance getValidity(VarianceExtension extension, TypeMirror type) {
		switch (type.getKind()) {
		case BOOLEAN:
		case BYTE:
		case CHAR:
		case DOUBLE:
		case FLOAT:
		case INT:
		case LONG:
		case SHORT:
		case VOID: {
			return INVARIANT;
		}
		case DECLARED: {
			DeclaredType declaredType = (DeclaredType) type;
			List<? extends TypeMirror> typeArguments = declaredType
					.getTypeArguments();
			if (typeArguments.size() == 0) {
				return INVARIANT;
			}

			TypeElement typeElement = (TypeElement) declaredType.asElement();
			List<? extends TypeParameterElement> typeParameters = typeElement
					.getTypeParameters();

			Variance validity = INVARIANT;

			Iterator<? extends TypeMirror> typeArgumentIterator = typeArguments
					.iterator();
			Iterator<? extends TypeParameterElement> typeParameterIterator = typeParameters
					.iterator();
			while (typeParameterIterator.hasNext()) {
				TypeParameterElement typeParameter = typeParameterIterator
						.next();
				TypeMirror typeArgument = typeArgumentIterator.next();
				Variance argumentValidity = getValidity(extension, typeArgument);
				Variance parameterVariance = getExtendedVariance(extension,
						typeParameter);

				if (typeArgument.getKind() == TypeKind.WILDCARD) {
					WildcardType wildcard = (WildcardType) typeArgument;
					if (wildcard.getSuperBound() != null) {
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
		}
		case ARRAY: {
			ArrayType arrayType = (ArrayType) type;

			return getValidity(extension, arrayType.getComponentType());
		}
		case TYPEVAR: {
			TypeVariable typeVariable = (TypeVariable) type;

			return getExtendedVariance(extension,
					(TypeParameterElement) typeVariable.asElement());
		}
		case WILDCARD: {
			WildcardType wildcardType = (WildcardType) type;

			TypeMirror superBound = wildcardType.getSuperBound();
			if (superBound != null) {
				return getValidity(extension, superBound);
			}

			TypeMirror extendsBound = wildcardType.getExtendsBound();
			if (extendsBound != null) {
				return getValidity(extension, extendsBound);
			}

			return INVARIANT;
		}
		default: {
			break;
		}
		}

		throw new IllegalArgumentException(type.toString());
	}

	private Variance getExtendedVariance(VarianceExtension extension,
			TypeParameterElement typeParameter) {
		return Variance.or(Variance.of(typeParameter),
				extension.getVariance(typeParameter));
	}
}
