package secure.coding.chapter09.fio.fio02;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * @rule FIO02-J. Detect and handle file-related errors
 * 
 * 
 * @description programs that ignore the return values from file operations
 *              often fail to detect that those operations have failed. Java
 *              programs must check the return values of methods that perform
 *              file I/O (this is a specific instance of rule EXP00-J).
 * 
 */

public class FileErrorCheck {
	/**
	 * @category Noncompliant code
	 *
	 * @description This noncompliant code example attempts to delete a specified
	 *              file but gives no indication of its success. The Java Platform,
	 *              Standard Edition 6 API Specification [ API 2006 ] requires
	 *              File.delete() to throw a SecurityException only when the program
	 *              lacks authorization to delete the file. No other exceptions are
	 *              thrown, so the deletion can silently fail.
	 * 
	 * @param filename
	 * @throws IOException
	 */
	public void example1(String filename) throws IOException {
		File file = new File(filename);
		file.delete();
	}

	/**
	 * @category Compliant solution
	 *
	 * @description This compliant solution checks the return value of delete().
	 * 
	 * @param filename
	 * @throws IOException
	 * 
	 */
	public static void example2(String filename) throws IOException {
		File file = new File(filename);
		if (!file.delete()) {
			System.out.println("Deletion failed");
		}
	}

	/**
	 * @category Compliant solution (Java SE 7)
	 *
	 * @description This compliant solution uses the java.nio.file.Files.delete()
	 *              method from Java SE 7 to delete the file.
	 * 
	 * @param filename
	 * @throws IOException
	 * 
	 */
	public static void example3(String filename) throws IOException {
		Path file = new File(filename).toPath();
		try {
			Files.delete(file);
		} catch (IOException x) {
			System.out.println("Deletion failed");
			// handle error
		}
	}
}