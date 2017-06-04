package gpd.interfaces.producto;

import java.util.List;

import gpd.dominio.producto.TipoProd;
import gpd.exceptions.PersistenciaException;

public interface IPersTipoProd {

	public TipoProd obtenerTipoProdPorId(Integer id) throws PersistenciaException;
	public List<TipoProd> obtenerListaTipoProd() throws PersistenciaException;
	public Integer guardarTipoProd(TipoProd tipoProd) throws PersistenciaException;
	public Integer modificarTipoProd(TipoProd  tipoProd) throws PersistenciaException;
	public Integer eliminarTipoProd(TipoProd tipoProd) throws PersistenciaException;
	
}
