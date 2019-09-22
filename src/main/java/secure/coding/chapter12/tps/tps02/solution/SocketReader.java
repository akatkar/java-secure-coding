package secure.coding.chapter12.tps.tps02.solution;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
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
 * @category Compliant Solution (Submit Interruptible Tasks)
 * 
 * @description This compliant solution defines an interruptible version of the
 *              SocketReader class, which is instantiated and submitted to the
 *              thread pool.
 */
public final class SocketReader implements Runnable {
	private final SocketChannel sc;
	private final Object lock = new Object();

	public SocketReader(String host, int port) throws IOException {
		sc = SocketChannel.open(new InetSocketAddress(host, port));
	}

	@Override
	public void run() {
		ByteBuffer buf = ByteBuffer.allocate(1024);
		try {
			synchronized (lock) {
				while (!Thread.interrupted()) {
					sc.read(buf);
					// . ..
				}
			}
		} catch (IOException ie) {
			// Forward to handler
		}
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