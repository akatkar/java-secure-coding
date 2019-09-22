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
 * @category Compliant Solution (Rollback)
 * 
 * @description This compliant solution replaces the catch block in the
 *              getVolumePackage() method with code that restores prior object
 *              state in the event of an exception.
 */
class Dimensions {

	private int length;
	private int width;
	private int height;
	static public final int PADDING = 2;
	static public final int MAX_DIMENSION = 10;

	public Dimensions(int length, int width, int height) {
		this.length = length;
		this.width = width;
		this.height = height;
	}

	protected int getVolumePackage(int weight) {
		length += PADDING;
		width += PADDING;
		height += PADDING;
		try {
			if (length <= PADDING || width <= PADDING || height <= PADDING || length > MAX_DIMENSION + PADDING
					|| width > MAX_DIMENSION + PADDING || height > MAX_DIMENSION + PADDING || weight <= 0
					|| weight > 20) {
				throw new IllegalArgumentException();
			}
			// 12 * 12 * 12 = 1728
			int volume = length * width * height;
			// Revert
			length -= PADDING;
			width -= PADDING;
			height -= PADDING;
			return volume;
		} catch (Throwable t) {
			// MyExceptionReporter mer = new MyExceptionReporter();
			// mer.report(t); // Sanitize
			// Revert
			length -= PADDING;
			width -= PADDING;
			height -= PADDING;
			return -1;
		}
	}

	public static void main(String[] args) {
		Dimensions d = new Dimensions(10, 10, 10);
		// Prints -1 (error)
		System.out.println(d.getVolumePackage(21));
		// Prints 2744 instead of 1728
		System.out.println(d.getVolumePackage(19));
	}
}
