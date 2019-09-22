package secure.coding.chapter07.ser.ser02;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.security.GeneralSecurityException;
import java.security.Key;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.SecureRandom;
import java.security.Signature;
import java.security.SignedObject;

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
 * @category Compliant solution
 *
 * @description This compliant solution correctly signs the object before
 *              sealing it. This provides a guarantee of authenticity to the
 *              object in addition to protection from man-in-the-middle attacks.
 */
public class Ser02CompliantSolution {
	public static void main(String[] args) throws IOException, GeneralSecurityException, ClassNotFoundException {
		// Build map
		SerializableMap<String, Integer> map = MapSerializer.buildMap();

		// Generate signing public/private key pair & sign map
		KeyPairGenerator kpg = KeyPairGenerator.getInstance("DSA");
		KeyPair kp = kpg.generateKeyPair();
		Signature sig = Signature.getInstance("SHA1withDSA");
		SignedObject signedMap = new SignedObject(map, kp.getPrivate(), sig);
		
		// Generate sealing key & seal map
		KeyGenerator generator;
		generator = KeyGenerator.getInstance("AES");
		generator.init(new SecureRandom());
		Key key = generator.generateKey();
		Cipher cipher = Cipher.getInstance("AES");
		cipher.init(Cipher.ENCRYPT_MODE, key);
		SealedObject sealedMap = new SealedObject(signedMap, cipher);
		
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
		signedMap = (SignedObject) sealedMap.getObject(cipher);
		
		// Verify signature and retrieve map
		if (!signedMap.verify(kp.getPublic(), sig)) {
			throw new GeneralSecurityException("Map failed verification");
		}
		map = (SerializableMap<String, Integer>) signedMap.getObject();

		// Inspect map
		MapSerializer.inspectMap(map);
	}
}
