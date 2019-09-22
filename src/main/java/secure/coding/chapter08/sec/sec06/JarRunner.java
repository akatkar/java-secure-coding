package secure.coding.chapter08.sec.sec06;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.net.JarURLConnection;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.jar.Attributes;

/**
 * @rule SEC06-J. Do not rely on the default automatic signature verification
 *       provided by URLClassLoader and java.util.jar
 * 
 * @description Code should only be signed when it requires elevated privileges
 *              to perform one or more tasks.
 * 
 * @category Noncompliant code
 *
 * @description This noncompliant code example demonstrates the JarRunner
 *              application, which can be used to dynamically execute a
 *              particular class residing within a JAR file (abridged version of
 *              the class in The Java Tutorials
 */
public class JarRunner {
	/**
	 * @category Compliant soulution
	 * 
	 * @usage jarsigner -verify signed-updates-jar-file.jar
	 * 
	 *        or use keychain
	 */
	public static void main(String[] args)
			throws IOException, ClassNotFoundException, NoSuchMethodException, InvocationTargetException {
		URL url = new URL(args[0]);
		// Create the class loader for the application jar file
		JarClassLoader cl = new JarClassLoader(url);
		// Get the application's main class name
		String name = cl.getMainClassName();
		// Get arguments for the application
		String[] newArgs = new String[args.length - 1];
		System.arraycopy(args, 1, newArgs, 0, newArgs.length);
		// Invoke application's main class
		cl.invokeClass(name, newArgs);
	}
}

final class JarClassLoader extends URLClassLoader {
	private URL url;

	public JarClassLoader(URL url) {
		super(new URL[] { url });
		this.url = url;
	}

	public String getMainClassName() throws IOException {
		URL u = new URL("jar", "", url + "!/");
		JarURLConnection uc = (JarURLConnection) u.openConnection();
		Attributes attr = uc.getMainAttributes();
		return attr != null ? attr.getValue(Attributes.Name.MAIN_CLASS) : null;
	}

	public void invokeClass(String name, String[] args)
			throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException {
		Class c = loadClass(name);
		Method m = c.getMethod("main", new Class[] { args.getClass() });
		m.setAccessible(true);
		int mods = m.getModifiers();
		if (m.getReturnType() != void.class || !Modifier.isStatic(mods) || !Modifier.isPublic(mods)) {
			throw new NoSuchMethodException("main");
		}
		try {
			m.invoke(null, new Object[] { args });
		} catch (IllegalAccessException e) {
			System.out.println("Access denied");
		}
	}
}