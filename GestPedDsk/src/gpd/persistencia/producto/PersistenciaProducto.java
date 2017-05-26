package gpd.persistencia.producto;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import gpd.db.constantes.CnstQryProducto;
import gpd.db.generic.GenSqlExecType;
import gpd.db.generic.GenSqlSelectType;
import gpd.dominio.producto.Producto;
import gpd.dominio.producto.TipoProd;
import gpd.dominio.util.Sinc;
import gpd.exceptions.ConectorException;
import gpd.exceptions.PersistenciaException;
import gpd.interfaces.producto.IPersProducto;
import gpd.persistencia.conector.Conector;
import gpd.types.Fecha;

public class PersistenciaProducto extends Conector implements IPersProducto {
	
	private static final Logger logger = Logger.getLogger(PersistenciaProducto.class);

	
	@Override
	public Producto obtenerProductoPorId(Integer id) throws PersistenciaException {
		Producto producto = null;
		try {
			GenSqlSelectType genType = new GenSqlSelectType(CnstQryProducto.QRY_SELECT_PROD_XID);
			genType.getSelectDatosCond().put(1, id);
			ResultSet rs = (ResultSet) runGeneric(genType);
			if(rs.next()) {
				producto = new Producto();
				producto.setIdProducto(rs.getInt("id_producto"));
				producto.setTipoProd(obtenerTipoProdPorId(rs.getInt("id_tipo_prod")));
				producto.setCodigo(rs.getString("codigo"));
				producto.setNombre(rs.getString("nombre"));
				producto.setDescripcion(rs.getString("descripcion"));
				producto.setStockMin(rs.getFloat("stock_min"));
				producto.setPrecio(rs.getDouble("precio"));
				char[] tipoChar = new char[1];
				rs.getCharacterStream("sinc").read(tipoChar);
				Sinc sinc = Sinc.getSincPorChar(tipoChar[0]);
				producto.setSinc(sinc);
				producto.setUltAct(new Fecha(rs.getTimestamp("ult_act")));
			}
		} catch (ConectorException | SQLException | IOException e) {
			Conector.rollbackConn();
			logger.log(Level.FATAL, "Excepcion al obtenerProductoPorId: " + e.getMessage(), e);
			throw new PersistenciaException(e.getMessage());
		}
		return producto;
	}
	
	@Override
	public List<Producto> obtenerListaProductoPorTipo(TipoProd tipoProd) throws PersistenciaException {
		List<Producto> listaProducto = new ArrayList<>();
		try {
			GenSqlSelectType genType = new GenSqlSelectType(CnstQryProducto.QRY_SELECT_PROD_X_TIPOPROD);
			genType.getSelectDatosCond().put(1, tipoProd.getIdTipoProd());
			ResultSet rs = (ResultSet) runGeneric(genType);
			while(rs.next()) {
				Producto producto = new Producto();
				producto.setIdProducto(rs.getInt("id_producto"));
				producto.setTipoProd(obtenerTipoProdPorId(rs.getInt("id_tipo_prod")));
				producto.setCodigo(rs.getString("codigo"));
				producto.setNombre(rs.getString("nombre"));
				producto.setDescripcion(rs.getString("descripcion"));
				producto.setStockMin(rs.getFloat("stock_min"));
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
			logger.log(Level.FATAL, "Excepcion al obtenerProductoPorId: " + e.getMessage(), e);
			throw new PersistenciaException(e.getMessage());
		}
		return listaProducto;
	}



	@Override
	public Integer guardarProducto(Producto producto) {
		Integer resultado = null;
		GenSqlExecType genExec = new GenSqlExecType(CnstQryProducto.QRY_INSERT_PROD);
		genExec.getExecuteDatosCond().put(1, producto.getCodigo());
		genExec.getExecuteDatosCond().put(2, producto.getNombre());
		genExec.getExecuteDatosCond().put(3, producto.getDescripcion());
		genExec.getExecuteDatosCond().put(4, producto.getStockMin());
		genExec.getExecuteDatosCond().put(5, producto.getPrecio());
		genExec.getExecuteDatosCond().put(6, producto.getSinc().getAsChar());
		genExec.getExecuteDatosCond().put(7, producto.getUltAct());
		try {
			resultado = (Integer) runGeneric(genExec);
		} catch (ConectorException e) {
			Conector.rollbackConn();
			logger.log(Level.FATAL, "Excepcion al guardarProducto: " + e.getMessage(), e);
		}
		return resultado;
	}

	@Override
	public Integer modificarProducto(Producto producto) {
		Integer resultado = null;
		GenSqlExecType genExec = new GenSqlExecType(CnstQryProducto.QRY_UPDATE_PROD);
		genExec.getExecuteDatosCond().put(1, producto.getCodigo());
		genExec.getExecuteDatosCond().put(2, producto.getNombre());
		genExec.getExecuteDatosCond().put(3, producto.getDescripcion());
		genExec.getExecuteDatosCond().put(4, producto.getStockMin());
		genExec.getExecuteDatosCond().put(5, producto.getPrecio());
		genExec.getExecuteDatosCond().put(6, producto.getSinc().getAsChar());
		genExec.getExecuteDatosCond().put(7, producto.getUltAct());
		genExec.getExecuteDatosCond().put(8, producto.getIdProducto());
		try {
			resultado = (Integer) runGeneric(genExec);
		} catch (ConectorException e) {
			Conector.rollbackConn();
			logger.log(Level.FATAL, "Excepcion al modificarProducto: " + e.getMessage(), e);
		}
		return resultado;
	}

	@Override
	public Integer eliminarProducto(Producto producto) {
		Integer resultado = null;
		GenSqlExecType genExec = new GenSqlExecType(CnstQryProducto.QRY_DELETE_PROD);
		genExec.getExecuteDatosCond().put(1, producto.getIdProducto());
		try {
			resultado = (Integer) runGeneric(genExec);
		} catch (ConectorException e) {
			Conector.rollbackConn();
			logger.log(Level.FATAL, "Excepcion al modificarProducto: " + e.getMessage(), e);
		}
		return resultado;
	}

	@Override
	public TipoProd obtenerTipoProdPorId(Integer id) throws PersistenciaException {
		TipoProd tipoProd = null;
		try {
			GenSqlSelectType genType = new GenSqlSelectType(CnstQryProducto.QRY_SELECT_TIPOPROD_X_ID);
			genType.getSelectDatosCond().put(1, id);
			ResultSet rs = (ResultSet) runGeneric(genType);
			if(rs.next()) {
				tipoProd = new TipoProd();
				tipoProd.setIdTipoProd(rs.getInt("id_tipo_prod"));
				tipoProd.setDescripcion(rs.getString("descripcion"));
			}
		} catch (ConectorException | SQLException e) {
			Conector.rollbackConn();
			logger.log(Level.FATAL, "Excepcion al obtenerTipoProdPorId: " + e.getMessage(), e);
			throw new PersistenciaException(e.getMessage());
		}
		return tipoProd;
	}
	
	@Override
	public List<TipoProd> obtenerListaTipoProd() throws PersistenciaException {
		List<TipoProd> listaTipoProd = new ArrayList<>();
		try {
			GenSqlSelectType genType = new GenSqlSelectType(CnstQryProducto.QRY_SELECT_TIPOPROD);
			ResultSet rs = (ResultSet) runGeneric(genType);
			while(rs.next()) {
				TipoProd tipoProd = new TipoProd();
				tipoProd.setIdTipoProd(rs.getInt("id_tipo_prod"));
				tipoProd.setDescripcion(rs.getString("descripcion"));
				listaTipoProd.add(tipoProd);
			}
		} catch (ConectorException | SQLException e) {
			Conector.rollbackConn();
			logger.log(Level.FATAL, "Excepcion al obtenerListaTipoProd: " + e.getMessage(), e);
			throw new PersistenciaException(e.getMessage());
		}
		return listaTipoProd;
	}

	@Override
	public Integer guardarTipoProd(TipoProd tipoProd) {
		Integer resultado = null;
		GenSqlExecType genExec = new GenSqlExecType(CnstQryProducto.QRY_INSERT_TIPOPROD);
		genExec.getExecuteDatosCond().put(1, tipoProd.getDescripcion());
		try {
			resultado = (Integer) runGeneric(genExec);
		} catch (ConectorException e) {
			Conector.rollbackConn();
			logger.log(Level.FATAL, "Excepcion al guardarTipoProd: " + e.getMessage(), e);
		}
		return resultado;
	}

	@Override
	public Integer modificarTipoProd(TipoProd tipoProd) {
		Integer resultado = null;
		GenSqlExecType genExec = new GenSqlExecType(CnstQryProducto.QRY_UPDATE_TIPOPROD);
		genExec.getExecuteDatosCond().put(1, tipoProd.getDescripcion());
		genExec.getExecuteDatosCond().put(2, tipoProd.getIdTipoProd());
		try {
			resultado = (Integer) runGeneric(genExec);
		} catch (ConectorException e) {
			Conector.rollbackConn();
			logger.log(Level.FATAL, "Excepcion al modificarTipoProd: " + e.getMessage(), e);
		}
		return resultado;
	}

	@Override
	public Integer eliminarTipoProd(TipoProd tipoProd) {
		Integer resultado = null;
		GenSqlExecType genExec = new GenSqlExecType(CnstQryProducto.QRY_UPDATE_TIPOPROD);
		genExec.getExecuteDatosCond().put(1, tipoProd.getIdTipoProd());
		try {
			resultado = (Integer) runGeneric(genExec);
		} catch (ConectorException e) {
			Conector.rollbackConn();
			logger.log(Level.FATAL, "Excepcion al eliminarTipoProd: " + e.getMessage(), e);
		}
		return resultado;
	}


}
