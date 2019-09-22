package secure.coding.chapter10.msc.MSC00.solution;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

import javax.net.ssl.SSLServerSocket;
import javax.net.ssl.SSLServerSocketFactory;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;

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
 * @category Compliant solution
 *
 * @description This compliant solution uses SSLSocket to protect packets using
 *              the SSL/TLS security protocols.
 * 
 *              Programs that use SSLSocket will block indefinitely if they
 *              attempt to connect to a port that is not using SSL. Similarly, a
 *              program that does not use SSLSocket will block when attempting
 *              to establish a connection through a port that does use SSL.
 */
// Exception handling has been omitted for the sake of brevity
class EchoServer {
	public static void main(String[] args) throws IOException {

		SSLServerSocketFactory sslServerSocketFactory = (SSLServerSocketFactory) SSLServerSocketFactory.getDefault();

		try (SSLServerSocket sslServerSocket = (SSLServerSocket) sslServerSocketFactory.createServerSocket(9999)) {
			SSLSocket sslSocket = (SSLSocket) sslServerSocket.accept();
			PrintWriter out = new PrintWriter(sslSocket.getOutputStream(), true);
			BufferedReader in = new BufferedReader(new InputStreamReader(sslSocket.getInputStream()));
			String inputLine;
			while ((inputLine = in.readLine()) != null) {
				System.out.println(inputLine);
				out.println(inputLine);
			}
		}
	}
}

class EchoClient {
	public static void main(String[] args) throws IOException {

		SSLSocketFactory sslSocketFactory = (SSLSocketFactory) SSLSocketFactory.getDefault();

		try (SSLSocket sslSocket = (SSLSocket) sslSocketFactory.createSocket("localhost", 9999)) {
			PrintWriter out = new PrintWriter(sslSocket.getOutputStream(), true);
			BufferedReader in = new BufferedReader(new InputStreamReader(sslSocket.getInputStream()));
			BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
			String userInput;
			while ((userInput = stdIn.readLine()) != null) {
				out.println(userInput);
				System.out.println(in.readLine());
			}
		}
	}
}