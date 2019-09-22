package secure.coding.chapter09.fio.fio01;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.nio.channels.SeekableByteChannel;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.nio.file.attribute.FileAttribute;
import java.nio.file.attribute.PosixFilePermission;
import java.nio.file.attribute.PosixFilePermissions;
import java.util.HashSet;
import java.util.Set;

/**
 * @rule FIO01-J. Create files with appropriate access permissions
 * 
 * 
 * @description Files on multiuser systems are generally owned by a particular
 *              user. The owner of the file can specify which other users on the
 *              system should be allowed to access the contents of these files.
 * 
 *              These file systems use a privileges and permissions model to
 *              protect file access. When a file is created, the file access
 *              permissions dictate who may access or operate on the file. When
 *              a program creates a file with insufficiently restrictive access
 *              permissions, an attacker may read or modify the file before the
 *              program can modify the permissions. Consequently, files must be
 *              created with access permissions that prevent unauthorized file
 *              access.
 * 
 */

public class FilePermission {
	/**
	 * @category Noncompliant code
	 *
	 * @description The constructors for FileOutputStream and FileWriter do not
	 *              allow the programmer to explicitly specify file access
	 *              permissions. In this noncompliant code example, the access
	 *              permissions of any file created are implementation-defined and
	 *              may not prevent unauthorized access.
	 * 
	 * @param filename
	 * @throws IOException
	 */
	public void example1(String filename) throws IOException {
		Writer out = new FileWriter(filename);
		out.close();
	}

	/**
	 * @throws IOException 
	 * @category Compliant solution (Java SE 7, POSIX)
	 *
	 * @description The Java SE 7 new I/O facility (java.nio) provides classes for
	 *              managing file access permissions. Additionally, many of the
	 *              methods and constructors that create files accept an argument
	 *              allowing the program to specify the initial file permissions.
	 * 
	 *              The Files.newByteChannel() method allows a file to be created
	 *              with specific permissions. This method is platform-independent,
	 *              but the actual permissions are platform-specific. This compliant
	 *              solution defines sufficiently restrictive permissions for POSIX
	 *              platforms.
	 * 
	 */
	public static void example2(String filename) throws IOException {
		Path file = new File(filename).toPath();
		// Throw exception rather than overwrite existing file
		Set<OpenOption> options = new HashSet<>();
		options.add(StandardOpenOption.CREATE_NEW);
		options.add(StandardOpenOption.APPEND);
		
		// File permissions should be such that only user may read/write file
		Set<PosixFilePermission> perms = PosixFilePermissions.fromString("rw-------");
		FileAttribute<Set<PosixFilePermission>> attr = PosixFilePermissions.asFileAttribute(perms);
		
		try (SeekableByteChannel sbc = Files.newByteChannel(file, options, attr)) {
			// write data
		}
	}
}