package secure.coding.chapter04.num.num11;

import java.math.BigDecimal;

/**
 * @rule: NUM11-J. Do not compare or inspect the string representation of
 *        floating-point values
 * 
 * 
 * @description: String representations of floating-point numbers must not be
 *               compared or inspected.
 */
public strictfp class FloatingPointString {

	/**
	 * @category Non-compliant code
	 * 
	 * @description: This noncompliant code example attempts to mitigate the extra
	 *               trailing zero by using a regular expression on the string
	 *               before comparing it.
	 */
	static void _compare() {
		int i = 1;
		String s = Double.valueOf(i / 1000.0).toString();
		if (s.equals("0.001")) {
			System.out.println("equals");
		}
	}

	/**
	 * @category Compliant solution
	 * 
	 * @description: This compliant solution uses the BigDecimal class to avoid
	 *               precision loss. It then performs a numeric comparison, which
	 *               passes as expected.
	 */
	static void compare() {
		int i = 1;
		BigDecimal d = new BigDecimal(Double.valueOf(i / 1000.0).toString());
		if (d.compareTo(new BigDecimal("0.001")) == 0) {
			System.out.println("equals");
		}
	}
	
	public static void main(String[] args) {
		_compare();
		compare();
	}
}
