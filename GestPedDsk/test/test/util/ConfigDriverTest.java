package test.util;

import static org.junit.Assert.fail;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.junit.Test;

import gpd.db.util.ConfigDriver;
import gpd.db.util.PropReader;

public class ConfigDriverTest {

	@Test
	public void testGetPropertiesValue() {
		ConfigDriver cfgDriver = new ConfigDriver();
		cfgDriver.logConfig();
//		BasicConfigurator.configure();
		final Logger logger = Logger.getLogger(PropReader.class.getName());
		logger.log(Level.INFO, "Finaliza ok");
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
