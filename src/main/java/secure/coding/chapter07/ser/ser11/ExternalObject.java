package secure.coding.chapter07.ser.ser11;

import java.io.IOException;
import java.io.ObjectInput;

/**
 * @rule SER11-J. Prevent overwriting of externalizable objects
 * 
 * 
 * @description Classes that implement the Externalizable interface must provide
 *              the readExternal() and writeExternal() methods. These methods
 *              have package-private or public access, and so they can be called
 *              by trusted and untrusted code alike. Consequently, programs must
 *              ensure that these methods execute only when intended and that
 *              they cannot overwrite the internal state of objects at arbitrary
 *              points during program execution.
 * 
 * @category Noncompliant code
 *
 * @description This noncompliant code example allows any caller to reset the
 *              value of the object at any time because the readExternal()
 *              method is necessarily declared to be public and lacks protection
 *              against hostile callers.
 */
class ExternalObject {
	
	private String name;
	private int UID;
	
	public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
		// Read instance fields
		this.name = (String) in.readObject();
		this.UID = in.readInt();
		// . ..
	}
}