package gpd.db.constantes;

public interface CnstQryTranLinea {

	public static final String QRY_INSERT_TRANLINEA = "INSERT INTO tran_linea "
														+ "(nro_transac, id_producto, cantidad, precio_unit) "
														+ "VALUES (?, ?, ?, ?)";
	
}
