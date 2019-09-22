package secure.coding.chapter11.err.err09.solution;

/**
 * @rule ERR09-J. Do not allow untrusted code to terminate the JVM
 * 
 * @description Invocation of System.exit() terminates the Java Virtual Machine
 *              (JVM), consequently terminating all running programs and
 *              threads. This can result in denial of service (DoS) attacks. For
 *              example, a call to System.exit() that is embedded in Java Server
 *              Pages (JSP) code can cause a web server to terminate, preventing
 *              further service for users. Programs must prevent both
 *              inadvertent and malicious calls to System.exit(). Additionally,
 *              programs should perform necessary cleanup actions when forcibly
 *              terminated (for example, by using the Windows Task Manager,
 *              POSIX kill command, or other mechanisms).
 * 
 * @category Compliant Solution
 * 
 * @description This compliant solution installs a custom security manager
 *              PasswordSecurityManager that overrides the checkExit() method
 *              defined in the SecurityManager class. This override is required
 *              to enable invocation of cleanup code before allowing the exit.
 *              The default checkExit() method in the SecurityManager class
 *              lacks this facility.
 */

class PasswordSecurityManager extends SecurityManager {
	private boolean isExitAllowedFlag;

	public PasswordSecurityManager() {
		super();
		isExitAllowedFlag = false;
	}

	public boolean isExitAllowed() {
		return isExitAllowedFlag;
	}

	@Override
	public void checkExit(int status) {
		if (!isExitAllowed()) {
			throw new SecurityException();
		}
		super.checkExit(status);
	}

	public void setExitAllowed(boolean f) {
		isExitAllowedFlag = f;
	}
}

public class InterceptExit {

	public static void main(String[] args) {
		PasswordSecurityManager secManager = new PasswordSecurityManager();
		System.setSecurityManager(secManager);
		try {
			// ...
			System.exit(1); // Abrupt exit call
		} catch (Throwable x) {
			if (x instanceof SecurityException) {
				System.out.println("Intercepted System.exit()");
				// Log exception
			} else {
				// Forward to exception handler
			}
		}
		// ...
		secManager.setExitAllowed(true); // Permit exit
		// System.exit() will work subsequently
		// ...
	}
}