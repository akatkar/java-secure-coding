package secure.coding.chapter01.ids.ids07;

import java.io.InputStream;

public class DirListNoneCompliant {
	
	public static void main(String[] args) throws Exception {
		System.setProperty("dir", "dummy & echo bad");
		String dir = System.getProperty("dir");
		Runtime rt = Runtime.getRuntime();
		Process proc = rt.exec("cmd.exe /C dir " + dir);
		int result = proc.waitFor();
		if (result != 0) {
			System.out.println("process error: " + result);
		}
		try (InputStream in = (result == 0) ? proc.getInputStream() : proc.getErrorStream()) {
			int c;
			while ((c = in.read()) != -1) {
				System.out.print((char) c);
			}
		}
	}

}
