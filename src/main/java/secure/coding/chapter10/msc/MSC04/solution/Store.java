package secure.coding.chapter10.msc.MSC04.solution;

import java.util.HashMap;
import java.util.Map;

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
 * @category Compliant solution (Reduce Scope of Instance Field)
 * 
 * @description This compliant solution declares the HashMap as a local variable
 *              within the doSomething() method. The hm local variable is
 *              eliminated after the method returns. When the local variable
 *              holds the only reference to the HashMap, the garbage collector
 *              can reclaim its associated storage.
 */
public class Store {

	private void doSomething() {
		Map<Integer, String> hm = new HashMap<>();
		// hm is used only here and never referenced again
		hm.put(1, "java");
		// ...
	}
}
