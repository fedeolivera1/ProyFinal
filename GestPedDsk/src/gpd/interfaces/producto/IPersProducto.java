package gpd.interfaces.producto;

import java.sql.Connection;
import java.util.List;

import gpd.dominio.producto.Producto;
import gpd.dominio.producto.TipoProd;
import gpd.dominio.util.Sinc;
import gpd.exceptions.PersistenciaException;
import gpd.types.Fecha;

public interface IPersProducto {

	public List<Producto> obtenerBusquedaProducto(Connection conn, Integer idTipoProd, String codigo, String nombre, String descripcion) throws PersistenciaException;
	public Producto obtenerProductoPorId(Connection conn, Integer id) throws PersistenciaException;
	public List<Producto> obtenerListaProductoPorTipo(Connection conn, TipoProd tipoProd) throws PersistenciaException;
	public List<Producto> obtenerListaProductoNoSinc(Connection conn, Fecha fechaDesde, Fecha fechaHasta) throws PersistenciaException;
	public Integer guardarProducto(Connection conn, Producto producto) throws PersistenciaException;
	public Integer modificarProducto(Connection conn, Producto producto) throws PersistenciaException;
	public Integer modificarSincProducto(Connection conn, Integer idProd, Sinc sinc, Fecha ultAct) throws PersistenciaException;
	public Integer desactivarProducto(Connection conn, Producto producto) throws PersistenciaException;

}
