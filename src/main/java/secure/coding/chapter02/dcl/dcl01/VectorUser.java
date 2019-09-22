package secure.coding.chapter02.dcl.dcl01;

class Vector {
	private int val = 1;

	public boolean isEmpty() {
		// compares with 1 instead of 0
		return val == 1;
	}
	// other functionality is same as java.util.Vector
}

public class VectorUser {
	public static void main(String[] args) {
		Vector v = new Vector();
		if (v.isEmpty()) {
			System.out.println("Vector is empty");
		}	
	}
}