package gpd.manager.transaccion;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;

import gpd.db.constantes.CnstQryTransaccion;
import gpd.dominio.producto.Lote;
import gpd.dominio.producto.Producto;
import gpd.dominio.transaccion.EstadoTran;
import gpd.dominio.transaccion.TipoTran;
import gpd.dominio.transaccion.TranLinea;
import gpd.dominio.transaccion.TranLineaLote;
import gpd.dominio.transaccion.Transaccion;
import gpd.dominio.util.Converters;
import gpd.exceptions.ConectorException;
import gpd.exceptions.PersistenciaException;
import gpd.exceptions.PresentacionException;
import gpd.interfaces.producto.IPersLote;
import gpd.interfaces.transaccion.IPersTranLinea;
import gpd.interfaces.transaccion.IPersTransaccion;
import gpd.manager.producto.ManagerProducto;
import gpd.persistencia.conector.Conector;
import gpd.persistencia.producto.PersistenciaLote;
import gpd.persistencia.transaccion.PersistenciaTranLinea;
import gpd.persistencia.transaccion.PersistenciaTransaccion;
import gpd.types.ErrorLogico;
import gpd.types.Fecha;
import gpd.util.ConfigDriver;

public class ManagerTransaccion {

	private static final Logger logger = Logger.getLogger(ManagerTransaccion.class);
	private static IPersTransaccion interfaceTransaccion;
	private static IPersTranLinea interfaceTranLinea;
	private static IPersLote interfaceLote;
	
	private static IPersTransaccion getInterfaceTransaccion() {
		if(interfaceTransaccion == null) {
			interfaceTransaccion = new PersistenciaTransaccion();
		}
		return interfaceTransaccion;
	}
	private static IPersTranLinea getInterfaceTranLinea() {
		if(interfaceTranLinea == null) {
			interfaceTranLinea = new PersistenciaTranLinea();
		}
		return interfaceTranLinea;
	}
	private static IPersLote getInterfaceLote() {
		if(interfaceLote == null) {
			interfaceLote = new PersistenciaLote();
		}
		return interfaceLote;
	}
	
	
	/*****************************************************************************************************************************************************/
	/** TRANSACCION */
	/*****************************************************************************************************************************************************/
	
	public Integer generarTransaccionCompra(Transaccion transaccion) throws PresentacionException {
		logger.info("Ingresa generarTransaccionCompra");
		Integer resultado = null;
		try {
			if(transaccion != null && TipoTran.C.equals(transaccion.getTipoTran())) {
				Conector.getConn();
				ConfigDriver cfgDrv = ConfigDriver.getConfigDriver();
				Double subTotal = new Double(0);
//				Double ivaSt = new Double(0);
				Double total = new Double(0);
				List<Lote> listaLote = new ArrayList<>();
				Double ivaTotal = new Double(0);
				for(TranLinea tl : transaccion.getListaTranLinea()) {
					Producto prod = tl.getProducto();
					Lote lote = new Lote();
					lote.setTranLinea(tl);
					lote.setStock(tl.getCantidad());
					listaLote.add(lote);
					//obtengo valor de iva para el producto
					Float ivaAplicaProd = Float.valueOf(cfgDrv.getIva(prod.getAplIva().getAplIvaProp()));
					//convierto numero divisor para el iva
					Float ivaProdDivisor = Converters.convertirPorcAMult(ivaAplicaProd);
					//calcula iva SUSTRAIDO del precio del producto
					Double ivaProd = (ivaProdDivisor != 0 ? (prod.getPrecio() - (prod.getPrecio() / ivaProdDivisor)) : prod.getPrecio());
					ivaProd = Converters.redondearDosDec(ivaProd);
					//setea a la linea de transaccion el iva correspondiente al producto
					tl.setIva(ivaProd);
					Double precioProd = prod.getPrecio();
					ivaTotal += (ivaProd * tl.getCantidad());
					total += (precioProd * tl.getCantidad());
				}
				ivaTotal =  Converters.redondearDosDec(ivaTotal);
				//se sustrae el ivaTotal al total ya que se manejan directamente precios con iva en la compra
				subTotal = Converters.redondearDosDec(total - ivaTotal);
				total = Converters.redondearDosDec(total);
				Integer nroTransac = Conector.obtenerSecuencia(CnstQryTransaccion.SEC_TRANSAC);
				transaccion.setNroTransac(nroTransac);
				transaccion.setEstadoTran(EstadoTran.P);
				transaccion.setFechaHora(new Fecha(Fecha.AMDHMS));
				transaccion.setSubTotal(subTotal);
				transaccion.setIva(ivaTotal);
				transaccion.setTotal(total);
				//se persiste la transaccion de tipo C (compra)
				resultado = getInterfaceTransaccion().guardarTransaccionCompra(transaccion);
				//se persiste el estado de la transaccion con estado 'P'
				getInterfaceTransaccion().guardarTranEstado(transaccion);
				//se persisten las lineas de la transaccion
				getInterfaceTranLinea().guardarListaTranLinea(transaccion.getListaTranLinea());
				//se periste el lote para cada producto de las lineas
				getInterfaceLote().guardarListaLote(listaLote);
				Conector.closeConn("generarTransaccionCompra");
			} else {
				throw new PresentacionException("generarTransaccionCompra ha sido mal implementado!");
			}
		} catch (ConectorException | PersistenciaException e) {
			logger.fatal("Excepcion en ManagerTransaccion > generarTransaccionCompra: " + e.getMessage(), e);
			throw new PresentacionException(e);
		} catch (Exception e) {
			logger.fatal("Excepcion no controlada en ManagerTransaccion > generarTransaccionCompra: " + e.getMessage(), e);
			throw new PresentacionException(e);
		}
		return resultado;
	}
	
	public Integer generarTransaccionVenta(Transaccion transaccion) throws PresentacionException {
		logger.info("Ingresa generarTransaccionVenta");
		Integer resultado = null;
		try {
			if(transaccion != null && TipoTran.V.equals(transaccion.getTipoTran())) {
//				Conector.getConn();//FIXME ver esto, no lo llamo porque la conexion está contenida arriba
				Integer nroTransac = Conector.obtenerSecuencia(CnstQryTransaccion.SEC_TRANSAC);
				transaccion.setNroTransac(nroTransac);
				//se persiste la transaccion de tipo V (venta)
				resultado = getInterfaceTransaccion().guardarTransaccionVenta(transaccion);
				//se persisten las lineas de la transaccion
				getInterfaceTranLinea().guardarListaTranLinea(transaccion.getListaTranLinea());
				//se persiste el estado de la transaccion con estado 'P'
				transaccion.setFechaHora(new Fecha(Fecha.AMDHMS));
				getInterfaceTransaccion().guardarTranEstado(transaccion);
//				Conector.closeConn("generarTransaccionVenta");//FIXME ver esto, no lo llamo porque la conexion está contenida arriba
			} else {
				throw new PresentacionException("generarTransaccionVenta ha sido mal implementado!");
			}
		} catch (/*ConectorException |*/ PersistenciaException e) {
			logger.fatal("Excepcion en ManagerTransaccion > generarTransaccionVenta: " + e.getMessage(), e);
			throw new PresentacionException(e);
		} catch (Exception e) {
			logger.fatal("Excepcion no controlada en ManagerTransaccion > generarTransaccionVenta: " + e.getMessage(), e);
			throw new PresentacionException(e);
		}
		return resultado;
	}
	
	public Transaccion obtenerTransaccionPorId(Integer idTransac) throws PresentacionException {
		Transaccion transac = null;
		try {
			Conector.getConn();
			transac = getInterfaceTransaccion().obtenerTransaccionPorId(idTransac);
			if(transac != null) {
				List<TranLinea> listaTranLinea = getInterfaceTranLinea().obtenerListaTranLinea(transac);
				if(listaTranLinea != null && !listaTranLinea.isEmpty()) {
					transac.setListaTranLinea(listaTranLinea);
				}
			}
			Conector.closeConn("obtenerTransaccionPorId");
		} catch (PersistenciaException e) {
			logger.fatal("Excepcion en ManagerTransaccion > obtenerTransaccionPorId: " + e.getMessage(), e);
			throw new PresentacionException(e);
		}
		return transac;
	}
	
	public List<Transaccion> obtenerListaTransaccionPorPersona(Long idPersona, TipoTran tipoTran, EstadoTran estadoTran) 
			throws PresentacionException {
		List<Transaccion> listaTransac = null;
		try {
			Conector.getConn();
			listaTransac = getInterfaceTransaccion().obtenerListaTransaccionPorPersona(idPersona, tipoTran, estadoTran);
			if(listaTransac != null && !listaTransac.isEmpty()) {
				for(Transaccion transac : listaTransac) {
					List<TranLinea> listaTranLinea = getInterfaceTranLinea().obtenerListaTranLinea(transac);
					if(listaTranLinea != null && !listaTranLinea.isEmpty()) {
						transac.setListaTranLinea(listaTranLinea);
					}
				}
			}
			Conector.closeConn("obtenerListaTransaccionPorPersona");
		} catch (PersistenciaException e) {
			logger.fatal("Excepcion en ManagerTransaccion > obtenerListaTransaccionPorPersona: " + e.getMessage(), e);
			throw new PresentacionException(e);
		}
		return listaTransac;
	}
	
	public List<Transaccion> obtenerListaTransaccionPorPeriodo(TipoTran tipoTran, EstadoTran estadoTran, Fecha fechaIni, Fecha fechaFin) 
			 throws PresentacionException {
		List<Transaccion> listaTransac = null;
		try {
			Conector.getConn();
			listaTransac = getInterfaceTransaccion().obtenerListaTransaccionPorPeriodo(tipoTran, estadoTran, fechaIni, fechaFin);
			if(listaTransac != null && !listaTransac.isEmpty()) {
				for(Transaccion transac : listaTransac) {
					List<TranLinea> listaTranLinea = getInterfaceTranLinea().obtenerListaTranLinea(transac);
					if(listaTranLinea != null && !listaTranLinea.isEmpty()) {
						transac.setListaTranLinea(listaTranLinea);
					}
				}
			}
			Conector.closeConn("obtenerListaTransaccionPorPeriodo");
		} catch (PersistenciaException e) {
			logger.fatal("Excepcion en ManagerTransaccion > obtenerListaTransaccionPorPeriodo: " + e.getMessage(), e);
			throw new PresentacionException(e);
		}
		return listaTransac;
	}
	
	public Integer modificarTransaccionCompra(Transaccion transaccion, List<Lote> listaLote) throws PresentacionException {
		if(transaccion != null) {
			//transaccion de tipo "compra"
			if(transaccion.getTipoTran().equals(TipoTran.C)) {
				try {
					Conector.getConn();
					//seteo estado a "confirmado"
					transaccion.setEstadoTran(EstadoTran.C);
					getInterfaceTransaccion().guardarTranEstado(transaccion);
					getInterfaceTransaccion().modificarEstadoTransaccion(transaccion);
					for(Lote lote : listaLote) {
						getInterfaceLote().actualizarLote(lote);
					}
					Conector.closeConn("modificarTransaccionCompra");
				} catch (PersistenciaException e) {
					logger.fatal("Excepcion en ManagerTransaccion > modificarTransaccionCompra: " + e.getMessage(), e);
					throw new PresentacionException(e);
				}
			}
		}
		return null;
	}
	
//	public Integer modificarEstadoTransaccion(Transaccion transaccion, List<Lote> listaLote) throws PresentacionException {
//		if(transaccion != null) {
//			//transaccion de tipo "compra"
//			if(transaccion.getTipoTran().equals(TipoTran.C)) {
//				try {
//					Conector.getConn();
//					//seteo estado a "confirmado"
//					transaccion.setEstadoTran(EstadoTran.C);
//					getInterfaceTransaccion().guardarTranEstado(transaccion);
//					getInterfaceTransaccion().modificarEstadoTransaccion(transaccion);
//					for(Lote lote : listaLote) {
//						getInterfaceLote().actualizarLote(lote);
//					}
//					Conector.closeConn("modificarTransaccionCompra");
//				} catch (PersistenciaException e) {
//					logger.fatal("Excepcion en ManagerTransaccion > modificarTransaccionCompra: " + e.getMessage(), e);
//					throw new PresentacionException(e);
//				}
//			}
//		}
//		return null;
//	}
	
	public List<TranLineaLote> obtenerListaTranLineaLote(Integer nroTransac, Integer idProducto) throws PresentacionException {
		logger.info("Se ingresa a obtenerListaTranLineaLote");
		List<TranLineaLote> listaTll = null;
		try {
			Conector.getConn();
			listaTll = getInterfaceTransaccion().obtenerListaTranLineaLote(nroTransac, idProducto);
			Conector.closeConn("obtenerListaLotePorTransac");
		} catch (PersistenciaException e) {
			logger.fatal("Excepcion en ManagerTransaccion > obtenerListaTranLineaLote: " + e.getMessage(), e);
			throw new PresentacionException(e);
		}
		return listaTll;
	}
	
	//anulaciones
	
	public ErrorLogico anularTransaccionVenta(Transaccion transaccion) throws PresentacionException {
		ErrorLogico error = null;
		try {
			Conector.getConn();
			if(transaccion != null) {
				transaccion.setEstadoTran(EstadoTran.A);
				transaccion.setFechaHora(new Fecha(Fecha.AMDHMS));
				//se debe manejar lote para devolver cantidad de productos
				error = validarAnulacionVto(transaccion);
				if(error != null) {
					return error;
				} else {
					List<TranLineaLote> listaTll = new ArrayList<>();
					for(TranLinea tl : transaccion.getListaTranLinea()) {
						listaTll.addAll(getInterfaceTransaccion().obtenerListaTranLineaLote(tl.getTransaccion().getNroTransac(), 
														tl.getProducto().getIdProducto()));
					}
					for(TranLineaLote tll : listaTll) {
						/*
						 * obtengo lote acutal de base, y le sumo la cantidad restada en la venta
						 * para la transaccion que se esta manejando
						 */
						Lote loteActual = getInterfaceLote().obtenerLotePorId(tll.getLote().getIdLote());
						Integer stock = loteActual.getStock() + tll.getCantidad();
						//actualizo en base
						getInterfaceLote().actualizarStockLote(loteActual.getIdLote(), stock);
					}
				}
				getInterfaceTransaccion().guardarTranEstado(transaccion);
				getInterfaceTransaccion().modificarEstadoTransaccion(transaccion);
				//elimino tran_vta_lote para la transaccion que se anula
				getInterfaceTransaccion().eliminarTranLineaLote(transaccion.getNroTransac());
			}
			Conector.closeConn("anularTransaccionVenta");
		} catch (PersistenciaException e) {
			logger.fatal("Excepcion en ManagerTransaccion > anularTransaccionVenta: " + e.getMessage(), e);
			throw new PresentacionException(e);
		}
		return error;
	}
	
	//FIXME chequear este metodo
	private ErrorLogico validarAnulacionVto(Transaccion transaccion) throws PresentacionException {
		ErrorLogico error = null;
		try {
			ManagerProducto mgrProd = new ManagerProducto();
			ConfigDriver cfgDrv = ConfigDriver.getConfigDriver();
			Integer diasTol = Integer.valueOf(cfgDrv.getVencTolerableAnul());
			for(TranLinea tl : transaccion.getListaTranLinea()) {
				Lote lote = mgrProd.obtenerLoteVtaPorTransacProdNoConn(tl.getTransaccion().getNroTransac(), tl.getProducto().getIdProducto());
				Fecha fechaAnulacion = new Fecha(Fecha.AMD);
				Fecha fechaVto = lote.getVenc();
				long diff = fechaVto.getTimeInMillis() - fechaAnulacion.getTimeInMillis(); 
			    long dias = TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
			    /*
			     * se maneja para la anulacion que los días restantes de vencimiento de los productos
			     * no superen lo establecido por properties
			     */
				if(diasTol.longValue() > dias) {
					error = new ErrorLogico();
					error.setCodigo(0);
					error.setDescripcion("Alguno de los productos no cumple con el requisito de tolerancia de vencimiento.");
					return error;
				}
			}
		} catch (PersistenciaException e) {
			logger.fatal("Excepcion en ManagerTransaccion > validarAnulacionVto: " + e.getMessage(), e);
			throw new PresentacionException(e);
		} catch (Exception e) {
			logger.fatal("Excepcion no controlada en ManagerTransaccion > validarAnulacionVto: " + e.getMessage(), e);
			throw new PresentacionException(e);
		}
		return error;
	}
	
}
