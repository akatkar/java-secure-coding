package secure.coding.chapter07.ser.ser00;

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
 * @category Noncompliant code
 *
 * @description This noncompliant code example implements a GameWeapon class
 *              with a serializable field called numOfWeapons and uses the
 *              default serialization form. Any changes to the internal
 *              representation of the class can break the existing serialized
 *              form.
 */
public class GameWeapon implements Serializable {
	int numOfWeapons = 10;

	public String toString() {
		return String.valueOf(numOfWeapons);
	}
}

