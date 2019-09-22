package secure.coding.chapter12.tps.tps03.solution;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

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
 * @category Compliant Solution (ThreadPoolExecutor Hooks)
 * 
 * @description Task-specific recovery or cleanup actions can be performed by
 *              overriding the afterExecute() hook of the
 *              java.util.concurrent.ThreadPoolExecutor class. This hook is
 *              called either when a task concludes successfully by executing
 *              all statements in its run() method or when the task halts
 *              because of an exception. Some implementations may fail to catch
 *              java.lang.Error. (See Bug ID 64502111 for more information [ SDN
 *              2008 ].) When using this approach, substitute the executor
 *              service with a custom ThreadPoolExecutor that overrides the
 *              afterExecute() hook
 * 
 *              The terminated() hook is called after all the tasks have
 *              finished executing and the Executor has terminated cleanly. This
 *              hook can be overridden to release resources acquired by the
 *              thread pool, much like a finally block.
 */
final class PoolService {
	// The values have been hard coded for brevity
	ExecutorService pool = new CustomThreadPoolExecutor(10, 10, 10, TimeUnit.SECONDS,
			new ArrayBlockingQueue<Runnable>(10));
	// . ..
}

class CustomThreadPoolExecutor extends ThreadPoolExecutor {
	// . .. Constructor . ..
	public CustomThreadPoolExecutor(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit,
			BlockingQueue<Runnable> workQueue) {
		super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue);
	}

	@Override
	public void afterExecute(Runnable r, Throwable t) {
		super.afterExecute(r, t);
		if (t != null) {
			// Exception occurred, forward to handler
		}

		// . .. Perform task-specific clean-up actions
	}

	@Override
	public void terminated() {
		super.terminated();
		// . .. Perform final clean-up actions
	}
}