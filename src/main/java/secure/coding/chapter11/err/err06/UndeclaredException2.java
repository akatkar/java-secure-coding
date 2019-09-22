package secure.coding.chapter11.err.err06;

import java.io.IOException;

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
 * @category Noncompliant code (Class.newInstance() Workarounds)
 * 
 * @description When the programmer wishes to catch and handle the possible
 *              undeclared checked exceptions, the compiler refuses to believe
 *              that any can be thrown in the particular context. This
 *              noncompliant code example attempts to catch undeclared checked
 *              exceptions thrown by Class.newInstance(). It catches Exception
 *              and dynamically checks whether the caught exception is an
 *              instance of the possible checked exception (carefully rethrowing
 *              all other exceptions).
 */
public class UndeclaredException2 {
	public static void main(String[] args) {
		try {
			NewInstance.undeclaredThrow(new IOException("Any checked exception"));
		} catch (Throwable e) {
			if (e instanceof IOException) {
				System.out.println("IOException occurred");
			} else if (e instanceof RuntimeException) {
				throw (RuntimeException) e;
			} else {
				// forward to handler
			}
		}
	}
}