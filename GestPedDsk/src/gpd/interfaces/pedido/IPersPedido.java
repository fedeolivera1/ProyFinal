package gpd.interfaces.pedido;

import java.sql.Connection;
import java.util.List;

import gpd.dominio.pedido.EstadoPedido;
import gpd.dominio.pedido.Pedido;
import gpd.dominio.util.Origen;
import gpd.exceptions.PersistenciaException;
import gpd.types.Fecha;

public interface IPersPedido {

	public Pedido obtenerPedidoPorId(Connection conn, Long idPersona, Fecha fechaHora) throws PersistenciaException;
	public List<Pedido> obtenerListaPedido(Connection conn, EstadoPedido ep, Long idPersona, Origen origen, Fecha fechaDesde, Fecha fechaHasta) throws PersistenciaException;
	public List<Pedido> obtenerListaPedidoNoSincWeb(Connection conn, EstadoPedido ep, Fecha fechaDesde, Fecha fechaHasta) throws PersistenciaException;
	public Integer guardarPedido(Connection conn, Pedido pedido) throws PersistenciaException;
	public Integer modificarPedido(Connection conn, Pedido pedido) throws PersistenciaException;
	public Integer eliminarPedido(Connection conn, Pedido pedido) throws PersistenciaException;
	public Boolean checkExistPedido(Connection conn, Pedido pedido) throws PersistenciaException;
	public Integer modificarSincUltActPedido(Connection conn, Pedido pedido) throws PersistenciaException;

}