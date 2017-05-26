package gpd.interfaces.producto;

import java.util.List;

import gpd.dominio.producto.Producto;
import gpd.dominio.producto.TipoProd;
import gpd.exceptions.PersistenciaException;

public interface IPersProducto {

	//producto
	public Producto obtenerProductoPorId(Integer id) throws PersistenciaException;
	public List<Producto> obtenerListaProductoPorTipo(TipoProd tipoProd) throws PersistenciaException;
	public Integer guardarProducto(Producto producto);
	public Integer modificarProducto(Producto producto);
	public Integer eliminarProducto(Producto producto);
	//tipo prod
	public TipoProd obtenerTipoProdPorId(Integer id) throws PersistenciaException;
	public List<TipoProd> obtenerListaTipoProd() throws PersistenciaException;
	public Integer guardarTipoProd(TipoProd tipoProd);
	public Integer modificarTipoProd(TipoProd  tipoProd);
	public Integer eliminarTipoProd(TipoProd tipoProd);
}
