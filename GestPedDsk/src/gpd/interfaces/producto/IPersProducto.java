package gpd.interfaces.producto;

import java.util.List;

import gpd.dominio.producto.Producto;
import gpd.dominio.producto.TipoProd;
import gpd.exceptions.PersistenciaException;

public interface IPersProducto {

	public List<Producto> obtenerBusquedaProducto(Integer idTipoProd, String codigo, String nombre, String descripcion) throws PersistenciaException;
	public Producto obtenerProductoPorId(Integer id) throws PersistenciaException;
	public List<Producto> obtenerListaProductoPorTipo(TipoProd tipoProd) throws PersistenciaException;
	public Integer guardarProducto(Producto producto) throws PersistenciaException;
	public Integer modificarProducto(Producto producto) throws PersistenciaException;
	public Integer desactivarProducto(Producto producto) throws PersistenciaException;

}
