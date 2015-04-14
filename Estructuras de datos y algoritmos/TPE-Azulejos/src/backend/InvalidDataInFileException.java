package backend;

public class InvalidDataInFileException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public InvalidDataInFileException(String string) {
		super(string);
	}

	public InvalidDataInFileException() {
		super();
	}

}
