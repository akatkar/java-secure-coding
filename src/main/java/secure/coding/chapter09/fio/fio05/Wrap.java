package secure.coding.chapter09.fio.fio05;

import java.nio.CharBuffer;

/**
 * @rule FIO05-J. Do not expose buffers created using the wrap() or duplicate()
 *       methods to untrusted code
 * 
 * 
 * @description Buffer classes defined in the java.nio package, such as
 *              IntBuffer, CharBuffer, and ByteBuffer, define a variety of
 *              wrap() methods that wrap an array of the corresponding primitive
 *              data type into a buffer and return the buffer as a Buffer
 *              object. Although these methods create a new Buffer object, the
 *              new Buffer is backed by the given input array.
 * 
 *              Exposing these buffers to untrusted code exposes the backing
 *              array to malicious modification. Likewise, the duplicate()
 *              methods create additional buffers that are backed by the
 *              original buffer’s backing array; exposing such additional
 *              buffers to untrusted code affords the same opportunity for
 *              malicious modification.
 * 
 */

final class Wrap {
	private char[] dataArray;

	public Wrap() {
		dataArray = new char[10];
		// Initialize
	}

	/**
	 * @category Noncompliant code
	 *
	 * @description This noncompliant code example declares a char array, wraps it
	 *              within a Buffer, and exposes that Buffer to untrusted code via
	 *              the getBufferCopy() method.
	 */
	public CharBuffer getBufferCopy1() {
		return CharBuffer.wrap(dataArray);
	}

	/**
	 * @category Compliant solution
	 *
	 * @description This compliant solution returns a read-only view of the char
	 *              array in the form of a readonly CharBuffer. The standard library
	 *              implementation of CharBuffer guarantees that attempts to modify
	 *              the elements of a read-only CharBuffer will result in a
	 *              java.nio.ReadOnlyBufferException.
	 */
	public CharBuffer getBufferCopy2() {
		return CharBuffer.wrap(dataArray).asReadOnlyBuffer();
	}

	/**
	 * @category Compliant solution (Copy)
	 *
	 * @description This compliant solution allocates a new CharBuffer and
	 *              explicitly copies the contents of the char array into it before
	 *              returning the copy. Consequently, malicious callers can modify
	 *              the copy of the array but cannot modify the original.
	 */
	public CharBuffer getBufferCopy3() {
		CharBuffer cb = CharBuffer.allocate(dataArray.length);
		cb.put(dataArray);
		return cb;
	}
}