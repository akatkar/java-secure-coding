package secure.coding.chapter10.msc.MSC00;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * /**
 * 
 * @rule MSC00-J. Use SSLSocket rather than Socket for secure data exchange
 * 
 * @description Programs must use the javax.net.ssl.SSLSocket class rather than
 *              the java.net.Socket class when transferring sensitive data over
 *              insecure communication channels. The class SSLSocket provides
 *              security protocols such as Secure Sockets Layer/Transport Layer
 *              Security (SSL/TLS) to ensure that the channel is not vulnerable
 *              to eavesdropping and malicious tampering. The principal
 *              protections included in SSLSocket that are not provided by the
 *              Socket class are:
 * 
 *              - Integrity protection: SSL protects against modification of
 *              messages by an active wire tapper.
 * 
 *              - Authentication: In most modes, SSL provides peer
 *              authentication. Servers are usually authenticated, and clients
 *              may be authenticated as requested by servers.
 * 
 *              - Confidentiality (privacy protection): In most modes, SSL
 *              encrypts data being sent between client and server. This
 *              protects the confidentiality of data so that passive wire
 *              tappers cannot observe sensitive data such as financial or
 *              personal information.
 * 
 * @category Noncompliant code
 *
 * @description This noncompliant code example shows the use of regular sockets
 *              for a server application that fails to protect sensitive
 *              information in transit. The insecure code for the corresponding
 *              client application follows the server’s code.
 */
// Exception handling has been omitted for the sake of brevity
class EchoServer {
	public static void main(String[] args) throws IOException {
		
		try(ServerSocket serverSocket= new ServerSocket(9999)) {
			Socket socket = serverSocket.accept();
			PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
			BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			String inputLine;
			while ((inputLine = in.readLine()) != null) {
				System.out.println(inputLine);
				out.println(inputLine);
			}
		}
	}
}

class EchoClient {
	public static void main(String[] args) throws UnknownHostException, IOException {
		
		try(Socket socket = new Socket("localhost", 9999)) {
			PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
			BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
			String userInput;
			while ((userInput = stdIn.readLine()) != null) {
				out.println(userInput);
				System.out.println(in.readLine());
			}
		}
	}
}
