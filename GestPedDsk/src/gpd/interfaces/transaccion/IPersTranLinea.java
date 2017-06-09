package gpd.interfaces.transaccion;

import java.util.List;

import gpd.dominio.transaccion.TranLinea;
import gpd.exceptions.PersistenciaException;

public interface IPersTranLinea {

	public Integer guardarListaTranLinea(List<TranLinea> tranLinea) throws PersistenciaException;
	
}
