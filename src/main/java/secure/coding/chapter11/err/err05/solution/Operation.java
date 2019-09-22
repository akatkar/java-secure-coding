package secure.coding.chapter11.err.err05.solution;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * @rule ERR04-J. Do not exit abruptly from a finally block
 * 
 * @description Never use return, break, continue, or throw statements within a
 *              finally block. When program execution enters a try block that
 *              has a finally block, the finally block always executes,
 *              regardless of whether the try block (or any associated catch
 *              blocks) executes to completion. Statements that cause the
 *              finally block to terminate abruptly also cause the try block to
 *              terminate abruptly and consequently suppress any exception
 *              thrown from the try or catch blocks
 * 
 * @category Compliant Solution (Handle Exceptions in finally Block)
 * 
 * @description This compliant solution encloses the close() method invocation
 *              in a try-catch block of its own within the finally block.
 *              Consequently, the potential IOException can be handled without
 *              allowing it to propagate further.
 */
class Operation {

	public static void doOperation(String some_file) {
		// ... code to check or set character encoding ...
		try {
			BufferedReader reader = new BufferedReader(new FileReader(some_file));
			try {
				// Do operations
			} finally {
				try {
					reader.close();
				} catch (IOException ie) {
					// Forward to handler
				}
				// ... Other clean-up code ...
			}
		} catch (IOException x) {
			// Forward to handler
		}
	}
}
