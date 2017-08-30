package gpd.persistencia.producto;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import gpd.db.constantes.CnstQryProducto;
import gpd.db.generic.GenSqlExecType;
import gpd.db.generic.GenSqlSelectType;
import gpd.dominio.producto.AplicaIva;
import gpd.dominio.producto.Producto;
import gpd.dominio.producto.TipoProd;
import gpd.dominio.util.Estado;
import gpd.dominio.util.Sinc;
import gpd.exceptions.ConectorException;
import gpd.exceptions.PersistenciaException;
import gpd.interfaces.producto.IPersProducto;
import gpd.persistencia.conector.Conector;
import gpd.types.Fecha;

public class PersistenciaProducto extends Conector implements IPersProducto, CnstQryProducto {
	
	private static final Logger logger = Logger.getLogger(PersistenciaProducto.class);

	
	@Override
	public List<Producto> obtenerBusquedaProducto(Connection conn, Integer idTipoProd, String codigo, String nombre, String descripcion) throws PersistenciaException {
		List<Producto> listaProd = new ArrayList<>();
		try {
			PersistenciaTipoProd ptp = new PersistenciaTipoProd();
			PersistenciaUnidad pu = new PersistenciaUnidad();
			GenSqlSelectType genSel = new GenSqlSelectType(QRY_SEARCH_PROD);
			genSel.setParamEmptyAsNumber(idTipoProd);
			genSel.setParamEmptyAsNumber(idTipoProd);
			genSel.setParamLikeRight(codigo);
			genSel.setParam(codigo);
			genSel.setParamLikeBoth(nombre);
			genSel.setParam(nombre);
			genSel.setParamLikeRight(descripcion);
			genSel.setParam(descripcion);
			try (ResultSet rs = (ResultSet) runGeneric(conn, genSel)) {
				while(rs.next()) {
					Producto producto = new Producto();
					producto.setIdProducto(rs.getInt("id_producto"));
					producto.setTipoProd(ptp.obtenerTipoProdPorId(conn, rs.getInt("id_tipo_prod")));
					producto.setCodigo(rs.getString("codigo"));
					producto.setNombre(rs.getString("nombre"));
					producto.setDescripcion(rs.getString("descripcion"));
					producto.setStockMin(rs.getFloat("stock_min"));
					char[] aplIvaChar = new char[1];
					rs.getCharacterStream("apl_iva").read(aplIvaChar);
					AplicaIva aplIva = AplicaIva.getAplicaIvaPorChar(aplIvaChar[0]);
					producto.setAplIva(aplIva);
					producto.setUnidad(pu.obtenerUnidadPorId(conn, rs.getInt("id_unidad")));
					producto.setCantUnidad(rs.getFloat("cant_unidad"));
					producto.setPrecio(rs.getDouble("precio"));
					char[] sincChar = new char[1];
					rs.getCharacterStream("sinc").read(sincChar);
					Sinc sinc = Sinc.getSincPorChar(sincChar[0]);
					producto.setSinc(sinc);
					producto.setUltAct(new Fecha(rs.getTimestamp("ult_act")));
					producto.setEstadoProd(Estado.getEstadoProdPorInt(rs.getInt("activo")));
					listaProd.add(producto);
				}
			}
		} catch (ConectorException | SQLException | IOException e) {
			Conector.rollbackConn(conn);
			logger.fatal("Excepcion al obtenerBusquedaProducto: " + e.getMessage(), e);
			throw new PersistenciaException(e);
		} catch (Exception e) {
			Conector.rollbackConn(conn);
			logger.fatal("Excepcion GENERICA al obtenerBusquedaProducto: " + e.getMessage(), e);
			throw new PersistenciaException(e);
		}
		return listaProd;
	}
	
	@Override
	public Producto obtenerProductoPorId(Connection conn, Integer id) throws PersistenciaException {
		Producto producto = null;
		try {
			PersistenciaTipoProd ptp = new PersistenciaTipoProd();
			PersistenciaUnidad pu = new PersistenciaUnidad();
			GenSqlSelectType genSel = new GenSqlSelectType(QRY_SELECT_PROD_XID);
			genSel.setParam(id);
			try (ResultSet rs = (ResultSet) runGeneric(conn, genSel)) {
				if(rs.next()) {
					producto = new Producto();
					producto.setIdProducto(rs.getInt("id_producto"));
					producto.setTipoProd(ptp.obtenerTipoProdPorId(conn, rs.getInt("id_tipo_prod")));
					producto.setCodigo(rs.getString("codigo"));
					producto.setNombre(rs.getString("nombre"));
					producto.setDescripcion(rs.getString("descripcion"));
					producto.setStockMin(rs.getFloat("stock_min"));
					char[] aplIvaChar = new char[1];
					rs.getCharacterStream("apl_iva").read(aplIvaChar);
					AplicaIva aplIva = AplicaIva.getAplicaIvaPorChar(aplIvaChar[0]);
					producto.setAplIva(aplIva);
					producto.setUnidad(pu.obtenerUnidadPorId(conn, rs.getInt("id_unidad")));
					producto.setCantUnidad(rs.getFloat("cant_unidad"));
					producto.setPrecio(rs.getDouble("precio"));
					char[] sincChar = new char[1];
					rs.getCharacterStream("sinc").read(sincChar);
					Sinc sinc = Sinc.getSincPorChar(sincChar[0]);
					producto.setSinc(sinc);
					producto.setUltAct(new Fecha(rs.getTimestamp("ult_act")));
					producto.setEstadoProd(Estado.getEstadoProdPorInt(rs.getInt("activo")));
				}
			}
		} catch (ConectorException | SQLException | IOException e) {
			Conector.rollbackConn(conn);
			logger.fatal("Excepcion al obtenerProductoPorId: " + e.getMessage(), e);
			throw new PersistenciaException(e);
		} catch (Exception e) {
			Conector.rollbackConn(conn);
			logger.fatal("Excepcion GENERICA al obtenerProductoPorId: " + e.getMessage(), e);
			throw new PersistenciaException(e);
		}
		return producto;
	}
	
	@Override
	public List<Producto> obtenerListaProductoPorTipo(Connection conn, TipoProd tipoProd) throws PersistenciaException {
		List<Producto> listaProducto = new ArrayList<>();
		try {
			PersistenciaTipoProd ptp = new PersistenciaTipoProd();
			PersistenciaUnidad pu = new PersistenciaUnidad();
			GenSqlSelectType genSel = new GenSqlSelectType(QRY_SELECT_PROD_X_TIPOPROD);
			genSel.setParam(tipoProd.getIdTipoProd());
			try (ResultSet rs = (ResultSet) runGeneric(conn, genSel)) {
				while(rs.next()) {
					Producto producto = new Producto();
					producto.setIdProducto(rs.getInt("id_producto"));
					producto.setTipoProd(ptp.obtenerTipoProdPorId(conn, rs.getInt("id_tipo_prod")));
					producto.setCodigo(rs.getString("codigo"));
					producto.setNombre(rs.getString("nombre"));
					producto.setDescripcion(rs.getString("descripcion"));
					producto.setStockMin(rs.getFloat("stock_min"));
					char[] aplIvaChar = new char[1];
					rs.getCharacterStream("apl_iva").read(aplIvaChar);
					AplicaIva aplIva = AplicaIva.getAplicaIvaPorChar(aplIvaChar[0]);
					producto.setAplIva(aplIva);
					producto.setUnidad(pu.obtenerUnidadPorId(conn, rs.getInt("id_unidad")));
					producto.setCantUnidad(rs.getFloat("cant_unidad"));
					producto.setPrecio(rs.getDouble("precio"));
					char[] sincChar = new char[1];
					rs.getCharacterStream("sinc").read(sincChar);
					Sinc sinc = Sinc.getSincPorChar(sincChar[0]);
					producto.setSinc(sinc);
					producto.setUltAct(new Fecha(rs.getTimestamp("ult_act")));
					producto.setEstadoProd(Estado.getEstadoProdPorInt(rs.getInt("activo")));
					listaProducto.add(producto);
				}
			}
		} catch (ConectorException | SQLException | IOException e) {
			Conector.rollbackConn(conn);
			logger.fatal("Excepcion al obtenerListaProductoPorTipo: " + e.getMessage(), e);
			throw new PersistenciaException(e);
		} catch (Exception e) {
			Conector.rollbackConn(conn);
			logger.fatal("Excepcion GENERICA al obtenerListaProductoPorTipo: " + e.getMessage(), e);
			throw new PersistenciaException(e);
		}
		return listaProducto;
	}
	
	@Override
	public List<Producto> obtenerListaProductoNoSinc(Connection conn, Fecha fechaDesde, Fecha fechaHasta) throws PersistenciaException {
		List<Producto> listaProducto = new ArrayList<>();
		try {
			PersistenciaTipoProd ptp = new PersistenciaTipoProd();
			PersistenciaUnidad pu = new PersistenciaUnidad();
			GenSqlSelectType genSel = new GenSqlSelectType(QRY_SELECT_PROD_NO_SINC);
			genSel.setParam(fechaDesde);
			genSel.setParam(fechaHasta);
			try (ResultSet rs = (ResultSet) runGeneric(conn, genSel)) {
				while(rs.next()) {
					Producto producto = new Producto();
					producto.setIdProducto(rs.getInt("id_producto"));
					producto.setTipoProd(ptp.obtenerTipoProdPorId(conn, rs.getInt("id_tipo_prod")));
					producto.setCodigo(rs.getString("codigo"));
					producto.setNombre(rs.getString("nombre"));
					producto.setDescripcion(rs.getString("descripcion"));
					producto.setStockMin(rs.getFloat("stock_min"));
					char[] aplIvaChar = new char[1];
					rs.getCharacterStream("apl_iva").read(aplIvaChar);
					AplicaIva aplIva = AplicaIva.getAplicaIvaPorChar(aplIvaChar[0]);
					producto.setAplIva(aplIva);
					producto.setUnidad(pu.obtenerUnidadPorId(conn, rs.getInt("id_unidad")));
					producto.setCantUnidad(rs.getFloat("cant_unidad"));
					producto.setPrecio(rs.getDouble("precio"));
					char[] sincChar = new char[1];
					rs.getCharacterStream("sinc").read(sincChar);
					Sinc sinc = Sinc.getSincPorChar(sincChar[0]);
					producto.setSinc(sinc);
					producto.setUltAct(new Fecha(rs.getTimestamp("ult_act")));
					producto.setEstadoProd(Estado.getEstadoProdPorInt(rs.getInt("activo")));
					listaProducto.add(producto);
				}
			}
		} catch (ConectorException | SQLException | IOException e) {
			Conector.rollbackConn(conn);
			logger.fatal("Excepcion al obtenerListaProductoNoSinc: " + e.getMessage(), e);
			throw new PersistenciaException(e);
		} catch (Exception e) {
			Conector.rollbackConn(conn);
			logger.fatal("Excepcion GENERICA al obtenerListaProductoNoSinc: " + e.getMessage(), e);
			throw new PersistenciaException(e);
		}
		return listaProducto;
	}

	@Override
	public Integer guardarProducto(Connection conn, Producto producto) throws PersistenciaException {
		Integer resultado = null;
		try {
			GenSqlExecType genExec = new GenSqlExecType(QRY_INSERT_PROD);
			genExec.setParam(producto.getTipoProd().getIdTipoProd());
			genExec.setParam(producto.getCodigo());
			genExec.setParam(producto.getNombre());
			genExec.setParam(producto.getDescripcion());
			genExec.setParam(producto.getStockMin());
			genExec.setParam(producto.getAplIva().getAsChar());
			genExec.setParam(producto.getUnidad().getIdUnidad());
			genExec.setParam(producto.getCantUnidad());
			genExec.setParam(producto.getPrecio());
			genExec.setParam(producto.getSinc().getAsChar());
			genExec.setParam(producto.getUltAct());
			genExec.setParam(producto.getEstadoProd().getAsInt());
			resultado = (Integer) runGeneric(conn, genExec);
		} catch (ConectorException e) {
			Conector.rollbackConn(conn);
			logger.fatal("Excepcion al guardarProducto: " + e.getMessage(), e);
			throw new PersistenciaException(e);
		} catch (Exception e) {
			Conector.rollbackConn(conn);
			logger.fatal("Excepcion GENERICA al guardarProducto: " + e.getMessage(), e);
			throw new PersistenciaException(e);
		}
		return resultado;
	}

	@Override
	public Integer modificarProducto(Connection conn, Producto producto) throws PersistenciaException {
		Integer resultado = null;
		try {
			GenSqlExecType genExec = new GenSqlExecType(QRY_UPDATE_PROD);
			genExec.setParam(producto.getTipoProd().getIdTipoProd());
			genExec.setParam(producto.getCodigo());
			genExec.setParam(producto.getNombre());
			genExec.setParam(producto.getDescripcion());
			genExec.setParam(producto.getStockMin());
			genExec.setParam(producto.getAplIva().getAsChar());
			genExec.setParam(producto.getUnidad().getIdUnidad());
			genExec.setParam(producto.getCantUnidad());
			genExec.setParam(producto.getPrecio());
			genExec.setParam(producto.getSinc().getAsChar());
			genExec.setParam(producto.getUltAct());
			genExec.setParam(producto.getIdProducto());
			resultado = (Integer) runGeneric(conn, genExec);
		} catch (ConectorException e) {
			Conector.rollbackConn(conn);
			logger.fatal("Excepcion al modificarProducto: " + e.getMessage(), e);
			throw new PersistenciaException(e);
		} catch (Exception e) {
			Conector.rollbackConn(conn);
			logger.fatal("Excepcion GENERICA al modificarProducto: " + e.getMessage(), e);
			throw new PersistenciaException(e);
		}
		return resultado;
	}

	@Override
	public Integer modificarSincProducto(Connection conn, Integer idProd, Sinc sinc, Fecha ultAct) throws PersistenciaException {
		Integer resultado = null;
		try {
			GenSqlExecType genExec = new GenSqlExecType(QRY_UPDATE_SINC_PROD);
			genExec.setParam(sinc.getAsChar());
			genExec.setParam(ultAct);
			genExec.setParam(idProd);
			resultado = (Integer) runGeneric(conn, genExec);
		} catch (ConectorException e) {
			Conector.rollbackConn(conn);
			logger.fatal("Excepcion al modificarSincProducto: " + e.getMessage(), e);
			throw new PersistenciaException(e);
		} catch (Exception e) {
			Conector.rollbackConn(conn);
			logger.fatal("Excepcion GENERICA al modificarSincProducto: " + e.getMessage(), e);
			throw new PersistenciaException(e);
		}
		return resultado;
	}
	
	@Override
	public Integer desactivarProducto(Connection conn, Producto producto) throws PersistenciaException {
		Integer resultado = null;
		try {
			GenSqlExecType genExec = new GenSqlExecType(QRY_DESACT_PROD);
			genExec.setParam(producto.getEstadoProd().getAsInt());
			genExec.setParam(producto.getSinc().getAsChar());
			genExec.setParam(producto.getIdProducto());
			resultado = (Integer) runGeneric(conn, genExec);
		} catch (ConectorException e) {
			Conector.rollbackConn(conn);
			logger.fatal("Excepcion al desactivarProducto: " + e.getMessage(), e);
			throw new PersistenciaException(e);
		} catch (Exception e) {
			Conector.rollbackConn(conn);
			logger.fatal("Excepcion GENERICA al desactivarProducto: " + e.getMessage(), e);
			throw new PersistenciaException(e);
		}
		return resultado;
	}


}
