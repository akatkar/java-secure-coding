package secure.coding.chapter03.exp.exp00.solution;

import java.io.File;

/**
 * @rule EXP00-J. Do not ignore values returned by methods
 * 
 * @description Methods can return values to communicate failure or success or
 *              to update local objects or fields. Security risks can arise when
 *              method return values are ignored or when the invoking method
 *              fails to take suitable action. Consequently, programs must not
 *              ignore method return values. When getter methods are named after
 *              an action, a programmer could fail to realize that a return
 *              value is expected.
 * 
 * @example the only purpose of the ProcessBuilder. redirectErrorStream() method
 *          is to report via return value whether the process builder
 *          successfully merged standard error and standard output. The method
 *          that actually performs redirection of the error stream is the
 *          overloaded single-argument method ProcessBuilder.
 *          redirectErrorStream(boolean).
 */
public class DeleteFile {

	/**
	 * @category Compliant solution
	 */
	public void deleteFile() {
		File someFile = new File("someFileName.txt");
		// do something with someFile
		if (!someFile.delete()) {
			// handle failure to delete the f ile
		}
	}

	/**
	 * @category Compliant solution
	 */
	public void replace() {
		String original = "insecure";
		original = original.replace('i', '9');
		System.out.println(original);
	}
	
	public static void main(String[] args) {
		new DeleteFile().replace();
	}
}
