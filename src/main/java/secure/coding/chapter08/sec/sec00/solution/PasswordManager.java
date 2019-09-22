package secure.coding.chapter08.sec.sec00.solution;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.security.AccessController;
import java.security.PrivilegedActionException;
import java.security.PrivilegedExceptionAction;

/**
 * @rule SEC00-J. Do not allow privileged blocks to leak sensitive information
 *       across a trust boundary
 * 
 * @description The java.security.AccessController class is part of Java’s
 *              security mechanism; it is responsible for enforcing the
 *              applicable security policy. This class’s static doPrivileged()
 *              method executes a code block with a relaxed security policy. The
 *              doPrivileged() method stops permissions from being checked
 *              further down the call chain. Consequently, any method that
 *              invokes doPrivileged() must assume responsibility for enforcing
 *              its own security on the code block supplied to doPrivileged().
 *              Likewise, code in the doPrivileged() method must not leak
 *              sensitive information or capabilities.
 * 
 *              For example, suppose that a web application must maintain a
 *              sensitive password file for a web service and also run untrusted
 *              code. The application could then enforce a security policy
 *              preventing the majority of its own code—as well as all untrusted
 *              code—from accessing the sensitive file. Because it must also
 *              provide mechanisms for adding and changing passwords, it can
 *              call the doPrivileged() method to temporarily allow untrusted
 *              code to access the sensitive file for the purpose of managing
 *              passwords. In this case, any privileged block must prevent any
 *              information about passwords from being accessible to untrusted
 *              code.
 * 
 * @category Compliant solution
 *
 * @description This compliant solution mitigates the vulnerability by declaring
 *              openPasswordFile() to be private. Consequently, an untrusted
 *              caller can call changePassword() but cannot directly invoke the
 *              openPasswordFile() method.
 */
public class PasswordManager {
	public static void changePassword() throws FileNotFoundException {
		FileInputStream fin = openPasswordFile();
		// test old password with password in file contents; change password
		// then close the password file
	}

	private static FileInputStream openPasswordFile() throws FileNotFoundException {
		final String password_file = "password";
		FileInputStream fin = null;
		try {
			fin = AccessController.doPrivileged(new PrivilegedExceptionAction<FileInputStream>() {
				public FileInputStream run() throws FileNotFoundException {
					// Sensitive action; can't be done outside privileged block
					FileInputStream in = new FileInputStream(password_file);
					return in;
				}
			});
		} catch (PrivilegedActionException x) {
			Exception cause = x.getException();
			if (cause instanceof FileNotFoundException) {
				throw (FileNotFoundException) cause;
			} else {
				throw new Error("Unexpected exception type", cause);
			}
		}
		return fin;
	}
}
