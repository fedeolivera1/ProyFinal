package test.service.consumer;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import gpd.exceptions.SincronizadorException;
import gpd.ws.consumer.ServiceConsumer;
import gpw.webservice.proxy.WsGestPed;

public class ServiceConsumerTest {

	@Test
	public void testConsumirWebService() {
		ServiceConsumer scvCns = new ServiceConsumer();
		try {
			WsGestPed igestped = scvCns.consumirWebService();
			String resultado = igestped.servicioFuncional();
			assertEquals(resultado, "Servicio Funcional");
		} catch (SincronizadorException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
