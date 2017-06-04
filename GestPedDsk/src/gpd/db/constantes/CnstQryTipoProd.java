package gpd.db.constantes;

public interface CnstQryTipoProd {

	public static final String QRY_SELECT_TIPOPROD_X_ID = "SELECT id_tipo_prod,  descripcion FROM tipo_prod WHERE id_tipo_prod = ?";
	
	public static final String QRY_SELECT_TIPOPROD = "SELECT id_tipo_prod,  descripcion FROM tipo_prod ORDER BY id_tipo_prod";
	
	public static final String QRY_INSERT_TIPOPROD = "INSERT INTO tipo_prod (descripcion) VALUES (?)";
	
	public static final String QRY_UPDATE_TIPOPROD = "UPDATE tipo_prod SET descripcion = ? WHERE id_tipo_prod = ?";
	
	public static final String QRY_DELETE_TIPOPROD = "DELETE FROM tipo_prod WHERE id_tipo_prod = ?";
	
}
