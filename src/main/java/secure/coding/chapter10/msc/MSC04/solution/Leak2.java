package secure.coding.chapter10.msc.MSC04.solution;

import java.io.IOException;
import java.util.Vector;

/**
 * @rule MSC04-J. Do not leak memory
 * 
 * @description Programming errors can prevent garbage collection of objects
 *              that are no longer relevant to program operation. The garbage
 *              collector collects only unreachable objects; consequently, the
 *              presence of reachable objects that remain unused indicates
 *              memory mismanagement. Consumption of all available heap space
 *              can cause an OutOfMemoryError, which usually results in program
 *              termination.
 * 
 *              Excessive memory leaks can lead to memory exhaustion and denial
 *              of service (DoS) and must be avoided.
 * 
 * @category Compliant solution
 * 
 * @description Prefer the use of standard language semantics where possible.
 *              This compliant solution uses the vector.clear() method, which
 *              removes all elements.
 */
class Leak2 {

	static Vector<String> vector = new Vector<>();

	public void useVector(int count) {
		for (int n = 0; n < count; n++) {
			vector.add(Integer.toString(n));
		}
		// ...
		vector.clear();
	}

	public static void main(String[] args) throws IOException {
		Leak2 le = new Leak2();
		int i = 1;
		while (true) {
			System.out.println("Iteration: " + i);
			le.useVector(1);
			i++;
		}
	}
}
