package secure.coding.chapter08.sec.sec00.solution;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.security.AccessController;
import java.security.PrivilegedAction;

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
 * @description Both the previous noncompliant code example and the previous
 *              compliant solution throw a FileNotFoundException when the
 *              password file is missing. If the existence of the password file
 *              is itself considered sensitive information, this exception must
 *              also not be allowed to leak outside the trusted code.
 * 
 *              This compliant solution suppresses the exception, leaving the
 *              array to contain a single null value to indicate that the file
 *              does not exist. It uses the simpler PrivilegedAction class
 *              rather than PrivilegedExceptionAction to prevent exceptions from
 *              propagating out of the doPrivileged() block. The Void return
 *              type is recommended for privileged actions that do not return
 *              any value.
 */
public class PasswordManager2 {
	public static void changePassword() throws FileNotFoundException {
		FileInputStream fin = openPasswordFile();
		if (fin == null) {
			// no password file; handle error
		}
		// test old password with password in file contents; change password
	}

	private static FileInputStream openPasswordFile() throws FileNotFoundException {
		final String password_file = "password";
		final FileInputStream fin[] = { null };

		PrivilegedAction<Void> privilegedAction = new PrivilegedAction<Void>() {
			@Override
			public Void run() {
				try {
					// Sensitive action; can't be done outside
					// doPrivileged() block
					fin[0] = new FileInputStream(password_file);
				} catch (FileNotFoundException x) {
					// report to handler
				}
				return null;
			}
		};

		AccessController.doPrivileged(privilegedAction);
		return fin[0];
	}
}
