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
 * @category Compliant Solution (Unmodified Object)
 * 
 * @description This compliant solution avoids the need to modify the object.
 *              The object’s state cannot be made inconsistent, and rollback is
 *              consequently unnecessary. This approach is preferred to
 *              solutions that modify the object but may be infeasible for
 *              complex code.
 */
class Dimensions4 {

	private int length;
	private int width;
	private int height;
	static public final int PADDING = 2;
	static public final int MAX_DIMENSION = 10;

	public Dimensions4(int length, int width, int height) {
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
		int volume = (length + PADDING) * (width + PADDING) * (height + PADDING);
		return volume;
	}

	public static void main(String[] args) {
		Dimensions4 d = new Dimensions4(10, 10, 10);
		// Prints -1 (error)
		System.out.println(d.getVolumePackage(21));
		// Prints 2744 instead of 1728
		System.out.println(d.getVolumePackage(19));
	}
}
