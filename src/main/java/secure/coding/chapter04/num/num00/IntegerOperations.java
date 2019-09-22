package secure.coding.chapter04.num.num00;

import static secure.coding.chapter04.num.num00.PreConditionTesting.*;

import java.math.BigInteger;

/**
 * @rule: NUM00-J. Detect or prevent integer overflow
 * 
 * @description: Programs must not allow mathematical operations to exceed the
 *               integer ranges provided by their primitive integer data types.
 * 
 */
public class IntegerOperations {

	/**
	 * @category NonCompliant Code
	 * 
	 * @description: Operation in this noncompliant code example could result in an
	 *               overflow. When overflow occurs, the result will be incorrect.
	 */
	public static int _multAccum(int oldAcc, int newVal, int scale) {
		// May result in overflow
		return oldAcc + (newVal * scale);
	}

	/**
	 * @category Compliant Solution
	 * 
	 * @description: Solution with Precondition Test
	 */
	public static int multAccumWithPreConditionTest(int oldAcc, int newVal, int scale) throws ArithmeticException {
		return safeAdd(oldAcc, safeMultiply(newVal, scale));
	}

	/**
	 * @category Compliant Solution
	 * 
	 * @description: Solution with Upcasting
	 */
	public static int multAccumWithUpcasting(int oldAcc, int newVal, int scale) throws ArithmeticException {
		final long res = intRangeCheck(((long) oldAcc) + intRangeCheck((long) newVal * (long) scale));
		return (int) res; // safe down-cast
	}

	public static long intRangeCheck(long value) throws ArithmeticException {
		if ((value < Integer.MIN_VALUE) || (value > Integer.MAX_VALUE)) {
			throw new ArithmeticException("Integer overflow");
		}
		return value;
	}
	
	/**
	 * @category Compliant Solution
	 * 
	 * @description: Solution with BigInteger
	 */
	private static final BigInteger bigMaxInt = BigInteger.valueOf(Integer.MAX_VALUE);
	private static final BigInteger bigMinInt = BigInteger.valueOf(Integer.MIN_VALUE);

	public static BigInteger intRangeCheck(BigInteger val) throws ArithmeticException {
		if (val.compareTo(bigMaxInt) == 1 || val.compareTo(bigMinInt) == -1) {
			throw new ArithmeticException("Integer overflow");
		}
		return val;
	}

	public static int multAccumWithBigInteger(int oldAcc, int newVal, int scale) throws ArithmeticException {
		BigInteger product = BigInteger.valueOf(newVal).multiply(BigInteger.valueOf(scale));
		BigInteger res = intRangeCheck(BigInteger.valueOf(oldAcc).add(product));
		return res.intValue(); // safe conversion
	}
}
