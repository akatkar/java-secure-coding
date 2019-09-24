package secure.coding.chapter08.sec.sec07;

import java.net.URL;
import java.net.URLClassLoader;
import java.security.CodeSource;
import java.security.PermissionCollection;
import java.security.Permissions;

/**
 * @rule SEC07-J. Call the superclass�s getPermissions() method when writing a
 *       custom class loader
 * 
 * @descriptionWhen a custom class loader must override the getPermissions()
 *                  method, the implementation must consult the default system
 *                  policy by explicitly invoking the superclass�s
 *                  getPermissions() method before assigning arbitrary
 *                  permissions to the code source. A custom class loader that
 *                  ignores the superclass�s getPermissions() could load
 *                  untrusted classes with elevated privileges.
 * 
 * @category Noncompliant code
 *
 * @description This noncompliant code example shows a fragment of a custom
 *              class loader that extends the class URLClassLoader. It overrides
 *              the getPermissions() method but does not call its superclass�s
 *              more restrictive getPermissions() method.
 * 
 *              Consequently, a class defined using this custom class loader has
 *              permissions that are completely independent of those specified
 *              in the system wide policy file. In effect, the classes
 *              permissions override them.
 */
public class PermissionException extends URLClassLoader {

	public PermissionException(URL[] urls) {
		super(urls);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected PermissionCollection getPermissions(CodeSource cs) {
		PermissionCollection pc = new Permissions();
		// allow exit from the VM anytime
		pc.add(new RuntimePermission("exitVM"));
		return pc;
	}
}
