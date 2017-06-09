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
	private IPersPersona interfacePersona;
	private IPersTipoDoc interfaceTipoDoc;
	private IPersDepLoc interfaceDepLoc;
	
	
	/* TIPO DOC */
	
	public List<TipoDoc> obtenerListaTipoDoc() {
		logger.info("Se ingresa a obtenerListaTipoDoc");
		List<TipoDoc> listaTipoDoc = null;
		interfaceTipoDoc = new PersistenciaTipoDoc();
		try {
			Conector.getConn();
			listaTipoDoc = interfaceTipoDoc.obtenerListaTipoDoc();
		} catch (PersistenciaException e) {
			e.printStackTrace();
		} finally {
			Conector.closeConn("obtenerListaTipoDoc", null);
		}
		return listaTipoDoc;
	}
	
	/* PERSONA JURIDICA */
	
	public PersonaFisica obtenerPersFisicaPorId(Long id) {
		logger.info("Se ingresa a obtenerPersFisicaPorId");
		PersonaFisica persFisica = null;
		interfacePersona = new PersistenciaPersona();
		try {
			Conector.getConn();
			persFisica = interfacePersona.obtenerPersFisicaPorId(id);
		} catch (PersistenciaException e) {
			e.printStackTrace();//FIXME ver como manejar esta excep
		} finally {
			Conector.closeConn("obtenerPersFisicaPorId", null);
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
		} catch (PersistenciaException e) {
			e.printStackTrace();//FIXME ver como manejar esta excep
		} finally {
			Conector.closeConn("obtenerBusquedaPersFisica", null);
		}
		return listaPf;
	}
	
	public Integer guardarPersFisica(PersonaFisica persFisica) {
		logger.info("Ingresa guardarPersFisica");
		Integer resultado = null;
		if(persFisica != null) {
			interfacePersona = new PersistenciaPersona();
			try {
				persFisica.setSinc(Sinc.N);
				persFisica.setUltAct(new Fecha(Fecha.AMDHMS));
				Conector.getConn();
				resultado = interfacePersona.guardarPersFisica(persFisica);
			} catch (PersistenciaException e) {
				e.printStackTrace();//FIXME ver como manejar esta excep
			} finally {
				Conector.closeConn("guardarPersFisica", null);
			}
		}
		return resultado;
	}
	
	public Integer modificarPersFisica(PersonaFisica persFisica) {
		logger.info("Se ingresa a modificarPersFisica");
		Integer resultado = null;
		if(persFisica != null) {
			interfacePersona = new PersistenciaPersona();
			try {
				persFisica.setSinc(Sinc.N);
				persFisica.setUltAct(new Fecha(Fecha.AMDHMS));
				Conector.getConn();
				resultado = interfacePersona.modificarPersFisica(persFisica);
			} catch (PersistenciaException e) {
				//FIXME ver como manejar esta excep
				e.printStackTrace();
			} finally {
				Conector.closeConn("modificarPersFisica", null);
			}
		}
		return resultado;
	}
	
	public Integer eliminarPersFisica(PersonaFisica persFisica) {
		logger.info("Se ingresa a eliminarPersFisica");
		Integer resultado = null;
		if(persFisica != null) {
			interfacePersona = new PersistenciaPersona();
			try {
				Conector.getConn();
				resultado = interfacePersona.eliminarPersFisica(persFisica);
			} catch (PersistenciaException e) {
				//FIXME ver como manejar esta excep
				e.printStackTrace();
			} finally {
				Conector.closeConn("eliminarPersFisica", null);
			}
		}
		return resultado;
	}

	/* PERSONA JURIDICA */
	
	public List<PersonaJuridica> obtenerBusquedaPersJuridica(Long rut, String nombre, String razonSoc, String bps, String bse, 
			Boolean esProv, String direccion, String telefono, String celular, String email, Localidad loc) {
		logger.info("Ingresa obtenerBusquedaPersJuridica");
		List<PersonaJuridica> listaPj = null;
		interfacePersona = new PersistenciaPersona();
		try {
			Conector.getConn();
			Integer idLoc = (loc != null ? loc.getIdLocalidad() : CnstQryGeneric.NUMBER_INVALID);
			listaPj = interfacePersona.obtenerBusquedaPersJuridica(rut, nombre, razonSoc, bps, bse, esProv, direccion, telefono, celular, email, idLoc);
		} catch (PersistenciaException e) {
			e.printStackTrace();//FIXME ver como manejar esta excep
		} finally {
			Conector.closeConn("obtenerBusquedaPersJuridica", null);
		}
		return listaPj;
	}
	
	public List<PersonaJuridica> obtenerListaProveedor() {
		logger.info("Ingresa obtenerListaProveedor");
		List<PersonaJuridica> listaPj = null;
		interfacePersona = new PersistenciaPersona();
		try {
			Conector.getConn();
			listaPj = interfacePersona.obtenerListaEmpresasPorTipo(true);
		} catch (PersistenciaException e) {
			e.printStackTrace();//FIXME ver como manejar esta excep
		} finally {
			Conector.closeConn("obtenerListaProveedor", null);
		}
		return listaPj;
	}
	
	public List<PersonaJuridica> obtenerListaEmpresas() {
		logger.info("Ingresa obtenerListaProveedor");
		List<PersonaJuridica> listaPj = null;
		interfacePersona = new PersistenciaPersona();
		try {
			Conector.getConn();
			listaPj = interfacePersona.obtenerListaEmpresasPorTipo(null);
		} catch (PersistenciaException e) {
			e.printStackTrace();//FIXME ver como manejar esta excep
		} finally {
			Conector.closeConn("obtenerListaProveedor", null);
		}
		return listaPj;
	}
	
	public PersonaJuridica obtenerPersJuridicaPorId(Long id) {
		logger.info("Se ingresa a obtenerPersJuridicaPorId");
		PersonaJuridica persJuridica = null;
		interfacePersona = new PersistenciaPersona();
		try {
			Conector.getConn();
			persJuridica = interfacePersona.obtenerPersJuridicaPorId(id);
		} catch (PersistenciaException e) {
			e.printStackTrace();//FIXME ver como manejar esta excep
		} finally {
			Conector.closeConn("obtenerPersJuridicaPorId", null);
		}
		return persJuridica;
	}
	
	public Integer guardarPersJuridica(PersonaJuridica persJuridica) {
		logger.info("Ingresa guardarPersJuridica");
		Integer resultado = null;
		if(persJuridica != null) {
			interfacePersona = new PersistenciaPersona();
			try {
				persJuridica.setSinc(Sinc.N);
				persJuridica.setUltAct(new Fecha(Fecha.AMDHMS));
				Conector.getConn();
				resultado = interfacePersona.guardarPersJuridica(persJuridica);
			} catch (PersistenciaException e) {
				e.printStackTrace();//FIXME ver como manejar esta excep
			} finally {
				Conector.closeConn("guardarPersJuridica", null);
			}
		}
		return resultado;
	}
	
	public Integer modificarPersJuridica(PersonaJuridica persJuridica) {
		logger.info("Se ingresa a modificarPersJuridica");
		Integer resultado = null;
		if(persJuridica != null) {
			interfacePersona = new PersistenciaPersona();
			try {
				persJuridica.setSinc(Sinc.N);
				persJuridica.setUltAct(new Fecha(Fecha.AMDHMS));
				Conector.getConn();
				resultado = interfacePersona.modificarPersJuridica(persJuridica);
			} catch (PersistenciaException e) {
				//FIXME ver como manejar esta excep
				e.printStackTrace();
			} finally {
				Conector.closeConn("modificarPersJuridica", null);
			}
		}
		return resultado;
	}
	
	public Integer eliminarPersFisica(PersonaJuridica persJuridica) {
		logger.info("Se ingresa a eliminarPersFisica");
		Integer resultado = null;
		if(persJuridica != null) {
			interfacePersona = new PersistenciaPersona();
			try {
				Conector.getConn();
				resultado = interfacePersona.eliminarPersJuridica(persJuridica);
			} catch (PersistenciaException e) {
				//FIXME ver como manejar esta excep
				e.printStackTrace();
			} finally {
				Conector.closeConn("eliminarPersFisica", null);
			}
		}
		return resultado;
	}
	
	/* DEP Y LOC */
	
	public List<Departamento> obtenerListaDepartamento() {
		logger.info("Se ingresa a obtenerListaDepartamento");
		List<Departamento> listaDep = null;
		interfaceDepLoc = new PersistenciaDepLoc();
		try {
			Conector.getConn();
			listaDep = interfaceDepLoc.obtenerListaDepartamentos();
		} catch (PersistenciaException e) {
			e.printStackTrace();//FIXME ver como manejar esta excep
		} finally {
			Conector.closeConn("obtenerListaDepartamento", null);
		}
		return listaDep;
	}
	
	public List<Localidad> obtenerListaLocalidadPorDep(Integer idDep) {
		logger.info("Se ingresa a obtenerListaLocalidadPorDep");
		List<Localidad> listaLoc = null;
		interfaceDepLoc = new PersistenciaDepLoc();
		try {
			Conector.getConn();
			listaLoc = interfaceDepLoc.obtenerListaLocPorDep(idDep);
		} catch (PersistenciaException e) {
			e.printStackTrace();//FIXME ver como manejar esta excep
		} finally {
			Conector.closeConn("obtenerListaLocalidadPorDep", null);
		}
		return listaLoc;
	}



}
