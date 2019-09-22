package secure.coding.chapter05.obj.obj06;

import java.net.HttpCookie;

/**
 * @rule OBJ06-J. Defensively copy mutable inputs and mutable internal
 *       components
 * 
 * @description A mutable input has the characteristic that its value may vary;
 *              that is, multiple accesses may see differing values. This
 *              characteristic enables potential attacks that exploit race
 *              conditions. For example, a time-of-check, time-of-use (TOCTOU)
 *              vulnerability may result when a field contains a value that
 *              passes validation and security checks but changes before use.
 *
 * @category Noncompliant code
 */
public class MutableClass {
	
	// java.net.HttpCookie is mutable
	public void useMutableInput(HttpCookie cookie) {
		if (cookie == null) {
			throw new NullPointerException();
		}
		// Check whether cookie has expired
		if (cookie.hasExpired()) {
			// Cookie is no longer valid,
			// handle condition by throwing an exception
		}
		// Cookie may have expired since time of check
		doLogic(cookie);
	}

	private void doLogic(HttpCookie cookie){
		System.out.println("cookie used");
	}
}
