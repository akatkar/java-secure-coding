package secure.coding.chapter03.exp.exp01;

import java.util.Collection;
import java.util.Iterator;

/**
 * @rule EXP01-J. Never dereference null pointers
 * 
 * @description Null pointer dereferencing occurs when a null variable is
 *              treated as if it were a valid object reference and used without
 *              checking its state. This condition results in a
 *              NullPointerException, and can also result in denial of service.
 *              Consequently, null pointers must never be dereferenced.
 */
public class NullPointerExample {

	/**
	 * @category Noncompliant code
	 * 
	 * @description This noncompliant example shows a bug in Tomcat version 4.1.24,
	 *              initially discovered by Reasoning [ Reasoning 2003 ]. The
	 *              cardinality method was designed to return the number of
	 *              occurrences of object obj in collection col. One valid use of
	 *              the cardinality method is to determine how many objects in the
	 *              collection are null. However, because membership in the
	 *              collection is checked using the expression obj.equals(elt), a
	 *              null pointer dereference is guaranteed whenever obj is null and
	 *              elt is not null.
	 */
	public static int cardinality(Object obj, final Collection<Object> col) {
		int count = 0;
		Iterator<Object> it = col.iterator();
		while (it.hasNext()) {
			Object elt = it.next();
			// null pointer dereference
			if ((null == obj && null == elt) || obj.equals(elt)) {
				count++;
			}
		}
		return count;
	}

}
