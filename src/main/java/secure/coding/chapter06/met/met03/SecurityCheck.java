package secure.coding.chapter06.met.met03;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @rule MET03-J. Methods that perform a security check must be declared private
 *       or final
 * 
 * @description Member methods of nonfinal classes that perform security checks
 *              can be compromised when a malicious subclass overrides the
 *              methods and omits the checks. Consequently, such methods must be
 *              declared private or final to prevent overriding.
 * 
 *              A security manager is an object that defines a security policy
 *              for an application. This policy specifies actions that are
 *              unsafe or sensitive. Any actions not allowed by the security
 *              policy cause a SecurityException to be thrown. An application
 *              can also query its security manager to discover which actions
 *              are allowed.
 * 
 *              Typically, a web applet runs with a security manager provided by
 *              the browser or Java Web Start plugin. Other kinds of
 *              applications normally run without a security manager, unless the
 *              application itself defines one. If no security manager is
 *              present, the application has no security policy and acts without
 *              restrictions.
 *              
 *              java -Djava.security.manager -Djava.security.policy=/path/to/other.policy
 */
public class SecurityCheck {

	private static final Logger LOGGER = LoggerFactory.getLogger(SecurityCheck.class);

	/**
	 * @category Noncompliant code
	 */
	public void _readSensitiveFile() {
		try {
			SecurityManager sm = System.getSecurityManager();
			if (sm != null) { // Check for permission to read file
				System.out.println("deneme");
				sm.checkRead("/temp/tempFile");
			}
			// Access the file
			LOGGER.info("Accessed the file");
		} catch (SecurityException se) {
			// Log exception
			LOGGER.error(se.getMessage());
		}
	}

	/**
	 * @category Compliant solution
	 * 
	 * @description This compliant solution prevents overriding of the
	 *              readSensitiveFile() method by declaring it final.
	 */
	public final void readSensitiveFile() {
		try {
			SecurityManager sm = System.getSecurityManager();
			if (sm != null) { // Check for permission to read file
				sm.checkRead("/temp/tempFile");
			}
			// Access the file
			LOGGER.info("Accessed the file");
		} catch (SecurityException se) {
			// Log exception
			LOGGER.error(se.getMessage());
		}
	}

	/**
	 * @category Compliant solution
	 * 
	 * @description This compliant solution prevents overriding of the
	 *              readSensitiveFile() method by declaring it private.
	 */
	private final void readSensitiveFile2() {
		try {
			SecurityManager sm = System.getSecurityManager();
			if (sm != null) { // Check for permission to read file
				sm.checkRead("/temp/tempFile");
			}
			// Access the file
			LOGGER.info("Accessed the file");
		} catch (SecurityException se) {
			// Log exception
			LOGGER.error(se.getMessage());
		}
	}

	public static void main(String[] args) {
		SecurityCheck sc = new SecurityCheck();

		sc._readSensitiveFile();
		sc.readSensitiveFile();
		sc.readSensitiveFile2();
	}
}
