package secure.coding.chapter06.met.met09.solution;

import java.util.HashMap;
import java.util.Map;

/**
 * @rule MET09-J. Classes that define an equals() method must also define a
 *       hashCode() method
 * 
 * @description Classes that override the Object.equals() method must also
 *              override the Object. hashCode() method. The java.lang.Object
 *              class requires that any two objects that compare equal using the
 *              equals() method must produce the same integer result when the
 *              hashCode() method is invoked on the objects [ API 2006 ].
 * 
 *              The equals() method is used to determine logical equivalence
 *              between object instances. Consequently, the hashCode() method
 *              must return the same value for all equivalent objects. Failure
 *              to follow this contract is a common source of defects.
 * 
 * @category Compliant solution
 */
public final class CreditCard {

	private final int number;

	public CreditCard(int number) {
		this.number = (short) number;
	}

	@Override
	public boolean equals(Object o) {
		if (o == this) {
			return true;
		}
		if (!(o instanceof CreditCard)) {
			return false;
		}
		CreditCard cc = (CreditCard) o;
		return cc.number == number;
	}

	@Override
	public int hashCode() {
		int result = 17;
		result = 31 * result + number;
		return result;
	}

	/**
	 * @description This compliant solution overrides the hashCode() method so that
	 *              it generates the same value for any two instances that are
	 *              considered to be equal by the equals() method.
	 */
	public static void main(String[] args) {
		Map<CreditCard, String> m = new HashMap<CreditCard, String>();
		m.put(new CreditCard(100), "4111111111111111");
		System.out.println(m.get(new CreditCard(100)));
	}
}
