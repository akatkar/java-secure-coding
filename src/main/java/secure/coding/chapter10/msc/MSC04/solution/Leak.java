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
 * @description This compliant solution corrects the mistake by changing the
 *              loop condition to n >= 0.
 */
class Leak {

	static Vector<String> vector = new Vector<>();

	public void useVector(int count) {
		for (int n = 0; n < count; n++) {
			vector.add(Integer.toString(n));
		}
		// ...
		for (int n = count - 1; n >= 0; n--) { // Free the memory
			vector.removeElementAt(n);
		}
	}

	public static void main(String[] args) throws IOException {
		Leak le = new Leak();
		int i = 1;
		while (true) {
			System.out.println("Iteration: " + i);
			le.useVector(1);
			i++;
		}
	}
}
