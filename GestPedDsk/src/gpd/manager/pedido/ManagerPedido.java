package gpd.manager.pedido;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import gpd.dominio.helper.HlpProducto;
import gpd.dominio.pedido.EstadoPedido;
import gpd.dominio.pedido.Pedido;
import gpd.dominio.pedido.PedidoLinea;
import gpd.dominio.producto.Producto;
import gpd.dominio.transaccion.EstadoTran;
import gpd.dominio.transaccion.TipoTran;
import gpd.dominio.transaccion.TranLinea;
import gpd.dominio.transaccion.Transaccion;
import gpd.dominio.usuario.UsuarioDsk;
import gpd.dominio.util.Converters;
import gpd.dominio.util.Origen;
import gpd.dominio.util.Sinc;
import gpd.exceptions.PersistenciaException;
import gpd.exceptions.PresentacionException;
import gpd.exceptions.ProductoSinStockException;
import gpd.interfaces.pedido.IPersPedido;
import gpd.interfaces.pedido.IPersPedidoLinea;
import gpd.interfaces.transaccion.IPersTranLinea;
import gpd.interfaces.transaccion.IPersTransaccion;
import gpd.manager.producto.ManagerProducto;
import gpd.manager.transaccion.ManagerTransaccion;
import gpd.persistencia.conector.Conector;
import gpd.persistencia.pedido.PersistenciaPedido;
import gpd.persistencia.pedido.PersistenciaPedidoLinea;
import gpd.persistencia.transaccion.PersistenciaTranLinea;
import gpd.persistencia.transaccion.PersistenciaTransaccion;
import gpd.types.Fecha;

public class ManagerPedido {

	private static final Logger logger = Logger.getLogger(ManagerPedido.class);
	private static IPersPedido interfacePedido;
	private static IPersPedidoLinea interfacePedidoLinea;
	private static IPersTransaccion interfaceTransac;
	private static IPersTranLinea interfaceTranLinea;
	private static final String ESC = "\n";
	
	private static IPersPedido getInterfacePedido() {
		if(interfacePedido == null) {
			interfacePedido = new PersistenciaPedido();
		}
		return interfacePedido;
	}
	private static IPersPedidoLinea getInterfacePedidoLinea() {
		if(interfacePedidoLinea == null) {
			interfacePedidoLinea = new PersistenciaPedidoLinea();
		}
		return interfacePedidoLinea;
	}
	private static IPersTransaccion getInterfaceTransaccion() {
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
	/** PEDIDO */
	/*****************************************************************************************************************************************************/
	
	public Pedido obtenerPedidoPorId(Long idPersona, Fecha fechaHora) throws PresentacionException {
		Pedido pedido = null;
		try (Connection conn = Conector.getConn()) {
			pedido = getInterfacePedido().obtenerPedidoPorId(conn, idPersona, fechaHora);
		} catch (PersistenciaException | SQLException e) {
			logger.fatal("Excepcion en ManagerPedido > obtenerPedidoPorId: " + e.getMessage(), e);
			throw new PresentacionException(e);
		} catch (Exception e) {
			logger.fatal("Excepcion GENERICA en ManagerPedido > obtenerPedidoPorId: " + e.getMessage(), e);
			throw new PresentacionException(e);
		}
		return pedido;
	}
	
	public List<Pedido> obtenerListaPedidoPorPeriodo(EstadoPedido ep, Long idPersona, Origen origen, Fecha fechaIni, Fecha fechaFin) throws PresentacionException {
		List<Pedido> listaPedido = null;
		try (Connection conn = Conector.getConn()) {
			listaPedido = getInterfacePedido().obtenerListaPedido(conn, ep, idPersona, origen, fechaIni, fechaFin);
		} catch (PersistenciaException | SQLException e) {
			logger.fatal("Excepcion en ManagerPedido > obtenerListaPedidoPorPeriodo: " + e.getMessage(), e);
			throw new PresentacionException(e);
		} catch (Exception e) {
			logger.fatal("Excepcion GENERICA en ManagerPedido > obtenerListaPedidoPorPeriodo: " + e.getMessage(), e);
			throw new PresentacionException(e);
		}
		return listaPedido;
	}
	
	/**
	 * metodo para generar nuevos pedidos
	 * @param pedido
	 * @return
	 * @throws PresentacionException
	 */
	public Integer generarNuevoPedido(Pedido pedido) throws PresentacionException {
		Integer resultado = 0;
		try (Connection conn = Conector.getConn()) {
			if(pedido != null && pedido.getListaPedidoLinea() != null &&
					!pedido.getListaPedidoLinea().isEmpty()) {
				//se genera nueva transaccion de VENTA con estado P
				ManagerTransaccion mgrTransac = new ManagerTransaccion();
				//transaccion
				Sinc sinc = Sinc.N;
				Fecha  ultAct = new Fecha(Fecha.AMDHMS);
				Transaccion transac = new Transaccion(TipoTran.V);
				List<TranLinea> listaTransacLinea = new ArrayList<>();
				Double subTotal = new Double(0);
				Double ivaTotal = new Double(0);
				Double total = new Double(0);
				for(PedidoLinea pl : pedido.getListaPedidoLinea()) {
					TranLinea tl = new TranLinea(transac);
					Producto prod = pl.getProducto();
					tl.setProducto(prod);
					tl.setCantidad(pl.getCantidad());
					tl.setIva(pl.getIva());
					tl.setPrecioUnit(pl.getPrecioUnit());
					listaTransacLinea.add(tl);
					total += pl.getPrecioUnit() * pl.getCantidad();
					ivaTotal += pl.getIva() * pl.getCantidad();
				}
				subTotal = total - ivaTotal;
				subTotal = Converters.redondearDosDec(subTotal);
				ivaTotal = Converters.redondearDosDec(ivaTotal);
				total = Converters.redondearDosDec(total);
				pedido.setSubTotal(subTotal);
				pedido.setIva(ivaTotal);
				pedido.setTotal(total);
				transac.setEstadoTran(EstadoTran.P);
				transac.setPersona(pedido.getPersona());
				transac.setFechaHora(pedido.getFechaHora());
				transac.setSubTotal(pedido.getSubTotal());
				transac.setIva(pedido.getIva());
				transac.setTotal(pedido.getTotal());
				//pedido
				pedido.setTransaccion(transac);
				pedido.setOrigen(Origen.D);
				pedido.setSinc(sinc);
				pedido.setUltAct(ultAct);
				transac.setListaTranLinea(listaTransacLinea);
				//generar trnsaccion de venta
				mgrTransac.generarTransaccionVenta(conn, transac);
				//persisto el pedido en base
				resultado = getInterfacePedido().guardarPedido(conn, pedido);
				//persisto lineas de pedido en base
				getInterfacePedidoLinea().guardarListaPedidoLinea(conn, pedido.getListaPedidoLinea());
			}
			Conector.commitConn(conn);
		} catch (PersistenciaException | SQLException e) {
			logger.fatal("Excepcion en ManagerPedido > generarNuevoPedido: " + e.getMessage(), e);
			throw new PresentacionException(e);
		} catch (Exception e) {
			logger.fatal("Excepcion GENERICA en ManagerPedido > manejarStockLotePorProducto: " + e.getMessage(), e);
			throw new PresentacionException(e);
		}
		return resultado;
	}
	
	/**
	 * metodo para actualizar pedidos tanto para ventas (a estado confirmado), como para pedidos pendientes que
	 * se cambian sus lineas o actualizan desde el sistema web
	 * @param pedido
	 * @param estPedCambio
	 * @return
	 * @throws PresentacionException
	 * @throws ProductoSinStockException
	 */
	public String actualizarPedido(Pedido pedido, EstadoPedido estPedCambio, UsuarioDsk usr) throws PresentacionException, ProductoSinStockException {
		try (Connection conn = Conector.getConn()) {
			if( (EstadoPedido.P.equals(pedido.getEstado()) && EstadoPedido.C.equals(estPedCambio)) ||
					((EstadoPedido.R.equals(pedido.getEstado()) && EstadoPedido.C.equals(estPedCambio)) && Origen.D.equals(pedido.getOrigen())) ||
					(EstadoPedido.F.equals(pedido.getEstado()) && EstadoPedido.C.equals(estPedCambio)) ) {
				//ESTADOS: Pendiente[P] > Confirmado[C] (dsk o web) | Revision[R] a Confirmado[C] (dsk) | PreConfirmado[F] > Confirmado[C] (web)
				logger.info("<< Ingresa actualizacion de pedidos >> estado actual: " + pedido.getEstado().getEstadoPedido() + 
						" - estado actualiza: " + estPedCambio.getEstadoPedido() + " [actualizacion a venta]");
				String controlStock = controlarStocksPedido(conn, pedido);
				if(null == controlStock) {
					ManagerProducto mgrProd = new ManagerProducto();
					pedido.setEstado(estPedCambio);
					/*
					 * en caso de que alcance Y que el estadoPedido sea C (confirmado)... 
					 * comienzo a bajar stock por producto con fecha de vencimiento mas cercana a hoy
					 */
					Transaccion transac;
					if(Origen.D.equals(pedido.getOrigen())) {
						//si el pedido fue hecho en sist DSK, la transaccion ya existe
						transac = pedido.getTransaccion();
						//marco como confirmada la transaccion
						transac.setEstadoTran(EstadoTran.C);
//						transac.setFechaHora(new Fecha(Fecha.AMDHMS));
//						getInterfaceTransaccion().modificarEstadoTransaccion(conn, transac);
						/*
						 * se invoca a metodo actualizarTransacDesdePedido ya que al ser una actualización, esta
						 * debe contener modificaciones en sus lineas, por lo que hay que impactar la transac.
						 */
						actualizarTransacDesdePedido(conn, pedido, transac);
						getInterfaceTransaccion().guardarTranEstado(conn, transac);
					} else {
						//si el pedido fue hecho en sist WEB, se genera la nueva transaccion
						transac = new Transaccion(TipoTran.V);
						List<TranLinea> listaTransacLinea = new ArrayList<>();
						Double subTotal = new Double(0);
						Double ivaTotal = new Double(0);
						Double total = new Double(0);
						for(PedidoLinea pl : pedido.getListaPedidoLinea()) {
							TranLinea tl = new TranLinea(transac);
							Producto prod = pl.getProducto();
							tl.setProducto(prod);
							tl.setCantidad(pl.getCantidad());
							tl.setIva(pl.getIva());
							tl.setPrecioUnit(pl.getPrecioUnit());
							listaTransacLinea.add(tl);
							total += pl.getPrecioUnit() * pl.getCantidad();
							ivaTotal += pl.getIva() * pl.getCantidad();
						}
						subTotal = total - ivaTotal;
						subTotal = Converters.redondearDosDec(subTotal);
						ivaTotal = Converters.redondearDosDec(ivaTotal);
						total = Converters.redondearDosDec(total);
						pedido.setSubTotal(subTotal);
						pedido.setIva(ivaTotal);
						pedido.setTotal(total);
						transac.setPersona(pedido.getPersona());
						transac.setFechaHora(pedido.getFechaHora());
						transac.setSubTotal(pedido.getSubTotal());
						transac.setIva(pedido.getIva());
						transac.setTotal(pedido.getTotal());
						transac.setListaTranLinea(listaTransacLinea);
						//marco como confirmada la transaccion
						transac.setEstadoTran(EstadoTran.C);
						transac.setFechaHora(new Fecha(Fecha.AMDHMS));
						ManagerTransaccion mgrTransac = new ManagerTransaccion();
						mgrTransac.generarTransaccionVenta(conn, transac);
						//le seteo al pedido la transaccion ya que en los casos web, de inicio no tendran transaccion asociada
						pedido.setTransaccion(transac);
					}
					
					for(PedidoLinea pl : pedido.getListaPedidoLinea()) {
						mgrProd.manejarStockLotePorProductoNoConn(conn, transac.getNroTransac(), pl.getProducto().getIdProducto(), pl.getCantidad());
					}
					pedido.setEstado(EstadoPedido.C);
					pedido.setSinc(Sinc.N);
					//le seteo el usuario actualizador del pedido
					pedido.setUsuario(usr);
					getInterfacePedido().modificarPedido(conn, pedido);
					//* guardo nuevo estado en tabla de estados (seteo fecha-hora temporalmente para estado_tran)
					Conector.commitConn(conn);
				} else {
					return controlStock;
				}
			} else if( (EstadoPedido.P.equals(pedido.getEstado()) && EstadoPedido.R.equals(estPedCambio)) || 
					((EstadoPedido.R.equals(pedido.getEstado()) && Sinc.N.equals(pedido.getSinc())) && EstadoPedido.R.equals(estPedCambio)) ) {
				//ESTADOS: Pendiente[P] > Revision[R] | Revision[R] > Revision[R] (sin haber sido sincronizado - pedido web)
				logger.info("<< Ingresa actualizacion de pedidos >> estado actual: " + pedido.getEstado().getEstadoPedido() + 
						" - estado actualiza: " + estPedCambio.getEstadoPedido() + " [actualizacion de lineas, pedido en estado P o R]");
				pedido.setEstado(EstadoPedido.R);
				Double subTotal = new Double(0);
				Double ivaTotal = new Double(0);
				Double total = new Double(0);
				for(PedidoLinea pl : pedido.getListaPedidoLinea()) {
					total += pl.getPrecioUnit() * pl.getCantidad();
					ivaTotal += pl.getIva() * pl.getCantidad();
				}
				subTotal = total - ivaTotal;
				subTotal = Converters.redondearDosDec(subTotal);
				ivaTotal = Converters.redondearDosDec(ivaTotal);
				total = Converters.redondearDosDec(total);
				pedido.setSubTotal(subTotal);
				pedido.setIva(ivaTotal);
				pedido.setTotal(total);
				//si es web lo marco como no sinc para que sea enviado en la sincronizacion
				if(pedido.getOrigen().equals(Origen.W)) {
					pedido.setSinc(Sinc.N);
				}
				getInterfacePedido().modificarPedido(conn, pedido);
				//* elimino lista de lineas de pedido
				getInterfacePedidoLinea().eliminarListaPedidoLinea(conn, pedido);
				//* agrego nueva lista de lineas de pedido
				getInterfacePedidoLinea().guardarListaPedidoLinea(conn, pedido.getListaPedidoLinea());

				Conector.commitConn(conn);
			} else if(  (EstadoPedido.P.equals(pedido.getEstado()) || 
					( EstadoPedido.R.equals(pedido.getEstado()) && 
							(Sinc.N.equals(pedido.getSinc()) && Origen.W.equals(pedido.getOrigen())) 
									|| Origen.D.equals(pedido.getOrigen()) ) ||
					EstadoPedido.F.equals(pedido.getEstado())) 
					&& EstadoPedido.A.equals(estPedCambio)  ) {
				//ESTADOS: Pendiente[P] > Anulado[A] (dsk o web) | Revision[R] (no sinc) > Anulado[A] | PreConfirmado[F] > Anulado[A]
				pedido.setEstado(estPedCambio);
				if(pedido.getOrigen().equals(Origen.W)) {
					pedido.setSinc(Sinc.N);
				}
				getInterfacePedido().modificarPedido(conn, pedido);
				Conector.commitConn(conn);
			} else {
				throw new PresentacionException("El estado del pedido no permite actualizacion! Verifique.");
			}
		} catch (ProductoSinStockException psse) {
			throw psse;
		} catch (Exception e) {
			logger.fatal("Excepcion GENERICA en ManagerPedido > manejarStockLotePorProducto: " + e.getMessage(), e);
			throw new PresentacionException(e);
		}
		return null;
	}
	
	/**
	 * metodo que recibe al pedido y la transaccion, borra las lineas de transaccion y las guarda de nuevo ya que
	 * es una modificación... impacta el cabezal de transac con los importes del pedido.
	 * @param conn
	 * @param pedido
	 * @param transac
	 * @throws PresentacionException
	 */
	private void actualizarTransacDesdePedido(Connection conn, Pedido pedido, Transaccion transac) throws PresentacionException {
		try {
			if(pedido != null) {
				List<TranLinea> listaTransacLinea = new ArrayList<>();
				for(PedidoLinea pl : pedido.getListaPedidoLinea()) {
					TranLinea tl = new TranLinea(transac);
					Producto prod = pl.getProducto();
					tl.setProducto(prod);
					tl.setCantidad(pl.getCantidad());
					tl.setIva(Converters.redondearDosDec(pl.getIva()));
					tl.setPrecioUnit(Converters.redondearDosDec(pl.getPrecioUnit()));
					listaTransacLinea.add(tl);
				}
				transac.setSubTotal(Converters.redondearDosDec(pedido.getSubTotal()));
				transac.setIva(Converters.redondearDosDec(pedido.getIva()));
				transac.setTotal(Converters.redondearDosDec(pedido.getTotal()));
				
				//borro y agrego nuevas lineas
				getInterfaceTranLinea().eliminarTranLinea(conn, transac.getNroTransac());
				transac.setListaTranLinea(listaTransacLinea);
				getInterfaceTranLinea().guardarListaTranLinea(conn, listaTransacLinea);
				//actualizo transaccion con datos nuevos
				getInterfaceTransaccion().actualizarTransaccion(conn, transac);
			}
		} catch (PersistenciaException e) {
			logger.fatal("Excepcion en ManagerPedido > actualizarTransacDesdePedido: " + e.getMessage(), e);
			throw new PresentacionException(e);
		} catch (Exception e) {
			logger.fatal("Excepcion GENERICA en ManagerPedido > actualizarTransacDesdePedido: " + e.getMessage(), e);
			throw new PresentacionException(e);
		}
	}
	
	/**
	 * metodo para controlar el stock de cada linea del pedido.
	 * en caso de todo ok, devuelve null, si a algun prod no le alcanza el stock, retorna el mensaje correpondiente.
	 * @param conn
	 * @param pedido
	 * @return
	 * @throws PresentacionException
	 */
	public String controlarStocksPedido(Connection conn, Pedido pedido) throws PresentacionException {
		StringBuilder sb = new StringBuilder();
		if(pedido != null && pedido.getListaPedidoLinea() != null && !pedido.getListaPedidoLinea().isEmpty()) {
			ManagerProducto mgrProd = new ManagerProducto();
			for(PedidoLinea pl : pedido.getListaPedidoLinea()) {
				Producto prod = pl.getProducto();
				HlpProducto hlpProd = mgrProd.obtenerStockPrecioLotePorProductoNoConn(conn, prod.getIdProducto());
				if(hlpProd.getStock().longValue() < pl.getCantidad().longValue()) {
					sb.append("Atencion: El producto [" + prod.getIdProducto() + "-" + prod.getNombre() + "] no tiene stock suficiente: "
							+ "se requieren " + pl.getCantidad() + " unidades, el stock es " + hlpProd.getStock() + " unidades.").append(ESC);
				}
			}
		}
		return !sb.toString().equals("") ? sb.toString() : null;
	}
	
	/*****************************************************************************************************************************************************/
	/** PEDIDO LINEA */
	/*****************************************************************************************************************************************************/
	
	public Pedido obtenerPedidoLinPorId(Long idPersona, Fecha fechaHora, Integer idProducto) throws PresentacionException {
		Pedido pedido = null;
		try (Connection conn = Conector.getConn()) {
			pedido = getInterfacePedido().obtenerPedidoPorId(conn, idPersona, fechaHora);
			if(pedido != null) {
				List<PedidoLinea> listaPedidoLinea = getInterfacePedidoLinea().obtenerListaPedidoLinea(conn, pedido);
				if(listaPedidoLinea != null && !listaPedidoLinea.isEmpty()) {
					pedido.setListaPedidoLinea(listaPedidoLinea);
				}
			}
		} catch (PersistenciaException | SQLException e) {
			logger.fatal("Excepcion en ManagerPedido > obtenerPedidoLinPorId: " + e.getMessage(), e);
			throw new PresentacionException(e);
		} catch (Exception e) {
			logger.fatal("Excepcion GENERICA en ManagerPedido > obtenerPedidoLinPorId: " + e.getMessage(), e);
			throw new PresentacionException(e);
		}
		return pedido;
	}
}
