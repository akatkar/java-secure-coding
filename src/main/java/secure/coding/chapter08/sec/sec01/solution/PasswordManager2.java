package secure.coding.chapter08.sec.sec01.solution;

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
 * @category Compliant Solution (Built-in File Name and Path)
 *
 * @description This compliant solution both explicitly hard-codes the name of
 *              the file and confines the variables used in the privileged block
 *              to the same method. This ensures that no malicious file can be
 *              loaded by exploiting the privileged method.
 */
public class PasswordManager2 {

	static final String FILEPATH = "/path/to/protected/file/fn.ext";

	private void privilegedMethod() throws FileNotFoundException {
		try {
			FileInputStream fis = (FileInputStream) AccessController.doPrivileged(new PrivilegedExceptionAction<FileInputStream>() {
				public FileInputStream run() throws FileNotFoundException {
					return new FileInputStream(FILEPATH);
				}
			});
			// do something with the file and then close it
		} catch (PrivilegedActionException e) {
			// forward to handler and log
		}
	}
}
