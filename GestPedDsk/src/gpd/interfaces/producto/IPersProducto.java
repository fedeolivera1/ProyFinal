package gpd.interfaces.producto;

import java.util.List;

import gpd.dominio.producto.Producto;
import gpd.dominio.producto.TipoProd;
import gpd.exceptions.PersistenciaException;

public interface IPersProducto {

	//producto
	public Producto obtenerProductoPorId(Integer id) throws PersistenciaException;
	public List<Producto> obtenerListaProductoPorTipo(TipoProd tipoProd) throws PersistenciaException;
	public Integer guardarProducto(Producto producto) throws PersistenciaException;
	public Integer modificarProducto(Producto producto) throws PersistenciaException;
	public Integer eliminarProducto(Producto producto) throws PersistenciaException;
	//tipo prod
	public TipoProd obtenerTipoProdPorId(Integer id) throws PersistenciaException;
	public List<TipoProd> obtenerListaTipoProd() throws PersistenciaException;
	public Integer guardarTipoProd(TipoProd tipoProd) throws PersistenciaException;
	public Integer modificarTipoProd(TipoProd  tipoProd) throws PersistenciaException;
	public Integer eliminarTipoProd(TipoProd tipoProd) throws PersistenciaException;
}
