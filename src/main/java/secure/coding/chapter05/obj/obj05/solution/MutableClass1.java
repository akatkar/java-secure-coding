package secure.coding.chapter05.obj.obj05.solution;

import java.util.Date;

/**
 * @rule OBJ05-J. Defensively copy private mutable class members before
 *       returning their references
 *
 * 
 * @description Returning references to internal mutable members of a class can
 *              compromise an application’s security both by breaking
 *              encapsulation and by providing the opportunity to corrupt the
 *              internal state of the class (whether accidentally or
 *              maliciously). As a result, programs must not return references
 *              to internal mutable classes. Returning a reference to a
 *              defensive copy of mutable internal state ensures that the caller
 *              cannot modify the original internal state, although the copy
 *              remains mutable.
 *
 * @category Compliant solution
 */
public final class MutableClass1 {
	private Date d;

	public MutableClass1() {
		d = new Date();
	}

	/**
	 * @description This compliant solution returns a clone of the Date object from
	 *              the getDate() accessor method. While Date can be extended by an
	 *              attacker, this is safe because the Date object returned by
	 *              getDate() is controlled by MutableClass and is known to be a
	 *              nonmalicious subclass.
	 */
	public Date getDate() {
		return (Date) d.clone();
	}
}
