package gpd.interfaces.persona;

import java.sql.Connection;
import java.util.List;

import gpd.dominio.persona.TipoDoc;
import gpd.exceptions.PersistenciaException;

public interface IPersTipoDoc {

	public TipoDoc obtenerTipoDocPorId(Connection conn, Integer id) throws PersistenciaException;
	public List<TipoDoc> obtenerListaTipoDoc(Connection conn) throws PersistenciaException;
	public Integer guardarTipoDoc(Connection conn, TipoDoc tipoDoc) throws PersistenciaException;
	public Integer modificarTipoDoc(Connection conn, TipoDoc  tipoDoc) throws PersistenciaException;
	public Integer eliminarTipoDoc(Connection conn, TipoDoc tipoDoc) throws PersistenciaException;
	
}
