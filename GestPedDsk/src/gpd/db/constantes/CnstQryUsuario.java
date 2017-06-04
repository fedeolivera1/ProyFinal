package gpd.db.constantes;

public interface CnstQryUsuario {

	public static final String QRY_LOGIN = "SELECT * FROM usr_dsk u WHERE u.nom_usu = ? AND u.passwd = ?";
	
	public static final String QRY_INSERT_USR = "INSERT INTO usr_dsk (nom_usu, passwd, tipo) VALUES (?, ?, ?)";
	
	public static final String QRY_UPDATE_USR = "UPDATE usr_dsk SET passwd = ? WHERE u.nom_usu = ?";
	
	public static final String QRY_DELETE_USR = "DELETE FROM usr_dsk WHERE u.nom_usu = ?";
	
}
