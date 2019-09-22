package secure.coding.chapter07.ser.ser11.solution;

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
 * @category Compliant solution
 *
 * @description This compliant solution protects against multiple initialization
 *              through the use of a Boolean flag that is set after the instance
 *              fields have been populated. It also protects against race
 *              conditions by synchronizing on a private lock object
 */
class ExternalObject {

	private final Object lock = new Object();
	private boolean initialized = false;

	private String name;
	private int UID;

	public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
		synchronized (lock) {
			if (!initialized) {
				// Read instance fields
				this.name = (String) in.readObject();
				this.UID = in.readInt();
				// . ..
				initialized = true;
			} else {
				throw new IllegalStateException();
			}
		}
	}
}