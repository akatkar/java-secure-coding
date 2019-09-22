package secure.coding.chapter10.msc.MSC04.solution;

import java.lang.ref.ReferenceQueue;
import java.lang.ref.SoftReference;
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
 * @category Compliant solution (Soft References)
 * 
 * @description Use of soft references is also permitted. Soft references
 *              guarantee that the referent will be reclaimed before an
 *              OutOfMemoryError occurs and also that the referent will remain
 *              live until memory begins to run out.
 * 
 *              Weak references are garbage-collected more aggressively than
 *              soft references. Consequently, weak references should be
 *              preferred in applications where efficient memory usage is
 *              critical, and soft references should be preferred in
 *              applications that rely heavily on caching.
 */
public class HashMetaData3 {

	private Map<SoftReference<SSLSocket>, InetAddress> m = Collections
			.synchronizedMap(new HashMap<SoftReference<SSLSocket>, InetAddress>());

	ReferenceQueue queue = new ReferenceQueue();

	public void storeTempConnection(SSLSocket sock, InetAddress ip) {

		SoftReference<SSLSocket> sr = new SoftReference<SSLSocket>(sock, queue);
		while ((sr = (SoftReference) queue.poll()) != null) {
			// Removes the WeakReference object and the value (not the referent)
			m.remove(sr);
		}
		m.put(sr, ip);
	}

	public void removeTempConnection(SSLSocket sock) {
		m.remove(sock);
	}
}