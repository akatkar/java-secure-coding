package secure.coding.chapter05.obj.obj05.solution;

import java.util.Hashtable;

/**
 * @rule OBJ05-J. Defensively copy private mutable class members before
 *       returning their references
 *
 * 
 * @description Returning references to internal mutable members of a class can
 *              compromise an application’s security both by breaking
 *              encapsulation and by providing the opportunity to corrupt the
 *              internal state of the class (whether accidentally or
 *              maliciously). As a result, programs must not return references
 *              to internal mutable classes. Returning a reference to a
 *              defensive copy of mutable internal state ensures that the caller
 *              cannot modify the original internal state, although the copy
 *              remains mutable.
 *
 * @category Compliant solution with shallow copy
 */
public final class ReturnRef {
	// Internal state, may contain sensitive data
	private Hashtable<Integer, String> ht = new Hashtable<>();

	private ReturnRef() {
		ht.put(1, "123-45-6666");
	}

	/**
	 * @description For mutable fields that contain immutable data, a shallow copy
	 *              is sufficient. Fields that refer to mutable data generally
	 *              require a deep copy.
	 */
	@SuppressWarnings("unchecked")
	private Hashtable<Integer, String> getValues() {
		// shallow copy
		return (Hashtable<Integer, String>) ht.clone(); 
	}

	public static void main(String[] args) {
		ReturnRef rr = new ReturnRef();
		// Prints non-sensitive data
		Hashtable<Integer, String> ht1 = rr.getValues();
		System.out.println(ht1);
		
		// Untrusted caller can only modify copy
		ht1.remove(1);
		// Prints non-sensitive data
		Hashtable<Integer, String> ht2 = rr.getValues();
		System.out.println(ht2);
	}
}
