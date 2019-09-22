package secure.coding.chapter11.err.err01;

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
 * @category Noncompliant code (Wrapping and Rethrowing Sensitive Exception)
 * 
 * @description This noncompliant code example logs the exception and then wraps
 *              it in a more general exception before rethrowing it.
 */
class ExceptionExample2 {

	public static void main(String[] args) throws IOException {
		try {
			try (FileInputStream fis = new FileInputStream(System.getenv("APPDATA") + args[0])) {

			}
		} catch (FileNotFoundException e) {
			// Log the exception
			throw new IOException("Unable to retrieve file", e);
		}

	}
}
