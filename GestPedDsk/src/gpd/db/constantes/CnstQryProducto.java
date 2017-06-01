package gpd.db.constantes;

public interface CnstQryProducto {

	//tipo_prod
	public static final String QRY_SELECT_TIPOPROD_X_ID = "SELECT id_tipo_prod,  descripcion FROM tipo_prod WHERE id_tipo_prod = ?";
	public static final String QRY_SELECT_TIPOPROD = "SELECT id_tipo_prod,  descripcion FROM tipo_prod ORDER BY id_tipo_prod";
	public static final String QRY_INSERT_TIPOPROD = "INSERT INTO tipo_prod (descripcion) VALUES (?)";
	public static final String QRY_UPDATE_TIPOPROD = "UPDATE tipo_prod SET descripcion = ? WHERE id_tipo_prod = ?";
	public static final String QRY_DELETE_TIPOPROD = "DELETE FROM tipo_prod WHERE id_tipo_prod = ?";
	
	//producto
	public static final String QRY_SELECT_PROD_XID = "SELECT id_producto, id_tipo_prod, codigo, nombre, descripcion, stock_min, precio, sinc, ult_act "
													+ "FROM producto p "
													+ "WHERE p.id_producto = ?";
	public static final String QRY_SELECT_PROD_X_TIPOPROD = "SELECT id_prodcuto, id_tipo_prod, codigo, nombre, descripcion, stock_min, precio, sinc, ult_act "
													+ "FROM producto p "
													+ "WHERE p.id_tipo_prod = ?";
	
	public static final String QRY_INSERT_PROD = "INSERT INTO producto "
													+ "(id_tipo_prod, codigo, nombre, descripcion, stock_min, precio, sinc, ult_act) "
													+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
	public static final String QRY_UPDATE_PROD = "UPDATE producto SET id_tipo_prod = ?, codigo = ?, descripcion = ?, stock_min = ?, precio = ?, sinc = ?, ult_act = ? "
													+ "WHERE id_producto = ?";
	public static final String QRY_DELETE_PROD = "DELETE FROM producto WHERE id_producto = ?";
	
}
