package secure.coding.chapter05.obj.obj11;

public class Storage {
	private static BankOperations bop;

	public static void store(BankOperations bo) {
		// Only store if it is initialized
		if (bop == null) {
			if (bo == null) {
				System.out.println("Invalid object!");
				System.exit(1);
			}
			bop = bo;
		}
	}
}