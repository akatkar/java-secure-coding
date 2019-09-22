package secure.coding.chapter10.msc.MSC04;

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
 * @category Noncompliant code (Strong References)
 * 
 * @description A common variation of the obsolete object fallacy is the
 *              unintentional retention of objects in collections such as maps.
 *              In this noncompliant code example, a server maintains temporary
 *              metadata about all committed secure connections.
 * 
 *              It is possible to close a socket without removing it from this
 *              map. Consequently, this map may contain dead sockets until
 *              removeTempConnection() is invoked on them. In the absence of
 *              notification logic, it is impossible to determine when to call
 *              removeTempConnection(). Moreover, nullifying original objects or
 *              referents (Socket connections) is unwieldy.
 */
public class HashMetaData {
	
	private Map<SSLSocket, InetAddress> m = Collections.synchronizedMap(new HashMap<>());

	public void storeTempConnection(SSLSocket sock, InetAddress ip) {
		m.put(sock, ip);
	}

	public void removeTempConnection(SSLSocket sock) {
		m.remove(sock);
	}
}
