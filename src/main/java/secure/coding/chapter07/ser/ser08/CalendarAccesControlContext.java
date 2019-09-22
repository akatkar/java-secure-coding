package secure.coding.chapter07.ser.ser08;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.security.PrivilegedExceptionAction;
import java.security.SecureRandom;

/**
 * @rule SER08-J. Minimize privileges before deserializing from a privileged
 *       context
 * 
 * 
 * @description Unrestricted deserializing from a privileged context allows an
 *              attacker to supply crafted input which, upon deserialization,
 *              can yield objects that the attacker would otherwise lack
 *              permissions to construct. One example is the construction of a
 *              sensitive object such as a custom class loader. Consequently,
 *              avoid deserializing from a privileged context. When
 *              deserializing requires privileges, programs must strip all
 *              permissions other than the minimum set required for the intended
 *              usage.
 * 
 * @category Noncompliant code
 *
 */
public class CalendarAccesControlContext implements Serializable {

	

}