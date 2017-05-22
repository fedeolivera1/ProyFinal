package gpd.exceptions;

public class PersistenciaException extends Exception {

	static final long serialVersionUID = 1L;
	
	public PersistenciaException() {
		super();
	}
	public PersistenciaException(String arg0) {
		super(arg0);
	}
	public PersistenciaException(Throwable cause) {
		super(cause);
	}
	public PersistenciaException(String message, Throwable cause) {
		super(message, cause);
	}
}
