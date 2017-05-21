package gpd.managers.persona;

import gpd.dominio.persona.Cliente;
import gpd.dominio.persona.Proveedor;
import gpd.interfaces.persona.IPersPersona;
import gpd.persistencia.persona.PersistenciaPersona;

public class ManagerPersona implements IPersPersona {

	Integer resultado = null;
	
	@Override
	public Integer guardarCliente(Cliente cliente) {
		if(cliente != null) {
			PersistenciaPersona perPersona = new PersistenciaPersona();
			resultado = perPersona.guardarCliente(cliente);
		}
		return resultado;
	}

	@Override
	public Integer guardarProveedor(Proveedor proveedor) {
		if(proveedor != null) {
			PersistenciaPersona perPersona = new PersistenciaPersona();
			resultado = perPersona.guardarProveedor(proveedor);
		}
		return resultado;
		
	}

}
