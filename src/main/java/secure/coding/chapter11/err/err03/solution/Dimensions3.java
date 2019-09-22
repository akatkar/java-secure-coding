package secure.coding.chapter11.err.err03.solution;

/**
 * @rule ERR03-J. Restore prior object state on method failure
 * 
 * @description Objects in general should—and security-critical objects must—be
 *              maintained in a consistent state even when exceptional
 *              conditions arise. Common techniques for maintaining object
 *              consistency include
 * 
 *              - Input validation (on method arguments, for example)
 * 
 *              - Reordering logic so that code that can result in the
 *              exceptional condition executes before the object is modified
 * 
 *              - Using rollbacks in the event of failure
 * 
 *              - Performing required operations on a temporary copy of the
 *              object and committing changes to the original object only after
 *              their successful completion
 * 
 *              - Avoiding the need to modify the object at all
 * 
 * @category Compliant Solution (Input Validation)
 * 
 * @description This compliant solution improves on the previous solution by
 *              performing input validation before modifying the state of the
 *              object.
 * 
 *              Note that the try block contains only those statements that
 *              could throw the exception; all others have been moved outside
 *              the try block.
 */
class Dimensions3 {

	private int length;
	private int width;
	private int height;
	static public final int PADDING = 2;
	static public final int MAX_DIMENSION = 10;

	public Dimensions3(int length, int width, int height) {
		this.length = length;
		this.width = width;
		this.height = height;
	}

	protected int getVolumePackage(int weight) {
		try {
			if (length <= 0 || width <= 0 || height <= 0 || length > MAX_DIMENSION || width > MAX_DIMENSION
					|| height > MAX_DIMENSION || weight <= 0 || weight > 20) {
				throw new IllegalArgumentException(); // Validate first
			}
		} catch (Throwable t) {
			// MyExceptionReporter mer = new MyExceptionReporter();
			// mer.report(t); // Sanitize
			return -1;
		}
		length += PADDING;
		width += PADDING;
		height += PADDING;
		int volume = length * width * height;
		length -= PADDING;
		width -= PADDING;
		height -= PADDING;
		return volume;
	}

	public static void main(String[] args) {
		Dimensions3 d = new Dimensions3(10, 10, 10);
		// Prints -1 (error)
		System.out.println(d.getVolumePackage(21));
		// Prints 2744 instead of 1728
		System.out.println(d.getVolumePackage(19));
	}
}
