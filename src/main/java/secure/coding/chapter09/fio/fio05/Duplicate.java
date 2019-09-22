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

final class Duplicate {
	CharBuffer cb;

	public Duplicate() {
		cb = CharBuffer.allocate(10);
		// Initialize
	}

	/**
	 * @category Noncompliant code
	 *
	 * @description This noncompliant code example invokes the duplicate() method to
	 *              create and return a copy of the CharBuffer. As stated in the
	 *              contract for the duplicate() method, the returned buffer is
	 *              backed by the same array as is the original buffer.
	 *              Consequently, if a caller were to modify the elements of the
	 *              backing array, these modifications would also affect the
	 *              original buffer.
	 */
	public CharBuffer getBufferCopy1() {
		return cb.duplicate();
	}

	/**
	 * @category Compliant solution
	 *
	 * @description This compliant solution exposes a read-only view of the
	 *              CharBuffer to untrusted code.
	 */
	public CharBuffer getBufferCopy() {
		return cb.asReadOnlyBuffer();
	}
}