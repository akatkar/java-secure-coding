package secure.coding.chapter05.obj.obj04.solution;

import java.util.Date;

/**
 * @rule OBJ04-J. Provide mutable classes with copy functionality to safely
 *       allow passing instances to untrusted code
 *
 * @description Mutable classes allow code external to the class to alter their
 *              instance or class fields. Provide means for creating copies of
 *              mutable classes so that disposable instances of such classes can
 *              be passed to untrusted code. This functionality is useful when
 *              methods in other classes must create copies of the particular
 *              class instance; see rules OBJ05-J and OBJ06-J for additional
 *              details
 *
 * @category Compliant solution with Public Static Factory Method
 */
public final class MutableClass2 {
	private final Date date;

	private MutableClass2(Date d) {
		// Non-instantiable and non-subclassable
		this.date = new Date(d.getTime()); // Make defensive copy
	}

	public Date getDate() {
		return (Date) date.clone(); // Copy and return
	}

	public static MutableClass2 getInstance(MutableClass2 mc) {
		return new MutableClass2(mc.getDate());
	}
}
