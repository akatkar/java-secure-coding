package secure.coding.chapter05.obj.obj00;

/**
 * 
 * @rule OBJ00-J. Limit extensibility of classes and methods with invariants to
 *       trusted subclasses only
 * 
 * @category Noncompliant code
 *       Malicious subclassing of java.math.BigInteger
 */
public class BigInteger extends java.math.BigInteger {

	private static final long serialVersionUID = 1L;

	private int value;

	public BigInteger(String str) {
		super(str);
		value = Integer.parseInt(str);
	}

	/**
	 * This malicious BigInteger class is clearly mutable because of the setValue()
	 * method. 
	 */
	public void setValue(int value) {
		this.value = value;
	}

	/**
	 * the modPow() method is subject to precision loss.
	 */
	@Override
	public BigInteger modPow(java.math.BigInteger exponent, java.math.BigInteger m) {
		this.value = ((int) (Math.pow(this.doubleValue(), exponent.doubleValue()))) % m.intValue();
		return this;
	}

	public static void main(String[] args) {
		BigInteger msg = new BigInteger("123");
		msg = msg.modPow(new BigInteger("2"), new BigInteger("8")); // Always returns 1
		System.out.println(msg);
	}
}