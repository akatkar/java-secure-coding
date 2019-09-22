package secure.coding.chapter04.num.num10;

import java.math.BigDecimal;

/**
 * @rule: NUM10-J. Do not construct BigDecimal objects from floating-point
 *        literals
 * 
 * @description: Literal decimal floating-point numbers cannot always be
 *               precisely represented as an IEEE 754 floating-point value.
 *               Consequently, the BigDecimal(double val) constructor must not
 *               be passed a floating-point literal as an argument when doing so
 *               results in an unacceptable loss of precision.
 */
public class ConstructBigDecimal {
	
	public static void main(String[] args) {
		// prints 0.1000000000000000055511151231257827021181583404541015625
		// when run in FP-strict mode
		System.out.println(new BigDecimal(0.1));
		
		// prints 0.1
		// when run in FP-strict mode
		System.out.println(new BigDecimal("0.1"));
	}
}
