package secure.coding.chapter10.msc.MSC02;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Random;

/**
 * @rule MSC02-J. Generate strong random numbers
 * 
 * @description Pseudorandom number generators (PRNGs) use deterministic
 *              mathematical algorithms to produce a sequence of numbers with
 *              good statistical properties. However, the sequences of numbers
 *              produced fail to achieve true randomness. PRNGs usually start
 *              with an arithmetic seed value. The algorithm uses this seed to
 *              generate an output value and a new seed, which is used to
 *              generate the next value, and so on.
 * 
 *              Consequently, the java.util.Random class must not be used either
 *              for security-critical applications or for protecting sensitive
 *              data. Use a more secure random number generator, such as the
 *              java.security.SecureRandom class.
 * 
 */
class StrongRandomNumbers {

	/**
	 * @category Noncompliant code
	 * 
	 * @description This noncompliant code example uses the insecure
	 *              java.util.Random class. This class produces an identical
	 *              sequence of numbers for each given seed value; consequently, the
	 *              sequence of numbers is predictable.
	 */
	public static void example1() {
		Random number = new Random(123L);
		// ...
		for (int i = 0; i < 20; i++) {
			// Generate another random integer in the range [0, 20]
			int n = number.nextInt(21);
			System.out.println(n);
		}
	}

	/**
	 * @category Compliant solution (Thread.sleep())
	 * 
	 * @description This compliant solution avoids use of a meaningless infinite
	 *              loop by invoking Thread.sleep() within the while loop. The loop
	 *              body contains semantically meaningful operations and
	 *              consequently cannot be optimized away.
	 */
	public static void example2() {
		try {
			SecureRandom number = SecureRandom.getInstance("SHA1PRNG");
			// Generate 20 integers 0..20
			for (int i = 0; i < 20; i++) {
				System.out.println(number.nextInt(21));
			}
		} catch (NoSuchAlgorithmException nsae) {
			// Forward to handler
		}
	}

	public static void main(String[] args) {
		System.out.println("insecure random numbers");
		example1();
		System.out.println();
		System.out.println("secure random numbers");
		example2();
	}
}
