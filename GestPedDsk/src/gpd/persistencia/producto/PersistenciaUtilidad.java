package gpd.persistencia.producto;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import gpd.db.constantes.CnstQryUtilidad;
import gpd.db.generic.GenSqlExecType;
import gpd.db.generic.GenSqlSelectType;
import gpd.dominio.producto.Utilidad;
import gpd.exceptions.ConectorException;
import gpd.exceptions.PersistenciaException;
import gpd.interfaces.producto.IPersUtilidad;
import gpd.persistencia.conector.Conector;

public class PersistenciaUtilidad extends Conector implements IPersUtilidad, CnstQryUtilidad {
	
	private static final Logger logger = Logger.getLogger(PersistenciaTipoProd.class);
	private ResultSet rs;
	

	@Override
	public Utilidad obtenerUtilidadPorId(Integer idUtil) throws PersistenciaException {
		Utilidad utilidad = null;
		try {
			GenSqlSelectType genType = new GenSqlSelectType(QRY_SELECT_UTIL_X_ID);
			genType.setParam(idUtil);
			rs = (ResultSet) runGeneric(genType);
			if(rs.next()) {
				utilidad = new Utilidad();
				utilidad.setIdUtil(rs.getInt("id_util"));
				utilidad.setDescripcion(rs.getString("descripcion"));
				utilidad.setPorc(rs.getFloat("porc"));
			}
		} catch (ConectorException | SQLException e) {
			Conector.rollbackConn();
			logger.fatal("Excepcion al obtenerUtilidadPorId: " + e.getMessage(), e);
			throw new PersistenciaException(e);
		} finally {
			closeRs(rs);
		}
		return utilidad;
	}

	@Override
	public List<Utilidad> obtenerListaUtilidad() throws PersistenciaException {
		List<Utilidad> listaUtilidad = new ArrayList<>();
		try {
			GenSqlSelectType genType = new GenSqlSelectType(QRY_SELECT_UTIL);
			rs = (ResultSet) runGeneric(genType);
			while(rs.next()) {
				Utilidad utilidad = new Utilidad();
				utilidad.setIdUtil(rs.getInt("id_util"));
				utilidad.setDescripcion(rs.getString("descripcion"));
				utilidad.setPorc(rs.getFloat("porc"));
				listaUtilidad.add(utilidad);
			}
		} catch (ConectorException | SQLException e) {
			Conector.rollbackConn();
			logger.fatal("Excepcion al obtenerListaUtilidad: " + e.getMessage(), e);
			throw new PersistenciaException(e);
		} finally {
			closeRs(rs);
		}
		return listaUtilidad;
	}

	@Override
	public Integer guardarUtilidad(Utilidad utilidad) throws PersistenciaException {
		Integer resultado = null;
		GenSqlExecType genExec = new GenSqlExecType(QRY_INSERT_UTIL);
		genExec.setParam(utilidad.getDescripcion());
		genExec.setParam(utilidad.getPorc());
		try {
			resultado = (Integer) runGeneric(genExec);
		} catch (ConectorException e) {
			Conector.rollbackConn();
			logger.fatal("Excepcion al guardarUtilidad: " + e.getMessage(), e);
			throw new PersistenciaException(e);
		}
		return resultado;
	}

	@Override
	public Integer modificarUtilidad(Utilidad utilidad) throws PersistenciaException {
		Integer resultado = null;
		GenSqlExecType genExec = new GenSqlExecType(QRY_UPDATE_UTIL);
		genExec.setParam(utilidad.getDescripcion());
		genExec.setParam(utilidad.getPorc());
		genExec.setParam(utilidad.getIdUtil());
		try {
			resultado = (Integer) runGeneric(genExec);
		} catch (ConectorException e) {
			Conector.rollbackConn();
			logger.fatal("Excepcion al modificarUtilidad: " + e.getMessage(), e);
			throw new PersistenciaException(e);
		}
		return resultado;
	}

	@Override
	public Integer eliminarUtilidad(Utilidad utilidad) throws PersistenciaException {
		Integer resultado = null;
		GenSqlExecType genExec = new GenSqlExecType(QRY_DELETE_UTIL);
		genExec.setParam(utilidad.getIdUtil());
		try {
			resultado = (Integer) runGeneric(genExec);
		} catch (ConectorException e) {
			Conector.rollbackConn();
			logger.fatal("Excepcion al eliminarUtilidad: " + e.getMessage(), e);
			throw new PersistenciaException(e);
		}
		return resultado;
	}

}
