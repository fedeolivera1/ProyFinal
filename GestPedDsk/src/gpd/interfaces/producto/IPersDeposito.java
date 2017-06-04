package gpd.interfaces.producto;

import java.util.List;

import gpd.dominio.producto.Deposito;
import gpd.exceptions.PersistenciaException;

public interface IPersDeposito {

	public Deposito obtenerDepositoPorId(Integer id) throws PersistenciaException;
	public List<Deposito> obtenerListaDeposito() throws PersistenciaException;
	public Integer guardarDeposito(Deposito deposito) throws PersistenciaException;
	public Integer modificarDeposito(Deposito  deposito) throws PersistenciaException;
	public Integer eliminarDeposito(Deposito deposito) throws PersistenciaException;
	
}
