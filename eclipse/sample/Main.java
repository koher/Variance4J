import org.koherent.variance.IllegalVarianceException;
import org.koherent.variance.In;
import org.koherent.variance.Out;
import org.koherent.variance.VarianceChecker;

public class Main {
	public static void main(String[] args) {
		try {
			VarianceChecker checker = new VarianceChecker();

			checker.check(Foo.class); // OK
			checker.check(Bar.class); // NG
		} catch (IllegalVarianceException e) {
			e.printStackTrace();
		}

		try {
			new VarianceChecker().check(Baz.class);
		} catch (IllegalVarianceException e) {
			e.printStackTrace();
		}
	}
}

interface Foo<@Out T> {
	T foo(); // OK
}

interface Bar<@In T> {
	T bar(); // NG
}

interface Baz<@In T, @Out U> {
	Function<? super T, ? extends U> baz(Function<? super U, ? extends T> f); // OK
}

interface Function<@In T, @Out R> {
	R apply(T t);
}
