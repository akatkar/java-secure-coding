package secure.coding.chapter01.ids.ids06;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class MyFormatter {

	static Calendar c = new GregorianCalendar(2019, GregorianCalendar.AUGUST, 23);
	
	public static void main(String[] args) {
		
			
		String s = String.format("Date: %1$tm %1$te, %1$tY", c);
			
		System.out.println(s);
		
		// args[0] should contain the credit card expiration date
		// but can contain either %1tm, %1$te or %1$ty as malicious arguments		
		System.out.format(args[0] + "did not match. It was issued on %1$terd of some month%n", c);
		
		
		System.out.format("%s did not match. It was issued on %2$terd of some month%n", args[0], c);
		
	}
}
