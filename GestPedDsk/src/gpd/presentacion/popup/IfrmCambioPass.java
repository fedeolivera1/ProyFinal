package gpd.presentacion.popup;

import javax.swing.JButton;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.SwingConstants;

import gpd.presentacion.controlador.CtrlFrmUsuario;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class IfrmCambioPass extends JInternalFrame {

	private static final long serialVersionUID = 1L;
	private CtrlFrmUsuario ctrlInterno;
	private JPasswordField pwfPassAct;
	private JPasswordField pwfPassNueva1;
	private JLabel lblRepitaNuevaContrasea;
	private JPasswordField pwfPassNueva2;
	private JButton btnConfirmarNc;

	/**
	 * Create the frame.
	 */
	public IfrmCambioPass(CtrlFrmUsuario ctrl) {
		setTitle("Cambio Contraseña");
		setClosable(true);
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(null);
		ctrlInterno = ctrl;
		
		JLabel lblContraseaActual = new JLabel("Contraseña Actual");
		lblContraseaActual.setHorizontalAlignment(SwingConstants.RIGHT);
		lblContraseaActual.setBounds(10, 14, 176, 14);
		getContentPane().add(lblContraseaActual);
		
		pwfPassAct = new JPasswordField();
		pwfPassAct.setBounds(196, 11, 171, 20);
		getContentPane().add(pwfPassAct);
		
		JLabel lblNuevaContrasea = new JLabel("Nueva Contraseña");
		lblNuevaContrasea.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNuevaContrasea.setBounds(10, 42, 176, 14);
		getContentPane().add(lblNuevaContrasea);
		
		pwfPassNueva1 = new JPasswordField();
		pwfPassNueva1.setBounds(196, 39, 171, 20);
		getContentPane().add(pwfPassNueva1);
		
		lblRepitaNuevaContrasea = new JLabel("Repita Nueva Contraseña");
		lblRepitaNuevaContrasea.setHorizontalAlignment(SwingConstants.RIGHT);
		lblRepitaNuevaContrasea.setBounds(10, 70, 176, 14);
		getContentPane().add(lblRepitaNuevaContrasea);
		
		pwfPassNueva2 = new JPasswordField();
		pwfPassNueva2.setBounds(196, 67, 171, 20);
		getContentPane().add(pwfPassNueva2);
		
		btnConfirmarNc = new JButton("Cambiar");
		btnConfirmarNc.setBounds(278, 98, 89, 23);
		getContentPane().add(btnConfirmarNc);

		btnConfirmarNc.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ctrlInterno.cambiarCont(pwfPassAct, pwfPassNueva1, pwfPassNueva2);
			}
		});
	}
	
	
	

}
