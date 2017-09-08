package gpd.db.constantes;

public interface CnstQryProducto {

	public static final String QRY_SEARCH_PROD = "SELECT p.id_producto, p.id_tipo_prod, p.codigo, p.nombre, p.descripcion, p.stock_min, "
													+ "p.precio, p.apl_iva, p.id_unidad, p.cant_unidad, p.sinc, p.ult_act, p.activo "
													+ "FROM producto p INNER JOIN tipo_prod tp "
													+ "ON p.id_tipo_prod = tp.id_tipo_prod "
													+ "WHERE (p.id_tipo_prod = ? OR -1 = ?) "
													+ "AND (p.codigo ILIKE ? OR '' = ?) "
													+ "AND (p.nombre ILIKE ? OR '' = ?) "
													+ "AND (p.descripcion ILIKE ? OR '' = ?) "
													+ "AND p.activo = 1";
	
	public static final String QRY_SELECT_PROD_XID = "SELECT id_producto, id_tipo_prod, codigo, nombre, descripcion, stock_min, apl_iva, id_unidad, cant_unidad, precio, sinc, ult_act, activo "
													+ "FROM producto p "
													+ "WHERE p.id_producto = ? "
													+ "AND activo = 1";
	
	public static final String QRY_SELECT_PROD_X_TIPOPROD = "SELECT id_producto, id_tipo_prod, codigo, nombre, descripcion, stock_min, "
													+ "apl_iva, id_unidad, cant_unidad, precio, sinc, ult_act, activo "
													+ "FROM producto "
													+ "WHERE id_tipo_prod = ? "
													+ "AND activo = 1";
	
	public static final String QRY_SELECT_PROD_NO_SINC = "SELECT id_producto, id_tipo_prod, codigo, nombre, descripcion, stock_min, "
													+ "apl_iva, id_unidad, cant_unidad, precio, sinc, ult_act, activo "
													+ "FROM producto "
													+ "WHERE sinc = 'N' "
													+ "AND ult_act::date BETWEEN ? AND ?";
	
	public static final String QRY_INSERT_PROD = "INSERT INTO producto "
													+ "(id_tipo_prod, codigo, nombre, descripcion, stock_min, apl_iva, id_unidad, cant_unidad, precio, sinc, ult_act, activo) "
													+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
	
	public static final String QRY_UPDATE_PROD = "UPDATE producto SET id_tipo_prod = ?, codigo = ?, nombre = ?, descripcion = ?, stock_min = ?, apl_iva = ?, "
													+ "id_unidad = ?, cant_unidad = ?, precio = ?, sinc = ?, ult_act = ? "
													+ "WHERE id_producto = ?";
	
	public static final String QRY_DESACT_PROD = "UPDATE producto SET activo = ?, sinc = ? WHERE id_producto = ?";
	
	public static final String QRY_UPDATE_SINC_PROD = "UPDATE producto SET sinc = ?, ult_act = ? "
													+ "WHERE id_producto = ? "
													+ "AND activo = 1";
	
	public static final String QRY_CHECK_EXIST_PROD = "SELECT (1) AS existe FROM producto WHERE id_producto = ?";

}
