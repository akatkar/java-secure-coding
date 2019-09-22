package secure.coding.chapter07.ser.ser02;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.security.GeneralSecurityException;
import java.security.Key;
import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SealedObject;

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
 * @description This noncompliant code example encrypts the map into a
 *              SealedObject, rendering the data inaccessible to prying eyes.
 *              However, the program fails to sign the data, rendering it
 *              impossible to authenticate.
 */
public class NonCompliant2 {
	public static void main(String[] args) throws IOException, GeneralSecurityException, ClassNotFoundException {
		// Build map
		SerializableMap<String, Integer> map = MapSerializer.buildMap();
		
		// Generate sealing key & seal map
		KeyGenerator generator;
		generator = KeyGenerator.getInstance("AES");
		generator.init(new SecureRandom());
		Key key = generator.generateKey();
		Cipher cipher = Cipher.getInstance("AES");
		cipher.init(Cipher.ENCRYPT_MODE, key);
		SealedObject sealedMap = new SealedObject(map, cipher);
		
		// Serialize map
		ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("data"));
		out.writeObject(sealedMap);
		out.close();
		
		// Deserialize map
		ObjectInputStream in = new ObjectInputStream(new FileInputStream("data"));
		sealedMap = (SealedObject) in.readObject();
		in.close();
		
		// Unseal map
		cipher = Cipher.getInstance("AES");
		cipher.init(Cipher.DECRYPT_MODE, key);
		map = (SerializableMap<String, Integer>) sealedMap.getObject(cipher);
		// Inspect map
		MapSerializer.inspectMap(map);
	}
}
