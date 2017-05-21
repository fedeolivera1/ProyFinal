package gpd.interfaces.persona;

import gpd.dominio.persona.Cliente;
import gpd.dominio.persona.Proveedor;

public interface IPersPersona {
	
	Integer resultado = null;
	public Integer guardarCliente(Cliente cliente);
	public Integer guardarProveedor(Proveedor proveedor);
	
}
