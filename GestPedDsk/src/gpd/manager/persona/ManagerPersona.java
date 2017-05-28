package gpd.manager.persona;

import java.util.List;

import org.apache.log4j.Logger;

import gpd.dominio.persona.PersonaFisica;
import gpd.dominio.persona.PersonaJuridica;
import gpd.dominio.persona.TipoDoc;
import gpd.exceptions.PersistenciaException;
import gpd.interfaces.persona.IPersPersona;
import gpd.persistencia.conector.Conector;
import gpd.persistencia.persona.PersistenciaPersona;

public class ManagerPersona {

	private static final Logger logger = Logger.getLogger(ManagerPersona.class);
	IPersPersona interfacePersona;
	
	public List<TipoDoc> obtenerListaTipoDoc() {
		logger.info("Se ingresa a obtenerListaTipoDoc");
		List<TipoDoc> listaTipoDoc = null;
		Conector.getConn();
		interfacePersona = new PersistenciaPersona();
		try {
			listaTipoDoc = interfacePersona.obtenerListaTipoDoc();
			Conector.closeConn("obtenerListaTipoDoc", null);
		} catch (PersistenciaException e) {
			e.printStackTrace();
		}
		return listaTipoDoc;
	}
	
	public PersonaFisica obtenerPersFisicaPorId(Integer id) {
		logger.info("Se ingresa a obtenerPersFisicaPorId");
		PersonaFisica persFisica = null;
		interfacePersona = new PersistenciaPersona();
		try {
			Conector.getConn();
			persFisica = interfacePersona.obtenerPersFisicaPorId(id);
			Conector.closeConn("obtenerPersFisicaPorId", null);
		} catch (PersistenciaException e) {
			e.printStackTrace();//FIXME ver como manejar esta excep
		}
		return persFisica;
	}
	
	public Integer guardarPersFisica(PersonaFisica persFisica) {
		logger.info("Ingresa guardarPersFisica");
		Integer resultado = null;
		if(persFisica != null) {
			Conector.getConn();
			interfacePersona = new PersistenciaPersona();
			try {
				resultado = interfacePersona.guardarPersFisica(persFisica);
			} catch (PersistenciaException e) {
				e.printStackTrace();//FIXME ver como manejar esta excep
			}
			Conector.closeConn("guardarPersFisica", null);
		}
		return resultado;
	}

	public PersonaJuridica obtenerPersJuridicaPorId(Integer id) {
		logger.info("Ingresa obtenerPersJuridicaPorId");
		// TODO Auto-generated method stub
		return null;
	}
	
	public Integer guardarPersJuridica(PersonaJuridica persJuridica) {
		logger.info("Ingresa guardarPersJuridica");
		Integer resultado = null;
		if(persJuridica != null) {
			Conector.getConn();
			interfacePersona = new PersistenciaPersona();
			try {
				resultado = interfacePersona.guardarPersJuridica(persJuridica);
			} catch (PersistenciaException e) {
				e.printStackTrace();//FIXME ver como manejar esta excep
			}
			Conector.closeConn("guardarPersFisica", null);
		}
		return resultado;
		
	}



}
