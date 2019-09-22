package secure.coding.chapter01.ids.ids02.solution;

import java.io.File;
import java.io.IOException;

public class CanonicalPathName {

	private static void processInput(String input) throws IOException {
		File f = new File(System.getProperty("user.home") + System.getProperty("file.separator") + input);
		String canonicalPath = f.getCanonicalPath();
		System.out.println(canonicalPath);
	}

	public static void main(String[] args) throws IOException {

		processInput("");
		processInput("../../System");
		processInput("test");

	}
}
