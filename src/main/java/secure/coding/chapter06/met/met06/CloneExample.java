package secure.coding.chapter06.met.met06;

import java.net.HttpCookie;

/**
 * @rule MET06-J. Do not invoke overridable methods in clone()
 * 
 * @description Calling overridable methods from the clone() method is insecure.
 * 
 *              First, a malicious subclass could override the method and affect
 *              the behavior of the clone() method.
 * 
 *              Second, a trusted subclass could observe (and potentially
 *              modify) the cloned object in a partially initialized state
 *              before its construction has concluded.
 * 
 *              In either case, the subclass could leave the clone, the object
 *              being cloned, or both, in an inconsistent state. Consequently,
 *              clone() methods may invoke only methods that are final or
 *              private.
 * 
 * @category Noncompliant code
 */
class SuperClass implements Cloneable {

	HttpCookie[] cookies;

	SuperClass(HttpCookie[] c) {
		cookies = c;
	}

	public Object clone() throws CloneNotSupportedException {
		final CloneExample clone = (CloneExample) super.clone();
		clone.doSomething(); // Invokes overridable method
		clone.cookies = clone.deepCopy();
		return clone;
	}

	void doSomething() { // Overridable
		for (int i = 0; i < cookies.length; i++) {
			cookies[i].setValue("" + i);
		}
	}

	HttpCookie[] deepCopy() {
		if (cookies == null) {
			throw new NullPointerException();
		}
		// deep copy
		HttpCookie[] cookiesCopy = new HttpCookie[cookies.length];
		for (int i = 0; i < cookies.length; i++) {
			// Manually create a copy of each element in array
			cookiesCopy[i] = (HttpCookie) cookies[i].clone();
		}
		return cookiesCopy;
	}
}

public class CloneExample extends SuperClass {

	CloneExample(HttpCookie[] c) {
		super(c);
	}

	public Object clone() throws CloneNotSupportedException {
		final CloneExample clone = (CloneExample) super.clone();
		clone.doSomething();
		return clone;
	}

	void doSomething() { // Erroneously executed
		for (int i = 0; i < cookies.length; i++) {
			cookies[i].setDomain(i + ".foo.com");
		}
	}

	public static void main(String[] args) throws CloneNotSupportedException {
		HttpCookie[] hc = new HttpCookie[20];
		for (int i = 0; i < hc.length; i++) {
			hc[i] = new HttpCookie("cookie" + i, "" + i);
		}
		CloneExample bc = new CloneExample(hc);
		bc.clone();
	}
}
