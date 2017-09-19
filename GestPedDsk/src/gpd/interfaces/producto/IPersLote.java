package gpd.interfaces.producto;

import java.sql.Connection;
import java.util.List;

import gpd.dominio.producto.Lote;
import gpd.exceptions.PersistenciaException;

public interface IPersLote {

	public Lote obtenerLotePorId(Connection conn, Integer idLote) throws PersistenciaException;
	public Lote obtenerLoteVtaPorTransacProd(Connection conn, Integer nroTransac, Integer idProd) throws PersistenciaException;
	public List<Lote> obtenerListaLotePorTransac(Connection conn, Integer nroTransac) throws PersistenciaException;
	public List<Lote> obtenerListaLotePorProd(Connection conn, Integer idProducto, Integer diasParaVenc) throws PersistenciaException;
	public Integer guardarListaLote(Connection conn, List<Lote> listaLote) throws PersistenciaException;
//	public Integer guardarLote(Connection conn, Lote lote) throws PersistenciaException;
	public Integer actualizarLote(Connection conn, Lote lote) throws PersistenciaException;
	public Integer actualizarStockLote(Connection conn, Integer idLote, Integer stock) throws PersistenciaException;
//	public Integer eliminarLote(Connection conn, Lote lote) throws PersistenciaException;
	//warnings
	public List<Lote> obtenerListaLoteProxVenc(Connection conn, Integer diasTol) throws PersistenciaException;
	
}
