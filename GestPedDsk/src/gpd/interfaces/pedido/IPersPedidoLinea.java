package gpd.interfaces.pedido;

import java.util.List;

import gpd.dominio.pedido.Pedido;
import gpd.dominio.pedido.PedidoLinea;
import gpd.exceptions.PersistenciaException;

public interface IPersPedidoLinea {

	public List<PedidoLinea> obtenerListaPedidoLinea(Pedido pedido) throws PersistenciaException;
	public Integer guardarListaPedidoLinea(List<PedidoLinea> listaPedidoLinea) throws PersistenciaException;
	public Integer eliminarListaPedidoLinea(Pedido pedido) throws PersistenciaException;
	
}
