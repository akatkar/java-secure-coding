package secure.coding.chapter07.ser.ser02;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

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
 */
class SerializableMap<K, V> implements Serializable {
	final static long serialVersionUID = -2648720192864531932L;
	private Map<K, V> map;

	public SerializableMap() {
		map = new HashMap<>();
	}

	public Object getData(K key) {
		return map.get(key);
	}

	public void setData(K key, V data) {
		map.put(key, data);
	}
}

public class MapSerializer implements Serializable {

	public static SerializableMap<String, Integer> buildMap() {
		SerializableMap<String, Integer> map = new SerializableMap<String, Integer>();
		map.setData("John Doe", new Integer(123456789));
		map.setData("Richard Roe", new Integer(246813579));
		return map;
	}

	public static void inspectMap(SerializableMap<String, Integer> map) {
		System.out.println("John Doe's number is " + map.getData("John Doe"));
		System.out.println("Richard Roe's number is " + map.getData("Richard Roe"));
	}
}
