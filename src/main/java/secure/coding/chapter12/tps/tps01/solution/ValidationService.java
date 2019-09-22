package secure.coding.chapter12.tps.tps01.solution;

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
 * @category Compliant Solution (No Interdependent Tasks)
 * 
 * @description This compliant solution modifies the ValidateInput<V> class so
 *              that the SanitizeInput tasks are executed in the same threads as
 *              the ValidateInput tasks rather than in separate threads.
 *              Consequently, the ValidateInput and SanitizeInput tasks are
 *              independent; this eliminates their need to wait for each other
 *              to complete. The SanitizeInput class has also been modified to
 *              omit implementation of the Callable interface.
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
		// . ..
		for (int i = 0; i < inputs.length; i++) {
		// Don't pass-in thread pool
			results[i] = pool.submit(new ValidateInput<String>(inputs[i]));
		}
		for (int i = 0; i < inputs.length; i++) { // Aggregates the results
			sb.append(results[i].get());
		}
		return sb;
	}
}

// Does not use same thread pool
final class ValidateInput<V> implements Callable<V> {
	private final V input;

	ValidateInput(V input) {
		this.input = input;
	}

	@Override
	public V call() throws Exception {
		// If validation fails, throw an exception here
		return new SanitizeInput<V>().sanitize(input);
	}
}

final class SanitizeInput<V> { // No longer a Callable task
	public SanitizeInput() {
	}

	public V sanitize(V input) {
		// Sanitize input and return
		return input;
	}
}