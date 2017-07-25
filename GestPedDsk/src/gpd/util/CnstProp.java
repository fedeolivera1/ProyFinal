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
	public static final String CFG_DIAS_PARA_VENC = "dias_para_venc";
	public static final String CFG_VENC_TOL_ANUL = "venc_tolerable_anulacion";
	
	//datos properties consumo del webservice
	public static final String CFG_WS_URL = "ws_url";
	public static final String CFG_WS_TN = "ws_target_ns";
	public static final String CFG_WS_NAME = "ws_name";
	
	//datos properties url de conexion de prueba
	public static final String CFG_INET_TEST_URL = "inet_test_url";
	public static final String CFG_INET_TEST_PORT = "inet_test_port";
	
}
