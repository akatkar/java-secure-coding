package secure.coding.chapter10.msc.MSC06;

import java.util.*;

/**
 * @rule MSC05-J. MSC06-J. Do not modify the underlying collection when an
 *       iteration is in progress
 * 
 */
class BadIterate {
	/**
	 * @category Noncompliant code
	 * 
	 * @description This noncompliant code example (based on Sun Developer Network [
	 *              SDN 2011 ] bug report 66872774) uses the Collection’s remove()
	 *              method to remove an element from an ArrayList while iterating
	 *              over the ArrayList. The resulting behavior is unspecified.
	 */
	private static void example1() {
		List<String> list = new ArrayList<String>();
		list.add("one");
		list.add("two");
		Iterator<String> iter = list.iterator();
		while (iter.hasNext()) {
			String s = (String) iter.next();
			if (s.equals("one")) {
				list.remove(s);
			}
		}
	}

	/**
	 * @category Compliant Solution (iterator.remove())
	 * 
	 * @description The Iterator.remove() method removes the last element returned
	 *              by the iterator from the underlying Collection. Its behavior is
	 *              fully specified, so it may be safely invoked while iterating
	 *              over a collection.
	 */
	private static void example2() {
		List<String> list = new ArrayList<String>();
		list.add("one");
		list.add("two");
		Iterator<String> iter = list.iterator();
		while (iter.hasNext()) {
			String s = (String) iter.next();
			if (s.equals("one")) {
				iter.remove();
			}
		}
	}

	public static void main(String[] args) {
		example1();
		example2();
	}

}
