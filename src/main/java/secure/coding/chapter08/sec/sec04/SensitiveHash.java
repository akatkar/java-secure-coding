package secure.coding.chapter08.sec.sec04;

import java.util.Hashtable;

/**
 * @rule SEC04-J. Protect sensitive operations with security manager checks
 * 
 * @description Sensitive operations must be protected by security manager
 *              checks.
 * 
 * @category Noncompliant code
 *
 * @description This noncompliant code example instantiates a Hashtable and
 *              defines a removeEntry() method to allow the removal of its
 *              entries. This method is considered sensitive, perhaps because
 *              the hash table contains sensitive information. However, the
 *              method is public and nonfinal, which leaves it exposed to
 *              malicious callers.
 */
public class SensitiveHash {

	Hashtable<Integer, String> ht = new Hashtable<>();

	public void removeEntry(Object key) {
		ht.remove(key);
	}
}
