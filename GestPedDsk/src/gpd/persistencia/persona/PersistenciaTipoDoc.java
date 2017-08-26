package gpd.persistencia.persona;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import gpd.db.constantes.CnstQryTipoDoc;
import gpd.db.generic.GenSqlExecType;
import gpd.db.generic.GenSqlSelectType;
import gpd.dominio.persona.TipoDoc;
import gpd.exceptions.ConectorException;
import gpd.exceptions.PersistenciaException;
import gpd.interfaces.persona.IPersTipoDoc;
import gpd.persistencia.conector.Conector;

public class PersistenciaTipoDoc extends Conector implements IPersTipoDoc, CnstQryTipoDoc {

	private static final Logger logger = Logger.getLogger(PersistenciaTipoDoc.class);

	
	@Override
	public TipoDoc obtenerTipoDocPorId(Connection conn, Integer id) throws PersistenciaException {
		TipoDoc tipoDoc = null;
		try {
			GenSqlSelectType genSel = new GenSqlSelectType(QRY_SELECT_TIPODOC_XID);
			genSel.setParam(id);
			try (ResultSet rs = (ResultSet) runGeneric(conn, genSel)) {
				if(rs.next()) {
					tipoDoc = new TipoDoc();
					tipoDoc.setIdTipoDoc(rs.getInt("id_tipo_doc"));
					tipoDoc.setNombre(rs.getString("nombre"));
				}
			}
		} catch (ConectorException | SQLException e) {
			Conector.rollbackConn(conn);
			logger.fatal("Excepcion al obtenerTipoDocPorId: " + e.getMessage(), e);
			throw new PersistenciaException(e);
		} catch (Exception e) {
			Conector.rollbackConn(conn);
			logger.fatal("Excepcion GENERICA al obtenerTipoDocPorId: " + e.getMessage(), e);
			throw new PersistenciaException(e);
		}
		return tipoDoc;
	}
	
	@Override
	public Integer guardarTipoDoc(Connection conn, TipoDoc tipoDoc) throws PersistenciaException {
		Integer resultado = null;
		try {
			GenSqlExecType genExec = new GenSqlExecType(QRY_INSERT_TIPODOC);
			genExec.setParam(tipoDoc.getNombre());
			resultado = (Integer) runGeneric(conn, genExec);
		} catch (ConectorException e) {
			Conector.rollbackConn(conn);
			logger.fatal("Excepcion al guardarTipoProd: " + e.getMessage(), e);
			throw new PersistenciaException(e);
		} catch (Exception e) {
			Conector.rollbackConn(conn);
			logger.fatal("Excepcion GENERICA al guardarTipoDoc: " + e.getMessage(), e);
			throw new PersistenciaException(e);
		}
		return resultado;
	}

	@Override
	public Integer modificarTipoDoc(Connection conn, TipoDoc tipoDoc) throws PersistenciaException {
		Integer resultado = null;
		try {
			GenSqlExecType genExec = new GenSqlExecType(QRY_UPDATE_TIPODOC);
			genExec.setParam(tipoDoc.getNombre());
			genExec.setParam(tipoDoc.getIdTipoDoc());
			resultado = (Integer) runGeneric(conn, genExec);
		} catch (ConectorException e) {
			Conector.rollbackConn(conn);
			logger.fatal("Excepcion al modificarTipoProd: " + e.getMessage(), e);
			throw new PersistenciaException(e);
		} catch (Exception e) {
			Conector.rollbackConn(conn);
			logger.fatal("Excepcion GENERICA al modificarTipoDoc: " + e.getMessage(), e);
			throw new PersistenciaException(e);
		}
		return resultado;
	}

	@Override
	public Integer eliminarTipoDoc(Connection conn, TipoDoc tipoDoc) throws PersistenciaException {
		Integer resultado = null;
		try {
			GenSqlExecType genExec = new GenSqlExecType(QRY_DELETE_TIPODOC);
			genExec.setParam(tipoDoc.getIdTipoDoc());
			resultado = (Integer) runGeneric(conn, genExec);
		} catch (ConectorException e) {
			Conector.rollbackConn(conn);
			logger.fatal("Excepcion al eliminarTipoProd: " + e.getMessage(), e);
			throw new PersistenciaException(e);
		} catch (Exception e) {
			Conector.rollbackConn(conn);
			logger.fatal("Excepcion GENERICA al eliminarTipoDoc: " + e.getMessage(), e);
			throw new PersistenciaException(e);
		}
		return resultado;
	}

	@Override
	public List<TipoDoc> obtenerListaTipoDoc(Connection conn) throws PersistenciaException {
		List<TipoDoc> listaTipoDoc = new ArrayList<>();
		try {
			GenSqlSelectType genSel = new GenSqlSelectType(QRY_SELECT_TIPODOC);
			try (ResultSet rs = (ResultSet) runGeneric(conn, genSel)) {
				while(rs.next()) {
					TipoDoc tipoDoc = new TipoDoc();
					tipoDoc.setIdTipoDoc(rs.getInt("id_tipo_doc"));
					tipoDoc.setNombre(rs.getString("nombre"));
					listaTipoDoc.add(tipoDoc);
				}
			}
		} catch (ConectorException | SQLException e) {
			Conector.rollbackConn(conn);
			logger.fatal("Excepcion al obtenerListaTipoDoc: " + e.getMessage(), e);
			throw new PersistenciaException(e);
		} catch (Exception e) {
			Conector.rollbackConn(conn);
			logger.fatal("Excepcion GENERICA al obtenerListaTipoDoc: " + e.getMessage(), e);
			throw new PersistenciaException(e);
		}
		return listaTipoDoc;
	}
	

}
