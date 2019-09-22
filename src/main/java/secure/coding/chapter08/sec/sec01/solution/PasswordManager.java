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
 * @category Compliant Solution (Input Validation)
 *
 * @description This compliant solution invokes the cleanAFilenameAndPath()
 *              method to sanitize malicious inputs. Successful completion of
 *              the sanitization method indicates that the input is acceptable
 *              and the doPrivileged() block can be executed.
 */
public class PasswordManager {

	private void privilegedMethod(final String filename) throws FileNotFoundException {
		final String cleanFilename;
		try {
			cleanFilename = cleanAFilenameAndPath(filename);
		} catch (Exception e /* exception as per spec of cleanAFileNameAndPath */) {
			// log or forward to handler as appropriate based on specification
			// of cleanAFilenameAndPath
			throw e;
		}
		try {
			FileInputStream fis = (FileInputStream) AccessController.doPrivileged(new PrivilegedExceptionAction<FileInputStream>() {
				public FileInputStream run() throws FileNotFoundException {
					return new FileInputStream(cleanFilename);
				}
			});
			// do something with the file and then close it
		} catch (PrivilegedActionException e) {
			// forward to handler
		}
	}

	private String cleanAFilenameAndPath(String filename) {
		System.out.println("input validation performed");
		return filename;
	}
}
