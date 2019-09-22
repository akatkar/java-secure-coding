package secure.coding.chapter03.exp.exp01.solution;

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
	 * @category Compliant solution
	 * 
	 */
	public static int cardinality(Object obj, final Collection<Object> col) {
		int count = 0;
		Iterator<Object> it = col.iterator();
		while (it.hasNext()) {
			Object elt = it.next();
			if ((null == obj && null == elt) || (null != obj && obj.equals(elt))) {
				count++;
			}
		}
		return count;
	}

}
