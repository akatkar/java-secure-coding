package secure.coding.chapter05.obj.obj03.solution;

import java.util.ArrayList;
import java.util.List;

/**
 * @rule OBJ03-J. D o not mix generic with nongeneric raw types in new code
 * 
 * @category Compliant solution
 */
class ListAdder {

	private static <T> void addToList(List<T> list, T t) {
		list.add(t); // No warning generated
	}

	private static <T> void printOne(T type) {
		if (type instanceof Integer) {
			List<Integer> list = new ArrayList<Integer>();
			addToList(list, 1);
			System.out.println(list.get(0));
		} else if (type instanceof Double) {
			List<Double> list = new ArrayList<Double>();
			// This will not compile if addToList(list, 1) is used
			addToList(list, 1.0);
			System.out.println(list.get(0));
		} else {
			System.out.println("Cannot print in the supplied type");
		}
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