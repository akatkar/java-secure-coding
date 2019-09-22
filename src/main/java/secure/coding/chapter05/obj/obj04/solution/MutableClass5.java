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
 * @category Compliant solution with Unmodifiable Date Wrapper
 */
public final class MutableClass5 implements Cloneable {
	private Date date;

	public MutableClass5(Date d) {
		this.date = d;
	}

	public void setDate(Date d) {
		this.date = (Date) d.clone();
	}

	public UnmodifiableDateView getDate() {
		return new UnmodifiableDateView(date);
	}
}

class UnmodifiableDateView extends Date {
	private Date date;

	public UnmodifiableDateView(Date date) {
		this.date = date;
	}

	public void setTime(long date) {
		throw new UnsupportedOperationException();
	}
	// Override all other mutator methods
	// to throw UnsupportedOperationException
}
