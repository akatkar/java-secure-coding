package secure.coding.chapter09.fio.fio06.solution;

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
 *              <p>
 *              An input stream must not have more than one buffered wrapper.
 *              Instead, create and use only one wrapper per input stream,
 *              either by passing it as an argument to the methods that need it
 *              or by declaring it as a class variable.
 *              
 * @category Compliant solution
 * 
 * @description Create and use only a single BufferedInputStream on System.in.
 *              This compliant solution ensures that all methods can access the
 *              BufferedInputStream by declaring it as a class variable.
 */
public final class InputLibrary {
	
	private static BufferedInputStream in = new BufferedInputStream(System.in);

	public static char getChar() throws IOException {
		int input = in.read();
		if (input == -1) {
			throw new EOFException();
		}
		in.skip(1); 
		// This statement is to advance to the next line
		// The noncompliant code example deceptively
		// appeared to work without it (in some cases)
		return (char) input;
	}

	public static void main(String[] args) {
		try {
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
