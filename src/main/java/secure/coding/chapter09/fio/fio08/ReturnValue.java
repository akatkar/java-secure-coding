package secure.coding.chapter09.fio.fio08;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * @rule FIO08-J. Use an int to capture the return value of methods that read a
 *       character or byte
 * 
 * @description The abstract InputStream.read() method reads a single byte from
 *              an input source and returns its value as an int in the range 0
 *              to 255. It will return -1 only when the end of the input stream
 *              has been reached. The similar Reader.read() method reads a
 *              single character and returns its value as an int in the range 0
 *              to 65,535. It also returns -1 only when the end of the stream
 *              has been reached. Both methods are meant to be overridden by
 *              subclasses.
 * 
 *              These methods are often used to read a byte or character from a
 *              stream. Unfortunately, many programmers prematurely convert the
 *              resulting int back to a byte or char before checking whether
 *              they have reached the end of the stream (indicated by a return
 *              value of -1). Programs must check for the end of stream (e.g.,
 *              -1) before narrowing the return value to a byte or char.
 */
public final class ReturnValue {
	/**
	 * @category Noncompliant code
	 *
	 * @description This noncompliant code example casts the value returned by the
	 *              read() method directly to a value of type byte and then compares
	 *              this value with -1 in an attempt to detect the end of the
	 *              stream. This conversion leaves the value of c as 0xFFFF (e.g.,
	 *              Character.MAX_VALUE) instead of -1. Consequently, the test for
	 *              the end of stream never evaluates to true (because the char type
	 *              is unsigned and the value of c is 0-extended to 0x0000FFFF).
	 */
	public static void example1() throws IOException {
		try (FileInputStream in = new FileInputStream(new File("somefile.txt"))) {
			byte data;
			while ((data = (byte) in.read()) != -1) {
				System.out.println(data);
			}
		}
	}

	/**
	 * @category Compliant solution
	 *
	 * @description Use a variable of type int to capture the return value of the
	 *              byte input method. When the value returned by read() is not -1,
	 *              it can be safely cast to type byte. When read() returns 0XFF,
	 *              the comparison will test 0x000000FF against 0xFFFFFFFF and fail.
	 */
	public static void example2() throws IOException {
		try (FileInputStream in = new FileInputStream(new File("somefile.txt"))) {
			int inbuff;
			while ((inbuff = in.read()) != -1) {
				byte data = (byte) inbuff;
				System.out.println(data);
			}
		}
	}

	/**
	 * @category Noncompliant code
	 *
	 * @description This noncompliant code example casts the value of type int
	 *              returned by the read() method directly to a value of type char,
	 *              which is then compared with -1 in an attempt to detect the end
	 *              of stream. This conversion leaves the value of c as 0xFFFF (that
	 *              is, Character.MAX_ VALUE) instead of -1. Consequently, the test
	 *              for the end of stream never evaluates to true.
	 */
	public static void example3() throws IOException {
		try (FileInputStream in = new FileInputStream(new File("somefile.txt"))) {
			char c;
			while ((c = (char) in.read()) != -1) {
				System.out.println(c);
			}
		}
	}

	/**
	 * @category Compliant solution
	 *
	 * @description Use a variable of type int to capture the return value of the
	 *              character input method. When the value returned by read() is not
	 *              -1, it can be safely cast to type char.
	 */
	public static void example4() throws IOException {
		try (FileInputStream in = new FileInputStream(new File("somefile.txt"))) {
			int inbuff;
			while ((inbuff = in.read()) != -1) {
				char data = (char) inbuff;
				System.out.println(data);
			}
		}
	}
}
