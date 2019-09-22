package secure.coding.chapter04.num.num08;

/**
 * @rule: NUM08-J. Check floating-point inputs for exceptional values
 *
 * @description: Floating-point numbers can take on three exceptional values:
 *               infinity, -infinity, and NaN (not-a-number).
 * 
 *               These values are produced as a result of exceptional or
 *               otherwise unresolvable floating-point operations, such as
 *               division by zero. These exceptional values can also be obtained
 *               directly from user input through methods such as Double.
 *               valueOf(String s). Failure to detect and handle such
 *               exceptional values can result in inconsistent behavior. The
 *               method Double.valueOf(String s) can return NaN or an infinite
 *               double, as specified by its contract. Programs must ensure that
 *               all floating-point inputs (especially those obtained from the
 *               user) are free of unexpected exceptional values. The methods
 *               Double. isNaN(double d) and Double.isInfinite(double d) can be
 *               used for this purpose. NaN values are particularly problematic
 *               because they are unordered. That is, the expression NaN == NaN
 *               always returns false. See rule NUM07-J for more information.
 * 
 */
public class CheckFloatingPoint {

	double currentBalance; // User's cash balance

	/**
	 * @category Non-compliant code
	 * 
	 * @description: This noncompliant code example accepts user data without
	 *               validating it.This code will produce unexpected results when an
	 *               exceptional value is entered for val and subsequently used in
	 *               calculations or as control values.
	 */
	void _doDeposit(String userInput) {
		double val;
		try {
			val = Double.valueOf(userInput);
		} catch (NumberFormatException e) {
			throw new IllegalArgumentException("argument must be numeric value: " + userInput);
		}
		if (val >= Double.MAX_VALUE - currentBalance) {
			throw new IllegalStateException("overflow error");
		}
		currentBalance += val;
		System.out.println("Non-compliant code : "+ currentBalance);
	}

	/**
	 * @category Compliant solution
	 * 
	 * @description: This compliant solution validates the floating-point input
	 *               before using it. The value is tested to ensure that it is
	 *               neither infinity, -infinity, nor NaN.
	 */
	void doDeposit(String userInput) {
		double val;
		try {
			val = Double.valueOf(userInput);
		} catch (NumberFormatException e) {
			throw new IllegalArgumentException("argument must be numeric value: " + userInput);
		}
		if (Double.isInfinite(val)) {
			throw new IllegalStateException("infinity error: " + userInput);
		}
		if (Double.isNaN(val)) {
			throw new IllegalStateException("NaN error: " + userInput);
		}
		if (val >= Double.MAX_VALUE - currentBalance) {
			throw new IllegalStateException("Overflow error");
		}
		currentBalance += val;
		System.out.println("Compliant solution : "+ currentBalance);
	}

	public static void main(String[] args) {
		CheckFloatingPoint object = new CheckFloatingPoint();
		System.out.println("initial value      : " + object.currentBalance);
		object._doDeposit("1.0000000000000000000001");
		object.doDeposit ("1.0000000000000000000001");
	}
}
