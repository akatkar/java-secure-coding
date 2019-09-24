package secure.coding.chapter03.exp.exp04.solution;

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
	 * @category Compliant solution
	 * 
	 * @description Objects removed from a collection must share the type of the
	 *              elements of the collection. Numeric promotion and autoboxing can
	 *              produce unexpected object types. This compliant solution uses an
	 *              explicit cast to short that matches the intended boxed type
	 */
	public static void main(String[] args) {
		HashSet<Short> s = new HashSet<>();
		for (short i = 0; i < 100; i++) {
			s.add(i);
			s.remove((short)(i - 1)); // tries to remove an Integer
		}
		System.out.println(s.size());
	}
}
