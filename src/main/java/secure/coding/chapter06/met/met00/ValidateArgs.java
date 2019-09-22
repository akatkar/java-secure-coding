package secure.coding.chapter06.met.met00;

/**
 * @rule MET00-J. Validate method arguments
 * 
 * @description Validate method arguments to ensure that they fall within the
 *              bounds of the method’s intended design. This practice ensures
 *              that operations on the method’s parameters yield valid results.
 *              Failure to validate method arguments can result in incorrect
 *              calculations, runtime exceptions, violation of class invariants,
 *              and inconsistent object state.
 * 
 * @category Noncompliant code
 */
public class ValidateArgs {

	private Object myState = null;

	// Sets some internal state in the library
	void setState(Object state) {
		myState = state;
	}

	// Performs some action using the file passed earlier
	void useState() {
		// Perform some action here
	}
}
