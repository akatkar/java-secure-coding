package secure.coding.chapter09.fio.fio12;

import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

/**
 * @rule FIO12-J. Provide methods to read and write little-endian data
 * 
 * @description In Java, data is stored in big-endian format (also called
 *              network order). That is, all data is represented sequentially
 *              starting from the most significant bit to the least significant.
 *              JDK versions prior to JDK 1.4 required definition of custom
 *              methods that manage reversing byte order to maintain
 *              compatibility with little-endian systems. Correct handling of
 *              byte order– related issues is critical when exchanging data in a
 *              networked environment that includes both big-endian and
 *              little-endian machines or when working with other languages
 *              using JNI. Failure to handle byte-ordering issues can cause
 *              unexpected program behavior.
 */
public final class LittleEndian {
	/**
	 * @category Noncompliant code
	 *
	 * @description The read methods (readByte(), readShort(), readInt(),
	 *              readLong(), readFloat(), and readDouble()) and the corresponding
	 *              write methods defined by class java.io.DataInput Stream and
	 *              class java.io.DataOutputStream operate only on big-endian data.
	 *              Use of these methods while interoperating with traditional
	 *              languages, such as C or C++, is insecure because such languages
	 *              lack any guarantees about endianness. This noncompliant code
	 *              example shows such a discrepancy.
	 */
	public static void readData() {
		try (DataInputStream dis = new DataInputStream(new FileInputStream("data"))) {
			// Little-endian data might be read as big-endian
			int serialNumber = dis.readInt();
			System.out.println(serialNumber);
		} catch (IOException e) {
			// handle error
		}
	}

	/**
	 * @category Compliant solution (ByteBuffer)
	 *
	 * @description This compliant solution uses methods provided by class
	 *              ByteBuffer (see [ API 2006 ] ByteBuffer) to correctly extract an
	 *              int from the original input value. It wraps the input byte array
	 *              with a ByteBuffer, sets the byte order to little-endian, and
	 *              extracts the int. The result is stored in the integer
	 *              serialNumber. Class ByteBuffer provides analogous get and put
	 *              methods for other numeric types.
	 */
	public static void readDataWithBuffer() {
		try (DataInputStream dis = new DataInputStream(new FileInputStream("data"))) {
			byte[] buffer = new byte[4];
			int bytesRead = dis.read(buffer); // Bytes are read into buffer
			if (bytesRead != 4) {
				throw new IOException("Unexpected End of Stream");
			}
			int serialNumber = ByteBuffer.wrap(buffer).order(ByteOrder.LITTLE_ENDIAN).getInt();
			System.out.println(serialNumber);
		} catch (IOException e) {
			// handle error
		}
	}

	/**
	 * @category Compliant solution (Define Special-Purpose Methods)
	 *
	 * @description An alternative compliant solution is to define read and write
	 *              methods that support the necessary byte-swapping while reading
	 *              from or writing to the file. In this example, the
	 *              readLittleEndianInteger() method reads four bytes into a byte
	 *              buffer and then pieces together the integer in the correct
	 *              order. The writeLittleEndianInteger() method obtains bytes by
	 *              repeatedly casting the integer so that the least significant
	 *              byte is extracted on successive right shifts. Long values can be
	 *              handled by defining a byte buffer of size 8.
	 */
	// Read method
	public static int readLittleEndianInteger(InputStream ips) throws IOException {
		byte[] buffer = new byte[4];
		int check = ips.read(buffer);
		if (check != 4) {
			throw new IOException("Unexpected End of Stream");
		}
		int result = (buffer[3] << 24) | (buffer[2] << 16) | (buffer[1] << 8) | buffer[0];
		return result;
	}

	// Write method
	public static void writeLittleEndianInteger(int i, OutputStream ops) throws IOException {
		byte[] buffer = new byte[4];
		buffer[0] = (byte) i;
		buffer[1] = (byte) (i >> 8);
		buffer[2] = (byte) (i >> 16);
		buffer[3] = (byte) (i >> 24);
		ops.write(buffer);
	}

	/**
	 * @category Compliant solution (reverseBytes())
	 *
	 * @description When programming for JDK 1.5+, use the reverseBytes() method
	 *              defined in the classes Character, Short, Integer, and Long to
	 *              reverse the order of the integral value’s bytes. Note that
	 *              classes Float and Double lack such a method.
	 */
	public static int reverse(int i) {
		return Integer.reverseBytes(i);
	}
}
