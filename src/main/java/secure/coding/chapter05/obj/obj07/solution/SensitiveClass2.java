package secure.coding.chapter05.obj.obj07.solution;

/**
 * @rule OBJ07-J. Sensitive classes must not let themselves be copied
 * 
 * @description Classes containing private, confidential, or otherwise sensitive
 *              data are best not copied. If a class is not meant to be copied,
 *              then failing to define copy mechanisms, such as a copy
 *              constructor, is insufficient to prevent copying.
 *
 * @category Compliant solution
 */
public class SensitiveClass2 {

	private char[] filename;
	private Boolean shared = false;

	SensitiveClass2(String filename) {
		this.filename = filename.toCharArray();
	}

	final void replace() {
		if (!shared) {
			for (int i = 0; i < filename.length; i++) {
				filename[i] = 'x';
			}
		}
	}

	final String get() {
		if (!shared) {
			shared = true;
			return String.valueOf(filename);
		} else {
			throw new IllegalStateException("Failed to get instance");
		}
	}

	final void printFilename() {
		System.out.println(String.valueOf(filename));
	}

	// final clone method
	@Override
	public final SensitiveClass2 clone() throws CloneNotSupportedException {
		throw new CloneNotSupportedException();
	}
}
