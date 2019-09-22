package secure.coding.chapter07.ser.ser07.solution;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.NotSerializableException;
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
 * @category Compliant solution
 *
 * @description This compliant solution uses an enum and adds a custom
 *              readResolve() method that replaces the deserialized instance
 *              with a reference to the appropriate singleton from the current
 *              execution. More complicated cases may also require custom
 *              writeObject() or readObject() methods in addition to (or instead
 *              of) the custom readResolve() method.
 * 
 *              This compliant solution uses composition rather than extension
 *              of the Number class.
 */
enum NumberEnum {
	INSTANCE;

	NumberData number = new NumberData();

	// . ..
	protected final Object readResolve() throws NotSerializableException {
		return INSTANCE;
	}
}

class NumberData extends Number {
	private static NumberEnum instance = NumberEnum.INSTANCE;
	
	static NumberData getInstance() {
		return instance.number;
	}

	public NumberData() {
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