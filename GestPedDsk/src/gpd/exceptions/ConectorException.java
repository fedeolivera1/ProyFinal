package gpd.exceptions;

public class ConectorException extends Exception {

	static final long serialVersionUID = 1L;
	
	public ConectorException() {
		super();
	}
	public ConectorException(String arg0) {
		super(arg0);
	}
	public ConectorException(Throwable cause) {
		super(cause);
	}
	public ConectorException(String message, Throwable cause) {
		super(message, cause);
	}
}
