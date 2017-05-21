package gpd.db.util;

import java.util.Vector;
import java.util.logging.Logger;

public class ConfigDriver extends PropManager {

private static Logger logger = Logger.getLogger(ConfigDriver.class.getName());
	
	public ConfigDriver() {
		super.loadConfig();
		super.configLog4J();
	}
	
	@Override
	protected Vector<String> getPropertiesValue() {
		Vector<String> resp = new Vector<String>();
		resp.add(CnstProp.PROP_DB);
		resp.add(CnstProp.PROP_LOG4J);
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

}
