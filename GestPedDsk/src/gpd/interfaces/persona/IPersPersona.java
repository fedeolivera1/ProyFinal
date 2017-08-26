package gpd.interfaces.persona;

import java.sql.Connection;
import java.util.List;

import gpd.dominio.persona.Persona;
import gpd.dominio.persona.PersonaFisica;
import gpd.dominio.persona.PersonaJuridica;
import gpd.dominio.util.Sinc;
import gpd.exceptions.PersistenciaException;
import gpd.types.Fecha;

public interface IPersPersona {
	
	//pf
	public PersonaFisica obtenerPersFisicaPorId(Connection conn, Long id) throws PersistenciaException;
	public List<PersonaFisica> obtenerBusquedaPersFisica(Connection conn, Long documento, String ape1, String ape2, String nom1, String nom2, 
			Character sexo, String direccion, String telefono, String celular, String email, Integer idLoc) throws PersistenciaException;
	public List<PersonaFisica> obtenerBusquedaPersFisicaGenerico(Connection conn, String filtroBusq, Integer idLoc) throws PersistenciaException;
	public Integer guardarPersFisica(Connection conn, PersonaFisica pf) throws PersistenciaException;
	public Integer modificarPersFisica(Connection conn, PersonaFisica pf) throws PersistenciaException;
	public Integer eliminarPersFisica(Connection conn, PersonaFisica pf) throws PersistenciaException;
	//pj
	public PersonaJuridica obtenerPersJuridicaPorId(Connection conn, Long id) throws PersistenciaException;
	public List<PersonaJuridica> obtenerBusquedaPersJuridica(Connection conn, Long rut, String nombre, String razonSoc, String bps, String bse, 
			Boolean esProv, String direccion, String telefono, String celular, String email, Integer loc) throws PersistenciaException;
	public List<PersonaJuridica> obtenerBusquedaPersJuridicaGenerico(Connection conn, String filtroBusq, Integer idLoc) throws PersistenciaException;
	public List<PersonaJuridica> obtenerListaEmpresasPorTipo(Connection conn, Boolean esProv) throws PersistenciaException;
	public Integer guardarPersJuridica(Connection conn, PersonaJuridica pj) throws PersistenciaException;
	public Integer modificarPersJuridica(Connection conn, PersonaJuridica pj) throws PersistenciaException;
	public Integer eliminarPersJuridica(Connection conn, PersonaJuridica pj) throws PersistenciaException;
	//generico
	public Persona obtenerPersGenerico(Connection conn, Long idPersona) throws PersistenciaException;
	public Boolean checkExistPersona(Connection conn, Long idPersona) throws PersistenciaException;
	public Integer actualizarSincPersona(Connection conn, Long idPersona, Sinc sinc, Fecha ultAct) throws PersistenciaException;
}
