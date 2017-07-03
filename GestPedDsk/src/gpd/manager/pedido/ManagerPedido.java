package gpd.manager.pedido;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import gpd.dominio.pedido.EstadoPedido;
import gpd.dominio.pedido.Pedido;
import gpd.dominio.pedido.PedidoLinea;
import gpd.dominio.producto.Producto;
import gpd.dominio.transaccion.EstadoTran;
import gpd.dominio.transaccion.TipoTran;
import gpd.dominio.transaccion.TranLinea;
import gpd.dominio.transaccion.Transaccion;
import gpd.dominio.util.Converters;
import gpd.dominio.util.Origen;
import gpd.dominio.util.Sinc;
import gpd.exceptions.PersistenciaException;
import gpd.exceptions.PresentacionException;
import gpd.exceptions.ProductoSinStockException;
import gpd.interfaces.pedido.IPersPedido;
import gpd.interfaces.pedido.IPersPedidoLinea;
import gpd.interfaces.transaccion.IPersTransaccion;
import gpd.manager.producto.ManagerProducto;
import gpd.manager.transaccion.ManagerTransaccion;
import gpd.persistencia.conector.Conector;
import gpd.persistencia.pedido.PersistenciaPedido;
import gpd.persistencia.pedido.PersistenciaPedidoLinea;
import gpd.persistencia.transaccion.PersistenciaTransaccion;
import gpd.types.Fecha;

public class ManagerPedido {

	private static final Logger logger = Logger.getLogger(ManagerPedido.class);
	private static IPersPedido interfacePedido;
	private static IPersPedidoLinea interfacePedidoLinea;
	private static IPersTransaccion interfaceTransac;
	
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
	
	/*****************************************************************************************************************************************************/
	/** PEDIDO */
	/*****************************************************************************************************************************************************/
	
	public Pedido obtenerPedidoPorId(Long idPersona, Fecha fechaHora) throws PresentacionException {
		Pedido pedido = null;
		try {
			Conector.getConn();
			pedido = getInterfacePedido().obtenerPedidoPorId(idPersona, fechaHora);
			if(pedido != null) {
				List<PedidoLinea> listaPedidoLinea = getInterfacePedidoLinea().obtenerListaPedidoLinea(pedido);
				if(listaPedidoLinea != null && !listaPedidoLinea.isEmpty()) {
					pedido.setListaPedidoLinea(listaPedidoLinea);
				}
			}
			Conector.closeConn("obtenerPedidoPorId");
		} catch (PersistenciaException e) {
			logger.fatal("Excepcion en ManagerTransaccion > obtenerPedidoPorId: " + e.getMessage(), e);
			throw new PresentacionException(e);
		}
		return pedido;
	}
	
	public List<Pedido> obtenerListaPedidoPorPeriodo(EstadoPedido ep, Long idPersona, Fecha fechaIni, Fecha fechaFin) throws PresentacionException {
		List<Pedido> listaPedido = null;
		try {
			Conector.getConn();
			listaPedido = getInterfacePedido().obtenerListaPedido(ep, idPersona, fechaIni, fechaFin);
			if(listaPedido != null && !listaPedido.isEmpty()) {
				for(Pedido pedido : listaPedido) {
					List<PedidoLinea> listaPedidoLinea = getInterfacePedidoLinea().obtenerListaPedidoLinea(pedido);
					if(listaPedidoLinea != null && !listaPedidoLinea.isEmpty()) {
						pedido.setListaPedidoLinea(listaPedidoLinea);
					}
				}
			}
			Conector.closeConn("obtenerListaPedidoPorPeriodo");
		} catch (PersistenciaException e) {
			logger.fatal("Excepcion en ManagerTransaccion > obtenerListaPedidoPorPeriodo: " + e.getMessage(), e);
			throw new PresentacionException(e);
		}
		return listaPedido;
	}
	
	public Integer generarNuevoPedido(Pedido pedido) throws PresentacionException {
		try {
			if(pedido != null && pedido.getListaPedidoLinea() != null &&
					!pedido.getListaPedidoLinea().isEmpty()) {
				//se genera nueva transaccion de VENTA con estado P
				Conector.getConn();
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
					pl.setSinc(sinc);
					pl.setUltAct(ultAct);
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
				mgrTransac.generarTransaccionVenta(transac);
				//persisto el pedido en base
				getInterfacePedido().guardarPedido(pedido);
				//persisto lineas de pedido en base
				getInterfacePedidoLinea().guardarListaPedidoLinea(pedido.getListaPedidoLinea());
			}
			Conector.closeConn("generarNuevoPedido");
		} catch (PersistenciaException e) {
			logger.fatal("Excepcion en ManagerTransaccion > generarNuevoPedido: " + e.getMessage(), e);
			throw new PresentacionException(e);
		}
		return null;
	}
	
	/**
	 * metodo para actualizar pedidos tanto para ventas (a estado confirmado), como para pedidos pendientes que
	 * se cambian sus lineas o actualizan desde el sistema web
	 * @param pedido
	 * @param estadoPedido
	 * @return
	 * @throws PresentacionException
	 * @throws ProductoSinStockException
	 */
	public Integer actualizarPedido(Pedido pedido, EstadoPedido estadoPedido) throws PresentacionException, ProductoSinStockException {
		try {
			if(pedido.getEstado().equals(EstadoPedido.P) && estadoPedido.equals(EstadoPedido.C)) {
				logger.info("<< Ingresa actualizacion de pedidos >> estado actual: " + pedido.getEstado().getEstadoPedido() + 
						"estado actualiza: " + estadoPedido.getEstadoPedido() + " [actualizacion a venta]");
				ManagerProducto mgrProd = new ManagerProducto();
				pedido.setEstado(estadoPedido);
				// en caso de que alcance Y que el estadoPedido sea C (confirmado)... 
				// comienzo a bajar stock por producto con fecha de vencimiento mas cercana a hoy
				for(PedidoLinea pl : pedido.getListaPedidoLinea()) {
					mgrProd.manejarStockLotePorProducto(pl.getProducto().getIdProducto(), pl.getCantidad());
				}
				getInterfacePedido().modificarPedido(pedido);
				//modifico estado de transaccion a confirmado (seteo aca mismo el estado tran a C)
				Transaccion transac = pedido.getTransaccion();
				transac.setEstadoTran(EstadoTran.C);
				getInterfaceTransaccion().modificarEstadoTransaccion(transac);
				//guardo nuevo estado en tabla de estados (seteo fecha-hora temporalmente para estado_tran)
				transac.setFechaHora(new Fecha(Fecha.AMDHMS));
				getInterfaceTransaccion().guardarTranEstado(transac);
			} else if(pedido.getEstado().equals(EstadoPedido.P) && estadoPedido.equals(EstadoPedido.P)) {
				logger.info("<< Ingresa actualizacion de pedidos >> estado actual: " + pedido.getEstado().getEstadoPedido() + 
						"estado actualiza: " + estadoPedido.getEstadoPedido() + " [actualizacion de lineas de pedido en estado C]");
				Double subTotal = new Double(0);
				Double ivaTotal = new Double(0);
				Double total = new Double(0);
				for(PedidoLinea pl : pedido.getListaPedidoLinea()) {
					total += pl.getPrecioUnit() * pl.getCantidad();
					ivaTotal += pl.getIva() * pl.getCantidad();
//					pl.setSinc(sinc);		//FIXME ver esto de acuerdo a si va a tener sinc y ult_act la linea
//					pl.setUltAct(ultAct);	//FIXME ver esto de acuerdo a si va a tener sinc y ult_act la linea
				}
				subTotal = total - ivaTotal;
				subTotal = Converters.redondearDosDec(subTotal);
				ivaTotal = Converters.redondearDosDec(ivaTotal);
				total = Converters.redondearDosDec(total);
				pedido.setSubTotal(subTotal);
				pedido.setIva(ivaTotal);
				pedido.setTotal(total);
				getInterfacePedido().modificarPedido(pedido);
				//elimino lista de lineas de pedido
				getInterfacePedidoLinea().eliminarListaPedidoLinea(pedido);
				//agrego nueva lista de lineas de pedido
				getInterfacePedidoLinea().guardarListaPedidoLinea(pedido.getListaPedidoLinea());
			} else {
				throw new PresentacionException("actualizaPedido mal implementado!!!");
			}
		} catch (PresentacionException pe) {
			throw pe;
		} catch (ProductoSinStockException psse) {
			throw psse;
		} catch (Exception e) {
			throw new PresentacionException(e);
		}
		return null;
	}
	
	/*****************************************************************************************************************************************************/
	/** PEDIDO LINEA */
	/*****************************************************************************************************************************************************/
	
	public Pedido obtenerPedidoLinPorId(Long idPersona, Fecha fechaHora, Integer idProducto) throws PresentacionException {
		Pedido pedido = null;
		try {
			Conector.getConn();
			pedido = getInterfacePedido().obtenerPedidoPorId(idPersona, fechaHora);
			if(pedido != null) {
				List<PedidoLinea> listaPedidoLinea = getInterfacePedidoLinea().obtenerListaPedidoLinea(pedido);
				if(listaPedidoLinea != null && !listaPedidoLinea.isEmpty()) {
					pedido.setListaPedidoLinea(listaPedidoLinea);
				}
			}
			Conector.closeConn("obtenerPedidoPorId");
		} catch (PersistenciaException e) {
			logger.fatal("Excepcion en ManagerTransaccion > obtenerPedidoPorId: " + e.getMessage(), e);
			throw new PresentacionException(e);
		}
		return pedido;
	}
}
