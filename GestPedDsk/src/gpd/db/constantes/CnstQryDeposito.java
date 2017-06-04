package gpd.db.constantes;

public interface CnstQryDeposito {

	public static final String QRY_SELECT_DEPOSITO_X_ID = "SELECT nro_dep, nombre FROM deposito WHERE nro_dep = ?";
	
	public static final String QRY_SELECT_DEPOSITO = "SELECT nro_dep, nombre FROM deposito ORDER BY nro_dep";
	
	public static final String QRY_INSERT_DEPOSITO = "INSERT INTO deposito (nombre) VALUES (?)";
	
	public static final String QRY_UPDATE_DEPOSITO = "UPDATE deposito SET nombre = ? WHERE nro_dep = ?";
	
	public static final String QRY_DELETE_DEPOSITO = "DELETE FROM deposito WHERE nro_dep = ?";
	
}
