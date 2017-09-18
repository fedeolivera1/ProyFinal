package gpd.util;

import java.util.HashSet;

import org.apache.log4j.Logger;

public class ConfigDriver extends PropManager {

	private static ConfigDriver instance; 
	private static Logger logger = Logger.getLogger(ConfigDriver.class);

	public static ConfigDriver getConfigDriver() {
		if(instance == null) {
			logger.info("Se genera nueva instancia de ConfigDriver > se leen properties...");
			instance = new ConfigDriver();
		}
		return instance;
	}
	
	public ConfigDriver() {
		super.loadConfig();
		super.configLog4J();
		logConfig();
	}
	
	@Override
	protected HashSet<String> getPropertiesValue() {
		HashSet<String> resp = new HashSet<String>();
		resp.add(CnstProp.PROP_DB);
		resp.add(CnstProp.PROP_LOG4J);
		resp.add(CnstProp.PROP_CONFIG);
		return resp;
	}
	
	

	public void logConfig() {
		try {
			logger.info("--- Lectura de properties ---");
			logger.info("--DB URL --> " + getDbUrl());
			logger.info("--DB User --> " + getDbUser());
			logger.info("--DB Driver --> " + getDbDriver());
			logger.info("-----------------------------");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Db Values
	 * @throws Exception
	 */
	public String getDbDriver() throws Exception {
		return super.getPropertyValue(CnstProp.PROP_DB, CnstProp.DB_DRIVER_VAL);
	}
	
	public String getDbUrl() throws Exception {
		return super.getPropertyValue(CnstProp.PROP_DB, CnstProp.DB_URL_VAL);
	}
	
	public String getDbName() throws Exception {
		return super.getPropertyValue(CnstProp.PROP_DB, CnstProp.DB_NAME_VAL);
	}
	
	public String getDbUser() throws Exception {
		return super.getPropertyValue(CnstProp.PROP_DB, CnstProp.DB_USER_VAL);
	}
	
	public String getDbPass() throws Exception {
		return super.getPropertyValue(CnstProp.PROP_DB, CnstProp.DB_PASS_VAL);
	}
	
	/**
	 * Transac Values
	 * @throws Exception
	 */
	public String getIva(String iva) throws Exception {
		return super.getPropertyValue(CnstProp.PROP_CONFIG, iva);
	}
	
	public String getDiasParaVenc() throws Exception {
		return super.getPropertyValue(CnstProp.PROP_CONFIG, CnstProp.CFG_DIAS_PARA_VENC);
	}
	
	/**
	 * WebService Values
	 * @throws Exception
	 */
	public String getDiasTolerableAnul() throws Exception {
		return super.getPropertyValue(CnstProp.PROP_CONFIG, CnstProp.CFG_DIAS_TOL_ANUL);
	}
	
	public String getWsUrl() throws Exception {
		return super.getPropertyValue(CnstProp.PROP_CONFIG, CnstProp.CFG_WS_URL);
	}
	
	public String getWsTargetNs() throws Exception {
		return super.getPropertyValue(CnstProp.PROP_CONFIG, CnstProp.CFG_WS_TN);
	}
	
	public String getWsName() throws Exception {
		return super.getPropertyValue(CnstProp.PROP_CONFIG, CnstProp.CFG_WS_NAME);
	}
	
	public String getWsUsrName() throws Exception {
		return super.getPropertyValue(CnstProp.PROP_CONFIG, CnstProp.CFG_WS_USERNAME);
	}
	
	public String getWsPassWd() throws Exception {
		return super.getPropertyValue(CnstProp.PROP_CONFIG, CnstProp.CFG_WS_PASSWORD);
	}
	
	/**
	 * Internet test values
	 * @throws Exception
	 */
	public String getInetTestUrl() throws Exception {
		return super.getPropertyValue(CnstProp.PROP_CONFIG, CnstProp.CFG_INET_TEST_URL);
	}
	
	public String getInetTestPort() throws Exception {
		return super.getPropertyValue(CnstProp.PROP_CONFIG, CnstProp.CFG_INET_TEST_PORT);
	}
}
