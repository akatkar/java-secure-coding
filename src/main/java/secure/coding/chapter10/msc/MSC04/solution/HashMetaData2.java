package secure.coding.chapter10.msc.MSC04.solution;

import java.lang.ref.ReferenceQueue;
import java.lang.ref.WeakReference;
import java.net.InetAddress;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import javax.net.ssl.SSLSocket;

/**
 * @rule MSC04-J. Do not leak memory
 * 
 * @description Programming errors can prevent garbage collection of objects
 *              that are no longer relevant to program operation. The garbage
 *              collector collects only unreachable objects; consequently, the
 *              presence of reachable objects that remain unused indicates
 *              memory mismanagement. Consumption of all available heap space
 *              can cause an OutOfMemoryError, which usually results in program
 *              termination.
 * 
 *              Excessive memory leaks can lead to memory exhaustion and denial
 *              of service (DoS) and must be avoided.
 * 
 * @category Compliant solution (Reference Queue)
 * 
 * @description Reference queues provide notifications when a referent is
 *              garbage-collected. When the referent is garbage-collected, the
 *              HashMap continues to strongly reference both the WeakReference
 *              object and the corresponding map value (for each entry in the
 *              HashMap).
 * 
 *              When the garbage collector clears the reference to an object, it
 *              adds the corresponding WeakReference object to the reference
 *              queue. The WeakReference object remains in the reference queue
 *              until some operation is performed on the queue (such as a put()
 *              or remove()). After such an operation, the WeakReference object
 *              in the hash map is also garbage-collected. Alternatively, this
 *              two-step procedure can be carried out manually by using the
 *              following code:
 */
public class HashMetaData2 {

	private Map<WeakReference<SSLSocket>, InetAddress> m = Collections.synchronizedMap(new HashMap<>());

	private ReferenceQueue<WeakReference<SSLSocket>> queue = new ReferenceQueue<>();

	public void storeTempConnection(SSLSocket sock, InetAddress ip) {
		
		WeakReference wr = new WeakReference(sock, queue);
		
		// poll for dead entries before adding more
		while ((wr = (WeakReference) queue.poll()) != null) {
			// Removes the WeakReference object and the value (not the referent)
			m.remove(wr);
		}
		m.put(wr, ip);
	}

	public void removeTempConnection(SSLSocket sock) {
		m.remove(sock);
	}
}
