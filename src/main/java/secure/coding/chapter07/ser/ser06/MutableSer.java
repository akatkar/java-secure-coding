package secure.coding.chapter07.ser.ser06;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.Date;

/**
 * @rule SER06-J. Make defensive copies of private mutable components during
 *       deserialization
 * 
 * 
 * @description Every serializable class that has private mutable instance
 *              variables must defensively copy them in the readObject() method.
 *              An attacker can tamper with the serialized form of such a class,
 *              appending extra references to the byte stream.
 * 
 *              When deserialized, this byte stream could allow the creation of
 *              a class instance whose internal variable references are
 *              controlled by the attacker. Consequently, the class instance can
 *              mutate and violate its class invariants.
 * 
 * @category Noncompliant code
 *
 * @description This noncompliant code example fails to defensively copy the
 *              mutable Date object date. An attacker might be able to create an
 *              instance of MutableSer whose date object contains a nefarious
 *              subclass of Date and whose methods can perform actions specified
 *              by an attacker. Any code that depends on the immutability of the
 *              subobject is vulnerable.
 */
public class MutableSer implements Serializable {
	private static final Date epoch = new Date(0);
	private Date date = null; // Mutable component

	public MutableSer(Date d) {
		// Constructor performs defensive copying
		date = new Date(d.getTime());
	}

	private void readObject(ObjectInputStream ois) throws IOException, ClassNotFoundException {
		ois.defaultReadObject();
		// Perform validation if necessary
	}
}
