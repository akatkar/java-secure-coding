package secure.coding.chapter05.obj.obj10;

import java.io.Serializable;

/**
 * @rule OBJ10-J. Do not use public static nonfinal variables
 * 
 * @description Improper use of public static fields can also result in
 *              type-safety issues. For example, untrusted code can supply an
 *              unexpected subtype with malicious methods when the variable is
 *              defined to be of a more general type, such as java.lang.Object.
 *              As a result, classes must not contain nonfinal public static
 *              fields.
 */
public class FunctionTable implements Serializable{

	/**
	 * @category Noncompliant code
	 * 
	 * @description An attacker can replace the function table as follows:
	 *              FunctionTable.functions = new_table;
	 */
	public static FuncLoader functions;
	
	/**
	 * @category Compliant solution
	 * 
	 */
	public static final FuncLoader FUNCTIONS = new FuncLoader();
		
	/**
	 * @category Noncompliant code
	 */
	public static long _serialVersionUID = 1973473122623778747L;
	
	/**
	 * @category Compliant solution
	 */
	public static final long serialVersionUID = 1973473122623778747L;
}

class FuncLoader {

}