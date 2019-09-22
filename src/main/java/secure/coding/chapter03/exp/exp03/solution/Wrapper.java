package secure.coding.chapter03.exp.exp03.solution;

import java.util.ArrayList;

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
public class Wrapper {
	/**
	 * @category Compliant solution
	 * 
	 */
	public static void main(String[] args) {
		// Create an array list of integers, where each element
		// is greater than 127
		ArrayList<Integer> list1 = new ArrayList<Integer>();
		for (int i = 0; i < 10; i++) {
			list1.add(i + 1000);
		}
		// Create another array list of integers, where each element
		// has the same value as the first list
		ArrayList<Integer> list2 = new ArrayList<Integer>();
		for (int i = 0; i < 10; i++) {
			list2.add(i + 1000);
		}
		// Count matching values
		int counter = 0;
		for (int i = 0; i < 10; i++) {
			if (list1.get(i).equals(list2.get(i))) { // uses 'equals()'
				counter++;
			}
		}
		// Print the counter: 0 in this example
		System.out.println(counter);
	}
}
