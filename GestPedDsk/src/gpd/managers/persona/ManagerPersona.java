package gpd.managers.persona;

import org.apache.log4j.Logger;

import gpd.dominio.persona.PersonaFisica;
import gpd.dominio.persona.PersonaJuridica;
import gpd.interfaces.persona.IPersPersona;
import gpd.persistencia.persona.PersistenciaPersona;

public class ManagerPersona implements IPersPersona {

	Logger logger = Logger.getLogger(ManagerPersona.class);
	Integer resultado = null;
	
	@Override
	public PersonaFisica obtenerPersFisicaPorId(Integer id) {
		logger.info("Ingresa obtenerPersFisicaPorId");
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public PersonaJuridica obtenerPersJuridicaPorId(Integer id) {
		logger.info("Ingresa obtenerPersJuridicaPorId");
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public Integer guardarPersFisica(PersonaFisica persFisica) {
		logger.info("Ingresa guardarPersFisica");
		if(persFisica != null) {
			PersistenciaPersona perPersona = new PersistenciaPersona();
			resultado = perPersona.guardarPersFisica(persFisica);
		}
		return resultado;
	}

	@Override
	public Integer guardarPersJuridica(PersonaJuridica persJuridica) {
		logger.info("Ingresa guardarPersJuridica");
		if(persJuridica != null) {
			PersistenciaPersona perPersona = new PersistenciaPersona();
			resultado = perPersona.guardarPersJuridica(persJuridica);
		}
		return resultado;
		
	}

}
