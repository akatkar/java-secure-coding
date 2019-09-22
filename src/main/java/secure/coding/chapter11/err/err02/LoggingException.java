package secure.coding.chapter11.err.err02;

import java.io.IOException;

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
 * @category Noncompliant code (Leaks from Exception Message and Type)
 * 
 * @description This noncompliant code example writes a critical security
 *              exception to the standard error stream.
 * 
 *              Writing such exceptions to the standard error stream is
 *              inadequate for logging purposes. First, the standard error
 *              stream may be exhausted or closed, preventing recording of
 *              subsequent exceptions. Second, the trust level of the standard
 *              error stream may be insufficient for recording certain
 *              security-critical exceptions or errors without leaking sensitive
 *              information. If an I/O error were to occur while writing the
 *              security exception, the catch block would throw an IOException
 *              and the critical security exception would be lost. Finally, an
 *              attacker may disguise the exception so that it occurs with
 *              several other innocuous exceptions.
 * 
 *              Similarly, using Console.printf(), System.out.print*(), or
 *              Throwable.printStackTrace() to output a security exception also
 *              constitutes a violation of this rule.
 */
class LoggingException {

	public static void main(String[] args) throws IOException {
		try {
			// ...
		} catch (SecurityException se) {
			System.err.println(se);
			// Recover from exception
		}
	}
}
