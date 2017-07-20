package gpd.persistencia.producto;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import gpd.db.constantes.CnstQryUnidad;
import gpd.db.generic.GenSqlExecType;
import gpd.db.generic.GenSqlSelectType;
import gpd.dominio.producto.Unidad;
import gpd.exceptions.ConectorException;
import gpd.exceptions.PersistenciaException;
import gpd.interfaces.producto.IPersUnidad;
import gpd.persistencia.conector.Conector;
import gpd.persistencia.persona.PersistenciaTipoDoc;

public class PersistenciaUnidad extends Conector implements IPersUnidad, CnstQryUnidad {

	private static final Logger logger = Logger.getLogger(PersistenciaTipoDoc.class);
	private ResultSet rs;
	
	
	@Override
	public Unidad obtenerUnidadPorId(Integer id) throws PersistenciaException {
		Unidad Unidad = null;
		try {
			GenSqlSelectType genType = new GenSqlSelectType(QRY_SELECT_UNI_X_ID);
			genType.setParam(id);
			rs = (ResultSet) runGeneric(genType);
			if(rs.next()) {
				Unidad = new Unidad();
				Unidad.setIdUnidad(rs.getInt("id_unidad"));
				Unidad.setNombre(rs.getString("nombre"));
			}
		} catch (ConectorException | SQLException e) {
			Conector.rollbackConn();
			logger.fatal("Excepcion al obtenerUnidadPorId: " + e.getMessage(), e);
			throw new PersistenciaException(e);
		} finally {
			closeRs(rs);
		}
		return Unidad;
	}
	
	@Override
	public List<Unidad> obtenerListaUnidad() throws PersistenciaException {
		List<Unidad> listaUnidad = new ArrayList<>();
		try {
			GenSqlSelectType genType = new GenSqlSelectType(QRY_SELECT_UNI);
			rs = (ResultSet) runGeneric(genType);
			while(rs.next()) {
				Unidad Unidad = new Unidad();
				Unidad.setIdUnidad(rs.getInt("id_unidad"));
				Unidad.setNombre(rs.getString("nombre"));
				listaUnidad.add(Unidad);
			}
		} catch (ConectorException | SQLException e) {
			Conector.rollbackConn();
			logger.fatal("Excepcion al obtenerListaUnidad: " + e.getMessage(), e);
			throw new PersistenciaException(e);
		} finally {
			closeRs(rs);
		}
		return listaUnidad;
	}

	@Override
	public Integer guardarUnidad(Unidad Unidad) throws PersistenciaException {
		Integer resultado = null;
		GenSqlExecType genExec = new GenSqlExecType(QRY_INSERT_UNI);
		genExec.setParam(Unidad.getNombre());
		try {
			resultado = (Integer) runGeneric(genExec);
		} catch (ConectorException e) {
			Conector.rollbackConn();
			logger.fatal("Excepcion al guardarUnidad: " + e.getMessage(), e);
			throw new PersistenciaException(e);
		}
		return resultado;
	}

	@Override
	public Integer modificarUnidad(Unidad Unidad) throws PersistenciaException {
		Integer resultado = null;
		GenSqlExecType genExec = new GenSqlExecType(QRY_UPDATE_UNI);
		genExec.setParam(Unidad.getNombre());
		genExec.setParam(Unidad.getIdUnidad());
		try {
			resultado = (Integer) runGeneric(genExec);
		} catch (ConectorException e) {
			Conector.rollbackConn();
			logger.fatal("Excepcion al modificarUnidad: " + e.getMessage(), e);
			throw new PersistenciaException(e);
		}
		return resultado;
	}

	@Override
	public Integer eliminarUnidad(Unidad Unidad) throws PersistenciaException {
		Integer resultado = null;
		GenSqlExecType genExec = new GenSqlExecType(QRY_DELETE_UNI);
		genExec.setParam(Unidad.getIdUnidad());
		try {
			resultado = (Integer) runGeneric(genExec);
		} catch (ConectorException e) {
			Conector.rollbackConn();
			logger.fatal("Excepcion al eliminarUnidad: " + e.getMessage(), e);
			throw new PersistenciaException(e);
		}
		return resultado;
	}

}
