package secure.coding.chapter10.msc.MSC05.solution;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
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
 * @category Compliant solution (Limited File Size)
 * 
 * @description This compliant solution imposes a limit on the size of the file
 *              being read. This is accomplished with the Files.size() method,
 *              If the file is within the limit, we can assume the standard
 *              readLine() method will not exhaust memory, nor will memory be
 *              exhausted by the while loop.
 */
class ReadNames {

	public static final int FILE_SIZE_LIMIT = 1_000_000;
	
	private Vector<String> names = new Vector<String>();
	private final InputStreamReader input;
	private final BufferedReader reader;
	
	public ReadNames(String filename) throws IOException {
		if (Files.size(Paths.get(filename)) > FILE_SIZE_LIMIT) {
			throw new IOException("File too large");
		}
		this.input = new FileReader(filename);
		this.reader = new BufferedReader(input);
	}

	public void addNames() throws IOException {
		try {
			String newName;
			while (((newName = reader.readLine()) != null) && !(newName.equalsIgnoreCase("quit"))) {
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
		ReadNames demo = new ReadNames(args[0]);
		demo.addNames();
	}
}
