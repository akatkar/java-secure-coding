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
 * @category Noncompliant code
 *
 * @description This noncompliant code example uses the
 *              java.security.SignedObject class to sign an object when the
 *              integrity of the object must be ensured. The two new arguments
 *              passed in to the SignedObject() method to sign the object are
 *              Signature and a private key derived from a KeyPair object. To
 *              verify the signature, a PublicKey as well as a Signature
 *              argument is passed to the SignedObject.verify() method.
 * 
 *              Because the signing occurs after the sealing, it cannot be
 *              assumed that the signer is the true originator of the object.
 */
public class NonCompliant3 {
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
		
		// Generate signing public/private key pair & sign map
		KeyPairGenerator kpg = KeyPairGenerator.getInstance("DSA");
		KeyPair kp = kpg.generateKeyPair();
		Signature sig = Signature.getInstance("SHA1withDSA");
		SignedObject signedMap = new SignedObject(sealedMap, kp.getPrivate(), sig);
		
		// Serialize map
		ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("data"));
		out.writeObject(signedMap);
		out.close();
		
		// Deserialize map
		ObjectInputStream in = new ObjectInputStream(new FileInputStream("data"));
		signedMap = (SignedObject) in.readObject();
		in.close();
		
		// Verify signature and retrieve map
		if (!signedMap.verify(kp.getPublic(), sig)) {
			throw new GeneralSecurityException("Map failed verification");
		}
		sealedMap = (SealedObject) signedMap.getObject();
		// Unseal map
		cipher = Cipher.getInstance("AES");
		cipher.init(Cipher.DECRYPT_MODE, key);
		map = (SerializableMap<String, Integer>) sealedMap.getObject(cipher);

		// Inspect map
		MapSerializer.inspectMap(map);
	}
}
