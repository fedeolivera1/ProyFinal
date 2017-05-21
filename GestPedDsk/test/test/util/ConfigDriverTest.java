package test.util;

import static org.junit.Assert.fail;

import org.apache.log4j.Logger;
import org.junit.Test;

import gpd.util.ConfigDriver;

public class ConfigDriverTest {

	static final Logger logger = Logger.getLogger(ConfigDriverTest.class);
	
	@Test
	public void testGetPropertiesValue() {
		ConfigDriver cfgDriver = new ConfigDriver();
		cfgDriver.logConfig();
		logger.info("Finaliza ok");
	}

	@Test
	public void testConfigDriver() {
		fail("Not yet implemented");
	}

	@Test
	public void testLogConfig() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetDbDriver() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetDbUrl() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetDbName() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetDbUser() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetDbPass() {
		fail("Not yet implemented");
	}

}
