package gpd.manager.usuario;

import java.util.ArrayList;

import org.apache.log4j.Logger;

import gpd.dominio.usuario.UsuarioDsk;
import gpd.exceptions.PersistenciaException;
import gpd.interfaces.usuario.IPersUsuario;
import gpd.persistencia.conector.Conector;
import gpd.persistencia.usuario.PersistenciaUsuario;

public class ManagerUsuario {

	private static final Logger logger = Logger.getLogger(ManagerUsuario.class);
	
	
	public UsuarioDsk obtenerUsuario(String nombreUsuario, String passwd) {
		UsuarioDsk usuario = null;
		Conector.getConn();
		IPersUsuario interfaceUsuario = new PersistenciaUsuario();
		try {
			usuario = interfaceUsuario.obtenerUsuario(nombreUsuario, passwd);
		} catch (PersistenciaException e) {
			e.printStackTrace();
		}
		Conector.closeConn("chequearLogin", null);
		return usuario;
	}

	public void guardarUsuario(UsuarioDsk usuario) {
		
		Conector.getConn();
		IPersUsuario interfaceUsuario = new PersistenciaUsuario();
		try {
			
			interfaceUsuario.guardarUsuario(usuario);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		Conector.closeConn("chequearSiSeGuardo", null);
	}
	
	//Para cargar Combobox
	public ArrayList<UsuarioDsk> obtenerTodosLosUsuarios(){
		ArrayList<UsuarioDsk> todo=new ArrayList<UsuarioDsk>();
		
		Conector.getConn();
		IPersUsuario interfaceUsuario = new PersistenciaUsuario();
		try {
			
			todo=interfaceUsuario.obtenerTodo();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		Conector.closeConn("viendoSiTrajoTodo", null);
		
		return todo;
	}
	
	public void modificarUsuario(UsuarioDsk usuario) {
		Conector.getConn();
		IPersUsuario interfaceUsuario = new PersistenciaUsuario();
		try {
			
			interfaceUsuario.modificarUsuario(usuario);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		Conector.closeConn("chequearSiSeModificó", null);
	}

	public void eliminarUsuario(UsuarioDsk usuario) {
		Conector.getConn();
		IPersUsuario interfaceUsuario = new PersistenciaUsuario();
		try {
			
			interfaceUsuario.eliminarUsuario(usuario);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		Conector.closeConn("chequearSiEliminó", null);
		
	}


	
}
