package secure.coding.chapter04.num.num01.solution;

/**
 * @rule: NUM01-J. Do not perform bitwise and arithmetic operations on the same
 *        data
 * 
 * 
 * @description: Performing bitwise and arithmetic operations on the same data
 *               indicates confusion regarding the purpose of the data stored in
 *               the variable. Unfortunately, bitwise operations are frequently
 *               performed on arithmetic values as a form of premature
 *               optimization. Bitwise operators include the unary operator ~
 *               and the binary operators <<, >>, >>>, &, ^, and |. Although
 *               such operations are valid and will compile, they can reduce
 *               code readability.
 * 
 * @category Compliant Solution
 */

public class BitwiseExamples {

	static int leftShift() {
		int x = 50;
		x = 5 * x + 1;
		return x;
	}

	/**
	 * @category Compliant Solution
	 */
	static int rightShift() {
		int x = -50;
		x /= 4;
		return x;
	}

	public static void main(String[] args) {

		System.out.println(leftShift());

		System.out.println(rightShift());
	}
}
