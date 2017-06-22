package gpd.manager.pedido;

import java.util.List;

import org.apache.log4j.Logger;

import gpd.dominio.pedido.EstadoPedido;
import gpd.dominio.pedido.Pedido;
import gpd.dominio.pedido.PedidoLinea;
import gpd.exceptions.PersistenciaException;
import gpd.exceptions.PresentacionException;
import gpd.interfaces.pedido.IPersPedido;
import gpd.interfaces.pedido.IPersPedidoLinea;
import gpd.persistencia.conector.Conector;
import gpd.persistencia.pedido.PersistenciaPedido;
import gpd.persistencia.pedido.PersistenciaPedidoLinea;
import gpd.types.Fecha;

public class ManagerPedido {

	private static final Logger logger = Logger.getLogger(ManagerPedido.class);
	private static IPersPedido interfacePedido;
	private static IPersPedidoLinea interfacePedidoLinea;
	
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
}
