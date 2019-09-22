package secure.coding.chapter07.ser.ser00.solution;

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
 * @description In this solution, the class has an explicit serialVersionUID
 *              that contains a number unique to this version of the class. The
 *              JVM will make a good-faith effort to deserialize any serialized
 *              object with the same class name and version ID.
 */
public class GameWeapon implements Serializable {
	private static final long serialVersionUID = 24L;
	int numOfWeapons = 10;

	public String toString() {
		return String.valueOf(numOfWeapons);
	}
}
