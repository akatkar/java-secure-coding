package secure.coding.chapter09.fio.fio03.solution;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

/**
 * @rule FIO03-J. Remove temporary files before termination
 * 
 * 
 * @description Temporary files can be used to
 * 
 *              - share data between processes.
 * 
 *              - store auxiliary program data (for example, to preserve
 *              memory).
 * 
 *              - construct and/or load classes, JAR files, and native libraries
 *              dynamically.
 * 
 * @category Compliant solution (Java SE 7: delete_on_close)
 *
 * @description This compliant solution creates a temporary file using several
 *              methods from Java SE 7’s NIO2 package. It uses the
 *              createTempFile() method, which creates an unpredictable name.
 * 
 * @param filename
 */
public class TemproraryFiles3 {

	public static void main(String[] args) {
		Path tempFile = null;
		try {
			tempFile = Files.createTempFile("tempnam", ".tmp");
			try (BufferedWriter writer = Files.newBufferedWriter(tempFile, Charset.forName("UTF8"),
					StandardOpenOption.DELETE_ON_CLOSE)) {
				// write to file
			}
			System.out.println("Temporary file write done, file erased");
		} catch (FileAlreadyExistsException x) {
			System.err.println("File exists: " + tempFile);
		} catch (IOException x) {
			// Some other sort of failure, such as permissions.
			System.err.println("Error creating temporary file: " + x);
		}
	}
}