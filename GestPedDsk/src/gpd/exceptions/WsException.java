package gpd.exceptions;

public class WsException extends Exception {

static final long serialVersionUID = 1L;
	
	public WsException() {
		super();
	}
	public WsException(String arg0) {
		super(arg0);
	}
	public WsException(Throwable cause) {
		super(cause);
	}
	public WsException(String message, Throwable cause) {
		super(message, cause);
	}
	
}
