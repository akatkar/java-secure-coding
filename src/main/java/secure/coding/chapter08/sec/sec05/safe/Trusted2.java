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
 * @category Compliant solution (Access Reduction)
 *
 * @description This compliant solution reduces the access of the create()
 *              method to package-private, preventing a caller from outside the
 *              package from using that method to bypass the language access
 *              checks to create an instance of the Trusted class. A caller that
 *              can create a Trusted class instance using reflection can simply
 *              call the Trusted() constructor instead.
 */

public class Trusted2 {

	// package private constructor
	Trusted2() {
	}

	static <T> T create(Class<T> c) throws InstantiationException, IllegalAccessException {
		return c.newInstance();
	}
}
