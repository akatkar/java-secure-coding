package secure.coding.chapter09.fio.fio13;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.logging.Logger;

/**
 * @rule FIO13-J. Do not log sensitive information outside a trust boundary
 * 
 * @description Logging is essential for debugging, incident response, and
 *              collecting forensic evidence. Nevertheless, logging sensitive
 *              data raises many concerns, including the privacy of the
 *              stakeholders, limitations imposed by the law on the collection
 *              of personal information, and the potential for data exposure by
 *              insiders. Sensitive information includes, but is not limited to,
 *              IP addresses, user names and passwords, email addresses, credit
 *              card numbers, and any personally identifiable information such
 *              as social security numbers. Many countries prohibit or restrict
 *              collection of personal data; others permit retention of personal
 *              data only when held in an anonymized form. Consequently, logs
 *              must not contain sensitive data, particularly when prohibited by
 *              law.
 */
public final class LogData {
	
	private static final Logger LOGGER = Logger.getLogger(LogData.class.getName());
	
	/**
	 * @category Noncompliant code
	 *
	 * @description In this noncompliant code example, a server logs the IP address
	 *              of the remote client in the event of a security exception. This
	 *              data can be misused, for example, to build a profile of a user’s
	 *              browsing habits. Such logging may violate legal restrictions in
	 *              many countries.
	 * 
	 *              When the log cannot contain IP addresses, it should not contain
	 *              any information about a SecurityException, because it might leak
	 *              an IP address. When an exception contains sensitive information,
	 *              the custom MyExceptionReporter class should extract or cleanse
	 *              it before returning control to the next statement in the catch
	 *              block (see rule ERR00-J).
	 */
	public void logRemoteIPAddress(String name) {

		InetAddress machine = null;
		try {
			machine = InetAddress.getByName(name);
		} catch (UnknownHostException e) {
			// Exception e = MyExceptionReporter.handle(e);
		} catch (SecurityException e) {
			// Exception e = MyExceptionReporter.handle(e);
			LOGGER.severe(name + "," + machine.getHostAddress() + "," + e.toString());
		}
	}

	/**
	 * @category Compliant solution (ByteBuffer)
	 *
	 * @description This compliant solution does not log security exceptions except
	 *              for the logging implicitly performed by MyExceptionReporter.
	 */
	public void logRemoteIPAddress2(String name) {

		InetAddress machine = null;
		try {
			machine = InetAddress.getByName(name);
		} catch (UnknownHostException e) {
			// Exception e = MyExceptionReporter.handle(e);
		} catch (SecurityException e) {
			// Exception e = MyExceptionReporter.handle(e);
		}
	}
}
