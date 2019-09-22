package secure.coding.chapter11.err.err05.solution;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * @rule ERR05-J. Do not let checked exceptions escape from a finally block
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
 * @category Compliant Solution (Java 1.7: try-with-resources)
 * 
 * @description Java 1.7 introduced a new feature, called try-with-resources,
 *              that can close certain resources automatically in the event of
 *              an error. This compliant solution uses try-with-resources to
 *              properly close the file.
 */
class Operation2 {

	public static void doOperation(String some_file) {
		// ... code to check or set character encoding ...
		try ( // try-with-resources
				BufferedReader reader = new BufferedReader(new FileReader(some_file))) {
			// Do operations
		} catch (IOException ex) {
			System.err.println("thrown exception: " + ex.toString());
			Throwable[] suppressed = ex.getSuppressed();
			for (int i = 0; i < suppressed.length; i++) {
				System.err.println("suppressed exception: " + suppressed[i].toString());
			}
			// Forward to handler
		}
	}

	public static void main(String[] args) {
		if (args.length < 1) {
			System.out.println("Please supply a path as an argument");
			return;
		}
		doOperation(args[0]);
	}
}