package secure.coding.chapter05.obj.obj01;

/**
 * @rule OBJ01-J. Declare data members as private and provide accessible wrapper
 *       methods
 * 
 */
public class WidgetExample {

	/**
	 * @category Non-compliant code
	 * 
	 * @description In this noncompliant code example, the data member total keeps
	 *              track of the total number of elements as they are added and
	 *              removed from a container using the methods add() and remove()
	 *              respectively.
	 */
	class _Widget {
		public int total; // Number of elements

		void add() {
			if (total < Integer.MAX_VALUE) {
				total++;
				// . ..
			} else {
				throw new ArithmeticException("Overflow");
			}
		}

		void remove() {
			if (total > 0) {
				total--;
				// . ..
			} else {
				throw new ArithmeticException("Overflow");
			}
		}
	}

	/**
	 * @category Compliant solution
	 * 
	 * @description This compliant solution declares total as private and provides a
	 *              public accessor so that the required member can be accessed
	 *              beyond the current package. The add() and remove() methods
	 *              modify its value without violating any class invariants.
	 */
	public class Widget {
		private int total; // Declared private

		public int getTotal() {
			return total;
		}

		// definitions for add() and remove() remain the same
		void add() {
			if (total < Integer.MAX_VALUE) {
				total++;
				// . ..
			} else {
				throw new ArithmeticException("Overflow");
			}
		}

		void remove() {
			if (total > 0) {
				total--;
				// . ..
			} else {
				throw new ArithmeticException("Overflow");
			}
		}
	}

}
