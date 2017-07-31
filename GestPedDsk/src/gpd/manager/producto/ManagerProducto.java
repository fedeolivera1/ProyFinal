package gpd.manager.producto;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.apache.log4j.Logger;

import gpd.db.constantes.CnstQryGeneric;
import gpd.dominio.helper.HlpProducto;
import gpd.dominio.producto.Deposito;
import gpd.dominio.producto.Lote;
import gpd.dominio.producto.Producto;
import gpd.dominio.producto.TipoProd;
import gpd.dominio.producto.Unidad;
import gpd.dominio.producto.Utilidad;
import gpd.dominio.transaccion.TranLinea;
import gpd.dominio.transaccion.TranLineaLote;
import gpd.dominio.util.Converters;
import gpd.dominio.util.Estado;
import gpd.dominio.util.Sinc;
import gpd.exceptions.PersistenciaException;
import gpd.exceptions.PresentacionException;
import gpd.exceptions.ProductoSinStockException;
import gpd.interfaces.producto.IPersDeposito;
import gpd.interfaces.producto.IPersLote;
import gpd.interfaces.producto.IPersProducto;
import gpd.interfaces.producto.IPersTipoProd;
import gpd.interfaces.producto.IPersUnidad;
import gpd.interfaces.producto.IPersUtilidad;
import gpd.interfaces.transaccion.IPersTranLinea;
import gpd.interfaces.transaccion.IPersTransaccion;
import gpd.persistencia.conector.Conector;
import gpd.persistencia.producto.PersistenciaDeposito;
import gpd.persistencia.producto.PersistenciaLote;
import gpd.persistencia.producto.PersistenciaProducto;
import gpd.persistencia.producto.PersistenciaTipoProd;
import gpd.persistencia.producto.PersistenciaUnidad;
import gpd.persistencia.producto.PersistenciaUtilidad;
import gpd.persistencia.transaccion.PersistenciaTranLinea;
import gpd.persistencia.transaccion.PersistenciaTransaccion;
import gpd.types.Fecha;
import gpd.util.ConfigDriver;

public class ManagerProducto {

	private static final Logger logger = Logger.getLogger(ManagerProducto.class);
	private static IPersProducto interfaceProducto;
	private static IPersTipoProd interfaceTipoProd;
	private static IPersUnidad interfaceUnidad;
	private static IPersDeposito interfaceDeposito;
	private static IPersUtilidad interfaceUtilidad;
	private static IPersLote interfaceLote;
	private static IPersTransaccion interfaceTransac;
	private static IPersTranLinea interfaceTranLinea;
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
	private static IPersUnidad getInterfaceUnidad() {
		if(interfaceUnidad == null) {
			interfaceUnidad = new PersistenciaUnidad();
		}
		return interfaceUnidad;
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
	private static IPersTransaccion getInterfaceTransac() {
		if(interfaceTransac == null) {
			interfaceTransac = new PersistenciaTransaccion();
		}
		return interfaceTransac;
	}
	private static IPersTranLinea getInterfaceTranLinea() {
		if(interfaceTranLinea == null) {
			interfaceTranLinea = new PersistenciaTranLinea();
		}
		return interfaceTranLinea;
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
			Conector.closeConn("obtenerBusquedaProducto");
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
			Conector.closeConn("obtenerUsuario");
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
			Conector.closeConn("obtenerListaProductoPorTipoProd");
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
				Conector.closeConn("guardarProducto");
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
				Conector.closeConn("modificarProducto");
			} catch (PersistenciaException e) {
				logger.fatal("Excepcion en ManagerProducto > modificarProducto: " + e.getMessage(), e);
				throw new PresentacionException(e);
			}
		}
		return resultado;
	}
	
	public Integer desactivarProducto(Producto producto) throws PresentacionException {
		logger.info("Se ingresa a eliminarProducto");
		if(producto != null) {
			try {
				Conector.getConn();
				producto.setSinc(Sinc.N);
				producto.setUltAct(new Fecha(Fecha.AMDHMS));
				resultado = getInterfaceProducto().desactivarProducto(producto);
				Conector.closeConn("eliminarProducto");
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
			Conector.closeConn("obtenerTipoProdPorId");
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
			Conector.closeConn("obtenerListaTipoProd");
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
				tipoProd.setSinc(Sinc.N);
				tipoProd.setEstado(Estado.A);
				resultado = getInterfaceTipoProd().guardarTipoProd(tipoProd);
				Conector.closeConn("guardarTipoProd");
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
				tipoProd.setSinc(Sinc.N);
				resultado = getInterfaceTipoProd().modificarTipoProd(tipoProd);
				Conector.closeConn("modificarTipoProd");
			} catch (PersistenciaException e) {
				logger.fatal("Excepcion en ManagerProducto > modificarTipoProd: " + e.getMessage(), e);
				throw new PresentacionException(e);
			}
		}
		return resultado;
	}
	
	public Boolean eliminarTipoProd(TipoProd tipoProd) throws PresentacionException {
		logger.info("Se ingresa a eliminarTipoProd");
		if(tipoProd != null) {
			try {
				Conector.getConn();
				if(!getInterfaceTipoProd().controlUtilTipoProd(tipoProd)) {
					tipoProd.setSinc(Sinc.N);
					tipoProd.setEstado(Estado.E);
					resultado = getInterfaceTipoProd().eliminarTipoProd(tipoProd);
					Conector.closeConn("eliminarTipoProd");
					return true;
				}
			} catch (PersistenciaException e) {
				logger.fatal("Excepcion en ManagerProducto > eliminarTipoProd: " + e.getMessage(), e);
				throw new PresentacionException(e);
			}
		}
		return false;
	}
	
	/*****************************************************************************************************************************************************/
	/** UNIDAD */
	/*****************************************************************************************************************************************************/

	public Unidad obtenerUnidadPorId(Integer id) throws PresentacionException {
		logger.info("Se ingresa a obtenerUnidadPorId");
		Unidad unidad = null;
		try {
			Conector.getConn();
			unidad = getInterfaceUnidad().obtenerUnidadPorId(id);
			Conector.closeConn("obtenerUnidadPorId");
		} catch (PersistenciaException e) {
			logger.fatal("Excepcion en ManagerProducto > obtenerUnidadPorId: " + e.getMessage(), e);
			throw new PresentacionException(e);
		}
		return unidad;
	}
	
	public List<Unidad> obtenerListaUnidad() throws PresentacionException {
		logger.info("Se ingresa a obtenerListaUnidad");
		List<Unidad> listaUnidad = null;
		try {
			Conector.getConn();
			listaUnidad = getInterfaceUnidad().obtenerListaUnidad();
			Conector.closeConn("obtenerListaUnidad");
		} catch (PersistenciaException e) {
			logger.fatal("Excepcion en ManagerProducto > obtenerListaUnidad: " + e.getMessage(), e);
			throw new PresentacionException(e);
		}
		return listaUnidad;
	}
	
	public Integer guardarUnidad(Unidad unidad) throws PresentacionException {
		logger.info("Se ingresa a guardarUnidad");
		if(unidad != null) {
			try {
				Conector.getConn();
				unidad.setSinc(Sinc.N);
				unidad.setEstado(Estado.A);
				resultado = getInterfaceUnidad().guardarUnidad(unidad);
				Conector.closeConn("guardarUnidad");
			} catch (PersistenciaException e) {
				logger.fatal("Excepcion en ManagerProducto > guardarUnidad: " + e.getMessage(), e);
				throw new PresentacionException(e);
			}
		}
		return resultado;
	}
	
	public Integer modificarUnidad(Unidad unidad) throws PresentacionException {
		logger.info("Ingresa modificarUnidad");
		if(unidad != null) {
			try {
				Conector.getConn();
				unidad.setSinc(Sinc.N);
				resultado = getInterfaceUnidad().modificarUnidad(unidad);
				Conector.closeConn("modificarUnidad");
			} catch (PersistenciaException e) {
				logger.fatal("Excepcion en ManagerProducto > modificarUnidad: " + e.getMessage(), e);
				throw new PresentacionException(e);
			}
		}
		return resultado;
	}
	
	//FIXME ver si este metodo es necesario
//	public Integer modificarSincUnidad(Integer idUnidad, Sinc sinc) throws PresentacionException {
//		logger.info("Ingresa modificarSincUnidad");
//		try {
//			Conector.getConn();
//			resultado = getInterfaceUnidad().modificarSincUnidad(idUnidad, sinc);
//			Conector.closeConn("modificarSincUnidad");
//		} catch (PersistenciaException e) {
//			logger.fatal("Excepcion en ManagerProducto > modificarSincUnidad: " + e.getMessage(), e);
//			throw new PresentacionException(e);
//		}
//		return resultado;
//	}
	
	public Boolean eliminarUnidad(Unidad unidad) throws PresentacionException {
		logger.info("Se ingresa a eliminarUnidad");
		if(unidad != null) {
			try {
				Conector.getConn();
				if(!getInterfaceUnidad().controlUtilUnidad(unidad)) {
					unidad.setSinc(Sinc.N);
					unidad.setEstado(Estado.E);
					resultado = getInterfaceUnidad().eliminarUnidad(unidad);
					Conector.closeConn("eliminarUnidad");
					return true;
				} 
			} catch (PersistenciaException e) {
				logger.fatal("Excepcion en ManagerProducto > eliminarUnidad: " + e.getMessage(), e);
				throw new PresentacionException(e);
			}
		}
		return false;
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
			Conector.closeConn("obtenerDepositoPorId");
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
			Conector.closeConn("obtenerListaDeposito");
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
				Conector.closeConn("guardarDeposito");
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
				Conector.closeConn("modificarDeposito");
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
				Conector.closeConn("eliminarDeposito");
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
			Conector.closeConn("obtenerUtilidadPorId");
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
			Conector.closeConn("obtenerListaUtilidad");
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
				Conector.closeConn("guardarUtilidad");
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
				Conector.closeConn("modificarUtilidad");
			} catch (PersistenciaException e) {
				logger.fatal("Excepcion en ManagerProducto > modificarUtilidad: " + e.getMessage(), e);
				throw new PresentacionException(e);
			}
		}
		return resultado;
	}

	public Integer eliminarUtilidad(Utilidad utilidad) throws PresentacionException {
		logger.info("Se ingresa a eliminarUtilidad");
		if(utilidad != null) {
			try {
				Conector.getConn();
				resultado = getInterfaceUtilidad().eliminarUtilidad(utilidad);
				Conector.closeConn("eliminarTipoProd");
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
	
	/**
	 * metodo que a partir de una transaccion y un producto devuelve el lote asociado a dicha transaccion 
	 * @param nroTransac
	 * @param idProducto
	 * @param diasTol
	 * @return
	 * @throws PresentacionException
	 * METODO NO CONN: NO ABRE NI CIERRA CONEXIONES, DEBE SER LLAMADO DESDE METODO CONTENEDOR DE CONEXION
	 */
	public Lote obtenerLoteVtaPorTransacProdNoConn(Integer nroTransac, Integer idProducto) throws PersistenciaException {
		logger.info("Se ingresa a obtenerLotePorTransacProd");
		Lote lote = null;
		try {
			lote = getInterfaceLote().obtenerLoteVtaPorTransacProd(nroTransac, idProducto);
		} catch (PersistenciaException e) {
			logger.fatal("Excepcion en ManagerProducto > obtenerListaLotePorTransac: " + e.getMessage(), e);
			throw e;
		}
		return lote;
	}
	
	public List<Lote> obtenerListaLotePorTransac(Integer nroTransac) throws PresentacionException {
		logger.info("Se ingresa a obtenerListaLotePorTransac");
		List<Lote> listaLote = null;
		try {
			Conector.getConn();
			listaLote = getInterfaceLote().obtenerListaLotePorTransac(nroTransac);
			Conector.closeConn("obtenerListaLotePorTransac");
		} catch (PersistenciaException e) {
			logger.fatal("Excepcion en ManagerProducto > obtenerListaLotePorTransac: " + e.getMessage(), e);
			throw new PresentacionException(e);
		}
		return listaLote;
	}
	
	/**
	 * metodo invocador de 'obtenerStockPrecioLotePorProductoNoConn' que contiene conexiones para
	 * ser invocado y manejar la conexion
	 * @param idProducto
	 * @return
	 * @throws PresentacionException
	 */
	public HlpProducto obtenerStockPrecioLotePorProducto(Integer idProducto) throws PresentacionException {
		HlpProducto hlpProd = null;
		try {
			Conector.getConn();
			hlpProd = obtenerStockPrecioLotePorProductoNoConn(idProducto);
			Conector.closeConn("obtenerStockPrecioLotePorProducto");
		} catch (PresentacionException e) {
			throw e;
		}
		return hlpProd;
	}
	
	/**
	 * metodo que recibe un producto (el cual será seleccionado en presentacion), y va a obtener los lotes
	 * que tengan stock y que cumplan con la condicion de el vencimiento (defierencia de dias seteado
	 * en config.properties). Para cada uno, se calculará el stock total y se obtendrá el precio de compra 
	 * mas alto (para casos remotos de diferencia de precios entre compras).
	 * >> metodo de precio: se aplicará sobre el precio del producto, el porcentaje de utilidad de ganancia, y sobre este calculo, se
	 * le adicionará el iva que corresponda.
	 * METODO NO CONN: NO ABRE NI CIERRA CONEXIONES, DEBE SER LLAMADO DESDE METODO CONTENEDOR DE CONEXION
	 * @param idProducto
	 * @return HlpProducto
	 * @throws PresentacionException
	 */
	public HlpProducto obtenerStockPrecioLotePorProductoNoConn(Integer idProducto) throws PresentacionException {
		logger.info("Se ingresa a obtenerStockPrecioLotePorProductoNoConn");
		HlpProducto hlpProd = null;
		try {
			ConfigDriver cfgDrv = ConfigDriver.getConfigDriver();
			Integer diasParaVenc = Integer.valueOf(cfgDrv.getDiasParaVenc());
			List<Lote> listaLote = getInterfaceLote().obtenerListaLotePorProd(idProducto, diasParaVenc);
			Double precioFinal = new Double(0);
			Long stock = new Long(0);
			if(listaLote != null && !listaLote.isEmpty()) {
				for(Lote lote : listaLote) {
					Producto prod = lote.getTranLinea().getProducto();
					Double precioConUtil = lote.getTranLinea().getPrecioUnit() * Converters.convertirPorcAMult(lote.getUtilidad().getPorc());
					precioConUtil = Converters.redondearDosDec(precioConUtil);
					//obtengo coeficiente IVA a partir del producto para sumarle al precio con ganancia
					Float coefIva = Float.valueOf(cfgDrv.getIva(prod.getAplIva().getAplIvaProp()));
					coefIva = Converters.convertirPorcAMult(coefIva);
					precioConUtil = coefIva > 0 ? (precioConUtil * coefIva) : precioConUtil;
					precioConUtil = Converters.redondearDosDec(precioConUtil);
					stock += lote.getStock();
					if(precioConUtil > precioFinal) {
						precioFinal = precioConUtil;
					}
				}
				hlpProd = new HlpProducto();
				hlpProd.setIdProducto(idProducto);
				hlpProd.setStock(stock);
				hlpProd.setPrecioVta(precioFinal);
			}
		} catch (PersistenciaException e) {
			logger.fatal("Excepcion en ManagerProducto > obtenerStockPrecioLotePorProductoNoConn: " + e.getMessage(), e);
			throw new PresentacionException(e);
		} catch (Exception e) {
			logger.fatal("Excepcion no controlada en ManagerProducto > obtenerStockPrecioLotePorProductoNoConn: " + e.getMessage(), e);
			throw new PresentacionException(e);
		}
		return hlpProd;
	}

	/**
	 * metodo que maneja el stock por lotes dependiendo del vencimiento
	 * no maneja conexiones ya que debería ser llamado desde otro manager
	 * METODO NO CONN: NO ABRE NI CIERRA CONEXIONES, DEBE SER LLAMADO DESDE METODO CONTENEDOR DE CONEXION
	 * @param idProducto
	 * @param cantidad
	 * @throws PresentacionException
	 */
	public void manejarStockLotePorProductoNoConn(Integer nroTransac, Integer idProducto, Integer cantidad) throws PresentacionException, ProductoSinStockException {
		logger.info("Se ingresa a manejarStockLotePorProducto: cantidad inicial del pedido: " + cantidad);
		List<Lote> listaLote = null;
		try {
			ConfigDriver cfgDrv = ConfigDriver.getConfigDriver();
			Integer diasParaVenc = Integer.valueOf(cfgDrv.getDiasParaVenc());
			/*
			 * vuelvo a comprobar cantidad de stock para el producto, en caso de no haber suficientes, corto la ejecucion
			 * con una excepcion del tipo ProductoSinStockException
			 */
			HlpProducto hlpProd = obtenerStockPrecioLotePorProductoNoConn(idProducto);
			if(hlpProd == null || hlpProd.getStock() < cantidad) {
				throw new ProductoSinStockException("--Stock insuficiente para el producto: " + idProducto + " !!!");
			}
			listaLote = getInterfaceLote().obtenerListaLotePorProd(idProducto, diasParaVenc);
			logger.info("# Cantidad de lotes para el producto: " + listaLote.size());
			HashMap<Long, List<Lote>> mapLotes = new HashMap<>();
			for(Lote lote : listaLote) {
				Long venc = lote.getVenc().getAsNumber(Fecha.AMD);
				if(mapLotes.containsKey(venc)) {
					mapLotes.get(venc).add(lote);
				} else {
					ArrayList<Lote> listaPorVenc = new ArrayList<>();
					listaPorVenc.add(lote);
					mapLotes.put(venc, listaPorVenc);
				}
			}
			
			Map<Long, List<Lote>> sortedMapLotes = new TreeMap<Long, List<Lote>>(mapLotes);
			List<TranLineaLote> listaTll = new ArrayList<>();
			
			Integer restanteActStock = cantidad;
			for (Map.Entry<Long, List<Lote>> entry : sortedMapLotes.entrySet()) {
				List<Lote> listaPorVenc = entry.getValue();
				logger.info("# Ingreso a verificar lotes, fecha vencimiento (aaaammdd) de lotes a verificar: " + entry.getKey().longValue());
				logger.info("# cant de lotes: " + listaPorVenc.size());
				//recorro cada uno de los lotes [COMPRADOS] con misma fecha de vencimiento
				for(Lote lote : listaPorVenc) {
					//obtengo la linea de VENTA
					TranLinea tl = getInterfaceTranLinea().obtenerTranLineaPorId(nroTransac, idProducto);
					TranLineaLote tll = new TranLineaLote(tl, lote, 0);
					if(lote.getStock() > restanteActStock) {
						logger.info(" >> El lote: " + lote.getIdLote() + " tiene stock suficiente para el producto: " + idProducto);
						/*
						 * agrego a tabla de control la cantidad que se le resta al lote >>
						 * esta tabla guarda para cada venta, el lote el cual provee las unidades 
						 * actualizo lote restando cantidad a bajar
						 */
						tll.setCantidad(restanteActStock);
						listaTll.add(tll);
						Integer nuevoStockLote = lote.getStock() - restanteActStock;
						restanteActStock = 0;
						getInterfaceLote().actualizarStockLote(lote.getIdLote(), nuevoStockLote);
						break;
					} else {
						logger.info(" >> El lote: " + lote.getIdLote() + " utlizará su stock restante [" + lote.getStock() + " unidades] para "
								+ " el producto: " + idProducto);
						restanteActStock -= lote.getStock();
						/*
						 * agrego a tabla de control la cantidad que se le resta al lote >>
						 * esta tabla guarda para cada venta, el lote el cual provee las unidades 
						 * agrego a tabla de control la cantidad restante del lote
						 */
						tll.setCantidad(lote.getStock());
						listaTll.add(tll);
						/*
						 * actualizo lote pasando stock de lote a 0 
						 * y busco en siguiente lote
						 */
						getInterfaceLote().actualizarStockLote(lote.getIdLote(), 0);
					}
				}
				//corto si ya no hay restante a actualizar (finalizo la actualizacion de lote para el prod)
				if(restanteActStock == 0) break;
			}
			//se manda a persistir lista con datos de stock por lotes
			getInterfaceTransac().guardarListaTranLineaLote(listaTll);
		} catch (PersistenciaException e) {
			logger.fatal("Excepcion en ManagerProducto > manejarStockLotePorProducto: " + e.getMessage(), e);
			throw new PresentacionException(e);
		} catch (Exception e) {
			logger.fatal("Excepcion no controlada en ManagerProducto > manejarStockLotePorProducto: " + e.getMessage(), e);
			throw new PresentacionException(e);
		}
//		return mapLotes;
	}
	
//	public Lote obtenerLotePorId(Integer idLote) {
//		logger.info("Se ingresa a obtenerLotePorId");
//		try {
//			Conector.getConn();
//			Conector.closeConn("obtenerLotePorId");
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
//			Conector.closeConn("actualizarLote");
//		} catch (PersistenciaException e) {
//			e.printStackTrace();
//		}
//		return resultado;
//	}
	
	
}
