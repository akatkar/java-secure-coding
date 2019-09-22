package secure.coding.chapter07.ser.ser03.solution;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.NotSerializableException;
import java.io.ObjectInputStream;
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
 * @category Compliant solution
 *
 * @description Extending a class or interface that implements Serializable
 *              should be avoided whenever possible. When extension of such a
 *              class is necessary, inappropriate serialization of the subclass
 *              can be prohibited by throwing NotSerializableException from a
 *              custom writeObject() or readResolve() method, defined in the
 *              subclass SensitiveClass. Note that the custom writeObject() or
 *              readResolve() methods must be declared final to prevent a
 *              malicious subclass from overriding them.
 */
class SingletonClass extends Number {
	// ..implement abstract methods, such as Number.doubleValue(). ..
	private static final SingletonClass INSTANCE = new SingletonClass();

	public static SingletonClass getInstance() {
		return INSTANCE;
	}

	private SingletonClass() {
		// Perform security checks and parameter validation
	}

	private final Object readResolve() throws NotSerializableException {
		throw new NotSerializableException();
	}
	
	protected int getBalance() {
		int balance = 1000;
		return balance;
	}

	@Override
	public double doubleValue() {
		return 0;
	}

	@Override
	public float floatValue() {
		return 0;
	}

	@Override
	public int intValue() {
		return 0;
	}

	@Override
	public long longValue() {
		return 0;
	}
	
}

public class MaliciousForSingleton {
	public static void main(String[] args) {
		SingletonClass sc = (SingletonClass) deepCopy(SingletonClass.getInstance());
		// Prints false; indicates new instance
		System.out.println(sc == SingletonClass.getInstance());
		System.out.println("Balance = " + sc.getBalance());
	}

	// This method should not be used in production code
	static public Object deepCopy(Object obj) {
		try {
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			new ObjectOutputStream(bos).writeObject(obj);
			ByteArrayInputStream bin = new ByteArrayInputStream(bos.toByteArray());
			return new ObjectInputStream(bin).readObject();
		} catch (Exception e) {
			throw new IllegalArgumentException(e);
		}
	}
}
