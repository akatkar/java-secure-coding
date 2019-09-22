package secure.coding.chapter05.obj.obj05;

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
 * @category Noncompliant code
 */
public final class ReturnRef {
	// Internal state, may contain sensitive data
	private Hashtable<Integer, String> ht = new Hashtable<Integer, String>();

	private ReturnRef() {
		ht.put(1, "123-45-6666");
	}

	public Hashtable<Integer, String> getValues() {
		return ht;
	}

	public static void main(String[] args) {
		ReturnRef rr = new ReturnRef();
		// Prints sensitive data 123-45-6666
		Hashtable<Integer, String> ht1 = rr.getValues();
		System.out.println(ht1);
		
		// Untrusted caller can remove entries
		ht1.remove(1);
		// Now prints null, original entry is removed
		Hashtable<Integer, String> ht2 = rr.getValues();
		System.out.println(ht2);
	}
}
