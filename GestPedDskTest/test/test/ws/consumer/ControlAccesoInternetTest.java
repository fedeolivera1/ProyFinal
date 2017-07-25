package test.ws.consumer;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import gpd.ws.consumer.ControlAccesoInternet;

public class ControlAccesoInternetTest {

	@Test
	public void testControlAccesoInternet() {
		try {
			Boolean b = ControlAccesoInternet.controlarConectividadInet();
			assertTrue(b);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
