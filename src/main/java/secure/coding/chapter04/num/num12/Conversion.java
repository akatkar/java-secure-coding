package secure.coding.chapter04.num.num12;

/**
 * @rule: NUM12-J. Ensure conversions of numeric types to narrower types do not
 *        result in lost or misinterpreted data
 * 
 * @description: Conversions of numeric types to narrower types can result in
 *               lost or misinterpreted data if the value of the wider type is
 *               outside the range of values of the narrower type. As a result,
 *               all narrowing conversions must be guaranteed safe by
 *               range-checking the value before conversion.
 */
public class Conversion {

	/**
	 * @category Non-compliant code
	 * 
	 * @description: In this noncompliant code example, a value of type int is
	 *               converted to a value of type byte without range checking.
	 */
	static void _workWith(int i) {
		byte b = (byte) i; // b has value -128
		// work with b
		System.out.println(b);
	}

	/**
	 * @category Compliant solution
	 * 
	 * @description: This compliant solution validates that the value stored in the
	 *               wider integer type is within the range of the narrower type
	 *               before converting to the narrower type.
	 */
	static void workWith(int i) {
		// check if i is within byte range
		if ((i < Byte.MIN_VALUE) || (i > Byte.MAX_VALUE)) {
			throw new ArithmeticException("Value is out of range: " + i);
		}
		byte b = (byte) i;
		// work with b
		System.out.println(b);
	}

	/**
	 * @category Non-compliant code
	 */
	static void _floatToInteger(float x, float y) {
		short a = (short) x;
		short b = (short) y;
		System.out.println("a:" + a + ", b:" + b);
	}

	/**
	 * @category Compliant solution
	 */
	static void floatToInteger(float x, float y) {
		short a = toShort(x);
		short b = toShort(y);
		System.out.println("a:" + a + ", b:" + b);
	}

	static private short toShort(float value) {
		if ((value < Short.MIN_VALUE) || (value > Short.MAX_VALUE)) {
			throw new ArithmeticException("Value is out of range: " + value);
		}
		return (short) value;
	}

	/**
	 * @category Non-compliant code
	 */
	static void _doubleToFloat(double x, double y) {
		float a = (float) x;
		float b = (float) y;
		System.out.println("a:" + a + ", b:" + b);
	}

	/**
	 * @category Compliant solution
	 */
	static void doubleToFloat(double x, double y) {
		float a = toFloat(x);
		float b = toFloat(y);
		System.out.println("a:" + a + ", b:" + b);
	}

	static private float toFloat(double value) {
		if ((value < Float.MIN_VALUE) || (value > Float.MAX_VALUE)) {
			throw new ArithmeticException("Value is out of range: " + value);
		}
		return (float) value;
	}

	public static void main(String[] args) {

		// Integer to byte
		int i = 128;
		_workWith(i);
		try {
			workWith(i);
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}

		// Float to Short
		_floatToInteger(Float.MIN_VALUE, Float.MAX_VALUE);

		try {
			floatToInteger(Float.MIN_VALUE, Float.MAX_VALUE);
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}

		// Double to Float
		_doubleToFloat(Double.MIN_VALUE, Double.MAX_VALUE);

		try {
			doubleToFloat(Double.MIN_VALUE, Double.MAX_VALUE);
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
	}
}
