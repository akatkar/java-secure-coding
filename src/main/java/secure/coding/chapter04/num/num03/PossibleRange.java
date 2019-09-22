package secure.coding.chapter04.num.num03;

import java.io.DataInputStream;
import java.io.IOException;

/**
 * @rule: NUM02-J. Ensure that division and modulo operations do not result in
 *        divide-by-zero errors
 * 
 * @description: The only unsigned primitive integer type in Java is the 16-bit
 *               char data type; all of the other primitive integer types are
 *               signed.
 * 
 *               To interoperate with native languages, such as C or C++, that
 *               use unsigned types extensively, any unsigned values must be
 *               read and stored into a Java integer type that can fully
 *               represent the possible range of the unsigned data.
 * 
 *               For example, the Java long type can be used to represent all
 *               possible unsigned 32-bit integer values obtained from native
 *               code.
 */
public class PossibleRange {

	/**
	 * @category NonCompliant Code
	 * 
	 * @description: This non-compliant code example uses a generic method for
	 *               reading integer data without considering the signedness of the
	 *               source. It assumes that the data read is always signed and
	 *               treats the most significant bit as the sign bit. When the data
	 *               read is unsigned, the actual sign and magnitude of the values
	 *               may be misinterpreted.
	 */
	public static int _getInteger(DataInputStream is) throws IOException {
		return is.readInt();
	}

	/**
	 * @category Compliant Solution
	 * 
	 * @description: This compliant solution requires that the values read are
	 *               32-bit unsigned integers. It reads an unsigned integer value
	 *               using the readInt() method. The readInt() method assumes signed
	 *               values and returns a signed int; the return value is converted
	 *               to a long with sign extension. The code uses an & operation to
	 *               mask off the upper 32 bits of the long producing a value in the
	 *               range of a 32-bit unsigned integer, as intended. The mask size
	 *               should be chosen to match the size of the unsigned integer
	 *               values being read.
	 */
	public static long getInteger(DataInputStream is) throws IOException {
		return is.readInt() & 0xFFFFFFFFL; // mask with 32 one-bits
	}
}
