package secure.coding.chapter10.msc.MSC07.solution;

/**
 * @rule MSC07-J. Prevent multiple instantiations of singleton objects
 * 
 * @description A class that implements the singleton design pattern must
 *              prevent multiple instantiations. Relevant techniques include
 * 
 *              - making its constructor private.
 * 
 *              - employing lock mechanisms to prevent an initialization routine
 *              from running simultaneously by multiple threads.
 * 
 *              - ensuring the class is not serializable.
 * 
 *              - ensuring the class cannot be cloned.
 * 
 *              - preventing the class from being garbage-collected if it was
 *              loaded by a custom class loader.
 * 
 * @category Compliant solution
 * 
 * @description This compliant solution reduces the accessibility of the
 *              constructor to private and immediately initializes the field
 *              Instance, allowing it to be declared final. Singleton
 *              constructors must be private.
 */
class MySingleton1 {

	private static final MySingleton1 Instance = new MySingleton1();

	private MySingleton1() {
		// private constructor prevents instantiation by untrusted callers
	}

	public static synchronized MySingleton1 getInstance() {
		return Instance;
	}
}
