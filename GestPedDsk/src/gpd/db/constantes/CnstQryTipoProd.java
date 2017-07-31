package gpd.db.constantes;

public interface CnstQryTipoProd {

	public static final String QRY_SELECT_TIPOPROD_X_ID = "SELECT id_tipo_prod, descripcion, sinc, activo "
														+ "FROM tipo_prod "
														+ "WHERE id_tipo_prod = ? "
														+ "AND activo = 1";
	
	public static final String QRY_SELECT_TIPOPROD = "SELECT id_tipo_prod, descripcion, sinc, activo "
														+ "FROM tipo_prod "
														+ "WHERE activo = 1 "
														+ "ORDER BY id_tipo_prod";
	
	public static final String QRY_INSERT_TIPOPROD = "INSERT INTO tipo_prod (descripcion, sinc, activo) VALUES (?, ?, ?)";
	
	public static final String QRY_UPDATE_TIPOPROD = "UPDATE tipo_prod SET descripcion = ? WHERE id_tipo_prod = ?";
	
	public static final String QRY_UPDATE_SINC_TIPOPROD = "UPDATE tipo_prod SET sinc = ? WHERE id_tipo_prod = ?";
	
	public static final String QRY_DISABLE_TIPOPROD = "UPDATE tipo_prod SET activo = ? WHERE id_tipo_prod = ?";
	
	public static final String QRY_CTRL_UTIL_TIPOPROD = "SELECT (1) "
														+ "FROM tipo_prod tp "
														+ "INNER JOIN producto p "
														+ "ON tp.id_tipo_prod = p.id_tipo_prod "
														+ "WHERE tp.id_tipo_prod = ? "
														+ "AND p.ACTIVO = 1";
	
}
