package gpd.ws.parsers;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import gpd.dominio.pedido.EstadoPedido;
import gpd.dominio.pedido.Pedido;
import gpd.dominio.pedido.PedidoLinea;
import gpd.dominio.persona.Persona;
import gpd.dominio.util.Origen;
import gpd.dominio.util.Sinc;
import gpd.exceptions.ParsersException;
import gpd.interfaces.persona.IPersPersona;
import gpd.interfaces.producto.IPersProducto;
import gpd.persistencia.persona.PersistenciaPersona;
import gpd.persistencia.producto.PersistenciaProducto;
import gpd.types.Fecha;
import gpw.webservice.proxy.ResultObtPedidosNoSinc;
import gpw.webservice.proxy.ResultPedidoLinea;
import gpw.webservice.proxy.ResultPedidoNoSinc;

public class ParserResultPedido {

	private static final Logger logger = Logger.getLogger(ParserResultPedido.class);
	private static IPersPersona interfacePersona;
	private static IPersProducto interfaceProducto;
	
	private static IPersPersona getInterfacePersona() {
		if(interfacePersona == null) {
			interfacePersona = new PersistenciaPersona();
		}
		return interfacePersona;
	}
	private static IPersProducto getInterfaceProducto() {
		if(interfaceProducto == null) {
			interfaceProducto = new PersistenciaProducto();
		}
		return interfaceProducto;
	}
	
	public static List<Pedido> parseResultPedidosNoSinc(ResultObtPedidosNoSinc result) throws ParsersException {
		List<Pedido> listaPedidosNoSinc = null;
		try {
			if( result != null && (result.getErroresServ() == null || result.getErroresServ().isEmpty()) ) {
				listaPedidosNoSinc = new ArrayList<>();
				for(ResultPedidoNoSinc resultPns : result.getListaPedidoNoSinc()) {
					Pedido pedido = new Pedido();
					Persona pers = getInterfacePersona().obtenerPersGenerico(resultPns.getIdPersona());
					pedido.setPersona(pers);
					pedido.setFechaHora(new Fecha(resultPns.getFechaHora(), Fecha.AMDHMS));
					pedido.setEstado(EstadoPedido.getEstadoPedidoPorChar(resultPns.getEstado().charAt(0)));
					pedido.setFechaProg(new Fecha(resultPns.getFechaProg(), Fecha.AMD));
					pedido.setHoraProg(new Fecha(resultPns.getHoraProg(), Fecha.HMS));
					pedido.setOrigen(Origen.getOrigenPorChar(resultPns.getOrigen().charAt(0)));
					pedido.setSubTotal(resultPns.getSubTotal());
					pedido.setIva(resultPns.getIva());
					pedido.setTotal(resultPns.getTotal());
					pedido.setUsuario(null);
					pedido.setTransaccion(null);
					pedido.setSinc(Sinc.getSincPorChar(resultPns.getSinc().charAt(0)));
					pedido.setUltAct(new Fecha(resultPns.getUltAct(), Fecha.AMDHMS));
					for(ResultPedidoLinea resultPl : resultPns.getListaPedidoLinea()) {
						PedidoLinea pl = new PedidoLinea(pedido);
						pl.setProducto(getInterfaceProducto().obtenerProductoPorId(resultPl.getIdProducto()));
						pl.setCantidad(resultPl.getCantidad());
						pl.setIva(resultPl.getIva());
						pl.setPrecioUnit(resultPl.getPrecioUnit());
						pedido.getListaPedidoLinea().add(pl);
					}
					listaPedidosNoSinc.add(pedido);
				}
			}
		} catch(Exception e) {
			logger.error("Excepcion al parsear en parseResultPedidosNoSinc: " + e.getMessage());
			throw new ParsersException(e);
		}
		return listaPedidosNoSinc;
	}
}
