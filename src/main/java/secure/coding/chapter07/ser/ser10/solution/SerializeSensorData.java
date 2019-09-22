package secure.coding.chapter07.ser.ser10.solution;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/**
 * @rule SER10-J. A void memory and resource leaks during serialization
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
 * @category Compliant solution
 * 
 * @description This compliant solution takes advantage of the known properties
 *              of the sensor data by resetting the output stream after each
 *              write. The reset clears the output stream’s internal object
 *              cache; consequently, the cache no longer maintains references to
 *              previously written SensorData objects. The garbage collector can
 *              collect SensorData instances that are no longer needed.
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
				out.reset(); // reset the stream
			}
		}
	}
}