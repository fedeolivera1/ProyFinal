package gpd.db.constantes;

public interface CnstQryPersona {

	public static final String QRY_SELECT_PF_XID = "SELECT pf.documento, pf.id_tipo_doc, pf.apellido1, pf.apellido2, pf.nombre1, pf.nombre2, pf.fecha_nac, pf.sexo, "
															+ "p.direccion, p.puerta, p.solar, p.manzana, p.km, p.complemento, p.telefono, p.celular, "
															+ "p.email, p.fecha_reg, p.tipo, p.id_loc, p.origen, p.sinc, p.ult_act "
														+ "FROM pers_fisica pf "
														+ "INNER JOIN persona p "
														+ "on pf.documento = p.id_persona "
														+ "where p.id_persona = ?";
	
	public static final String QRY_SELECT_PJ_XID = "SELECT pj.rut, pj.nombre, pj.razon_social, pj.bps, pj.bse, pj.es_prov, p.direccion, p.puerta, p.solar, "
															+ "p.manzana, p.km, p.complemento, p.telefono, p.celular, p.email, p.fecha_reg, p.tipo, "
															+ "p.id_loc, p.origen, p.sinc, p.ult_act "
														+ "FROM pers_juridica pj "
														+ "INNER JOIN persona p "
														+ "on pj.rut = p.id_persona "
														+ "WHERE pj.rut = ?";
	
	public static final String QRY_SEARCH_PF = "SELECT pf.documento, pf.id_tipo_doc, pf.apellido1, pf.apellido2, pf.nombre1, pf.nombre2, pf.fecha_nac, pf.sexo, "
															+ "p.direccion, p.puerta, p.solar, p.manzana, p.km, p.complemento, p.telefono, p.celular, "
															+ "p.email, p.fecha_reg, p.tipo, p.id_loc, p.origen, p.sinc, p.ult_act "
														+ "FROM pers_fisica pf INNER JOIN persona p "
														+ "ON pf.documento = p.id_persona "
														+ "WHERE (pf.documento = ? OR -1 = ?) "
														+ "AND ( (pf.apellido1 LIKE ? OR '' = ?) "
															+ "AND (pf.apellido2 LIKE ? OR '' = ?) "
															+ "AND (pf.nombre1 LIKE ? OR '' = ?) "
															+ "AND (pf.nombre2 LIKE ? OR '' = ?) ) "
														+ "AND (pf.sexo = ? OR '' = ?) "
														+ "AND (p.direccion LIKE ? OR '' = ?) "
														+ "AND (p.telefono LIKE ? OR '' = ?) "
														+ "AND (p.celular LIKE ? OR '' = ?) "
														+ "AND (p.email LIKE ? OR '' = ?) "
														+ "AND (p.id_loc = ? OR -1 = ?) ";
	
	public static final String QRY_SEARCH_PF_GEN = "SELECT pf.documento, pf.id_tipo_doc, pf.apellido1, pf.apellido2, pf.nombre1, pf.nombre2, pf.fecha_nac, pf.sexo, "
															+ "p.direccion, p.puerta, p.solar, p.manzana, p.km, p.complemento, p.telefono, p.celular, "
															+ "p.email, p.fecha_reg, p.tipo, p.id_loc, p.origen, p.sinc, p.ult_act "
														+ "FROM pers_fisica pf INNER JOIN persona p "
														+ "ON pf.documento = p.id_persona "
														+ "WHERE (pf.documento = ?) "
														+ "OR ( (pf.apellido1 LIKE ?) "
															+ "OR (pf.apellido2 LIKE ?) "
															+ "OR (pf.nombre1 LIKE ?) "
															+ "OR (pf.nombre2 LIKE ?) "
															+ "OR (p.direccion LIKE ?) "
															+ "OR (p.telefono LIKE ?) "
															+ "OR (p.celular LIKE ?) "
															+ "OR (p.email LIKE ?) ) "
														+ "AND (p.id_loc = ? OR -1 = ?) ";
	
	public static final String QRY_SEARCH_PJ = "SELECT pj.rut, pj.nombre, pj.razon_social, pj.bps, pj.bse, pj.es_prov, p.direccion, p.puerta, p.solar, "
															+ "p.manzana, p.km, p.complemento, p.telefono, p.celular, p.email, p.fecha_reg, p.tipo, "
															+ "p.id_loc, p.origen, p.sinc, p.ult_act "
														+ "FROM pers_juridica pj INNER JOIN persona p "
														+ "ON pj.rut = p.id_persona "
														+ "WHERE (pj.rut = ? OR -1 = ?) "
														+ "AND (pj.nombre LIKE ? OR '' = ?) "
														+ "AND (pj.razon_social LIKE ? OR '' = ?) "
														+ "AND (pj.bps LIKE ? OR '' = ?) "
														+ "AND (pj.bse LIKE ? OR '' = ?) "
														+ "AND (pj.es_prov = ? OR '' = ?) "
														+ "AND (p.direccion LIKE ? OR '' = ?) "
														+ "AND (p.telefono LIKE ? OR '' = ?) "
														+ "AND (p.celular LIKE ? OR '' = ?) "
														+ "AND (p.email LIKE ? OR '' = ?) "
														+ "AND (p.id_loc = ? OR -1 = ?) ";
	
	public static final String QRY_SEARCH_PJ_GEN = "SELECT pj.rut, pj.nombre, pj.razon_social, pj.bps, pj.bse, pj.es_prov, p.direccion, p.puerta, p.solar, "
														+ "p.manzana, p.km, p.complemento, p.telefono, p.celular, p.email, p.fecha_reg, p.tipo, "
														+ "p.id_loc, p.origen, p.sinc, p.ult_act "
														+ "FROM pers_juridica pj INNER JOIN persona p "
														+ "ON pj.rut = p.id_persona "
														+ "WHERE (pj.rut = ?) "
														+ "OR ( (pj.nombre LIKE ?) "
															+ "OR (pj.razon_social LIKE ?) "
															+ "OR (pj.bps LIKE ?) "
															+ "OR (pj.bse LIKE ?) "
															+ "OR (p.direccion LIKE ?) "
															+ "OR (p.telefono LIKE ?) "
															+ "OR (p.celular LIKE ?) "
															+ "OR (p.email LIKE ?) ) "
														+ "AND (p.id_loc = ? OR -1 = ?) ";
	
//	public static final String QRY_SEARCH_PERS_GENERIC = "";
	
	public static final String QRY_SELECT_PJ = "SELECT pj.rut, pj.nombre, pj.razon_social, pj.bps, pj.bse, pj.es_prov, p.direccion, p.puerta, p.solar, "
															+ "p.manzana, p.km, p.complemento, p.telefono, p.celular, p.email, p.fecha_reg, p.tipo, "
															+ "p.id_loc, p.origen, p.sinc, p.ult_act "
														+ "FROM pers_juridica pj INNER JOIN persona p "
														+ "ON pj.rut = p.id_persona "
														+ "WHERE (pj.es_prov = ? OR ' ' =  ?)";
	
	public static final String QRY_SELECT_PERS_GENERIC = "SELECT tipo FROM persona WHERE id_persona = ?";

	public static final String QRY_INSERT_PERS = "INSERT INTO persona "
														+ "(id_persona, direccion, puerta, solar, manzana, km, complemento, telefono, celular, email, fecha_reg, tipo, id_loc, origen, sinc, ult_act) "
														+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
	
	public static final String QRY_INSERT_PF = "INSERT INTO pers_fisica "
														+ "(documento, id_tipo_doc, apellido1, apellido2, nombre1, nombre2, fecha_nac, sexo) "
														+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
	
	public static final String QRY_INSERT_PJ = "INSERT INTO pers_juridica "
														+ "(rut, nombre, razon_social, bps, bse, es_prov) "
														+ "VALUES (?, ?, ?, ?, ?, ?)";

	public static final String QRY_UPDATE_PERS = "UPDATE persona "
														+ "SET id_persona = ?, direccion = ?, puerta = ?, solar = ?, manzana = ?, km = ?, complemento = ?, telefono = ?, celular = ?, email = ?, fecha_reg = ?, id_loc = ?, origen = ?, sinc = ?, ult_act = ? "
														+ "WHERE id_persona = ?";//INFO: tipo no va a proposito, no se puede cambiar tipo de persona
	
	public static final String QRY_UPDATE_PF = "UPDATE pers_fisica "
														+ "SET documento = ?, id_tipo_doc = ?, apellido1 = ?, apellido2 = ?, nombre1 = ?, nombre2 = ?, fecha_nac = ?, sexo = ? "
														+ "WHERE documento = ?";
	
	public static final String QRY_UPDATE_PJ = "UPDATE pers_juridica "
														+ "SET rut = ?, nombre = ?, razon_social = ?, bps = ?, bse = ?, es_prov = ? "
														+ "WHERE rut = ?";
	
	public static final String QRY_DELETE_PF = "DELETE FROM pers_fisica WHERE documento = ?";
	
	public static final String QRY_DELETE_PJ = "DELETE FROM pers_juridica WHERE rut = ?";
	
	public static final String QRY_DELETE_PERS = "DELETE FROM persona WHERE id_persona = ?";
	
}
