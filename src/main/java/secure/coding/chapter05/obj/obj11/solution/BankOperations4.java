package secure.coding.chapter05.obj.obj11.solution;

/**
 * @rule OBJ11-J. Be wary of letting constructors throw exceptions
 * 
 * @description An object is partially initialized if a constructor has begun
 *              building the object but has not finished. As long as the object
 *              is not fully initialized, it must be hidden from other classes.
 * 
 * @category Compliant solution Initialized Flag
 */
public final class BankOperations4 {

	private volatile boolean initialized = false;

	public BankOperations4() {
		if (!performSSNVerification()) {
			throw new SecurityException("Access Denied!");
		}
		this.initialized = true; // object construction successful
	}

	private boolean performSSNVerification() {
		return false;
	}

	public void greet() {
		if (!this.initialized) {
			throw new SecurityException("Access Denied!");
		}
		System.out.println("Welcome user! You may now use all the features.");
	}
}