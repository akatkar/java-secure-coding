package secure.coding.chapter12.tps.tps00;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @rule TPS00-J. Use thread pools to enable graceful degradation of service
 *       during traffic bursts
 * 
 * @description Many programs must address the problem of handling a series of
 *              incoming requests. One simple concurrency strategy is the
 *              thread-per-message design pattern, which uses a new thread for
 *              each request. This pattern is generally preferred over
 *              sequential executions of time-consuming, I/O-bound,
 *              session-based, or isolated tasks.
 * 
 *              However, the pattern also introduces overheads not seen in
 *              sequential execution, including the time and resources required
 *              for thread creation and scheduling, for task processing, for
 *              resource allocation and deallocation, and for frequent context
 *              switching. Furthermore, an attacker can cause a denial of
 *              service (DoS) by overwhelming the system with too many requests
 *              all at once, causing the system to become unresponsive rather
 *              than degrading gracefully. From a safety perspective, one
 *              component can exhaust all resources because of an intermittent
 *              error, consequently starving all other components.
 * 
 *              Thread pools allow a system to limit the maximum number of
 *              simultaneous requests that it processes to a number that it can
 *              comfortably serve rather than terminating all services when
 *              presented with a deluge of requests. Thread pools overcome these
 *              issues by controlling the maximum number of worker threads that
 *              can execute concurrently. Each object that supports thread pools
 *              accepts a Runnable or Callable<T> task and stores it in a
 *              temporary queue until resources become available. Additionally,
 *              thread life-cycle management overhead is minimized because the
 *              threads in a thread pool can be reused and can be efficiently
 *              added to or removed from the pool.
 * 
 * @category Noncompliant code
 * 
 * @description This noncompliant code example demonstrates the
 *              thread-per-message design pattern. The RequestHandler class
 *              provides a public static factory method so that callers can
 *              obtain a RequestHandler instance. The handleRequest() method is
 *              subsequently invoked to handle each request in its own thread.
 */
class Helper {
	public void handle(Socket socket) {
		// . ..
	}
}

public final class RequestHandler {

	private final Helper helper = new Helper();
	private final ServerSocket server;

	private RequestHandler(int port) throws IOException {
		server = new ServerSocket(port);
	}

	public static RequestHandler newInstance() throws IOException {
		return new RequestHandler(0); // Selects next available port
	}

	public void handleRequest() {
		new Thread(new Runnable() {
			public void run() {
				try {
					helper.handle(server.accept());
				} catch (IOException e) {
					// Forward to handler
				}
			}
		}).start();
	}
}