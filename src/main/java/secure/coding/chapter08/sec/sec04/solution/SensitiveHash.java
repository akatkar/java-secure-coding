package secure.coding.chapter08.sec.sec04.solution;

import java.util.Hashtable;

/**
 * @rule SEC04-J. Protect sensitive operations with security manager checks
 * 
 * @description Sensitive operations must be protected by security manager
 *              checks.
 * 
 * @category Compliant solution
 *
 * @description This compliant solution installs a security check to protect
 *              entries from being maliciously removed from the Hashtable
 *              instance. A SecurityException is thrown if the caller lacks the
 *              java.security.SecurityPermission removeKeyPermission.
 */
public class SensitiveHash {

	Hashtable<Integer, String> ht = new Hashtable<Integer, String>();

	void removeEntry(Object key) {
		check("removeKeyPermission");
		ht.remove(key);
	}

	/**
	 * @description The SecurityManager.checkSecurityAccess() method determines
	 *              whether the action controlled by the particular permission is
	 *              allowed or not.
	 */
	private void check(String directive) {
		SecurityManager sm = System.getSecurityManager();
		if (sm != null) {
			sm.checkSecurityAccess(directive);
		}
	}
}
