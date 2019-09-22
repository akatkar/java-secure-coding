package secure.coding.chapter06.met.met11.solution;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Hashtable;

/**
 * @rule MET11-J. Ensure that keys used in comparison operations are immutable
 * 
 * @description Objects that serve as keys in ordered sets and maps should be
 *              immutable. When some fields must be mutable, the equals(),
 *              hashCode(), and compareTo() methods must consider only immutable
 *              state when comparing objects. Violations of this rule can
 *              produce inconsistent orderings in collections. The documentation
 *              of java.util.Interface Set<E> and java.util.Interface Map<K,V>
 *              warns against this.
 * 
 * @category Compliant solution
 * 
 * @description This compliant solution changes the type of the key value to be
 *              an Integer object. Consequently, key values remain consistent
 *              across multiple runs of the program, across serialization and
 *              deserialization, and also across multiple JVMs.
 */
public final class HashSer {
	public static void main(String[] args) throws IOException, ClassNotFoundException {
		Hashtable<Integer, String> ht = new Hashtable<>();
		ht.put(new Integer(1), "Value");
		System.out.println("Entry: " + ht.get(1)); // Retrieve using the key
		// Serialize the Hashtable object
		FileOutputStream fos = new FileOutputStream("hashdata.ser");
		ObjectOutputStream oos = new ObjectOutputStream(fos);
		oos.writeObject(ht);
		oos.close();
		// Deserialize the Hashtable object
		FileInputStream fis = new FileInputStream("hashdata.ser");
		ObjectInputStream ois = new ObjectInputStream(fis);
		Hashtable<Integer, String> ht_in = (Hashtable<Integer, String>) (ois.readObject());
		ois.close();
		if (ht_in.contains("Value"))
			// Check whether the object actually exists in the Hashtable
			System.out.println("Value was found in deserialized object.");
		if (ht_in.get(1) == null) // Not printed
			System.out.println("Object was not found when retrieved using the key.");
	}
}
