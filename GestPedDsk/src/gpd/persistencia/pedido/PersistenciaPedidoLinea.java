package gpd.persistencia.pedido;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import gpd.db.constantes.CnstQryPedidoLinea;
import gpd.db.generic.GenSqlExecType;
import gpd.db.generic.GenSqlSelectType;
import gpd.dominio.pedido.Pedido;
import gpd.dominio.pedido.PedidoLinea;
import gpd.exceptions.ConectorException;
import gpd.exceptions.PersistenciaException;
import gpd.interfaces.pedido.IPersPedidoLinea;
import gpd.persistencia.conector.Conector;
import gpd.persistencia.producto.PersistenciaProducto;

public class PersistenciaPedidoLinea extends Conector implements IPersPedidoLinea, CnstQryPedidoLinea {

	private static final Logger logger = Logger.getLogger(PersistenciaPedidoLinea.class);
	
	
	@Override
	public List<PedidoLinea> obtenerListaPedidoLinea(Connection conn, Pedido pedido) throws PersistenciaException {
		List<PedidoLinea> listaPedidoLinea = new ArrayList<>();	
		PersistenciaProducto pp = new PersistenciaProducto();
		try {
			GenSqlSelectType genSel = new GenSqlSelectType(QRY_SELECT_PL);
			genSel.setParam(pedido.getPersona().getIdPersona());
			genSel.setParam(pedido.getFechaHora());
			try (ResultSet rs = (ResultSet) runGeneric(conn, genSel)) {
				while(rs.next()) {
					PedidoLinea pedidoLinea = new PedidoLinea(pedido);
					pedidoLinea.setProducto(pp.obtenerProductoPorId(conn, rs.getInt("id_producto")));
					pedidoLinea.setCantidad(rs.getInt("cantidad"));
					pedidoLinea.setIva(rs.getDouble("iva"));
					pedidoLinea.setPrecioUnit(rs.getDouble("precio_unit"));
					listaPedidoLinea.add(pedidoLinea);
				}
			}
		} catch (ConectorException | SQLException e) {
			Conector.rollbackConn(conn);
			logger.fatal("Excepcion al obtenerListaPedidoLinea: " + e.getMessage(), e);
			throw new PersistenciaException(e);
		} catch (Exception e) {
			Conector.rollbackConn(conn);
			logger.fatal("Excepcion GENERICA al obtenerListaPedidoLinea: " + e.getMessage(), e);
			throw new PersistenciaException(e);
		}
		return listaPedidoLinea;
	}


	@Override
	public Integer guardarListaPedidoLinea(Connection conn, List<PedidoLinea> listaPedidoLinea) throws PersistenciaException {
		Integer resultado = null;
		try {
			GenSqlExecType genExec = new GenSqlExecType(QRY_INSERT_PL);
			ArrayList<Object> paramList = null;
			for(PedidoLinea pl : listaPedidoLinea) {
				paramList = new ArrayList<>();
				paramList.add(pl.getPedido().getPersona().getIdPersona());
				paramList.add(pl.getPedido().getFechaHora());
				paramList.add(pl.getProducto().getIdProducto());
				paramList.add(pl.getCantidad());
				paramList.add(pl.getIva());
				paramList.add(pl.getPrecioUnit());
				genExec.setParamList(paramList);
			}
			resultado = (Integer) runGeneric(conn, genExec);
		} catch (ConectorException e) {
			Conector.rollbackConn(conn);
			logger.error("Excepcion al guardarListaPedidoLinea: " + e.getMessage(), e);
			throw new PersistenciaException(e);
		} catch (Exception e) {
			Conector.rollbackConn(conn);
			logger.fatal("Excepcion GENERICA al guardarListaPedidoLinea: " + e.getMessage(), e);
			throw new PersistenciaException(e);
		}
		return resultado;
	}
	
	@Override
	public Integer eliminarListaPedidoLinea(Connection conn, Pedido pedido) throws PersistenciaException {
		Integer resultado = null;
		try {
			GenSqlExecType genExec = new GenSqlExecType(QRY_DELETE_PL);
			genExec.setParam(pedido.getPersona().getIdPersona());
			genExec.setParam(pedido.getFechaHora());
			resultado = (Integer) runGeneric(conn, genExec);
		} catch (ConectorException e) {
			Conector.rollbackConn(conn);
			logger.error("Excepcion al eliminarListaPedidoLinea: " + e.getMessage(), e);
			throw new PersistenciaException(e);
		} catch (Exception e) {
			Conector.rollbackConn(conn);
			logger.fatal("Excepcion GENERICA al eliminarListaPedidoLinea: " + e.getMessage(), e);
			throw new PersistenciaException(e);
		}
		return resultado;
	}

}
