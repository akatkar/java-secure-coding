package secure.coding.chapter09.fio.fio14;

import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;

/**
 * @rule FIO14-J. Perform proper cleanup at program termination
 * 
 * @description When certain kinds of errors are detected, such as irrecoverable
 *              logic errors, rather than risk data corruption by continuing to
 *              execute in an indeterminate state, the appropriate strategy may
 *              be for the system to quickly shut down, allowing the operator to
 *              start it afresh in a determinate state.
 * 
 *              Java programs do not flush unwritten buffered data or close open
 *              files when they exit, so programs must perform these operations
 *              manually. Programs must also perform any other cleanup that
 *              involves external resources, such as releasing shared locks.
 */
public final class CleanUp {

	/**
	 * @category Noncompliant code
	 *
	 * @description This example creates a new file, outputs some text to it, and
	 *              abruptly exits using Runtime.exit(). Consequently, the file may
	 *              be closed without the text actually being written.
	 */
	public static void createFile() throws FileNotFoundException {
		final PrintStream out = new PrintStream(new BufferedOutputStream(new FileOutputStream("foo.txt")));
		out.println("hello");
		Runtime.getRuntime().exit(1);
	}

	/**
	 * @category Compliant solution (close())
	 *
	 * @description This solution explicitly closes the file before exiting.
	 */
	public static void createFile2() throws FileNotFoundException {

		final PrintStream out = new PrintStream(new BufferedOutputStream(new FileOutputStream("foo.txt")));

		try {
			out.println("hello");
		} finally {
			out.close();
		}
		Runtime.getRuntime().exit(1);
	}

	public static void createFile3() throws FileNotFoundException {

		try (final PrintStream out = new PrintStream(new BufferedOutputStream(new FileOutputStream("foo.txt")))) {
			out.println("hello");
		}
		Runtime.getRuntime().exit(1);
	}

	/**
	 * @category Compliant solution (Shutdown Hook)
	 *
	 * @description This compliant solution adds a shutdown hook to close the file.
	 *              This hook is invoked by Runtime.exit() and is called before the
	 *              JVM is halted.
	 */
	public static void createFile4() throws FileNotFoundException {

		final PrintStream out = new PrintStream(new BufferedOutputStream(new FileOutputStream("foo.txt")));

		Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
			public void run() {
				out.close();
			}
		}));

		out.println("hello");
		Runtime.getRuntime().exit(1);
	}

	public static void createFile5() throws FileNotFoundException {

		final PrintStream out = new PrintStream(new BufferedOutputStream(new FileOutputStream("foo.txt")));

		Runtime.getRuntime().addShutdownHook(new Thread(() -> out.close()));
		out.println("hello");
		Runtime.getRuntime().exit(1);
	}

	/**
	 * @category Noncompliant code (Runtime.halt())
	 *
	 * @description This noncompliant code example calls Runtime.halt() instead of
	 *              Runtime.exit(). The Runtime.halt() method stops the JVM without
	 *              invoking any shutdown hooks; consequently the file is not
	 *              properly written to or closed.
	 */
	public static void createFile6() throws FileNotFoundException {
		final PrintStream out = new PrintStream(new BufferedOutputStream(new FileOutputStream("foo.txt")));
		Runtime.getRuntime().addShutdownHook(new Thread(out::close));
		out.println("hello");
		Runtime.getRuntime().halt(1);
	}

	/**
	 * @category Noncompliant code (Signal)
	 *
	 * @description When a user forcefully exits a program, for example by pressing
	 *              the ctrl + c keys or by using the kill command, the JVM
	 *              terminates abruptly. Although this event cannot be captured, the
	 *              program should nevertheless perform any mandatory cleanup
	 *              operations before exiting. This noncompliant code example fails
	 *              to do so.
	 */
	public static void InterceptExit() throws FileNotFoundException {
		InputStream in = null;
		try {
			in = new FileInputStream("file");
			System.out.println("Regular code block");
			// Abrupt exit such as ctrl + c key pressed
			System.out.println("This never executes");
		} finally {
			if (in != null) {
				try {
					in.close(); // this never executes either
				} catch (IOException x) {
					// handle error
				}
			}
		}
	}

	/**
	 * @category Compliant solution (addShutdownHook())
	 *
	 * @description Use the addShutdownHook() method of java.lang.Runtime to assist
	 *              with performing cleanup operations in the event of abrupt
	 *              termination. The JVM starts the shutdown hook thread when abrupt
	 *              termination is initiated; the shutdown hook runs concurrently
	 *              with other JVM threads.
	 */
	public static void InterceptExit2() throws FileNotFoundException {
		try {
			final InputStream in = new FileInputStream("file");
			Runtime.getRuntime().addShutdownHook(new Thread() {
				public void run() {
					// Log shutdown and close all resources
					try {
						in.close();
					} catch (IOException e) {
					}
				}
			});
			// ...
		} catch (IOException x) {
			// handle error
		}
	}

}
