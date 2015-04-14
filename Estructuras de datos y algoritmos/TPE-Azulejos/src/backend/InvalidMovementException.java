package backend;

public class InvalidMovementException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public InvalidMovementException(String string) {
		super(string);
	}

	public InvalidMovementException() {
		super();
	}
}
