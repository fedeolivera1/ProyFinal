package gpd.db.constantes;

public interface CnstQryPedido {

	public static final String QRY_PEDIDO = "SELECT p.id_persona, p.fecha_hora, p.estado, p.fecha_prog, p.hora_prog, "
												+ "p.origen, p.total, p.nom_usu, p.nro_transac, p.sinc, p.ult_act "
											+ "FROM pedido p "
											+ "WHERE (p.id_persona = ? OR -1 = ?) "
											+ "AND (TO_CHAR(p.fecha_hora, 'YYYY-MM-DD') BETWEEN ? AND ?) "
											+ "ORDER BY p.fecha_hora DESC;";
}
