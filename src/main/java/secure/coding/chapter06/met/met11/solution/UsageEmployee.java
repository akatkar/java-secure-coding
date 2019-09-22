package secure.coding.chapter06.met.met11.solution;

import java.util.Calendar;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @rule MET11-J. Ensure that keys used in comparison operations are immutable
 * 
 * @description Objects that serve as keys in ordered sets and maps should be
 *              immutable. When some fields must be mutable, the equals(),
 *              hashCode(), and compareTo() methods must consider only immutable
 *              state when comparing objects. Violations of this rule can
 *              produce inconsistent orderings in collections. The documentation
 *              of java.util.Interface Set<E> and java.util.Interface Map<K,V>
 *              warns against this.
 * 
 * @category Compliant solution
 * 
 * @description This compliant solution adds a final field employeeID that is
 *              immutable after initialization. The equals() method compares
 *              Employee objects on the basis of this field.
 */

// Mutable class Employee
class Employee {
	private final long employeeID; // Unique for each Employee
	private String name;
	private double salary;

	Employee(long employeeId, String empName, double empSalary) {
		this.employeeID = employeeId;
		this.name = empName;
		this.salary = empSalary;
	}

	public void setEmployeeName(String empName) {
		this.name = empName;
	}

	public void Salary(double empSalary) {
		this.salary = empSalary;
	}

	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (employeeID ^ (employeeID >>> 32));
		return result;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof Employee)) {
			return false;
		}
		Employee emp = (Employee) obj;
		return emp.employeeID == employeeID;
	}

}

public final class UsageEmployee {
	public static void main(String[] args) {
		// Client code
		Map<Employee, Calendar> map = new ConcurrentHashMap<>();
		// . ..
	}
}
