package gpd.manager.persona;

import java.util.List;

import org.apache.log4j.Logger;

import gpd.db.constantes.CnstQryGeneric;
import gpd.dominio.persona.Departamento;
import gpd.dominio.persona.Localidad;
import gpd.dominio.persona.PersonaFisica;
import gpd.dominio.persona.PersonaJuridica;
import gpd.dominio.persona.Sexo;
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
		interfacePersona = new PersistenciaPersona();
		try {
			Conector.getConn();
			listaTipoDoc = interfacePersona.obtenerListaTipoDoc();
			Conector.closeConn("obtenerListaTipoDoc", null);
		} catch (PersistenciaException e) {
			e.printStackTrace();
		}
		return listaTipoDoc;
	}
	
	public PersonaFisica obtenerPersFisicaPorId(Long id) {
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
	
	public List<PersonaFisica> obtenerBusquedaPersFisica(Long documento, String ape1, String ape2, String nom1, String nom2, 
			Sexo sexo, String direccion, String telefono, String celular, String email, Localidad loc) {
		logger.info("Ingresa obtenerBusquedaPersFisica");
		List<PersonaFisica> listaPf = null;
		interfacePersona = new PersistenciaPersona();
		try {
			Conector.getConn();
			Character sexoAsChar = (sexo != null ? sexo.getAsChar() : CnstQryGeneric.CHAR_EMPTY);
			Integer idLoc = (loc != null ? loc.getIdLocalidad() : CnstQryGeneric.NUMBER_INVALID);
			listaPf = interfacePersona.obtenerBusquedaPersFisica(documento, ape1, ape2, nom1, nom2, sexoAsChar, direccion, telefono, celular, email, idLoc);
			Conector.closeConn("obtenerBusquedaPersFisica", null);
		} catch (PersistenciaException e) {
			e.printStackTrace();//FIXME ver como manejar esta excep
		}
		return listaPf;
	}
	
	public Integer guardarPersFisica(PersonaFisica persFisica) {
		logger.info("Ingresa guardarPersFisica");
		Integer resultado = null;
		if(persFisica != null) {
			interfacePersona = new PersistenciaPersona();
			try {
				Conector.getConn();
				resultado = interfacePersona.guardarPersFisica(persFisica);
				Conector.closeConn("guardarPersFisica", null);
			} catch (PersistenciaException e) {
				e.printStackTrace();//FIXME ver como manejar esta excep
			}
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
			interfacePersona = new PersistenciaPersona();
			try {
				Conector.getConn();
				resultado = interfacePersona.guardarPersJuridica(persJuridica);
			} catch (PersistenciaException e) {
				e.printStackTrace();//FIXME ver como manejar esta excep
			}
			Conector.closeConn("guardarPersFisica", null);
		}
		return resultado;
	}
	
	public List<Departamento> obtenerListaDepartamento() {
		logger.info("Se ingresa a obtenerListaDepartamento");
		List<Departamento> listaDep = null;
		interfacePersona = new PersistenciaPersona();
		try {
			Conector.getConn();
			listaDep = interfacePersona.obtenerListaDepartamentos();
			Conector.closeConn("obtenerListaDepartamento", null);
		} catch (PersistenciaException e) {
			e.printStackTrace();//FIXME ver como manejar esta excep
		}
		return listaDep;
	}
	
	public List<Localidad> obtenerListaLocalidadPorDep(Integer idDep) {
		logger.info("Se ingresa a obtenerListaLocalidadPorDep");
		List<Localidad> listaLoc = null;
		interfacePersona = new PersistenciaPersona();
		try {
			Conector.getConn();
			listaLoc = interfacePersona.obtenerListaLocPorDep(idDep);
			Conector.closeConn("obtenerListaLocalidadPorDep", null);
		} catch (PersistenciaException e) {
			e.printStackTrace();//FIXME ver como manejar esta excep
		}
		return listaLoc;
	}



}
