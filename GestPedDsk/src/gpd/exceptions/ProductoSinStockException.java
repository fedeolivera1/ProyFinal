package gpd.exceptions;

public class ProductoSinStockException extends Exception {

	static final long serialVersionUID = 1L;
	
	public ProductoSinStockException() {
		super();
	}
	public ProductoSinStockException(String arg0) {
		super(arg0);
	}
	public ProductoSinStockException(Throwable cause) {
		super(cause);
	}
	public ProductoSinStockException(String message, Throwable cause) {
		super(message, cause);
	}
}
