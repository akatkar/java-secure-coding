package secure.coding.chapter04.num.num00;

public class PreConditionTesting {

	/**
	 * @category Compliant Solution
	 */
	static final int safeAdd(int left, int right) throws ArithmeticException {
		if (right > 0 ? left > Integer.MAX_VALUE - right : left < Integer.MIN_VALUE - right) {
			throw new ArithmeticException("Integer overflow");
		}
		return left + right;
	}

	/**
	 * @category Compliant Solution
	 */
	static final int safeSubtract(int left, int right) throws ArithmeticException {
		if (right > 0 ? left < Integer.MIN_VALUE + right : left > Integer.MAX_VALUE + right) {
			throw new ArithmeticException("Integer overflow");
		}
		return left - right;
	}

	/**
	 * @category Compliant Solution
	 */
	static final int safeMultiply(int left, int right) throws ArithmeticException {
		if (right > 0 ? left > Integer.MAX_VALUE / right || left < Integer.MIN_VALUE / right
				: (right < -1 ? left > Integer.MIN_VALUE / right || left < Integer.MAX_VALUE / right
						: right == -1 && left == Integer.MIN_VALUE)) {
			throw new ArithmeticException("Integer overflow");
		}
		return left * right;
	}

	/**
	 * @category Compliant Solution
	 */
	static final int safeDivide(int left, int right) throws ArithmeticException {
		if ((left == Integer.MIN_VALUE) && (right == -1)) {
			throw new ArithmeticException("Integer overflow");
		}
		return left / right;
	}

	/**
	 * @category Compliant Solution
	 */
	static final int safeNegate(int a) throws ArithmeticException {
		if (a == Integer.MIN_VALUE) {
			throw new ArithmeticException("Integer overflow");
		}
		return -a;
	}

	/**
	 * @category Compliant Solution
	 */
	static final int safeAbs(int a) throws ArithmeticException {
		if (a == Integer.MIN_VALUE) {
			throw new ArithmeticException("Integer overflow");
		}
		return Math.abs(a);
	}
}
