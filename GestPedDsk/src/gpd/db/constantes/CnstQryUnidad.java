package gpd.db.constantes;

public interface CnstQryUnidad {

	public static final String QRY_SELECT_UNI_X_ID = "SELECT id_unidad, nombre, sinc, activo "
												+ "FROM unidad "
												+ "WHERE id_unidad = ? AND activo = 1";
	
	public static final String QRY_SELECT_UNI = "SELECT id_unidad, nombre, sinc,activo "
												+ "FROM unidad "
												+ "WHERE activo = 1 "
												+ "ORDER BY id_unidad";
	
	public static final String QRY_INSERT_UNI = "INSERT INTO unidad (nombre, sinc, activo) VALUES (?, ?, ?)";
	
	public static final String QRY_UPDATE_UNI = "UPDATE unidad SET nombre = ? WHERE id_unidad = ?";

	public static final String QRY_UPDATE_SINC_UNI = "UPDATE unidad SET sinc = ? WHERE id_unidad = ?";
	
	public static final String QRY_DELETE_UNI = "UPDATE unidad SET activo = ? WHERE id_unidad = ?";
	
	public static final String QRY_CTRL_UTIL_UNI = "SELECT (1) "
												+ "FROM unidad u "
												+ "INNER JOIN producto p "
												+ "ON u.id_unidad = p.id_unidad "
												+ "WHERE u.id_unidad = ? "
												+ "AND p.ACTIVO = 1";
	
}
