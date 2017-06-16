package gpd.db.constantes;

public interface CnstQryTranLinea {

	public static final String QRY_SELECT_TRANLINEA_XTRANSAC = "SELECT nro_transac, id_producto, cantidad, precio_unit "
														+ "FROM tran_linea "
														+ "WHERE nro_transac = ?";
	
	public static final String QRY_SELECT_TRANLINEA_XID = "SELECT nro_transac, id_producto, cantidad, precio_unit "
														+ "FROM tran_linea "
														+ "WHERE nro_transac = ? "
														+ "AND id_producto = ?";
	
	public static final String QRY_INSERT_TRANLINEA = "INSERT INTO tran_linea "
														+ "(nro_transac, id_producto, cantidad, precio_unit) "
														+ "VALUES (?, ?, ?, ?)";
	
	public static final String QRY_DELETE_TRANLINEA = "DELETE FROM tran_linea WHERE nro_transac = ?";
}
