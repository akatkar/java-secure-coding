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
 * @category Noncompliant code (Sanitized Exception)
 * 
 * @description This noncompliant code example logs the exception and throws a
 *              custom exception that does not wrap the FileNotFoundException.
 * 
 *              While this exception is less likely than the previous
 *              noncompliant code examples to leak useful information, it still
 *              reveals that the specified file cannot be read. More
 *              specifically, the program reacts differently to nonexistent file
 *              paths than it does to valid ones, and an attacker can still
 *              infer sensitive information about the file system from this
 *              program’s behavior. Failure to restrict user input leaves the
 *              system vulnerable to a brute-force attack in which the attacker
 *              discovers valid file names by issuing queries that collectively
 *              cover the space of possible file names. File names that cause
 *              the program to return the sanitized exception indicate
 *              nonexistent files, while file names that do not return
 *              exceptions reveal existing files.
 */
class ExceptionExample3 {

	public static void main(String[] args) throws IOException {
		try {
			try (FileInputStream fis = new FileInputStream(System.getenv("APPDATA") + args[0])) {

			}
		} catch (FileNotFoundException e) {
			// Log the exception
			throw new SecurityIOException();
		}
	}
}

class SecurityIOException extends IOException {
	/* ... */};
