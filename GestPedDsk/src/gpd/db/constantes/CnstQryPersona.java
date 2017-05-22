package gpd.db.constantes;

public interface CnstQryPersona {

//	public static final String QUERY_SELECT_SEQ_PER = "SELECT NEXTVAL('seq_proveedor')";
	public static final String QUERY_SELECT_PERSFISICA = "SELECT pf.documento,pf.id_tipo_doc,pf.apellido1,pf.apellido2,pf.nombre1,pf.nombre2,pf.fecha_nac,pf.sexo,"
															+ "pf.origen,pf.sinc,pf.ult_act,p.direccion,p.puerta,p.solar,p.manzana,p.km,"
															+ "p.complemento,p.telefono,p.celular,p.email,p.tipo,p.id_loc "
														+ "FROM pers_fisica pf "
														+ "INNER JOIN persona p "
														+ "on pf.documento=p.id_persona "
														+ "where p.id_persona=?";
	public static final String QUERY_SELECT_PERSJURIDICA = "SELECT pj.nombre,pj.razon_social,pj.bps,pj.bse,pj.sinc,pj.ult_act,p.direccion,p.puerta,"
															+ "p.solar,p.manzana,p.km,p.complemento,p.telefono,p.celular,p.email,p.tipo,"
															+ "p.id_loc "
														+ "FROM pers_juridica pj "
														+ "INNER JOIN persona p "
														+ "on pj.rut=p.id_persona "
														+ "WHERE pj.rut=?";
	
	public static final String QUERY_INSERT_PERSONA = "INSERT INTO persona "
														+ "(id_persona,direccion,puerta,solar,manzana,km,complemento,telefono,celular,email,tipo,id_loc) "
														+ "VALUES (?,?,?,?,?,?,?,?,?,?,?,?)";
	public static final String QUERY_INSERT_CLIENTE = "INSERT INTO pers_fisica "
														+ "(documento,id_tipo_doc,apellido1,apellido2,nombre1,nombre2,fecha_nac,sexo,origen,sinc,ult_act) "
														+ "VALUES (?,?,?,?,?,?,?,?,?,?,?)";
	public static final String QUERY_INSERT_PROVEEDOR = "INSERT INTO pers_juridica "
														+ "(rut,nombre,razon_social,bps,bse,es_prov) "
														+ "VALUES (?,?,?,?,?,?)";

	public static final String QUERY_UPDATE_CLIENTE = "UPDATE pers_fisica SET ?,?,?,?,?,?,?,?,?,?,? WHERE documento=?";
	public static final String QUERY_UPDATE_PROVEEDOR = "UPDATE pers_juridica SET ?,?,?,?,?,?,?,?,?,?,? WHERE rut=?";
	public static final String QUERY_UPDATE_PERSONA = "UPDATE persona SET ?,?,?,?,?,?,?,?,?,?,? WHERE id_persona=?";
	
	public static final String QUERY_DELETE_CLIENTE = "DELETE FROM pers_fisica WHERE documento=?";
	public static final String QUERY_DELETE_PROVEEDOR = "DELETE FROM pers_juridica WHERE rut=?";
	public static final String QUERY_DELETE_PERSONA = "DELETE FROM persona WHERE id_persona=?";
	
}
