package secure.coding.chapter09.fio.fio04;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * @rule FIO04-J. Close resources when they are no longer needed
 * 
 * 
 * @description The Java garbage collector is called to free unreferenced but
 *              as-yet unreleased memory. However, the garbage collector cannot
 *              free nonmemory resources such as open file descriptors and
 *              database connections.
 * 
 *              Consequently, failing to release such resources can lead to
 *              resource exhaustion attacks. In addition, programs can
 *              experience resource starvation while waiting for finalize() to
 *              release resources such as Lock or Semaphore objects. This can
 *              occur because Java lacks any temporal guarantee of when
 *              finalize() methods execute, other than “sometime before program
 *              termination.” Finally, output streams may cache object
 *              references; such cached objects are not garbage-collected until
 *              after the output stream is closed. Consequently, output streams
 *              should be closed promptly after use.
 * 
 */

public class SqlClosing {
	/**
	 * @category Noncompliant code
	 *
	 * @description This noncompliant code example opens a file and uses it but
	 *              fails to explicitly close the file.
	 * 
	 * @param filename
	 * @throws IOException,
	 *             FileNotFoundException
	 */
	public int processFile1(String fileName) throws IOException {
		FileInputStream stream = new FileInputStream(fileName);
		BufferedReader bufRead = new BufferedReader(new InputStreamReader(stream));
		String line;
		while ((line = bufRead.readLine()) != null) {
			// sendLine(line);
		}
		return 1;
	}

	/**
	 * @category Compliant solution
	 *
	 * @description This compliant solution releases all acquired resources,
	 *              regardless of any exceptions that might occur. Even though
	 *              dereferencing bufRead might result in an exception, the
	 *              FileInputStream object is closed as required.
	 * 
	 * @param filename
	 * @throws IOException
	 */
	public int processFile2(String fileName) {
		try {
			final FileInputStream stream = new FileInputStream(fileName);
			try {
				final BufferedReader bufRead = new BufferedReader(new InputStreamReader(stream));
				String line;
				while ((line = bufRead.readLine()) != null) {
					// sendLine(line);
				}
			} finally {
				if (stream != null) {
					try {
						stream.close();
					} catch (IOException e) {
						// forward to handler
					}
				}
			}
		} catch (IOException e) {
			// forward to handler
		}
		return 1;
	}

	/**
	 * @category Compliant solution
	 *
	 * @description This compliant solution uses the try-with-resources statement,
	 *              introduced in Java SE 7, to release all acquired resources,
	 *              regardless of any exceptions that might occur.
	 * 
	 * @param filename
	 * @throws IOException
	 */
	public int processFile3(String fileName) {
		try (FileInputStream stream = new FileInputStream(fileName);
				BufferedReader bufRead = new BufferedReader(new InputStreamReader(stream))) {
			String line;
			while ((line = bufRead.readLine()) != null) {
				// sendLine(line);
			}
		} catch (IOException e) {
			// forward to handler
		}
		return 1;
	}
}