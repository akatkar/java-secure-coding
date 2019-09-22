package secure.coding.chapter05.obj.obj11;

/**
 * @rule OBJ11-J. Be wary of letting constructors throw exceptions
 * 
 * @description An object is partially initialized if a constructor has begun
 *              building the object but has not finished. As long as the object
 *              is not fully initialized, it must be hidden from other classes.
 
 * @category Noncompliant code
 */
public class BankOperations {
	public BankOperations() {
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
}