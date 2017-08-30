package gpd.interfaces.usuario;

import java.sql.Connection;
import java.util.List;

import gpd.dominio.usuario.UsuarioDsk;
import gpd.exceptions.PersistenciaException;

public interface IPersUsuario {

	public UsuarioDsk obtenerUsuario(Connection conn, String nombreUsuario, String password) throws PersistenciaException;
	public UsuarioDsk obtenerUsuarioPorId(Connection conn, String nombreUsuario) throws PersistenciaException;
	public List <UsuarioDsk> obtenerListaUsuario(Connection conn) throws PersistenciaException;
	public Integer guardarUsuario(Connection conn, UsuarioDsk usuario) throws PersistenciaException;
	public Integer modificarUsuario(Connection conn, UsuarioDsk usuario) throws PersistenciaException;
	public Integer modificarUsuarioSinPass(Connection conn, UsuarioDsk usuario) throws PersistenciaException;
	public Integer eliminarUsuario(Connection conn, UsuarioDsk usuario) throws PersistenciaException;
	
}
