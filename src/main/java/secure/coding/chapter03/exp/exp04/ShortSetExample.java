package secure.coding.chapter03.exp.exp04;

import java.util.HashSet;

/**
 * @rule EXP04-J. Ensure that autoboxed values have the intended type
 * 
 * 
 * @description A boxing conversion converts the value of a primitive type to
 *              the corresponding value of the reference type. One example is
 *              the automatic conversion from int to Integer [ JLS 2005 ]. This
 *              is convenient in cases where an object parameter is required,
 *              such as with collection classes like Map and List. Another use
 *              case is for inter operation with methods that require their
 *              parameters to be object references rather than primitive types.
 *              Automatic conversion to the resulting wrapper types also reduces
 *              clutter in code.
 */
public class ShortSetExample {
	/**
	 * @category Noncompliant code
	 * 
	 * @description This noncompliant code example prints 100 as the size of the
	 *              HashSet rather than the expected result (1). The combination of
	 *              values of types short and int in the operation i-1 causes the
	 *              result to be autoboxed into an object of type Integer rather
	 *              than one of type Short. The HashSet contains only values of type
	 *              Short; the code attempts to remove objects of type Integer.
	 *              Consequently, the remove() operation accomplishes nothing.
	 */
	public static void main(String[] args) {
		HashSet<Short> s = new HashSet<Short>();
		for (short i = 0; i < 100; i++) {
			s.add(i);
			s.remove(i - 1); // tries to remove an Integer
		}
		System.out.println(s.size());
	}
}
