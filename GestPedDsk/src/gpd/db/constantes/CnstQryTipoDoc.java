package gpd.db.constantes;

public interface CnstQryTipoDoc {

	public static final String QRY_SELECT_TIPODOC = "SELECT id_tipo_doc, nombre FROM tipo_doc order by id_tipo_doc";
	
	public static final String QRY_SELECT_TIPODOC_XID = "SELECT id_tipo_doc, nombre FROM tipo_doc WHERE id_tipo_doc = ?";
	
	public static final String QRY_INSERT_TIPODOC = "INSERT INTO tipo_doc (nombre) VALUES (?)";
	
	public static final String QRY_UPDATE_TIPODOC = "UPDATE tipo_doc SET nombre = ? WHERE id_tipo_doc = ?";
	
	public static final String QRY_DELETE_TIPODOC = "DELETE FROM tipo_doc WHERE id_tipo_doc = ?";
	
}
