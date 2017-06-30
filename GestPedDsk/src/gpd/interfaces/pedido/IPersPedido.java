package gpd.interfaces.pedido;

import java.util.List;

import gpd.dominio.pedido.EstadoPedido;
import gpd.dominio.pedido.Pedido;
import gpd.exceptions.PersistenciaException;
import gpd.types.Fecha;

public interface IPersPedido {

	public Pedido obtenerPedidoPorId(Long idPersona, Fecha fechaHora) throws PersistenciaException;
	public List<Pedido> obtenerListaPedido(EstadoPedido ep, Long idPersona, Fecha fechaDesde, Fecha fechaHasta) throws PersistenciaException;
	public Integer guardarPedido(Pedido pedido) throws PersistenciaException;
	public Integer modificarPedido(Pedido pedido) throws PersistenciaException;
	public Integer eliminarPedido(Pedido pedido) throws PersistenciaException;

}