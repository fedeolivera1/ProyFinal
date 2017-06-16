package gpd.db.constantes;

public interface CnstQryLote {

	public static final String QRY_INSERT_LOTE = "INSERT INTO lote "
												+ "(nro_transac, id_producto, stock) "
												+ "VALUES (?, ?, ?)";
	
	public static final String QRY_UPDATE_LOTE = "UPDATE lote SET "
												+ "venc = ?, nro_dep = ?, id_util = ? "
												+ "WHERE id_lote = ?";
	
	public static final String QRY_SELECT_LOTES_XEST = "SELECT l.id_lote, l.nro_transac, l.id_producto, l.venc, l.nro_dep, l.id_util, l.stock "
												+ "FROM lote l "
												+ "INNER JOIN tran_linea tl "
												+ "ON l.nro_transac = tl.nro_transac "
												+ "AND l.id_producto = tl.id_producto "
												+ "INNER JOIN transaccion t "
												+ "ON t.nro_transac = tl.nro_transac "
												+ "WHERE l.venc IS NULL "
												+ "AND t.estado_act = ? OR '' = ? "
												+ "ORDER BY l.id_lote DESC";
}
