package gpd.persistencia.producto;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import gpd.db.constantes.CnstQryTipoProd;
import gpd.db.generic.GenSqlExecType;
import gpd.db.generic.GenSqlSelectType;
import gpd.dominio.producto.TipoProd;
import gpd.exceptions.ConectorException;
import gpd.exceptions.PersistenciaException;
import gpd.interfaces.producto.IPersTipoProd;
import gpd.persistencia.conector.Conector;

public class PersistenciaTipoProd extends Conector implements IPersTipoProd, CnstQryTipoProd {

	private static final Logger logger = Logger.getLogger(PersistenciaTipoProd.class);
	private ResultSet rs;
	
	
	@Override
	public TipoProd obtenerTipoProdPorId(Integer id) throws PersistenciaException {
		TipoProd tipoProd = null;
		try {
			GenSqlSelectType genType = new GenSqlSelectType(QRY_SELECT_TIPOPROD_X_ID);
			genType.setParam(id);
			rs = (ResultSet) runGeneric(genType);
			if(rs.next()) {
				tipoProd = new TipoProd();
				tipoProd.setIdTipoProd(rs.getInt("id_tipo_prod"));
				tipoProd.setDescripcion(rs.getString("descripcion"));
			}
		} catch (ConectorException | SQLException e) {
			Conector.rollbackConn();
			logger.log(Level.FATAL, "Excepcion al obtenerTipoProdPorId: " + e.getMessage(), e);
			throw new PersistenciaException(e);
		} finally {
			closeRs(rs);
		}
		return tipoProd;
	}
	
	@Override
	public List<TipoProd> obtenerListaTipoProd() throws PersistenciaException {
		List<TipoProd> listaTipoProd = new ArrayList<>();
		try {
			GenSqlSelectType genType = new GenSqlSelectType(QRY_SELECT_TIPOPROD);
			rs = (ResultSet) runGeneric(genType);
			while(rs.next()) {
				TipoProd tipoProd = new TipoProd();
				tipoProd.setIdTipoProd(rs.getInt("id_tipo_prod"));
				tipoProd.setDescripcion(rs.getString("descripcion"));
				listaTipoProd.add(tipoProd);
			}
		} catch (ConectorException | SQLException e) {
			Conector.rollbackConn();
			logger.log(Level.FATAL, "Excepcion al obtenerListaTipoProd: " + e.getMessage(), e);
			throw new PersistenciaException(e);
		} finally {
			closeRs(rs);
		}
		return listaTipoProd;
	}

	@Override
	public Integer guardarTipoProd(TipoProd tipoProd) throws PersistenciaException {
		Integer resultado = null;
		GenSqlExecType genExec = new GenSqlExecType(QRY_INSERT_TIPOPROD);
		genExec.setParam(tipoProd.getDescripcion());
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
	public Integer modificarTipoProd(TipoProd tipoProd) throws PersistenciaException {
		Integer resultado = null;
		GenSqlExecType genExec = new GenSqlExecType(QRY_UPDATE_TIPOPROD);
		genExec.setParam(tipoProd.getDescripcion());
		genExec.setParam(tipoProd.getIdTipoProd());
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
	public Integer eliminarTipoProd(TipoProd tipoProd) throws PersistenciaException {
		Integer resultado = null;
		GenSqlExecType genExec = new GenSqlExecType(QRY_DELETE_TIPOPROD);
		genExec.setParam(tipoProd.getIdTipoProd());
		try {
			resultado = (Integer) runGeneric(genExec);
		} catch (ConectorException e) {
			Conector.rollbackConn();
			logger.log(Level.FATAL, "Excepcion al eliminarTipoProd: " + e.getMessage(), e);
			throw new PersistenciaException(e);
		}
		return resultado;
	}

}
