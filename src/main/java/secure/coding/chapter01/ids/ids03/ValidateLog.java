package secure.coding.chapter01.ids.ids03;

import java.util.regex.Pattern;

import org.hibernate.criterion.Example;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ValidateLog {

	private static final Logger LOGGER = LoggerFactory.getLogger(ValidateLog.class);

	/**
	 * @category Noncompliant Code Example
	 * 
	 * @description This noncompliant code example logs the user’s login name when
	 *              an invalid request is received. No input sanitization is
	 *              performed.
	 */

	public static void example1(String username) {
		boolean loginSuccessful = false;
		if (loginSuccessful) {
			LOGGER.error("User login succeeded for: " + username);
		} else {
			LOGGER.error("User login failed for: " + username);
		}
	}

	/**
	 * @category Compliant Solution
	 * 
	 * @description This compliant solution sanitizes the username input before
	 *              logging it, preventing injection attacks. Refer to rule IDS00-J
	 *              for more details on input sanitization.
	 */
	public static void example2(String username) {
		boolean loginSuccessful = false;
		if (!Pattern.matches("[A-Za-z0-9_]+", username)) {
			// Unsanitized username
			LOGGER.error("User login failed for unauthorized user");
		} else if (loginSuccessful) {
			LOGGER.error("User login succeeded for: " + username);
		} else {
			LOGGER.error("User login failed for: " + username);
		}
	}
	
	public static void main(String[] args) {
		
		example1("david");
		example2("david");
			
		String  malicious = "david\n15:54:50.565 [main] ERROR secure.coding.chapter01.ids.ids03.ValidateLog - User login succeeded for: administrator";
		
		example1(malicious);		
		example2(malicious);
	}
}
