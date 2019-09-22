package secure.coding.chapter03.exp.exp03.solution;

import java.util.Comparator;

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
public class ComparatorExample {
	/**
	 * @category Compliant solution
	 * 
	 * @description This compliant solution uses the comparison operators, <, >, <=,
	 *              or >=, because these cause automatic unboxing of the primitive
	 *              values. The == and != operators should not be used to compare
	 *              boxed primitives.
	 */
	static Comparator<Integer> cmp = new Comparator<Integer>() {
		public int compare(Integer i, Integer j) {
			return i < j ? -1 : (i > j ? 1 : 0);
		}
	};
	
	static Comparator<Integer> cmp2 = new Comparator<Integer>() {
		public int compare(Integer i, Integer j) {
			return Integer.compare(i, j);
		}
	};
	
}
