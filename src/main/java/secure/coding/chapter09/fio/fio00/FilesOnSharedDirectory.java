package secure.coding.chapter09.fio.fio00;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.FileSystem;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.PosixFileAttributes;
import java.nio.file.attribute.PosixFilePermission;
import java.nio.file.attribute.UserPrincipal;
import java.nio.file.attribute.UserPrincipalLookupService;
import java.util.Set;

/**
 * @rule FIO00-J. Do not operate on files in shared directories
 * 
 * 
 * @description Multiuser systems allow multiple users with different privileges
 *              to share a file system. Each user in such an environment must be
 *              able to determine which files are shared and which are private,
 *              and each user must be able to enforce these decisions.
 * 
 *              Unfortunately, a wide variety of file system vulnerabilities can
 *              be exploited by an attacker to gain access to files for which
 *              they lack sufficient privileges, particularly when operating on
 *              files that reside in shared directories in which multiple users
 *              may create, move, or delete files. Privilege escalation is also
 *              possible when these programs run with elevated privileges. A
 *              number of file system properties and capabilities can be
 *              exploited by an attacker, including file links, device files,
 *              and shared file access. To prevent vulnerabilities, a program
 *              must operate only on files in secure directories.
 * 
 */

public class FilesOnSharedDirectory {
	/**
	 * @category Noncompliant code
	 *
	 * @description In this noncompliant code example, an attacker could specify the
	 *              name of a locked device or a first in, first out (FIFO) file,
	 *              causing the program to hang when opening the file.
	 * 
	 * @param filename
	 *            (provided by user)
	 */
	public void example1(String filename) {
		InputStream in = null;
		try {
			in = new FileInputStream(filename);
			// ...
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} finally {
			try {
				if (in != null) {
					in.close();
				}
			} catch (IOException x) {
				// handle error
			}
		}
	}

	/**
	 * @category Noncompliant code Example (Java SE 7)
	 *
	 * @description This noncompliant code example uses the try-with-resources
	 *              statement from Java SE 7 to open the file. While this guarantees
	 *              the file’s successful closure if an exception is thrown, it is
	 *              subject to the same vulnerabilities as the previous example.
	 * 
	 * @param filename
	 *            (provided by user)
	 */
	public void example2(String filename) {
		Path path = new File(filename).toPath();
		try (InputStream in = Files.newInputStream(path)) {
			// read file
		} catch (IOException x) {
			// handle error
		}
	}

	/**
	 * @category Noncompliant code Example (Java SE 7: isRegularFile())
	 *
	 * @description This noncompliant code example first checks that the file is a
	 *              regular file (using the new Java SE 7 NIO2 APIs) before opening
	 *              it.
	 * 
	 *              This test can still be circumvented by a symbolic link. By
	 *              default, the readAttributes() method follows symbolic links and
	 *              reads the file attributes of the final target of the link. The
	 *              result is that the program may reference a file other than the
	 *              one intended.
	 * 
	 * @param filename
	 *            (provided by user)
	 */
	public void example3(String filename) {
		Path path = new File(filename).toPath();
		try {
			BasicFileAttributes attr = Files.readAttributes(path, BasicFileAttributes.class);
			// Check
			if (!attr.isRegularFile()) {
				System.out.println("Not a regular file");
				return;
			}
			// other necessary checks
			// Use
			try (InputStream in = Files.newInputStream(path)) {
				// read file
			}
		} catch (IOException x) {
			// handle error
		}
	}

	/**
	 * @category Noncompliant code Example (Java SE 7: NOFOLLOW_LINKS)
	 *
	 * @description This noncompliant code example checks the file by calling the
	 *              readAttributes() method with the NOFOLLOW_LINKS link option to
	 *              prevent the method from following symbolic links. This allows
	 *              the detection of symbolic links because the isRegularFile()
	 *              check is carried out on the symbolic link file and not on the
	 *              final target of the link.
	 * 
	 *              This code is still vulnerable to a time-of-check, time-of-use
	 *              (TOCTOU) race condition. For example, an attacker can replace
	 *              the regular file with a file link or device file after the code
	 *              has completed its checks but before it opens the file.
	 * 
	 * @param filename
	 *            (provided by user)
	 */
	public void example4(String filename) {
		Path path = new File(filename).toPath();
		try {
			BasicFileAttributes attr = Files.readAttributes(path, BasicFileAttributes.class, LinkOption.NOFOLLOW_LINKS);
			// Check
			if (!attr.isRegularFile()) {
				System.out.println("Not a regular file");
				return;
			}
			// other necessary checks
			// Use
			try (InputStream in = Files.newInputStream(path)) {
				// read file
			}
		} catch (IOException x) {
			// handle error
		}
	}

	/**
	 * @category Noncompliant code Example (Java SE 7: Check-Use-Check)
	 *
	 * @description This noncompliant code example performs the necessary checks and
	 *              then opens the file. After opening the file, it performs a
	 *              second check to make sure that the file has not been moved and
	 *              that the file opened is the same file that was checked. This
	 *              reduces the chance that an attacker has changed the file between
	 *              checking and then opening the file.
	 * 
	 *              While this code goes to great lengths to prevent an attacker
	 *              from successfully tricking it into opening the wrong file, it
	 *              still has several vulnerabilities:
	 * 
	 *              - The TOCTOU race condition still exists between the first check
	 *              and open. During this race window, an attacker can replace the
	 *              regular file with a symbolic link or other nonregular file. The
	 *              second check detects this race condition but does not eliminate
	 *              it.
	 * 
	 *              - An attacker could subvert this code by letting the check
	 *              operate on a regular file, substituting the nonregular file for
	 *              the open, and then resubstituting the regular file to circumvent
	 *              the second check. This vulnerability exists because Java lacks
	 *              any mechanism to obtain file attributes from a file by any means
	 *              other than the file name, and the binding of the file name to a
	 *              file object is reasserted every time the file name is used in an
	 *              operation. Consequently, an attacker can still swap a benign
	 *              file for a nefarious file, such as a symbolic link.
	 * 
	 *              - A system with hard links allows an attacker to construct a
	 *              malicious file that is a hard link to a protected file. Hard
	 *              links cannot be reliably detected by a program and can foil any
	 *              canonicalization attempts, which are prescribed by rule IDS02-J.
	 * 
	 * @param filename
	 *            (provided by user)
	 */
	public void example5(String filename) {
		Path path = new File(filename).toPath();
		try {
			BasicFileAttributes attr = Files.readAttributes(path, BasicFileAttributes.class, LinkOption.NOFOLLOW_LINKS);
			Object fileKey = attr.fileKey();
			// Check
			if (!attr.isRegularFile()) {
				System.out.println("Not a regular file");
				return;
			}
			// other necessary checks

			// Use
			try (InputStream in = Files.newInputStream(path)) {
				// Check
				BasicFileAttributes attr2 = Files.readAttributes(path, BasicFileAttributes.class,
						LinkOption.NOFOLLOW_LINKS);
				Object fileKey2 = attr2.fileKey();
				if (fileKey != fileKey2) {
					System.out.println("File has been tampered with");
				}
				// read file
			}
		} catch (IOException x) {
			// handle error
		}
	}

	/**
	 * @category Compliant solution (POSIX, Java SE 7: Secure Directory)
	 *
	 * @description Because of the potential for race conditions and the inherent
	 *              accessibility of shared directories, files must be operated on
	 *              only in secure directories. Because programs may run with
	 *              reduced privileges and lack the facilities to construct a secure
	 *              directory, a program may need to throw an exception if it
	 *              determines that a given path name is not in a secure directory.
	 * 
	 *              Following is a POSIX-specific implementation of an
	 *              isInSecureDir() method. This method ensures that the supplied
	 *              file and all directories above it are owned by either the user
	 *              or the system administrator, that each directory lacks write
	 *              access for any other users, and that directories above the given
	 *              file may not be deleted or renamed by any other users (except
	 *              the system administrator).
	 * 
	 */
	public static boolean isInSecureDir(Path file) {
		return isInSecureDir(file, null);
	}

	public static boolean isInSecureDir(Path file, UserPrincipal user) {
		return isInSecureDir(file, null, 5);
	}

	/**
	 * Indicates whether file lives in a secure directory relative to the program's
	 * user
	 * 
	 * @param file
	 *            Path to test
	 * @param user
	 *            User to test. If null, defaults to current user
	 * @param symlinkDepth
	 *            Number of symbolic links allowed
	 * @return true if file's directory is secure
	 */
	public static boolean isInSecureDir(Path file, UserPrincipal user, int symlinkDepth) {
		if (!file.isAbsolute()) {
			file = file.toAbsolutePath();
			if (symlinkDepth <= 0) {
				// Too many levels of symbolic links
				return false;
			}
			// Get UserPincipal for specified user and superuser
			FileSystem fileSystem = Paths.get(file.getRoot().toString()).getFileSystem();
			UserPrincipalLookupService upls = fileSystem.getUserPrincipalLookupService();
			UserPrincipal root = null;
			try {
				root = upls.lookupPrincipalByName("root");
				if (user == null) {
					user = upls.lookupPrincipalByName(System.getProperty("user.name"));
				}
				if (root == null || user == null) {
					return false;
				}
			} catch (IOException x) {
				return false;
			}
			// If any parent dirs (from root on down) are not secure,
			// dir is not secure
			for (int i = 1; i <= file.getNameCount(); i++) {
				Path partialPath = Paths.get(file.getRoot().toString(), file.subpath(0, i).toString());
				try {
					if (Files.isSymbolicLink(partialPath)) {
						if (!isInSecureDir(Files.readSymbolicLink(partialPath), user, symlinkDepth - 1)) {
							// Symbolic link, linked-to dir not secure
							return false;
						}
					} else {
						UserPrincipal owner = Files.getOwner(partialPath);
						if (!user.equals(owner) && !root.equals(owner)) {
							// dir owned by someone else, not secure
							return false;
						}
						PosixFileAttributes attr = Files.readAttributes(partialPath, PosixFileAttributes.class);
						Set<PosixFilePermission> perms = attr.permissions();
						if (perms.contains(PosixFilePermission.GROUP_WRITE)
								|| perms.contains(PosixFilePermission.OTHERS_WRITE)) {
							// someone else can write files, not secure
							return false;
						}
					}
				} catch (IOException x) {
					return false;
				}
			}
		}
		return true;
	}
}