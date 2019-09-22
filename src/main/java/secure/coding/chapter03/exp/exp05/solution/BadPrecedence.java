package secure.coding.chapter03.exp.exp05.solution;

/**
 * @rule EEXP05-J. Do not write more than once to the same variable within an
 *       expression
 * 
 * 
 * @description The Java programming language guarantees that the operands of
 *              operators appear to be evaluated in a specific evaluation order,
 *              namely, from left to right. Section 15.7.3, “Evaluation Respects
 *              Parentheses and Precedence” adds: Java programming language
 *              implementations must respect the order of evaluation as
 *              indicated explicitly by parentheses and implicitly by operator
 *              precedence.
 */
public class BadPrecedence {
	/**
	 * @category Compliant solution
	 * 
	 * @description This compliant solution uses equivalent code with no side
	 *              effects and performs not more than one write per expression. The
	 *              resulting expression can be reordered without concern for the
	 *              evaluation order of the component expressions, making the code
	 *              easier to understand and maintain.
	 */

	public static void main(String[] args) {

		int number = 17;
		int[] threshold = new int[20];
		final int authnum = get();

		threshold[0] = 10;
		number = ((31 * (number + 1)) * authnum) + (authnum > threshold[0] ? 0 : -2);
		// …
		if (number == 0) {
			System.out.println("Access granted");
		} else {
			System.out.println("Denied access"); // number = -2
		}
	}

	public static int get() {
		int number = 0;
		// Assign number to non zero value if authorized else 0
		return number;
	}
}
