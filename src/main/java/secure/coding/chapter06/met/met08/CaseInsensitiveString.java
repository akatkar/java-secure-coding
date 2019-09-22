package secure.coding.chapter06.met.met08;

/**
 * @rule MET08-J. Ensure objects that are equated are equatable
 * 
 * @description Composition or inheritance may be used to create a new class
 *              that both encapsulates an existing class and adds one or more
 *              fields. When one class extends another in this way, the concept
 *              of equality for the subclass may or may not involve its new
 *              fields. That is, when comparing two subclass objects for
 *              equality, sometimes their respective fields must also be equal,
 *              and other times they need not be equal. Depending on the concept
 *              of equality for the subclass, the subclass might override
 *              equals(). Furthermore, this method must follow the general
 *              contract for equals() as specified by the Java Language
 *              Specification [ JLS 2005 ].
 * 
 * @category Noncompliant code
 */
public class CaseInsensitiveString {

	private String s;

	public CaseInsensitiveString(String s) {
		if (s == null) {
			throw new NullPointerException();
		}
		this.s = s;
	}

	// This method violates symmetry
	public boolean equals(Object o) {
		if (o instanceof CaseInsensitiveString) {
			return s.equalsIgnoreCase(((CaseInsensitiveString) o).s);
		}
		if (o instanceof String) {
			return s.equalsIgnoreCase((String) o);
		}
		return false;
	}

	// Comply with MET09-J
	public int hashCode() {
		return s.hashCode();
	}

	public static void main(String[] args) {
		CaseInsensitiveString cis = new CaseInsensitiveString("Java");
		String s = "java";
		System.out.println(cis.equals(s)); // Returns true
		System.out.println(s.equals(cis)); // Returns false
	}
}
