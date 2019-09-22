package secure.coding.chapter11.err.err05;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * @rule ERR05-J. Do not let checked exceptions escape from a finally block
 * 
 * @description Methods invoked from within a finally block can throw an
 *              exception. Failure to catch and handle such exceptions results
 *              in the abrupt termination of the entire try block. This causes
 *              any exception thrown in the try block to be lost, preventing any
 *              possible recovery method from handling that specific problem.
 *              Additionally, the transfer of control associated with the
 *              exception may prevent execution of any expressions or statements
 *              that occur after the point in the finally block from which the
 *              exception is thrown. Consequently, programs must appropriately
 *              handle checked exceptions that are thrown from within a finally
 *              block.
 * 
 *              Allowing checked exceptions to escape a finally block also
 *              violates rule ERR04-J.
 *              
 * @category Noncompliant code
 * 
 * @description This noncompliant code example contains a finally block that
 *              closes the reader object. The programmer incorrectly assumes
 *              that the statements in the finally block cannot throw exceptions
 *              and consequently fails to appropriately handle any exception
 *              that may arise.
 * 
 *              The close() method can throw an IOException, which, if thrown,
 *              would prevent execution of any subsequent cleanup statements.
 *              The compiler correctly fails to diagnose this problem because
 *              any IOException would be caught by the outer catch block. Also,
 *              an exception thrown from the close() operation can mask any
 *              exception that gets thrown during execution of the Do operations
 *              block, preventing proper recovery.
 */
class Operation {

	public static void doOperation(String some_file) {
		// ... code to check or set character encoding ...
		try {
			BufferedReader reader = new BufferedReader(new FileReader(some_file));
			try {
				// Do operations
			} finally {
				reader.close();
				// ... Other cleanup code ...
			}
		} catch (IOException x) {
			// Forward to handler
		}
	}
}
