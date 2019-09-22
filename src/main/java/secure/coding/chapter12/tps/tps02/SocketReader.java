package secure.coding.chapter12.tps.tps02;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @rule TPS02-J. Ensure that tasks submitted to a thread pool are interruptible
 * 
 * @description Programs may submit only tasks that support interruption using
 *              Thread.interrupt() to thread pools that require the ability to
 *              shut down the thread pool or to cancel individual tasks within
 *              the pool. Programs must not submit tasks that lack interruption
 *              support to such thread pools. According to the Java API
 *              interface,the java.util.concurrent.ExecutorService.shutdownNow()
 *              method:
 * 
 * @category Noncompliant code (Shutting Down Thread Pools)
 * 
 * @description This noncompliant code example submits the SocketReader class as
 *              a task to the thread pool declared in PoolService.
 * 
 *              The shutdownNow() method may fail to shut down the thread pool
 *              because the task lacks support for interruption using the
 *              Thread.interrupt() method, and because the shutdown() method
 *              must wait until all executing tasks have finished.
 * 
 *              Similarly, tasks that use some mechanism other than
 *              Thread.interrupted() to determine when to shut down will be
 *              unresponsive to shutdown() or shutdownNow(). For instance, tasks
 *              that check a volatile flag to determine whether it is safe to
 *              shut down are unresponsive to these methods. Rule THI05-J
 *              provides more information on using a flag to terminate threads.
 */
public final class SocketReader implements Runnable { // Thread-safe class
	private final Socket socket;
	private final BufferedReader in;
	private final Object lock = new Object();

	public SocketReader(String host, int port) throws IOException {
		this.socket = new Socket(host, port);
		this.in = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
	}

	// Only one thread can use the socket at a particular time
	@Override
	public void run() {
		try {
			synchronized (lock) {
				readData();
			}
		} catch (IOException ie) {
			// Forward to handler
		}
	}

	public void readData() throws IOException {
		String string;
		try {
			while ((string = in.readLine()) != null) {
				// Blocks until end of stream (null)
			}
		} finally {
			shutdown();
		}
	}

	public void shutdown() throws IOException {
		socket.close();
	}
}

final class PoolService {
	private final ExecutorService pool;

	public PoolService(int poolSize) {
		pool = Executors.newFixedThreadPool(poolSize);
	}

	public void doSomething() throws InterruptedException, IOException {
		pool.submit(new SocketReader("somehost", 8080));
		// . ..
		List<Runnable> awaitingTasks = pool.shutdownNow();
	}

	public static void main(String[] args) throws InterruptedException, IOException {
		PoolService service = new PoolService(5);
		service.doSomething();
	}
}