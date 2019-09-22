package secure.coding.chapter05.obj.obj01;

import java.util.HashMap;

/**
 * @rule OBJ01-J. Declare data members as private and provide accessible wrapper
 *       methods
 * 
 */
public class HashMapExample {
	/**
	 * @category Non-compliant code
	 */
	public static final HashMap<Integer, String> _HASH_MAP = new HashMap<Integer, String>();

	/**
	 * @category Compliant solution
	 */
	private static final HashMap<Integer, String> HASH_MAP = new HashMap<Integer, String>();

	public static String getElement(int key) {
		return HASH_MAP.get(key);
	}
}
