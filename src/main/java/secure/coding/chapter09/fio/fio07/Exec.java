package secure.coding.chapter09.fio.fio07;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;

/**
 * @rule FIO07-J. Do not let external processes block on input and output
 *       streams
 * 
 * @description Output from an external process can exhaust the available buffer
 *              reserved for its output or error stream. When this occurs, the
 *              Java program can block the external process as well, preventing
 *              any forward progress for both the Java program and the external
 *              process.
 * 
 *              Note that many platforms limit the buffer size available for
 *              output streams. Consequently, when invoking an external process,
 *              if the process sends any data to its output stream, the output
 *              stream must be emptied. Similarly, if the process sends any data
 *              to its error stream, the error stream must also be emptied.
 */
public final class Exec {
	/**
	 * @category Noncompliant code
	 *
	 * @description This noncompliant code example invokes notemaker using the
	 *              exec() method, which returns a Process object. The exitValue()
	 *              method returns the exit value for processes that have
	 *              terminated, but it throws an IllegalThreadStateException when
	 *              invoked on an active process. Because this noncompliant example
	 *              program fails to wait for the notemaker process to terminate,
	 *              the call to exitValue() is likely to throw an
	 *              IllegalThreadStateException.
	 */
	public static int execNotemaker() throws IOException {
		Runtime rt = Runtime.getRuntime();
		Process proc = rt.exec("notemaker");
		return proc.exitValue();
	}

	/**
	 * @category Noncompliant code
	 *
	 * @description In this noncompliant code example, the waitFor() method blocks
	 *              the calling thread until the notemaker process terminates. This
	 *              prevents the IllegalThreadStateException from the previous
	 *              example. However, the example program may experience an
	 *              arbitrary delay before termination. Output from the notemaker
	 *              process can exhaust the available buffer for the output or error
	 *              stream because neither stream is read while waiting for the
	 *              process to complete. If either buffer becomes full, it can block
	 *              the notemaker process as well, preventing all progress for both
	 *              the notemaker process and the Java program.
	 */
	public static int waitForNotemaker() throws IOException, InterruptedException {
		Runtime rt = Runtime.getRuntime();
		Process proc = rt.exec("notemaker");
		return proc.waitFor();
	}

	/**
	 * @category Noncompliant code
	 *
	 * @description This noncompliant code example properly empties the process’s
	 *              output stream, thereby preventing the output stream buffer from
	 *              becoming full and blocking. However, it ignores the process’s
	 *              error stream, which can also fill and cause the process to
	 *              block.
	 */
	public static int processStream() throws IOException, InterruptedException {
		Runtime rt = Runtime.getRuntime();
		Process proc = rt.exec("notemaker");
		InputStream is = proc.getInputStream();
		int c;
		while ((c = is.read()) != -1) {
			System.out.print((char) c);
		}
		return proc.waitFor();
	}

	/**
	 * @category Compliant solution (redirectErrorStream())
	 *
	 * @description This compliant solution redirects the process’s error stream to
	 *              its output stream. Consequently, the program can empty the
	 *              single output stream without fear of blockage.
	 */
	public static int redirectErrorStream() throws IOException, InterruptedException {
		ProcessBuilder pb = new ProcessBuilder("notemaker");
		pb = pb.redirectErrorStream(true);
		Process proc = pb.start();
		InputStream is = proc.getInputStream();
		int c;
		while ((c = is.read()) != -1) {
			System.out.print((char) c);
		}
		return proc.waitFor();
	}

	/**
	 * @category Compliant solution (Process Output Stream and Error Stream)
	 *
	 * @description This compliant solution spawns two threads to consume the
	 *              process’s output stream and error stream. Consequently, the
	 *              process cannot block indefinitely on those streams.
	 * 
	 *              When the output and error streams are handled separately, they
	 *              must be emptied independently. Failure to do so can cause the
	 *              program to block indefinitely.
	 */
	public static int processStreams() throws IOException, InterruptedException {
		Runtime rt = Runtime.getRuntime();
		Process proc = rt.exec("notemaker");
		// Any error message?
		StreamGobbler errorGobbler = new StreamGobbler(proc.getErrorStream(), System.err);
		// Any output?
		StreamGobbler outputGobbler = new StreamGobbler(proc.getInputStream(), System.out);
		errorGobbler.start();
		outputGobbler.start();
		// Any error?
		int exitVal = proc.waitFor();
		errorGobbler.join(); // Handle condition where the
		outputGobbler.join(); // process ends before the threads finish
		return exitVal;
	}

	public static void main(String args[]) throws IOException {

	}
}

class StreamGobbler extends Thread {
	InputStream is;
	PrintStream os;

	StreamGobbler(InputStream is, PrintStream os) {
		this.is = is;
		this.os = os;
	}

	public void run() {
		try {
			int c;
			while ((c = is.read()) != -1)
				os.print((char) c);
		} catch (IOException x) {
			// handle error
		}
	}
}
