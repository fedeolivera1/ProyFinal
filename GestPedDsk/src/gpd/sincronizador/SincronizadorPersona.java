package gpd.sincronizador;

import org.apache.log4j.Logger;

import gpd.interfaces.persona.IPersPersona;
import gpd.persistencia.persona.PersistenciaPersona;

public class SincronizadorPersona {

	private static final Logger logger = Logger.getLogger(SincronizadorPersona.class);
	private static IPersPersona interfacePersona;
	
	private static IPersPersona getInterfacePersona() {
		if(interfacePersona == null) {
			interfacePersona = new PersistenciaPersona();
		}
		return interfacePersona;
	}
	
	
}
