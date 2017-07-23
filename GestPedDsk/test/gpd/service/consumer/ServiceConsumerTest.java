package gpd.service.consumer;

import static org.junit.Assert.*;

import org.junit.Test;

import gpd.exceptions.PresentacionException;

public class ServiceConsumerTest {

	@Test
	public void testConsumirWebService() {
		ServiceConsumer scvCns = new ServiceConsumer();
		try {
			scvCns.consumirWebService("servicioFuncional");
		} catch (PresentacionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		assertEquals("ServicioFuncional", true);
	}

}
