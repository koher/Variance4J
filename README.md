Java-Variance
============================

__org.koherent.variance__ provides the __declaration-site variance annotations like C# for Java__.

```java
interface Function<@In T, @Out R> {
        R apply(T t);
}
```

It is possible to check variances with VarianceChecker.

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

License
----------------------------

[The MIT License](LICENSE).

References
----------------------------

- [Exact rules for variance validity](http://blogs.msdn.com/b/ericlippert/archive/2009/12/03/exact-rules-for-variance-validity.aspx)

