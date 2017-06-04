package gpd.persistencia.persona;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import gpd.db.constantes.CnstQryPersona;
import gpd.db.generic.GenSqlExecType;
import gpd.db.generic.GenSqlSelectType;
import gpd.dominio.persona.Departamento;
import gpd.dominio.persona.Localidad;
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

public class PersistenciaPersona extends Conector implements IPersPersona  {
	
	private static final Logger logger = Logger.getLogger(PersistenciaPersona.class);
	private Integer resultado;
	
	
	@Override
	public PersonaFisica obtenerPersFisicaPorId(Long id) throws PersistenciaException {
		PersonaFisica pf = null;
		PersistenciaTipoDoc ptd = new PersistenciaTipoDoc();
		try {
			GenSqlSelectType genSel = new GenSqlSelectType(CnstQryPersona.QRY_SELECT_PF_XID);
			genSel.setParam(id);
			ResultSet rs = (ResultSet) runGeneric(genSel);
			if(rs.next()) {
				pf = new PersonaFisica();
				pf.setDocumento(rs.getLong("documento"));
				pf.setTipoDoc(ptd.obtenerTipoDocPorId(rs.getInt("id_tipo_doc")));
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
				pf.setLocalidad(obtenerLocalidadPorId(rs.getInt("id_loc")));
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
		} catch (ConectorException | SQLException | IOException e) {
			Conector.rollbackConn();
			logger.log(Level.FATAL, "Excepcion al obtenerProductoPorId: " + e.getMessage(), e);
			throw new PersistenciaException(e);
		}
		return pf;
	}

	@Override
	public PersonaJuridica obtenerPersJuridicaPorId(Long id) throws PersistenciaException {
		PersonaJuridica pj = null;
		try {
			GenSqlSelectType genSel = new GenSqlSelectType(CnstQryPersona.QRY_SELECT_PJ_XID);
			genSel.setParam(id);
			ResultSet rs = (ResultSet) runGeneric(genSel);
			if(rs.next()) {
				pj = new PersonaJuridica();
				pj.setRut(rs.getLong("rut"));
				pj.setNombre(rs.getString("nombre"));
				pj.setRazonSocial(rs.getString("razon_social"));
				pj.setBps(rs.getString("bps"));
				pj.setBse(rs.getString("bse"));
				char[] esProv = new char[1];
				rs.getCharacterStream("es_prov").read(esProv);
				pj.setEsProv(esProv[0] == 'S' ? true : false);
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
				pj.setLocalidad(obtenerLocalidadPorId(rs.getInt("id_loc")));
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
		} catch (ConectorException | SQLException | IOException e) {
			Conector.rollbackConn();
			logger.log(Level.FATAL, "Excepcion al obtenerProductoPorId: " + e.getMessage(), e);
			throw new PersistenciaException(e);
		}
		return pj;
	}
	
	@Override
	public Integer guardarPersFisica(PersonaFisica pf) throws PersistenciaException {
		try {
			Long documento = pf.getDocumento();
			
			guardarPersona(documento, pf.getDireccion(), pf.getPuerta(), pf.getSolar(), pf.getManzana(),
					pf.getKm(), pf.getComplemento(), pf.getTelefono(), pf.getCelular(), pf.getEmail(),
					pf.getFechaReg(), pf.getTipoPers(), pf.getLocalidad().getIdLocalidad(), pf.getOrigen(),
					pf.getSinc(), pf.getUltAct());
			
			GenSqlExecType genExec = new GenSqlExecType(CnstQryPersona.QRY_INSERT_PF);
			genExec.setParam(documento);
			genExec.setParam(pf.getTipoDoc().getIdTipoDoc());
			genExec.setParam(pf.getApellido1());
			genExec.setParam(pf.getApellido2());
			genExec.setParam(pf.getNombre1());
			genExec.setParam(pf.getNombre2());
			genExec.setParam(pf.getFechaNac());
			genExec.setParam(pf.getSexo().getAsChar());
			resultado = (Integer) runGeneric(genExec);
			
		} catch (ConectorException e) {
			Conector.rollbackConn();
			logger.log(Level.FATAL, "Excepcion al guardarPersFisica: " + e.getMessage(), e);
			throw new PersistenciaException(e);
		}
		return resultado;
	}
	
	@Override
	public Integer modificarPersFisica(PersonaFisica pf) throws PersistenciaException {
		try {
			Long documento = pf.getDocumento();
			
			modificarPersona(documento, pf.getDireccion(), pf.getPuerta(), pf.getSolar(), pf.getManzana(),
					pf.getKm(), pf.getComplemento(), pf.getTelefono(), pf.getCelular(), pf.getEmail(),
					pf.getFechaReg(), pf.getLocalidad().getIdLocalidad(), pf.getOrigen(),
					pf.getSinc(), pf.getUltAct());
			
			GenSqlExecType genExec = new GenSqlExecType(CnstQryPersona.QRY_UPDATE_PF);
			genExec.setParam(pf.getTipoDoc().getIdTipoDoc());
			genExec.setParam(pf.getApellido1());
			genExec.setParam(pf.getApellido2());
			genExec.setParam(pf.getNombre1());
			genExec.setParam(pf.getNombre2());
			genExec.setParam(pf.getFechaNac());
			genExec.setParam(pf.getSexo().getAsChar());
			genExec.setParam(documento);
			resultado = (Integer) runGeneric(genExec);
			
		} catch (ConectorException e) {
			Conector.rollbackConn();
			logger.log(Level.FATAL, "Excepcion al guardarPersFisica: " + e.getMessage(), e);
			throw new PersistenciaException(e);
		}
		return resultado;
	}

	@Override
	public Integer eliminarPersFisica(PersonaFisica pf) throws PersistenciaException {
		try {
			Long documento = pf.getDocumento();
			eliminarPersona(documento);
			
			GenSqlExecType genExec = new GenSqlExecType(CnstQryPersona.QRY_DELETE_PF);
			genExec.getExecuteDatosCond().put(1, documento);
			resultado = (Integer) runGeneric(genExec);
		} catch (ConectorException e) {
			Conector.rollbackConn();
			logger.log(Level.FATAL, "Excepcion al eliminarPersFisica: " + e.getMessage(), e);
			throw new PersistenciaException(e);
		}
		return resultado;
	}

	@Override
	public Integer guardarPersJuridica(PersonaJuridica pj) throws PersistenciaException {
		try {
			guardarPersona(pj.getRut(), pj.getDireccion(), pj.getPuerta(), pj.getSolar(), pj.getManzana(),
					pj.getKm(), pj.getComplemento(), pj.getTelefono(), pj.getCelular(), pj.getEmail(),
					pj.getFechaReg(), pj.getTipoPers(), pj.getLocalidad().getIdLocalidad(), pj.getOrigen(),
					pj.getSinc(), pj.getUltAct());
			
			GenSqlExecType genExec = new GenSqlExecType(CnstQryPersona.QRY_INSERT_PJ);
			genExec.setParam(pj.getRut());
			genExec.setParam(pj.getNombre());
			genExec.setParam(pj.getRazonSocial());
			genExec.setParam(pj.getBps());
			genExec.setParam(pj.getBse());
			genExec.setParam(pj.getEsProv() ? 'S' : 'N');
			resultado = (Integer) runGeneric(genExec);
			
		} catch (ConectorException e) {
			Conector.rollbackConn();
			logger.log(Level.FATAL, "Excepcion al guardarPersJuridica: " + e.getMessage(), e);
			throw new PersistenciaException(e);
		}
		return resultado;
	}
	
	@Override
	public Integer modificarPersJuridica(PersonaJuridica pj) throws PersistenciaException {
		try {
			Long rut = pj.getRut();
			
			modificarPersona(rut, pj.getDireccion(), pj.getPuerta(), pj.getSolar(), pj.getManzana(),
					pj.getKm(), pj.getComplemento(), pj.getTelefono(), pj.getCelular(), pj.getEmail(),
					pj.getFechaReg(), pj.getLocalidad().getIdLocalidad(), pj.getOrigen(),
					pj.getSinc(), pj.getUltAct());
			
			GenSqlExecType genExec = new GenSqlExecType(CnstQryPersona.QRY_UPDATE_PJ);
			genExec.setParam(pj.getNombre());
			genExec.setParam(pj.getRazonSocial());
			genExec.setParam(pj.getBps());
			genExec.setParam(pj.getBse());
			genExec.setParam(pj.getEsProv() ? 'S' : 'N');
			genExec.setParam(rut);
			resultado = (Integer) runGeneric(genExec);
			
		} catch (ConectorException e) {
			Conector.rollbackConn();
			logger.log(Level.FATAL, "Excepcion al modificarPersJuridica: " + e.getMessage(), e);
			throw new PersistenciaException(e);
		}
		return resultado;
	}

	@Override
	public Integer eliminarPersJuridica(PersonaJuridica pj) throws PersistenciaException {
		try {
			Long rut = pj.getRut();
			eliminarPersona(rut);
			
			GenSqlExecType genExec = new GenSqlExecType(CnstQryPersona.QRY_DELETE_PJ);
			genExec.setParam(rut);
			resultado = (Integer) runGeneric(genExec);
		} catch (ConectorException e) {
			Conector.rollbackConn();
			logger.log(Level.FATAL, "Excepcion al eliminarPersJuridica: " + e.getMessage(), e);
			throw new PersistenciaException(e);
		}
		return resultado;
	}
	
	private Integer guardarPersona(Long idPersona, String direccion, String puerta, String solar, String manzana,
			Float km, String complemento, String telefono, String celular, String email, Fecha fechaReg, TipoPersona tipoPers, 
			Integer idLoc, Origen origen, Sinc sinc, Fecha ultAct) throws ConectorException {
		try {
			GenSqlExecType genExec = new GenSqlExecType(CnstQryPersona.QRY_INSERT_PERS);
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
			resultado = (Integer) runGeneric(genExec);
		} catch (ConectorException e) {
			Conector.rollbackConn();
			logger.log(Level.FATAL, "Excepcion al guardarPersona: " + e.getMessage(), e);
			throw e;
		}
		return resultado;
	}
	
	private Integer modificarPersona(Long idPersona, String direccion, String puerta, String solar, String manzana,
			Float km, String complemento, String telefono, String celular, String email, Fecha fechaReg, Integer idLoc,
			Origen origen, Sinc sinc, Fecha ultAct) 
					throws ConectorException {
		try {
			GenSqlExecType genExec = new GenSqlExecType(CnstQryPersona.QRY_UPDATE_PERS);
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
			genExec.setParam(origen);
			genExec.setParam(sinc);
			genExec.setParam(ultAct);
			genExec.setParam(idPersona);
			resultado = (Integer) runGeneric(genExec);
		} catch (ConectorException e) {
			Conector.rollbackConn();
			logger.log(Level.FATAL, "Excepcion al modificarPersona: " + e.getMessage(), e);
			throw e;
		}
		return resultado;
	}
	
	private Integer eliminarPersona(Long idPersona) throws ConectorException {
		try {
			GenSqlExecType genExec = new GenSqlExecType(CnstQryPersona.QRY_DELETE_PERS);
			genExec.getExecuteDatosCond().put(1, idPersona);
			resultado = (Integer) runGeneric(genExec);
		} catch (ConectorException e) {
			Conector.rollbackConn();
			logger.log(Level.FATAL, "Excepcion al eliminarPersona: " + e.getMessage(), e);
			throw e;
		}
		return resultado;
	}

	@Override
	public List<PersonaFisica> obtenerBusquedaPersFisica(Long documento, String ape1, String ape2, String nom1, String nom2, 
			Character sexo, String direccion, String telefono, String celular, String email, Integer idLoc) throws PersistenciaException {
		List<PersonaFisica> listaPf = new ArrayList<>();
		PersistenciaTipoDoc ptd = new PersistenciaTipoDoc();
		try {
			GenSqlSelectType genType = new GenSqlSelectType(CnstQryPersona.QRY_SEARCH_PF);
			genType.setParamEmptyAsNumber(documento);
			genType.setParamEmptyAsNumber(documento);
			genType.setParamLikeBoth(ape1);
			genType.setParam(ape1);
			genType.setParamLikeBoth(ape2);
			genType.setParam(ape2);
			genType.setParamLikeBoth(nom1);
			genType.setParam(nom1);
			genType.setParamLikeBoth(nom2);
			genType.setParam(nom2);
			genType.setParam(sexo);
			genType.setParam(sexo);
			genType.setParamLikeBoth(direccion);
			genType.setParam(direccion);
			genType.setParamLikeBoth(telefono);
			genType.setParam(telefono);
			genType.setParamLikeBoth(celular);
			genType.setParam(celular);
			genType.setParamLikeBoth(email);
			genType.setParam(email);
			genType.setParamEmptyAsNumber(idLoc);
			genType.setParamEmptyAsNumber(idLoc);
			ResultSet rs = (ResultSet) runGeneric(genType);
			while(rs.next()) {
				PersonaFisica pf = new PersonaFisica();
				pf.setDocumento(rs.getLong("documento"));
				pf.setTipoDoc(ptd.obtenerTipoDocPorId(rs.getInt("id_tipo_doc")));
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
				pf.setLocalidad(obtenerLocalidadPorId(rs.getInt("id_loc")));
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
		} catch (ConectorException | SQLException | IOException e) {
			Conector.rollbackConn();
			logger.log(Level.FATAL, "Excepcion al obtenerListaTipoDoc: " + e.getMessage(), e);
			throw new PersistenciaException(e.getMessage());
		}
		return listaPf;
	}

	@Override
	public List<PersonaJuridica> obtenerBusquedaPersJuridica(Long rut, String nombre, String razonSoc, String bps, String bse, 
			Boolean esProv, String direccion, String telefono, String celular, String email, Integer idLoc) throws PersistenciaException {
		List<PersonaJuridica> listaPj = new ArrayList<>();
		try {
			GenSqlSelectType genType = new GenSqlSelectType(CnstQryPersona.QRY_SEARCH_PJ);
			genType.setParamEmptyAsNumber(rut);
			genType.setParamEmptyAsNumber(rut);
			genType.setParamLikeBoth(nombre);
			genType.setParam(nombre);
			genType.setParamLikeBoth(razonSoc);
			genType.setParam(razonSoc);
			genType.setParamLikeBoth(bps);
			genType.setParam(bps);
			genType.setParamLikeBoth(bse);
			genType.setParam(bse);
			genType.setParam(esProv ? 'S' : 'N');
			genType.setParam(esProv ? 'S' : 'N');
			genType.setParamLikeBoth(direccion);
			genType.setParam(direccion);
			genType.setParamLikeBoth(telefono);
			genType.setParam(telefono);
			genType.setParamLikeBoth(celular);
			genType.setParam(celular);
			genType.setParamLikeBoth(email);
			genType.setParam(email);
			genType.setParamEmptyAsNumber(idLoc);
			genType.setParamEmptyAsNumber(idLoc);
			ResultSet rs = (ResultSet) runGeneric(genType);
			while(rs.next()) {
				PersonaJuridica pj = new PersonaJuridica();
				pj.setRut(rs.getLong("rut"));
				pj.setNombre(rs.getString("nombre"));
				pj.setRazonSocial(rs.getString("razon_social"));
				pj.setBps(rs.getString("bps"));
				pj.setBse(rs.getString("bse"));
				char[] esProvChar = new char[1];
				rs.getCharacterStream("es_prov").read(esProvChar);
				pj.setEsProv(esProvChar.equals('S') ? true : false);
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
				pj.setLocalidad(obtenerLocalidadPorId(rs.getInt("id_loc")));
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
		} catch (ConectorException | SQLException | IOException e) {
			Conector.rollbackConn();
			logger.log(Level.FATAL, "Excepcion al obtenerListaTipoDoc: " + e.getMessage(), e);
			throw new PersistenciaException(e.getMessage());
		}
		return listaPj;
	}

	@Override
	public List<Departamento> obtenerListaDepartamentos() throws PersistenciaException {
		List<Departamento> listaDep = new ArrayList<>();
		try {
			GenSqlSelectType genType = new GenSqlSelectType(CnstQryPersona.QRY_SELECT_DEP);
			ResultSet rs = (ResultSet) runGeneric(genType);
			while(rs.next()) {
				Departamento departamento = new Departamento();
				departamento.setIdDepartamento(rs.getInt("id_dep"));
				departamento.setNombreDepartamento(rs.getString("nombre"));
				listaDep.add(departamento);
			}
		} catch (ConectorException | SQLException e) {
			Conector.rollbackConn();
			logger.log(Level.FATAL, "Excepcion al obtenerListaDepartamentos: " + e.getMessage(), e);
			throw new PersistenciaException(e.getMessage());
		}
		return listaDep;
	}
	
	@Override
	public Departamento obtenerDepartamentoPorId(Integer idDep) throws PersistenciaException {
		Departamento departamento = null;
		try {
			GenSqlSelectType genType = new GenSqlSelectType(CnstQryPersona.QRY_SELECT_DEP_XID);
			genType.setParam(idDep);
			ResultSet rs = (ResultSet) runGeneric(genType);
			if(rs.next()) {
				departamento = new Departamento();
				departamento.setIdDepartamento(rs.getInt("id_dep"));
				departamento.setNombreDepartamento(rs.getString("nombre"));
			}
		} catch (ConectorException | SQLException e) {
			Conector.rollbackConn();
			logger.log(Level.FATAL, "Excepcion al obtenerDepartamentoPorId: " + e.getMessage(), e);
			throw new PersistenciaException(e.getMessage());
		}
		return departamento;
	}

	@Override
	public List<Localidad> obtenerListaLocPorDep(Integer idDep) throws PersistenciaException {
		List<Localidad> listaLoc = new ArrayList<>();
		try {
			GenSqlSelectType genType = new GenSqlSelectType(CnstQryPersona.QRY_SELECT_LOC_XDEP);
			genType.setParam(idDep);
			ResultSet rs = (ResultSet) runGeneric(genType);
			while(rs.next()) {
				Localidad localidad = new Localidad();
				localidad.setIdLocalidad(rs.getInt("id_loc"));
				localidad.setNombreLocalidad(rs.getString("nombre"));
				localidad.setDepartamento(obtenerDepartamentoPorId(rs.getInt("id_dep")));
				listaLoc.add(localidad);
			}
		} catch (ConectorException | SQLException e) {
			Conector.rollbackConn();
			logger.log(Level.FATAL, "Excepcion al obtenerListaLocPorDep: " + e.getMessage(), e);
			throw new PersistenciaException(e.getMessage());
		}
		return listaLoc;
	}

	@Override
	public Localidad obtenerLocalidadPorId(Integer idLoc) throws PersistenciaException {
		Localidad localidad = null;
		try {
			GenSqlSelectType genType = new GenSqlSelectType(CnstQryPersona.QRY_SELECT_LOC_XID);
			genType.setParam(idLoc);
			ResultSet rs = (ResultSet) runGeneric(genType);
			if(rs.next()) {
				localidad = new Localidad();
				localidad.setIdLocalidad(rs.getInt("id_loc"));
				localidad.setNombreLocalidad(rs.getString("nombre"));
				localidad.setDepartamento(obtenerDepartamentoPorId(rs.getInt("id_dep")));
			}
		} catch (ConectorException | SQLException e) {
			Conector.rollbackConn();
			logger.log(Level.FATAL, "Excepcion al obtenerLocalidadPorId: " + e.getMessage(), e);
			throw new PersistenciaException(e.getMessage());
		}
		return localidad;
	}


}
