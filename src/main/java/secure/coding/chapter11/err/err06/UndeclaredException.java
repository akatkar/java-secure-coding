package secure.coding.chapter11.err.err06;

/**
 * @rule ERR06-J. Do not throw undeclared checked exceptions
 * 
 * @description Java requires that each method address every checked exception
 *              that can be thrown during its execution either by handling the
 *              exception within a try-catch block or by declaring that the
 *              exception can propagate out of the method (via the throws
 *              clause). Unfortunately, there are a few techniques that permit
 *              undeclared checked exceptions to be thrown at runtime. Such
 *              techniques defeat the ability of caller methods to use the
 *              throws clause to determine the complete set of checked
 *              exceptions that could propagate from an invoked method.
 *              Consequently such techniques must not be used to throw
 *              undeclared checked exceptions.
 * 
 * @category Noncompliant code (Class.newInstance())
 * 
 * @description This noncompliant code example throws undeclared checked
 *              exceptions. The undeclaredThrow() method takes a Throwable
 *              argument, and invokes a function that will throw the argument
 *              without declaring it. While undeclaredThrow() catches any
 *              exceptions the function declares that it might throw, it
 *              nevertheless throws the argument it is given without regard to
 *              whether the argument is one of the declared exceptions.
 */

class NewInstance {
	private static Throwable throwable;

	private NewInstance() throws Throwable {
		throw throwable;
	}

	public static synchronized void undeclaredThrow(Throwable throwable) {
		// These exceptions should not be passed
		if (throwable instanceof IllegalAccessException || throwable instanceof InstantiationException) {
			// Unchecked, no declaration required
			throw new IllegalArgumentException();
		}
		NewInstance.throwable = throwable;
		try {
			// next line throws the Throwable argument passed in above,
			// even though the throws clause of class.newInstance fails
			// to declare that this may happen
			NewInstance.class.newInstance();
		} catch (InstantiationException e) { /* unreachable */
		} catch (IllegalAccessException e) { /* unreachable */
		} finally { // Avoid memory leak
			NewInstance.throwable = null;
		}
	}
}

public class UndeclaredException {
	public static void main(String[] args) {
		// No declared checked exceptions
		NewInstance.undeclaredThrow(new Exception("Any checked exception"));
	}
}