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
import gpd.dominio.util.Sinc;
import gpd.exceptions.PersistenciaException;
import gpd.exceptions.PresentacionException;
import gpd.interfaces.persona.IPersDepLoc;
import gpd.interfaces.persona.IPersPersona;
import gpd.interfaces.persona.IPersTipoDoc;
import gpd.persistencia.conector.Conector;
import gpd.persistencia.persona.PersistenciaDepLoc;
import gpd.persistencia.persona.PersistenciaPersona;
import gpd.persistencia.persona.PersistenciaTipoDoc;
import gpd.types.Fecha;

public class ManagerPersona {

	private static final Logger logger = Logger.getLogger(ManagerPersona.class);
	private static IPersPersona interfacePersona;
	private static IPersTipoDoc interfaceTipoDoc;
	private static IPersDepLoc interfaceDepLoc;
	
	
	private static IPersPersona getInterfacePersona() {
		if(interfacePersona == null) {
			interfacePersona = new PersistenciaPersona();
		}
		return interfacePersona;
	}
	private static IPersTipoDoc getInterfaceTipoDoc() {
		if(interfaceTipoDoc == null) {
			interfaceTipoDoc = new PersistenciaTipoDoc();
		}
		return interfaceTipoDoc;
	}
	private static IPersDepLoc getInterfaceDepLoc() {
		if(interfaceDepLoc == null) {
			interfaceDepLoc = new PersistenciaDepLoc();
		}
		return interfaceDepLoc;
	}
	
	
	/*****************************************************************************************************************************************************/
	/** TIPO DOC */
	/*****************************************************************************************************************************************************/
	
	public List<TipoDoc> obtenerListaTipoDoc() throws PresentacionException {
		logger.info("Se ingresa a obtenerListaTipoDoc");
		List<TipoDoc> listaTipoDoc = null;
		try {
			Conector.getConn();
			listaTipoDoc = getInterfaceTipoDoc().obtenerListaTipoDoc();
			Conector.closeConn("obtenerListaTipoDoc", null);
		} catch (PersistenciaException e) {
			logger.fatal("Excepcion en ManagerPersona > obtenerListaTipoDoc: " + e.getMessage(), e);
			throw new PresentacionException(e);
		}
		return listaTipoDoc;
	}
	
	/*****************************************************************************************************************************************************/
	/** PERSONA FISICA */
	/*****************************************************************************************************************************************************/
	
	public PersonaFisica obtenerPersFisicaPorId(Long id) throws PresentacionException {
		logger.info("Se ingresa a obtenerPersFisicaPorId");
		PersonaFisica persFisica = null;
		try {
			Conector.getConn();
			persFisica = getInterfacePersona().obtenerPersFisicaPorId(id);
			Conector.closeConn("obtenerPersFisicaPorId", null);
		} catch (PersistenciaException e) {
			logger.fatal("Excepcion en ManagerPersona > obtenerPersFisicaPorId: " + e.getMessage(), e);
			throw new PresentacionException(e);
		}
		return persFisica;
	}
	
	public List<PersonaFisica> obtenerBusquedaPersFisica(Long documento, String ape1, String ape2, String nom1, String nom2, 
			Sexo sexo, String direccion, String telefono, String celular, String email, Localidad loc) throws PresentacionException {
		logger.info("Ingresa obtenerBusquedaPersFisica");
		List<PersonaFisica> listaPf = null;
		try {
			Conector.getConn();
			Character sexoAsChar = (sexo != null ? sexo.getAsChar() : CnstQryGeneric.CHAR_EMPTY);
			Integer idLoc = (loc != null ? loc.getIdLocalidad() : CnstQryGeneric.NUMBER_INVALID);
			listaPf = getInterfacePersona().obtenerBusquedaPersFisica(documento, ape1, ape2, nom1, nom2, sexoAsChar, direccion, telefono, celular, email, idLoc);
			Conector.closeConn("obtenerBusquedaPersFisica", null);
		} catch (PersistenciaException e) {
			logger.fatal("Excepcion en ManagerPersona > obtenerBusquedaPersFisica: " + e.getMessage(), e);
			throw new PresentacionException(e);
		}
		return listaPf;
	}
	
	public Integer guardarPersFisica(PersonaFisica persFisica) throws PresentacionException {
		logger.info("Ingresa guardarPersFisica");
		Integer resultado = null;
		if(persFisica != null) {
			try {
				persFisica.setSinc(Sinc.N);
				persFisica.setUltAct(new Fecha(Fecha.AMDHMS));
				Conector.getConn();
				resultado = getInterfacePersona().guardarPersFisica(persFisica);
				Conector.closeConn("guardarPersFisica", null);
			} catch (PersistenciaException e) {
				logger.fatal("Excepcion en ManagerPersona > guardarPersFisica: " + e.getMessage(), e);
				throw new PresentacionException(e);
			}
		}
		return resultado;
	}
	
	public Integer modificarPersFisica(PersonaFisica persFisica) throws PresentacionException {
		logger.info("Se ingresa a modificarPersFisica");
		Integer resultado = null;
		if(persFisica != null) {
			try {
				persFisica.setSinc(Sinc.N);
				persFisica.setUltAct(new Fecha(Fecha.AMDHMS));
				Conector.getConn();
				resultado = getInterfacePersona().modificarPersFisica(persFisica);
				Conector.closeConn("modificarPersFisica", null);
			} catch (PersistenciaException e) {
				logger.fatal("Excepcion en ManagerPersona > modificarPersFisica: " + e.getMessage(), e);
				throw new PresentacionException(e);
			}
		}
		return resultado;
	}
	
	public Integer eliminarPersFisica(PersonaFisica persFisica) throws PresentacionException {
		logger.info("Se ingresa a eliminarPersFisica");
		Integer resultado = null;
		if(persFisica != null) {
			try {
				Conector.getConn();
				resultado = getInterfacePersona().eliminarPersFisica(persFisica);
				Conector.closeConn("eliminarPersFisica", null);
			} catch (PersistenciaException e) {
				logger.fatal("Excepcion en ManagerPersona > eliminarPersFisica: " + e.getMessage(), e);
				throw new PresentacionException(e);
			}
		}
		return resultado;
	}

	/*****************************************************************************************************************************************************/
	/** PERSONA JURIDICA */
	/*****************************************************************************************************************************************************/
	
	public List<PersonaJuridica> obtenerBusquedaPersJuridica(Long rut, String nombre, String razonSoc, String bps, String bse, 
			Boolean esProv, String direccion, String telefono, String celular, String email, Localidad loc) throws PresentacionException {
		logger.info("Ingresa obtenerBusquedaPersJuridica");
		List<PersonaJuridica> listaPj = null;
		try {
			Conector.getConn();
			Integer idLoc = (loc != null ? loc.getIdLocalidad() : CnstQryGeneric.NUMBER_INVALID);
			listaPj = getInterfacePersona().obtenerBusquedaPersJuridica(rut, nombre, razonSoc, bps, bse, esProv, direccion, telefono, celular, email, idLoc);
			Conector.closeConn("obtenerBusquedaPersJuridica", null);
		} catch (PersistenciaException e) {
			logger.fatal("Excepcion en ManagerPersona > obtenerBusquedaPersJuridica: " + e.getMessage(), e);
			throw new PresentacionException(e);
		}
		return listaPj;
	}
	
	public List<PersonaJuridica> obtenerListaProveedor() throws PresentacionException {
		logger.info("Ingresa obtenerListaProveedor");
		List<PersonaJuridica> listaPj = null;
		try {
			Conector.getConn();
			listaPj = getInterfacePersona().obtenerListaEmpresasPorTipo(true);
			Conector.closeConn("obtenerListaProveedor", null);
		} catch (PersistenciaException e) {
			logger.fatal("Excepcion en ManagerPersona > obtenerListaProveedor: " + e.getMessage(), e);
			throw new PresentacionException(e);
		}
		return listaPj;
	}
	
	public List<PersonaJuridica> obtenerListaEmpresas() throws PresentacionException {
		logger.info("Ingresa obtenerListaProveedor");
		List<PersonaJuridica> listaPj = null;
		try {
			Conector.getConn();
			listaPj = getInterfacePersona().obtenerListaEmpresasPorTipo(null);
			Conector.closeConn("obtenerListaProveedor", null);
		} catch (PersistenciaException e) {
			logger.fatal("Excepcion en ManagerPersona > obtenerListaEmpresas: " + e.getMessage(), e);
			throw new PresentacionException(e);
		}
		return listaPj;
	}
	
	public PersonaJuridica obtenerPersJuridicaPorId(Long id) throws PresentacionException {
		logger.info("Se ingresa a obtenerPersJuridicaPorId");
		PersonaJuridica persJuridica = null;
		try {
			Conector.getConn();
			persJuridica = getInterfacePersona().obtenerPersJuridicaPorId(id);
			Conector.closeConn("obtenerPersJuridicaPorId", null);
		} catch (PersistenciaException e) {
			logger.fatal("Excepcion en ManagerPersona > obtenerPersJuridicaPorId: " + e.getMessage(), e);
			throw new PresentacionException(e);
		}
		return persJuridica;
	}
	
	public Integer guardarPersJuridica(PersonaJuridica persJuridica) throws PresentacionException {
		logger.info("Ingresa guardarPersJuridica");
		Integer resultado = null;
		if(persJuridica != null) {
			try {
				persJuridica.setSinc(Sinc.N);
				persJuridica.setUltAct(new Fecha(Fecha.AMDHMS));
				Conector.getConn();
				resultado = getInterfacePersona().guardarPersJuridica(persJuridica);
				Conector.closeConn("guardarPersJuridica", null);
			} catch (PersistenciaException e) {
				logger.fatal("Excepcion en ManagerPersona > guardarPersJuridica: " + e.getMessage(), e);
				throw new PresentacionException(e);
			}
		}
		return resultado;
	}
	
	public Integer modificarPersJuridica(PersonaJuridica persJuridica) throws PresentacionException {
		logger.info("Se ingresa a modificarPersJuridica");
		Integer resultado = null;
		if(persJuridica != null) {
			try {
				persJuridica.setSinc(Sinc.N);
				persJuridica.setUltAct(new Fecha(Fecha.AMDHMS));
				Conector.getConn();
				resultado = getInterfacePersona().modificarPersJuridica(persJuridica);
				Conector.closeConn("modificarPersJuridica", null);
			} catch (PersistenciaException e) {
				logger.fatal("Excepcion en ManagerPersona > modificarPersJuridica: " + e.getMessage(), e);
				throw new PresentacionException(e);
			}
		}
		return resultado;
	}
	
	public Integer eliminarPersJuridica(PersonaJuridica persJuridica) throws PresentacionException {
		logger.info("Se ingresa a eliminarPersFisica");
		Integer resultado = null;
		if(persJuridica != null) {
			try {
				Conector.getConn();
				resultado = getInterfacePersona().eliminarPersJuridica(persJuridica);
				Conector.closeConn("eliminarPersFisica", null);
			} catch (PersistenciaException e) {
				logger.fatal("Excepcion en ManagerPersona > eliminarPersJuridica: " + e.getMessage(), e);
				throw new PresentacionException(e);
			}
		}
		return resultado;
	}
	
	/*****************************************************************************************************************************************************/
	/** DEP - LOC */
	/*****************************************************************************************************************************************************/
	
	public List<Departamento> obtenerListaDepartamento() throws PresentacionException {
		logger.info("Se ingresa a obtenerListaDepartamento");
		List<Departamento> listaDep = null;
		try {
			Conector.getConn();
			listaDep = getInterfaceDepLoc().obtenerListaDepartamentos();
			Conector.closeConn("obtenerListaDepartamento", null);
		} catch (PersistenciaException e) {
			logger.fatal("Excepcion en ManagerPersona > obtenerListaDepartamento: " + e.getMessage(), e);
			throw new PresentacionException(e);
		}
		return listaDep;
	}
	
	public List<Localidad> obtenerListaLocalidadPorDep(Integer idDep) throws PresentacionException {
		logger.info("Se ingresa a obtenerListaLocalidadPorDep");
		List<Localidad> listaLoc = null;
		try {
			Conector.getConn();
			listaLoc = getInterfaceDepLoc().obtenerListaLocPorDep(idDep);
			Conector.closeConn("obtenerListaLocalidadPorDep", null);
		} catch (PersistenciaException e) {
			logger.fatal("Excepcion en ManagerPersona > obtenerListaLocalidadPorDep: " + e.getMessage(), e);
			throw new PresentacionException(e);
		}
		return listaLoc;
	}



}
