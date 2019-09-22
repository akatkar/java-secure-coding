package secure.coding.chapter11.err.err03;


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
 * @category Noncompliant code
 * 
 * @description This noncompliant code example shows a Dimensions class that
 *              contains three internal attributes, the length, width, and
 *              height of a rectangular box. The getVolumePackage() method is
 *              designed to return the total volume required to hold the box
 *              after accounting for packaging material, which adds 2 units to
 *              the dimensions of each side. Nonpositive values of the
 *              dimensions of the box (exclusive of packaging material) are
 *              rejected during input validation. No dimension can be larger
 *              than 10. Also, the weight of the object is passed in as an
 *              argument and cannot be more than 20 units.
 * 
 *              Consider the case where the weight is more than 20 units. This
 *              causes an IllegalArgumentException, which is intercepted by the
 *              custom error reporter. While the logic restores the object’s
 *              original state in the absence of this exception, the rollback
 *              code fails to execute in the event of an exception.
 *              Consequently, subsequent invocations of getVolumePackage()
 *              produce incorrect results.
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
			return -1; // Non-positive error code
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
