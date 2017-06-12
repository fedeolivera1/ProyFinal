package gpd.db.constantes;

public interface CnstQryLote {

	public static final String QRY_INSERT_LOTE = "INSERT INTO lote "
												+ "(nro_transac, id_producto, stock) "
												+ "VALUES (?, ?, ?)";
	
	public static final String QRY_SELECT_LOTES_ACT = "SELECT l.* "
												+ "FROM lote l "
												+ "INNER JOIN TRAN_LINEA tl "
												+ "ON l.nro_transac = tl.nro_transac "
												+ "AND l.id_producto = tl.id_producto "
												+ "WHERE l.venc IS NULL "
												+ "AND NOT EXISTS (SELECT ta.nro_transac FROM TRAN_ESTADO ta WHERE ta.nro_transac = l.nro_transac "
																										+ "AND ta.ESTADO <> 'P') "
												+ "ORDER BY l.id_lote DESC";
}
