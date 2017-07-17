package gpd.db.constantes;

public interface CnstQryTransaccion {

	public static final String SEC_TRANSAC = "transaccion_nro_transac_seq";
	
	public static final String QRY_INSERT_TRANSACCION = "INSERT INTO transaccion "
															+ "(nro_transac, id_persona, operacion, fecha_hora, sub_total, iva, total, estado_act) "
															+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
	
	public static final String QRY_UPDATE_TRAN_EST = "UPDATE transaccion SET estado_act = ? "
															+ "WHERE nro_transac = ?";
			
	public static final String QRY_SELECT_TRAN_XID = "SELECT t.nro_transac, t.id_persona, t.operacion, t.fecha_hora, t.sub_total, t.iva, t.total, t.estado_act "
															+ "FROM transaccion t "
															+ "INNER JOIN tran_estado te "
															+ "ON t.nro_transac = te.nro_transac "
															+ "AND t.estado_act = te.estado "
															+ "WHERE t.nro_transac = ?";
	
	public static final String QRY_SELECT_TRAN_XPERS = "SELECT t.nro_transac, t.id_persona, t.operacion, t.fecha_hora, t.sub_total, t.iva, t.total, t.estado_act "
															+ "FROM transaccion t "
															+ "INNER JOIN tran_estado te "
															+ "ON t.nro_transac = te.nro_transac "
															+ "AND t.estado_act = te.estado "
															+ "WHERE id_persona = ? "
															+ "AND (operacion = ? OR '' = ?) "
															+ "AND (t.estado_act = ? OR '' = ?) "
															+ "ORDER BY te.fecha_hora DESC";
	
	public static final String QRY_SELECT_TRAN_XPERIODO = "SELECT t.nro_transac, t.id_persona, t.operacion, t.fecha_hora, t.sub_total, t.iva, t.total, t.estado_act "
															+ "FROM transaccion t "
															+ "INNER JOIN tran_estado te "
															+ "ON t.nro_transac = te.nro_transac "
															+ "AND t.estado_act = te.estado "
															+ "WHERE (operacion = ? OR '' = ?) "
															+ "AND (t.estado_act = ? OR '' = ?) "
															+ "AND (t.fecha_hora::date BETWEEN ? AND ? ) "
															+ "ORDER BY te.fecha_hora DESC";
	
	//tran_estado
	public static final String QRY_SELECT_ULT_TRANESTADO_XID = "SELECT te.estado FROM tran_estado te "
															+ "WHERE te.nro_transac = ? "
															+ "ORDER BY te.fecha_hora DESC "
															+ "LIMIT 1";
	
	public static final String QRY_INSERT_TRANESTADO = "INSERT INTO tran_estado "
															+ "(nro_transac, estado, fecha_hora) "
															+ "VALUES (?, ?, ?)";
	
	//tran_vta_lote
	public static final String QRY_SELECT_TRA_VTA_LOTE_XID = "SELECT tl.nro_transac, tl.id_producto, l.id_lote, tvl.cantidad "
															+ "FROM tran_vta_lote tvl "
															+ "INNER JOIN tran_linea tl "
															+ "ON tvl.nro_transac = tl.nro_transac "
															+ "AND tvl.id_producto = tl.id_producto "
															+ "INNER JOIN lote l "
															+ "ON tvl.id_lote = l.id_lote "
															+ "WHERE tvl.nro_transac = ? "
															+ "AND tvl.id_producto = ? ";
	
	public static final String QRY_INSERT_TRA_VTA_LOTE = "INSERT INTO tran_vta_lote "
															+ "(nro_transac, id_producto, id_lote, cantidad) "
															+ "VALUES (?, ?, ?, ?)";
	
	public static final String QRY_DELETE_TRA_VTA_LOTE = "DELETE FROM tran_vta_lote WHERE nro_transac = ?";
	
}
