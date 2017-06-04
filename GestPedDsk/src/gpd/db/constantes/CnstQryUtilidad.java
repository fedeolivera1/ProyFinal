package gpd.db.constantes;

public interface CnstQryUtilidad {

public static final String QRY_SELECT_UTIL_X_ID = "SELECT id_util, descripcion, porc FROM utilidad WHERE id_utilidad = ?";
	
	public static final String QRY_SELECT_UTIL = "SELECT id_util, descripcion, porc FROM utilidad ORDER BY id_util";
	
	public static final String QRY_INSERT_UTIL = "INSERT INTO utilidad (descripcion, porc) VALUES (?, ?)";
	
	public static final String QRY_UPDATE_UTIL = "UPDATE utilidad SET descripcion = ?, porc = ? WHERE id_util = ?";
	
	public static final String QRY_DELETE_UTIL = "DELETE FROM utilidad WHERE id_util = ?";
	
}
