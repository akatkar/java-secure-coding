package secure.coding.chapter06.met.met07.solution;

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
 * @category Compliant solution
 * 
 * @description In this compliant solution, the programmer declares the
 *              displayAccountStatus() methods as instance methods by removing
 *              the static keyword. Consequently, the dynamic dispatch at the
 *              call sites produces the expected result. The @Override
 *              annotation indicates intentional overriding of the parent
 *              method.
 */
class GrantAccess {
	public void displayAccountStatus() {
		System.out.print("Account details for admin: XX");
	}
}

class GrantUserAccess extends GrantAccess {
	@Override
	public void displayAccountStatus() {
		System.out.print("Account details for user: XX");
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
