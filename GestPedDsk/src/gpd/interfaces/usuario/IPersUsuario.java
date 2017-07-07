package gpd.interfaces.usuario;

import java.util.List;

import gpd.dominio.usuario.UsuarioDsk;
import gpd.exceptions.PersistenciaException;

public interface IPersUsuario {

	public UsuarioDsk obtenerUsuario(String nombreUsuario, String password) throws PersistenciaException;
	public UsuarioDsk obtenerUsuarioPorId(String nombreUsuario) throws PersistenciaException;
	public List <UsuarioDsk> obtenerListaUsuario() throws PersistenciaException;
	public Integer guardarUsuario(UsuarioDsk usuario) throws PersistenciaException;
	public Integer modificarUsuario(UsuarioDsk usuario) throws PersistenciaException;
	public Integer eliminarUsuario(UsuarioDsk usuario) throws PersistenciaException;
	
}
