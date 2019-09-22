package secure.coding.chapter10.msc.MSC01;

/**
 * 
 * @rule MSC01-J. Do not use an empty infinite loop
 * 
 * @description An infinite loop with an empty body consumes CPU cycles but does
 *              nothing. Optimizing compilers and just-in-time systems (JITs)
 *              are permitted to (perhaps unexpectedly) remove such a loop.
 *              Consequently, programs must not include infinite loops with
 *              empty bodies.
 */
class Loop {

	/**
	 * @category Noncompliant code
	 * 
	 * @description This noncompliant code example implements an idle task that
	 *              continuously executes a loop without executing any instructions
	 *              within the loop. An optimizing compiler or JIT could remove the
	 *              while loop in this example.
	 */
	public int nop1() {
		while (true) {
		}
	}

	/**
	 * @category Compliant solution (Thread.sleep())
	 * 
	 * @description This compliant solution avoids use of a meaningless infinite
	 *              loop by invoking Thread.sleep() within the while loop. The loop
	 *              body contains semantically meaningful operations and
	 *              consequently cannot be optimized away.
	 */
	public final int DURATION = 10000; // in milliseconds

	public void nop2() throws InterruptedException {
		while (true) {
			// Useful operations
			Thread.sleep(DURATION);
		}
	}

	/**
	 * @category Compliant solution (yield())
	 * 
	 * @description This compliant solution invokes Thread.yield(), which causes the
	 *              thread running this method to consistently defer to other
	 *              threads
	 */
	public void nop3() {
		while (true) {
			// Useful operations
			Thread.yield();
		}
	}
}
