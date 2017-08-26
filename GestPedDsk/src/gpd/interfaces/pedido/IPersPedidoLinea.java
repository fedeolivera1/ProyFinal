package gpd.interfaces.pedido;

import java.sql.Connection;
import java.util.List;

import gpd.dominio.pedido.Pedido;
import gpd.dominio.pedido.PedidoLinea;
import gpd.exceptions.PersistenciaException;

public interface IPersPedidoLinea {

	public List<PedidoLinea> obtenerListaPedidoLinea(Connection conn, Pedido pedido) throws PersistenciaException;
	public Integer guardarListaPedidoLinea(Connection conn, List<PedidoLinea> listaPedidoLinea) throws PersistenciaException;
	public Integer eliminarListaPedidoLinea(Connection conn, Pedido pedido) throws PersistenciaException;
	
}
