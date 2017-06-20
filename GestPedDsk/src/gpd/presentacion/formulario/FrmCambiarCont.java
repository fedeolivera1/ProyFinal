package gpd.presentacion.formulario;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import gpd.dominio.usuario.UsuarioDsk;
import gpd.presentacion.controlador.CtrlUsuario;

import java.awt.Toolkit;
import javax.swing.JLabel;
import javax.swing.JComboBox;
import javax.swing.JPasswordField;
import javax.swing.JButton;
import java.awt.Font;
import java.awt.Color;

public class FrmCambiarCont extends JFrame {

	private JPanel contentPane;
	private JPasswordField passUno;
	private JPasswordField passDos;
	private UsuarioDsk usu;
	private CtrlUsuario ctrlusu;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					FrmCambiarCont frame = new FrmCambiarCont();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public FrmCambiarCont() {
		this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		
		setTitle("Cambiar contrase\u00F1a");
		setIconImage(Toolkit.getDefaultToolkit().getImage(FrmCambiarCont.class.getResource("/gpd/recursos/Icono.png")));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 320);
		contentPane = new JPanel();
		contentPane.setBackground(Color.BLACK);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblElijaElUsuario = new JLabel("Elija el usuario:");
		lblElijaElUsuario.setForeground(Color.WHITE);
		lblElijaElUsuario.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblElijaElUsuario.setBounds(95, 39, 103, 14);
		contentPane.add(lblElijaElUsuario);
		
		JLabel lblEscribaLaNueva = new JLabel("Escriba la nueva contrase\u00F1a:");
		lblEscribaLaNueva.setForeground(Color.WHITE);
		lblEscribaLaNueva.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblEscribaLaNueva.setBounds(10, 97, 188, 14);
		contentPane.add(lblEscribaLaNueva);
		
		JLabel lblRepitaLaContrasea = new JLabel("Repita la contrase\u00F1a:");
		lblRepitaLaContrasea.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblRepitaLaContrasea.setForeground(Color.WHITE);
		lblRepitaLaContrasea.setBounds(52, 154, 146, 14);
		contentPane.add(lblRepitaLaContrasea);
		
		JComboBox cbUsuario = new JComboBox();
		cbUsuario.setBounds(200, 37, 212, 20);
		contentPane.add(cbUsuario);
		//Método para cargar 
		
		passUno = new JPasswordField();
		passUno.setBounds(200, 91, 212, 20);
		contentPane.add(passUno);
		
		passDos = new JPasswordField();
		passDos.setBounds(200, 148, 212, 20);
		contentPane.add(passDos);
		
		JButton btnCambiarContrasea = new JButton("Modificar contrase\u00F1a");
		btnCambiarContrasea.setBounds(266, 209, 146, 23);
		contentPane.add(btnCambiarContrasea);
	}

	public FrmCambiarCont(UsuarioDsk usr) {
		this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		usu=usr;
		setTitle("Cambiar contrase\u00F1a");
		setIconImage(Toolkit.getDefaultToolkit().getImage(FrmCambiarCont.class.getResource("/gpd/recursos/Icono.png")));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 320);
		contentPane = new JPanel();
		contentPane.setBackground(Color.BLACK);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblElijaElUsuario = new JLabel("Elija el usuario:");
		lblElijaElUsuario.setForeground(Color.WHITE);
		lblElijaElUsuario.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblElijaElUsuario.setBounds(95, 39, 103, 14);
		contentPane.add(lblElijaElUsuario);
		
		JLabel lblEscribaLaNueva = new JLabel("Escriba la nueva contrase\u00F1a:");
		lblEscribaLaNueva.setForeground(Color.WHITE);
		lblEscribaLaNueva.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblEscribaLaNueva.setBounds(10, 97, 188, 14);
		contentPane.add(lblEscribaLaNueva);
		
		JLabel lblRepitaLaContrasea = new JLabel("Repita la contrase\u00F1a:");
		lblRepitaLaContrasea.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblRepitaLaContrasea.setForeground(Color.WHITE);
		lblRepitaLaContrasea.setBounds(52, 154, 146, 14);
		contentPane.add(lblRepitaLaContrasea);
		
		JComboBox comboBox = new JComboBox();
		comboBox.setBounds(200, 37, 212, 20);
		contentPane.add(comboBox);
		
		passUno = new JPasswordField();
		passUno.setBounds(200, 91, 212, 20);
		contentPane.add(passUno);
		
		passDos = new JPasswordField();
		passDos.setBounds(200, 148, 212, 20);
		contentPane.add(passDos);
		
		JButton btnCambiarContrasea = new JButton("Modificar contrase\u00F1a");
		btnCambiarContrasea.setBounds(266, 209, 146, 23);
		contentPane.add(btnCambiarContrasea);
	}
	
	public void cargarCbx(){
	
	}
}
