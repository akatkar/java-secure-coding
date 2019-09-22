package secure.coding.chapter03.exp.exp03.solution;

/**
 * @rule EXP03-J. Do not use the equality operators when comparing values of
 *       boxed primitives
 * 
 * @description The values of boxed primitives cannot be directly compared using
 *              the == and != operators because these operators compare object
 *              references rather than object values. Programmers can find this
 *              behavior surprising because autoboxing memoizes, or caches, the
 *              values of some primitive variables. Consequently, reference
 *              comparisons and value comparisons produce identical results for
 *              the subset of values that are memoized.
 */
public class NewBoolean {
	/**
	 * @category Compliant solution
	 * 
	 */
	public static void main(String[] args) {
		Boolean b1 = true; // Or Boolean.True
		Boolean b2 = true; // Or Boolean.True
		
		if (b1 == b2) { // always equal
			System.out.println("Will always be printed");
		}
	}
}
