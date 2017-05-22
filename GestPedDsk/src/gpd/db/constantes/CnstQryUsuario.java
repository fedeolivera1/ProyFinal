package gpd.db.constantes;

public interface CnstQryUsuario {

	public static final String QUERY_LOGIN = "SELECT * FROM USR_DSK u WHERE u.nom_usu=? AND u.passwd=?";
}
