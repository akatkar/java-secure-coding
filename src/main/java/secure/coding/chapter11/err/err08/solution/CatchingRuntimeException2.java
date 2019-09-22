package secure.coding.chapter11.err.err08.solution;

/**
 * @rule ERR08-J. Do not catch NullPointerException or any of its ancestors
 * 
 * @description Programs must not catch java.lang.NullPointerException. A
 *              NullPointerException exception thrown at runtime indicates the
 *              existence of an underlying null pointer dereference that must be
 *              fixed in the application code
 * 
 *              Likewise, programs must not catch RuntimeException, Exception,
 *              or Throwable. When a method catches RuntimeException, it may
 *              receive exceptions unanticipated by the designer, including
 *              NullPointerException and ArrayIndexOutOfBoundsException.
 * 
 *              Many catch clauses simply log or ignore the enclosed exceptional
 *              condition and attempt to resume normal execution; this practice
 *              often violates rule ERR00-J. Runtime exceptions often indicate
 *              bugs in the program that should be fixed by the developer and
 *              often cause control flow vulnerabilities.
 * 
 * @category Compliant Solution
 * 
 * @description This compliant solution omits an explicit check for a null
 *              reference and permits a NullPointerException to be thrown.
 * 
 *              Omitting the null check means that the program fails more
 *              quickly than if the program had returned false and lets an
 *              invoking method discover the null value. A method that throws a
 *              NullPointerException without a null check must provide a
 *              precondition that the argument being passed to it is not null.
 */
public class CatchingRuntimeException2 {

	private static boolean isName(String s) {
		String names[] = s.split(" ");
		if (names.length != 2) {
			return false;
		}
		return (isCapitalized(names[0]) && isCapitalized(names[1]));
	}

	private static boolean isCapitalized(String name) {
		String capitalized = name.substring(0, 1).toUpperCase() + name.substring(1).toLowerCase();
		return capitalized.equals(name);
	}

	public static void main(String[] args) {
		System.out.println(isName("Ali Katkar"));
		System.out.println(isName("Ali katkar"));
		System.out.println(isName("ali Katkar"));
		System.out.println(isName("ali katkar"));
		System.out.println(isName(""));
		System.out.println(isName(null));
	}
}