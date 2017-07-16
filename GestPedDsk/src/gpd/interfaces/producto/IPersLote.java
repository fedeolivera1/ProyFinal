package gpd.interfaces.producto;

import java.util.List;

import gpd.dominio.producto.Lote;
import gpd.exceptions.PersistenciaException;

public interface IPersLote {

	public Lote obtenerLotePorId(Integer idLote) throws PersistenciaException;
	public Lote obtenerLotePorTransacProd(Integer nroTransac, Integer idProd) throws PersistenciaException;
	public List<Lote> obtenerListaLotePorTransac(Integer nroTransac) throws PersistenciaException;
	public List<Lote> obtenerListaLotePorProd(Integer idProducto, Integer diasParaVenc) throws PersistenciaException;
	public Integer guardarListaLote(List<Lote> listaLote) throws PersistenciaException;
	public Integer guardarLote(Lote lote) throws PersistenciaException;
	public Integer actualizarLote(Lote lote) throws PersistenciaException;
	public Integer actualizarStockLote(Integer idLote, Integer stock) throws PersistenciaException;
	public Integer eliminarLote(Lote lote) throws PersistenciaException;
	
}
