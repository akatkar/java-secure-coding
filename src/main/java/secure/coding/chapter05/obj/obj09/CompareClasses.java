package secure.coding.chapter05.obj.obj09;

/**
 * @rule OBJ09-J. Compare classes and not class names
 * 
 * 
 * @description In a JVM, “Two classes are the same class (and consequently the
 *              same type) if they are loaded by the same class loader, and they
 *              have the same fully qualified name” [ JVMSpec 1999]. Two classes
 *              with the same name but different package names are distinct, as
 *              are two classes with the same fully qualified name loaded by
 *              different class loaders.
 */
public class CompareClasses {

	/**
	 * @category Noncompliant code
	 * 
	 * @description This noncompliant code example compares the name of the class of
	 *              object auth to the string
	 *              "com.application.auth.DefaultAuthenticationHandler" and branches
	 *              on the result of the comparison.
	 */
	void _compareAuth() {
		Object auth = new Object();
		// Determine whether object auth has required/expected class object
		if (auth.getClass().getName().equals("com.application.auth.DefaultAuthenticationHandler")) {
			// . ..
		}
	}

	/**
	 * @category Compliant solution
	 * 
	 * @description This compliant solution compares the class object auth to the
	 *              class object that the current class loader loads, instead of
	 *              comparing just the class names.
	 * 
	 * @throws ClassNotFoundException
	 */
	void compareAuth() throws ClassNotFoundException {
		Object auth = new Object();
		// Determine whether object auth has required/expected class name
		if (auth.getClass() == this.getClass().getClassLoader()
				.loadClass("com.application.auth.DefaultAuthenticationHandler")) {
			// . ..
		}
	}

	/**
	 * @category Noncompliant code
	 * 
	 * @description This noncompliant code example compares the names of the class
	 *              objects of x and y using the equals() method. Again, it is
	 *              possible that x and y are distinct classes with the same name if
	 *              they come from different class loaders.
	 */
	void _compare(Object x, Object y) {
		// Determine whether objects x and y have the same class name
		if (x.getClass().getName().equals(y.getClass().getName())) {
			// Objects have the same class
		}
	}

	/**
	 * @category Compliant solution
	 * 
	 * @description This compliant solution correctly compares the two objects’
	 *              classes.
	 */
	void compare(Object x, Object y) {
		// Determine whether objects x and y have the same class
		if (x.getClass() == y.getClass()) {
			// Objects have the same class
		}
	}
}
