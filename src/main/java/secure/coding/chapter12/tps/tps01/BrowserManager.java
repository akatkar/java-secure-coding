package secure.coding.chapter12.tps.tps01;

import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Collections;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @rule TPS01-J. Do not execute interdependent tasks in a bounded thread pool
 * 
 * @description Bounded thread pools allow the programmer to specify an upper
 *              limit on the number of threads that can concurrently execute in
 *              a thread pool. Programs must not use threads from a bounded
 *              thread pool to execute tasks that depend on the completion of
 *              other tasks in the pool.
 * 
 *              A form of deadlock called thread-starvation deadlock arises when
 *              all the threads executing in the pool are blocked on tasks that
 *              are waiting on an internal queue for an available thread in
 *              which to execute. Thread-starvation deadlock occurs when
 *              currently executing tasks submit other tasks to a thread pool
 *              and wait for them to complete and the thread pool lacks the
 *              capacity to accommodate all the tasks at once.
 * 
 *              This problem can be confusing because the program can function
 *              correctly when fewer threads are needed. The issue can be
 *              mitigated, in some cases, by choosing a larger pool size.
 *              However, determining a suitable size may be difficult or even
 *              impossible.
 * 
 * @category Noncompliant code (Subtasks)
 * 
 * @description This noncompliant code example contains a series of subtasks
 *              that execute in a shared thread pool. The BrowserManager class
 *              calls perUser(), which starts tasks that invoke perProfile().
 *              The perProfile() method starts tasks that invoke perTab(), and
 *              in turn, perTab starts tasks that invoke doSomething().
 *              BrowserManager then waits for the tasks to finish. The threads
 *              are allowed to invoke doSomething() in any order, provided that
 *              count correctly records the number of methods executed.
 * 
 *              Unfortunately, this program is susceptible to a
 *              thread-starvation deadlock. For example, if each of the five
 *              perUser tasks spawns five perProfile tasks, where each
 *              perProfile task spawns a perTab task, the thread pool will be
 *              exhausted, and perTab() will be unable to allocate any
 *              additional threads to invoke the doSomething() method.
 */
public final class BrowserManager {
	private final ExecutorService pool = Executors.newFixedThreadPool(10);
	private final int numberOfTimes;
	private static AtomicInteger count = new AtomicInteger(); // count = 0

	public BrowserManager(int n) {
		numberOfTimes = n;
	}

	public void perUser() {
		methodInvoker(numberOfTimes, "perProfile");
		pool.shutdown();
	}

	public void perProfile() {
		methodInvoker(numberOfTimes, "perTab");
	}

	public void perTab() {
		methodInvoker(numberOfTimes, "doSomething");
	}

	public void doSomething() {
		System.out.println(count.getAndIncrement());
	}

	public void methodInvoker(int n, final String method) {
		final BrowserManager manager = this;
		Callable<Object> callable = new Callable<Object>() {
			@Override
			public Object call() throws Exception {
				Method meth = manager.getClass().getMethod(method);
				return meth.invoke(manager);
			}
		};
		Collection<Callable<Object>> collection = Collections.nCopies(n, callable);
		try {
			Collection<Future<Object>> futures = pool.invokeAll(collection);
		} catch (InterruptedException e) {
			// Forward to handler
			Thread.currentThread().interrupt(); // Reset interrupted status
		}
		// . ..
	}

	public static void main(String[] args) {
		BrowserManager manager = new BrowserManager(5);
		manager.perUser();
	}
}