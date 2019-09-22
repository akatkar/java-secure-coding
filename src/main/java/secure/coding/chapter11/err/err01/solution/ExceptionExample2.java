package secure.coding.chapter11.err.err01.solution;

import java.io.FileInputStream;

/**
 * @rule ERR01-J. Do not allow exceptions to expose sensitive information
 * 
 * @description Failure to filter sensitive information when propagating
 *              exceptions often results in information leaks that can assist an
 *              attacker’s efforts to develop further exploits. An attacker may
 *              craft input arguments to expose internal structures and
 *              mechanisms of the application. Both the exception message text
 *              and the type of an exception can leak information. For example,
 *              the FileNotFoundException message reveals information about the
 *              file system layout, and the exception type reveals the absence
 *              of the requested file.
 * 
 * @category Compliant Solution (Restricted Input)
 * 
 * @description This compliant solution operates under the policy that only
 *              c:\homepath\file1 and c:\homepath\file2 are permitted to be
 *              opened by the user.
 * 
 *              It also catches Throwable, as permitted by exception ERR08-EX0.
 *              It uses the MyExceptionReporter class described in rule ERR00-J,
 *              which filters sensitive information from any resulting
 *              exceptions.
 */
class ExceptionExample2 {
	public static void main(String[] args) {
		FileInputStream fis = null;
		try {
			switch (Integer.valueOf(args[0])) {
			case 1:
				fis = new FileInputStream("c:\\homepath\\file1");
				break;
			case 2:
				fis = new FileInputStream("c:\\homepath\\file2");
				break;
			// ...
			default:
				System.out.println("Invalid option");
				break;
			}
		} catch (Throwable t) {
			//MyExceptionReporter.report(t); // Sanitize
		}
	}
}
