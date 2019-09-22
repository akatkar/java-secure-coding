package secure.coding.chapter10.msc.MSC04;

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
 * @category Noncompliant code (Nonlocal Instance Field)
 * 
 * @description This noncompliant code example declares and allocates a HashMap
 *              instance field that is used only in the doSomething() method.
 */
public class Store {

	private Map<Integer, String> hm = new HashMap<>();

	private void doSomething() {
		// hm is used only here and never referenced again
		hm.put(1, "java");
		// ...
	}
}
