package secure.coding.chapter03.exp.exp02;

/**
 * @rule EXP02-J. Use the two-argument Arrays.equals() method to compare the
 *       contents of arrays
 * 
 * 
 * @description Arrays do not override the Object.equals() method; the
 *              implementation of the equals() method compares array references
 *              rather than their contents. Programs must use the two argument
 *              Arrays.equals() method to compare the contents of two arrays.
 *              Programs must use the reference equality operators, == and !=,
 *              when intentionally testing reference equality . Programs also
 *              must not use the array equals() method because it can lead to
 *              unexpected results.
 */
public class ArrayEqualsExample {
	/**
	 * @category Noncompliant code
	 */
	public void arrayEqualsExample() {
		int[] arr1 = new int[20]; // initialized to 0
		int[] arr2 = new int[20]; // initialized to 0
		arr1.equals(arr2); // false
	}
}
