package gpd.persistencia.transaccion;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import gpd.db.constantes.CnstQryTranLinea;
import gpd.db.generic.GenSqlExecType;
import gpd.db.generic.GenSqlSelectType;
import gpd.dominio.transaccion.TranLinea;
import gpd.dominio.transaccion.Transaccion;
import gpd.exceptions.ConectorException;
import gpd.exceptions.PersistenciaException;
import gpd.interfaces.transaccion.IPersTranLinea;
import gpd.persistencia.conector.Conector;
import gpd.persistencia.producto.PersistenciaProducto;

public class PersistenciaTranLinea extends Conector implements IPersTranLinea, CnstQryTranLinea {

	private static final Logger logger = Logger.getLogger(PersistenciaTranLinea.class);
	private ResultSet rs;
	
	
	@Override
	public TranLinea obtenerTranLineaPorId(Long nroTransac, Integer idProducto) throws PersistenciaException {
		TranLinea tranLinea = null;
		PersistenciaProducto pp = new PersistenciaProducto();
		PersistenciaTransaccion pt = new PersistenciaTransaccion();
		try {
			GenSqlSelectType genType = new GenSqlSelectType(QRY_SELECT_TRANLINEA_XID);
			genType.setParam(nroTransac);
			genType.setParam(idProducto);
			rs = (ResultSet) runGeneric(genType);
			if(rs.next()) {
				Transaccion transac = pt.obtenerTransaccionPorId(nroTransac);
				tranLinea = new TranLinea(transac);
				tranLinea.setProducto(pp.obtenerProductoPorId(rs.getInt("id_producto")));
				tranLinea.setCantidad(rs.getInt("cantidad"));
				tranLinea.setPrecioUnit(rs.getDouble("precio_unit"));
			}
		} catch (ConectorException | SQLException e) {
			Conector.rollbackConn();
			logger.log(Level.FATAL, "Excepcion al obtenerListaTranLinea: " + e.getMessage(), e);
			throw new PersistenciaException(e);
		} finally {
			closeRs(rs);
		}
		return tranLinea;
	}
	
	@Override
	public List<TranLinea> obtenerListaTranLinea(Transaccion transac) throws PersistenciaException {
		List<TranLinea> listaTranLinea = new ArrayList<>();
		PersistenciaProducto pp = new PersistenciaProducto();
		try {
			GenSqlSelectType genType = new GenSqlSelectType(QRY_SELECT_TRANLINEA_XTRANSAC);
			genType.setParam(transac.getNroTransac());
			rs = (ResultSet) runGeneric(genType);
			while(rs.next()) {
				TranLinea tranLinea = new TranLinea(transac);
				tranLinea.setProducto(pp.obtenerProductoPorId(rs.getInt("id_producto")));
				tranLinea.setCantidad(rs.getInt("cantidad"));
				tranLinea.setPrecioUnit(rs.getDouble("precio_unit"));
				listaTranLinea.add(tranLinea);
			}
		} catch (ConectorException | SQLException e) {
			Conector.rollbackConn();
			logger.log(Level.FATAL, "Excepcion al obtenerListaTranLinea: " + e.getMessage(), e);
			throw new PersistenciaException(e);
		} finally {
			closeRs(rs);
		}
		return listaTranLinea;
	}

	@Override
	public Integer guardarListaTranLinea(List<TranLinea> tranLinea) throws PersistenciaException {
		Integer resultado = null;
		GenSqlExecType genExec = new GenSqlExecType(QRY_INSERT_TRANLINEA);
		ArrayList<Object> paramList = null;
		for(TranLinea tl : tranLinea) {
			paramList = new ArrayList<>();
			paramList.add(tl.getTransaccion().getNroTransac());
			paramList.add(tl.getProducto().getIdProducto());
			paramList.add(tl.getCantidad());
			paramList.add(tl.getPrecioUnit());
			genExec.setParamList(paramList);
		}
		try {
			resultado = (Integer) runGeneric(genExec);
		} catch (ConectorException e) {
			Conector.rollbackConn();
			logger.error("Excepcion al guardarTranLinea: " + e.getMessage(), e);
			throw new PersistenciaException(e);
		}
		return resultado;
	}
	
	@Override
	public Integer eliminarTranLinea(Long nroTransac) throws PersistenciaException {
		Integer resultado = null;
		GenSqlExecType genExec = new GenSqlExecType(QRY_DELETE_TRANLINEA);
		genExec.setParam(nroTransac);
		try {
			resultado = (Integer) runGeneric(genExec);
		} catch (ConectorException e) {
			Conector.rollbackConn();
			logger.log(Level.FATAL, "Excepcion al eliminarTranLinea: " + e.getMessage(), e);
			throw new PersistenciaException(e);
		}
		return resultado;
	}

}
