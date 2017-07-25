package gpd.exceptions;

public class NoInetConnectionException extends Exception {

	static final long serialVersionUID = 1L;
	
	public NoInetConnectionException() {
		super();
	}
	public NoInetConnectionException(String arg0) {
		super(arg0);
	}
	public NoInetConnectionException(Throwable cause) {
		super(cause);
	}
	public NoInetConnectionException(String message, Throwable cause) {
		super(message, cause);
	}
}
