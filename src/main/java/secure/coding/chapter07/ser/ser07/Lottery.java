package secure.coding.chapter07.ser.ser07;

import java.io.IOException;
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
 * @category Noncompliant code
 *
 * @description This noncompliant code example uses a custom-defined
 *              readObject() method but fails to perform input validation after
 *              deserialization. The design of the system requires the maximum
 *              ticket number of any lottery ticket to be 20,000. However, an
 *              attacker can manipulate the serialized array to generate a
 *              different number on deserialization.
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

	private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
		in.defaultReadObject();
	}

	public static void main(String[] args) {
		Lottery l = new Lottery(2);
		for (int i = 0; i < 10; i++) {
			l.roll();
			System.out.println(l.getTicket());
		}
	}

}