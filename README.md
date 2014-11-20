Variance4J
============================

__Variance4J__ provides the __declaration-site variance annotations like C# for Java__.

```java
interface Function<@In T, @Out R> {
        R apply(T t);
}
```

It is possible to check variances with `VarianceChecker`.

```java
interface Foo<@Out T> {
	T foo(); // OK
}

interface Bar<@In T> {
	T bar(); // NG
}

// in main
try {
	VarianceChecker checker = new VarianceChecker();

	checker.check(Foo.class); // OK
	checker.check(Bar.class); // NG
} catch (IllegalVarianceException e) {
	e.printStackTrace();
}
```

```
org.koherent.variance.IllegalVarianceException: The return type T of 'T Bar#bar()' is illegal.
```

It can also check more complex types like the following.

```java
interface Baz<@In T, @Out U> {
        Function<? super T, ? extends U> baz(Function<? super U, ? extends T> f); // OK
}
```

Features
----------------------------

- `@In`, `@Out` : Declaration-site variance annotations by Java annotations
- `VarianceChecker` : Checks variance validity based on `@In` and `@Out`
- `VarianceExtension` : Annotates variances for existing classes

License
----------------------------

[The MIT License](LICENSE).

References
----------------------------

- [Exact rules for variance validity - Eric Lippert's Blog](http://blogs.msdn.com/b/ericlippert/archive/2009/12/03/exact-rules-for-variance-validity.aspx)
- [Covariance and contravariance (computer science) - Wikipedia](http://en.wikipedia.org/wiki/Covariance_and_contravariance_%28computer_science%29)
