package gpd.interfaces.transaccion;

import java.sql.Connection;
import java.util.List;

import gpd.dominio.transaccion.EstadoTran;
import gpd.dominio.transaccion.TipoTran;
import gpd.dominio.transaccion.TranLineaLote;
import gpd.dominio.transaccion.Transaccion;
import gpd.exceptions.PersistenciaException;
import gpd.types.Fecha;

public interface IPersTransaccion {

	public Integer guardarTransaccionCompra(Connection conn, Transaccion transaccion) throws PersistenciaException;
	public Integer guardarTransaccionVenta(Connection conn, Transaccion transaccion) throws PersistenciaException;
	public Integer actualizarTransaccion(Connection conn, Transaccion transaccion) throws PersistenciaException;
	public Integer modificarEstadoTransaccion(Connection conn, Transaccion transaccion) throws PersistenciaException;
	
	public Transaccion obtenerTransaccionPorId(Connection conn, Integer idTransac) throws PersistenciaException;
	public List<Transaccion> obtenerListaTransaccionPorPersona(Connection conn, Long idPersona, TipoTran tipoTran, EstadoTran estadoTran) throws PersistenciaException;
	public List<Transaccion> obtenerListaTransaccionPorPeriodo(Connection conn, TipoTran tipoTran, EstadoTran estadoTran, Fecha fechaIni, Fecha fechaFin) throws PersistenciaException;
	
	public Integer guardarTranEstado(Connection conn, Transaccion transaccion) throws PersistenciaException;
	public EstadoTran obtenerUltTranEstadoPorId(Connection conn, Integer idTransac) throws PersistenciaException;

	public List<TranLineaLote> obtenerListaTranLineaLote(Connection conn, Integer nroTransac, Integer idProducto) throws PersistenciaException;
	public Integer guardarListaTranVtaLote(Connection conn, List<TranLineaLote> listaTll) throws PersistenciaException;
	public Integer eliminarTranVtaLote(Connection conn, Integer nroTransac) throws PersistenciaException;
	
}
