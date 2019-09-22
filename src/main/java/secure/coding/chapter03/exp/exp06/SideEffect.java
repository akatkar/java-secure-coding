package secure.coding.chapter03.exp.exp06;

import java.util.ArrayList;

/**
 * @rule EXP06-J. D o not use side-effecting expressions in assertions
 * 
 * @description The assert statement is a convenient mechanism for incorporating
 *              diagnostic tests in code. Expressions used with the standard
 *              assert statement must avoid side effects. Typically, the
 *              behavior of the assert statement depends on the status of a
 *              runtime property. When enabled, the assert statement is designed
 *              to evaluate its expression argument and throw an AssertionError
 *              if the result of the expression is false. When disabled, assert
 *              is defined to be a no-op; any side effects resulting from
 *              evaluation of the expression in the assertion are lost when
 *              assertions are disabled. Consequently, programs must not use
 *              side-effecting expressions in assertions.
 */
public class SideEffect {

	private ArrayList<String> names;

	/**
	 * @category Noncompliant code
	 * 
	 * @description This noncompliant code is attempting to delete all the null
	 *              names from the list in an assertion. However, the boolean
	 *              expression is not evaluated when assertions are disabled.
	 */
	void process(int index) {
		assert names.remove(null); // side-effect
		// …
	}
}
