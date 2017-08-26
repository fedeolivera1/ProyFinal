package gpd.interfaces.producto;

import java.sql.Connection;
import java.util.List;

import gpd.dominio.producto.Deposito;
import gpd.exceptions.PersistenciaException;

public interface IPersDeposito {

	public Deposito obtenerDepositoPorId(Connection conn, Integer id) throws PersistenciaException;
	public List<Deposito> obtenerListaDeposito(Connection conn) throws PersistenciaException;
	public Integer guardarDeposito(Connection conn, Deposito deposito) throws PersistenciaException;
	public Integer modificarDeposito(Connection conn, Deposito  deposito) throws PersistenciaException;
	public Integer eliminarDeposito(Connection conn, Deposito deposito) throws PersistenciaException;
	
}
