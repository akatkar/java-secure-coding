package secure.coding.chapter06.met.met01;

/**
 * @rule MET01-J. Never use assertions to validate method arguments
 * 
 * @description Never use assertions to validate arguments of public methods.
 *              Another problem with using assertions for argument checking is
 *              that erroneous arguments should result in an appropriate runtime
 *              exception (such as IllegalArgumentException,
 *              IndexOutOfBoundsException, or NullPointerException). An
 *              assertion failure will not throw an appropriate exception.
 */
public class ValidateWithAssertion {

	/**
	 * @category Noncompliant code
	 */
	public static int _getAbsAdd(int x, int y) {
		assert x != Integer.MIN_VALUE;
		assert y != Integer.MIN_VALUE;
		int absX = Math.abs(x);
		int absY = Math.abs(y);
		assert (absX <= Integer.MAX_VALUE - absY);
		return absX + absY;
	}

	/**
	 * @category Compliant solution
	 */
	public static int getAbsAdd(int x, int y) {
		if (x == Integer.MIN_VALUE || y == Integer.MIN_VALUE) {
			throw new IllegalArgumentException();
		}
		int absX = Math.abs(x);
		int absY = Math.abs(y);
		if (absX > Integer.MAX_VALUE - absY) {
			throw new IllegalArgumentException();
		}
		return absX + absY;
	}
}
