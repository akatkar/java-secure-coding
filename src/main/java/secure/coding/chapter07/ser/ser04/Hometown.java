package secure.coding.chapter07.ser.ser04;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.nio.file.AccessDeniedException;

/**
 * @rule SER04-J. Do not allow serialization and deserialization to bypass the
 *       security manager
 * 
 * 
 * @description Serialization and deserialization features can be exploited to
 *              bypass security manager checks. A serializable class may contain
 *              security manager checks in its constructors for various reasons,
 *              including preventing untrusted code from modifying the internal
 *              state of the class. Such security manager checks must be
 *              replicated anywhere a class instance can be constructed.
 * 
 *              For example, if a class enables a caller to retrieve sensitive
 *              internal state contingent upon security checks, those checks
 *              must be replicated during deserialization. This ensures that an
 *              attacker cannot extract sensitive information by deserializing
 *              the object.
 * 
 * @category Noncompliant code
 *
 * @description The data members of class Point are private. Assuming the
 *              coordinates are sensitive, their presence in the data stream
 *              would expose them to malicious tampering.
 */

public class Hometown implements Serializable {
	
	// Private internal state
	private String town;
	private static final String UNKNOWN = "UNKNOWN";

	void performSecurityManagerCheck() throws AccessDeniedException {
		System.out.println("Security check performed");
	}

	void validateInput(String newCC) throws IllegalArgumentException {
		System.out.println("Input validated");
	}

	public Hometown() throws AccessDeniedException {
		performSecurityManagerCheck();
		// Initialize town to default value
		town = UNKNOWN;
	}

	// Allows callers to retrieve internal state
	String getValue() throws AccessDeniedException {
		performSecurityManagerCheck();
		return town;
	}

	// Allows callers to modify (private) internal state
	public void changeTown(String newTown) throws AccessDeniedException {
		if (town.equals(newTown)) {
			// No change
			return;
		} else {
			performSecurityManagerCheck();
			validateInput(newTown);
			town = newTown;
		}
	}

	private void writeObject(ObjectOutputStream out) throws IOException {
		out.writeObject(town);
	}

	private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
		in.defaultReadObject();
		// If the deserialized name does not match
		// the default value normally
		// created at construction time, duplicate the checks
		if (!UNKNOWN.equals(town)) {
			validateInput(town);
		}
	}
}