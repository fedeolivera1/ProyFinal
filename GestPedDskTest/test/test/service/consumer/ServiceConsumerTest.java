package test.service.consumer;

import org.junit.Test;

import gpd.exceptions.SincronizadorException;
import gpd.exceptions.WsException;
import gpd.ws.consumer.ServiceConsumer;
import gpw.webservice.proxy.ResultServFuncional;
import gpw.webservice.proxy.WsGestPed;

public class ServiceConsumerTest {

	@Test
	public void testConsumirWebService() {
		ServiceConsumer scvCns = new ServiceConsumer();
		try {
			WsGestPed igestped = scvCns.consumirWebService();
			ResultServFuncional resultado = igestped.servFuncional();
			assert(!resultado.getError().equals(null));
		} catch (SincronizadorException | WsException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
