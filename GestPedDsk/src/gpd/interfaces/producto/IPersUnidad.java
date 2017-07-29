package gpd.interfaces.producto;

import java.util.List;

import gpd.dominio.producto.Unidad;
import gpd.exceptions.PersistenciaException;

public interface IPersUnidad {

	public Unidad obtenerUnidadPorId(Integer id) throws PersistenciaException;
	public List<Unidad> obtenerListaUnidad() throws PersistenciaException;
	public Integer guardarUnidad(Unidad unidad) throws PersistenciaException;
	public Integer modificarUnidad(Unidad unidad) throws PersistenciaException;
	public Integer modificarSincUnidad(Unidad unidad) throws PersistenciaException;
	public Integer eliminarUnidad(Unidad unidad) throws PersistenciaException;
	
}
