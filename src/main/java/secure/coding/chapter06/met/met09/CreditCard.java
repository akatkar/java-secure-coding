package secure.coding.chapter06.met.met09;

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
 * @category Noncompliant code
 * 
 * @description This noncompliant code example associates credit card numbers
 *              with strings using a HashMap and subsequently attempts to
 *              retrieve the string value associated with a credit card number.
 *              The expected retrieved value is 4111111111111111; the actual
 *              retrieved value is null.
 * 
 *              The cause of this erroneous behavior is that the CreditCard
 *              class overrides the equals() method but fails to override the
 *              hashCode() method. Consequently, the default hashCode() method
 *              returns a different value for each object, even though the
 *              objects are logically equivalent; these differing values lead to
 *              examination of different hash buckets, which prevents the get()
 *              method from finding the intended value
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

	public static void main(String[] args) {
		Map<CreditCard, String> m = new HashMap<CreditCard, String>();
		
		m.put(new CreditCard(100), "4111111111111111");
		System.out.println(m.get(new CreditCard(100)));
	}
}
