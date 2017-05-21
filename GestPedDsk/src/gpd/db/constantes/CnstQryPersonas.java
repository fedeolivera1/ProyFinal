package gpd.db.constantes;

public interface CnstQryPersonas {

	public static final String QUERY_SELECT_SEQ_PER = "select nextval('SEQ_PROVEEDOR')";
	public static final String QUERY_INSERT_CLIENTE = "insert into CLIENTE "
														+ "(DOC_CLIENTE,ID_TIPO_DOC,APELLIDO1,APELLIDO2,NOMBRE1,NOMBRE2,FECHA_NAC,SEXO,ORIGEN,SINC,ULT_ACT) "
														+ "values (?,?,?,?,?,?,?,?,?,?,?)";
	public static final String QUERY_INSERT_PROVEEDOR = "insert into PROVEEDOR "
														+ "(ID_PROVEEDOR,RAZON_SOCIAL,RUT,BPS,BSE) "
														+ "values (?,?,?,?,?)";
	public static final String QUERY_INSERT_PERSONA = "insert into PERSONA "
														+ "(ID_PERSONA,DIRECCION,PUERTA,SOLAR,MANZANA,KM,COMPLEMENTO,TELEFONO,CELULAR,EMAIL,TIPO,ID_LOC) "
														+ "values (?,?,?,?,?,?,?,?,?,?,?,?)";

	public static final String QUERY_UPDATE_CLIENTE = "update CLIENTE set ?,?,?,?,?,?,?,?,?,?,? where DOC_CLIENTE=?";
	public static final String QUERY_UPDATE_PROVEEDOR = "update PROVEEDOR set ?,?,?,?,?,?,?,?,?,?,? where ID_PROVEEDOR=?";
	public static final String QUERY_UPDATE_PERSONA = "update PERSONA set ?,?,?,?,?,?,?,?,?,?,? where ID_PERSONA=?";
	
	
}
