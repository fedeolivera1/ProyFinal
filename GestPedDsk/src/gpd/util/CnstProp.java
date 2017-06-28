package gpd.util;

public interface CnstProp {

	//archivos properties
	public static final String PROP_DB	= "dbdsk";
	public static final String PROP_LOG4J = "log4j";
	public static final String PROP_CONFIG = "config";
	
	//datos properties dbdsk
	public static final String DB_URL_VAL = "dbdskurl";
	public static final String DB_NAME_VAL = "dbname";
	public static final String DB_USER_VAL = "dbuser";
	public static final String DB_PASS_VAL = "dbpass";
	public static final String DB_DRIVER_VAL = "dbdriver";

	//datos properties config
//	public static final String CFG_IVA = "iva";// se toma valor de enum para levantar del .properties
	public static final String CFG_DPV = "dias_para_venc";
	
}
