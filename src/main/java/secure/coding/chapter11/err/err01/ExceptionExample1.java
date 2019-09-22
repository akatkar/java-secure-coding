package secure.coding.chapter11.err.err01;

import java.io.FileInputStream;
import java.io.IOException;

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
 * @category Noncompliant code (Leaks from Exception Message and Type)
 * 
 * @description In this noncompliant code example, the program must read a file
 *              supplied by the user, but the contents and layout of the file
 *              system are sensitive. The program accepts a file name as an
 *              input argument but fails to prevent any resulting exceptions
 *              from being presented to the user.
 */
class ExceptionExample1 {

	public static void main(String[] args) throws IOException {
		// Linux stores a user's home directory path in
		// the environment variable $HOME, Windows in %APPDATA%
		try(FileInputStream fis = new FileInputStream(System.getenv("APPDATA") + args[0])){
			
		}
	}
}
