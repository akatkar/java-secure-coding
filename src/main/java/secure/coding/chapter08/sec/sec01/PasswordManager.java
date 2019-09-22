package secure.coding.chapter08.sec.sec01;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.security.AccessController;
import java.security.PrivilegedActionException;
import java.security.PrivilegedExceptionAction;

/**
 * @rule SEC01-J. Do not allow tainted variables in privileged blocks
 * 
 * @description Do not operate on unvalidated or untrusted data (also known as
 *              tainted data ) in a privileged block. An attacker can supply
 *              malicious input that could result in privilege escalation
 *              attacks. Appropriate mitigations include hard-coding values
 *              rather than accepting arguments (when appropriate) and
 *              validating or sanitizing data before performing privileged
 *              operations
 * 
 * @category Noncompliant code
 *
 * @description This noncompliant code example accepts a tainted path or file
 *              name as an argument. An attacker can access a protected file by
 *              supplying its path name as an argument to this method.
 */
public class PasswordManager {
	
	private void privilegedMethod(final String filename) throws FileNotFoundException {
		try {
			FileInputStream fis = (FileInputStream) AccessController.doPrivileged(new PrivilegedExceptionAction<FileInputStream>() {
				public FileInputStream run() throws FileNotFoundException {
					return new FileInputStream(filename);
				}
			});
			// do something with the file and then close it
		} catch (PrivilegedActionException e) {
			// forward to handler
		}
	}
}
