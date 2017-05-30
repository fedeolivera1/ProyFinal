package gpd.presentacion.formulario;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import gpd.dominio.usuario.TipoUsr;
import gpd.dominio.usuario.UsuarioDsk;
import gpd.presentacion.controlador.ControladorFrmRegUsuario;

import java.awt.Toolkit;
import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.CardLayout;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.BoxLayout;
import javax.swing.DefaultComboBoxModel;
import javax.swing.SwingConstants;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class FrmRegUsuario extends JFrame {

	private JPanel contentPane;
	private JTextField txtNombre;
	private JPasswordField txtCont;
	private JPasswordField txtCont2;
	private ControladorFrmRegUsuario ctrl;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					FrmRegUsuario frame = new FrmRegUsuario();
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
	public FrmRegUsuario() {
		
		//Hago que el controlador funcione
		ctrl=new ControladorFrmRegUsuario();
		
		setTitle("Registrar nuevo usuario");
		setIconImage(Toolkit.getDefaultToolkit().getImage(FrmRegUsuario.class.getResource("/gpd/recursos/Icono.png")));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBackground(Color.BLACK);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblTipoDeUsuario = new JLabel("Tipo de usuario:");
		lblTipoDeUsuario.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblTipoDeUsuario.setForeground(Color.WHITE);
		lblTipoDeUsuario.setBounds(24, 33, 121, 17);
		contentPane.add(lblTipoDeUsuario);
		
		JLabel lblNombreDeUsuario = new JLabel("Nombre de usuario:");
		lblNombreDeUsuario.setForeground(Color.WHITE);
		lblNombreDeUsuario.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblNombreDeUsuario.setBounds(24, 75, 143, 14);
		contentPane.add(lblNombreDeUsuario);
		
		JLabel lblContrasea = new JLabel("Contrase\u00F1a:");
		lblContrasea.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblContrasea.setForeground(Color.WHITE);
		lblContrasea.setBounds(24, 115, 95, 14);
		contentPane.add(lblContrasea);
		
		JLabel lblVuelvaAIngresar = new JLabel("Vuelva a ingresar la contrase\u00F1a:");
		lblVuelvaAIngresar.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblVuelvaAIngresar.setForeground(Color.WHITE);
		lblVuelvaAIngresar.setBounds(32, 158, 224, 14);
		contentPane.add(lblVuelvaAIngresar);
		
		JButton btnRegistrarUsuario = new JButton("Registrar usuario");
		btnRegistrarUsuario.setBounds(267, 213, 143, 23);
		contentPane.add(btnRegistrarUsuario);
		
		JComboBox <TipoUsr> cbxTipo = new JComboBox();
		cbxTipo.setBounds(155, 33, 200, 20);
		cbxTipo.setModel(new DefaultComboBoxModel<>(TipoUsr.values()));
		contentPane.add(cbxTipo);
		
		
		txtNombre = new JTextField();
		txtNombre.setBounds(177, 74, 178, 20);
		contentPane.add(txtNombre);
		txtNombre.setColumns(10);
		
		txtCont = new JPasswordField();
		txtCont.setBounds(129, 114, 226, 20);
		contentPane.add(txtCont);
		
		txtCont2 = new JPasswordField();
		txtCont2.setBounds(254, 157, 101, 20);
		contentPane.add(txtCont2);
		
		//Cuando cliqueas en el botón de registrar
		btnRegistrarUsuario.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent arg0) {
				
				String mensaje="ATENCIÓN /n /n";
				boolean error=false;
				
				//Controlando si todo está en órden
				if(lblNombreDeUsuario.getText()==""){
					error=true;
					mensaje=mensaje+"Ingrese un nombre de usuario. /n";
				}
				
				if(txtCont.getPassword()!=txtCont2.getPassword()){
					error=true;
					mensaje=mensaje+"Las contraseñas no coinciden. /n";
				}
				
				if(error==true){
					JOptionPane.showMessageDialog(null, mensaje, "Revise los datos.", JOptionPane.ERROR_MESSAGE);
				}else{
					
					//Pregunto si la contraseña es mayor a 6 caracteres
					if((txtCont.getPassword()).length<6){
						mensaje=mensaje+"La contraseña debe tener 6 o más caracteres.";
						JOptionPane.showMessageDialog(null, mensaje, "Revise los datos.", JOptionPane.ERROR_MESSAGE);
					}else{
						UsuarioDsk nuevo=new UsuarioDsk(txtNombre.getText(), new String(txtCont.getPassword()), cbxTipo.getItemAt(cbxTipo.getSelectedIndex()));
						
						
					}
				}
				
				
				
			}
		});
	}
}
