package gpd.interfaces.producto;

import java.sql.Connection;
import java.util.List;

import gpd.dominio.producto.Utilidad;
import gpd.exceptions.PersistenciaException;

public interface IPersUtilidad {

	public Utilidad obtenerUtilidadPorId(Connection conn, Integer id) throws PersistenciaException;
	public List<Utilidad> obtenerListaUtilidad(Connection conn) throws PersistenciaException;
	public Integer guardarUtilidad(Connection conn, Utilidad utilidad) throws PersistenciaException;
	public Integer modificarUtilidad(Connection conn, Utilidad  utilidad) throws PersistenciaException;
	public Integer eliminarUtilidad(Connection conn, Utilidad utilidad) throws PersistenciaException;
	
}
