package secure.coding.chapter03.exp.exp03;

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
	 * @category Noncompliant code
	 * 
	 * @description This noncompliant code example defines a Comparator with a
	 *              compare() method [ Bloch 2009]. The compare() method accepts two
	 *              boxed primitives as arguments. The == operator is used to
	 *              compare the two boxed primitives. In this context, however, it
	 *              compares the references to the wrapper objects rather than
	 *              comparing the values held in those objects.
	 */
	static Comparator<Integer> cmp = new Comparator<Integer>() {
		public int compare(Integer i, Integer j) {
			return i < j ? -1 : (i == j ? 0 : 1);
		}
	};
}
