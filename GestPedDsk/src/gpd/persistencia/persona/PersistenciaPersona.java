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
import gpd.dominio.persona.PersonaFisica;
import gpd.dominio.persona.PersonaJuridica;
import gpd.dominio.persona.TipoDoc;
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
	public TipoDoc obtenerTipoDocPorId(Integer id) throws PersistenciaException {
		TipoDoc tipoDoc = null;
		try {
			GenSqlSelectType genSel = new GenSqlSelectType(CnstQryPersona.QRY_SELECT_TIPODOC_XID);
			genSel.setParam(id);
			ResultSet rs = (ResultSet) runGeneric(genSel);
			if(rs.next()) {
				tipoDoc = new TipoDoc();
				tipoDoc.setIdTipoDoc(rs.getInt("id_tipo_doc"));
				tipoDoc.setNombre(rs.getString("nombre"));
			}
		} catch (ConectorException | SQLException e) {
			Conector.rollbackConn();
			logger.log(Level.FATAL, "Excepcion al obtenerListaTipoDoc: " + e.getMessage(), e);
			throw new PersistenciaException(e);
		}
		return tipoDoc;
	}
	
	@Override
	public Integer guardarTipoDoc(TipoDoc tipoDoc) throws PersistenciaException {
		Integer resultado = null;
		GenSqlExecType genExec = new GenSqlExecType(CnstQryPersona.QRY_INSERT_TIPODOC);
		genExec.setParam(tipoDoc.getNombre());
		try {
			resultado = (Integer) runGeneric(genExec);
		} catch (ConectorException e) {
			Conector.rollbackConn();
			logger.log(Level.FATAL, "Excepcion al guardarTipoProd: " + e.getMessage(), e);
			throw new PersistenciaException(e);
		}
		return resultado;
	}

	@Override
	public Integer modificarTipoDoc(TipoDoc tipoDoc) throws PersistenciaException {
		Integer resultado = null;
		GenSqlExecType genExec = new GenSqlExecType(CnstQryPersona.QRY_UPDATE_TIPODOC);
		genExec.setParam(tipoDoc.getNombre());
		genExec.setParam(tipoDoc.getIdTipoDoc());
		try {
			resultado = (Integer) runGeneric(genExec);
		} catch (ConectorException e) {
			Conector.rollbackConn();
			logger.log(Level.FATAL, "Excepcion al modificarTipoProd: " + e.getMessage(), e);
			throw new PersistenciaException(e);
		}
		return resultado;
	}

	@Override
	public Integer eliminarTipoDoc(TipoDoc tipoDoc) throws PersistenciaException {
		Integer resultado = null;
		GenSqlExecType genExec = new GenSqlExecType(CnstQryPersona.QRY_DELETE_TIPODOC);
		genExec.setParam(tipoDoc.getIdTipoDoc());
		try {
			resultado = (Integer) runGeneric(genExec);
		} catch (ConectorException e) {
			Conector.rollbackConn();
			logger.log(Level.FATAL, "Excepcion al eliminarTipoProd: " + e.getMessage(), e);
			throw new PersistenciaException(e);
		}
		return resultado;
	}
	
	@Override
	public PersonaFisica obtenerPersFisicaPorId(Integer id) throws PersistenciaException {
		PersonaFisica persFisica = null;
		try {
			GenSqlSelectType genSel = new GenSqlSelectType(CnstQryPersona.QRY_SELECT_PF_XID);
			genSel.setParam(id);
			ResultSet rs = (ResultSet) runGeneric(genSel);
			if(rs.next()) {
				persFisica = new PersonaFisica();
				
				char[] tipoChar = new char[1];
				rs.getCharacterStream("sinc").read(tipoChar);
				Sinc sinc = Sinc.getSincPorChar(tipoChar[0]);
				persFisica.setSinc(sinc);
				persFisica.setUltAct(new Fecha(rs.getTimestamp("ult_act")));
			}
		} catch (ConectorException | SQLException | IOException e) {
			Conector.rollbackConn();
			logger.log(Level.FATAL, "Excepcion al obtenerProductoPorId: " + e.getMessage(), e);
			throw new PersistenciaException(e);
		}
		return persFisica;
	}

	@Override
	public PersonaJuridica obtenerPersJuridicaPorId(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public Integer guardarPersFisica(PersonaFisica pf) throws PersistenciaException {
		try {
			Long documento = pf.getDocumento();
			
			guardarPersona(documento, pf.getDireccion(), pf.getPuerta(), pf.getSolar(), pf.getManzana(),
					pf.getKm(), pf.getComplemento(), pf.getTelefono(), pf.getCelular(), pf.getEmail(),
					pf.getFechaReg(), pf.getTipoPers(), pf.getLocalidad().getIdLocalidad());
			
			GenSqlExecType genExec = new GenSqlExecType(CnstQryPersona.QRY_INSERT_PF);
			genExec.setParam(documento);
			genExec.setParam(pf.getTipoDoc().getIdTipoDoc());
			genExec.setParam(pf.getApellido1());
			genExec.setParam(pf.getApellido2());
			genExec.setParam(pf.getNombre1());
			genExec.setParam(pf.getNombre2());
			genExec.setParam(pf.getFechaNac());
			genExec.setParam(pf.getSexo().getAsChar());
			genExec.setParam(Origen.D.getAsChar());
			genExec.setParam(Sinc.N.getAsChar());
			genExec.setParam(pf.getUltAct());
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
					pf.getFechaReg(), pf.getLocalidad().getIdLocalidad());
			
			GenSqlExecType genExec = new GenSqlExecType(CnstQryPersona.QRY_UPDATE_PF);
			genExec.setParam(pf.getTipoDoc().getIdTipoDoc());
			genExec.setParam(pf.getApellido1());
			genExec.setParam(pf.getApellido2());
			genExec.setParam(pf.getNombre1());
			genExec.setParam(pf.getNombre2());
			genExec.setParam(pf.getFechaNac());
			genExec.setParam(pf.getSexo().getAsChar());
			genExec.setParam(Origen.D.getAsChar());
			genExec.setParam(Sinc.N.getAsChar());
			genExec.setParam(pf.getUltAct());
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
	public Integer guardarPersJuridica(PersonaJuridica personaJuridica) throws PersistenciaException {
		try {
			guardarPersona(personaJuridica.getRut(), personaJuridica.getDireccion(), personaJuridica.getPuerta(), personaJuridica.getSolar(), personaJuridica.getManzana(),
					personaJuridica.getKm(), personaJuridica.getComplemento(), personaJuridica.getTelefono(), personaJuridica.getCelular(), personaJuridica.getEmail(),
					personaJuridica.getFechaReg(), personaJuridica.getTipoPers(), personaJuridica.getLocalidad().getIdLocalidad());
			
			GenSqlExecType genExec = new GenSqlExecType(CnstQryPersona.QRY_INSERT_PJ);
			genExec.setParam(personaJuridica.getRut());
			genExec.setParam(personaJuridica.getNombre());
			genExec.setParam(personaJuridica.getRazonSocial());
			genExec.setParam(personaJuridica.getBps());
			genExec.setParam(personaJuridica.getBse());
			genExec.setParam(personaJuridica.getEsProv() ? 'S' : 'N');
			resultado = (Integer) runGeneric(genExec);
			
		} catch (ConectorException e) {
			Conector.rollbackConn();
			logger.log(Level.FATAL, "Excepcion al guardarPersJuridica: " + e.getMessage(), e);
			throw new PersistenciaException(e);
		}
		return resultado;
	}
	
	@Override
	public Integer modificarPersJuridica(PersonaJuridica pj) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Integer eliminarPersJuridica(PersonaJuridica pj) {
		// TODO Auto-generated method stub
		return null;
	}
	
	private Integer guardarPersona(Long idPersona, String direccion, String puerta, String solar, String manzana,
			Float km, String complemento, String telefono, String celular, String email, Fecha fechaReg, TipoPersona tipoPers, 
			Integer idLoc) throws ConectorException {
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
			resultado = (Integer) runGeneric(genExec);
		} catch (ConectorException e) {
			Conector.rollbackConn();
			logger.log(Level.FATAL, "Excepcion al guardarPersona: " + e.getMessage(), e);
			throw e;
		}
		return resultado;
	}
	
	private Integer modificarPersona(Long idPersona, String direccion, String puerta, String solar, String manzana,
			Float km, String complemento, String telefono, String celular, String email, Fecha fechaReg, Integer idLoc) 
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
	public List<TipoDoc> obtenerListaTipoDoc() throws PersistenciaException {
		List<TipoDoc> listaTipoDoc = new ArrayList<>();
		try {
			GenSqlSelectType genType = new GenSqlSelectType(CnstQryPersona.QRY_SELECT_TIPODOC);
			ResultSet rs = (ResultSet) runGeneric(genType);
			while(rs.next()) {
				TipoDoc tipoDoc = new TipoDoc();
				tipoDoc.setIdTipoDoc(rs.getInt("id_tipo_doc"));
				tipoDoc.setNombre(rs.getString("nombre"));
				listaTipoDoc.add(tipoDoc);
			}
		} catch (ConectorException | SQLException e) {
			Conector.rollbackConn();
			logger.log(Level.FATAL, "Excepcion al obtenerListaTipoDoc: " + e.getMessage(), e);
			throw new PersistenciaException(e.getMessage());
		}
		return listaTipoDoc;
	}
	
	

	@Override
	public List<PersonaFisica> obtenerBusquedaPersFisica(String x) {
		// TODO Auto-generated method stub
		return null;
	}

	

	@Override
	public List<PersonaJuridica> obtenerBusquedaPersJuridica(String x) {
		// TODO Auto-generated method stub
		return null;
	}

	


}
