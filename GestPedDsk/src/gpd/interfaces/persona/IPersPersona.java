package gpd.interfaces.persona;

import java.util.List;

import gpd.dominio.persona.Departamento;
import gpd.dominio.persona.Localidad;
import gpd.dominio.persona.PersonaFisica;
import gpd.dominio.persona.PersonaJuridica;
import gpd.dominio.persona.TipoDoc;
import gpd.exceptions.PersistenciaException;

public interface IPersPersona {
	
	//tipo doc
	public TipoDoc obtenerTipoDocPorId(Integer id) throws PersistenciaException;
	public List<TipoDoc> obtenerListaTipoDoc() throws PersistenciaException;
	public Integer guardarTipoDoc(TipoDoc tipoDoc) throws PersistenciaException;
	public Integer modificarTipoDoc(TipoDoc  tipoDoc) throws PersistenciaException;
	public Integer eliminarTipoDoc(TipoDoc tipoDoc) throws PersistenciaException;
	//pf
	public PersonaFisica obtenerPersFisicaPorId(Long id) throws PersistenciaException;
	public List<PersonaFisica> obtenerBusquedaPersFisica(Long documento, String ape1, String ape2, String nom1, String nom2, 
			Character sexo, String direccion, String telefono, String celular, String email, Integer idLoc) throws PersistenciaException;
	public Integer guardarPersFisica(PersonaFisica pf) throws PersistenciaException;
	public Integer modificarPersFisica(PersonaFisica pf) throws PersistenciaException;
	public Integer eliminarPersFisica(PersonaFisica pf) throws PersistenciaException;
	//pj
	public PersonaJuridica obtenerPersJuridicaPorId(Long id) throws PersistenciaException;
	public List<PersonaJuridica> obtenerBusquedaPersJuridica(String x) throws PersistenciaException;
	public Integer guardarPersJuridica(PersonaJuridica pj) throws PersistenciaException;
	public Integer modificarPersJuridica(PersonaJuridica pj) throws PersistenciaException;
	public Integer eliminarPersJuridica(PersonaJuridica pj) throws PersistenciaException;
	//loc_dep
	public List<Departamento> obtenerListaDepartamentos() throws PersistenciaException;
	public Departamento obtenerDepartamentoPorId(Integer id) throws PersistenciaException;
	public List<Localidad> obtenerListaLocPorDep(Integer idDep) throws PersistenciaException;
	public Localidad obtenerLocalidadPorId(Integer idLoc) throws PersistenciaException;
}
