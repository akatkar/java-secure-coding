package secure.coding.chapter11.err.err00.solution;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * @rule ERR00-J. Do not suppress or ignore checked exceptions
 * 
 * @description Programmers often suppress checked exceptions by catching
 *              exceptions with an empty or trivial catch block. Each catch
 *              block must ensure that the program continues only with valid
 *              invariants. Consequently, the catch block must either recover
 *              from the exceptional condition, rethrow the exception to allow
 *              the next nearest enclosing catch clause of a try statement to
 *              recover, or throw an exception that is appropriate to the
 *              context of the catch block.
 * 
 *              Exceptions disrupt the expected control flow of the application.
 *              For example, no part of any expression or statement that occurs
 *              in the try block after the point from which the exception is
 *              thrown is evaluated. Consequently, exceptions must be handled
 *              appropriately. Many reasons for suppressing exceptions are
 *              invalid. For example, when the client cannot be expected to
 *              recover from the underlying problem, it is good practice to
 *              allow the exception to propagate outwards rather than to catch
 *              and suppress the exception.
 * 
 * @category Compliant Solution (Interactive)
 * 
 * @description This compliant solution handles a FileNotFoundException by
 *              requesting that the user specify another file name.
 */
class HandleException {

	volatile static boolean validFlag = false;

	public static void main(String[] args) {

		do {
			try {
				// If requested file does not exist, throws FileNotFoundException
				// If requested file exists, sets validFlag to true
				try (FileInputStream fis = new FileInputStream(new File("some.txt"))) {
					validFlag = true;
				}
				
			} catch (FileNotFoundException e) {
				// Ask the user for a different file name
			} catch (IOException e) {
				// Ask the user for a different file name
			}
		} while (validFlag != true);
		// Use the file

	}
}
