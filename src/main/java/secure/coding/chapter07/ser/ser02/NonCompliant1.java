package secure.coding.chapter07.ser.ser02;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * @rule SER02-J. Sign then seal sensitive objects before sending them across a
 *       trust boundary
 * 
 * 
 * @description Sensitive data must be protected from eavesdropping and
 *              malicious tampering. An obfuscated transfer object that is
 *              strongly encrypted can protect data. This approach is known as
 *              sealing the object. To guarantee object integrity, apply a
 *              digital signature to the sealed object.
 * 
 * @category Noncompliant code
 *
 * @description This noncompliant code example simply serializes then
 *              deserializes the map. Consequently, the map can be serialized
 *              and transferred across different business tiers. Unfortunately,
 *              the example lacks any safeguards against byte stream
 *              manipulation attacks while the binary data is in transit.
 *              Likewise, anyone can reverse-engineer the serialized stream data
 *              to recover the data in the HashMap.
 */
public class NonCompliant1 {
	public static void main(String[] args) throws IOException, ClassNotFoundException {
		// Build map
		SerializableMap<String, Integer> map = MapSerializer.buildMap();
		// Serialize map
		ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("data"));
		out.writeObject(map);
		out.close();
		// Deserialize map
		ObjectInputStream in = new ObjectInputStream(new FileInputStream("data"));
		map = (SerializableMap<String, Integer>) in.readObject();
		in.close();
		// Inspect map
		MapSerializer.inspectMap(map);
	}
}
