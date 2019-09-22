package secure.coding.chapter08.sec.sec02;

import java.io.FileNotFoundException;
import java.io.RandomAccessFile;
import java.security.AccessController;
import java.security.PrivilegedAction;

/**
 * @rule SEC02-J. Do not base security checks on untrusted sources
 * 
 * @description Security checks based on untrusted sources can be bypassed. Any
 *              untrusted object or argument must be defensively copied before a
 *              security check is performed. The copy operation must be a deep
 *              copy; the implementation of the clone() method may produce a
 *              shallow copy, which can still be compromised. In addition, the
 *              implementation of the clone() method can be provided by the
 *              attacker. See rule OBJ06-J for more information.
 * 
 * @category Noncompliant code
 *
 * @description This noncompliant code example describes a security
 *              vulnerability from the Java 1.5 java.io package. In this
 *              release, java.io.File is nonfinal, allowing an attacker to
 *              supply an untrusted argument constructed by extending the
 *              legitimate File class. In this manner, the getPath() method can
 *              be overridden so that the security check passes the first time
 *              it is called but the value changes the second time to refer to a
 *              sensitive file such as /etc/passwd. This is an example of a
 *              time-of-check, time-of-use (TOCTOU) vulnerability.
 *              
 */
public class BaseSecurityCheck {

	public RandomAccessFile openFile(final java.io.File f) {
		askUserPermission(f.getPath());
		// . ..
		return (RandomAccessFile) AccessController.doPrivileged(new PrivilegedAction<RandomAccessFile>() {

			public RandomAccessFile run() {
				try {
					return new RandomAccessFile("", f.getPath());
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				return null;
			}

		});
	}

	private void askUserPermission(String path) {
		System.out.println("Check user permission");
	}

	// The attacker could extend java.io.File as follows:
//	public class BadFile extends java.io.File {
//		private int count;
//
//		public String getPath() {
//			return (++count == 1) ? "/tmp/foo" : "/etc/passwd";
//		}
//	}
	// This vulnerability can be mitigated by declaring java.io.File final.
}
