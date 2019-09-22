package secure.coding.chapter04.num.num06;

/**
 * @rule: NUM06-J. Use the strictfp modifier for floating-point calculation
 *        consistency across platforms
 *
 * @description: Programs that require consistent results from floating-point
 *               operations across different JVMs and platforms must use the
 *               strictfp modifier. This modifier requires the JVM and the
 *               platform to behave as though all floating-point computations
 *               were performed using values limited to those that can be
 *               represented by a standard Java float or double, guaranteeing
 *               that the result of the computations will match exactly across
 *               all JVMs and platforms.
 */
public class FloatingPointModifier {

	/**
	 * @category Non-compliant code
	 * 
	 * @description: This noncompliant code example does not mandate FP-strict
	 *               computation. Double. MAX_VALUE is multiplied by 1.1 and reduced
	 *               back by dividing by 1.1, according to the evaluation order. If
	 *               Double.MAX_VALUE is the maximum value permissible by the
	 *               platform, the calculation will yield the result infinity
	 */
	static class NonCompliantExample {
		public static void calculate() {
			double d = Double.MAX_VALUE;
			System.out.println("This value \"" + ((d * 1.1) / 1.1) + "\" cannot be represented as double.");
		}
	}

	/**
	 * @category Compliant solution
	 * 
	 * @description: For maximum portability, use the strictfp modifier within an
	 *               expression (class, method, or interface) to guarantee that
	 *               intermediate results do not vary because of implementation
	 *               defined behavior. The calculation in this compliant solution is
	 *               guaranteed to produce infinity because of the intermediate
	 *               overflow condition, regardless of what floating-point support
	 *               is provided by the platform.
	 */
	strictfp static class CompliantExample {
		public static void calculate() {
			double d = Double.MAX_VALUE;
			System.out.println("This value \"" + ((d * 1.1) / 1.1) + "\" cannot be represented as double.");
		}
	}

	double d = 0.0;

	/**
	 * @category Non-compliant code
	 * 
	 * @description: Native floating-point hardware provides greater range than
	 *               double. On these platforms, the JIT is permitted to use
	 *               floating-point registers to hold values of type float or type
	 *               double (in the absence of the strictfp modifier), even though
	 *               the registers support values with greater exponent range than
	 *               that of the primitive types. Consequently, conversion from
	 *               float to double can cause an effective loss of magnitude.
	 */
	public void _example() {
		float f = Float.MAX_VALUE;
		float g = Float.MAX_VALUE;
		this.d = f * g;
		System.out.println("d (" + this.d + ") might not be equal to " + (f * g));
	}

	/**
	 * @category Compliant solution
	 * 
	 * @description: This compliant solution uses the strictfp keyword to require
	 *               exact conformance with standard Java floating point.
	 *               Consequently, the intermediate value of both computations of f
	 *               * g is identical to the value stored in this.d, even on
	 *               platforms that support extended range exponents.
	 */
	public strictfp void example() {
		float f = Float.MAX_VALUE;
		float g = Float.MAX_VALUE;
		this.d = f * g;
		System.out.println("d (" + this.d + ") might not be equal to " + (f * g));
	}

	public static void main(String[] args) {
		FloatingPointModifier object = new FloatingPointModifier();
		object._example();
		object.example();
	}
}
