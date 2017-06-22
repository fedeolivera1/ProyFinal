package gpd.db.constantes;

public interface CnstQryPedidoLinea {

	public static final String QRY_PEDIDO_LIN = "SELECT pl.id_persona, pl.fecha_hora, pl.id_producto, pl.cantidad, pl.sinc, pl.ult_act "
												+ "FROM pedido_linea pl "
												+ "INNER JOIN pedido p "
												+ "ON pl.id_persona = p.id_persona "
												+ "AND pl.fecha_hora = p.fecha_hora "
												+ "WHERE pl.id_persona = ? "
												+ "AND pl.fecha_hora = ?";
	
	public static final String QRY_INSERT_PL = "INSERT INTO pedido_linea "
												+ "(pl.id_persona, pl.fecha_hora, pl.id_producto, pl.cantidad, pl.sinc, pl.ult_act) "
												+ "VALUES (?, ?, ?, ?, ?, ?)";
	
	public static final String QRY_DELETE_PL = "DELETE FROM pedido_linea WHERE id_persona = ? AND fecha_hora = ?";
	
}
