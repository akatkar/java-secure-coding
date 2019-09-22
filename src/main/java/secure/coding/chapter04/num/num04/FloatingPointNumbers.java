package secure.coding.chapter04.num.num04;

/**
 * @rule: NUM04-J. D o not use floating-point numbers if precise computation is
 *        required
 *
 * @description: The Java language provides two primitive floating-point types,
 *               float and double, which are associated with the
 *               single-precision 32-bit and double-precision 64-bit format
 *               values and operations specified by IEEE 754 [ IEEE 754 ]. Each
 *               of the floating-point types has a fixed, limited number of
 *               mantissa bits. Consequently, it is impossible to precisely
 *               represent any ptg7041395 irrational number (for example, π).
 *               Further, because these types use a binary mantissa, they cannot
 *               precisely represent many finite decimal numbers, such as 0.1,
 *               because these numbers have repeating binary representations.
 */
public class FloatingPointNumbers {

	/**
	 * @category Non-compliant code
	 * 
	 * @description: This non-compliant code example performs some basic currency
	 *               calculations. A dollar less 7 dimes is $0.29999999999999993
	 */
	static void _calculatePrice() {
		double dollar = 1.00;
		double dime = 0.10;
		int number = 7;
		System.out.println("A dollar less " + number + " dimes is $" + (dollar - number * dime));
	}

	/**
	 * @category Compliant solution
	 * 
	 * @description: This code correctly outputs: A dollar less 7 dimes is 30 cents
	 */
	static void calculatePrice() {
		long dollar = 100;
		long dime = 10;
		int number = 7;
		System.out.println("A dollar less " + number + " dimes is " + (dollar - number * dime) + " cents");
	}
	
	public static void main(String[] args) {
		_calculatePrice();
		calculatePrice();
	}
}
