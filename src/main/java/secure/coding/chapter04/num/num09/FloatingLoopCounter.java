package secure.coding.chapter04.num.num09;

/**
 * @rule: NUM09-J. Do not use floating-point variables as loop counters
 *
 * @description: Floating-point variables must not be used as loop counters.
 *               Limited-precision IEEE 754 floating-point types cannot
 *               represent
 */
public class FloatingLoopCounter {

	/**
	 * @category Non-compliant code
	 * 
	 * @description: This noncompliant code example uses a floating-point variable
	 *               as a loop counter. The decimal number 0.1 cannot be precisely
	 *               represented as a float or even as a double. Because 0.1f is
	 *               rounded to the nearest value that can be represented in the
	 *               value set of the float type, the actual quantity added to x on
	 *               each iteration is somewhat larger than 0.1. Consequently, the
	 *               loop executes only nine times and typically fails to produce
	 *               the expected output.
	 */
	static void _loop() {
		for (float x = 0.1f; x <= 1.0f; x += 0.1f) {
			System.out.println(x);
		}
	}

	/**
	 * @category Compliant solution
	 * 
	 * @description: This compliant solution uses an integer loop counter from which
	 *               the desired floating-point value is derived.
	 */
	static void loop() {
		for (int count = 1; count <= 10; count += 1) {
			float x = count / 10.0f;
			System.out.println(x);
		}
	}

	public static void main(String[] args) {
		System.out.println("Non Compliant");
		_loop();
		System.out.println("\nCompliant Solution");
		loop();
	}
}
