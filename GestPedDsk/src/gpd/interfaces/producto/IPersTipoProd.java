package gpd.interfaces.producto;

import java.sql.Connection;
import java.util.List;

import gpd.dominio.producto.TipoProd;
import gpd.dominio.util.Sinc;
import gpd.exceptions.PersistenciaException;

public interface IPersTipoProd {

	public TipoProd obtenerTipoProdPorId(Connection conn, Integer id) throws PersistenciaException;
	public List<TipoProd> obtenerListaTipoProd(Connection conn) throws PersistenciaException;
	public Integer guardarTipoProd(Connection conn, TipoProd tipoProd) throws PersistenciaException;
	public Integer modificarTipoProd(Connection conn, TipoProd  tipoProd) throws PersistenciaException;
	public Integer modificarSincTipoProd(Connection conn, Integer idTipoProd, Sinc sinc) throws PersistenciaException;
	public Integer eliminarTipoProd(Connection conn, TipoProd tipoProd) throws PersistenciaException;
	public Boolean controlUtilTipoProd(Connection conn, TipoProd tipoProd) throws PersistenciaException;
	
}
