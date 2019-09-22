package secure.coding.chapter11.err.err00.solution;

public class ExceptionReporter { 
	// Exception reporter that prints the exception
	// to the console (used as default)
	private static final Reporter PrintException = new Reporter() {
		public void report(Throwable t) {
			System.err.println(t.toString());
		}
	};
	// Stores the default reporter.
	// The default reporter can be changed by the user.
	private static Reporter Default = PrintException;

	// Helps change the default reporter back to
	// PrintException in the future
	public static Reporter getPrintException() {
		return PrintException;
	}

	public static Reporter getExceptionReporter() {
		return Default;
	}

	// May throw a SecurityException (which is unchecked)
	public static void setExceptionReporter(Reporter reporter) {
		// Custom permission
		ExceptionReporterPermission perm = new ExceptionReporterPermission("exc.reporter");
		SecurityManager sm = System.getSecurityManager();
		if (sm != null) {
			// Check whether the caller has appropriate permissions
			sm.checkPermission(perm);
		}
		// Change the default exception reporter
		Default = reporter;
	}
}