package gpd.exceptions;

public class UsuarioNoExisteException extends Exception {

	static final long serialVersionUID = 1L;
	
	public UsuarioNoExisteException() {
		super();
	}
	public UsuarioNoExisteException(String arg0) {
		super(arg0);
	}
	public UsuarioNoExisteException(Throwable cause) {
		super(cause);
	}
	public UsuarioNoExisteException(String message, Throwable cause) {
		super(message, cause);
	}
}
