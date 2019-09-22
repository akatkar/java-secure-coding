package secure.coding.chapter05.obj.obj04;

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
 * @category Noncompliant code
 */
public final class MutableClass {
	private Date date;

	public MutableClass(Date d) {
		this.date = d;
	}

	public void setDate(Date d) {
		this.date = d;
	}

	public Date getDate() {
		return date;
	}
}
