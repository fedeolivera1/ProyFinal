package gpd.ws.consumer;

import java.net.Socket;

import org.apache.log4j.Logger;

import gpd.exceptions.NoInetConnectionException;
import gpd.util.ConfigDriver;

public class ControlAccesoInternet {
	
	private static final Logger logger = Logger.getLogger(ControlAccesoInternet.class);

	@SuppressWarnings("resource")
	public static Boolean controlarConectividadInet() throws NoInetConnectionException {
		logger.info("El sistema ingresa a comprobar conectividad... ");
		String TEST_URL = null;
		Integer TEST_PORT = null;
		try {
			ConfigDriver cfgDrv = ConfigDriver.getConfigDriver();
			TEST_URL = String.valueOf(cfgDrv.getInetTestUrl());
			TEST_PORT = Integer.valueOf(cfgDrv.getInetTestPort());
		} catch(Exception e) {
			logger.fatal("Excepcion en ControlAccesoInternet > controlarConectividadInet: " + e.getMessage(), e);
			throw new NoInetConnectionException(e);
		}
		try {
			Socket s = new Socket(TEST_URL, TEST_PORT);
			if(s.isConnected()) {
				logger.debug("Conexión establecida con la dirección: " +  s.getInetAddress());
				return true;
			}
		} catch(Exception e) {
			logger.warn("No se pudo establecer conectividad de prueba con Internet.");
			throw new NoInetConnectionException("No se pudo establecer conectividad de prueba con Internet.");
		}
		return false;
	}
}
