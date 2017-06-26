package gpd.db.constantes;

public interface CnstQryLote {

	public static final String QRY_INSERT_LOTE = "INSERT INTO lote "
												+ "(nro_transac, id_producto, stock) "
												+ "VALUES (?, ?, ?)";
	
	public static final String QRY_UPDATE_LOTE = "UPDATE lote SET "
												+ "venc = ?, nro_dep = ?, id_util = ? "
												+ "WHERE id_lote = ?";
	
	public static final String QRY_SELECT_LOTES_XTRANSAC = "SELECT l.id_lote, l.nro_transac, l.id_producto, l.venc, l.nro_dep, l.id_util, l.stock "
												+ "FROM lote l "
												+ "INNER JOIN tran_linea tl "
												+ "ON l.nro_transac = tl.nro_transac "
												+ "AND l.id_producto = tl.id_producto "
												+ "INNER JOIN transaccion t "
												+ "ON t.nro_transac = tl.nro_transac "
												+ "WHERE t.nro_transac= ? "
												+ "ORDER BY l.id_lote DESC";
	
//	public static final String QRY_SELECT_STOCK = "SELECT sum(l.stock) AS stock "
//												+ "FROM lote l "
//												+ "WHERE l.id_producto = ? "
//												+ "AND l.stock > 0 "
//												+ "AND DATE_PART('day', l.venc - now()) > ?";
	
	public static final String QRY_SELECT_LOTES_XPROD = "SELECT l.id_lote, l.nro_transac, l.id_producto, l.venc, l.nro_dep, l.id_util, l.stock "
												+ "FROM lote l "
												+ "WHERE l.id_producto = ? "
												+ "AND l.stock > 0 "
												+ "AND DATE_PART('day', l.venc - now()) > ? "
												+ "ORDER BY l.venc ASC";

}
