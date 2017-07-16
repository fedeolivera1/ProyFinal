package gpd.interfaces.transaccion;

import java.util.List;

import gpd.dominio.transaccion.EstadoTran;
import gpd.dominio.transaccion.TipoTran;
import gpd.dominio.transaccion.TranLineaLote;
import gpd.dominio.transaccion.Transaccion;
import gpd.exceptions.PersistenciaException;
import gpd.types.Fecha;

public interface IPersTransaccion {

	public Integer guardarTransaccionCompra(Transaccion transaccion) throws PersistenciaException;
	public Integer guardarTransaccionVenta(Transaccion transaccion) throws PersistenciaException;
	public Integer modificarEstadoTransaccion(Transaccion transaccion) throws PersistenciaException;
	
	public Transaccion obtenerTransaccionPorId(Integer idTransac) throws PersistenciaException;
	public List<Transaccion> obtenerListaTransaccionPorPersona(Long idPersona, TipoTran tipoTran, EstadoTran estadoTran) throws PersistenciaException;
	public List<Transaccion> obtenerListaTransaccionPorPeriodo(TipoTran tipoTran, EstadoTran estadoTran, Fecha fechaIni, Fecha fechaFin) throws PersistenciaException;
	
	public Integer guardarTranEstado(Transaccion transaccion) throws PersistenciaException;
	public EstadoTran obtenerUltTranEstadoPorId(Integer idTransac) throws PersistenciaException;

	public List<TranLineaLote> obtenerListaTranLineaLote(Integer nroTransac, Integer idProducto) throws PersistenciaException;
	public Integer guardarListaTranLineaLote(List<TranLineaLote> listaTll) throws PersistenciaException;
	public Integer eliminarTranLineaLote(Integer nroTransac) throws PersistenciaException;
	
}
