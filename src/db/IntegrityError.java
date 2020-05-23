package db;

public class IntegrityError extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	public IntegrityError(String msg) {
		super(msg);
	}
}
