package secure.coding.chapter04.num.num02;

/**
 * @rule: NUM02-J. Ensure that division and modulo operations do not result in
 *        divide-by-zero errors
 * 
 * @description: Division and modulo operations are susceptible to
 *               divide-by-zero errors. Consequently, the divisor in a division
 *               or modulo operation must be checked for zero prior to the
 *               operation.
 */
public class DivisionZeroCheck {

	/**
	 * @category NonCompliant Code
	 * 
	 * @description: The result of the / operator is the quotient from the division
	 *               of the first arithmetic operand by the second arithmetic
	 *               operand. Division operations are susceptible to divide-by-zero
	 *               errors. Overflow can also occur during two’s-complement signed
	 *               integer division when the dividend is equal to the minimum
	 *               (negative) value for the signed integer type and the divisor is
	 *               equal to −1.
	 */
	static void _divide() {
		long num1 = 5, num2 = 0, result;
		result = num1 / num2;
		System.out.println(result);
	}

	/**
	 * @category Compliant Solution
	 * 
	 * @description: This compliant solution tests the divisor to guarantee there is
	 *               no possibility of divide-by zero errors.
	 */
	static void divide() {
		long num1 = 5, num2 = 0, result;

		if (num2 == 0) {
			throw new IllegalStateException("num2 must not be zero");
		}
		result = num1 / num2;
		System.out.println(result);
	}
	
	public static void main(String[] args) {
		try {
			_divide();
		}catch(Exception e) {
			System.err.println("Non compliant code: " + e.getMessage());
		}
		
		divide();
	}
}
