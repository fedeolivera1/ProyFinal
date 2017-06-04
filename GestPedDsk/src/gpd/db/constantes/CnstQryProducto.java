package gpd.db.constantes;

public interface CnstQryProducto {

	public static final String QRY_SEARCH_PROD = "SELECT p.id_producto, p.id_tipo_prod, p.codigo, p.nombre, p.descripcion, p.stock_min, p.precio, p.sinc, p.ult_act "
													+ "FROM producto p INNER JOIN tipo_prod tp "
													+ "ON p.id_tipo_prod = tp.id_tipo_prod "
													+ "WHERE (p.id_tipo_prod = ? OR -1 = ?) "
													+ "AND (p.codigo = ? OR '' = ?) "
													+ "AND (p.nombre = ? OR '' = ?) "
													+ "AND (p.descripcion = ? OR '' = ?) ";
			
	public static final String QRY_SELECT_PROD_XID = "SELECT id_producto, id_tipo_prod, codigo, nombre, descripcion, stock_min, precio, sinc, ult_act "
													+ "FROM producto p "
													+ "WHERE p.id_producto = ?";
	
	public static final String QRY_SELECT_PROD_X_TIPOPROD = "SELECT id_prodcuto, id_tipo_prod, codigo, nombre, descripcion, stock_min, precio, sinc, ult_act "
													+ "FROM producto p "
													+ "WHERE p.id_tipo_prod = ?";
	
	public static final String QRY_INSERT_PROD = "INSERT INTO producto "
													+ "(id_tipo_prod, codigo, nombre, descripcion, stock_min, precio, sinc, ult_act) "
													+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
	
	public static final String QRY_UPDATE_PROD = "UPDATE producto SET id_tipo_prod = ?, codigo = ?, nombre = ?, descripcion = ?, stock_min = ?, precio = ?, sinc = ?, ult_act = ? "
													+ "WHERE id_producto = ?";
	
	public static final String QRY_DELETE_PROD = "DELETE FROM producto WHERE id_producto = ?";
	
}
