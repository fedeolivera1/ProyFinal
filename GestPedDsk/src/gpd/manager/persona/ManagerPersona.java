package gpd.manager.persona;

import org.apache.log4j.Logger;

import gpd.dominio.persona.PersonaFisica;
import gpd.dominio.persona.PersonaJuridica;
import gpd.dominio.persona.Proveedor;
import gpd.interfaces.persona.IPersPersona;
import gpd.persistencia.conector.Conector;
import gpd.persistencia.persona.PersistenciaPersona;

public class ManagerPersona {

	private static ManagerPersona instance;
	private static final Logger logger = Logger.getLogger(ManagerPersona.class);
	Integer resultado = null;
	
	
	public static ManagerPersona getManagerPersona() {
		if(instance == null) {
			instance = new ManagerPersona();
			logger.info("Se genera nueva instancia de ManagerPersona...");
		}
		return instance;
	}
	
	public PersonaFisica obtenerPersFisicaPorId(Integer id) {
		logger.info("Ingresa obtenerPersFisicaPorId");
		// TODO Auto-generated method stub
		return null;
	}
	
	public PersonaJuridica obtenerPersJuridicaPorId(Integer id) {
		logger.info("Ingresa obtenerPersJuridicaPorId");
		// TODO Auto-generated method stub
		return null;
	}
	
	public Integer guardarPersFisica(PersonaFisica persFisica) {
		logger.info("Ingresa guardarPersFisica");
		if(persFisica != null) {
			Conector.getConn();
			IPersPersona interfacePersona = new PersistenciaPersona();
			resultado = interfacePersona.guardarPersFisica(persFisica);
			Conector.closeConn("guardarPersFisica", null);
		}
		return resultado;
	}

	public Integer guardarPersJuridica(PersonaJuridica persJuridica) {
		logger.info("Ingresa guardarPersJuridica");
		if(persJuridica != null) {
			Conector.getConn();
			IPersPersona interfacePersona = new PersistenciaPersona();
			char tipoPj = (persJuridica instanceof Proveedor ? 'S' : 'N');//FIXME chequear si esto se deja aca o será parte de pj
			resultado = interfacePersona.guardarPersJuridica(persJuridica, tipoPj);
			Conector.closeConn("guardarPersFisica", null);
		}
		return resultado;
		
	}

}
