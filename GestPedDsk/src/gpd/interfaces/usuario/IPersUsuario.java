package gpd.interfaces.usuario;

import gpd.dominio.usuario.UsuarioDsk;
import gpd.exceptions.PersistenciaException;

public interface IPersUsuario {

	public UsuarioDsk obtenerUsuario(String nombreUsuario, String password) throws PersistenciaException;
	public void guardarUsuario(UsuarioDsk usuario);
	public UsuarioDsk modificarUsuario(UsuarioDsk usuario);
	public UsuarioDsk eliminarUsuario(UsuarioDsk usuario);
	
}
