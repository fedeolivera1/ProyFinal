package gpd.interfaces.persona;

import java.util.List;

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
	public PersonaFisica obtenerPersFisicaPorId(Integer id) throws PersistenciaException;
	public List<PersonaFisica> obtenerBusquedaPersFisica(String x) throws PersistenciaException;
	public Integer guardarPersFisica(PersonaFisica pf) throws PersistenciaException;
	public Integer modificarPersFisica(PersonaFisica pf) throws PersistenciaException;
	public Integer eliminarPersFisica(PersonaFisica pf) throws PersistenciaException;
	//pj
	public PersonaJuridica obtenerPersJuridicaPorId(Integer id) throws PersistenciaException;
	public List<PersonaJuridica> obtenerBusquedaPersJuridica(String x) throws PersistenciaException;
	public Integer guardarPersJuridica(PersonaJuridica pj) throws PersistenciaException;
	public Integer modificarPersJuridica(PersonaJuridica pj) throws PersistenciaException;
	public Integer eliminarPersJuridica(PersonaJuridica pj) throws PersistenciaException;
	
}
