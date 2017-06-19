package gpd.manager.producto;

import java.util.List;

import org.apache.log4j.Logger;

import gpd.db.constantes.CnstQryGeneric;
import gpd.dominio.producto.Deposito;
import gpd.dominio.producto.Lote;
import gpd.dominio.producto.Producto;
import gpd.dominio.producto.TipoProd;
import gpd.dominio.producto.Utilidad;
import gpd.dominio.util.Sinc;
import gpd.exceptions.PersistenciaException;
import gpd.exceptions.PresentacionException;
import gpd.interfaces.producto.IPersDeposito;
import gpd.interfaces.producto.IPersLote;
import gpd.interfaces.producto.IPersProducto;
import gpd.interfaces.producto.IPersTipoProd;
import gpd.interfaces.producto.IPersUtilidad;
import gpd.persistencia.conector.Conector;
import gpd.persistencia.producto.PersistenciaDeposito;
import gpd.persistencia.producto.PersistenciaLote;
import gpd.persistencia.producto.PersistenciaProducto;
import gpd.persistencia.producto.PersistenciaTipoProd;
import gpd.persistencia.producto.PersistenciaUtilidad;
import gpd.types.Fecha;

public class ManagerProducto {

	private static final Logger logger = Logger.getLogger(ManagerProducto.class);
	private static IPersProducto interfaceProducto;
	private static IPersTipoProd interfaceTipoProd;
	private static IPersDeposito interfaceDeposito;
	private static IPersUtilidad interfaceUtilidad;
	private static IPersLote interfaceLote;
	private Integer resultado;
	
	
	private static IPersProducto getInterfaceProducto() {
		if(interfaceProducto == null) {
			interfaceProducto = new PersistenciaProducto();
		}
		return interfaceProducto;
	}
	private static IPersTipoProd getInterfaceTipoProd() {
		if(interfaceTipoProd == null) {
			interfaceTipoProd = new PersistenciaTipoProd();
		}
		return interfaceTipoProd;
	}
	private static IPersDeposito getInterfaceDeposito() {
		if(interfaceDeposito == null) {
			interfaceDeposito = new PersistenciaDeposito();
		}
		return interfaceDeposito;
	}
	private static IPersUtilidad getInterfaceUtilidad() {
		if(interfaceUtilidad == null) {
			interfaceUtilidad = new PersistenciaUtilidad();
		}
		return interfaceUtilidad;
	}
	private static IPersLote getInterfaceLote() {
		if(interfaceLote == null) {
			interfaceLote = new PersistenciaLote();
		}
		return interfaceLote;
	}
	
	/*****************************************************************************************************************************************************/
	/** PRODUCTO */
	/*****************************************************************************************************************************************************/
	
	public List<Producto> obtenerBusquedaProducto(TipoProd tipoProd, String codigo, String nombre, String descripcion) throws PresentacionException {
		logger.info("Ingresa obtenerBusquedaProducto");
		List<Producto> listaProd = null;
		try {
			Conector.getConn();
			Integer idTipoProd = (tipoProd != null ? tipoProd.getIdTipoProd() : CnstQryGeneric.NUMBER_INVALID);
			listaProd = getInterfaceProducto().obtenerBusquedaProducto(idTipoProd, codigo, nombre, descripcion);
			Conector.closeConn("obtenerBusquedaProducto", null);
		} catch (PersistenciaException e) {
			logger.fatal("Excepcion en ManagerProducto > obtenerBusquedaProducto: " + e.getMessage(), e);
			throw new PresentacionException(e);
		}
		return listaProd;
	}
	
	public Producto obtenerProductoPorId(Integer id) throws PresentacionException {
		logger.info("Se ingresa a obtenerProductoPorId");
		Producto producto = null;
		try {
			Conector.getConn();
			producto = getInterfaceProducto().obtenerProductoPorId(id);
			Conector.closeConn("obtenerUsuario", null);
		} catch (PersistenciaException e) {
			logger.fatal("Excepcion en ManagerProducto > obtenerProductoPorId: " + e.getMessage(), e);
			throw new PresentacionException(e);
		}
		return producto;
	}
	
	public List<Producto> obtenerListaProductoPorTipoProd(TipoProd tipoProd) throws PresentacionException {
		logger.info("Se ingresa a obtenerListaProductoPorTipoProd");
		List<Producto> listaProducto = null;
		try {
			Conector.getConn();
			listaProducto = getInterfaceProducto().obtenerListaProductoPorTipo(tipoProd);
			Conector.closeConn("obtenerListaProductoPorTipoProd", null);
		} catch (PersistenciaException e) {
			logger.fatal("Excepcion en ManagerProducto > obtenerListaProductoPorTipoProd: " + e.getMessage(), e);
			throw new PresentacionException(e);
		}
		return listaProducto;
	}
	
	public Integer guardarProducto(Producto producto) throws PresentacionException {
		logger.info("Se ingresa a guardarProducto");
		if(producto != null) {
			try {
				producto.setSinc(Sinc.N);
				producto.setUltAct(new Fecha(Fecha.AMDHMS));
				Conector.getConn();
				resultado = getInterfaceProducto().guardarProducto(producto);
				Conector.closeConn("guardarProducto", null);
			} catch (PersistenciaException e) {
				logger.fatal("Excepcion en ManagerProducto > guardarProducto: " + e.getMessage(), e);
				throw new PresentacionException(e);
			}
		}
		return resultado;
	}
	
	public Integer modificarProducto(Producto producto) throws PresentacionException {
		logger.info("Se ingresa a modificarProducto");
		if(producto != null) {
			try {
				producto.setSinc(Sinc.N);
				producto.setUltAct(new Fecha(Fecha.AMDHMS));
				Conector.getConn();
				resultado = getInterfaceProducto().modificarProducto(producto);
				Conector.closeConn("modificarProducto", null);
			} catch (PersistenciaException e) {
				logger.fatal("Excepcion en ManagerProducto > modificarProducto: " + e.getMessage(), e);
				throw new PresentacionException(e);
			}
		}
		return resultado;
	}
	
	public Integer eliminarProducto(Producto producto) throws PresentacionException {
		logger.info("Se ingresa a eliminarProducto");
		if(producto != null) {
			try {
				Conector.getConn();
				resultado = getInterfaceProducto().eliminarProducto(producto);
				Conector.closeConn("eliminarProducto", null);
			} catch (PersistenciaException e) {
				logger.fatal("Excepcion en ManagerProducto > eliminarProducto: " + e.getMessage(), e);
				throw new PresentacionException(e);
			}
		}
		return resultado;
	}
	
	
	/*****************************************************************************************************************************************************/
	/** TIPO PROD */
	/*****************************************************************************************************************************************************/
	
	public TipoProd obtenerTipoProdPorId(Integer id) throws PresentacionException {
		logger.info("Se ingresa a obtenerTipoProdPorId");
		TipoProd tipoProd = null;
		try {
			Conector.getConn();
			tipoProd = getInterfaceTipoProd().obtenerTipoProdPorId(id);
			Conector.closeConn("obtenerTipoProdPorId", null);
		} catch (PersistenciaException e) {
			logger.fatal("Excepcion en ManagerProducto > obtenerTipoProdPorId: " + e.getMessage(), e);
			throw new PresentacionException(e);
		}
		return tipoProd;
	}
	
	public List<TipoProd> obtenerListaTipoProd() throws PresentacionException {
		logger.info("Se ingresa a obtenerListaTipoProd");
		List<TipoProd> listaTipoProd = null;
		try {
			Conector.getConn();
			listaTipoProd = getInterfaceTipoProd().obtenerListaTipoProd();
			Conector.closeConn("obtenerListaTipoProd", null);
		} catch (PersistenciaException e) {
			logger.fatal("Excepcion en ManagerProducto > obtenerListaTipoProd: " + e.getMessage(), e);
			throw new PresentacionException(e);
		}
		return listaTipoProd;
	}
	
	public Integer guardarTipoProd(TipoProd tipoProd) throws PresentacionException {
		logger.info("Se ingresa a guardarTipoProd");
		if(tipoProd != null) {
			try {
				Conector.getConn();
				resultado = getInterfaceTipoProd().guardarTipoProd(tipoProd);
				Conector.closeConn("guardarTipoProd", null);
			} catch (PersistenciaException e) {
				logger.fatal("Excepcion en ManagerProducto > guardarTipoProd: " + e.getMessage(), e);
				throw new PresentacionException(e);
			}
		}
		return resultado;
	}
	
	public Integer modificarTipoProd(TipoProd tipoProd) throws PresentacionException {
		logger.info("Ingresa modificarTipoProd");
		if(tipoProd != null) {
			try {
				Conector.getConn();
				resultado = getInterfaceTipoProd().modificarTipoProd(tipoProd);
				Conector.closeConn("modificarTipoProd", null);
			} catch (PersistenciaException e) {
				logger.fatal("Excepcion en ManagerProducto > modificarTipoProd: " + e.getMessage(), e);
				throw new PresentacionException(e);
			}
		}
		return resultado;
	}
	
	public Integer eliminarTipoProd(TipoProd tipoProd) throws PresentacionException {
		logger.info("Se ingresa a eliminarTipoProd");
		if(tipoProd != null) {
			try {
				Conector.getConn();
				resultado = getInterfaceTipoProd().eliminarTipoProd(tipoProd);
				Conector.closeConn("eliminarTipoProd", null);
			} catch (PersistenciaException e) {
				logger.fatal("Excepcion en ManagerProducto > eliminarTipoProd: " + e.getMessage(), e);
				throw new PresentacionException(e);
			}
		}
		return resultado;
	}

	/*****************************************************************************************************************************************************/
	/** DEPOSITO */
	/*****************************************************************************************************************************************************/
	
	public Deposito obtenerDepositoPorId(Integer id) throws PresentacionException {
		logger.info("Se ingresa a obtenerDepositoPorId");
		Deposito deposito = null;
		try {
			Conector.getConn();
			deposito = getInterfaceDeposito().obtenerDepositoPorId(id);
			Conector.closeConn("obtenerDepositoPorId", null);
		} catch (PersistenciaException e) {
			logger.fatal("Excepcion en ManagerProducto > obtenerDepositoPorId: " + e.getMessage(), e);
			throw new PresentacionException(e);
		}
		return deposito;
	}
	
	public List<Deposito> obtenerListaDeposito() throws PresentacionException {
		logger.info("Se ingresa a obtenerListaDeposito");
		List<Deposito> listaDeposito = null;
		try {
			Conector.getConn();
			listaDeposito = getInterfaceDeposito().obtenerListaDeposito();
			Conector.closeConn("obtenerListaDeposito", null);
		} catch (PersistenciaException e) {
			logger.fatal("Excepcion en ManagerProducto > obtenerListaDeposito: " + e.getMessage(), e);
			throw new PresentacionException(e);
		}
		return listaDeposito;
	}

	public Integer guardarDeposito(Deposito deposito) throws PresentacionException {
		logger.info("Se ingresa a guardarDeposito");
		if(deposito != null) {
			try {
				Conector.getConn();
				resultado = getInterfaceDeposito().guardarDeposito(deposito);
				Conector.closeConn("guardarDeposito", null);
			} catch (PersistenciaException e) {
				logger.fatal("Excepcion en ManagerProducto > guardarDeposito: " + e.getMessage(), e);
				throw new PresentacionException(e);
			}
		}
		return resultado;
	}

	public Integer modificarDeposito(Deposito deposito) throws PresentacionException {
		logger.info("Ingresa modificarDeposito");
		if(deposito != null) {
			try {
				Conector.getConn();
				resultado = getInterfaceDeposito().modificarDeposito(deposito);
				Conector.closeConn("modificarDeposito", null);
			} catch (PersistenciaException e) {
				logger.fatal("Excepcion en ManagerProducto > modificarDeposito: " + e.getMessage(), e);
				throw new PresentacionException(e);
			}
		}
		return resultado;
	}

	public Integer eliminarDeposito(Deposito deposito) throws PresentacionException {
		logger.info("Se ingresa a eliminarDeposito");
		if(deposito != null) {
			try {
				Conector.getConn();
				resultado = getInterfaceDeposito().eliminarDeposito(deposito);
				Conector.closeConn("eliminarDeposito", null);
			} catch (PersistenciaException e) {
				logger.fatal("Excepcion en ManagerProducto > eliminarDeposito: " + e.getMessage(), e);
				throw new PresentacionException(e);
			}
		}
		return resultado;
	}
	
	/*****************************************************************************************************************************************************/
	/** UTILIDAD */
	/*****************************************************************************************************************************************************/
	
	public Utilidad obtenerUtilidadPorId(Integer id) throws PresentacionException {
		logger.info("Se ingresa a obtenerUtilidadPorId");
		Utilidad utilidad = null;
		try {
			Conector.getConn();
			utilidad = getInterfaceUtilidad().obtenerUtilidadPorId(id);
			Conector.closeConn("obtenerUtilidadPorId", null);
		} catch (PersistenciaException e) {
			logger.fatal("Excepcion en ManagerProducto > obtenerUtilidadPorId: " + e.getMessage(), e);
			throw new PresentacionException(e);
		}
		return utilidad;
	}
	
	public List<Utilidad> obtenerListaUtilidad() throws PresentacionException {
		logger.info("Se ingresa a obtenerListaUtilidad");
		List<Utilidad> listaUtilidad = null;
		try {
			Conector.getConn();
			listaUtilidad = getInterfaceUtilidad().obtenerListaUtilidad();
			Conector.closeConn("obtenerListaUtilidad", null);
		} catch (PersistenciaException e) {
			logger.fatal("Excepcion en ManagerProducto > obtenerListaUtilidad: " + e.getMessage(), e);
			throw new PresentacionException(e);
		}
		return listaUtilidad;
	}

	public Integer guardarUtilidad(Utilidad utilidad) throws PresentacionException {
		logger.info("Se ingresa a guardarUtilidad");
		if(utilidad != null) {
			try {
				Conector.getConn();
				resultado = getInterfaceUtilidad().guardarUtilidad(utilidad);
				Conector.closeConn("guardarUtilidad", null);
			} catch (PersistenciaException e) {
				logger.fatal("Excepcion en ManagerProducto > guardarUtilidad: " + e.getMessage(), e);
				throw new PresentacionException(e);
			}
		}
		return resultado;
	}

	public Integer modificarUtilidad(Utilidad utilidad) throws PresentacionException {
		logger.info("Ingresa modificarUtilidad");
		if(utilidad != null) {
			try {
				Conector.getConn();
				resultado = getInterfaceUtilidad().modificarUtilidad(utilidad);
				Conector.closeConn("modificarUtilidad", null);
			} catch (PersistenciaException e) {
				logger.fatal("Excepcion en ManagerProducto > modificarUtilidad: " + e.getMessage(), e);
				throw new PresentacionException(e);
			}
		}
		return resultado;
	}

	public Integer eliminarUtilidad(Utilidad utilidad) throws PresentacionException {
		logger.info("Se ingresa a eliminarTipoProd");
		if(utilidad != null) {
			try {
				Conector.getConn();
				resultado = getInterfaceUtilidad().eliminarUtilidad(utilidad);
				Conector.closeConn("eliminarTipoProd", null);
			} catch (PersistenciaException e) {
				logger.fatal("Excepcion en ManagerProducto > eliminarUtilidad: " + e.getMessage(), e);
				throw new PresentacionException(e);
			}
		}
		return resultado;
	}
	
	/*****************************************************************************************************************************************************/
	/** LOTE */
	/*****************************************************************************************************************************************************/
	//se deberá obtener los lotes por producto, ordenarlos por fecha venc y restar stocks de ventas a partir de los lotes con vencimiento mas proximo
	//se utilizará un hashmap con claves de fechas, y objetos lotes con un stock sumados
	
	//para los lotes recien ingresados, se deberán actualizar (esto se realizaría cuando se recibiera fisicamente el producto) los lotes con las fechas de venc
	//y la utilidad a ganar a cada lote.
	
	public List<Lote> obtenerListaLotePorTransac(Long nroTransac) throws PresentacionException {
		logger.info("Se ingresa a obtenerListaLotePorTransac");
		List<Lote> listaLote = null;
		try {
			Conector.getConn();
			listaLote = getInterfaceLote().obtenerListaLotePorTransac(nroTransac);
			Conector.closeConn("obtenerListaLotePorTransac", null);
		} catch (PersistenciaException e) {
			logger.fatal("Excepcion en ManagerProducto > obtenerListaLotePorTransac: " + e.getMessage(), e);
			throw new PresentacionException(e);
		}
		return listaLote;
	}

//	public Lote obtenerLotePorId(Integer idLote) {
//		logger.info("Se ingresa a obtenerLotePorId");
//		try {
//			Conector.getConn();
//			Conector.closeConn("obtenerLotePorId", null);
//		} catch (PersistenciaException e) {
//			e.printStackTrace();
//		}
//		return null;
//	}
	
//	public Integer actualizarLote(Lote lote) {
//		logger.info("Se ingresa a actualizarLote");
//		try {
//			Conector.getConn();
//			resultado = getInterfaceLote().actualizarLote(lote);
//			Conector.closeConn("actualizarLote", null);
//		} catch (PersistenciaException e) {
//			e.printStackTrace();
//		}
//		return resultado;
//	}
	
	
}
