package secure.coding.chapter09.fio.fio10;

import java.io.DataInputStream;
import java.io.EOFException;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * @rule FIO10-J. Ensure the array is filled when using read() to fill an array
 * 
 * @description This rule applies only to read() methods that take an array
 *              argument. To read a single byte, use the InputStream.read()
 *              method that takes no arguments and returns an int. To read a
 *              single character, use a Reader.read() method that takes no
 *              arguments and returns the character read as an int.
 */
public final class FillArray {
	/**
	 * @category Noncompliant code
	 *
	 * @description This noncompliant code example attempts to read 1024 bytes
	 *              encoded in UTF-8 from an InputStream and return them as a
	 *              String. It explicitly specifies the character encoding used to
	 *              build the string, in compliance with rule IDS13-J.
	 */
	public static String readBytes1(InputStream in) throws IOException {
		byte[] data = new byte[1024];
		if (in.read(data) == -1) {
			throw new EOFException();
		}
		return new String(data, "UTF-8");
	}

	/**
	 * @category Compliant solution (Multiple Calls to read())
	 *
	 * @description This compliant solution reads all the desired bytes into its
	 *              buffer, accounting for the total number of bytes read and
	 *              adjusting the remaining bytes’ offset, consequently ensuring
	 *              that the required data is read in full. It also avoids splitting
	 *              multibyte encoded characters across buffers by deferring
	 *              construction of the result string until the data has been fully
	 *              read. (See rule IDS10-J for more information.)
	 */
	public static String readBytes(InputStream in) throws IOException {
		int offset = 0;
		int bytesRead = 0;
		byte[] data = new byte[1024];
		while ((bytesRead = in.read(data, offset, data.length - offset)) != -1) {
			offset += bytesRead;
			if (offset >= data.length) {
				break;
			}
		}
		String str = new String(data, "UTF-8");
		return str;
	}

	/**
	 * @category Compliant solution (readFully())
	 *
	 * @description The no-argument and one-argument readFully() methods of the
	 *              DataInputStream class guarantee that either all of the requested
	 *              data is read or an exception is thrown. These methods throw
	 *              EOFException if they detect the end of input before the required
	 *              number of bytes have been read; they throw IOException if some
	 *              other I/O error occurs.
	 */
	public static String readBytes(FileInputStream fis) throws IOException {
		byte[] data = new byte[1024];
		DataInputStream dis = new DataInputStream(fis);
		dis.readFully(data);
		String str = new String(data, "UTF-8");
		return str;
	}
}
