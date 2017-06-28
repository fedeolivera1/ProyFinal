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
import gpd.exceptions.PersistenciaException;
import gpd.exceptions.PresentacionException;
import gpd.interfaces.pedido.IPersPedido;
import gpd.interfaces.pedido.IPersPedidoLinea;
import gpd.manager.transaccion.ManagerTransaccion;
import gpd.persistencia.conector.Conector;
import gpd.persistencia.pedido.PersistenciaPedido;
import gpd.persistencia.pedido.PersistenciaPedidoLinea;
import gpd.types.Fecha;

public class ManagerPedido {

	private static final Logger logger = Logger.getLogger(ManagerPedido.class);
	private static IPersPedido interfacePedido;
	private static IPersPedidoLinea interfacePedidoLinea;
//	private static IPersTransaccion interfaceTransac;
	
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
//	private static IPersTransaccion getInterfaceTransac() {
//		if(interfaceTransac == null) {
//			interfaceTransac = new PersistenciaTransaccion();
//		}
//		return interfaceTransac;
//	}
	
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
				Transaccion transac = new Transaccion(TipoTran.V);
				transac.setEstadoTran(EstadoTran.P);
				transac.setPersona(pedido.getPersona());
				transac.setFechaHora(pedido.getFechaHora());
				transac.setSubTotal(pedido.getSubTotal());
				transac.setIva(pedido.getIva());
				transac.setTotal(pedido.getTotal());
				pedido.setTransaccion(transac);
				ManagerTransaccion mgrTransac = new ManagerTransaccion();
				//persisto lineas de pedido en base
				List<TranLinea> listaTransacLinea = new ArrayList<>();
				for(PedidoLinea pl : pedido.getListaPedidoLinea()) {
					TranLinea tl = new TranLinea(transac);
					Producto prod = pl.getProducto();
					tl.setProducto(prod);
					tl.setCantidad(pl.getCantidad());
					tl.setIva(pl.getIva());
					tl.setPrecioUnit(pl.getPrecioUnit());
					listaTransacLinea.add(tl);
				}
				transac.setListaTranLinea(listaTransacLinea);
				//generar venta
				mgrTransac.generarTransaccionVenta(transac);
				//persisto el pedido en base
				getInterfacePedido().guardarPedido(pedido);
				//persisto lineas de pedido
				getInterfacePedidoLinea().guardarListaPedidoLinea(pedido.getListaPedidoLinea());
			}
			Conector.closeConn("generarNuevoPedido");
		} catch (PersistenciaException e) {
			logger.fatal("Excepcion en ManagerTransaccion > generarNuevoPedido: " + e.getMessage(), e);
			throw new PresentacionException(e);
		}
		return null;
	}
	
	public Integer actualizarPedido(Pedido pedido) throws PresentacionException {
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
