package secure.coding.chapter09.fio.fio03;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

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
 * @category Noncompliant code
 *
 * @description This noncompliant code example attempts to delete a specified
 *              file but gives no indication of its success. The Java Platform,
 *              Standard Edition 6 API Specification [ API 2006 ] requires
 *              File.delete() to throw a SecurityException only when the program
 *              lacks authorization to delete the file. No other exceptions are
 *              thrown, so the deletion can silently fail.
 */

public class TemproraryFiles2 {

	public static void main(String[] args) throws IOException {
		File f = new File("tempnam.tmp");
		if (f.exists()) {
			System.out.println("This file already exists");
			return;
		}
		FileOutputStream fop = null;
		try {
			fop = new FileOutputStream(f);
			String str = "Data";
			fop.write(str.getBytes());
		} finally {
			if (fop != null) {
				try {
					fop.close();
				} catch (IOException x) {
					// handle error
				}
			}
		}
	}
}