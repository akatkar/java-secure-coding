package secure.coding.chapter04.num.num05;

/**
 * @rule: NUM05-J. Do not use denormalized numbers
 *
 */
public class DenormalizedNumbers {

	/**
	 * @category Non-compliant code
	 * 
	 * @description: This noncompliant code example attempts to reduce a
	 *               floating-point number to a denormalized value and then restore
	 *               the value.
	 */
	static strictfp void _calculate() {
		float x = 1 / 3.0f;
		System.out.println("Original : " + x);
		x = x * 7e-45f;
		System.out.println("Denormalized: " + x);
		x = x / 7e-45f;
		System.out.println("Restored : " + x);
	}

	/**
	 * @category Compliant solution
	 * 
	 * @description: Do not use code that could use denormalized numbers. When
	 *               calculations using float produce denormalized numbers, use of
	 *               double can provide sufficient precision..
	 */
	static strictfp void calculate() {
		double x = 1/3.0;
		System.out.println("Original : " + x);
		x = x * 7e-45;
		System.out.println("Denormalized: " + x);
		x = x / 7e-45;
		System.out.println("Restored : " + x);
	}

	public static void main(String[] args) {
		_calculate();
		System.out.println();
		calculate();
	}
}
