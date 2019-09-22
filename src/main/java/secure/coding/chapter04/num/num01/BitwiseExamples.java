package secure.coding.chapter04.num.num01;

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
 */
public class BitwiseExamples {

	/**
	 * @category NonCompliant Code
	 * 
	 * @description: The result is a prematurely optimized statement that assigns
	 *               the value 5x + 1 to x, which is what the programmer intended to
	 *               express.
	 */
	static int _leftShift() {
		int x = 50;
		x += (x << 2) + 1;
		return x;
	}

	/**
	 * @category Compliant Solution
	 */
	static int leftShift() {
		int x = 50;
		x = 5 * x + 1;
		return x;
	}

	/**
	 * @category NonCompliant Code
	 * 
	 * @description: The >>>= operator is a logical right shift; it fills the
	 *               leftmost bits with zeroes, regardless of the number’s original
	 *               sign. After execution of this code sequence, x contains a large
	 *               positive number (specifically, 0x3FFFFFF3). Using logical right
	 *               shift for division produces an incorrect result when the
	 *               dividend (x in this example) contains a negative value.
	 */
	static int _rightShift() {
		int x = -50;
		x >>>= 2;
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

		System.out.println(_leftShift());
		System.out.println(leftShift());

		System.out.println(_rightShift());
		System.out.println(rightShift());

	}
}
