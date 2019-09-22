package secure.coding.chapter11.err.err01.solution;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * @rule ERR01-J. Do not allow exceptions to expose sensitive information
 * 
 * @description Failure to filter sensitive information when propagating
 *              exceptions often results in information leaks that can assist an
 *              attacker’s efforts to develop further exploits. An attacker may
 *              craft input arguments to expose internal structures and
 *              mechanisms of the application. Both the exception message text
 *              and the type of an exception can leak information. For example,
 *              the FileNotFoundException message reveals information about the
 *              file system layout, and the exception type reveals the absence
 *              of the requested file.
 * 
 * @category Compliant Solution (Security Policy)
 * 
 * @description This compliant solution implements the policy that only files
 *              that live in c:\homepath may be opened by the user and that the
 *              user is not allowed to discover anything about files outside
 *              this directory. The solution issues a terse error message when
 *              the file cannot be opened or the file does not live in the
 *              proper directory. Any information about files outside
 *              c:\homepath is concealed.
 * 
 *              The compliant solution also uses the File.getCanonicalFile()
 *              method to canonicalize the file to simplify subsequent path name
 *              comparisons
 */
class ExceptionExample1 {
	public static void main(String[] args) {
		File file = null;
		try {
			file = new File(System.getenv("APPDATA") + args[0]).getCanonicalFile();
			if (!file.getPath().startsWith("c:\\homepath")) {
				System.out.println("Invalid file");
				return;
			}
		} catch (IOException x) {
			System.out.println("Invalid file");
			return;
		}
		try {
			FileInputStream fis = new FileInputStream(file);
		} catch (FileNotFoundException x) {
			System.out.println("Invalid file");
			return;
		}
	}
}
