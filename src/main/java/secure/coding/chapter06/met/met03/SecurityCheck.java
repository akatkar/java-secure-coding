package secure.coding.chapter06.met.met03;

/**
 * @rule MET03-J. Methods that perform a security check must be declared private
 *       or final
 * 
 * @description Member methods of nonfinal classes that perform security checks
 *              can be compromised when a malicious subclass overrides the
 *              methods and omits the checks. Consequently, such methods must be
 *              declared private or final to prevent overriding.
 */
public class SecurityCheck {

	/**
	 * @category Noncompliant code
	 */
	public void _readSensitiveFile() {
		try {
			SecurityManager sm = System.getSecurityManager();
			if (sm != null) { // Check for permission to read file
				sm.checkRead("/temp/tempFile");
			}
			// Access the file
		} catch (SecurityException se) {
			// Log exception
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
		} catch (SecurityException se) {
			// Log exception
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
		} catch (SecurityException se) {
			// Log exception
		}
	}
}
