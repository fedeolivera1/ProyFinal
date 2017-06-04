package gpd.interfaces.producto;

import java.util.List;

import gpd.dominio.producto.Utilidad;
import gpd.exceptions.PersistenciaException;

public interface IPersUtilidad {

	public Utilidad obtenerUtilidadPorId(Integer id) throws PersistenciaException;
	public List<Utilidad> obtenerListaUtilidad() throws PersistenciaException;
	public Integer guardarUtilidad(Utilidad utilidad) throws PersistenciaException;
	public Integer modificarUtilidad(Utilidad  utilidad) throws PersistenciaException;
	public Integer eliminarUtilidad(Utilidad utilidad) throws PersistenciaException;
	
}
