package secure.coding.chapter05.obj.obj08.solution;

/**
 * @rule OBJ08-J. Do not expose private members of an outer class from within a
 *       nested class
 * 
 * @description The use of a nested class is error prone unless the semantics
 *              are well understood. A common notion is that only the nested
 *              class may access the contents of the outer class. Not only does
 *              the nested class have access to the private fields of the outer
 *              class, the same fields can be accessed by any other class within
 *              the package when the nested class is declared public or if it
 *              contains public methods or constructors. As a result, the nested
 *              class must not expose the private members of the outer class to
 *              external classes or packages.
 *
 * @category Compliant solution
 */
class Coordinates {

	private int x;
	private int y;

	/**
	 * @description Use the private access specifier to hide the inner class and all
	 *              contained methods and constructors.
	 */
	private class Point {
		private void getPoint() {
			System.out.println("(" + x + "," + y + ")");
		}
	}
}

public class UsingCoordinates {
	public static void main(String[] args) {
		Coordinates c = new Coordinates();
//		Coordinates.Point p = c.new Point();
//		p.getPoint();
	}
}
