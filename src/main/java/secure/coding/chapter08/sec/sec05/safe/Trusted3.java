package secure.coding.chapter08.sec.sec05.safe;

/**
 * @rule SEC05-J. Do not use reflection to increase accessibility of classes,
 *       methods, or fields
 * 
 * @description The Java reflection API includes a method that enables fields
 *              that are normally inaccessible to Platform Security (SEC) be
 *              accessed under reflection.
 * 
 *              {@code 
 *              Field fields[] = SomeClass.getDeclaredFields(); 
 *              for (Field field : fields) { 
 *              if (!Modifier.isPublic(field.getModifiers())) {
 *              field.setAccessible(true); } System.out.print("Field: " +
 *              field.getName()); System.out.println(", value: " +
 *              field.get(someObject)); }}
 * 
 *              Use of reflection complicates security analysis and can easily
 *              introduce security vulnerabilities. Consequently, programmers
 *              should avoid using the reflection APIs when it is feasible to do
 *              so. Exercise extreme caution when the use of reflection is
 *              necessary. In particular, reflection must not be used to provide
 *              access to classes, methods, and fields unless these items are
 *              already accessible without the use of reflection. For example,
 *              the use of reflection to access or modify fields is not allowed
 *              unless those fields are already accessible and modifiable by
 *              other means, such as through getter and setter methods.
 * 
 * @category Compliant solution (Security Manager Check)
 *
 * @description This compliant solution uses the getConstructors() method to
 *              check whether the class provided as an argument has public
 *              constructors. The security issue is irrelevant when public
 *              constructors are present because such constructors are already
 *              accessible even to malicious code.
 * 
 *              When public constructors are absent, the create() method uses
 *              the security manager’s checkPackageAccess() method to ensure
 *              that all callers in the execution chain have sufficient
 *              permissions to access classes and their respective members
 *              defined in package Safe.
 */

public class Trusted3 {

	Trusted3() { }

	public static <T> T create(Class<T> c) throws InstantiationException, IllegalAccessException {
		if (c.getConstructors().length == 0) { // No public constructors
			SecurityManager sm = System.getSecurityManager();
			if (sm != null) {
				// throws an exception when access is not allowed
				sm.checkPackageAccess("Safe");
			}
		}
		return c.newInstance(); // Safe to return
	}
}
