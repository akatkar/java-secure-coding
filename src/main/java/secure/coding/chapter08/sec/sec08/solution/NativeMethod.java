package secure.coding.chapter08.sec.sec08.solution;

/**
 * @rule SEC08-J. Define wrappers around native methods
 * 
 * 
 * @descriptionWhen Native methods are defined in Java and written in languages
 *                  such as C and C++ [ JNI 2006 ]. The added extensibility
 *                  comes at the cost of flexibility and portability because the
 *                  code no longer conforms to the policies enforced by Java.
 *                  Native methods have been used for performing
 *                  platform-specific operations, interfacing with legacy
 *                  library code, and improving program performance
 * 
 *                  Defining a wrapper method facilitates installing appropriate
 *                  security manager checks, validating arguments passed to
 *                  native code, validating return values, defensively copying
 *                  mutable inputs, and sanitizing untrusted data. Consequently,
 *                  every native method must be private and must be invoked only
 *                  by a wrapper method.
 * 
 * @category Compliant solution
 *
 * @description This compliant solution declares the native method private. The
 *              doOperation() wrapper method checks permissions, creates a
 *              defensive copy of the mutable input array data, and checks the
 *              ranges of the arguments. The nativeOperation() method is
 *              consequently called with secure inputs. Note that the validation
 *              checks must produce outputs that conform to the input
 *              requirements of the native methods.
 */
public class NativeMethod {

	// private native method
	private native void nativeOperation(byte[] data, int offset, int len);

	// wrapper method performs SecurityManager and input validation checks
	public void doOperation(byte[] data, int offset, int len) {
		// permission needed to invoke native method
		// securityManagerCheck();
		if (data == null) {
			throw new NullPointerException();
		}
		// copy mutable input
		data = data.clone();
		// validate input
		if ((offset < 0) || (len < 0) || (offset > (data.length - len))) {
			throw new IllegalArgumentException();
		}
		nativeOperation(data, offset, len);
	}

	static {
		// load native library in static initializer of class
		System.loadLibrary("NativeMethodLib");
	}
}
