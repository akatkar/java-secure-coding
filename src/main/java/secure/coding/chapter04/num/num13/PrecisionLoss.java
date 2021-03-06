package secure.coding.chapter04.num.num13;

/**
 * @rule: NUM13-J. Avoid loss of precision when converting primitive integers
 *        to floating-point
 * 
 * 
 * @description: Conversion from int or long to float or from long to double can
 *               lead to loss of precision (loss of least significant bits).
 */
public strictfp class PrecisionLoss {

	/**
	 * @category Non-compliant code
	 */
	public static int _subFloatFromInt(int op1, float op2) {
		return op1 - (int) op2;
	}

	/**
	 * @category Compliant solution
	 * 
	 * @description: This compliant solution range checks the argument of the
	 *               integer argument (op2) to ensure it can be represented as a
	 *               value of type float without a loss of precision.
	 */
	public static int subFloatFromInt(int op1, float op2) throws ArithmeticException {
		// The significant can store at most 23 bits
		if ((op2 > 0x007FFFFF) || (op2 < -0x800000)) {
			throw new ArithmeticException("Insufficient precision");
		}
		return op1 - (int) op2;
	}

	/**
	 * @category Compliant solution
	 * 
	 * @description: Compliant Solution (Wider Type) This compliant solution accepts
	 *               an argument of type double instead of an argument of type
	 *               float. In FP-strict mode, values of type double have 52
	 *               mantissa bits, a sign bit, and an 11-bit exponent. Integer
	 *               values of type int and narrower can be converted to double
	 *               without a loss of precision.
	 * 
	 * @note: Note that this compliant solution cannot be used when the primitive
	 *        integers are of type long because Java lacks a primitive
	 *        floating-point type whose mantissa can represent the full range of a
	 *        long.
	 */
	public static int subDoubleFromInt(int op1, double op2) {
		return op1 - (int) op2;
	}

	public static void main(String[] args) {
		// This prints -46, and not 0 as may be expected
		int result = _subFloatFromInt(1234567890, 1234567890);
		System.out.println(result);

		try {
			// This throws an exception as Insufficient precision
			result = subFloatFromInt(1234567890, 1234567890);
			System.out.println(result);
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
		// This prints 0 as may be expected
		result = subDoubleFromInt(1234567890, 1234567890);	
		System.out.println(result);
	}
}
