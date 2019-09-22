package secure.coding.chapter05.obj.obj11;

public class Interceptor extends BankOperations {
	private static Interceptor stealInstance = null;

	public static Interceptor get() {
		try {
			new Interceptor();
		} catch (Exception ex) {
			/* ignore exception */}
		try {
			synchronized (Interceptor.class) {
				while (stealInstance == null) {
					System.gc();
					Interceptor.class.wait(10);
				}
			}
		} catch (InterruptedException ex) {
			return null;
		}
		return stealInstance;
	}

	public void finalize() {
		synchronized (Interceptor.class) {
			stealInstance = this;
			Interceptor.class.notify();
		}
		System.out.println("Stole the instance in finalize of " + this);
	}
}