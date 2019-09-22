package secure.coding.chapter12.tps.tps01;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

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
 * @category Noncompliant code
 * 
 * @description This noncompliant code example is vulnerable to
 *              thread-starvation deadlock. It consists of the ValidationService
 *              class, which performs various input validation tasks such as
 *              checking whether a user-supplied field exists in a back-end
 *              database.
 * 
 *              The fieldAggregator() method accepts a variable number of String
 *              arguments and creates a task corresponding to each argument to
 *              enable concurrent processing. The task performs input validation
 *              using the ValidateInput class
 * 
 *              In turn, the ValidateInput class attempts to sanitize the input
 *              by creating a subtask for each request using the SanitizeInput
 *              class. All tasks are executed in the same thread pool. The
 *              fieldAggregator() method blocks until all the tasks have
 *              finished executing and, when all results are available, returns
 *              the aggregated results as a StringBuilder object to the caller.
 * 
 *              Assume, for example, that the pool size is set to six. The
 *              ValidationService.fieldAggregator() method is invoked to
 *              validate six arguments; consequently, it submits six tasks to
 *              the thread pool. Each task submits a corresponding subtask to
 *              sanitize the input. The SanitizeInput subtasks must execute
 *              before the original six tasks can return their results. However,
 *              this is impossible because all six threads in the thread pool
 *              are blocked. Furthermore, the shutdown() method cannot shut down
 *              the thread pool when it contains active tasks. Thread-starvation
 *              deadlock can also occur when a single-threaded Executor is used,
 *              for example, when the caller creates several subtasks and waits
 *              for the results.
 */
public final class ValidationService {
	private final ExecutorService pool;

	public ValidationService(int poolSize) {
		pool = Executors.newFixedThreadPool(poolSize);
	}

	public void shutdown() {
		pool.shutdown();
	}

	public StringBuilder fieldAggregator(String... inputs) throws InterruptedException, ExecutionException {
		StringBuilder sb = new StringBuilder();
		// Stores the results
		Future<String>[] results = new Future[inputs.length];
		// Submits the tasks to thread pool
		for (int i = 0; i < inputs.length; i++) {
			results[i] = pool.submit(new ValidateInput<String>(inputs[i], pool));
		}
		for (int i = 0; i < inputs.length; i++) { // Aggregates the results
			sb.append(results[i].get());
		}
		return sb;
	}
}

final class ValidateInput<V> implements Callable<V> {
	private final V input;
	private final ExecutorService pool;

	ValidateInput(V input, ExecutorService pool) {
		this.input = input;
		this.pool = pool;
	}

	@Override
	public V call() throws Exception {
		// If validation fails, throw an exception here
		// Subtask
		Future<V> future = pool.submit(new SanitizeInput<V>(input));
		return (V) future.get();
	}
}

final class SanitizeInput<V> implements Callable<V> {
	private final V input;

	SanitizeInput(V input) {
		this.input = input;
	}

	@Override
	public V call() throws Exception {
		// Sanitize input and return
		return (V) input;
	}
}