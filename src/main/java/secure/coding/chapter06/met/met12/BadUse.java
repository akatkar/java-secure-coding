package secure.coding.chapter06.met.met12;

import java.util.Date;

/**
 * @rule MET12-J. DO not use finalizers
 * 
 * @description The garbage collector invokes object finalizer methods after it
 *              determines that the object is unreachable but before it reclaims
 *              the object’s storage. Execution of the finalizer provides an
 *              opportunity to release resources such as open streams, files,
 *              and network connections that might not otherwise be released
 *              automatically through the normal action of the garbage
 *              collector. A sufficient number of problems are associated with
 *              finalizers to restrict their use to exceptional conditions:
 * 
 *              - There is no fixed time at which finalizers must be executed
 *              because this depends on the JVM.
 * 
 *              - The JVM may terminate without invoking the finalizer on some
 *              or all unreachable objects. Consequently, attempts to update
 *              critical persistent state from finalizer methods can fail
 *              without warning.
 * 
 * @category Noncompliant code
 */

class BaseClass {

	@Override
	protected void finalize() throws Throwable {
		System.out.println("Superclass finalize!");
		doLogic();
	}

	public void doLogic() throws Throwable {
		System.out.println("This is super-class!");
	}
}

class SubClass extends BaseClass {
	private Date d; // mutable instance field

	protected SubClass() {
		d = new Date();
	}

	@Override
	protected void finalize() throws Throwable {
		System.out.println("Subclass finalize!");
		try {// cleanup resources
			d = null;
		} finally {
			super.finalize(); // Call BaseClass's finalizer
		}
	}

	@Override
	public void doLogic() throws Throwable {
		// any resource allocations made here will persist
		// inconsistent object state
		System.out.println("This is sub-class! The date object is: " + d);
		// 'd' is already null
	}
}

/**
 * @description This noncompliant code example uses the
 *              System.runFinalizersOnExit() method to simulate a
 *              garbage-collection run. Note that this method is deprecated
 *              because of threadsafety issues
 *
 * @description Compliant Solution
 * 
 *              Joshua Bloch [ Bloch 2008 ] suggests implementing a stop()
 *              method explicitly such that it leaves the class in an unusable
 *              state beyond its lifetime. A private field within the class can
 *              signal whether the class is unusable. All the class methods must
 *              check this field prior to operating on the class. This is akin
 *              to the “initialized flag”–compliant solution discussed in rule
 *              OBJ11-J. As always, a good place to call the termination logic
 *              is in the finally block.
 */
public final class BadUse {
	public static void main(String[] args) {
		try {
			BaseClass bc = new SubClass();
			// Artificially simulate finalization (do not do this)
			System.runFinalizersOnExit(true);
		} catch (Throwable t) {
			// handle error
		}
	}
}