package secure.coding.chapter05.obj.obj11.solution;

/**
 * @rule OBJ11-J. Be wary of letting constructors throw exceptions
 * 
 * @description An object is partially initialized if a constructor has begun
 *              building the object but has not finished. As long as the object
 *              is not fully initialized, it must be hidden from other classes.
 * 
 * @category Compliant solution Java SE 6, Public and Private Constructors
 */
public final class BankOperations3 {

	public BankOperations3() {
		this(performSSNVerification());
	}

	private BankOperations3(boolean secure) {
		// secure is always true
		// constructor without any security checks
	}

	private static boolean performSSNVerification() {
		// Returns true if data entered is valid, else throws
		// a SecurityException
		// Assume that the attacker just enters invalid SSN;
		// so this method always throws the exception
		throw new SecurityException("Access Denied!");
	}

	public void greet() {
		System.out.println("Welcome user! You may now use all the features.");
	}
}