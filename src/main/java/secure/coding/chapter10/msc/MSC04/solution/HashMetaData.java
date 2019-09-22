package secure.coding.chapter10.msc.MSC04.solution;

import java.net.InetAddress;
import java.util.Collections;
import java.util.Map;
import java.util.WeakHashMap;

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
 * @category Compliant solution (Weak References)
 * 
 * @description This compliant solution uses weak references to allow timely
 *              garbage collection..
 * 
 *              Strong references prevent the garbage collector from reclaiming
 *              objects that are stored inside container objects, such as in a
 *              Map. According to the Java API [ API 2006 ], weak reference
 *              objects “do not prevent their referents2 from being made
 *              finalizable, finalized, and then reclaimed.”
 * 
 *              One pruning technique is to call the get() method of WeakHashMap
 *              and remove any entry that corresponds to a null return value
 *              (polling). Use of reference queues is a more efficient method
 */
public class HashMetaData {

	private Map<SSLSocket, InetAddress> m = Collections.synchronizedMap(new WeakHashMap<>());

	public void storeTempConnection(SSLSocket sock, InetAddress ip) {
		m.put(sock, ip);
	}

	public void removeTempConnection(SSLSocket sock) {
		m.remove(sock);
	}
}
