package gpd.manager.persona;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import gpd.db.constantes.CnstQryGeneric;
import gpd.dominio.persona.Departamento;
import gpd.dominio.persona.Localidad;
import gpd.dominio.persona.Persona;
import gpd.dominio.persona.PersonaFisica;
import gpd.dominio.persona.PersonaJuridica;
import gpd.dominio.persona.Sexo;
import gpd.dominio.persona.TipoDoc;
import gpd.dominio.persona.TipoPersona;
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
		try (Connection conn = Conector.getConn()) {
			listaTipoDoc = getInterfaceTipoDoc().obtenerListaTipoDoc(conn);
		} catch (PersistenciaException e) {
			logger.fatal("Excepcion en ManagerPersona > obtenerListaTipoDoc: " + e.getMessage(), e);
			throw new PresentacionException(e);
		} catch (Exception e) {
			logger.fatal("Excepcion GENERICA en ManagerPersona > obtenerListaTipoDoc: " + e.getMessage(), e);
			throw new PresentacionException(e);
		}
		return listaTipoDoc;
	}
	
	/*****************************************************************************************************************************************************/
	/** PERSONA FISICA */
	/*****************************************************************************************************************************************************/
	
	public List<PersonaFisica> obtenerBusquedaPersFisica(Long documento, String ape1, String ape2, String nom1, String nom2, 
			Sexo sexo, String direccion, String telefono, String celular, String email, Localidad loc) throws PresentacionException {
		logger.info("Ingresa obtenerBusquedaPersFisica");
		List<PersonaFisica> listaPf = null;
		try (Connection conn = Conector.getConn()) {
			Character sexoAsChar = (sexo != null ? sexo.getAsChar() : CnstQryGeneric.CHAR_EMPTY);
			Integer idLoc = (loc != null ? loc.getIdLocalidad() : CnstQryGeneric.NUMBER_INVALID);
			listaPf = getInterfacePersona().obtenerBusquedaPersFisica(conn, documento, ape1, ape2, nom1, nom2, sexoAsChar, direccion, telefono, celular, email, idLoc);
		} catch (PersistenciaException | SQLException e) {
			logger.fatal("Excepcion en ManagerPersona > obtenerBusquedaPersFisica: " + e.getMessage(), e);
			throw new PresentacionException(e);
		} catch (Exception e) {
			logger.fatal("Excepcion GENERICA en ManagerPersona > obtenerBusquedaPersFisica: " + e.getMessage(), e);
			throw new PresentacionException(e);
		}
		return listaPf;
	}
	
	public PersonaFisica obtenerPersFisicaPorId(Long id) throws PresentacionException {
		logger.info("Se ingresa a obtenerPersFisicaPorId");
		PersonaFisica persFisica = null;
		try (Connection conn = Conector.getConn()) {
			persFisica = getInterfacePersona().obtenerPersFisicaPorId(conn, id);
		} catch (PersistenciaException | SQLException e) {
			logger.fatal("Excepcion en ManagerPersona > obtenerPersFisicaPorId: " + e.getMessage(), e);
			throw new PresentacionException(e);
		} catch (Exception e) {
			logger.fatal("Excepcion GENERICA en ManagerPersona > obtenerPersFisicaPorId: " + e.getMessage(), e);
			throw new PresentacionException(e);
		}
		return persFisica;
	}
	
	public Integer guardarPersFisica(PersonaFisica persFisica) throws PresentacionException {
		logger.info("Ingresa guardarPersFisica");
		Integer resultado = null;
		try (Connection conn = Conector.getConn()) {
			if(persFisica != null) {
				persFisica.setSinc(Sinc.N);
				persFisica.setUltAct(new Fecha(Fecha.AMDHMS));
				resultado = getInterfacePersona().guardarPersFisica(conn, persFisica);
				Conector.commitConn(conn);
			}
		} catch (PersistenciaException | SQLException e) {
			logger.fatal("Excepcion en ManagerPersona > guardarPersFisica: " + e.getMessage(), e);
			throw new PresentacionException(e);
		} catch (Exception e) {
			logger.fatal("Excepcion GENERICA en ManagerPersona > guardarPersFisica: " + e.getMessage(), e);
			throw new PresentacionException(e);
		}
		return resultado;
	}
	
	public Integer modificarPersFisica(PersonaFisica persFisica) throws PresentacionException {
		logger.info("Se ingresa a modificarPersFisica");
		Integer resultado = null;
		try (Connection conn = Conector.getConn()) {
			if(persFisica != null) {
				persFisica.setSinc(Sinc.N);
				persFisica.setUltAct(new Fecha(Fecha.AMDHMS));
				resultado = getInterfacePersona().modificarPersFisica(conn, persFisica);
				Conector.commitConn(conn);
			}
		} catch (PersistenciaException | SQLException e) {
			logger.fatal("Excepcion en ManagerPersona > modificarPersFisica: " + e.getMessage(), e);
			throw new PresentacionException(e);
		} catch (Exception e) {
			logger.fatal("Excepcion GENERICA en ManagerPersona > modificarPersFisica: " + e.getMessage(), e);
			throw new PresentacionException(e);
		}
		return resultado;
	}
	
	public Integer eliminarPersFisica(PersonaFisica persFisica) throws PresentacionException {
		logger.info("Se ingresa a eliminarPersFisica");
		Integer resultado = null;
		if(persFisica != null) {
			try (Connection conn = Conector.getConn()) {
				resultado = getInterfacePersona().eliminarPersFisica(conn, persFisica);
				Conector.commitConn(conn);
			} catch (PersistenciaException | SQLException e) {
				logger.fatal("Excepcion en ManagerPersona > eliminarPersFisica: " + e.getMessage(), e);
				throw new PresentacionException(e);
			} catch (Exception e) {
				logger.fatal("Excepcion GENERICA en ManagerPersona > eliminarPersFisica: " + e.getMessage(), e);
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
		try (Connection conn = Conector.getConn()) {
			Integer idLoc = (loc != null ? loc.getIdLocalidad() : CnstQryGeneric.NUMBER_INVALID);
			listaPj = getInterfacePersona().obtenerBusquedaPersJuridica(conn, rut, nombre, razonSoc, bps, bse, esProv, direccion, telefono, celular, email, idLoc);
		} catch (PersistenciaException | SQLException e) {
			logger.fatal("Excepcion en ManagerPersona > obtenerBusquedaPersJuridica: " + e.getMessage(), e);
			throw new PresentacionException(e);
		} catch (Exception e) {
			logger.fatal("Excepcion GENERICA en ManagerPersona > obtenerBusquedaPersJuridica: " + e.getMessage(), e);
			throw new PresentacionException(e);
		}
		return listaPj;
	}
	
	public List<PersonaJuridica> obtenerListaProveedor() throws PresentacionException {
		logger.info("Ingresa obtenerListaProveedor");
		List<PersonaJuridica> listaPj = null;
		try (Connection conn = Conector.getConn()) {
			listaPj = getInterfacePersona().obtenerListaEmpresasPorTipo(conn, true);
		} catch (PersistenciaException | SQLException e) {
			logger.fatal("Excepcion en ManagerPersona > obtenerListaProveedor: " + e.getMessage(), e);
			throw new PresentacionException(e);
		} catch (Exception e) {
			logger.fatal("Excepcion GENERICA en ManagerPersona > obtenerListaProveedor: " + e.getMessage(), e);
			throw new PresentacionException(e);
		}
		return listaPj;
	}
	
	public List<PersonaJuridica> obtenerListaEmpresas() throws PresentacionException {
		logger.info("Ingresa obtenerListaEmpresas");
		List<PersonaJuridica> listaPj = null;
		try (Connection conn = Conector.getConn()) {
			listaPj = getInterfacePersona().obtenerListaEmpresasPorTipo(conn, null);
		} catch (PersistenciaException | SQLException e) {
			logger.fatal("Excepcion en ManagerPersona > obtenerListaEmpresas: " + e.getMessage(), e);
			throw new PresentacionException(e);
		} catch (Exception e) {
			logger.fatal("Excepcion GENERICA en ManagerPersona > obtenerListaEmpresas: " + e.getMessage(), e);
			throw new PresentacionException(e);
		}
		return listaPj;
	}
	
	public PersonaJuridica obtenerPersJuridicaPorId(Long id) throws PresentacionException {
		logger.info("Se ingresa a obtenerPersJuridicaPorId");
		PersonaJuridica persJuridica = null;
		try (Connection conn = Conector.getConn()) {
			persJuridica = getInterfacePersona().obtenerPersJuridicaPorId(conn, id);
		} catch (PersistenciaException | SQLException e) {
			logger.fatal("Excepcion en ManagerPersona > obtenerPersJuridicaPorId: " + e.getMessage(), e);
			throw new PresentacionException(e);
		} catch (Exception e) {
			logger.fatal("Excepcion GENERICA en ManagerPersona > obtenerPersJuridicaPorId: " + e.getMessage(), e);
			throw new PresentacionException(e);
		}
		return persJuridica;
	}
	
	public Integer guardarPersJuridica(PersonaJuridica persJuridica) throws PresentacionException {
		logger.info("Ingresa guardarPersJuridica");
		Integer resultado = null;
		try (Connection conn = Conector.getConn()) {
			if(persJuridica != null) {
				persJuridica.setSinc(Sinc.N);
				persJuridica.setUltAct(new Fecha(Fecha.AMDHMS));
				resultado = getInterfacePersona().guardarPersJuridica(conn, persJuridica);
				Conector.commitConn(conn);
			}
		} catch (PersistenciaException | SQLException e) {
			logger.fatal("Excepcion en ManagerPersona > guardarPersJuridica: " + e.getMessage(), e);
			throw new PresentacionException(e);
		} catch (Exception e) {
			logger.fatal("Excepcion GENERICA en ManagerPersona > guardarPersJuridica: " + e.getMessage(), e);
			throw new PresentacionException(e);
		}
		return resultado;
	}
	
	public Integer modificarPersJuridica(PersonaJuridica persJuridica) throws PresentacionException {
		logger.info("Se ingresa a modificarPersJuridica");
		Integer resultado = null;
		try (Connection conn = Conector.getConn()) {
			if(persJuridica != null) {
				persJuridica.setSinc(Sinc.N);
				persJuridica.setUltAct(new Fecha(Fecha.AMDHMS));
				resultado = getInterfacePersona().modificarPersJuridica(conn, persJuridica);
				Conector.commitConn(conn);
			}
		} catch (PersistenciaException | SQLException e) {
			logger.fatal("Excepcion en ManagerPersona > modificarPersJuridica: " + e.getMessage(), e);
			throw new PresentacionException(e);
		} catch (Exception e) {
			logger.fatal("Excepcion GENERICA en ManagerPersona > modificarPersJuridica: " + e.getMessage(), e);
			throw new PresentacionException(e);
		}
		return resultado;
	}
	
	public Integer eliminarPersJuridica(PersonaJuridica persJuridica) throws PresentacionException {
		logger.info("Se ingresa a eliminarPersFisica");
		Integer resultado = null;
		try (Connection conn = Conector.getConn()) {
			if(persJuridica != null) {
				resultado = getInterfacePersona().eliminarPersJuridica(conn, persJuridica);
				Conector.commitConn(conn);
			}
		} catch (PersistenciaException | SQLException e) {
			logger.fatal("Excepcion en ManagerPersona > eliminarPersJuridica: " + e.getMessage(), e);
			throw new PresentacionException(e);
		} catch (Exception e) {
			logger.fatal("Excepcion GENERICA en ManagerPersona > eliminarPersJuridica: " + e.getMessage(), e);
			throw new PresentacionException(e);
		}
		return resultado;
	}
	
	/*****************************************************************************************************************************************************/
	/** PERSONA GENERICO */
	/*****************************************************************************************************************************************************/
	
	@SuppressWarnings("unchecked")
	public List<Persona> obtenerBusquedaPersona(TipoPersona tp, String filtroBusq, Localidad loc) throws PresentacionException {
		List<Persona> listaPersona = new ArrayList<>();
		Integer idLoc = loc != null ? loc.getIdLocalidad() : new Integer(-1);
		try (Connection conn = Conector.getConn()) {
			if(tp.equals(TipoPersona.F)) {
				List<PersonaFisica> listaPf = getInterfacePersona().obtenerBusquedaPersFisicaGenerico(conn, filtroBusq, idLoc);
				listaPersona = (List<Persona>) (List<? extends Persona>) listaPf;
			} else if(tp.equals(TipoPersona.J)) {
				List<PersonaJuridica> listaPj = getInterfacePersona().obtenerBusquedaPersJuridicaGenerico(conn, filtroBusq, idLoc);
				listaPersona = (List<Persona>) (List<? extends Persona>) listaPj;
			} else {
				Conector.rollbackConn(conn);
				throw new PresentacionException("obtenerBusquedaPersona ha sido mal implementado!");
			}
		} catch (PersistenciaException | SQLException e) {
			logger.fatal("Excepcion en ManagerPersona > obtenerBusquedaPersona: " + e.getMessage(), e);
			throw new PresentacionException(e);
		} catch (Exception e) {
			logger.fatal("Excepcion GENERICA en ManagerPersona > obtenerBusquedaPersona: " + e.getMessage(), e);
			throw new PresentacionException(e);
		}
		return listaPersona;
	}
	
	public Long obtenerIdPersonaGenerico(Persona persona) throws PresentacionException {
		Long idPersona = null;
		if(persona != null) {
			if(persona instanceof PersonaFisica) {
				PersonaFisica pf = (PersonaFisica) persona;
				idPersona = pf.getDocumento();
			} else if(persona instanceof PersonaJuridica) {
				PersonaJuridica pj = (PersonaJuridica) persona;
				idPersona = pj.getRut();
			} else {
				throw new PresentacionException("obtenerIdPersonaGenerico ha sido mal implementado!");
			}
		}
		return idPersona;
	}

	public Boolean checkExistPersona(Long idPersona) throws PresentacionException {
		try (Connection conn = Conector.getConn()) {
			if(idPersona != null) {
				return getInterfacePersona().checkExistPersona(conn, idPersona);
			}
		} catch (PersistenciaException | SQLException e) {
			logger.fatal("Excepcion en ManagerPersona > obtenerBusquedaPersona: " + e.getMessage(), e);
			throw new PresentacionException(e);
		} catch (Exception e) {
			logger.fatal("Excepcion GENERICA en ManagerPersona > obtenerBusquedaPersona: " + e.getMessage(), e);
			throw new PresentacionException(e);
		}
		return false;
	}
	
	/*****************************************************************************************************************************************************/
	/** DEP - LOC */
	/*****************************************************************************************************************************************************/
	
	public List<Departamento> obtenerListaDepartamento() throws PresentacionException {
		logger.info("Se ingresa a obtenerListaDepartamento");
		List<Departamento> listaDep = null;
		try (Connection conn = Conector.getConn()) {
			listaDep = getInterfaceDepLoc().obtenerListaDepartamentos(conn);
		} catch (PersistenciaException | SQLException e) {
			logger.fatal("Excepcion en ManagerPersona > obtenerListaDepartamento: " + e.getMessage(), e);
			throw new PresentacionException(e);
		} catch (Exception e) {
			logger.fatal("Excepcion GENERICA en ManagerPersona > obtenerListaDepartamento: " + e.getMessage(), e);
			throw new PresentacionException(e);
		}
		return listaDep;
	}
	
	public List<Localidad> obtenerListaLocalidadPorDep(Integer idDep) throws PresentacionException {
		logger.info("Se ingresa a obtenerListaLocalidadPorDep");
		List<Localidad> listaLoc = null;
		try (Connection conn = Conector.getConn()) {
			listaLoc = getInterfaceDepLoc().obtenerListaLocPorDep(conn, idDep);
		} catch (PersistenciaException | SQLException e) {
			logger.fatal("Excepcion en ManagerPersona > obtenerListaLocalidadPorDep: " + e.getMessage(), e);
			throw new PresentacionException(e);
		} catch (Exception e) {
			logger.fatal("Excepcion GENERICA en ManagerPersona > obtenerListaLocalidadPorDep: " + e.getMessage(), e);
			throw new PresentacionException(e);
		}
		return listaLoc;
	}




}
