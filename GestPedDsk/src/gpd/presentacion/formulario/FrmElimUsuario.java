package gpd.presentacion.formulario;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import gpd.dominio.usuario.UsuarioDsk;
import gpd.exceptions.NuevaContraseniaException;
import gpd.presentacion.controlador.CtrlUsuario;

import java.awt.Toolkit;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JComboBox;
import javax.swing.JButton;
import java.awt.Font;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.awt.event.ActionEvent;

public class FrmElimUsuario extends JFrame {

	private JPanel contentPane;
	private UsuarioDsk usu;
	private CtrlUsuario ctrlusu;

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
		ctrlusu=new CtrlUsuario();
		setTitle("Eliminar usuario");
		setIconImage(Toolkit.getDefaultToolkit().getImage(FrmElimUsuario.class.getResource("/gpd/recursos/Icono.png")));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 427, 242);
		contentPane = new JPanel();
		contentPane.setBackground(Color.BLACK);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JComboBox<UsuarioDsk> comboBox = new JComboBox<UsuarioDsk>();
		comboBox.setBounds(175, 33, 219, 20);
		contentPane.add(comboBox);
		cargarCbx(comboBox);
		
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
		this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		
		ctrlusu=new CtrlUsuario();
		setTitle("Eliminar usuario");
		setIconImage(Toolkit.getDefaultToolkit().getImage(FrmElimUsuario.class.getResource("/gpd/recursos/Icono.png")));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 427, 242);
		contentPane = new JPanel();
		contentPane.setBackground(Color.BLACK);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JComboBox<UsuarioDsk> cbxUsuario = new JComboBox<UsuarioDsk>();
		cbxUsuario.setBounds(175, 33, 219, 20);
		contentPane.add(cbxUsuario);
		cargarCbx(cbxUsuario);
		
		JLabel lblElijaElUsuario = new JLabel("Elija el usuario a eliminar:");
		lblElijaElUsuario.setForeground(Color.WHITE);
		lblElijaElUsuario.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblElijaElUsuario.setBounds(10, 35, 168, 14);
		contentPane.add(lblElijaElUsuario);
		
		JButton btnEliminar = new JButton("Eliminar");
		btnEliminar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				// Pregunto dos veces si quiere eliminar el usuario por las
				// dudas
				int opcion = JOptionPane.showConfirmDialog(null,
						"¿Está seguro que desea eliminar a "
								+ (cbxUsuario.getItemAt(cbxUsuario.getSelectedIndex())).getNomUsu() + "?",
						"Confirmación.", JOptionPane.OK_CANCEL_OPTION);

				if (opcion == JOptionPane.OK_OPTION) {
					opcion = JOptionPane.showConfirmDialog(null,
							"¿Realmente desea eliminar a "
									+ (cbxUsuario.getItemAt(cbxUsuario.getSelectedIndex())).getNomUsu() + "?",
							"Confirmación.", JOptionPane.YES_NO_OPTION);
					if (opcion == JOptionPane.YES_OPTION) {

						// Elimina el usuario y se cierra la ventana
						UsuarioDsk usuario = cbxUsuario.getItemAt(cbxUsuario.getSelectedIndex());
						ctrlusu.eliminarUsuario(usuario);
						FrmElimUsuario.this.setVisible(false);
						dispose();
					}
				}

			}
		});
		btnEliminar.setBounds(305, 114, 89, 23);
		contentPane.add(btnEliminar);
		
		
	}
	
	public void cargarCbx(JComboBox<UsuarioDsk> usuariosDsk){
		ArrayList<UsuarioDsk> usuarios=ctrlusu.traerTodo();
		for(UsuarioDsk usu : usuarios) {
			usuariosDsk.addItem(usu);
			}
	}
}
