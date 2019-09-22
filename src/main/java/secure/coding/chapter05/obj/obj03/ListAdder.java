package secure.coding.chapter05.obj.obj03;

import java.util.ArrayList;
import java.util.List;

/** 
 * @rule OBJ03-J. D o not mix generic with nongeneric raw types in new code
 * 
 * @category Non-compliant code
 */
class ListAdder {

	@SuppressWarnings("unchecked")
	private static void addToList(List list, Object obj) {
		list.add(obj); // Unchecked warning
	}

	private static <T> void printOne(T type) {
		if (!(type instanceof Integer || type instanceof Double)) {
			System.out.println("Cannot print in the supplied type");
		}
		List<T> list = new ArrayList<T>();
		addToList(list, 1);
		System.out.println(list.get(0));
	}

	public static void main(String[] args) {
		double d = 1;
		int i = 1;
		System.out.println(d);
		ListAdder.printOne(d);
		System.out.println(i);
		ListAdder.printOne(i);
	}
}