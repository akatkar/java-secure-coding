package secure.coding.chapter09.fio.fio09;

import java.io.DataOutputStream;
import java.io.IOException;

/**
 * @rule FIO09-J. Do not rely on the write() method to output integers outside
 *       the range 0 to 255
 * 
 * @description The write() method, defined in the class java.io.OutputStream,
 *              takes an argument of type int the value of which must be in the
 *              range 0 to 255. Because a value of type int could be outside
 *              this range, failure to range check can result in the truncation
 *              of the higher-order bits of the argument.
 * 
 *              The general contract for the write() method says that it writes
 *              one byte to the output stream. The byte to be written
 *              constitutes the eight lower-order bits of the argument b, passed
 *              to the write() method; the 24 high-order bits of b are ignored
 *              (see [ API 2006 ] java.io.OutputStream.write() for more
 *              information).
 */
public final class Writer {
	/**
	 * @category Noncompliant code
	 *
	 * @description This noncompliant code example accepts a value from the user
	 *              without validating it. Any value that is not in the range of 0
	 *              to 255 is truncated. For instance, write(303) prints / on
	 *              ASCII-based systems because the lower-order 8 bits of 303 are
	 *              used while the 24 high-order bits are ignored (303 % 256 = 47,
	 *              which is the ASCII code for /). That is, the result is the
	 *              remainder of the input divided by 256.
	 */
	public static void main(String[] args) {
		// Any input value > 255 will result in unexpected output
		System.out.write(Integer.valueOf(args[0]));
		System.out.flush();
	}

	/**
	 * @category Compliant solution
	 *
	 * @description This compliant solution prints the corresponding character only
	 *              if the input integer is in the proper range. If the input is
	 *              outside the representable range of an int, the Integer.valueOf()
	 *              method throws a NumberFormatException. If the input can be
	 *              represented by an int but is outside the range required by
	 *              write(), this code throws an ArithmeticException.
	 */
	public static void rangeCheck(String valueStr) throws IOException {
		// Perform range checking
		int value = Integer.valueOf(valueStr);
		if (value < 0 || value > 255) {
			throw new ArithmeticException("Value is out of range");
		}
		System.out.write(value);
		System.out.flush();
	}

	/**
	 * @category Compliant solution
	 *
	 * @description This compliant solution uses the writeInt() method of the
	 *              DataOutputStream class, which can output the entire range of
	 *              values representable as an int.
	 */
	public static void writeInt(String valueStr) throws IOException {

		DataOutputStream dos = new DataOutputStream(System.out);
		dos.writeInt(Integer.valueOf(valueStr));
		System.out.flush();
	}
}
