package secure.coding.chapter11.err.err06.solution;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

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
 * @category Compliant Solution (Constructor.newInstance())
 * 
 * @description This compliant solution uses
 *              java.lang.reflect.Constructor.newInstance() rather than
 *              Class.newInstance(). The Constructor.newInstance() method wraps
 *              any exceptions thrown from within the constructor into a checked
 *              exception called InvocationTargetException.
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
			Constructor<NewInstance> constructor = NewInstance.class.getConstructor(new Class<?>[0]);
			constructor.newInstance();
		} catch (InstantiationException e) { /* unreachable */
		} catch (IllegalAccessException e) { /* unreachable */
		} catch (InvocationTargetException e) {
			System.out.println("Exception thrown: " + e.getCause().toString());
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
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