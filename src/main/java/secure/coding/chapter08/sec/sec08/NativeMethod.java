package secure.coding.chapter08.sec.sec08;

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
 * @category Noncompliant code
 *
 * @description In this noncompliant code example, the nativeOperation() method
 *              is both native and public; therefore, untrusted callers may
 *              invoke it. Native method invocations bypass security manager
 *              checks.
 * 
 *              This example includes the doOperation() wrapper method, which
 *              invokes the nativeOperation() native method but fails to provide
 *              input validation or security checks.
 */
public class NativeMethod {

	// public native method
	public native void nativeOperation(byte[] data, int offset, int len);

	// wrapper method that lacks security checks and input validation
	public void doOperation(byte[] data, int offset, int len) {
		nativeOperation(data, offset, len);
	}

	static {
		// load native library in static initializer of class
		System.loadLibrary("NativeMethodLib");
	}
}
