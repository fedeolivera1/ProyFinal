package test.presentacion;

import static org.junit.Assert.assertEquals;

import javax.swing.JPasswordField;
import javax.swing.JTextField;

import org.junit.Test;

import gpd.dominio.usuario.UsuarioDsk;
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
		CtrlFrmLogin ctrl = new CtrlFrmLogin(null);
		UsuarioDsk logueado = null;
		JTextField txtUsu = new JTextField();
		txtUsu.setText("PEPE");
		JPasswordField txtPass = new JPasswordField();
		txtPass.setText("loscañaberales");
		logueado = ctrl.obtenerUsuario(txtUsu, txtPass);
		System.out.println("logueado: " + (null!=logueado));
		txtUsu.setText("admin");
		txtPass.setText("admin");
		logueado = ctrl.obtenerUsuario(txtUsu, txtPass);
		System.out.println("logueado: " + (null!=logueado));
		assertEquals(logueado, true);
	}

}
