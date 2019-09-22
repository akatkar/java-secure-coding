package secure.coding.chapter11.err.err00.solution;

import java.security.Permission;

public class ExceptionReporterPermission extends Permission {

	public ExceptionReporterPermission(String name) {
		super(name);
	}

	@Override
	public boolean equals(Object obj) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public String getActions() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int hashCode() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean implies(Permission permission) {
		// TODO Auto-generated method stub
		return false;
	}
	
}
