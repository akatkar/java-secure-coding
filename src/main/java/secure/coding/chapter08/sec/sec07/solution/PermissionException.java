package secure.coding.chapter08.sec.sec07.solution;

import java.net.URL;
import java.net.URLClassLoader;
import java.security.CodeSource;
import java.security.PermissionCollection;
import java.security.Permissions;

/**
 * @rule SEC07-J. Call the superclass’s getPermissions() method when writing a
 *       custom class loader
 * 
 * @descriptionWhen a custom class loader must override the getPermissions()
 *                  method, the implementation must consult the default system
 *                  policy by explicitly invoking the superclass’s
 *                  getPermissions() method before assigning arbitrary
 *                  permissions to the code source. A custom class loader that
 *                  ignores the superclass’s getPermissions() could load
 *                  untrusted classes with elevated privileges.
 * 
 *                  /**
 * @category Compliant solution
 *
 * @description In this compliant solution, the getPermissions() method calls
 *              super.getPermissions(). As a result, the default system wide
 *              security policy is applied, in addition to the custom policy.
 */
public class PermissionException extends URLClassLoader {

	public PermissionException(URL[] urls) {
		super(urls);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected PermissionCollection getPermissions(CodeSource cs) {
		PermissionCollection pc = super.getPermissions(cs);
		// allow exit from the VM anytime
		pc.add(new RuntimePermission("exitVM"));
		return pc;
	}
}
