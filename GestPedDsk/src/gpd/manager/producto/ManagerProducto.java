package gpd.manager.producto;

import java.util.List;

import org.apache.log4j.Logger;

import gpd.db.constantes.CnstQryGeneric;
import gpd.dominio.producto.Deposito;
import gpd.dominio.producto.Producto;
import gpd.dominio.producto.TipoProd;
import gpd.dominio.producto.Utilidad;
import gpd.dominio.util.Sinc;
import gpd.exceptions.PersistenciaException;
import gpd.interfaces.producto.IPersDeposito;
import gpd.interfaces.producto.IPersProducto;
import gpd.interfaces.producto.IPersTipoProd;
import gpd.interfaces.producto.IPersUtilidad;
import gpd.persistencia.conector.Conector;
import gpd.persistencia.producto.PersistenciaDeposito;
import gpd.persistencia.producto.PersistenciaProducto;
import gpd.persistencia.producto.PersistenciaTipoProd;
import gpd.persistencia.producto.PersistenciaUtilidad;
import gpd.types.Fecha;

public class ManagerProducto {

	private static final Logger logger = Logger.getLogger(ManagerProducto.class);
	private IPersProducto interfaceProducto;
	private IPersTipoProd interfaceTipoProd;
	private IPersDeposito interfaceDeposito;
	private IPersUtilidad interfaceUtilidad;
	private Integer resultado;
	
	
	/*****************************************************************************************************************************************************/
	/** PRODUCTO */
	/*****************************************************************************************************************************************************/
	
	public List<Producto> obtenerBusquedaProducto(TipoProd tipoProd, String codigo, String nombre, String descripcion) {
		logger.info("Ingresa obtenerBusquedaProducto");
		List<Producto> listaProd = null;
		interfaceProducto = new PersistenciaProducto();
		try {
			Conector.getConn();
			Integer idTipoProd = (tipoProd != null ? tipoProd.getIdTipoProd() : CnstQryGeneric.NUMBER_INVALID);
			listaProd = interfaceProducto.obtenerBusquedaProducto(idTipoProd, codigo, nombre, descripcion);
			Conector.closeConn("obtenerBusquedaProducto", null);
		} catch (PersistenciaException e) {
			e.printStackTrace();//FIXME ver como manejar esta excep
		}
		return listaProd;
	}
	
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
				producto.setSinc(Sinc.N);
				producto.setUltAct(new Fecha(Fecha.AMDHMS));
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
				producto.setSinc(Sinc.N);
				producto.setUltAct(new Fecha(Fecha.AMDHMS));
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
	
	/*****************************************************************************************************************************************************/
	/** TIPO PROD */
	/*****************************************************************************************************************************************************/
	
	public TipoProd obtenerTipoProdPorId(Integer id) {
		logger.info("Se ingresa a obtenerTipoProd");
		TipoProd tipoProd = null;
		interfaceProducto = new PersistenciaProducto();
		try {
			Conector.getConn();
			tipoProd = interfaceTipoProd.obtenerTipoProdPorId(id);
			Conector.closeConn("obtenerUsuario", null);
		} catch (PersistenciaException e) {
			//FIXME ver como manejar esta excep
			e.printStackTrace();
		}
		return tipoProd;
	}
	
	public List<TipoProd> obtenerListaTipoProd() {
		logger.info("Se ingresa a obtenerListaTipoProd");
		List<TipoProd> listaTipoProd = null;
		interfaceTipoProd = new PersistenciaTipoProd();
		try {
			Conector.getConn();
			listaTipoProd = interfaceTipoProd.obtenerListaTipoProd();
			Conector.closeConn("obtenerListaTipoProd", null);
		} catch (PersistenciaException e) {
			//FIXME ver como manejar esta excep
			e.printStackTrace();
		}
		return listaTipoProd;
	}
	
	public Integer guardarTipoProd(TipoProd tipoProd) {
		logger.info("Se ingresa a guardarTipoProd");
		if(tipoProd != null) {
			interfaceTipoProd = new PersistenciaTipoProd();
			try {
				Conector.getConn();
				resultado = interfaceTipoProd.guardarTipoProd(tipoProd);
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
			interfaceTipoProd = new PersistenciaTipoProd();
			try {
				Conector.getConn();
				resultado = interfaceTipoProd.modificarTipoProd(tipoProd);
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
			interfaceTipoProd = new PersistenciaTipoProd();
			try {
				Conector.getConn();
				resultado = interfaceTipoProd.eliminarTipoProd(tipoProd);
				Conector.closeConn("eliminarTipoProd", null);
			} catch (PersistenciaException e) {
				//FIXME ver como manejar esta excep
				e.printStackTrace();
			}
		}
		return resultado;
	}

	/*****************************************************************************************************************************************************/
	/** DEPOSITO */
	/*****************************************************************************************************************************************************/
	
	public Deposito obtenerDepositoPorId(Integer id) {
		logger.info("Se ingresa a obtenerDepositoPorId");
		Deposito deposito = null;
		interfaceDeposito = new PersistenciaDeposito();
		try {
			Conector.getConn();
			deposito = interfaceDeposito.obtenerDepositoPorId(id);
			Conector.closeConn("obtenerDepositoPorId", null);
		} catch (PersistenciaException e) {
			e.printStackTrace();
		}
		return deposito;
	}
	
	public List<Deposito> obtenerListaDeposito() {
		logger.info("Se ingresa a obtenerListaDeposito");
		List<Deposito> listaDeposito = null;
		interfaceDeposito = new PersistenciaDeposito();
		try {
			Conector.getConn();
			listaDeposito = interfaceDeposito.obtenerListaDeposito();
			Conector.closeConn("obtenerListaDeposito", null);
		} catch (PersistenciaException e) {
			//FIXME ver como manejar esta excep
			e.printStackTrace();
		}
		return listaDeposito;
	}

	public Integer guardarDeposito(Deposito deposito) {
		logger.info("Se ingresa a guardarDeposito");
		if(deposito != null) {
			interfaceDeposito = new PersistenciaDeposito();
			try {
				Conector.getConn();
				resultado = interfaceDeposito.guardarDeposito(deposito);
				Conector.closeConn("guardarDeposito", null);
			} catch (PersistenciaException e) {
				//FIXME ver como manejar esta excep
				e.printStackTrace();
			}
		}
		return resultado;
	}

	public Integer modificarDeposito(Deposito deposito) {
		logger.info("Ingresa modificarDeposito");
		if(deposito != null) {
			interfaceDeposito = new PersistenciaDeposito();
			try {
				Conector.getConn();
				resultado = interfaceDeposito.modificarDeposito(deposito);
				Conector.closeConn("modificarDeposito", null);
			} catch (PersistenciaException e) {
				//FIXME ver como manejar esta excep
				e.printStackTrace();
			}
		}
		return resultado;
	}

	public Integer eliminarDeposito(Deposito deposito) {
		logger.info("Se ingresa a eliminarDeposito");
		if(deposito != null) {
			interfaceDeposito = new PersistenciaDeposito();
			try {
				Conector.getConn();
				resultado = interfaceDeposito.eliminarDeposito(deposito);
				Conector.closeConn("eliminarDeposito", null);
			} catch (PersistenciaException e) {
				//FIXME ver como manejar esta excep
				e.printStackTrace();
			}
		}
		return resultado;
	}
	
	/*****************************************************************************************************************************************************/
	/** UTILIDAD */
	/*****************************************************************************************************************************************************/
	
	public Utilidad obtenerUtilidadPorId(Integer id) {
		logger.info("Se ingresa a obtenerUtilidadPorId");
		Utilidad utilidad = null;
		interfaceUtilidad = new PersistenciaUtilidad();
		try {
			Conector.getConn();
			utilidad = interfaceUtilidad.obtenerUtilidadPorId(id);
			Conector.closeConn("obtenerUtilidadPorId", null);
		} catch (PersistenciaException e) {
			e.printStackTrace();
		}
		return utilidad;
	}
	
	public List<Utilidad> obtenerListaUtilidad() {
		logger.info("Se ingresa a obtenerListaUtilidad");
		List<Utilidad> listaUtilidad = null;
		interfaceUtilidad = new PersistenciaUtilidad();
		try {
			Conector.getConn();
			listaUtilidad = interfaceUtilidad.obtenerListaUtilidad();
			Conector.closeConn("obtenerListaUtilidad", null);
		} catch (PersistenciaException e) {
			//FIXME ver como manejar esta excep
			e.printStackTrace();
		}
		return listaUtilidad;
	}

	public Integer guardarUtilidad(Utilidad utilidad) {
		logger.info("Se ingresa a guardarUtilidad");
		if(utilidad != null) {
			interfaceUtilidad = new PersistenciaUtilidad();
			try {
				Conector.getConn();
				resultado = interfaceUtilidad.guardarUtilidad(utilidad);
				Conector.closeConn("guardarUtilidad", null);
			} catch (PersistenciaException e) {
				//FIXME ver como manejar esta excep
				e.printStackTrace();
			}
		}
		return resultado;
	}

	public Integer modificarUtilidad(Utilidad utilidad) {
		logger.info("Ingresa modificarUtilidad");
		if(utilidad != null) {
			interfaceUtilidad = new PersistenciaUtilidad();
			try {
				Conector.getConn();
				resultado = interfaceUtilidad.modificarUtilidad(utilidad);
				Conector.closeConn("modificarUtilidad", null);
			} catch (PersistenciaException e) {
				//FIXME ver como manejar esta excep
				e.printStackTrace();
			}
		}
		return resultado;
	}

	public Integer eliminarUtilidad(Utilidad utilidad) {
		logger.info("Se ingresa a eliminarTipoProd");
		if(utilidad != null) {
			interfaceUtilidad = new PersistenciaUtilidad();
			try {
				Conector.getConn();
				resultado = interfaceUtilidad.eliminarUtilidad(utilidad);
				Conector.closeConn("eliminarTipoProd", null);
			} catch (PersistenciaException e) {
				//FIXME ver como manejar esta excep
				e.printStackTrace();
			}
		}
		return resultado;
	}
	
}
