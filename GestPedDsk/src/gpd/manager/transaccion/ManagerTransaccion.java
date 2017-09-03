package gpd.manager.transaccion;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;

import gpd.db.constantes.CnstQryTransaccion;
import gpd.dominio.helper.HlpProducto;
import gpd.dominio.producto.Lote;
import gpd.dominio.producto.Producto;
import gpd.dominio.transaccion.EstadoTran;
import gpd.dominio.transaccion.TipoTran;
import gpd.dominio.transaccion.TranLinea;
import gpd.dominio.transaccion.TranLineaLote;
import gpd.dominio.transaccion.Transaccion;
import gpd.dominio.util.Converters;
import gpd.dominio.util.Sinc;
import gpd.exceptions.ConectorException;
import gpd.exceptions.PersistenciaException;
import gpd.exceptions.PresentacionException;
import gpd.interfaces.producto.IPersLote;
import gpd.interfaces.producto.IPersProducto;
import gpd.interfaces.transaccion.IPersTranLinea;
import gpd.interfaces.transaccion.IPersTransaccion;
import gpd.manager.producto.ManagerProducto;
import gpd.persistencia.conector.Conector;
import gpd.persistencia.producto.PersistenciaLote;
import gpd.persistencia.producto.PersistenciaProducto;
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
	private static IPersProducto interfaceProducto;
	
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
	private static IPersProducto getInterfaceProducto() {
		if(interfaceProducto == null) {
			interfaceProducto = new PersistenciaProducto();
		}
		return interfaceProducto;
	}
	
	
	/*****************************************************************************************************************************************************/
	/** TRANSACCION */
	/*****************************************************************************************************************************************************/
	
	/**
	 * metodo que recibe la transaccin con sus lineas, genera los calculos para desglosar iva y persiste
	 * @param transaccion
	 * @return
	 * @throws PresentacionException
	 */
	public Integer generarTransaccionCompra(Transaccion transaccion) throws PresentacionException {
		logger.info("Ingresa generarTransaccionCompra");
		Integer resultado = null;
		try (Connection conn = Conector.getConn()) {
			if(transaccion != null && TipoTran.C.equals(transaccion.getTipoTran())) {
				ConfigDriver cfgDrv = ConfigDriver.getConfigDriver();
				Double subTotal = new Double(0);
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
//					Float ivaProdDivisor = Converters.convertirPorcAMult(ivaAplicaProd);
					//calcula iva SUSTRAIDO del precio del producto
					Double ivaProd = (ivaAplicaProd > 0 ? (Converters.obtenerIvaDePrecio(prod.getPrecio(), ivaAplicaProd)) : new Double(0));
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
				Integer nroTransac = Conector.obtenerSecuencia(conn, CnstQryTransaccion.SEC_TRANSAC);
				transaccion.setNroTransac(nroTransac);
				transaccion.setEstadoTran(EstadoTran.P);
				transaccion.setFechaHora(new Fecha(Fecha.AMDHMS));
				transaccion.setSubTotal(subTotal);
				transaccion.setIva(ivaTotal);
				transaccion.setTotal(total);
				//se persiste la transaccion de tipo C (compra)
				resultado = getInterfaceTransaccion().guardarTransaccionCompra(conn, transaccion);
				//se persiste el estado de la transaccion con estado 'P'
				getInterfaceTransaccion().guardarTranEstado(conn, transaccion);
				//se persisten las lineas de la transaccion
				getInterfaceTranLinea().guardarListaTranLinea(conn, transaccion.getListaTranLinea());
				//se periste el lote para cada producto de las lineas
				getInterfaceLote().guardarListaLote(conn, listaLote);
				Conector.commitConn(conn);
			} else {
				throw new PresentacionException("generarTransaccionCompra ha sido mal implementado!");
			}
		} catch (ConectorException | PersistenciaException e) {
			logger.fatal("Excepcion en ManagerTransaccion > generarTransaccionCompra: " + e.getMessage(), e);
			throw new PresentacionException(e);
		} catch (Exception e) {
			logger.fatal("Excepcion GENERICA en ManagerTransaccion > generarTransaccionCompra: " + e.getMessage(), e);
			throw new PresentacionException(e);
		}
		return resultado;
	}
	
	/**
	 * recibe conexion y no comitea, por ser llamado desde otro manager
	 * @param conn RECIBE CONEXION por ser llamado desde otro manager
	 * @param transaccion
	 * @return
	 * @throws PresentacionException
	 * 
	 * <<<<< RECIBE CONNECTION - NO AUTOGENERA >>>>>
	 * 
	 */
	public Integer generarTransaccionVenta(Connection conn, Transaccion transaccion) throws PresentacionException {
		logger.info("Ingresa generarTransaccionVenta");
		Integer resultado = null;
		try {
			if(transaccion != null && TipoTran.V.equals(transaccion.getTipoTran())) {
				Integer nroTransac = Conector.obtenerSecuencia(conn, CnstQryTransaccion.SEC_TRANSAC);
				transaccion.setNroTransac(nroTransac);
				//se persiste la transaccion de tipo V (venta)
				resultado = getInterfaceTransaccion().guardarTransaccionVenta(conn, transaccion);
				//se persisten las lineas de la transaccion
				getInterfaceTranLinea().guardarListaTranLinea(conn, transaccion.getListaTranLinea());
				//se persiste el estado de la transaccion con estado 'P'
				transaccion.setFechaHora(new Fecha(Fecha.AMDHMS));
				getInterfaceTransaccion().guardarTranEstado(conn, transaccion);
				/*
				 * NO COMITEA POR SER METODO QUE ES LLAMADO DESDE OTROS MANAGERS
				 */
			} else {
				throw new PresentacionException("generarTransaccionVenta ha sido mal implementado!");
			}
		} catch (/*ConectorException |*/ PersistenciaException e) {
			logger.fatal("Excepcion en ManagerTransaccion > generarTransaccionVenta: " + e.getMessage(), e);
			throw new PresentacionException(e);
		} catch (Exception e) {
			logger.fatal("Excepcion GENERICA en ManagerTransaccion > generarTransaccionVenta: " + e.getMessage(), e);
			throw new PresentacionException(e);
		}
		return resultado;
	}
	
	public Transaccion obtenerTransaccionPorId(Integer idTransac) throws PresentacionException {
		Transaccion transac = null;
		try (Connection conn = Conector.getConn()) {
			transac = getInterfaceTransaccion().obtenerTransaccionPorId(conn, idTransac);
			if(transac != null) {
				List<TranLinea> listaTranLinea = getInterfaceTranLinea().obtenerListaTranLinea(conn, transac);
				if(listaTranLinea != null && !listaTranLinea.isEmpty()) {
					transac.setListaTranLinea(listaTranLinea);
				}
			}
		} catch (PersistenciaException | SQLException e) {
			logger.fatal("Excepcion en ManagerTransaccion > obtenerTransaccionPorId: " + e.getMessage(), e);
			throw new PresentacionException(e);
		} catch (Exception e) {
			logger.fatal("Excepcion GENERICA en ManagerTransaccion > obtenerTransaccionPorId: " + e.getMessage(), e);
			throw new PresentacionException(e);
		}
		return transac;
	}
	
	public List<Transaccion> obtenerListaTransaccionPorPersona(Long idPersona, TipoTran tipoTran, EstadoTran estadoTran) 
			throws PresentacionException {
		List<Transaccion> listaTransac = null;
		try (Connection conn = Conector.getConn()) {
			listaTransac = getInterfaceTransaccion().obtenerListaTransaccionPorPersona(conn, idPersona, tipoTran, estadoTran);
			if(listaTransac != null && !listaTransac.isEmpty()) {
				for(Transaccion transac : listaTransac) {
					List<TranLinea> listaTranLinea = getInterfaceTranLinea().obtenerListaTranLinea(conn, transac);
					if(listaTranLinea != null && !listaTranLinea.isEmpty()) {
						transac.setListaTranLinea(listaTranLinea);
					}
				}
			}
		} catch (PersistenciaException | SQLException e) {
			logger.fatal("Excepcion en ManagerTransaccion > obtenerListaTransaccionPorPersona: " + e.getMessage(), e);
			throw new PresentacionException(e);
		} catch (Exception e) {
			logger.fatal("Excepcion GENERICA en ManagerTransaccion > obtenerListaTransaccionPorPersona: " + e.getMessage(), e);
			throw new PresentacionException(e);
		}
		return listaTransac;
	}
	
	public List<Transaccion> obtenerListaTransaccionPorPeriodo(TipoTran tipoTran, EstadoTran estadoTran, Fecha fechaIni, Fecha fechaFin) 
			 throws PresentacionException {
		List<Transaccion> listaTransac = null;
		try (Connection conn = Conector.getConn()) {
			listaTransac = getInterfaceTransaccion().obtenerListaTransaccionPorPeriodo(conn, tipoTran, estadoTran, fechaIni, fechaFin);
			if(listaTransac != null && !listaTransac.isEmpty()) {
				for(Transaccion transac : listaTransac) {
					List<TranLinea> listaTranLinea = getInterfaceTranLinea().obtenerListaTranLinea(conn, transac);
					if(listaTranLinea != null && !listaTranLinea.isEmpty()) {
						transac.setListaTranLinea(listaTranLinea);
					}
				}
			}
		} catch (PersistenciaException | SQLException e) {
			logger.fatal("Excepcion en ManagerTransaccion > obtenerListaTransaccionPorPeriodo: " + e.getMessage(), e);
			throw new PresentacionException(e);
		} catch (Exception e) {
			logger.fatal("Excepcion GENERICA en ManagerTransaccion > obtenerListaTransaccionPorPeriodo: " + e.getMessage(), e);
			throw new PresentacionException(e);
		}
		return listaTransac;
	}
	
	public Integer modificarTransaccionCompra(Transaccion transaccion, List<Lote> listaLote) throws PresentacionException {
		try (Connection conn = Conector.getConn()) {
			if(transaccion != null && transaccion.getTipoTran().equals(TipoTran.C)) {
				//transaccion de tipo "compra"
				Fecha ultAct = new Fecha(Fecha.AMDHMS);
				//seteo estado a "confirmado"
				transaccion.setEstadoTran(EstadoTran.C);
				getInterfaceTransaccion().guardarTranEstado(conn, transaccion);
				getInterfaceTransaccion().modificarEstadoTransaccion(conn, transaccion);
				for(Lote lote : listaLote) {
					/*
					 * aparte de actualizar el lote, verifica el precio actual que va a tener el lote contra
					 * el precio de esta compra, en caso de diferir, se va a marcar el producto en 'N' para 
					 * ser sincronizado hacia el sistema web
					 */
					Producto prod = lote.getTranLinea().getProducto();
					ManagerProducto mgrProd = new ManagerProducto();
					HlpProducto hlpProdAnt = mgrProd.obtenerStockPrecioLotePorProductoNoConn(conn, prod.getIdProducto());
					getInterfaceLote().actualizarLote(conn, lote);
					/*
					 * vuelvo a obtener el precio del stock actual del lote, para ver si hubieron cambios de precio 
					 * con respecto al anterior, en caso positivo, actualiza el producto para ser sincronizado
					 */
					HlpProducto hlpProdAct = mgrProd.obtenerStockPrecioLotePorProductoNoConn(conn, prod.getIdProducto());
					if(hlpProdAnt.getPrecioVta().doubleValue() != hlpProdAct.getPrecioVta().doubleValue()) {
						//modifico el producto para dejarlo sinc = 'N' y que pueda ser tomado por el sincronizador
						getInterfaceProducto().modificarSincProducto(conn, prod.getIdProducto(), Sinc.N, ultAct);
					}
				}
				Conector.commitConn(conn);
			}
		} catch (PersistenciaException | SQLException e) {
			logger.fatal("Excepcion en ManagerTransaccion > modificarTransaccionCompra: " + e.getMessage(), e);
			throw new PresentacionException(e);
		} catch (Exception e) {
			logger.fatal("Excepcion GENERICA en ManagerTransaccion > modificarTransaccionCompra: " + e.getMessage(), e);
			throw new PresentacionException(e);
		}
		return null;
	}
	
	public List<TranLineaLote> obtenerListaTranLineaLote(Integer nroTransac, Integer idProducto) throws PresentacionException {
		logger.info("Se ingresa a obtenerListaTranLineaLote");
		List<TranLineaLote> listaTll = null;
		try (Connection conn = Conector.getConn()) {
			listaTll = getInterfaceTransaccion().obtenerListaTranLineaLote(conn, nroTransac, idProducto);
		} catch (PersistenciaException | SQLException e) {
			logger.fatal("Excepcion en ManagerTransaccion > obtenerListaTranLineaLote: " + e.getMessage(), e);
			throw new PresentacionException(e);
		} catch (Exception e) {
			logger.fatal("Excepcion GENERICA en ManagerTransaccion > obtenerListaTranLineaLote: " + e.getMessage(), e);
			throw new PresentacionException(e);
		}
		return listaTll;
	}
	
	//anulaciones compras
	public ErrorLogico anularTransaccionCompra(Transaccion transaccion) throws PresentacionException {
		ErrorLogico error = null;
		try (Connection conn = Conector.getConn()) {
			if(transaccion != null && TipoTran.C.equals(transaccion.getTipoTran())) {
				transaccion.setEstadoTran(EstadoTran.A);
				transaccion.setFechaHora(new Fecha(Fecha.AMDHMS));
				getInterfaceTransaccion().guardarTranEstado(conn, transaccion);
				getInterfaceTransaccion().modificarEstadoTransaccion(conn, transaccion);
				Conector.commitConn(conn);
			}
		} catch (PersistenciaException | SQLException e) {
			logger.fatal("Excepcion en ManagerTransaccion > anularTransaccionVenta: " + e.getMessage(), e);
			throw new PresentacionException(e);
		} catch (Exception e) {
			logger.fatal("Excepcion GENERICA en ManagerTransaccion > anularTransaccionVenta: " + e.getMessage(), e);
			throw new PresentacionException(e);
		}
		return error;
	}
	
	//anulaciones ventas
	public ErrorLogico anularTransaccionVenta(Transaccion transaccion) throws PresentacionException {
		ErrorLogico error = null;
		try (Connection conn = Conector.getConn()) {
			if(transaccion != null && TipoTran.V.equals(transaccion.getTipoTran())) {
				transaccion.setEstadoTran(EstadoTran.A);
				transaccion.setFechaHora(new Fecha(Fecha.AMDHMS));
				//se debe manejar lote para devolver cantidad de productos
				error = validarAnulacionVto(transaccion);
				if(error != null) {
					return error;
				} else {
					List<TranLineaLote> listaTll = new ArrayList<>();
					for(TranLinea tl : transaccion.getListaTranLinea()) {
						listaTll.addAll(getInterfaceTransaccion().obtenerListaTranLineaLote(conn, tl.getTransaccion().getNroTransac(), 
														tl.getProducto().getIdProducto()));
					}
					for(TranLineaLote tll : listaTll) {
						/*
						 * obtengo lote acutal de base, y le sumo la cantidad restada en la venta
						 * para la transaccion que se esta manejando
						 */
						Lote loteActual = getInterfaceLote().obtenerLotePorId(conn, tll.getLote().getIdLote());
						Integer stock = loteActual.getStock() + tll.getCantidad();
						//actualizo en base
						getInterfaceLote().actualizarStockLote(conn, loteActual.getIdLote(), stock);
					}
				}
				getInterfaceTransaccion().guardarTranEstado(conn, transaccion);
				getInterfaceTransaccion().modificarEstadoTransaccion(conn, transaccion);
				//elimino tran_vta_lote para la transaccion que se anula
				getInterfaceTransaccion().eliminarTranVtaLote(conn, transaccion.getNroTransac());
				Conector.commitConn(conn);
			}
		} catch (PersistenciaException | SQLException e) {
			logger.fatal("Excepcion en ManagerTransaccion > anularTransaccionVenta: " + e.getMessage(), e);
			throw new PresentacionException(e);
		} catch (Exception e) {
			logger.fatal("Excepcion GENERICA en ManagerTransaccion > anularTransaccionVenta: " + e.getMessage(), e);
			throw new PresentacionException(e);
		}
		return error;
	}
	
	private ErrorLogico validarAnulacionVto(Transaccion transaccion) throws PresentacionException {
		ErrorLogico error = null;
		try (Connection conn = Conector.getConn()) {
			ManagerProducto mgrProd = new ManagerProducto();
			ConfigDriver cfgDrv = ConfigDriver.getConfigDriver();
			Integer diasTol = Integer.valueOf(cfgDrv.getVencTolerableAnul());
			for(TranLinea tl : transaccion.getListaTranLinea()) {
				Lote lote = mgrProd.obtenerLoteVtaPorTransacProdNoConn(conn, tl.getTransaccion().getNroTransac(), tl.getProducto().getIdProducto());
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
		} catch (PersistenciaException | SQLException e) {
			logger.fatal("Excepcion en ManagerTransaccion > validarAnulacionVto: " + e.getMessage(), e);
			throw new PresentacionException(e);
		} catch (Exception e) {
			logger.fatal("Excepcion generica en ManagerTransaccion > validarAnulacionVto: " + e.getMessage(), e);
			throw new PresentacionException(e);
		}
		return error;
	}
	
}
