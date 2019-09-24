package secure.coding.chapter12.tps.tps03.solution;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

import secure.coding.chapter11.err.err00.solution.ExceptionReporter;

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
 * @category Compliant Solution (Uncaught Exception Handler)
 * 
 * @description This compliant solution sets an uncaught exception handler on
 *              behalf of the thread pool. A ThreadFactory argument is passed to
 *              the thread pool during construction. The factory is responsible
 *              for creating new threads and setting the uncaught exception
 *              handler on their behalf. The Task class is unchanged from the
 *              noncompliant code example.
 * 
 *              The ExecutorService.submit() method can be used (in place of the
 *              execute() method) to submit a task to a thread pool and obtain a
 *              Future object. When the task is submitted via
 *              ExecutorService.submit(), thrown exceptions never reach the
 *              uncaught exception handler because the thrown exception is
 *              considered to be part of the return status and is consequently
 *              wrapped in an ExecutionException and rethrown by Future. get()
 */
final class PoolService2 {
	private static final ThreadFactory factory = new ExceptionThreadFactory(new MyExceptionHandler());
	private static final ExecutorService pool = Executors.newFixedThreadPool(10, factory);

	public void doSomething() {
		pool.execute(new Task()); // Task is a runnable class
	}

	public static class ExceptionThreadFactory implements ThreadFactory {
		private static final ThreadFactory defaultFactory = Executors.defaultThreadFactory();
		private final Thread.UncaughtExceptionHandler handler;

		public ExceptionThreadFactory(Thread.UncaughtExceptionHandler handler) {
			this.handler = handler;
		}

		@Override
		public Thread newThread(Runnable run) {
			Thread thread = defaultFactory.newThread(run);
			thread.setUncaughtExceptionHandler(handler);
			return thread;
		}
	}

	public static class MyExceptionHandler extends ExceptionReporter implements Thread.UncaughtExceptionHandler {
		// . ..
		@Override
		public void uncaughtException(Thread thread, Throwable t) {
			// Recovery or logging code
		}
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
