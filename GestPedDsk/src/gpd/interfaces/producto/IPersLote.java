package gpd.interfaces.producto;

import java.util.List;

import gpd.dominio.producto.Lote;
import gpd.exceptions.PersistenciaException;

public interface IPersLote {

	public Lote obtenerLotePorTransacProd(Long nroTransac, Integer idProd) throws PersistenciaException;
	public List<Lote> obtenerListaLotePorTransac(Long nroTransac) throws PersistenciaException;
//	public ? obtenerStockLotePorProd(Integer idProducto, Integer diasParaVenc) throws PersistenciaException;
	public List<Lote> obtenerListaLotePorProd(Integer idProducto, Integer diasParaVenc) throws PersistenciaException;
	public Integer guardarListaLote(List<Lote> listaLote) throws PersistenciaException;
	public Integer guardarLote(Lote lote) throws PersistenciaException;
	public Integer actualizarLote(Lote lote) throws PersistenciaException;
	public Integer actualizarStockLote(Integer idLote, Integer stock) throws PersistenciaException;
	public Integer eliminarLote(Lote lote) throws PersistenciaException;
	
}
