package secure.coding.chapter11.err.err07;

/**
 * @rule ERR07-J. Do not throw RuntimeException, Exception, or Throwable
 * 
 * @description Methods must not throw RuntimeException, Exception, or
 *              Throwable. Handling these exceptions requires catching
 *              RuntimeException, which is disallowed by rule ERR08-J. Moreover,
 *              throwing a RuntimeException can lead to subtle errors; for
 *              example, a caller cannot examine the exception to determine why
 *              it was thrown and consequently cannot attempt recovery.
 * 
 *              Methods can throw a specific exception subclassed from Exception
 *              or RuntimeException. Note that it is permissible to construct an
 *              exception class specifically for a single throw statement.
 * 
 * @category Noncompliant code
 */
public class ThrowExceptionExample {

	/**
	 * @description The isCapitalized() method in this noncompliant code example
	 *              accepts a string and returns true when the string consists of a
	 *              capital letter followed by lowercase letters. The method also
	 *              throws a RuntimeException when passed a null string argument.
	 */
	boolean isCapitalized(String s) {
		if (s == null) {
			throw new NullPointerException();
		}
		if (s.equals("")) {
			return true;
		}
		String first = s.substring(0, 1);
		String rest = s.substring(1);
		return (first.equals(first.toUpperCase()) && rest.equals(rest.toLowerCase()));
	}

	/**
	 * @description And the other noncompliant code example specifies the Exception
	 *              class in the throws clause of the method declaration for the
	 *              doSomething() method.
	 */
	void doSomething() throws Exception {
		// ...
	}
}