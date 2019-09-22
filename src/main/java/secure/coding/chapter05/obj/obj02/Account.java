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
public class Account {
	// Maintains all banking related data such as account balance
	private double balance = 100;

	boolean withdraw(double amount) {
		if ((balance - amount) >= 0) {
			balance -= amount;
			System.out.println("Withdrawal successful. The balance is : " + balance);
			return true;
		}
		return false;
	}

	// Maintains all banking related data such as account balance
	boolean overdraft() {
		balance += 300; // Add 300 in case there is an overdraft
		System.out.println("Added back-up amount. The balance is :" + balance);
		return true;
	}
}
