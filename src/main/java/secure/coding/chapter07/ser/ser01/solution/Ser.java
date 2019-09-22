package secure.coding.chapter07.ser.ser01.solution;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/**
 * @rule SER01-J. Do not deviate from the proper signatures of serialization
 *       methods
 * 
 * 
 * @description Classes that require special handling during object
 *              serialization and deserialization must implement special methods
 *              with exactly the following signatures [ API 2006 ]:
 * 
 *              private void writeObject(java.io.ObjectOutputStream out) throws IOException;
 * 
 *              private void readObject(java.io.ObjectInputStream in) throws IOException, ClassNotFoundException;
 * 
 *              private void readObjectNoData() throws ObjectStreamException;
 * 
 *              Note that these methods must be declared private for any
 *              serializable class. Serializable classes may also implement the
 *              readResolve() and writeReplace() methods. According to the
 *              Serialization Specification [ Sun 2006 ], readResolve() and
 *              writeReplace() method documentation:
 * 
 * @category Compliant solution
 *
 * @description This compliant solution declares the readObject() and
 *              writeObject() methods private and nonstatic to limit their
 *              accessibility.
 */
public class Ser implements Serializable {
	private final long serialVersionUID = 123456789;

	private Ser() {
		// initialize
	}

	private void writeObject(final ObjectOutputStream stream) throws IOException {
		stream.defaultWriteObject();
	}

	private void readObject(final ObjectInputStream stream) throws IOException, ClassNotFoundException {
		stream.defaultReadObject();
	}
}
