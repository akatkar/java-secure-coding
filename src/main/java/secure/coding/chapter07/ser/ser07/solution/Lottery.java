package secure.coding.chapter07.ser.ser07.solution;

import java.io.IOException;
import java.io.InvalidObjectException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.security.SecureRandom;

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
 * @description This compliant solution performs field-by-field validation by
 *              reading all fields of the object using the readFields() method
 *              and ObjectInputStream. GetField() constructor. The value for
 *              each field must be fully validated before it is assigned to the
 *              object under construction. For more complicated invariants, this
 *              may require reading multiple field values into local variables
 *              to enable checks that depend on combinations of field values.
 */
public class Lottery implements Serializable {

	private int ticket = 1;
	private SecureRandom draw = new SecureRandom();

	public Lottery(int ticket) {
		this.ticket = (int) (Math.abs(ticket % 20000) + 1);
	}

	public int getTicket() {
		return this.ticket;
	}

	public int roll() {
		this.ticket = (int) ((Math.abs(draw.nextInt()) % 20000) + 1);
		return this.ticket;
	}

	private synchronized void readObject(java.io.ObjectInputStream s) throws IOException, ClassNotFoundException {
		ObjectInputStream.GetField fields = s.readFields();
		int ticket = fields.get("ticket", 0);
		if (ticket > 20000 || ticket <= 0) {
			throw new InvalidObjectException("Not in range!");
		}
		// Validate draw
		this.ticket = ticket;
	}

	public static void main(String[] args) {
		Lottery l = new Lottery(2);
		for (int i = 0; i < 10; i++) {
			l.roll();
			System.out.println(l.getTicket());
		}
	}

}