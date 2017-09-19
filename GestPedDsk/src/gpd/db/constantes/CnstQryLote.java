package gpd.db.constantes;

public interface CnstQryLote {

	public static final String QRY_INSERT_LOTE = "INSERT INTO lote "
												+ "(nro_transac, id_producto, stock) "
												+ "VALUES (?, ?, ?)";
	
	public static final String QRY_UPDATE_LOTE = "UPDATE lote SET "
												+ "venc = ?, nro_dep = ?, id_util = ? "
												+ "WHERE id_lote = ?";
	
	public static final String QRY_UPDATE_STOCK_LOTE = "UPDATE lote SET stock = ? "
												+ "WHERE id_lote = ?";
	
	public static final String QRY_SELECT_LOTE_XID = "SELECT l.id_lote, l.nro_transac, l.id_producto, l.venc, l.nro_dep, l.id_util, l.stock "
												+ "FROM lote l "
												+ "WHERE id_lote = ?";
	
	public static final String QRY_SELECT_LOTEVTA_XTRANSACPROD = "SELECT l.id_lote, tvl.nro_transac, l.id_producto, l.venc, l.nro_dep, l.id_util, l.stock "
												+ "FROM lote l "
												+ "INNER JOIN producto p ON l.id_producto = p.id_producto "
												+ "INNER JOIN tran_vta_lote tvl ON l.id_lote = tvl.id_lote "
												+ "INNER JOIN tran_linea tl ON tvl.nro_transac = tl.nro_transac AND tvl.id_producto = tl.id_producto " 
												+ "INNER JOIN transaccion t ON t.nro_transac = tl.nro_transac "
												+ "WHERE p.activo = 1 "
												+ "AND tvl.nro_transac = ? "
												+ "AND tvl.id_producto = ?"; 
	
	public static final String QRY_SELECT_LOTES_XTRANSAC = "SELECT l.id_lote, l.nro_transac, l.id_producto, l.venc, l.nro_dep, l.id_util, l.stock "
												+ "FROM lote l "
												+ "INNER JOIN producto p "
												+ "ON l.id_producto = p.id_producto "
												+ "INNER JOIN tran_linea tl "
												+ "ON l.nro_transac = tl.nro_transac "
												+ "AND l.id_producto = tl.id_producto "
												+ "INNER JOIN transaccion t "
												+ "ON t.nro_transac = tl.nro_transac "
												+ "WHERE p.activo = 1 "
												+ "AND t.nro_transac= ? "
												+ "ORDER BY l.id_lote DESC";
	
	public static final String QRY_SELECT_LOTES_XPROD = "SELECT l.id_lote, l.nro_transac, l.id_producto, l.venc, l.nro_dep, l.id_util, l.stock "
												+ "FROM lote l "
												+ "INNER JOIN producto p "
												+ "ON l.id_producto = p.id_producto "
												+ "WHERE p.activo = 1 "
												+ "AND l.id_producto = ? "
												+ "AND (l.venc IS NOT NULL AND l.nro_dep IS NOT NULL AND l.id_util IS NOT NULL) "
												+ "AND l.stock > 0 "
												+ "AND DATE_PART('day', l.venc - now()) > ? "
												+ "ORDER BY l.venc ASC";
	
	public static final String QRY_SELECT_LOTES_PROX_VENC = "SELECT * FROM lote l "
												+ "INNER JOIN producto p "
												+ "ON l.id_producto = p.id_producto "
												+ "WHERE l.stock > 0 "
												+ "AND DATE_PART('day', l.venc - now()) <= ?";

}
