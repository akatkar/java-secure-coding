package secure.coding.chapter03.exp.exp05;

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
	 * @category Noncompliant code
	 * 
	 * @description This noncompliant code example shows how side effects in
	 *              expressions can lead to unanticipated outcomes. The programmer
	 *              intends to write access control logic based on different
	 *              threshold levels. Each user has a rating that must be above the
	 *              threshold to be granted access. As shown, a simple method can
	 *              calculate the rating. The get() method is expected to return a
	 *              nonzero factor for users who are authorized and a zero value for
	 *              those who are unauthorized.
	 */

	public static void main(String[] args) {
		int number = 17;
		int[] threshold = new int[20];
		threshold[0] = 10;
		number = (number > threshold[0] ? 0 : -2) + ((31 * ++number) * (number = get()));
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
