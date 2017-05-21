package gpd.interfaces.persona;

import gpd.dominio.persona.PersonaFisica;
import gpd.dominio.persona.PersonaJuridica;

public interface IPersPersona {
	
//	Integer resultado = null;
	public PersonaFisica obtenerPersFisicaPorId(Integer id);
	public PersonaJuridica obtenerPersJuridicaPorId(Integer id);
	public Integer guardarPersFisica(PersonaFisica pf);
	public Integer guardarPersJuridica(PersonaJuridica pj);
	
}
