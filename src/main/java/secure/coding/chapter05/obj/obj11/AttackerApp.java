package secure.coding.chapter05.obj.obj11;

public class AttackerApp {
	// Invoke class and gain access
	// to the restrictive features
	public static void main(String[] args) {
		Interceptor i = Interceptor.get(); // stolen instance
		// Can store the stolen object even though this should have printed
		// “Invalid Object!”
		Storage.store(i);
		// Now invoke any instance method of BankOperations class
		i.greet();

		UserApp.main(args); // Invoke the original UserApp
	}
}
