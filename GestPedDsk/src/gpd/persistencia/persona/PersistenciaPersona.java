package gpd.persistencia.persona;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import gpd.db.constantes.CnstQryPersona;
import gpd.db.generic.GenSqlExecType;
import gpd.db.generic.GenSqlSelectType;
import gpd.dominio.persona.Persona;
import gpd.dominio.persona.PersonaFisica;
import gpd.dominio.persona.PersonaJuridica;
import gpd.dominio.persona.Sexo;
import gpd.dominio.persona.TipoPersona;
import gpd.dominio.util.Origen;
import gpd.dominio.util.Sinc;
import gpd.exceptions.ConectorException;
import gpd.exceptions.PersistenciaException;
import gpd.interfaces.persona.IPersPersona;
import gpd.persistencia.conector.Conector;
import gpd.types.Fecha;

public class PersistenciaPersona extends Conector implements IPersPersona, CnstQryPersona {
	
	private static final Logger logger = Logger.getLogger(PersistenciaPersona.class);
	private Integer resultado;

	
	@Override
	public PersonaFisica obtenerPersFisicaPorId(Connection conn, Long id) throws PersistenciaException {
		PersonaFisica pf = null;
		PersistenciaTipoDoc ptd = new PersistenciaTipoDoc();
		PersistenciaDepLoc pdl = new PersistenciaDepLoc();
		try {
			GenSqlSelectType genSel = new GenSqlSelectType(QRY_SELECT_PF_XID);
			genSel.setParam(id);
			try (ResultSet rs = (ResultSet) runGeneric(conn, genSel)) {
				if(rs.next()) {
					pf = new PersonaFisica();
					pf.setDocumento(rs.getLong("documento"));
					pf.setTipoDoc(ptd.obtenerTipoDocPorId(conn, rs.getInt("id_tipo_doc")));
					pf.setApellido1(rs.getString("apellido1"));
					pf.setApellido2(rs.getString("apellido2"));
					pf.setNombre1(rs.getString("nombre1"));
					pf.setNombre2(rs.getString("nombre2"));
					pf.setFechaNac(rs.getDate("fecha_nac") != null ? new Fecha(rs.getDate("fecha_nac")) : null);
					char[] sexoChar = new char[1];
					rs.getCharacterStream("sexo").read(sexoChar);
					Sexo sexoE = Sexo.getSexoPorChar(sexoChar[0]);
					pf.setSexo(sexoE);
					//persona
					pf.setDireccion(rs.getString("direccion"));
					pf.setPuerta(rs.getString("puerta"));
					pf.setSolar(rs.getString("solar"));
					pf.setManzana(rs.getString("manzana"));
					pf.setKm(rs.getFloat("km"));
					pf.setComplemento(rs.getString("complemento"));
					pf.setTelefono(rs.getString("telefono"));
					pf.setCelular(rs.getString("celular"));
					pf.setEmail(rs.getString("email"));
					pf.setFechaReg(new Fecha(rs.getDate("fecha_reg")));
					char[] tipoChar = new char[1];
					rs.getCharacterStream("tipo").read(tipoChar);
					pf.setTipoPers(TipoPersona.getTipoPersonaPorChar(tipoChar[0]));
					pf.setLocalidad(pdl.obtenerLocalidadPorId(conn, rs.getInt("id_loc")));
					char[] origenChar = new char[1];
					rs.getCharacterStream("origen").read(origenChar);
					Origen origen = Origen.getOrigenPorChar(origenChar[0]);
					pf.setOrigen(origen);
					char[] sincChar = new char[1];
					rs.getCharacterStream("sinc").read(sincChar);
					Sinc sinc = Sinc.getSincPorChar(sincChar[0]);
					pf.setSinc(sinc);
					pf.setUltAct(new Fecha(rs.getTimestamp("ult_act")));
				}
			}
		} catch (ConectorException | SQLException | IOException e) {
			Conector.rollbackConn(conn);
			logger.fatal("Excepcion al obtenerPersFisicaPorId: " + e.getMessage(), e);
			throw new PersistenciaException(e);
		} catch (Exception e) {
			Conector.rollbackConn(conn);
			logger.fatal("Excepcion GENERICA al obtenerPersFisicaPorId: " + e.getMessage(), e);
			throw new PersistenciaException(e);
		}
		return pf;
	}

	@Override
	public PersonaJuridica obtenerPersJuridicaPorId(Connection conn, Long id) throws PersistenciaException {
		PersonaJuridica pj = null;
		PersistenciaDepLoc pdl = new PersistenciaDepLoc();
		try {
			GenSqlSelectType genSel = new GenSqlSelectType(QRY_SELECT_PJ_XID);
			genSel.setParam(id);
			try (ResultSet rs = (ResultSet) runGeneric(conn, genSel)) {
				if(rs.next()) {
					pj = new PersonaJuridica();
					pj.setRut(rs.getLong("rut"));
					pj.setNombre(rs.getString("nombre"));
					pj.setRazonSocial(rs.getString("razon_social"));
					pj.setBps(rs.getString("bps"));
					pj.setBse(rs.getString("bse"));
					char[] esProvChar = new char[1];
					rs.getCharacterStream("es_prov").read(esProvChar);
					pj.setEsProv(esProvChar[0] == S_CHAR ? true : false);
					//persona
					pj.setDireccion(rs.getString("direccion"));
					pj.setPuerta(rs.getString("puerta"));
					pj.setSolar(rs.getString("solar"));
					pj.setManzana(rs.getString("manzana"));
					pj.setKm(rs.getFloat("km"));
					pj.setComplemento(rs.getString("complemento"));
					pj.setTelefono(rs.getString("telefono"));
					pj.setCelular(rs.getString("celular"));
					pj.setEmail(rs.getString("email"));
					pj.setFechaReg(new Fecha(rs.getDate("fecha_reg")));
					char[] tipoChar = new char[1];
					rs.getCharacterStream("tipo").read(tipoChar);
					pj.setTipoPers(TipoPersona.getTipoPersonaPorChar(tipoChar[0]));
					pj.setLocalidad(pdl.obtenerLocalidadPorId(conn, rs.getInt("id_loc")));
					char[] origenChar = new char[1];
					rs.getCharacterStream("origen").read(origenChar);
					Origen origen = Origen.getOrigenPorChar(origenChar[0]);
					pj.setOrigen(origen);
					char[] sincChar = new char[1];
					rs.getCharacterStream("sinc").read(sincChar);
					Sinc sinc = Sinc.getSincPorChar(sincChar[0]);
					pj.setSinc(sinc);
					pj.setUltAct(new Fecha(rs.getTimestamp("ult_act")));
				}
			}
		} catch (ConectorException | SQLException | IOException e) {
			Conector.rollbackConn(conn);
			logger.fatal("Excepcion al obtenerPersJuridicaPorId: " + e.getMessage(), e);
			throw new PersistenciaException(e);
		} catch (Exception e) {
			Conector.rollbackConn(conn);
			logger.fatal("Excepcion GENERICA al obtenerPersJuridicaPorId: " + e.getMessage(), e);
			throw new PersistenciaException(e);
		} 
		return pj;
	}
	
	@Override
	public Integer guardarPersFisica(Connection conn, PersonaFisica pf) throws PersistenciaException {
		try {
			Long documento = pf.getDocumento();
			
			guardarPersona(conn, documento, pf.getDireccion(), pf.getPuerta(), pf.getSolar(), pf.getManzana(),
					pf.getKm(), pf.getComplemento(), pf.getTelefono(), pf.getCelular(), pf.getEmail(),
					pf.getFechaReg(), pf.getTipoPers(), pf.getLocalidad().getIdLocalidad(), pf.getOrigen(),
					pf.getSinc(), pf.getUltAct());
			
			GenSqlExecType genExec = new GenSqlExecType(QRY_INSERT_PF);
			genExec.setParam(documento);
			genExec.setParam(pf.getTipoDoc().getIdTipoDoc());
			genExec.setParam(pf.getApellido1());
			genExec.setParam(pf.getApellido2());
			genExec.setParam(pf.getNombre1());
			genExec.setParam(pf.getNombre2());
			genExec.setParam(pf.getFechaNac());
			genExec.setParam(pf.getSexo().getAsChar());
			resultado = (Integer) runGeneric(conn, genExec);
			
		} catch (ConectorException e) {
			Conector.rollbackConn(conn);
			logger.fatal("Excepcion al guardarPersFisica: " + e.getMessage(), e);
			throw new PersistenciaException(e);
		} catch (Exception e) {
			Conector.rollbackConn(conn);
			logger.fatal("Excepcion GENERICA al guardarPersFisica: " + e.getMessage(), e);
			throw new PersistenciaException(e);
		}
		return resultado;
	}
	
	@Override
	public Integer modificarPersFisica(Connection conn, PersonaFisica pf) throws PersistenciaException {
		try {
			Long documento = pf.getDocumento();
			Long docAnt = pf.getDocumentoAnt() != null ? pf.getDocumentoAnt() : null;
			
			modificarPersona(conn, documento, docAnt, pf.getDireccion(), pf.getPuerta(), pf.getSolar(), pf.getManzana(),
					pf.getKm(), pf.getComplemento(), pf.getTelefono(), pf.getCelular(), pf.getEmail(),
					pf.getFechaReg(), pf.getLocalidad().getIdLocalidad(), pf.getOrigen(),
					pf.getSinc(), pf.getUltAct());
			
			GenSqlExecType genExec = new GenSqlExecType(QRY_UPDATE_PF);
			genExec.setParam(pf.getTipoDoc().getIdTipoDoc());
			genExec.setParam(pf.getApellido1());
			genExec.setParam(pf.getApellido2());
			genExec.setParam(pf.getNombre1());
			genExec.setParam(pf.getNombre2());
			genExec.setParam(pf.getFechaNac());
			genExec.setParam(pf.getSexo().getAsChar());
			genExec.setParam(documento);
			resultado = (Integer) runGeneric(conn, genExec);
		} catch (ConectorException e) {
			Conector.rollbackConn(conn);
			logger.fatal("Excepcion al modificarPersFisica: " + e.getMessage(), e);
			throw new PersistenciaException(e);
		} catch (Exception e) {
			Conector.rollbackConn(conn);
			logger.fatal("Excepcion GENERICA al modificarPersFisica: " + e.getMessage(), e);
			throw new PersistenciaException(e);
		}
		return resultado;
	}

	@Override
	public Integer eliminarPersFisica(Connection conn, PersonaFisica pf) throws PersistenciaException {
		try {
			Long documento = pf.getDocumento();
			eliminarPersona(conn, documento);
			
			GenSqlExecType genExec = new GenSqlExecType(QRY_DELETE_PF);
			genExec.getExecuteDatosCond().put(1, documento);
			resultado = (Integer) runGeneric(conn, genExec);
		} catch (ConectorException e) {
			Conector.rollbackConn(conn);
			logger.fatal("Excepcion al eliminarPersFisica: " + e.getMessage(), e);
			throw new PersistenciaException(e);
		} catch (Exception e) {
			Conector.rollbackConn(conn);
			logger.fatal("Excepcion GENERICA al eliminarPersFisica: " + e.getMessage(), e);
			throw new PersistenciaException(e);
		}
		return resultado;
	}

	@Override
	public Integer guardarPersJuridica(Connection conn, PersonaJuridica pj) throws PersistenciaException {
		try {
			guardarPersona(conn, pj.getRut(), pj.getDireccion(), pj.getPuerta(), pj.getSolar(), pj.getManzana(),
					pj.getKm(), pj.getComplemento(), pj.getTelefono(), pj.getCelular(), pj.getEmail(),
					pj.getFechaReg(), pj.getTipoPers(), pj.getLocalidad().getIdLocalidad(), pj.getOrigen(),
					pj.getSinc(), pj.getUltAct());
			
			GenSqlExecType genExec = new GenSqlExecType(QRY_INSERT_PJ);
			genExec.setParam(pj.getRut());
			genExec.setParam(pj.getNombre());
			genExec.setParam(pj.getRazonSocial());
			genExec.setParam(pj.getBps());
			genExec.setParam(pj.getBse());
			genExec.setParam(pj.getEsProv() ? S_CHAR : N_CHAR);
			resultado = (Integer) runGeneric(conn, genExec);
		} catch (ConectorException e) {
			Conector.rollbackConn(conn);
			logger.fatal("Excepcion al guardarPersJuridica: " + e.getMessage(), e);
			throw new PersistenciaException(e);
		} catch (Exception e) {
			Conector.rollbackConn(conn);
			logger.fatal("Excepcion GENERICA al guardarPersJuridica: " + e.getMessage(), e);
			throw new PersistenciaException(e);
		}
		return resultado;
	}
	
	@Override
	public Integer modificarPersJuridica(Connection conn, PersonaJuridica pj) throws PersistenciaException {
		try {
			Long rut = pj.getRut();
			Long rutAnt = pj.getRutAnt() != null ? pj.getRutAnt() : null;
			
			modificarPersona(conn, rut, rutAnt, pj.getDireccion(), pj.getPuerta(), pj.getSolar(), pj.getManzana(),
					pj.getKm(), pj.getComplemento(), pj.getTelefono(), pj.getCelular(), pj.getEmail(),
					pj.getFechaReg(), pj.getLocalidad().getIdLocalidad(), pj.getOrigen(),
					pj.getSinc(), pj.getUltAct());
			
			GenSqlExecType genExec = new GenSqlExecType(QRY_UPDATE_PJ);
			genExec.setParam(pj.getNombre());
			genExec.setParam(pj.getRazonSocial());
			genExec.setParam(pj.getBps());
			genExec.setParam(pj.getBse());
			genExec.setParam(pj.getEsProv() ? S_CHAR : N_CHAR);
			genExec.setParam(rut);
			resultado = (Integer) runGeneric(conn, genExec);
			
		} catch (ConectorException e) {
			Conector.rollbackConn(conn);
			logger.fatal("Excepcion al modificarPersJuridica: " + e.getMessage(), e);
			throw new PersistenciaException(e);
		} catch (Exception e) {
			Conector.rollbackConn(conn);
			logger.fatal("Excepcion GENERICA al modificarPersJuridica: " + e.getMessage(), e);
			throw new PersistenciaException(e);
		}
		return resultado;
	}

	@Override
	public Integer eliminarPersJuridica(Connection conn, PersonaJuridica pj) throws PersistenciaException {
		try {
			Long rut = pj.getRut();
			eliminarPersona(conn, rut);
			
			GenSqlExecType genExec = new GenSqlExecType(QRY_DELETE_PJ);
			genExec.setParam(rut);
			resultado = (Integer) runGeneric(conn, genExec);
		} catch (ConectorException e) {
			Conector.rollbackConn(conn);
			logger.fatal("Excepcion al eliminarPersJuridica: " + e.getMessage(), e);
			throw new PersistenciaException(e);
		} catch (Exception e) {
			Conector.rollbackConn(conn);
			logger.fatal("Excepcion GENERICA al eliminarPersJuridica: " + e.getMessage(), e);
			throw new PersistenciaException(e);
		}
		return resultado;
	}
	
	private Integer guardarPersona(Connection conn, Long idPersona, String direccion, String puerta, String solar, String manzana,
			Float km, String complemento, String telefono, String celular, String email, Fecha fechaReg, TipoPersona tipoPers, 
			Integer idLoc, Origen origen, Sinc sinc, Fecha ultAct) throws PersistenciaException {
		try {
			GenSqlExecType genExec = new GenSqlExecType(QRY_INSERT_PERS);
			genExec.setParam(idPersona);
			genExec.setParam(direccion);
			genExec.setParam(puerta);
			genExec.setParam(solar);
			genExec.setParam(manzana);
			genExec.setParam(km);
			genExec.setParam(complemento);
			genExec.setParam(telefono);
			genExec.setParam(celular);
			genExec.setParam(email);
			genExec.setParam(fechaReg);
			genExec.setParam(tipoPers.getAsChar());
			genExec.setParam(idLoc);
			genExec.setParam(origen.getAsChar());
			genExec.setParam(sinc.getAsChar());
			genExec.setParam(ultAct);
			resultado = (Integer) runGeneric(conn, genExec);
		} catch (ConectorException e) {
			Conector.rollbackConn(conn);
			logger.fatal("Excepcion al guardarPersona: " + e.getMessage(), e);
			throw new PersistenciaException(e);
		} catch (Exception e) {
			Conector.rollbackConn(conn);
			logger.fatal("Excepcion GENERICA al guardarPersona: " + e.getMessage(), e);
			throw new PersistenciaException(e);
		}
		return resultado;
	}
	
	private Integer modificarPersona(Connection conn, Long idPersona, Long idPersonaAnt, String direccion, String puerta, String solar, String manzana,
			Float km, String complemento, String telefono, String celular, String email, Fecha fechaReg, Integer idLoc,
			Origen origen, Sinc sinc, Fecha ultAct) throws PersistenciaException {
		try {
			GenSqlExecType genExec = new GenSqlExecType(QRY_UPDATE_PERS);
			genExec.setParam(idPersona);
			genExec.setParam(direccion);
			genExec.setParam(puerta);
			genExec.setParam(solar);
			genExec.setParam(manzana);
			genExec.setParam(km);
			genExec.setParam(complemento);
			genExec.setParam(telefono);
			genExec.setParam(celular);
			genExec.setParam(email);
			genExec.setParam(fechaReg);
			genExec.setParam(idLoc);
			genExec.setParam(origen.getAsChar());
			genExec.setParam(sinc.getAsChar());
			genExec.setParam(ultAct);
			genExec.setParam(idPersonaAnt != null ? idPersonaAnt : idPersona);
			resultado = (Integer) runGeneric(conn, genExec);
		} catch (ConectorException e) {
			Conector.rollbackConn(conn);
			logger.fatal("Excepcion al modificarPersona: " + e.getMessage(), e);
			throw new PersistenciaException(e);
		} catch (Exception e) {
			Conector.rollbackConn(conn);
			logger.fatal("Excepcion GENERICA al modificarPersona: " + e.getMessage(), e);
			throw new PersistenciaException(e);
		}
		return resultado;
	}
	
	private Integer eliminarPersona(Connection conn, Long idPersona) throws PersistenciaException {
		try {
			GenSqlExecType genExec = new GenSqlExecType(QRY_DELETE_PERS);
			genExec.setParam(idPersona);
			resultado = (Integer) runGeneric(conn, genExec);
		} catch (ConectorException e) {
			Conector.rollbackConn(conn);
			logger.fatal("Excepcion al eliminarPersona: " + e.getMessage(), e);
			throw new PersistenciaException(e);
		} catch (Exception e) {
			Conector.rollbackConn(conn);
			logger.fatal("Excepcion GENERICA al eliminarPersona: " + e.getMessage(), e);
			throw new PersistenciaException(e);
		}
		return resultado;
	}

	@Override
	public List<PersonaFisica> obtenerBusquedaPersFisica(Connection conn, Long documento, String ape1, String ape2, String nom1, String nom2, 
			Character sexo, String direccion, String telefono, String celular, String email, Integer idLoc) throws PersistenciaException {
		List<PersonaFisica> listaPf = new ArrayList<>();
		try {
			GenSqlSelectType genSel = new GenSqlSelectType(QRY_SEARCH_PF);
			genSel.setParamEmptyAsNumber(documento);
			genSel.setParamEmptyAsNumber(documento);
			genSel.setParamLikeBoth(ape1);
			genSel.setParam(ape1);
			genSel.setParamLikeBoth(ape2);
			genSel.setParam(ape2);
			genSel.setParamLikeBoth(nom1);
			genSel.setParam(nom1);
			genSel.setParamLikeBoth(nom2);
			genSel.setParam(nom2);
			genSel.setParam(sexo);
			genSel.setParam(sexo);
			genSel.setParamLikeBoth(direccion);
			genSel.setParam(direccion);
			genSel.setParamLikeBoth(telefono);
			genSel.setParam(telefono);
			genSel.setParamLikeBoth(celular);
			genSel.setParam(celular);
			genSel.setParamLikeBoth(email);
			genSel.setParam(email);
			genSel.setParamEmptyAsNumber(idLoc);
			genSel.setParamEmptyAsNumber(idLoc);
			
			try (ResultSet rs = (ResultSet) runGeneric(conn, genSel)) {
				listaPf.addAll(cargarPfDesdeRs(conn, rs));
			}
		} catch (ConectorException e) {
			Conector.rollbackConn(conn);
			logger.fatal("Excepcion al obtenerBusquedaPersFisica: " + e.getMessage());
			throw new PersistenciaException(e);
		} catch (Exception e) {
			Conector.rollbackConn(conn);
			logger.fatal("Excepcion GENERICA al obtenerBusquedaPersFisica: " + e.getMessage(), e);
			throw new PersistenciaException(e);
		}
		return listaPf;
	}
	
	@Override
	public List<PersonaFisica> obtenerBusquedaPersFisicaGenerico(Connection conn, String filtroBusq, Integer idLoc)
			throws PersistenciaException {
		List<PersonaFisica> listaPf = new ArrayList<>();
		try {
			GenSqlSelectType genSel = new GenSqlSelectType(QRY_SEARCH_PF_GEN);
			genSel.setParamLongIfString(filtroBusq);
			genSel.setParamLikeBoth(filtroBusq);
			genSel.setParamLikeBoth(filtroBusq);
			genSel.setParamLikeBoth(filtroBusq);
			genSel.setParamLikeBoth(filtroBusq);
			genSel.setParamLikeBoth(filtroBusq);
			genSel.setParamLikeBoth(filtroBusq);
			genSel.setParamLikeBoth(filtroBusq);
			genSel.setParamLikeBoth(filtroBusq);
			genSel.setParamEmptyAsNumber(idLoc);
			genSel.setParamEmptyAsNumber(idLoc);
			
			try (ResultSet rs = (ResultSet) runGeneric(conn, genSel)) {
				listaPf.addAll(cargarPfDesdeRs(conn, rs));
			}
		} catch (ConectorException e) {
			Conector.rollbackConn(conn);
			logger.fatal("Excepcion al obtenerBusquedaPersFisicaGenerico: " + e.getMessage());
			throw new PersistenciaException(e);
		} catch (Exception e) {
			Conector.rollbackConn(conn);
			logger.fatal("Excepcion GENERICA al obtenerBusquedaPersFisicaGenerico: " + e.getMessage(), e);
			throw new PersistenciaException(e);
		}
		return listaPf;
	}

	@Override
	public List<PersonaJuridica> obtenerBusquedaPersJuridica(Connection conn, Long rut, String nombre, String razonSoc, String bps, String bse, 
			Boolean esProv, String direccion, String telefono, String celular, String email, Integer idLoc) throws PersistenciaException {
		List<PersonaJuridica> listaPj = new ArrayList<>();
		try {
			GenSqlSelectType genSel = new GenSqlSelectType(QRY_SEARCH_PJ);
			genSel.setParamEmptyAsNumber(rut);
			genSel.setParamEmptyAsNumber(rut);
			genSel.setParamLikeBoth(nombre);
			genSel.setParam(nombre);
			genSel.setParamLikeBoth(razonSoc);
			genSel.setParam(razonSoc);
			genSel.setParamLikeBoth(bps);
			genSel.setParam(bps);
			genSel.setParamLikeBoth(bse);
			genSel.setParam(bse);
			genSel.setParam(esProv ? S_CHAR : N_CHAR);
			genSel.setParam(esProv ? S_CHAR : N_CHAR);
			genSel.setParamLikeBoth(direccion);
			genSel.setParam(direccion);
			genSel.setParamLikeBoth(telefono);
			genSel.setParam(telefono);
			genSel.setParamLikeBoth(celular);
			genSel.setParam(celular);
			genSel.setParamLikeBoth(email);
			genSel.setParam(email);
			genSel.setParamEmptyAsNumber(idLoc);
			genSel.setParamEmptyAsNumber(idLoc);
			
			try (ResultSet rs = (ResultSet) runGeneric(conn, genSel)) {
				listaPj.addAll(cargarPjDesdeRs(conn, rs));
			}
		} catch (ConectorException | SQLException e) {
			Conector.rollbackConn(conn);
			logger.fatal("Excepcion al obtenerBusquedaPersJuridica: " + e.getMessage());
			throw new PersistenciaException(e);
		} catch (Exception e) {
			Conector.rollbackConn(conn);
			logger.fatal("Excepcion GENERICA al obtenerBusquedaPersJuridica: " + e.getMessage(), e);
			throw new PersistenciaException(e);
		}
		return listaPj;
	}
	
	@Override
	public List<PersonaJuridica> obtenerBusquedaPersJuridicaGenerico(Connection conn, String filtroBusq, Integer idLoc)
			throws PersistenciaException {
		List<PersonaJuridica> listaPj = new ArrayList<>();
		try {
			GenSqlSelectType genSel = new GenSqlSelectType(QRY_SEARCH_PJ_GEN);
			genSel.setParamLongIfString(filtroBusq);
			genSel.setParamLikeBoth(filtroBusq);
			genSel.setParamLikeBoth(filtroBusq);
			genSel.setParamLikeBoth(filtroBusq);
			genSel.setParamLikeBoth(filtroBusq);
			genSel.setParamLikeBoth(filtroBusq);
			genSel.setParamLikeBoth(filtroBusq);
			genSel.setParamLikeBoth(filtroBusq);
			genSel.setParamLikeBoth(filtroBusq);
			genSel.setParamEmptyAsNumber(idLoc);
			genSel.setParamEmptyAsNumber(idLoc);
			try (ResultSet rs = (ResultSet) runGeneric(conn, genSel)) {
				listaPj.addAll(cargarPjDesdeRs(conn, rs));
			}
		} catch (ConectorException | SQLException e) {
			Conector.rollbackConn(conn);
			logger.fatal("Excepcion al obtenerBusquedaPersJuridicaGenerico: " + e.getMessage());
			throw new PersistenciaException(e);
		} catch (Exception e) {
			Conector.rollbackConn(conn);
			logger.fatal("Excepcion GENERICA al obtenerBusquedaPersJuridicaGenerico: " + e.getMessage(), e);
			throw new PersistenciaException(e);
		}
		return listaPj;
	}
	
	@Override
	public List<PersonaJuridica> obtenerListaEmpresasPorTipo(Connection conn, Boolean esProv) throws PersistenciaException {
		List<PersonaJuridica> listaPj = new ArrayList<>();
		PersistenciaDepLoc pdl = new PersistenciaDepLoc();
		try {
			GenSqlSelectType genSel = new GenSqlSelectType(QRY_SELECT_PJ);
			genSel.setParam(esProv != null && esProv ? S_CHAR : EMPTY_CHAR);
			genSel.setParam(esProv != null && esProv ? S_CHAR : EMPTY_CHAR);
			try (ResultSet rs = (ResultSet) runGeneric(conn, genSel)) {
				while(rs.next()) {
					PersonaJuridica pj = new PersonaJuridica();
					pj.setRut(rs.getLong("rut"));
					pj.setNombre(rs.getString("nombre"));
					pj.setRazonSocial(rs.getString("razon_social"));
					pj.setBps(rs.getString("bps"));
					pj.setBse(rs.getString("bse"));
					char[] esProvChar = new char[1];
					rs.getCharacterStream("es_prov").read(esProvChar);
					pj.setEsProv((esProvChar[0] == S_CHAR) ? true : false);
					//persona
					pj.setDireccion(rs.getString("direccion"));
					pj.setPuerta(rs.getString("puerta"));
					pj.setSolar(rs.getString("solar"));
					pj.setManzana(rs.getString("manzana"));
					pj.setKm(rs.getFloat("km"));
					pj.setComplemento(rs.getString("complemento"));
					pj.setTelefono(rs.getString("telefono"));
					pj.setCelular(rs.getString("celular"));
					pj.setEmail(rs.getString("email"));
					pj.setFechaReg(new Fecha(rs.getDate("fecha_reg")));
					char[] tipoChar = new char[1];
					rs.getCharacterStream("tipo").read(tipoChar);
					pj.setTipoPers(TipoPersona.getTipoPersonaPorChar(tipoChar[0]));
					pj.setLocalidad(pdl.obtenerLocalidadPorId(conn, rs.getInt("id_loc")));
					char[] origenChar = new char[1];
					rs.getCharacterStream("origen").read(origenChar);
					Origen origen = Origen.getOrigenPorChar(origenChar[0]);
					pj.setOrigen(origen);
					char[] sincChar = new char[1];
					rs.getCharacterStream("sinc").read(sincChar);
					Sinc sinc = Sinc.getSincPorChar(sincChar[0]);
					pj.setSinc(sinc);
					pj.setUltAct(new Fecha(rs.getTimestamp("ult_act")));
					
					listaPj.add(pj);
				}
			}
		} catch (ConectorException | SQLException | IOException e) {
			Conector.rollbackConn(conn);
			logger.fatal("Excepcion al obtenerListaEmpresasPorTipo: " + e.getMessage(), e);
			throw new PersistenciaException(e);
		} catch (Exception e) {
			Conector.rollbackConn(conn);
			logger.fatal("Excepcion GENERICA al obtenerListaEmpresasPorTipo: " + e.getMessage(), e);
			throw new PersistenciaException(e);
		}
		return listaPj;
	}

	
	/***************************************************/
	/* METODOS GENERICOS */
	/***************************************************/

	/**
	 * metodo que recibe el resultset de la consulta, y carga la lista de personas fisica
	 * @param ResultSet rs
	 * @return lista persona fisica
	 * @throws PersistenciaException
	 */
	private List<PersonaFisica> cargarPfDesdeRs(Connection conn, ResultSet rs) throws PersistenciaException {
		List<PersonaFisica> listaPf = new ArrayList<>();
		PersistenciaTipoDoc ptd = new PersistenciaTipoDoc();
		PersistenciaDepLoc pdl = new PersistenciaDepLoc();
		try {
			while(rs.next()) {
				PersonaFisica pf = new PersonaFisica();
				pf.setDocumento(rs.getLong("documento"));
				pf.setTipoDoc(ptd.obtenerTipoDocPorId(conn, rs.getInt("id_tipo_doc")));
				pf.setApellido1(rs.getString("apellido1"));
				pf.setApellido2(rs.getString("apellido2"));
				pf.setNombre1(rs.getString("nombre1"));
				pf.setNombre2(rs.getString("nombre2"));
				pf.setFechaNac(rs.getDate("fecha_nac") != null ? new Fecha(rs.getDate("fecha_nac")) : null);
				char[] sexoChar = new char[1];
				rs.getCharacterStream("sexo").read(sexoChar);
				Sexo sexoE = Sexo.getSexoPorChar(sexoChar[0]);
				pf.setSexo(sexoE);
				//persona
				pf.setDireccion(rs.getString("direccion"));
				pf.setPuerta(rs.getString("puerta"));
				pf.setSolar(rs.getString("solar"));
				pf.setManzana(rs.getString("manzana"));
				pf.setKm(rs.getFloat("km"));
				pf.setComplemento(rs.getString("complemento"));
				pf.setTelefono(rs.getString("telefono"));
				pf.setCelular(rs.getString("celular"));
				pf.setEmail(rs.getString("email"));
				pf.setFechaReg(new Fecha(rs.getDate("fecha_reg")));
				char[] tipoChar = new char[1];
				rs.getCharacterStream("tipo").read(tipoChar);
				pf.setTipoPers(TipoPersona.getTipoPersonaPorChar(tipoChar[0]));
				pf.setLocalidad(pdl.obtenerLocalidadPorId(conn, rs.getInt("id_loc")));
				char[] origenChar = new char[1];
				rs.getCharacterStream("origen").read(origenChar);
				Origen origen = Origen.getOrigenPorChar(origenChar[0]);
				pf.setOrigen(origen);
				char[] sincChar = new char[1];
				rs.getCharacterStream("sinc").read(sincChar);
				Sinc sinc = Sinc.getSincPorChar(sincChar[0]);
				pf.setSinc(sinc);
				pf.setUltAct(new Fecha(rs.getTimestamp("ult_act")));
				
				listaPf.add(pf);
			}
		} catch (SQLException | PersistenciaException | IOException e) {
			//no rollbackea ya que debe ser metodo contenido por otro de pers
			logger.fatal("Excepcion al cargarPfDesdeRs: " + e.getMessage(), e);
			throw new PersistenciaException(e);
		} catch (Exception e) {
			//no rollbackea ya que debe ser metodo contenido por otro de pers
			logger.fatal("Excepcion GENERICA al cargarPfDesdeRs: " + e.getMessage(), e);
			throw new PersistenciaException(e);
		}
		return listaPf;
	}
	
	/**
	 * metodo que recibe el resultset de la consulta, y carga la lista de persona juridica
	 * @param ResultSet rs
	 * @return lista persona juridica
	 * @throws PersistenciaException
	 */
	private List<PersonaJuridica> cargarPjDesdeRs(Connection conn, ResultSet rs) throws PersistenciaException {
		List<PersonaJuridica> listaPj = new ArrayList<>();
		PersistenciaDepLoc pdl = new PersistenciaDepLoc();
		try {
			while(rs.next()) {
				PersonaJuridica pj = new PersonaJuridica();
				pj.setRut(rs.getLong("rut"));
				pj.setNombre(rs.getString("nombre"));
				pj.setRazonSocial(rs.getString("razon_social"));
				pj.setBps(rs.getString("bps"));
				pj.setBse(rs.getString("bse"));
				char[] esProvChar = new char[1];
				rs.getCharacterStream("es_prov").read(esProvChar);
				pj.setEsProv((esProvChar[0] == S_CHAR) ? true : false);
				//persona
				pj.setDireccion(rs.getString("direccion"));
				pj.setPuerta(rs.getString("puerta"));
				pj.setSolar(rs.getString("solar"));
				pj.setManzana(rs.getString("manzana"));
				pj.setKm(rs.getFloat("km"));
				pj.setComplemento(rs.getString("complemento"));
				pj.setTelefono(rs.getString("telefono"));
				pj.setCelular(rs.getString("celular"));
				pj.setEmail(rs.getString("email"));
				pj.setFechaReg(new Fecha(rs.getDate("fecha_reg")));
				char[] tipoChar = new char[1];
				rs.getCharacterStream("tipo").read(tipoChar);
				pj.setTipoPers(TipoPersona.getTipoPersonaPorChar(tipoChar[0]));
				pj.setLocalidad(pdl.obtenerLocalidadPorId(conn, rs.getInt("id_loc")));
				char[] origenChar = new char[1];
				rs.getCharacterStream("origen").read(origenChar);
				Origen origen = Origen.getOrigenPorChar(origenChar[0]);
				pj.setOrigen(origen);
				char[] sincChar = new char[1];
				rs.getCharacterStream("sinc").read(sincChar);
				Sinc sinc = Sinc.getSincPorChar(sincChar[0]);
				pj.setSinc(sinc);
				pj.setUltAct(new Fecha(rs.getTimestamp("ult_act")));
				
				listaPj.add(pj);
			}
		} catch (SQLException | PersistenciaException | IOException e) {
			//no rollbackea ya que debe ser metodo contenido por otro de pers
			logger.fatal("Excepcion al cargarPjDesdeRs: " + e.getMessage(), e);
			throw new PersistenciaException(e);
		} catch (Exception e) {
			//no rollbackea ya que debe ser metodo contenido por otro de pers
			logger.fatal("Excepcion GENERICA al cargarPjDesdeRs: " + e.getMessage(), e);
			throw new PersistenciaException(e);
		}
		return listaPj;
	}
	
	@Override
	public Persona obtenerPersGenerico(Connection conn, Long idPersona) throws PersistenciaException {
		Persona persona = null;
		try {
			GenSqlSelectType genSel = new GenSqlSelectType(QRY_SELECT_PERS_GENERIC);
			genSel.setParam(idPersona);
			try (ResultSet rs = (ResultSet) runGeneric(conn, genSel)) {
				if(rs.next()) {
					char[] tipoChar = new char[1];
					rs.getCharacterStream("tipo").read(tipoChar);
					TipoPersona tp = TipoPersona.getTipoPersonaPorChar(tipoChar[0]);
					if(tp.equals(TipoPersona.F)) {
						persona = obtenerPersFisicaPorId(conn, idPersona);
					} else if(tp.equals(TipoPersona.J)) {
						persona = obtenerPersJuridicaPorId(conn, idPersona);
					} else {
						throw new PersistenciaException("Tipo de Persona no soportado...");
					}
				}
			}
		} catch (ConectorException | SQLException | IOException e) {
			Conector.rollbackConn(conn);
			logger.fatal("Excepcion al obtenerPersGenerico: " + e.getMessage(), e);
			throw new PersistenciaException(e);
		} catch (Exception e) {
			Conector.rollbackConn(conn);
			logger.fatal("Excepcion GENERICA al obtenerPersGenerico: " + e.getMessage(), e);
			throw new PersistenciaException(e);
		}
		return persona;
	}

	@Override
	public Boolean checkExistPersona(Connection conn, Long idPersona) throws PersistenciaException {
		try {
			GenSqlSelectType genSel = new GenSqlSelectType(QRY_CHECK_EXIST_PERS);
			genSel.setParam(idPersona);
			try (ResultSet rs = (ResultSet) runGeneric(conn, genSel)) {
				if(rs.next()) {
					return true;
				}
			}
		} catch (ConectorException | SQLException e) {
			Conector.rollbackConn(conn);
			logger.fatal("Excepcion al checkExistPersona: " + e.getMessage(), e);
			throw new PersistenciaException(e);
		} catch (Exception e) {
			Conector.rollbackConn(conn);
			logger.fatal("Excepcion GENERICA al checkExistPersona: " + e.getMessage(), e);
			throw new PersistenciaException(e);
		}
		return false;
	}

	@Override
	public Integer actualizarSincPersona(Connection conn, Long idPersona, Sinc sinc, Fecha ultAct) throws PersistenciaException {
		try {
			GenSqlExecType genExec = new GenSqlExecType(QRY_UPDATE_SINC_PERS);
			genExec.setParam(sinc.getAsChar());
			genExec.setParam(ultAct);
			genExec.setParam(idPersona);
			resultado = (Integer) runGeneric(conn, genExec);
		} catch (ConectorException e) {
			Conector.rollbackConn(conn);
			logger.fatal("Excepcion al actualizarSincPersona: " + e.getMessage(), e);
			throw new PersistenciaException(e);
		} catch (Exception e) {
			Conector.rollbackConn(conn);
			logger.fatal("Excepcion GENERICA al actualizarSincPersona: " + e.getMessage(), e);
			throw new PersistenciaException(e);
		}
		return resultado;
	}
}
