package gpd.interfaces.persona;

import java.util.List;

import gpd.dominio.persona.TipoDoc;
import gpd.exceptions.PersistenciaException;

public interface IPersTipoDoc {

	public TipoDoc obtenerTipoDocPorId(Integer id) throws PersistenciaException;
	public List<TipoDoc> obtenerListaTipoDoc() throws PersistenciaException;
	public Integer guardarTipoDoc(TipoDoc tipoDoc) throws PersistenciaException;
	public Integer modificarTipoDoc(TipoDoc  tipoDoc) throws PersistenciaException;
	public Integer eliminarTipoDoc(TipoDoc tipoDoc) throws PersistenciaException;
	
}
