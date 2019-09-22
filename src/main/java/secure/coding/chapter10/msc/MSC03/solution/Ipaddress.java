package secure.coding.chapter10.msc.MSC03.solution;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * @rule MSC03-J. Never hard code sensitive information
 * 
 * @description Hard coding sensitive information, such as passwords, server IP
 *              addresses, and encryption keys can expose the information to
 *              attackers. Anyone who has access to the class files can
 *              decompile them and discover the sensitive information.
 *              Consequently, programs must not hard code sensitive information.
 * 
 * @category Compliant solution
 * 
 * @description This compliant solution retrieves the server IP address from an
 *              external file located in a secure directory. Exposure is further
 *              limited by clearing the server IP address from memory
 *              immediately after use.
 */
class Ipaddress {

	public static void main(String[] args) throws IOException {

		char[] ipAddress = new char[100];

		try (BufferedReader br = new BufferedReader(
				new InputStreamReader(new FileInputStream("serveripaddress.txt")))) {
			// Reads the server IP address into the char array,
			// returns the number of bytes read
			int n = br.read(ipAddress);
			// Validate server IP address
			// Manually clear out the server IP address
			// immediately after use
			for (int i = n - 1; i >= 0; i--) {
				ipAddress[i] = 0;
			}
		}
	}
}
