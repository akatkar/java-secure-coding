package secure.coding.chapter01.ids.ids02;

import java.io.File;

public class CanonicalPathName {

	private static void processInput(String input) {
		File f = new File(System.getProperty("user.home") + System.getProperty("file.separator") + input);

		String absPath = f.getAbsolutePath();
		
		System.out.println(absPath);
	}
	
	public static void main(String[] args) {

		processInput("");
		processInput("../../System");
		processInput("test");
		
	}
}
