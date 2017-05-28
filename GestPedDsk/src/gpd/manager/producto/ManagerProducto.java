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
		Conector.getConn();
		interfaceProducto = new PersistenciaProducto();
		try {
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
			Conector.getConn();
			interfaceProducto = new PersistenciaProducto();
			try {
				resultado = interfaceProducto.guardarProducto(producto);
			} catch (PersistenciaException e) {
				//FIXME ver como manejar esta excep
				e.printStackTrace();
			}
			Conector.closeConn("guardarProducto", null);
		}
		return resultado;
	}
	
	public Integer modificarProducto(Producto producto) {
		logger.info("Se ingresa a modificarProducto");
		if(producto != null) {
			Conector.getConn();
			interfaceProducto = new PersistenciaProducto();
			try {
				resultado = interfaceProducto.modificarProducto(producto);
			} catch (PersistenciaException e) {
				//FIXME ver como manejar esta excep
				e.printStackTrace();
			}
			Conector.closeConn("modificarProducto", null);
		}
		return resultado;
	}
	
	public Integer eliminarProducto(Producto producto) {
		logger.info("Se ingresa a eliminarProducto");
		if(producto != null) {
			Conector.getConn();
			interfaceProducto = new PersistenciaProducto();
			try {
				resultado = interfaceProducto.eliminarProducto(producto);
			} catch (PersistenciaException e) {
				//FIXME ver como manejar esta excep
				e.printStackTrace();
			}
			Conector.closeConn("eliminarProducto", null);
		}
		return resultado;
	}
	
	
	public TipoProd obtenerTipoProdPorId(Integer id) {
		logger.info("Se ingresa a obtenerTipoProd");
		TipoProd tipoProd = null;
		Conector.getConn();
		interfaceProducto = new PersistenciaProducto();
		try {
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
		Conector.getConn();
		interfaceProducto = new PersistenciaProducto();
		try {
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
			Conector.getConn();
			interfaceProducto = new PersistenciaProducto();
			try {
				resultado = interfaceProducto.guardarTipoProd(tipoProd);
			} catch (PersistenciaException e) {
				//FIXME ver como manejar esta excep
				e.printStackTrace();
			}
			Conector.closeConn("guardarTipoProd", null);
		}
		return resultado;
	}
	
	public Integer modificarTipoProd(TipoProd tipoProd) {
		logger.info("Ingresa modificarTipoProd");
		if(tipoProd != null) {
			Conector.getConn();
			interfaceProducto = new PersistenciaProducto();
			try {
				resultado = interfaceProducto.modificarTipoProd(tipoProd);
			} catch (PersistenciaException e) {
				//FIXME ver como manejar esta excep
				e.printStackTrace();
			}
			Conector.closeConn("modificarTipoProd", null);
		}
		return resultado;
	}
	
	public Integer eliminarTipoProd(TipoProd tipoProd) {
		logger.info("Se ingresa a eliminarTipoProd");
		if(tipoProd != null) {
			Conector.getConn();
			interfaceProducto = new PersistenciaProducto();
			try {
				resultado = interfaceProducto.eliminarTipoProd(tipoProd);
			} catch (PersistenciaException e) {
				//FIXME ver como manejar esta excep
				e.printStackTrace();
			}
			Conector.closeConn("eliminarTipoProd", null);
		}
		return resultado;
	}
}
