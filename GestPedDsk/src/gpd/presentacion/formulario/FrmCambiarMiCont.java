package gpd.presentacion.formulario;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import gpd.dominio.usuario.UsuarioDsk;

import java.awt.Color;
import java.awt.Toolkit;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JPasswordField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class FrmCambiarMiCont extends JFrame {

	private JPanel contentPane;
	private JPasswordField pwfContAct;
	private JPasswordField pwfNuCont1;
	private JPasswordField pwfNuCont2;
	private UsuarioDsk miUsuario;

	/**
	 * Launch the application.
	 
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					FrmCambiarMiCont frame = new FrmCambiarMiCont();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}*/

	/**
	 * Create the frame.
	 */
	public FrmCambiarMiCont(UsuarioDsk usuario) {
		miUsuario=usuario;
		
		setTitle("Modificar contrase\u00F1a");
		setIconImage(Toolkit.getDefaultToolkit().getImage(FrmCambiarMiCont.class.getResource("/gpd/recursos/Icono.png")));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBackground(Color.BLACK);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblIngreseSuContrasea = new JLabel("Ingrese su contrase\u00F1a actual:");
		lblIngreseSuContrasea.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblIngreseSuContrasea.setForeground(Color.WHITE);
		lblIngreseSuContrasea.setBounds(20, 34, 213, 14);
		contentPane.add(lblIngreseSuContrasea);
		
		JLabel lblIngreseSuNueva = new JLabel("Ingrese su nueva contrase\u00F1a:");
		lblIngreseSuNueva.setForeground(Color.WHITE);
		lblIngreseSuNueva.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblIngreseSuNueva.setBounds(23, 83, 210, 14);
		contentPane.add(lblIngreseSuNueva);
		
		JLabel lblVuelvaAIngresar = new JLabel("Vuelva a ingresar la nueva contrase\u00F1a:");
		lblVuelvaAIngresar.setForeground(Color.WHITE);
		lblVuelvaAIngresar.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblVuelvaAIngresar.setBounds(20, 132, 272, 14);
		contentPane.add(lblVuelvaAIngresar);
		
		pwfContAct = new JPasswordField();
		pwfContAct.setBounds(232, 33, 180, 20);
		contentPane.add(pwfContAct);
		
		pwfNuCont1 = new JPasswordField();
		pwfNuCont1.setBounds(232, 82, 180, 20);
		contentPane.add(pwfNuCont1);
		
		pwfNuCont2 = new JPasswordField();
		pwfNuCont2.setBounds(288, 131, 124, 20);
		contentPane.add(pwfNuCont2);
		
		JButton btnCambiarContrasea = new JButton("Cambiar contrase\u00F1a");
		btnCambiarContrasea.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//Si la contraseña actual se escribe bien, 
				if(String.valueOf(pwfContAct.getPassword()).equals(miUsuario.getPass())){
					
				}
			}
		});
		btnCambiarContrasea.setBounds(257, 192, 155, 23);
		contentPane.add(btnCambiarContrasea);
	}
}
