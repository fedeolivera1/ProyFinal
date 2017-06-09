package gpd.interfaces.transaccion;

import gpd.dominio.transaccion.Transaccion;
import gpd.exceptions.PersistenciaException;

public interface IPersTransaccion {

	public Integer guardarTransaccionCompra(Transaccion transaccion) throws PersistenciaException;
	public Integer guardarTransaccionVenta(Transaccion transaccion) throws PersistenciaException;
	
}
