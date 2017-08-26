package gpd.interfaces.transaccion;

import java.sql.Connection;
import java.util.List;

import gpd.dominio.transaccion.TranLinea;
import gpd.dominio.transaccion.Transaccion;
import gpd.exceptions.PersistenciaException;

public interface IPersTranLinea {

	public TranLinea obtenerTranLineaPorId(Connection conn, Integer nroTransac, Integer idProducto) throws PersistenciaException;
	public List<TranLinea> obtenerListaTranLinea(Connection conn, Transaccion transac) throws PersistenciaException;
	public Integer guardarListaTranLinea(Connection conn, List<TranLinea> tranLinea) throws PersistenciaException;
	public Integer eliminarTranLinea(Connection conn, Integer nroTransac) throws PersistenciaException;
	
}
