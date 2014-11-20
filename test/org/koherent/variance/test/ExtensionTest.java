package org.koherent.variance.test;

import static org.koherent.variance.Variance.CONTRAVARIANT;
import static org.koherent.variance.Variance.COVARIANT;
import static org.koherent.variance.Variance.INVARIANT;

import java.lang.reflect.Method;
import java.lang.reflect.TypeVariable;
import java.util.Comparator;
import java.util.Iterator;

import org.junit.Test;
import org.koherent.variance.IllegalVarianceException;
import org.koherent.variance.In;
import org.koherent.variance.Out;
import org.koherent.variance.StandardVarianceExtension;
import org.koherent.variance.Variance;
import org.koherent.variance.VarianceChecker;
import org.koherent.variance.VarianceExtension;

public class ExtensionTest {
	@Test
	public void testFooOK1() throws IllegalVarianceException {
		new VarianceChecker(new VarianceExtension() {
			@Override
			public Variance getVariance(TypeVariable<?> typeVariable) {
				return Iterator.class.getTypeParameters()[0]
						.equals(typeVariable) ? COVARIANT : INVARIANT;
			}

			@Override
			public boolean ignoresMethod(Method method) {
				return false;
			}
		}).check(Foo.class);
	}

	@Test
	public void testFooOK2() throws IllegalVarianceException {
		new VarianceChecker(StandardVarianceExtension.getInstance())
				.check(Foo.class);
	}

	@Test
	public void testBarOK1() throws IllegalVarianceException {
		new VarianceChecker(new VarianceExtension() {
			@Override
			public Variance getVariance(TypeVariable<?> typeVariable) {
				return Comparator.class.getTypeParameters()[0]
						.equals(typeVariable) ? CONTRAVARIANT : INVARIANT;
			}

			@Override
			public boolean ignoresMethod(Method method) {
				return false;
			}
		}).check(Bar.class);
	}

	@Test
	public void testBarOK2() throws IllegalVarianceException {
		new VarianceChecker(StandardVarianceExtension.getInstance())
				.check(Bar.class);
	}

	@Test
	public void testBazOK1() throws IllegalVarianceException {
		new VarianceChecker(new VarianceExtension() {
			@Override
			public Variance getVariance(TypeVariable<?> typeVariable) {
				return Iterator.class.getTypeParameters()[0]
						.equals(typeVariable) ? COVARIANT : INVARIANT;
			}

			@Override
			public boolean ignoresMethod(Method method) {
				return false;
			}
		}).check(Baz.class);
	}

	@Test
	public void testBazOK2() throws IllegalVarianceException {
		new VarianceChecker(StandardVarianceExtension.getInstance())
				.check(Baz.class);
	}

	@Test
	public void testQuxOK1() throws IllegalVarianceException {
		new VarianceChecker(new VarianceExtension() {
			@Override
			public Variance getVariance(TypeVariable<?> typeVariable) {
				return Comparator.class.getTypeParameters()[0]
						.equals(typeVariable) ? CONTRAVARIANT : INVARIANT;
			}

			@Override
			public boolean ignoresMethod(Method method) {
				return false;
			}
		}).check(Qux.class);
	}

	@Test
	public void testQuxOK2() throws IllegalVarianceException {
		new VarianceChecker(StandardVarianceExtension.getInstance())
				.check(Qux.class);
	}

	@Test(expected = IllegalVarianceException.class)
	public void testFooNG() throws IllegalVarianceException {
		new VarianceChecker().check(Foo.class);
	}

	@Test(expected = IllegalVarianceException.class)
	public void testBarNG() throws IllegalVarianceException {
		new VarianceChecker().check(Bar.class);
	}

	@Test(expected = IllegalVarianceException.class)
	public void testBazNG() throws IllegalVarianceException {
		new VarianceChecker().check(Baz.class);
	}

	@Test(expected = IllegalVarianceException.class)
	public void testQuxNG() throws IllegalVarianceException {
		new VarianceChecker().check(Qux.class);
	}

	private static interface Foo<@Out T> {
		public Iterator<T> foo();
	}

	private static interface Bar<@Out T> {
		public void bar(Comparator<T> comparator);
	}

	private static interface Baz<@In T> {
		public void baz(Iterator<T> iterator);
	}

	private static interface Qux<@In T> {
		public Comparator<T> qux();
	}
}
