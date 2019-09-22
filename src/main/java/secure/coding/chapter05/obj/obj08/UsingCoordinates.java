package secure.coding.chapter05.obj.obj08;

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
 * @category Noncompliant code
 */
class Coordinates {

	private int x;
	private int y;

	/**
	 * @description This noncompliant code example exposes the private (x,y)
	 *              coordinates through the getPoint() method of the inner class.
	 *              Consequently, the UsingCoordinates class that belongs to the same
	 *              package can also access the coordinates.
	 */
	public class Point {
		public void getPoint() {
			System.out.println("(" + x + "," + y + ")");
		}
	}
}

public class UsingCoordinates {
	public static void main(String[] args) {
		Coordinates c = new Coordinates();
		Coordinates.Point p = c.new Point();
		p.getPoint();
	}
}
