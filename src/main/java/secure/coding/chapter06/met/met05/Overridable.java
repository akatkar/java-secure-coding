package secure.coding.chapter06.met.met05;

/**
 * @rule MET05-J. Ensure that constructors do not call overridable methods
 * 
 * @description Invocation of an overridable method during object construction
 *              may result in the use of uninitialized data, leading to runtime
 *              exceptions or to unanticipated outcomes. Calling overridable
 *              methods from constructors can also leak the this reference
 *              before object construction is complete, potentially exposing
 *              uninitialized or inconsistent data to other threads.
 *              
 * @category Noncompliant code
 */
class SuperClass {

	public SuperClass() {
		doLogic();
	}

	public void doLogic() {
		System.out.println("This is superclass!");
	}
}

class SubClass extends SuperClass {
	private String color = null;

	public SubClass() {
		super();
		color = "Red";
	}

	public void doLogic() {
		System.out.println("This is subclass! The color is :" + color);
		// . ..
	}
}

public class Overridable {
	public static void main(String[] args) {
		SuperClass bc = new SuperClass();
		// Prints "This is superclass!"
		bc.doLogic();
		
		SuperClass sc = new SubClass();
		// Prints "This is subclass! The color is :null"
		sc.doLogic();
	}
}