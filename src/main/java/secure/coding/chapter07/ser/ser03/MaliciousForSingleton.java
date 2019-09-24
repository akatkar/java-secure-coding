package secure.coding.chapter07.ser.ser03;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * @rule SER03-J. Do not serialize unencrypted, sensitive data
 * 
 * 
 * @description While serialization allows an object�s state to be saved as a
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
 * @description In this noncompliant code example [ Bloch 2005a ], a class with
 *              singleton semantics uses the default serialized form, which
 *              fails to enforce any implementation-defined invariants.
 * 
 *              Consequently, malicious code can create a second instance even
 *              though the class should have only a single instance. For
 *              purposes of this example, we assume that the class contains only
 *              nonsensitive data.
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
