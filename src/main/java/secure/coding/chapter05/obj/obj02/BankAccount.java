package secure.coding.chapter05.obj.obj02;

/**
 * @rule OBJ02-J. Preserve dependencies in subclasses when changing super
 *       classes
 * 
 * @description Developers often separate program logic across multiple classes
 *              or files to modularize code and to increase re-usability. When
 *              developers modify a superclass (during maintenance, for
 *              example), the developer must ensure that changes in super
 *              classes preserve all the program invariants on which the
 *              subclasses depend. Failure to maintain all relevant invariants
 *              can cause security vulnerabilities.
 */
public class BankAccount extends Account {
	// Subclass handles authentication
	@Override
	boolean withdraw(double amount) {
		if (!securityCheck()) {
			throw new IllegalStateException("Should be IllegalAccessException");
		}
		return super.withdraw(amount);
	}

	private final boolean securityCheck() {
		// check that account management may proceed
		return true;
	}

	// NOTE: overdraft method is added later than this class
	// NOTE: lacks override of overdraft method

//	/**
//	 * @category Compliant solution
//	 */
//	@Override
//	boolean overdraft() { // override
//		throw new IllegalStateException("Should be IllegalAccessException");
//	}

	public static void main(String[] args) {
		 BankAccount account = new BankAccount();
		 // Enforce security manager check
		 boolean result = account.withdraw(200.0);
		 System.out.println("Withdrawal successful? " + result);

//		Account account = new BankAccount();
//		// Enforce security manager check
//		boolean result = account.withdraw(200.0);
//		if (!result) {
//			result = account.overdraft();
//		}
//		System.out.println("Withdrawal successful? " + result);
	}
}
