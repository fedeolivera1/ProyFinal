package gpd.persistencia.producto;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import gpd.db.constantes.CnstQryTipoProd;
import gpd.db.generic.GenSqlExecType;
import gpd.db.generic.GenSqlSelectType;
import gpd.dominio.producto.TipoProd;
import gpd.dominio.util.Estado;
import gpd.dominio.util.Sinc;
import gpd.exceptions.ConectorException;
import gpd.exceptions.PersistenciaException;
import gpd.interfaces.producto.IPersTipoProd;
import gpd.persistencia.conector.Conector;

public class PersistenciaTipoProd extends Conector implements IPersTipoProd, CnstQryTipoProd {

	private static final Logger logger = Logger.getLogger(PersistenciaTipoProd.class);
	
	
	@Override
	public TipoProd obtenerTipoProdPorId(Connection conn, Integer id) throws PersistenciaException {
		TipoProd tipoProd = null;
		try {
			GenSqlSelectType genSel = new GenSqlSelectType(QRY_SELECT_TIPOPROD_X_ID);
			genSel.setParam(id);
			try (ResultSet rs = (ResultSet) runGeneric(conn, genSel)) {
				if(rs.next()) {
					tipoProd = new TipoProd();
					tipoProd.setIdTipoProd(rs.getInt("id_tipo_prod"));
					tipoProd.setDescripcion(rs.getString("descripcion"));
					char[] sincChar = new char[1];
					rs.getCharacterStream("sinc").read(sincChar);
					Sinc sinc = Sinc.getSincPorChar(sincChar[0]);
					tipoProd.setSinc(sinc);
					tipoProd.setEstado(Estado.getEstadoProdPorInt(rs.getInt("activo")));
				}
			}
		} catch (ConectorException | SQLException | IOException e) {
			Conector.rollbackConn(conn);
			logger.fatal("Excepcion al obtenerTipoProdPorId: " + e.getMessage(), e);
			throw new PersistenciaException(e);
		} catch (Exception e) {
			Conector.rollbackConn(conn);
			logger.fatal("Excepcion GENERICA al obtenerTipoProdPorId: " + e.getMessage(), e);
			throw new PersistenciaException(e);
		}
		return tipoProd;
	}
	
	@Override
	public List<TipoProd> obtenerListaTipoProd(Connection conn) throws PersistenciaException {
		List<TipoProd> listaTipoProd = new ArrayList<>();
		try {
			GenSqlSelectType genSel = new GenSqlSelectType(QRY_SELECT_TIPOPROD);
			try (ResultSet rs = (ResultSet) runGeneric(conn, genSel)) {
				while(rs.next()) {
					TipoProd tipoProd = new TipoProd();
					tipoProd.setIdTipoProd(rs.getInt("id_tipo_prod"));
					tipoProd.setDescripcion(rs.getString("descripcion"));
					char[] sincChar = new char[1];
					rs.getCharacterStream("sinc").read(sincChar);
					Sinc sinc = Sinc.getSincPorChar(sincChar[0]);
					tipoProd.setSinc(sinc);
					tipoProd.setEstado(Estado.getEstadoProdPorInt(rs.getInt("activo")));
					listaTipoProd.add(tipoProd);
				}
			}
		} catch (ConectorException | SQLException | IOException e) {
			Conector.rollbackConn(conn);
			logger.fatal("Excepcion al obtenerListaTipoProd: " + e.getMessage(), e);
			throw new PersistenciaException(e);
		} catch (Exception e) {
			Conector.rollbackConn(conn);
			logger.fatal("Excepcion GENERICA al obtenerListaTipoProd: " + e.getMessage(), e);
			throw new PersistenciaException(e);
		}
		return listaTipoProd;
	}

	@Override
	public Integer guardarTipoProd(Connection conn, TipoProd tipoProd) throws PersistenciaException {
		Integer resultado = null;
		try {
			GenSqlExecType genExec = new GenSqlExecType(QRY_INSERT_TIPOPROD);
			genExec.setParam(tipoProd.getDescripcion());
			genExec.setParam(tipoProd.getSinc().getAsChar());
			genExec.setParam(tipoProd.getEstado().getAsInt());
			resultado = (Integer) runGeneric(conn, genExec);
		} catch (ConectorException e) {
			Conector.rollbackConn(conn);
			logger.fatal("Excepcion al guardarTipoProd: " + e.getMessage(), e);
			throw new PersistenciaException(e);
		} catch (Exception e) {
			Conector.rollbackConn(conn);
			logger.fatal("Excepcion GENERICA al guardarTipoProd: " + e.getMessage(), e);
			throw new PersistenciaException(e);
		}
		return resultado;
	}

	@Override
	public Integer modificarTipoProd(Connection conn, TipoProd tipoProd) throws PersistenciaException {
		Integer resultado = null;
		try {
			GenSqlExecType genExec = new GenSqlExecType(QRY_UPDATE_TIPOPROD);
			genExec.setParam(tipoProd.getDescripcion());
			genExec.setParam(tipoProd.getSinc().getAsChar());
			genExec.setParam(tipoProd.getIdTipoProd());
			resultado = (Integer) runGeneric(conn, genExec);
		} catch (ConectorException e) {
			Conector.rollbackConn(conn);
			logger.fatal("Excepcion al modificarTipoProd: " + e.getMessage(), e);
			throw new PersistenciaException(e);
		} catch (Exception e) {
			Conector.rollbackConn(conn);
			logger.fatal("Excepcion GENERICA al modificarTipoProd: " + e.getMessage(), e);
			throw new PersistenciaException(e);
		}
		return resultado;
	}
	
	@Override
	public Integer modificarSincTipoProd(Connection conn, Integer idTipoProd, Sinc sinc) throws PersistenciaException {
		Integer resultado = null;
		try {
			GenSqlExecType genExec = new GenSqlExecType(QRY_UPDATE_SINC_TIPOPROD);
			genExec.setParam(sinc.getAsChar());
			genExec.setParam(idTipoProd);
			resultado = (Integer) runGeneric(conn, genExec);
		} catch (ConectorException e) {
			Conector.rollbackConn(conn);
			logger.fatal("Excepcion al modificarSincTipoProd: " + e.getMessage(), e);
			throw new PersistenciaException(e);
		} catch (Exception e) {
			Conector.rollbackConn(conn);
			logger.fatal("Excepcion GENERICA al modificarSincTipoProd: " + e.getMessage(), e);
			throw new PersistenciaException(e);
		}
		return resultado;
	}

	@Override
	public Integer eliminarTipoProd(Connection conn, TipoProd tipoProd) throws PersistenciaException {
		Integer resultado = null;
		try {
			GenSqlExecType genExec = new GenSqlExecType(QRY_DISABLE_TIPOPROD);
			genExec.setParam(tipoProd.getEstado().getAsInt());
			genExec.setParam(tipoProd.getSinc().getAsChar());
			genExec.setParam(tipoProd.getIdTipoProd());
			resultado = (Integer) runGeneric(conn, genExec);
		} catch (ConectorException e) {
			Conector.rollbackConn(conn);
			logger.fatal("Excepcion al eliminarTipoProd: " + e.getMessage(), e);
			throw new PersistenciaException(e);
		} catch (Exception e) {
			Conector.rollbackConn(conn);
			logger.fatal("Excepcion GENERICA al eliminarTipoProd: " + e.getMessage(), e);
			throw new PersistenciaException(e);
		}
		return resultado;
	}
	
	@Override
	public Boolean controlUtilTipoProd(Connection conn, TipoProd tipoProd) throws PersistenciaException {
		try {
			GenSqlSelectType genSel = new GenSqlSelectType(QRY_CTRL_UTIL_TIPOPROD);
			genSel.setParam(tipoProd.getIdTipoProd());
			try (ResultSet rs = (ResultSet) runGeneric(conn, genSel)) {
				if(rs.next()) {
					return true;
				}
			}
		} catch (ConectorException | SQLException e) {
			Conector.rollbackConn(conn);
			logger.fatal("Excepcion al controlUtilTipoProd: " + e.getMessage(), e);
			throw new PersistenciaException(e);
		} catch (Exception e) {
			Conector.rollbackConn(conn);
			logger.fatal("Excepcion GENERICA al controlUtilTipoProd: " + e.getMessage(), e);
			throw new PersistenciaException(e);
		}
		return false;
	}

}
