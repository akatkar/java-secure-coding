package secure.coding.chapter04.num.num00;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @rule: NUM00-J. Detect or prevent integer overflow
 * 
 */
public class AtomicIntegerExample {
	private final AtomicInteger itemsInInventory = new AtomicInteger(100);

	/**
	 * @category NonCompliant Code
	 * 
	 * @description: This noncompliant code example uses an AtomicInteger, which is
	 *               part of the concurrency utilities. The concurrency utilities
	 *               lack integer overflow checks.
	 */
	public final void _nextItem() {
		itemsInInventory.getAndIncrement();
	}

	/**
	 * @category Compliant Solution
	 * 
	 * @description: This compliant solution uses the get() and compareAndSet()
	 *               methods provided by AtomicInteger to guarantee successful
	 *               manipulation of the shared value of itemsInInventory. This
	 *               solution has the following characteristics: 
	 *               - The number and order of accesses to itemsInInventory remain 
	 *               unchanged from the noncompliant code example.
	 *               - All operations on the value of itemsInInventory are performed 
	 *               on a temporary local copy of its value.
	 *               - The overflow check in this example is performed in inline code
	 *               rather than encapsulated in a method call. This is an acceptable
	 *               alternative implementation.
	 */
	public final void nextItem() {
		while (true) {
			int old = itemsInInventory.get();
			if (old == Integer.MAX_VALUE) {
				throw new ArithmeticException("Integer overflow");
			}
			int next = old + 1; // Increment
			if (itemsInInventory.compareAndSet(old, next)) {
				break;
			}
		} // end while
	} // end nextItem()
}
