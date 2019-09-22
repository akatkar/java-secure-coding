package secure.coding.chapter10.msc.MSC03;

/**
 * @rule  MSC03-J. Never hard code sensitive information
 * 
 * @description Hard coding sensitive information, such as passwords, server IP
 *              addresses, and encryption keys can expose the information to
 *              attackers. Anyone who has access to the class files can
 *              decompile them and discover the sensitive information.
 *              Consequently, programs must not hard code sensitive information.
 * 
 * @category Noncompliant code
 * 
 * @description This noncompliant code example includes a hard-coded server IP
 *              address in a constant String.
 * 
 *              A malicious user can use the javap -c IPaddress command to
 *              disassemble the class and discover the hard-coded server IP
 *              address. The output of the disassembler reveals the server IP
 *              address 172.16.254.1 in clear text:
 */

// Compiled from "IPaddress.java"
// class IPaddress extends java.lang.Object{
// java.lang.String ipAddress;
// IPaddress();
//   Code:
// 		0: aload_0
//		1: invokespecial #1; //Method java/lang/Object."<init>":()V
//		4: aload_0
//		5: new #2; //class java/lang/String
//		8: dup
//		9: ldc #3; //String 172.16.254.1
//		11: invokespecial #4; //Method java/lang/String."<init>":(Ljava/lang/String;)V
//		14: putfield #5; //Field ipAddress:Ljava/lang/String;
//		17: return
// public static void main(java.lang.String[]);
//	 Code:
//		0: return
//}
class Ipaddress {

	String ipAddress = new String("172.16.254.1");

	public static void main(String[] args) {
		// ...
	}
}
