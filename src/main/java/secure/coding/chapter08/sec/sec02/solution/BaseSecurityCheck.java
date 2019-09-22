package secure.coding.chapter08.sec.sec02.solution;

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
 * @category Compliant solution (Copy)
 *
 * @description This compliant solution ensures that the java.io.File object can
 *              be trusted despite not being final. The solution creates a new
 *              File object using the standard constructor. This ensures that
 *              any methods invoked on the File object are the standard library
 *              methods and not overriding methods that have been provided by
 *              the attacker.
 */
public class BaseSecurityCheck {

	public RandomAccessFile openFile(final java.io.File f) {
		final java.io.File copy = new java.io.File(f.getPath());
		askUserPermission(copy.getPath());
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
}
