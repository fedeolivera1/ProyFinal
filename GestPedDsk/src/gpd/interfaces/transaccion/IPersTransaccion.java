package gpd.interfaces.transaccion;

import java.util.List;

import gpd.dominio.transaccion.EstadoTran;
import gpd.dominio.transaccion.TipoTran;
import gpd.dominio.transaccion.Transaccion;
import gpd.exceptions.PersistenciaException;

public interface IPersTransaccion {

	public Integer guardarTransaccionCompra(Transaccion transaccion) throws PersistenciaException;
	public Integer guardarTransaccionVenta(Transaccion transaccion) throws PersistenciaException;
	public Integer modificarEstadoTransaccion(Transaccion transaccion) throws PersistenciaException;
	
	public Transaccion obtenerTransaccionPorId(Long idTransac) throws PersistenciaException;
	public List<Transaccion> obtenerListaTransaccionPorPersona(Long idPersona, TipoTran tipoTran, EstadoTran estadoTran) throws PersistenciaException;
	
	public Integer guardarTranEstado(Transaccion transaccion) throws PersistenciaException;
	public EstadoTran obtenerUltTranEstadoPorId(Long idTransac) throws PersistenciaException;
	
}
