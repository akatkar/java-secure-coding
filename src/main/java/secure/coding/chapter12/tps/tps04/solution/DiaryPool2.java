package secure.coding.chapter12.tps.tps04.solution;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @rule TPS04-J. Ensure ThreadLocal variables are reinitialized when using
 *       thread pools
 * 
 * @description The use of ThreadLocal objects requires care in classes whose
 *              objects are required to be executed by multiple threads in a
 *              thread pool. The technique of thread pooling allows threads to
 *              be reused to reduce thread creation overhead or when creating an
 *              unbounded number of threads can diminish the reliability of the
 *              system. Each task that enters the pool expects to see
 *              ThreadLocal objects in their initial, default state. However,
 *              when ThreadLocal objects are modified on a thread that is
 *              subsequently made available for reuse, the next task executing
 *              on the reused thread sees the state of the ThreadLocal objects
 *              as modified by the previous task that executed on that thread.
 * 
 *              Programs must ensure that each task that executes on a thread
 *              from a thread pool sees only correctly initialized instances of
 *              ThreadLocal objects.
 * 
 * @category Compliant Solution (try-finally Clause)
 * 
 * @description This compliant solution adds the removeDay() method to the Diary
 *              class and wraps the statements in the doSomething1() method of
 *              class DiaryPool in a try-finally block. The finally block
 *              restores the initial state of the thread-local days object by
 *              removing the current thread’s value from it.
 */
public final class DiaryPool2 {

	final int numOfThreads = 2; // Maximum number of threads allowed in pool
	final Executor exec;
	final Diary diary;

	DiaryPool2() {
		exec = new CustomThreadPoolExecutor(numOfThreads, numOfThreads, 10, TimeUnit.SECONDS,
				new ArrayBlockingQueue<Runnable>(10));
		diary = new Diary();
	}
	
	public void doSomething1() {
		exec.execute(new Runnable() {
			@Override
			public void run() {
				try {
					Diary.setDay(Day.FRIDAY);
					diary.threadSpecificTask();
				} finally {
					Diary.removeDay(); // Diary.setDay(Day.MONDAY)
					// can also be used
				}
			}
		});
	}

	public void doSomething2() {
		exec.execute(new Runnable() {
			@Override
			public void run() {
				diary.threadSpecificTask();
			}
		});
	}

	public static void main(String[] args) {
		DiaryPool2 dp = new DiaryPool2();
		dp.doSomething1(); // Thread 1, requires current day as Friday
		dp.doSomething2(); // Thread 2, requires current day as Monday
		dp.doSomething2(); // Thread 3, requires current day as Monday
	}
}

class CustomThreadPoolExecutor extends ThreadPoolExecutor {
	public CustomThreadPoolExecutor(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit,
			BlockingQueue<Runnable> workQueue) {
		super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue);
	}

	@Override
	public void beforeExecute(Thread t, Runnable r) {
		if (t == null || r == null) {
			throw new NullPointerException();
		}
		Diary.setDay(Day.MONDAY);
		super.beforeExecute(t, r);
	}
}
