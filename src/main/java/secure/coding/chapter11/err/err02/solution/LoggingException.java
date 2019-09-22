package secure.coding.chapter11.err.err02.solution;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @rule ERR02-J. Prevent exceptions while logging data
 * 
 * @description Exceptions that are thrown while logging is in progress can
 *              prevent successful logging unless special care is taken. Failure
 *              to account for exceptions during the logging process can cause
 *              security vulnerabilities, such as allowing an attacker to
 *              conceal critical security exceptions by preventing them from
 *              being logged. Consequently, programs must ensure that data
 *              logging continues to operate correctly even when exceptions are
 *              thrown during the logging process.
 * 
 * @category Compliant Solution
 * 
 * @description This compliant solution uses java.util.logging.Logger, the
 *              default logging API provided by JDK 1.4 and later. Use of other
 *              compliant logging mechanisms, such as log4j, is also permitted.
 */
class LoggingException {
	
	private static final Logger logger = null;

	public static void main(String[] args) throws IOException {
		try {
			// ...
		} catch (SecurityException se) {
			logger.log(Level.SEVERE, se.getMessage());
			// Recover from exception
		}
	}
}
