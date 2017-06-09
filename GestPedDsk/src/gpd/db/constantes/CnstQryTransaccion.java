package gpd.db.constantes;

public interface CnstQryTransaccion {

	public static final String SEC_TRANSAC = "transaccion_nro_transac_seq";
	
	public static final String QRY_INSERT_TRANSACCION = "INSERT INTO transaccion "
															+ "(nro_transac, id_persona, operacion, fecha_hora, sub_total, iva, total) "
															+ "VALUES (?, ?, ?, ?, ?, ?, ?)";
}
