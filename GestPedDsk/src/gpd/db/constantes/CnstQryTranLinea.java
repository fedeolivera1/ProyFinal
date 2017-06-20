package gpd.db.constantes;

public interface CnstQryTranLinea {

	public static final String QRY_SELECT_TRANLINEA_XTRANSAC = "SELECT tl.nro_transac, tl.id_producto, tl.cantidad, tl.precio_unit "
														+ "FROM tran_linea tl "
														+ "INNER JOIN transaccion t "
														+ "ON tl.nro_transac = t.nro_transac "
														+ "WHERE tl.nro_transac = ?";
	
	public static final String QRY_SELECT_TRANLINEA_XID = "SELECT tl.nro_transac, tl.id_producto, tl.cantidad, tl.precio_unit "
														+ "FROM tran_linea tl "
														+ "INNER JOIN transaccion t "
														+ "ON tl.nro_transac = t.nro_transac "
														+ "WHERE tl.nro_transac = ? "
														+ "AND tl.id_producto = ?";
	
	public static final String QRY_INSERT_TRANLINEA = "INSERT INTO tran_linea "
														+ "(nro_transac, id_producto, cantidad, precio_unit) "
														+ "VALUES (?, ?, ?, ?)";
	
	public static final String QRY_DELETE_TRANLINEA = "DELETE FROM tran_linea WHERE nro_transac = ?";
}
