package gpd.interfaces.persona;

import java.util.List;

import gpd.dominio.persona.Persona;
import gpd.dominio.persona.PersonaFisica;
import gpd.dominio.persona.PersonaJuridica;
import gpd.dominio.util.Sinc;
import gpd.exceptions.PersistenciaException;
import gpd.types.Fecha;

public interface IPersPersona {
	
	//pf
	public PersonaFisica obtenerPersFisicaPorId(Long id) throws PersistenciaException;
	public List<PersonaFisica> obtenerBusquedaPersFisica(Long documento, String ape1, String ape2, String nom1, String nom2, 
			Character sexo, String direccion, String telefono, String celular, String email, Integer idLoc) throws PersistenciaException;
	public List<PersonaFisica> obtenerBusquedaPersFisicaGenerico(String filtroBusq, Integer idLoc) throws PersistenciaException;
	public Integer guardarPersFisica(PersonaFisica pf) throws PersistenciaException;
	public Integer modificarPersFisica(PersonaFisica pf) throws PersistenciaException;
	public Integer eliminarPersFisica(PersonaFisica pf) throws PersistenciaException;
	//pj
	public PersonaJuridica obtenerPersJuridicaPorId(Long id) throws PersistenciaException;
	public List<PersonaJuridica> obtenerBusquedaPersJuridica(Long rut, String nombre, String razonSoc, String bps, String bse, 
			Boolean esProv, String direccion, String telefono, String celular, String email, Integer loc) throws PersistenciaException;
	public List<PersonaJuridica> obtenerBusquedaPersJuridicaGenerico(String filtroBusq, Integer idLoc) throws PersistenciaException;
	public List<PersonaJuridica> obtenerListaEmpresasPorTipo(Boolean esProv) throws PersistenciaException;
	public Integer guardarPersJuridica(PersonaJuridica pj) throws PersistenciaException;
	public Integer modificarPersJuridica(PersonaJuridica pj) throws PersistenciaException;
	public Integer eliminarPersJuridica(PersonaJuridica pj) throws PersistenciaException;
	//generico
	public Persona obtenerPersGenerico(Long idPersona) throws PersistenciaException;
	public Boolean checkExistPersona(Long idPersona) throws PersistenciaException;
	public Integer actualizarSincPersona(Long idPersona, Sinc sinc, Fecha ultAct) throws PersistenciaException;
}
