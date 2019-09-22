package secure.coding.chapter11.err.err09;

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
 * @category Noncompliant code
 * 
 * @description This noncompliant code example uses System.exit() to forcefully
 *              shutdown the JVM and terminate the running process. The program
 *              lacks a security manager; consequently, it lacks the capability
 *              to check whether the caller is permitted to invoke
 *              System.exit(). *
 */
public class InterceptExit {

	public static void main(String[] args) {
		// ...
		System.exit(1); // Abrupt exit
		System.out.println("This never executes");
	}
}