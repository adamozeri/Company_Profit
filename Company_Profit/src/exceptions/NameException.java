package exceptions;

public class NameException extends GeneralException{

	public NameException() {
		super("Name must be letters");
	}

	public NameException(String msg) {
		super(msg);
	}
}
