package secure.coding.chapter07.ser.ser07;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * @rule SER07-J. Do not use the default serialized form for
 *       implementation-defined invariants
 * 
 * 
 * @description Serialization can be used maliciously, for example, to violate
 *              the intended invariants of a class. Deserialization is
 *              equivalent to object construction; consequently, all invariants
 *              enforced during object construction must also be enforced during
 *              deserialization.
 * 
 *              The default serialized form lacks any enforcement of class
 *              invariants; consequently, programs must not use the default
 *              serialized form for any class with implementation-defined
 *              invariants.
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
class NumberData extends Number {
	// . ..implement abstract Number methods, like Number.doubleValue(). ..
	private static final NumberData INSTANCE = new NumberData();

	public static NumberData getInstance() {
		return INSTANCE;
	}

	private NumberData() {
		// Perform security checks and parameter validation
	}

	protected int printData() {
		int data = 1000;
		// print data
		return data;
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
		NumberData sc = (NumberData) deepCopy(NumberData.getInstance());
		// Prints false; indicates new instance
		System.out.println(sc == NumberData.getInstance());
		System.out.println("Balance = " + sc.printData());
	}

	// This method should not be used in production code
	public static Object deepCopy(Object obj) {
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