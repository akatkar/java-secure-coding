package secure.coding.chapter08.sec.sec05.solution;

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
 * @category Compliant solution
 *
 * @description When you must use reflection, make sure that the immediate
 *              caller (method) is isolated from hostile code by declaring it
 *              private or final, as in this compliant solution.
 * 
 *              Note that when language access checks are overridden through use
 *              of java.lang. reflect.Field.setAccessible(), the immediate
 *              caller gains access even to the private fields of other classes.
 *              
 *              Consequently, never grant the permission ReflectPermission with
 *              action suppressAccessChecks; this ensures that the security
 *              manager will block attempts to access private fields of other
 *              classes.
 */

public class FieldExample {

	private int i = 3;
	private int j = 4;

	public String toString() {
		return "FieldExample: i=" + i + ", j=" + j;
	}

	public void zeroI() {
		this.i = 0;
	}

	private void zeroField(String fieldName) {
		try {
			Field f = this.getClass().getDeclaredField(fieldName);
			// Subsequent access to field f passes language access checks
			// because zeroField() could have accessed the field via
			// ordinary field references
			f.setInt(this, 0);
			// log appropriately or throw sanitized exception; see EXC06-J
		} catch (NoSuchFieldException ex) {
			// report to handler
		} catch (IllegalAccessException ex) {
			// report to handler
		}
	}

	public static void main(String[] args) {
		FieldExample fe = new FieldExample();
		System.out.println(fe.toString());

		String[] fields = { "i", "j" };
		for (String arg : fields) {
			fe.zeroField(arg);
			System.out.println(fe.toString());
		}
	}
}
