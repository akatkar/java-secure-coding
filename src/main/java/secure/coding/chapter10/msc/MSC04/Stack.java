package secure.coding.chapter10.msc.MSC04;

import java.util.EmptyStackException;

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
 * @category Noncompliant code (Member Object Leaks)
 * 
 * @description This noncompliant code example implements a stack data structure
 *              that continues to hold references to elements after they have
 *              been popped off the stack
 */
public class Stack {
	private Object[] elements;
	private int size = 0;

	public Stack(int initialCapacity) {
		this.elements = new Object[initialCapacity];
	}

	public void push(Object e) {
		ensureCapacity();
		elements[size++] = e;
	}

	public Object pop() { // This method causes memory leaks
		if (size == 0) {
			throw new EmptyStackException();
		}
		return elements[--size];
	}

	/*
	 * Ensure space for at least one more element, roughly doubling the capacity
	 * each time the array needs to grow.
	 */
	private void ensureCapacity() {
		if (elements.length == size) {
			Object[] oldElements = elements;
			elements = new Object[2 * elements.length + 1];
			System.arraycopy(oldElements, 0, elements, 0, size);
		}
	}
}
