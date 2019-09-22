package secure.coding.chapter06.met.met07;

/**
 * @rule MET07-J. Never declare a class method that hides a method declared in a
 *       superclass or super interface
 * 
 * @description When a class declares a static method m, the declaration of m
 *              hides any method m', where the signature of m is a sub signature
 *              of the signature of m', and the declaration of m' is both in the
 *              super classes and super interfaces of the declaring class and
 *              also would otherwise be accessible to code in the declaring
 *              class (Java Language Specification, §8.4.8.2, “Hiding (by Class
 *              Methods)” [ JLS 2005 ]).
 * 
 * @category Noncompliant code
 */
class GrantAccess {
	public static void displayAccountStatus() {
		System.out.println("Account details for admin: XX");
	}
}

/**
 * @description In this noncompliant example, the programmer hides the static
 *              method rather than overriding it. Consequently, the code invokes
 *              the displayAccountStatus() method of the superclass at two
 *              different call sites instead of invoking the superclass method
 *              at one call site and the subclass method at the other.
 */
class GrantUserAccess extends GrantAccess {
	public static void displayAccountStatus() {
		System.out.println("Account details for user: XX");
	}
}

public class StatMethod {

	public static void choose(String username) {
		GrantAccess admin = new GrantAccess();
		GrantAccess user = new GrantUserAccess();
		if (username.equals("admin")) {
			admin.displayAccountStatus();
		} else {
			user.displayAccountStatus();
		}
	}

	public static void main(String[] args) {
		choose("user");
	}
}
