package secure.coding.chapter12.tps.tps03;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

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
 * @category Noncompliant code (Abnormal Task Termination)
 * 
 * @description This noncompliant code example consists of the PoolService class
 *              that encapsulates a thread pool and a runnable Task class. The
 *              Task.run() method can throw runtime exceptions, such as
 *              NullPointerException.
 * 
 *              The task fails to notify the application when it terminates
 *              unexpectedly as a result of the runtime exception. Moreover, it
 *              lacks a recovery mechanism. Consequently, if Task were to throw
 *              a NullPointerException, the exception would be ignored.
 */
final class PoolService {

	private final ExecutorService pool = Executors.newFixedThreadPool(10);

	public void doSomething() {
		pool.execute(new Task());
	}
}

final class Task implements Runnable {
	@Override
	public void run() {
		// . ..
		throw new NullPointerException();
		// . ..
	}
}
