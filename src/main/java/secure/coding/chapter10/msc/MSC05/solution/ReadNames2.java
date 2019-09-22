package secure.coding.chapter10.msc.MSC05.solution;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Vector;

/**
 * @rule MSC05-J. Do not exhaust heap space
 * 
 * @descriptionA Java OutofMemoryError occurs when the program attempts to use
 *               more heap space than is available. Among other causes, this
 *               error may result from - a memory leak (see rule MSC04-J).
 * 
 *               - an infinite loop
 * 
 *               - limited amounts of default heap memory available.
 * 
 *               - incorrect implementation of common data structures (hash
 *               tables, vectors, and so on).
 * 
 *               - unbounded deserialization.
 * 
 *               - writing a large number of objects to an ObjectOutputStream
 *               (see rule SER10-J).
 * 
 *               - creating a large number of threads.
 * 
 *               - uncompressing a file (see rule IDS04-J). Some of these causes
 *               are platform-dependent and difficult to anticipate. Others are
 *               fairly easy to anticipate, such as reading data from a file. As
 *               a result, programs must not accept untrusted input in a manner
 *               that can cause the program to exhaust memory.
 * 
 * @category Compliant solution (Limited Length Input)
 * 
 * @description This compliant solution imposes limits both on the length of
 *              each line and on the total number of items to add to the vector.
 */
class ReadNames2 {

	public static final int FILE_SIZE_LIMIT = 1_000_000;

	private Vector<String> names = new Vector<String>();
	private final InputStreamReader input;
	private final BufferedReader reader;

	public static String readLimitedLine(Reader reader, int limit) throws IOException {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < limit; i++) {
			int c = reader.read();
			if (c == -1) {
				return null;
			}
			if (((char) c == '\n') || ((char) c == '\r')) {
				break;
			}
			sb.append((char) c);
		}
		return sb.toString();
	}

	public static final int MAX_LINE_LENGTH = 1024;
	public static final int MAX_LINE_COUNT = 1_000_000;

	public ReadNames2(String filename) throws IOException {
		if (Files.size(Paths.get(filename)) > FILE_SIZE_LIMIT) {
			throw new IOException("File too large");
		}
		this.input = new FileReader(filename);
		this.reader = new BufferedReader(input);
	}

	public void addNames() throws IOException {
		try {
			String newName;
			for (int i = 0; i < MAX_LINE_COUNT; i++) {
				newName = readLimitedLine(reader, MAX_LINE_LENGTH);
				if (newName == null || newName.equalsIgnoreCase("quit")) {
					break;
				}
				names.addElement(newName);
				System.out.println("adding " + newName);
			}
		} finally {
			input.close();
		}
	}

	public static void main(String[] args) throws IOException {
		if (args.length != 1) {
			System.out.println("Arguments: [filename]");
			return;
		}
		ReadNames2 demo = new ReadNames2(args[0]);
		demo.addNames();
	}
}

/**
 * @solution The OutOfMemoryError can be avoided by ensuring the absence of
 *           infinite loops, memory leaks, and unnecessary object retention.
 *           When memory requirements are known ahead of time, the heap size can
 *           be tailored to fit the requirements using the following runtime
 *           parameters [ Java 2006 ]:
 * 
 *           java -Xms<initial heap size> -Xmx<maximum heap size>
 * 
 *           For example, java -Xms128m -Xmx512m ShowHeapError
 * 
 *           Here the initial heap size is set to 128MB and the maximum heap
 *           size to 512MB. These settings can be changed either using the Java
 *           Control Panel or from the command line. They cannot be adjusted
 *           through the application itself.
 */