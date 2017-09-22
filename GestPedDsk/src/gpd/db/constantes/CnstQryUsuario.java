package gpd.db.constantes;

public interface CnstQryUsuario {

	public static final String QRY_LOGIN = "SELECT nom_usu, passwd, tipo FROM usr_dsk WHERE nom_usu = ? AND passwd = ?";
	
	public static final String QRY_SELECT_USR_XID = "SELECT nom_usu, tipo FROM usr_dsk WHERE nom_usu = ?";
	
	public static final String QRY_SELECT_USR = "SELECT * FROM usr_dsk";
	
	public static final String QRY_INSERT_USR = "INSERT INTO usr_dsk (nom_usu, passwd, tipo) VALUES (?, ?, ?)";
	
	public static final String QRY_UPDATE_USR = "UPDATE usr_dsk SET passwd = ?, tipo = ? WHERE nom_usu = ?";

	public static final String QRY_UPDATE_USR_SINPASS = "UPDATE usr_dsk SET tipo = ? WHERE nom_usu = ?";
	
	public static final String QRY_DELETE_USR = "DELETE FROM usr_dsk WHERE nom_usu = ?";
	
	public static final String QRY_CHECK_EXIST_USR = "SELECT 1 FROM usr_dsk WHERE nom_usu ILIKE(?)";
	
}
