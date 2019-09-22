package secure.coding.chapter12.tps.tps04;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

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
 * @category Noncompliant code
 * 
 * @description This noncompliant code example consists of an enumeration of
 *              days (Day) and two classes (Diary and DiaryPool). The Diary
 *              class uses a ThreadLocal variable to store thread- specific
 *              information, such as each task’s current day. The initial value
 *              of the current day is Monday; this can be changed later by
 *              invoking the setDay() method. The class also contains a
 *              threadSpecificTask() instance method that performs a
 *              thread-specific task.
 * 
 *              The DiaryPool class consists of the doSomething1() and
 *              doSomething2() methods that each start a thread. The
 *              doSomething1() method changes the initial (default) value of the
 *              day to Friday and invokes threadSpecificTask(). On the other
 *              hand, doSomething2() relies on the initial value of the day
 *              (Monday) and invokes threadSpecificTask(). The main() method
 *              creates one thread using doSomething1() and two more using
 *              doSomething2().
 * 
 *              The DiaryPool class creates a thread pool that reuses a fixed
 *              number of threads operating off a shared, unbounded queue. At
 *              any point, no more than numOfThreads threads are actively
 *              processing tasks. If additional tasks are submitted when all
 *              threads are active, they wait in the queue until a thread is
 *              available. The thread-local state of the thread persists when a
 *              thread is recycled.
 * 
 *              The following table shows a possible execution order:
 * 
 *              Time Task Pool Thread Submitted by Method Day
 * 
 *              1 t1 1 doSomething1() Friday
 * 
 *              2 t2 2 doSomething2() Monday
 * 
 *              3 t3 1 doSomething2() Friday
 * 
 *              In this execution order, it is expected that the two tasks (t2
 *              and t3) started from doSomething2() would observe the current
 *              day as Monday. However, because pool thread 1 is reused, t 3
 *              observes the day to be Friday.
 */
public final class DiaryPool {

	final int numOfThreads = 2; // Maximum number of threads allowed in pool
	final Executor exec;
	final Diary diary;

	DiaryPool() {
		exec = (Executor) Executors.newFixedThreadPool(numOfThreads);
		diary = new Diary();
	}

	public void doSomething1() {
		exec.execute(new Runnable() {
			@Override
			public void run() {
				diary.setDay(Day.FRIDAY);
				diary.threadSpecificTask();
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
		DiaryPool dp = new DiaryPool();
		dp.doSomething1(); // Thread 1, requires current day as Friday
		dp.doSomething2(); // Thread 2, requires current day as Monday
		dp.doSomething2(); // Thread 3, requires current day as Monday
	}
}

enum Day {
	MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY, SATURDAY, SUNDAY;
}

final class Diary {
	private static final ThreadLocal<Day> days = new ThreadLocal<Day>() {
		// Initialize to Monday
		protected Day initialValue() {
			return Day.MONDAY;
		}
	};

	private static Day currentDay() {
		return days.get();
	}

	public static void setDay(Day newDay) {
		days.set(newDay);
	}

	// Performs some thread-specific task
	public void threadSpecificTask() {
		// Do task . ..
	}
}