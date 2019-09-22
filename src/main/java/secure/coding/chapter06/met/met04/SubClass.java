package secure.coding.chapter06.met.met04;

/**
 * @rule MET04-J. Do not increase the accessibility of overridden or hidden
 *       methods
 * 
 * @description Increasing the accessibility of overridden or hidden methods
 *              permits a malicious subclass to offer wider access to the
 *              restricted method than was originally intended. Consequently,
 *              programs must override methods only when necessary and must
 *              declare methods final whenever possible to prevent malicious
 *              subclassing. When methods cannot be declared final, programs
 *              must refrain from increasing the accessibility of overridden
 *              methods.
 */

class Super {
	/**
	 * @category Noncompliant code
	 */
	protected void doLogic() {
		System.out.println("Super invoked");
	}

	/**
	 * @category Compliant solution
	 * 
	 * @description This compliant solution declares the doLogic() method final to
	 *              prevent malicious overriding.
	 */
	protected final void doLogic2() { // declare as final
		System.out.println("Super invoked");
		// Do sensitive operations
	}
}

public class SubClass extends Super {
	public void doLogic() {
		System.out.println("Sub invoked");
		// Do sensitive operations
	}
}