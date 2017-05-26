package test.presentacion;

import static org.junit.Assert.*;

import org.junit.Test;

import gpd.dominio.usuario.UsuarioDsk;
import gpd.exceptions.UsuarioNoExisteException;
import gpd.presentacion.controlador.CtrlFrmLogin;

public class ControladorFrmLoginTest {

	@Test
	public void testMD5() {
		//cambiar metodo a publico para probar
//		String hashPass = ControladorFrmLogin.getMD5("admin");
//		System.out.println("Pass=" + hashPass);
//		assertNotNull(hashPass);
	}
	
	@Test
	public void loguearUsuario() {
		CtrlFrmLogin ctrl = new CtrlFrmLogin();
		UsuarioDsk logueado = null;
		try {
			logueado = ctrl.obtenerUsuario("pepe", "loscañaberales");
			System.out.println("logueado: " + (null!=logueado));
			logueado = ctrl.obtenerUsuario("admin", "admin");
			System.out.println("logueado: " + (null!=logueado));
		} catch (UsuarioNoExisteException e) {
			e.printStackTrace();
		}
		assertEquals(logueado, true);
	}

}
