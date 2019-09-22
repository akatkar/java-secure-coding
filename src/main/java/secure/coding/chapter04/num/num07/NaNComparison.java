package secure.coding.chapter04.num.num07;

/**
 * @rule: NUM07-J. D o not attempt comparisons with NaN
 *
 * 
 * @description: NaN (not-a-number) is unordered, so the numerical comparison
 *               operators <, <=, >, and >= return false if either or both
 *               operands are NaN. The equality operator == returns false if
 *               either operand is NaN, and the inequality operator != returns
 *               true if either operand is NaN.
 */
public class NaNComparison {

	/**
	 * @category Non-compliant code
	 * 
	 * @description: This noncompliant code example attempts a direct comparison
	 *               with NaN. In accordance with the semantics of NaN, all
	 *               comparisons with NaN yield false (with the exception of the !=
	 *               operator, which returns true). Consequently, this comparison
	 *               always returns false, and the “result is NaN” message is never
	 *               printed.
	 */
	static void _compareNaN() {
		double x = 0.0;
		double result = Math.cos(1 / x); // returns NaN if input is infinity
		if (result == Double.NaN) { // comparison is always false!
			System.out.println("result is NaN");
		}
	}

	/**
	 * @category Compliant solution
	 * 
	 * @description: This compliant solution uses the method Double.isNaN() to check
	 *               whether the expression corresponds to a NaN value.
	 */
	static void compareNaN() {
		double x = 0.0;
		double result = Math.cos(1 / x); // returns NaN when input is infinity
		if (Double.isNaN(result)) {
			System.out.println("result is NaN");
		}
	}

	public static void main(String[] args) {
		System.out.print("Non-compliant code : ");
		_compareNaN();
		System.out.println();
		
		System.out.print("Compliant solution : ");
		compareNaN();
	}
}
