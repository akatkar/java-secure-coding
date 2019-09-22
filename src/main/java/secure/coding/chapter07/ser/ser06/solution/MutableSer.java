package secure.coding.chapter07.ser.ser06.solution;

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
 * @category Compliant solution
 *
 * @description This compliant solution creates a defensive copy of the mutable
 *              Date object date in the readObject() method.
 * 
 *              Note the use of field-by-field input and validation of incoming
 *              fields. Additionally, note that this compliant solution is
 *              insufficient to protect sensitive data (see rule SER03-J for
 *              additional information).
 */
public class MutableSer implements Serializable {
	private static final Date epoch = new Date(0);
	private Date date = null; // Mutable component

	public MutableSer(Date d) {
		// Constructor performs defensive copying
		date = new Date(d.getTime());
	}

	private void readObject(ObjectInputStream ois) throws IOException, ClassNotFoundException {
		ObjectInputStream.GetField fields = ois.readFields();
		Date inDate = (Date) fields.get("date", epoch);
		// Defensively copy the mutable component
		date = new Date(inDate.getTime());
		// Perform validation if necessary
	}
}
