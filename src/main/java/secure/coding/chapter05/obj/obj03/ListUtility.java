package secure.coding.chapter05.obj.obj03;

import java.util.ArrayList;
import java.util.List;

/**
 * @rule OBJ03-J. D o not mix generic with nongeneric raw types in new code
 * 
 * 
 * @description Generically typed code can be freely used with raw types when
 *              attempting to preserve compatibility between nongeneric legacy
 *              code and newer generic code. Using raw types with generic code
 *              causes most Java compilers to issue “unchecked” warnings but
 *              still compile
 */
public class ListUtility {

	/**
	 * @category Non-compliant code
	 * 
	 * @description This noncompliant code example compiles but produces an
	 *              unchecked warning because the raw type of the List.add() method
	 *              is used (the list parameter in the addToList() method) rather
	 *              than the parameterized type.
	 */
	private static void addToList(List list, Object obj) {
		list.add(obj); // Unchecked warning
	}

	public static void main(String[] args) {
		List<String> list = new ArrayList<String>();
		addToList(list, 1);
		System.out.println(list.get(0));
	}
}
