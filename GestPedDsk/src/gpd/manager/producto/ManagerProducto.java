package gpd.manager.producto;

import java.util.List;

import org.apache.log4j.Logger;

import gpd.dominio.producto.Producto;
import gpd.dominio.producto.TipoProd;
import gpd.exceptions.PersistenciaException;
import gpd.interfaces.producto.IPersProducto;
import gpd.persistencia.conector.Conector;
import gpd.persistencia.producto.PersistenciaProducto;

public class ManagerProducto {

	private static final Logger logger = Logger.getLogger(ManagerProducto.class);
	IPersProducto interfaceProducto;
	Integer resultado;
	
	public Producto obtenerProductoPorId(Integer id) {
		logger.info("Se ingresa a obtenerProductoPorId");
		Producto producto = null;
		interfaceProducto = new PersistenciaProducto();
		try {
			Conector.getConn();
			producto = interfaceProducto.obtenerProductoPorId(id);
			Conector.closeConn("obtenerUsuario", null);
		} catch (PersistenciaException e) {
			e.printStackTrace();
		}
		return producto;
	}
	
	public List<Producto> obtenerListaProductoPorTipoProd(TipoProd tipoProd) {
		logger.info("Se ingresa a obtenerListaProductoPorTipoProd");
		List<Producto> listaProducto = null;
		interfaceProducto = new PersistenciaProducto();
		try {
			Conector.getConn();
			listaProducto = interfaceProducto.obtenerListaProductoPorTipo(tipoProd);
			Conector.closeConn("obtenerListaProductoPorTipoProd", null);
		} catch (PersistenciaException e) {
			e.printStackTrace();
		}
		return listaProducto;
	}
	
	public Integer guardarProducto(Producto producto) {
		logger.info("Se ingresa a guardarProducto");
		if(producto != null) {
			interfaceProducto = new PersistenciaProducto();
			try {
				Conector.getConn();
				resultado = interfaceProducto.guardarProducto(producto);
				Conector.closeConn("guardarProducto", null);
			} catch (PersistenciaException e) {
				//FIXME ver como manejar esta excep
				e.printStackTrace();
			}
		}
		return resultado;
	}
	
	public Integer modificarProducto(Producto producto) {
		logger.info("Se ingresa a modificarProducto");
		if(producto != null) {
			interfaceProducto = new PersistenciaProducto();
			try {
				Conector.getConn();
				resultado = interfaceProducto.modificarProducto(producto);
				Conector.closeConn("modificarProducto", null);
			} catch (PersistenciaException e) {
				//FIXME ver como manejar esta excep
				e.printStackTrace();
			}
		}
		return resultado;
	}
	
	public Integer eliminarProducto(Producto producto) {
		logger.info("Se ingresa a eliminarProducto");
		if(producto != null) {
			interfaceProducto = new PersistenciaProducto();
			try {
				Conector.getConn();
				resultado = interfaceProducto.eliminarProducto(producto);
				Conector.closeConn("eliminarProducto", null);
			} catch (PersistenciaException e) {
				//FIXME ver como manejar esta excep
				e.printStackTrace();
			}
		}
		return resultado;
	}
	
	
	public TipoProd obtenerTipoProdPorId(Integer id) {
		logger.info("Se ingresa a obtenerTipoProd");
		TipoProd tipoProd = null;
		interfaceProducto = new PersistenciaProducto();
		try {
			Conector.getConn();
			tipoProd = interfaceProducto.obtenerTipoProdPorId(id);
			Conector.closeConn("obtenerUsuario", null);
		} catch (PersistenciaException e) {
			e.printStackTrace();
		}
		return tipoProd;
	}
	
	public List<TipoProd> obtenerListaTipoProd() {
		logger.info("Se ingresa a obtenerTipoProd");
		List<TipoProd> listaTipoProd = null;
		interfaceProducto = new PersistenciaProducto();
		try {
			Conector.getConn();
			listaTipoProd = interfaceProducto.obtenerListaTipoProd();
			Conector.closeConn("obtenerUsuario", null);
		} catch (PersistenciaException e) {
			e.printStackTrace();
		}
		return listaTipoProd;
	}
	
	public Integer guardarTipoProd(TipoProd tipoProd) {
		logger.info("Se ingresa a guardarTipoProd");
		if(tipoProd != null) {
			interfaceProducto = new PersistenciaProducto();
			try {
				Conector.getConn();
				resultado = interfaceProducto.guardarTipoProd(tipoProd);
				Conector.closeConn("guardarTipoProd", null);
			} catch (PersistenciaException e) {
				//FIXME ver como manejar esta excep
				e.printStackTrace();
			}
		}
		return resultado;
	}
	
	public Integer modificarTipoProd(TipoProd tipoProd) {
		logger.info("Ingresa modificarTipoProd");
		if(tipoProd != null) {
			interfaceProducto = new PersistenciaProducto();
			try {
				Conector.getConn();
				resultado = interfaceProducto.modificarTipoProd(tipoProd);
				Conector.closeConn("modificarTipoProd", null);
			} catch (PersistenciaException e) {
				//FIXME ver como manejar esta excep
				e.printStackTrace();
			}
		}
		return resultado;
	}
	
	public Integer eliminarTipoProd(TipoProd tipoProd) {
		logger.info("Se ingresa a eliminarTipoProd");
		if(tipoProd != null) {
			interfaceProducto = new PersistenciaProducto();
			try {
				Conector.getConn();
				resultado = interfaceProducto.eliminarTipoProd(tipoProd);
				Conector.closeConn("eliminarTipoProd", null);
			} catch (PersistenciaException e) {
				//FIXME ver como manejar esta excep
				e.printStackTrace();
			}
		}
		return resultado;
	}
}
