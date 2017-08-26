package gpd.persistencia.transaccion;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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
	
	
	@Override
	public TranLinea obtenerTranLineaPorId(Connection conn, Integer nroTransac, Integer idProducto) throws PersistenciaException {
		TranLinea tranLinea = null;
		PersistenciaProducto pp = new PersistenciaProducto();
		PersistenciaTransaccion pt = new PersistenciaTransaccion();
		try {
			GenSqlSelectType genSel = new GenSqlSelectType(QRY_SELECT_TRANLINEA_XID);
			genSel.setParam(nroTransac);
			genSel.setParam(idProducto);
			try (ResultSet rs = (ResultSet) runGeneric(conn, genSel)) {
				if(rs.next()) {
					Transaccion transac = pt.obtenerTransaccionPorId(conn, nroTransac);
					tranLinea = new TranLinea(transac);
					tranLinea.setProducto(pp.obtenerProductoPorId(conn, rs.getInt("id_producto")));
					tranLinea.setCantidad(rs.getInt("cantidad"));
					tranLinea.setIva(rs.getDouble("iva"));
					tranLinea.setPrecioUnit(rs.getDouble("precio_unit"));
				}
			}
		} catch (ConectorException | SQLException e) {
			Conector.rollbackConn(conn);
			logger.fatal("Excepcion al obtenerTranLineaPorId: " + e.getMessage(), e);
			throw new PersistenciaException(e);
		} catch (Exception e) {
			Conector.rollbackConn(conn);
			logger.fatal("Excepcion GENERICA al obtenerTranLineaPorId: " + e.getMessage(), e);
			throw new PersistenciaException(e);
		}
		return tranLinea;
	}
	
	@Override
	public List<TranLinea> obtenerListaTranLinea(Connection conn, Transaccion transac) throws PersistenciaException {
		List<TranLinea> listaTranLinea = new ArrayList<>();
		PersistenciaProducto pp = new PersistenciaProducto();
		try {
			GenSqlSelectType genSel = new GenSqlSelectType(QRY_SELECT_TRANLINEA_XTRANSAC);
			genSel.setParam(transac.getNroTransac());
			try (ResultSet rs = (ResultSet) runGeneric(conn, genSel)) {
				while(rs.next()) {
					TranLinea tranLinea = new TranLinea(transac);
					tranLinea.setProducto(pp.obtenerProductoPorId(conn, rs.getInt("id_producto")));
					tranLinea.setCantidad(rs.getInt("cantidad"));
					tranLinea.setIva(rs.getDouble("iva"));
					tranLinea.setPrecioUnit(rs.getDouble("precio_unit"));
					listaTranLinea.add(tranLinea);
				}
			}
		} catch (ConectorException | SQLException e) {
			Conector.rollbackConn(conn);
			logger.fatal("Excepcion al obtenerListaTranLinea: " + e.getMessage(), e);
			throw new PersistenciaException(e);
		} catch (Exception e) {
			Conector.rollbackConn(conn);
			logger.fatal("Excepcion GENERICA al obtenerListaTranLinea: " + e.getMessage(), e);
			throw new PersistenciaException(e);
		}
		return listaTranLinea;
	}

	@Override
	public Integer guardarListaTranLinea(Connection conn, List<TranLinea> tranLinea) throws PersistenciaException {
		Integer resultado = null;
		try {
			GenSqlExecType genExec = new GenSqlExecType(QRY_INSERT_TRANLINEA);
			ArrayList<Object> paramList = null;
			for(TranLinea tl : tranLinea) {
				paramList = new ArrayList<>();
				paramList.add(tl.getTransaccion().getNroTransac());
				paramList.add(tl.getProducto().getIdProducto());
				paramList.add(tl.getCantidad());
				paramList.add(tl.getIva());
				paramList.add(tl.getPrecioUnit());
				genExec.setParamList(paramList);
			}
			resultado = (Integer) runGeneric(conn, genExec);
		} catch (ConectorException e) {
			Conector.rollbackConn(conn);
			logger.error("Excepcion al guardarListaTranLinea: " + e.getMessage(), e);
			throw new PersistenciaException(e);
		} catch (Exception e) {
			Conector.rollbackConn(conn);
			logger.fatal("Excepcion GENERICA al guardarListaTranLinea: " + e.getMessage(), e);
			throw new PersistenciaException(e);
		}
		return resultado;
	}
	
	@Override
	public Integer eliminarTranLinea(Connection conn, Integer nroTransac) throws PersistenciaException {
		Integer resultado = null;
		try {
			GenSqlExecType genExec = new GenSqlExecType(QRY_DELETE_TRANLINEA);
			genExec.setParam(nroTransac);
			resultado = (Integer) runGeneric(conn, genExec);
		} catch (ConectorException e) {
			Conector.rollbackConn(conn);
			logger.fatal("Excepcion al eliminarTranLinea: " + e.getMessage(), e);
			throw new PersistenciaException(e);
		} catch (Exception e) {
			Conector.rollbackConn(conn);
			logger.fatal("Excepcion GENERICA al eliminarTranLinea: " + e.getMessage(), e);
			throw new PersistenciaException(e);
		}
		return resultado;
	}

}
