package gpd.interfaces.transaccion;

import java.util.List;

import gpd.dominio.transaccion.TranLinea;
import gpd.exceptions.PersistenciaException;

public interface IPersTranLinea {

	public List<TranLinea> obtenerListaTranLinea(Long nroTransac) throws PersistenciaException;
	public Integer guardarListaTranLinea(List<TranLinea> tranLinea) throws PersistenciaException;
	public Integer eliminarTranLinea(Long nroTransac) throws PersistenciaException;
	
}
