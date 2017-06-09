package gpd.interfaces.persona;

import java.util.List;

import gpd.dominio.persona.Departamento;
import gpd.dominio.persona.Localidad;
import gpd.exceptions.PersistenciaException;

public interface IPersDepLoc {

	public List<Departamento> obtenerListaDepartamentos() throws PersistenciaException;
	public Departamento obtenerDepartamentoPorId(Integer id) throws PersistenciaException;
	public List<Localidad> obtenerListaLocPorDep(Integer idDep) throws PersistenciaException;
	public Localidad obtenerLocalidadPorId(Integer idLoc) throws PersistenciaException;
	
}
