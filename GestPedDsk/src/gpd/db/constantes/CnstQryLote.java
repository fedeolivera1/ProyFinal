package gpd.db.constantes;

public interface CnstQryLote {

	public static final String QRY_INSERT_LOTE = "INSERT INTO lote "
			+ "(id_producto, stock) "
			+ "VALUES (?, ?)";
}
