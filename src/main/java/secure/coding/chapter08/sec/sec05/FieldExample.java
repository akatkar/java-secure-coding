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
 * @description In this noncompliant code example, the private fields i and j
 *              can be modified using reflection via a Field object.
 *              Furthermore, any class can modify these fields using reflection
 *              via the zeroField() method. However, only class FieldExample can
 *              modify these fields without the use of reflection.
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

	public void zeroField(String fieldName) {
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
		
		String[] fields = {"i","j"};
		for (String arg : fields) {
			fe.zeroField(arg);
			System.out.println(fe.toString());
		}
	}
}
