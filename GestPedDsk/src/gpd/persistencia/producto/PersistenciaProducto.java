package gpd.persistencia.producto;

import java.io.IOException;
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
import gpd.dominio.util.Sinc;
import gpd.exceptions.ConectorException;
import gpd.exceptions.PersistenciaException;
import gpd.interfaces.producto.IPersProducto;
import gpd.persistencia.conector.Conector;
import gpd.types.Fecha;

public class PersistenciaProducto extends Conector implements IPersProducto, CnstQryProducto {
	
	private static final Logger logger = Logger.getLogger(PersistenciaProducto.class);
	private ResultSet rs;
	
	@Override
	public List<Producto> obtenerBusquedaProducto(Integer idTipoProd, String codigo, String nombre, String descripcion) throws PersistenciaException {
		List<Producto> listaProd = new ArrayList<>();
		PersistenciaTipoProd ptp = new PersistenciaTipoProd();
		PersistenciaUnidad pu = new PersistenciaUnidad();
		try {
			GenSqlSelectType genSel = new GenSqlSelectType(QRY_SEARCH_PROD);
			genSel.setParamEmptyAsNumber(idTipoProd);
			genSel.setParamEmptyAsNumber(idTipoProd);
			genSel.setParamLikeRight(codigo);
			genSel.setParam(codigo);
			genSel.setParamLikeBoth(nombre);
			genSel.setParam(nombre);
			genSel.setParamLikeRight(descripcion);
			genSel.setParam(descripcion);
			rs = (ResultSet) runGeneric(genSel);
			while(rs.next()) {
				Producto producto = new Producto();
				producto.setIdProducto(rs.getInt("id_producto"));
				producto.setTipoProd(ptp.obtenerTipoProdPorId(rs.getInt("id_tipo_prod")));
				producto.setCodigo(rs.getString("codigo"));
				producto.setNombre(rs.getString("nombre"));
				producto.setDescripcion(rs.getString("descripcion"));
				producto.setStockMin(rs.getFloat("stock_min"));
				char[] aplIvaChar = new char[1];
				rs.getCharacterStream("apl_iva").read(aplIvaChar);
				AplicaIva aplIva = AplicaIva.getAplicaIvaPorChar(aplIvaChar[0]);
				producto.setAplIva(aplIva);
				producto.setUnidad(pu.obtenerUnidadPorId(rs.getInt("id_unidad")));
				producto.setCantUnidad(rs.getInt("cant_unidad"));
				producto.setPrecio(rs.getDouble("precio"));
				char[] tipoChar = new char[1];
				rs.getCharacterStream("sinc").read(tipoChar);
				Sinc sinc = Sinc.getSincPorChar(tipoChar[0]);
				producto.setSinc(sinc);
				producto.setUltAct(new Fecha(rs.getTimestamp("ult_act")));
				listaProd.add(producto);
			}
		} catch (ConectorException | SQLException | IOException e) {
			Conector.rollbackConn();
			logger.fatal("Excepcion al obtenerBusquedaProducto: " + e.getMessage(), e);
			throw new PersistenciaException(e);
		} finally {
			closeRs(rs);
		}
		return listaProd;
	}
	
	@Override
	public Producto obtenerProductoPorId(Integer id) throws PersistenciaException {
		Producto producto = null;
		PersistenciaTipoProd ptp = new PersistenciaTipoProd();
		PersistenciaUnidad pu = new PersistenciaUnidad();
		try {
			GenSqlSelectType genSel = new GenSqlSelectType(QRY_SELECT_PROD_XID);
			genSel.setParam(id);
			rs = (ResultSet) runGeneric(genSel);
			if(rs.next()) {
				producto = new Producto();
				producto.setIdProducto(rs.getInt("id_producto"));
				producto.setTipoProd(ptp.obtenerTipoProdPorId(rs.getInt("id_tipo_prod")));
				producto.setCodigo(rs.getString("codigo"));
				producto.setNombre(rs.getString("nombre"));
				producto.setDescripcion(rs.getString("descripcion"));
				producto.setStockMin(rs.getFloat("stock_min"));
				char[] aplIvaChar = new char[1];
				rs.getCharacterStream("apl_iva").read(aplIvaChar);
				AplicaIva aplIva = AplicaIva.getAplicaIvaPorChar(aplIvaChar[0]);
				producto.setAplIva(aplIva);
				producto.setUnidad(pu.obtenerUnidadPorId(rs.getInt("id_unidad")));
				producto.setCantUnidad(rs.getInt("cant_unidad"));
				producto.setPrecio(rs.getDouble("precio"));
				char[] tipoChar = new char[1];
				rs.getCharacterStream("sinc").read(tipoChar);
				Sinc sinc = Sinc.getSincPorChar(tipoChar[0]);
				producto.setSinc(sinc);
				producto.setUltAct(new Fecha(rs.getTimestamp("ult_act")));
			}
		} catch (ConectorException | SQLException | IOException e) {
			Conector.rollbackConn();
			logger.fatal("Excepcion al obtenerProductoPorId: " + e.getMessage(), e);
			throw new PersistenciaException(e);
		}
		return producto;
	}
	
	@Override
	public List<Producto> obtenerListaProductoPorTipo(TipoProd tipoProd) throws PersistenciaException {
		List<Producto> listaProducto = new ArrayList<>();
		PersistenciaTipoProd ptp = new PersistenciaTipoProd();
		PersistenciaUnidad pu = new PersistenciaUnidad();
		try {
			GenSqlSelectType genSel = new GenSqlSelectType(QRY_SELECT_PROD_X_TIPOPROD);
			genSel.setParam(tipoProd.getIdTipoProd());
			rs = (ResultSet) runGeneric(genSel);
			while(rs.next()) {
				Producto producto = new Producto();
				producto.setIdProducto(rs.getInt("id_producto"));
				producto.setTipoProd(ptp.obtenerTipoProdPorId(rs.getInt("id_tipo_prod")));
				producto.setCodigo(rs.getString("codigo"));
				producto.setNombre(rs.getString("nombre"));
				producto.setDescripcion(rs.getString("descripcion"));
				producto.setStockMin(rs.getFloat("stock_min"));
				char[] aplIvaChar = new char[1];
				rs.getCharacterStream("apl_iva").read(aplIvaChar);
				AplicaIva aplIva = AplicaIva.getAplicaIvaPorChar(aplIvaChar[0]);
				producto.setAplIva(aplIva);
				producto.setUnidad(pu.obtenerUnidadPorId(rs.getInt("id_unidad")));
				producto.setCantUnidad(rs.getInt("cant_unidad"));
				producto.setPrecio(rs.getDouble("precio"));
				char[] tipoChar = new char[1];
				rs.getCharacterStream("sinc").read(tipoChar);
				Sinc sinc = Sinc.getSincPorChar(tipoChar[0]);
				producto.setSinc(sinc);
				producto.setUltAct(new Fecha(rs.getTimestamp("ult_act")));
				listaProducto.add(producto);
			}
		} catch (ConectorException | SQLException | IOException e) {
			Conector.rollbackConn();
			logger.fatal("Excepcion al obtenerProductoPorId: " + e.getMessage(), e);
			throw new PersistenciaException(e);
		}
		return listaProducto;
	}



	@Override
	public Integer guardarProducto(Producto producto) throws PersistenciaException {
		Integer resultado = null;
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
		try {
			resultado = (Integer) runGeneric(genExec);
		} catch (ConectorException e) {
			Conector.rollbackConn();
			logger.fatal("Excepcion al guardarProducto: " + e.getMessage(), e);
			throw new PersistenciaException(e);
		}
		return resultado;
	}

	@Override
	public Integer modificarProducto(Producto producto) throws PersistenciaException {
		Integer resultado = null;
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
		try {
			resultado = (Integer) runGeneric(genExec);
		} catch (ConectorException e) {
			Conector.rollbackConn();
			logger.fatal("Excepcion al modificarProducto: " + e.getMessage(), e);
			throw new PersistenciaException(e);
		}
		return resultado;
	}

	@Override
	public Integer eliminarProducto(Producto producto) throws PersistenciaException {
		Integer resultado = null;
		GenSqlExecType genExec = new GenSqlExecType(QRY_DELETE_PROD);
		genExec.getExecuteDatosCond().put(1, producto.getIdProducto());
		try {
			resultado = (Integer) runGeneric(genExec);
		} catch (ConectorException e) {
			Conector.rollbackConn();
			logger.fatal("Excepcion al eliminarProducto: " + e.getMessage(), e);
			throw new PersistenciaException(e);
		}
		return resultado;
	}


}
