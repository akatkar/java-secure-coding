package secure.coding.chapter10.msc.MSC07;

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
 * @category Noncompliant code
 * 
 * @description This noncompliant code example uses a nonprivate constructor for
 *              instantiating a singleton.
 */
class MySingleton1 {

	private static MySingleton1 Instance;

	protected MySingleton1() {
		Instance = new MySingleton1();
	}

	public static synchronized MySingleton1 getInstance() {
		return Instance;
	}
}
