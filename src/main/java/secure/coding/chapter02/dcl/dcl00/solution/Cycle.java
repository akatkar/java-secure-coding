package secure.coding.chapter02.dcl.dcl00.solution;

public class Cycle {
	private final int balance;
	// Random deposit
	private static final int deposit = (int) (Math.random() * 100);
	// Inserted after initialization of required fields
	private static final Cycle c = new Cycle();

	public Cycle() {
		balance = deposit - 10; // Subtract processing fee
	}

	public static void main(String[] args) {
		System.out.println("The account balance is: " + c.balance);
	}
}