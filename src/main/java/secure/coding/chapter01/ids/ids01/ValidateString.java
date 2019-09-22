package secure.coding.chapter01.ids.ids01;

import java.text.Normalizer;
import java.text.Normalizer.Form;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ValidateString {

	private static String normalizeAndValidate(String s) {

		Pattern pattern = Pattern.compile("[<>]");
		Matcher matcher = pattern.matcher(s);
		if (matcher.find()) {
			// Found black listed tag
			throw new IllegalStateException();
		} else {
			System.out.println("input is valid");
		}
		s = Normalizer.normalize(s, Form.NFKC);
		return s;
	}

	public static void main(String[] args) {
		// Assume s is user controlled
		// \uFE64 is normalized to < and
		// \uFE65 is normalized to > using NFKC

		String input = "\uFE64" + "script" + "\uFE65";
		System.out.println("unnormalized string : " + input);
		input = normalizeAndValidate(input);
		System.out.println("normalized string   : " + input);

	}
}
