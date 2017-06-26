package gpd.interfaces.usuario;

import java.util.ArrayList;

import gpd.dominio.usuario.UsuarioDsk;
import gpd.exceptions.PersistenciaException;

public interface IPersUsuario {

	public UsuarioDsk obtenerUsuario(String nombreUsuario, String password) throws PersistenciaException;
	public Integer guardarUsuario(UsuarioDsk usuario);
	public Integer modificarUsuario(UsuarioDsk usuario);
	public Integer eliminarUsuario(UsuarioDsk usuario);
	public ArrayList<UsuarioDsk> obtenerTodo();
	
}
