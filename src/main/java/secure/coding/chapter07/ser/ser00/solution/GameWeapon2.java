package secure.coding.chapter07.ser.ser00.solution;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.ObjectStreamField;
import java.io.Serializable;

/**
 * @rule SER00-J. Maintain serialization compatibility during class evolution
 * 
 * 
 * @description Once an object of a particular class has been serialized, future
 *              refactoring of the class’s code often becomes problematic.
 *              Specifically, existing serialized forms (encoded
 *              representations) become part of the object’s published API and
 *              must be supported for an indefinite period. This can be
 *              troublesome from a security perspective; not only does it
 *              promote dead code, it also forces the provider to maintain a
 *              compatible code base for the lifetime of their products.
 * 
 *              Classes that implement Serializable without overriding its
 *              functionality are said to be using the default serialized form.
 *              In the event the class changes, byte streams produced by users
 *              of old versions of the class become incompatible with the new
 *              implementation. Programs must maintain serialization
 *              compatibility during class evolution. An acceptable approach is
 *              the use of a custom serialized form, which relieves the
 *              implementer of the necessity to maintain the original serialized
 *              form and the corresponding version of the class in addition to
 *              the newly evolved version.
 * 
 * @category Compliant solution
 *
 * @description Ideally, Serializable should only be implemented for stable
 *              classes. One way to maintain the original serialized form and
 *              allow the class to evolve is to use custom serialization with
 *              the help of serialPersistentFields. The static and transient
 *              qualifiers specify which fields should not be serialized,
 *              whereas the serialPersistentFields field specifies which fields
 *              should be serialized. It also relieves the class from defining
 *              the serializable field within the class implementation,
 *              decoupling the current implementation from the overall logic.
 *              New fields can easily be added without breaking compatibility
 *              across releases.
 */
class WeaponStore implements Serializable {
	int numOfWeapons = 10; // Total number of weapons
}

public class GameWeapon2 implements Serializable {
	
	WeaponStore ws = new WeaponStore();
	private static final ObjectStreamField[] serialPersistentFields = {
			new ObjectStreamField("ws", WeaponStore.class) };

	private void readObject(ObjectInputStream ois) throws IOException {
		try {
			ObjectInputStream.GetField gf = ois.readFields();
			this.ws = (WeaponStore) gf.get("ws", ws);
		} catch (ClassNotFoundException e) {
			/* Forward to handler */ }
	}

	private void writeObject(ObjectOutputStream oos) throws IOException {
		ObjectOutputStream.PutField pf = oos.putFields();
		pf.put("ws", ws);
		oos.writeFields();
	}

	public String toString() {
		return String.valueOf(ws);
	}
}