package gpd.exceptions;

public class ParsersException extends Exception {

	static final long serialVersionUID = 1L;
	
	public ParsersException() {
		super();
	}
	public ParsersException(String arg0) {
		super(arg0);
	}
	public ParsersException(Throwable cause) {
		super(cause);
	}
	public ParsersException(String message, Throwable cause) {
		super(message, cause);
	}
}
