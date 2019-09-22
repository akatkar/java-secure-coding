package secure.coding.chapter05.obj.obj11.solution;

/**
 * @rule OBJ11-J. Be wary of letting constructors throw exceptions
 * 
 * @description An object is partially initialized if a constructor has begun
 *              building the object but has not finished. As long as the object
 *              is not fully initialized, it must be hidden from other classes.
 * 
 * @category Compliant solution
 */
public final class BankOperations2 {
	public BankOperations2() {
		if (!performSSNVerification()) {
			throw new SecurityException("Access Denied!");
		}
	}

	private boolean performSSNVerification() {
		return false;
		// Returns true if data entered is valid, else false.
		// Assume that the attacker always enters an invalid SSN.
	}

	public void greet() {
		System.out.println("Welcome user! You may now use all the features.");
	}

	/**
	 * @category Compliant solution
	 *  
	 * @description If the class itself cannot be declared final, it can still
	 *              thwart the finalizer attack by declaring its own finalize()
	 *              method and making it final.
	 */
	@Override
	public final void finalize() {
		// do nothing
	}
}