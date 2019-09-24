package secure.coding.chapter07.ser.ser10;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/**
 * @rule SER10-J. Avoid memory and resource leaks during serialization
 * 
 * 
 * @description Serialization can extend the lifetime of objects, preventing
 *              their garbage collection. The ObjectOutputStream ensures that
 *              each object is written to the stream only once by retaining a
 *              reference (or handle) to each object written to the stream. When
 *              a previously written object is subsequently written to the
 *              stream again, it is replaced with a reference to the originally
 *              written data in the stream.
 * 
 * @category Noncompliant code
 *
 */
class SensorData implements Serializable {
	// 1 MB of data per instance!
	public static SensorData readSensorData() {
		return new SensorData();
	}

	public static boolean isAvailable() {
		return true;
	}
}

class SerializeSensorData {
	public static void main(String[] args) throws IOException {
		try (ObjectOutputStream out = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream("ser.dat")))) {
			while (SensorData.isAvailable()) {
				// note that each SensorData object is 1 MB in size
				SensorData sd = SensorData.readSensorData();
				out.writeObject(sd);
			}
		}
	}
}