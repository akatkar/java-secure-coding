package secure.coding.chapter08.sec.sec05;

import java.lang.reflect.Field;

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
 * @category Noncompliant code
 *
 * @description In this noncompliant code example, the programmer intends that
 *              code outside the Safe package should be prevented from creating
 *              a new instance of an arbitrary class. Consequently, the Trusted
 *              class uses a package-private constructor. However, because the
 *              API is public, an attacker can pass Trusted.class itself as an
 *              argument to the create() method and bypass the language access
 *              checks that prevent code outside the package from invoking the
 *              package-private constructor. The create() method returns an
 *              unauthorized instance of the Trusted class.
 */

import secure.coding.chapter08.sec.sec05.safe.*;

public class Attack {
	public static void main(String[] args) throws InstantiationException, IllegalAccessException {
		
		System.out.println(Trusted.create(Trusted.class)); // succeeds
//		System.out.println(Trusted2.create(Trusted2.class)); // Access is not permitted. Compile error
		System.out.println(Trusted3.create(Trusted3.class)); 
		
	}
}