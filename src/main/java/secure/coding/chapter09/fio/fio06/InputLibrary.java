package secure.coding.chapter09.fio.fio06;

import java.io.BufferedInputStream;
import java.io.EOFException;
import java.io.IOException;

/**
 * @rule FIO06-J. Do not create multiple buffered wrappers on a single
 *       InputStream
 * 
 * @description Java input classes such as Scanner and BufferedInputStream
 *              facilitate fast, nonblocking I/O by buffering an underlying
 *              input stream. Programs can create multiple wrappers on an
 *              InputStream. Programs that use multiple wrappers around a single
 *              input stream, however, can behave unpredictably depending on
 *              whether the wrappers allow look-ahead. An attacker can exploit
 *              this difference in behavior by, for example, redirecting
 *              System.in (from a file) or by using the System.setIn() method to
 *              redirect System.in. In general, any input stream that supports
 *              nonblocking buffered I/O is susceptible to this form of misuse.
 * 
 *              An input stream must not have more than one buffered wrapper.
 *              Instead, create and use only one wrapper per input stream,
 *              either by passing it as an argument to the methods that need it
 *              or by declaring it as a class variable.
 * 
 * @category Noncompliant code
 *
 * @description This noncompliant code example creates multiple
 *              BufferedInputStream wrappers on System.in, even though there is
 *              only one declaration of a BufferedInputStream. The getChar()
 *              method creates a new BufferedInputStream each time it is called.
 *              Data that is read from the underlying stream and placed in the
 *              buffer during execution of one call cannot be replaced in the
 *              underlying stream so that a second call has access to it.
 *              
 *              Consequently, data that remains in the buffer at the end of a
 *              particular execution of getChar() is lost. Although this
 *              noncompliant code example uses a BufferedInputStream, any
 *              buffered wrapper is unsafe; this condition is also exploitable
 *              when using a Scanner,
 */
public final class InputLibrary {
	public static char getChar() throws EOFException, IOException {
		// wrapper
		BufferedInputStream in = new BufferedInputStream(System.in);
		int input = in.read();
		if (input == -1) {
			throw new EOFException();
		}
		// Down casting is permitted because InputStream
		// guarantees read() in range
		// 0..255 if it is not -1
		return (char) input;
	}

	public static void main(String[] args) {
		try {
			// Either redirect input from the console or use
			// System.setIn(new FileInputStream("input.dat"));
			System.out.print("Enter first initial: ");
			char first = getChar();
			System.out.println("Your first initial is " + first);
			System.out.print("Enter last initial: ");
			char last = getChar();
			System.out.println("Your last initial is " + last);
		} catch (EOFException e) {
			System.err.println("ERROR");
			// Forward to handler
		} catch (IOException e) {
			System.err.println("ERROR");
			// Forward to handler
		}
	}
}
