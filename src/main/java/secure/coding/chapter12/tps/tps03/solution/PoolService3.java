package secure.coding.chapter12.tps.tps03.solution;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * @rule TPS03-J. Ensure that tasks executing in a thread pool do not fail
 *       silently
 * 
 * @description All tasks in a thread pool must provide a mechanism for
 *              notifying the application if they terminate abnormally. Failure
 *              to do so cannot cause resource leaks because the threads in the
 *              pool are still recycled, but it makes failure diagnosis
 *              extremely difficult or impossible.
 * 
 *              The best way to handle exceptions at the application level is to
 *              use an exception handler. The handler can perform diagnostic
 *              actions, clean up and shut down the JVM, or simply log the
 *              details of the failure.
 * 
 * @category Compliant Solution (Future<V> and submit())
 * 
 * @description This compliant solution invokes the ExecutorService.submit()
 *              method to submit the task so that a Future object can be
 *              obtained. It uses the Future object to let the task re-throw the
 *              exception so that it can be handled locally.
 */
final class PoolService3 {
	private final ExecutorService pool = Executors.newFixedThreadPool(10);

	public void doSomething() {
		Future<?> future = pool.submit(new Task());
		// . ..
		try {
			future.get();
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt(); // Reset interrupted status
		} catch (ExecutionException e) {
			Throwable exception = e.getCause();
			// Forward to exception reporter
		}
	}
}

//final class Task implements Runnable {
//	@Override
//	public void run() {
//		// . ..
//		throw new NullPointerException();
//		// . ..
//	}
//}
