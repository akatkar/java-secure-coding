package secure.coding.chapter07.ser.ser03;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/**
 * @rule SER03-J. Do not serialize unencrypted, sensitive data
 * 
 * 
 * @description While serialization allows an object’s state to be saved as a
 *              sequence of bytes and then reconstituted at a later time, it
 *              provides no mechanism to protect the serialized data. An
 *              attacker who gains access to the serialized data can use it to
 *              discover sensitive information and to determine implementation
 *              details of the objects. An attacker can also modify the
 *              serialized data in an attempt to compromise the system when the
 *              malicious data is deserialized. Consequently, sensitive data
 *              that is serialized is potentially exposed, without regard to the
 *              access qualifiers (such as the private keyword) that were used
 *              in the original code. Moreover, the security manager cannot
 *              guarantee the integrity of the deserialized data.
 * 
 *              Examples of sensitive data that should never be serialized
 *              include cryptographic keys, digital certificates, and classes
 *              that may hold references to sensitive data at the time of
 *              serialization.
 * 
 * @category Noncompliant code
 *
 * @description The data members of class Point are private. Assuming the
 *              coordinates are sensitive, their presence in the data stream
 *              would expose them to malicious tampering.
 */
class Point {
	private double x;
	private double y;

	public Point(double x, double y) {
		this.x = x;
		this.y = y;
	}

	public Point() {
		// no-argument constructor
	}
}

public class Coordinates implements Serializable {
	public static void main(String[] args) {
		FileOutputStream fout = null;
		try {
			Point p = new Point(5, 2);
			fout = new FileOutputStream("point.ser");
			ObjectOutputStream oout = new ObjectOutputStream(fout);
			oout.writeObject(p);
		} catch (Throwable t) {
			// Forward to handler
		} finally {
			if (fout != null) {
				try {
					fout.close();
				} catch (IOException x) {
					// handle error
				}
			}
		}
	}
}
