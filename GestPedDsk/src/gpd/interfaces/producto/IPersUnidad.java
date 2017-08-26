package gpd.interfaces.producto;

import java.sql.Connection;
import java.util.List;

import gpd.dominio.producto.Unidad;
import gpd.dominio.util.Sinc;
import gpd.exceptions.PersistenciaException;

public interface IPersUnidad {

	public Unidad obtenerUnidadPorId(Connection conn, Integer id) throws PersistenciaException;
	public List<Unidad> obtenerListaUnidad(Connection conn) throws PersistenciaException;
	public Integer guardarUnidad(Connection conn, Unidad unidad) throws PersistenciaException;
	public Integer modificarUnidad(Connection conn, Unidad unidad) throws PersistenciaException;
	public Integer modificarSincUnidad(Connection conn, Integer idUnidad, Sinc sinc) throws PersistenciaException;
	public Integer eliminarUnidad(Connection conn, Unidad unidad) throws PersistenciaException;
	public Boolean controlUtilUnidad(Connection conn, Unidad unidad) throws PersistenciaException;
	
}
