package gpd.presentacion.formulario;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import gpd.dominio.usuario.UsuarioDsk;

import java.awt.Toolkit;
import javax.swing.JLabel;
import javax.swing.JComboBox;
import javax.swing.JButton;
import java.awt.Font;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class FrmElimUsuario extends JFrame {

	private JPanel contentPane;
	private UsuarioDsk usu;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					FrmElimUsuario frame = new FrmElimUsuario();
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
	public FrmElimUsuario(UsuarioDsk usr) {
		usu=usr;
		setTitle("Eliminar usuario");
		setIconImage(Toolkit.getDefaultToolkit().getImage(FrmElimUsuario.class.getResource("/gpd/recursos/Icono.png")));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 427, 242);
		contentPane = new JPanel();
		contentPane.setBackground(Color.BLACK);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JComboBox comboBox = new JComboBox();
		comboBox.setBounds(175, 33, 219, 20);
		contentPane.add(comboBox);
		
		JLabel lblElijaElUsuario = new JLabel("Elija el usuario a eliminar:");
		lblElijaElUsuario.setForeground(Color.WHITE);
		lblElijaElUsuario.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblElijaElUsuario.setBounds(10, 35, 168, 14);
		contentPane.add(lblElijaElUsuario);
		
		JButton btnEliminar = new JButton("Eliminar");
		btnEliminar.setBounds(305, 114, 89, 23);
		contentPane.add(btnEliminar);
	}

	public FrmElimUsuario() {
		setTitle("Eliminar usuario");
		setIconImage(Toolkit.getDefaultToolkit().getImage(FrmElimUsuario.class.getResource("/gpd/recursos/Icono.png")));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 427, 242);
		contentPane = new JPanel();
		contentPane.setBackground(Color.BLACK);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JComboBox cbxUsuario = new JComboBox();
		cbxUsuario.setBounds(175, 33, 219, 20);
		contentPane.add(cbxUsuario);
		
		
		JLabel lblElijaElUsuario = new JLabel("Elija el usuario a eliminar:");
		lblElijaElUsuario.setForeground(Color.WHITE);
		lblElijaElUsuario.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblElijaElUsuario.setBounds(10, 35, 168, 14);
		contentPane.add(lblElijaElUsuario);
		
		JButton btnEliminar = new JButton("Eliminar");
		btnEliminar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});
		btnEliminar.setBounds(305, 114, 89, 23);
		contentPane.add(btnEliminar);
	}
}
