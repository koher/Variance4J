package org.koherent.variance.test;

import java.util.Optional;
import java.util.Spliterator;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.BiPredicate;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.function.ToDoubleBiFunction;
import java.util.function.ToDoubleFunction;
import java.util.function.ToIntBiFunction;
import java.util.function.ToIntFunction;
import java.util.function.ToLongBiFunction;
import java.util.function.ToLongFunction;
import java.util.stream.Collector;
import java.util.stream.Stream;

import org.junit.Test;
import org.koherent.variance.IllegalVarianceException;
import org.koherent.variance.StandardVarianceExtension;
import org.koherent.variance.VarianceChecker;

public class StandardExtensionTest8 {
	@Test
	public void testOK() throws IllegalVarianceException {
		VarianceChecker checker = new VarianceChecker(
				StandardVarianceExtension.getInstance());

		checker.check(Supplier.class);
		checker.check(Consumer.class);
		checker.check(Predicate.class);
		checker.check(Function.class);
		checker.check(ToIntFunction.class);
		checker.check(ToLongFunction.class);
		checker.check(ToDoubleFunction.class);

		checker.check(BiConsumer.class);
		checker.check(BiPredicate.class);
		checker.check(BiFunction.class);
		checker.check(ToIntBiFunction.class);
		checker.check(ToLongBiFunction.class);
		checker.check(ToDoubleBiFunction.class);

		checker.check(Optional.class);

		checker.check(Stream.class);
		checker.check(Collector.class);

		checker.check(Spliterator.class);
	}
}
