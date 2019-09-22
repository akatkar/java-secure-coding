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
 * @category Compliant solution with Clone
 */
public final class MutableClass3 implements Cloneable {
	private Date date;

	public MutableClass3(Date d) {
		this.date = new Date(d.getTime());
	}

	public Date getDate() {
		return (Date) date.clone();
	}

	public void setDate(Date d) {
		this.date = (Date) d.clone();
	}

	public Object clone() throws CloneNotSupportedException {
		final MutableClass3 cloned = (MutableClass3) super.clone();
		// manually copy mutable Date object
		cloned.date = (Date) date.clone();
		return cloned;
	}
}
