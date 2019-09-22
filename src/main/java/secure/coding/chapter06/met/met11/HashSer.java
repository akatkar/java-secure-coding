package secure.coding.chapter06.met.met11;

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
 * @category Noncompliant code
 * 
 */

// Mutable class Employee
class MyKey implements Serializable {
	// Does not override hashCode()
}

/**
 * @description This noncompliant code example follows that advice but can
 *              nevertheless fail after serialization and deserialization.
 *              Consequently, it may be impossible to retrieve the value of the
 *              object after deserialization by using the original key.
 */
public final class HashSer {
	public static void main(String[] args) throws IOException, ClassNotFoundException {
		Hashtable<MyKey, String> ht = new Hashtable<>();
		MyKey key = new MyKey();
		ht.put(key, "Value");
		System.out.println("Entry: " + ht.get(key));
		// Retrieve using the key, works
		// Serialize the Hashtable object
		FileOutputStream fos = new FileOutputStream("hashdata.ser");
		ObjectOutputStream oos = new ObjectOutputStream(fos);
		oos.writeObject(ht);
		oos.close();
		// Deserialize the Hashtable object
		FileInputStream fis = new FileInputStream("hashdata.ser");
		ObjectInputStream ois = new ObjectInputStream(fis);
		Hashtable<MyKey, String> ht_in = (Hashtable<MyKey, String>) (ois.readObject());
		ois.close();
		if (ht_in.contains("Value"))
			// Check whether the object actually exists in the hash table
			System.out.println("Value was found in deserialized object.");
		if (ht_in.get(key) == null) // Gets printed
			System.out.println("Object was not found when retrieved using the key.");
	}
}
