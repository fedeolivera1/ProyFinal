package gpd.interfaces.producto;

import java.util.List;

import gpd.dominio.producto.Lote;
import gpd.dominio.transaccion.EstadoTran;
import gpd.exceptions.PersistenciaException;

public interface IPersLote {

	public List<Lote> obtenerListaLotePorEstado(EstadoTran estado) throws PersistenciaException;
	public Integer guardarListaLote(List<Lote> listaLote) throws PersistenciaException;
	public Integer guardarLote(Lote lote) throws PersistenciaException;
	public Integer modificarLote(Lote lote) throws PersistenciaException;
	public Integer eliminarLote(Lote lote) throws PersistenciaException;
	
}
