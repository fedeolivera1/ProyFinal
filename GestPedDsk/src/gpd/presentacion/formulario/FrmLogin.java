package gpd.presentacion.formulario;

import java.awt.EventQueue;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;

import gpd.dominio.usuario.UsuarioDsk;
import gpd.exceptions.UsuarioNoExisteException;
import gpd.presentacion.controlador.ControladorFrmLogin;
import javax.swing.JPasswordField;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.Color;
import javax.swing.ImageIcon;
import java.awt.Toolkit;

public class FrmLogin extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField txtUsr;
	private ControladorFrmLogin ctrl;
	private JPasswordField txtPass;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
					FrmLogin frame = new FrmLogin();
					frame.setLocationRelativeTo(null);
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
	public FrmLogin() {
		setTitle("Inicio");
		setIconImage(Toolkit.getDefaultToolkit().getImage(FrmLogin.class.getResource("/gpd/recursos/Icono.png")));
		ctrl = new ControladorFrmLogin();
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 425, 300);
		contentPane = new JPanel();
		contentPane.setBackground(Color.BLACK);
		contentPane.setForeground(Color.BLACK);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		txtUsr = new JTextField();
		txtUsr.setBounds(268, 94, 120, 20);
		contentPane.add(txtUsr);
		txtUsr.setColumns(10);
		
		JButton btnLogin = new JButton("Entrar");
		btnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String nomUsu = txtUsr.getText();
				char[] passWd = txtPass.getPassword();
				try {
					UsuarioDsk usr = ctrl.obtenerUsuario(nomUsu, String.valueOf(passWd));
					if(null != usr) {
						FrmPrincipal frmPpl = new FrmPrincipal(usr);
						frmPpl.setLocationRelativeTo(null);
						frmPpl.setDefaultCloseOperation(FrmPrincipal.EXIT_ON_CLOSE);
						frmPpl.setVisible(true);
						setVisible(false);
					} else {
						JOptionPane.showMessageDialog(null, "EL usuario no se ha podido autenticar.", "Usuario", JOptionPane.ERROR_MESSAGE);
					}
				} catch (HeadlessException | UsuarioNoExisteException e) {
					e.printStackTrace();
				}
			}
		});
		btnLogin.setBounds(299, 171, 89, 23);
		contentPane.add(btnLogin);
		
		txtPass = new JPasswordField();
		txtPass.setBounds(268, 125, 120, 20);
		contentPane.add(txtPass);
		
		JLabel lblUsuario = new JLabel("Usuario:");
		lblUsuario.setForeground(Color.WHITE);
		lblUsuario.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblUsuario.setBounds(210, 96, 56, 14);
		contentPane.add(lblUsuario);
		
		JLabel lblNewLabel = new JLabel("Contrase\u00F1a:");
		lblNewLabel.setForeground(Color.WHITE);
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblNewLabel.setBounds(186, 127, 82, 14);
		contentPane.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("New label");
		lblNewLabel_1.setIcon(new ImageIcon(FrmLogin.class.getResource("/gpd/recursos/InicioDog.png")));
		lblNewLabel_1.setBounds(56, 38, 120, 195);
		contentPane.add(lblNewLabel_1);
	}
}
