package secure.coding.chapter05.obj.obj03.solution;

import java.util.ArrayList;
import java.util.List;

/**
 * @rule OBJ03-J. D o not mix generic with nongeneric raw types in new code
 * 
 * 
 * @description Generically typed code can be freely used with raw types when
 *              attempting to preserve compatibility between nongeneric legacy
 *              code and newer generic code. Using raw types with generic code
 *              causes most Java compilers to issue �unchecked� warnings but
 *              still compile
 */
public class ListUtility {

	/**
	 * @category Compliant solution
	 * 
	 * @description This compliant solution enforces type safety by changing the
	 *              addToList() method signature to enforce proper type checking.
	 */
	private static void addToList(List<String> list, String str) {
		list.add(str); // No warning generated
	}

	public static void main(String[] args) {
		List<String> list = new ArrayList<String>();
		addToList(list, "1");
		System.out.println(list.get(0));
	}
}
