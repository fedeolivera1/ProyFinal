package gpd.db.constantes;

public interface CnstQryPersona {

//	public static final String QUERY_SELECT_SEQ_PER = "SELECT NEXTVAL('seq_proveedor')";
	public static final String QRY_SELECT_PF_XID = "SELECT pf.documento,pf.id_tipo_doc,pf.apellido1,pf.apellido2,pf.nombre1,pf.nombre2,pf.fecha_nac,pf.sexo,"
															+ "pf.origen,pf.sinc,pf.ult_act,p.direccion,p.puerta,p.solar,p.manzana,p.km,"
															+ "p.complemento,p.telefono,p.celular,p.email,p.tipo,p.id_loc "
														+ "FROM pers_fisica pf "
														+ "INNER JOIN persona p "
														+ "on pf.documento=p.id_persona "
														+ "where p.id_persona=?";
	public static final String QRY_SELECT_PJ_XID = "SELECT pj.nombre,pj.razon_social,pj.bps,pj.bse,pj.sinc,pj.ult_act,p.direccion,p.puerta,"
															+ "p.solar,p.manzana,p.km,p.complemento,p.telefono,p.celular,p.email,p.tipo,"
															+ "p.id_loc "
														+ "FROM pers_juridica pj "
														+ "INNER JOIN persona p "
														+ "on pj.rut=p.id_persona "
														+ "WHERE pj.rut=?";
	
	public static final String QRY_INSERT_PERS = "INSERT INTO persona "
														+ "(id_persona,direccion,puerta,solar,manzana,km,complemento,telefono,celular,email,fecha_reg,tipo,id_loc) "
														+ "VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?)";
	public static final String QRY_INSERT_PF = "INSERT INTO pers_fisica "
														+ "(documento,id_tipo_doc,apellido1,apellido2,nombre1,nombre2,fecha_nac,sexo,origen,sinc,ult_act) "
														+ "VALUES (?,?,?,?,?,?,?,?,?,?,?)";
	public static final String QRY_INSERT_PJ = "INSERT INTO pers_juridica "
														+ "(rut,nombre,razon_social,bps,bse,es_prov) "
														+ "VALUES (?,?,?,?,?,?)";

	public static final String QRY_UPDATE_PERS = "UPDATE persona "
														+ "SET id_persona=?,direccion=?,puerta=?,solar=?,manzana=?,km=?,complemento=?,telefono=?,celular=?,email=?,fecha_reg=?,id_loc=? "
														+ "WHERE id_persona=?";//INFO: tipo no va a proposito, no se puede cambiar tipo de persona
	public static final String QRY_UPDATE_PF = "UPDATE pers_fisica "
														+ "SET documento=?,id_tipo_doc=?,apellido1=?,apellido2=?,nombre1=?,nombre2=?,fecha_nac=?,sexo=?,origen=?,sinc=?,ult_act=? "
														+ "WHERE documento=?";
	public static final String QRY_UPDATE_PJ = "UPDATE pers_juridica "
														+ "SET rut=?,nombre=?,razon_social=?,bps=?,bse=?,es_prov=?, "
														+ "WHERE rut=?";
	
	public static final String QRY_DELETE_PF = "DELETE FROM pers_fisica WHERE documento=?";
	public static final String QRY_DELETE_PJ = "DELETE FROM pers_juridica WHERE rut=?";
	public static final String QRY_DELETE_PERS = "DELETE FROM persona WHERE id_persona=?";
	
	public static final String QRY_SELECT_TIPODOC = "SELECT id_tipo_doc,nombre FROM tipo_doc order by id_tipo_doc";
	public static final String QRY_SELECT_TIPODOC_XID = "SELECT id_tipo_doc,nombre FROM tipo_doc WHERE id_tipo_doc=?";
	public static final String QRY_INSERT_TIPODOC = "INSERT INTO tipo_doc (nombre) VALUES (?)";
	public static final String QRY_UPDATE_TIPODOC = "UPDATE tipo_doc SET nombre=? WHERE id_tipo_doc=?";
	public static final String QRY_DELETE_TIPODOC = null;
	
}
