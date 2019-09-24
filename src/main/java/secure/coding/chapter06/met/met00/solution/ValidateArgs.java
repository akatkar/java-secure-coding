package secure.coding.chapter06.met.met00.solution;

/**
 * @rule MET00-J. Validate method arguments
 * 
 * @description Validate method arguments to ensure that they fall within the
 *              bounds of the methods intended design. This practice ensures
 *              that operations on the methods parameters yield valid results.
 *              Failure to validate method arguments can result in incorrect
 *              calculations, runtime exceptions, violation of class invariants,
 *              and inconsistent object state.
 * 
 * @category Compliant solution
 */
public class ValidateArgs {

	private Object myState = null;

	// Sets some internal state in the library
	void setState(Object state) {
		if (state == null) {
			// Handle null state
		}
		// Defensive copy here when state is mutable
		if (isInvalidState(state)) {
			// Handle invalid state
		}
		myState = state;
	}

	private boolean isInvalidState(Object state) {
		return false;
	}

	// Performs some action using the state passed earlier
	void useState() {
		if (myState == null) {
			// Handle no state (e.g. null) condition
		}
		// ...
		System.out.println("State is " + myState);
	}
}
