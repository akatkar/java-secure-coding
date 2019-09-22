package secure.coding.chapter05.obj.obj07;

/**
 * @rule OBJ07-J. Sensitive classes must not let themselves be copied
 * 
 * @description Classes containing private, confidential, or otherwise sensitive
 *              data are best not copied. If a class is not meant to be copied,
 *              then failing to define copy mechanisms, such as a copy
 *              constructor, is insufficient to prevent copying.
 *
 * @category Noncompliant code
 */
class MaliciousSubclass extends SensitiveClass implements Cloneable {
	
	protected MaliciousSubclass(String filename) {
		super(filename);
	}

	@Override
	public MaliciousSubclass clone() {
		// Well-behaved clone() method
		MaliciousSubclass s = null;
		try {
			s = (MaliciousSubclass) super.clone();
		} catch (Exception e) {
			System.out.println("not cloneable");
		}
		return s;
	}

	public static void main(String[] args) {
		MaliciousSubclass ms1 = new MaliciousSubclass("file.txt");
		MaliciousSubclass ms2 = ms1.clone(); // Creates a copy
		String s = ms1.get(); // Returns filename
		System.out.println(s); // Filename is "file.txt"
		ms2.replace(); // Replaces all characters with 'x'
		// Both ms1.get() and ms2.get() will subsequently
		// return filename = 'xxxxxxxx'
		ms1.printFilename(); // Filename becomes 'xxxxxxxx'
		ms2.printFilename(); // Filename becomes 'xxxxxxxx'
	}
}
