package secure.coding.chapter01.ids.ids11;

import java.text.Normalizer;
import java.text.Normalizer.Form;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TagFilter {
	
	public static void main(String[] args) {
	
		// "\uFEFF" is a non-character code point
		String maliciousInput = "<scr" + "\uFDEF" + "ipt>";
		
		String sb = filteringBad(maliciousInput);
		System.out.println(sb);
		
		String sg = filtering(maliciousInput);
		System.out.println(sg);
		
	}

	private static String filteringBad(String str) {

		String s = Normalizer.normalize(str, Form.NFKC);
		// Input validation
		Pattern pattern = Pattern.compile("<script>");
		Matcher matcher = pattern.matcher(s);
		if (matcher.find()) {
			System.out.println("Found black listed tag");
			throw new IllegalArgumentException("Invalid Input");
		} 
		// Deletes all non-valid characters
		return s.replaceAll("[^\\p{ASCII}]", "");
	}
	

	private static String filtering(String str) {
		String s = Normalizer.normalize(str, Form.NFKC);
		// Deletes all non-valid characters
		s = s.replaceAll("[^\\p{ASCII}]", "");
		System.out.println("before validation string became: " + s);
		
		// Input validation
		Pattern pattern = Pattern.compile("<script>");
		Matcher matcher = pattern.matcher(s);
		if (matcher.find()) {
			System.err.println("Found black listed tag");
			throw new IllegalArgumentException("Invalid Input");
		}
		return s;
	}
}
