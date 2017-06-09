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
		} catch (PersistenciaException e) {
			e.printStackTrace();//FIXME ver como manejar esta excep
		} finally {
			Conector.closeConn("obtenerBusquedaProducto", null);
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
		} catch (PersistenciaException e) {
			e.printStackTrace();
		} finally {
			Conector.closeConn("obtenerUsuario", null);
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
		} catch (PersistenciaException e) {
			e.printStackTrace();
		} finally {
			Conector.closeConn("obtenerListaProductoPorTipoProd", null);
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
			} catch (PersistenciaException e) {
				//FIXME ver como manejar esta excep
				e.printStackTrace();
			} finally {
				Conector.closeConn("guardarProducto", null);
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
			} catch (PersistenciaException e) {
				//FIXME ver como manejar esta excep
				e.printStackTrace();
			} finally {
				Conector.closeConn("modificarProducto", null);
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
			} catch (PersistenciaException e) {
				//FIXME ver como manejar esta excep
				e.printStackTrace();
			} finally {
				Conector.closeConn("eliminarProducto", null);
			}
		}
		return resultado;
	}
	
	/*****************************************************************************************************************************************************/
	/** TIPO PROD */
	/*****************************************************************************************************************************************************/
	
	public TipoProd obtenerTipoProdPorId(Integer id) {
		logger.info("Se ingresa a obtenerTipoProdPorId");
		TipoProd tipoProd = null;
		interfaceProducto = new PersistenciaProducto();
		try {
			Conector.getConn();
			tipoProd = interfaceTipoProd.obtenerTipoProdPorId(id);
		} catch (PersistenciaException e) {
			//FIXME ver como manejar esta excep
			e.printStackTrace();
		} finally {
			Conector.closeConn("obtenerTipoProdPorId", null);
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
		} catch (PersistenciaException e) {
			//FIXME ver como manejar esta excep
			e.printStackTrace();
		} finally {
			Conector.closeConn("obtenerListaTipoProd", null);
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
			} catch (PersistenciaException e) {
				//FIXME ver como manejar esta excep
				e.printStackTrace();
			} finally {
				Conector.closeConn("guardarTipoProd", null);
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
			} catch (PersistenciaException e) {
				//FIXME ver como manejar esta excep
				e.printStackTrace();
			} finally {
				Conector.closeConn("modificarTipoProd", null);
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
			} catch (PersistenciaException e) {
				//FIXME ver como manejar esta excep
				e.printStackTrace();
			} finally {
				Conector.closeConn("eliminarTipoProd", null);
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
		} catch (PersistenciaException e) {
			e.printStackTrace();
		} finally {
			Conector.closeConn("obtenerDepositoPorId", null);
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
		} catch (PersistenciaException e) {
			//FIXME ver como manejar esta excep
			e.printStackTrace();
		} finally {
			Conector.closeConn("obtenerListaDeposito", null);
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
			} catch (PersistenciaException e) {
				//FIXME ver como manejar esta excep
				e.printStackTrace();
			} finally {
				Conector.closeConn("guardarDeposito", null);
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
			} catch (PersistenciaException e) {
				//FIXME ver como manejar esta excep
				e.printStackTrace();
			} finally {
				Conector.closeConn("modificarDeposito", null);
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
			} catch (PersistenciaException e) {
				//FIXME ver como manejar esta excep
				e.printStackTrace();
			} finally {	
				Conector.closeConn("eliminarDeposito", null);
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
		} catch (PersistenciaException e) {
			e.printStackTrace();
		} finally {
			Conector.closeConn("obtenerUtilidadPorId", null);
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
		} catch (PersistenciaException e) {
			//FIXME ver como manejar esta excep
			e.printStackTrace();
		} finally {
			Conector.closeConn("obtenerListaUtilidad", null);
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
			} catch (PersistenciaException e) {
				//FIXME ver como manejar esta excep
				e.printStackTrace();
			} finally {
				Conector.closeConn("guardarUtilidad", null);
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
			} catch (PersistenciaException e) {
				//FIXME ver como manejar esta excep
				e.printStackTrace();
			} finally {
				Conector.closeConn("modificarUtilidad", null);
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
			} catch (PersistenciaException e) {
				//FIXME ver como manejar esta excep
				e.printStackTrace();
			} finally {
				Conector.closeConn("eliminarTipoProd", null);
			}
		}
		return resultado;
	}
	
	/*****************************************************************************************************************************************************/
	/** LOTE */
	/*****************************************************************************************************************************************************/
	
	//se deberá obtener los lotes por productos, ordenarlos por fecha venc y restar stocks de ventas a partir de los lotes con vencimiento mas proximo
	//se utilizará un hashmap con claves de fechas, y objetos lotes con un stock sumados
	
	//para los lotes recien ingresados, se deberán actualizar (esto se realizaría cuando se recibiera fisicamente el producto) los lotes con las fechas de venc
	//y la utilidad a ganar a cada lote.
}
