package gpd.persistencia.producto;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import gpd.db.constantes.CnstQryUnidad;
import gpd.db.generic.GenSqlExecType;
import gpd.db.generic.GenSqlSelectType;
import gpd.dominio.producto.Unidad;
import gpd.dominio.util.Estado;
import gpd.dominio.util.Sinc;
import gpd.exceptions.ConectorException;
import gpd.exceptions.PersistenciaException;
import gpd.interfaces.producto.IPersUnidad;
import gpd.persistencia.conector.Conector;
import gpd.persistencia.persona.PersistenciaTipoDoc;

public class PersistenciaUnidad extends Conector implements IPersUnidad, CnstQryUnidad {

	private static final Logger logger = Logger.getLogger(PersistenciaTipoDoc.class);
	
	
	@Override
	public Unidad obtenerUnidadPorId(Connection conn, Integer id) throws PersistenciaException {
		Unidad unidad = null;
		try {
			GenSqlSelectType genSel = new GenSqlSelectType(QRY_SELECT_UNI_X_ID);
			genSel.setParam(id);
			try (ResultSet rs = (ResultSet) runGeneric(conn, genSel)) {
				if(rs.next()) {
					unidad = new Unidad();
					unidad.setIdUnidad(rs.getInt("id_unidad"));
					unidad.setNombre(rs.getString("nombre"));
					char[] sincChar = new char[1];
					rs.getCharacterStream("sinc").read(sincChar);
					Sinc sinc = Sinc.getSincPorChar(sincChar[0]);
					unidad.setSinc(sinc);
					unidad.setEstado(Estado.getEstadoProdPorInt(rs.getInt("activo")));
				}
			}
		} catch (ConectorException | SQLException | IOException e) {
			Conector.rollbackConn(conn);
			logger.fatal("Excepcion al obtenerUnidadPorId: " + e.getMessage(), e);
			throw new PersistenciaException(e);
		} catch (Exception e) {
			Conector.rollbackConn(conn);
			logger.fatal("Excepcion GENERICA al obtenerUnidadPorId: " + e.getMessage(), e);
			throw new PersistenciaException(e);
		}
		return unidad;
	}
	
	@Override
	public List<Unidad> obtenerListaUnidad(Connection conn) throws PersistenciaException {
		List<Unidad> listaUnidad = new ArrayList<>();
		try {
			GenSqlSelectType genSel = new GenSqlSelectType(QRY_SELECT_UNI);
			try (ResultSet rs = (ResultSet) runGeneric(conn, genSel)) {
				while(rs.next()) {
					Unidad unidad = new Unidad();
					unidad.setIdUnidad(rs.getInt("id_unidad"));
					unidad.setNombre(rs.getString("nombre"));
					char[] sincChar = new char[1];
					rs.getCharacterStream("sinc").read(sincChar);
					Sinc sinc = Sinc.getSincPorChar(sincChar[0]);
					unidad.setSinc(sinc);
					unidad.setEstado(Estado.getEstadoProdPorInt(rs.getInt("activo")));
					listaUnidad.add(unidad);
				}
			}
		} catch (ConectorException | SQLException | IOException e) {
			Conector.rollbackConn(conn);
			logger.fatal("Excepcion al obtenerListaUnidad: " + e.getMessage(), e);
			throw new PersistenciaException(e);
		} catch (Exception e) {
			Conector.rollbackConn(conn);
			logger.fatal("Excepcion GENERICA al obtenerListaUnidad: " + e.getMessage(), e);
			throw new PersistenciaException(e);
		}
		return listaUnidad;
	}

	@Override
	public Integer guardarUnidad(Connection conn, Unidad unidad) throws PersistenciaException {
		Integer resultado = null;
		try {
			GenSqlExecType genExec = new GenSqlExecType(QRY_INSERT_UNI);
			genExec.setParam(unidad.getNombre());
			genExec.setParam(unidad.getSinc().getAsChar());
			genExec.setParam(unidad.getEstado().getAsInt());
			resultado = (Integer) runGeneric(conn, genExec);
		} catch (ConectorException e) {
			Conector.rollbackConn(conn);
			logger.fatal("Excepcion al guardarUnidad: " + e.getMessage(), e);
			throw new PersistenciaException(e);
		} catch (Exception e) {
			Conector.rollbackConn(conn);
			logger.fatal("Excepcion GENERICA al guardarUnidad: " + e.getMessage(), e);
			throw new PersistenciaException(e);
		}
		return resultado;
	}

	@Override
	public Integer modificarUnidad(Connection conn, Unidad unidad) throws PersistenciaException {
		Integer resultado = null;
		try {
			GenSqlExecType genExec = new GenSqlExecType(QRY_UPDATE_UNI);
			genExec.setParam(unidad.getNombre());
			genExec.setParam(unidad.getSinc().getAsChar());
			genExec.setParam(unidad.getIdUnidad());
			resultado = (Integer) runGeneric(conn, genExec);
		} catch (ConectorException e) {
			Conector.rollbackConn(conn);
			logger.fatal("Excepcion al modificarUnidad: " + e.getMessage(), e);
			throw new PersistenciaException(e);
		} catch (Exception e) {
			Conector.rollbackConn(conn);
			logger.fatal("Excepcion GENERICA al modificarUnidad: " + e.getMessage(), e);
			throw new PersistenciaException(e);
		}
		return resultado;
	}

	@Override
	public Integer modificarSincUnidad(Connection conn, Integer idUnidad, Sinc sinc) throws PersistenciaException {
		Integer resultado = null;
		try {
			GenSqlExecType genExec = new GenSqlExecType(QRY_UPDATE_SINC_UNI);
			genExec.setParam(sinc.getAsChar());
			genExec.setParam(idUnidad);
			resultado = (Integer) runGeneric(conn, genExec);
		} catch (ConectorException e) {
			Conector.rollbackConn(conn);
			logger.fatal("Excepcion al modificarUnidad: " + e.getMessage(), e);
			throw new PersistenciaException(e);
		} catch (Exception e) {
			Conector.rollbackConn(conn);
			logger.fatal("Excepcion GENERICA al modificarSincUnidad: " + e.getMessage(), e);
			throw new PersistenciaException(e);
		}
		return resultado;
	}
	
	@Override
	public Integer eliminarUnidad(Connection conn, Unidad unidad) throws PersistenciaException {
		Integer resultado = null;
		try {
			GenSqlExecType genExec = new GenSqlExecType(QRY_DELETE_UNI);
			genExec.setParam(unidad.getEstado().getAsInt());
			genExec.setParam(unidad.getSinc().getAsChar());
			genExec.setParam(unidad.getIdUnidad());
			resultado = (Integer) runGeneric(conn, genExec);
		} catch (ConectorException e) {
			Conector.rollbackConn(conn);
			logger.fatal("Excepcion al eliminarUnidad: " + e.getMessage(), e);
			throw new PersistenciaException(e);
		} catch (Exception e) {
			Conector.rollbackConn(conn);
			logger.fatal("Excepcion GENERICA al eliminarUnidad: " + e.getMessage(), e);
			throw new PersistenciaException(e);
		}
		return resultado;
	}

	@Override
	public Boolean controlUtilUnidad(Connection conn, Unidad unidad) throws PersistenciaException {
		try {
			GenSqlSelectType genSel = new GenSqlSelectType(QRY_CTRL_UTIL_UNI);
			genSel.setParam(unidad.getIdUnidad());
			try (ResultSet rs = (ResultSet) runGeneric(conn, genSel)) {
				if(rs.next()) {
					return true;
				}
			}
		} catch (ConectorException | SQLException e) {
			Conector.rollbackConn(conn);
			logger.fatal("Excepcion al controlUtilUnidad: " + e.getMessage(), e);
			throw new PersistenciaException(e);
		} catch (Exception e) {
			Conector.rollbackConn(conn);
			logger.fatal("Excepcion GENERICA al controlUtilUnidad: " + e.getMessage(), e);
			throw new PersistenciaException(e);
		}
		return false;
	}

}
