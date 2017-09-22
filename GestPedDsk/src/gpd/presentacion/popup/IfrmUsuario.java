package gpd.presentacion.popup;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;

import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.event.InternalFrameAdapter;
import javax.swing.event.InternalFrameEvent;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import gpd.dominio.usuario.TipoUsr;
import gpd.dominio.usuario.UsuarioDsk;
import gpd.presentacion.controlador.CtrlFrmUsuario;
import gpd.presentacion.generic.CnstPresGeneric;

import javax.swing.JSeparator;
import java.awt.SystemColor;

public class IfrmUsuario extends JInternalFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField txtUsuNombre;
	private CtrlFrmUsuario ctrlInterno;
	private JPanel pnlDatosUsu;
	private JScrollPane scrollPaneUsu;
	private JList<UsuarioDsk> jlUsuario;
	private JPasswordField pwfUsuPass1;
	private JPasswordField pwfUsuPass2;
	private JComboBox<TipoUsr> cbxTipoUsr;
	

	/**
	 * Create the frame.
	 */
	public IfrmUsuario(CtrlFrmUsuario ctrl) {
		ctrlInterno = ctrl;
		ctrlInterno.setIfrmUsr(this);
        
		setIconifiable(true);
		setClosable(true);
		setBounds(100, 100, 600, 400);
		setTitle("Usuario");
		setResizable(false);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		if(ClassLoader.getSystemResource(CnstPresGeneric.ICON_BG) != null) {
			URL url = ClassLoader.getSystemResource(CnstPresGeneric.ICON_BG);
			ImageIcon icon = new ImageIcon(url);
			setFrameIcon(icon);
		}
		
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		DefaultListModel<UsuarioDsk> dlm = new DefaultListModel<>();
		
		
		JButton btnUsuAgr = new JButton("Agregar");
		btnUsuAgr.setBounds(199, 174, 89, 23);
		contentPane.add(btnUsuAgr);
		
		JButton btnUsuMod = new JButton("Modificar");
		btnUsuMod.setBounds(199, 208, 89, 23);
		contentPane.add(btnUsuMod);
		
		JButton btnUsuEli = new JButton("Eliminar");
		btnUsuEli.setBounds(199, 242, 89, 23);
		contentPane.add(btnUsuEli);
		
		scrollPaneUsu = new JScrollPane();
		scrollPaneUsu.setBounds(298, 0, 286, 370);
		contentPane.add(scrollPaneUsu);
		
		jlUsuario = new JList<>();
		scrollPaneUsu.setViewportView(jlUsuario);
		jlUsuario.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		jlUsuario.setModel(dlm);
		
		/*****************************************************************************************************************************************************/
		/* ACCIONES CONTROLES */
		/*****************************************************************************************************************************************************/
		ctrl.cargarListUsuario(jlUsuario);
		
		JButton button = new JButton("Limpiar");
		button.setBounds(199, 276, 89, 23);
		contentPane.add(button);
		
		JSeparator separator = new JSeparator();
		separator.setForeground(SystemColor.info);
		separator.setBackground(SystemColor.info);
		separator.setBounds(10, 159, 278, 2);
		contentPane.add(separator);
		
		pnlDatosUsu = new JPanel();
		pnlDatosUsu.setBounds(0, 0, 297, 148);
		contentPane.add(pnlDatosUsu);
		pnlDatosUsu.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Nombre Usuario");
		lblNewLabel.setBounds(10, 14, 97, 14);
		pnlDatosUsu.add(lblNewLabel);
		lblNewLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		
		txtUsuNombre = new JTextField();
		txtUsuNombre.setBounds(117, 11, 171, 20);
		pnlDatosUsu.add(txtUsuNombre);
		txtUsuNombre.setColumns(10);
		
		JLabel lblContrasea = new JLabel("Contraseña");
		lblContrasea.setBounds(10, 45, 97, 14);
		pnlDatosUsu.add(lblContrasea);
		lblContrasea.setHorizontalAlignment(SwingConstants.RIGHT);
		
		pwfUsuPass1 = new JPasswordField();
		pwfUsuPass1.setBounds(117, 42, 171, 20);
		pnlDatosUsu.add(pwfUsuPass1);
		
		JLabel lblRepitaContrasea = new JLabel("Repita Contraseña");
		lblRepitaContrasea.setBounds(10, 76, 97, 14);
		pnlDatosUsu.add(lblRepitaContrasea);
		lblRepitaContrasea.setHorizontalAlignment(SwingConstants.RIGHT);
		
		pwfUsuPass2 = new JPasswordField();
		pwfUsuPass2.setBounds(117, 73, 171, 20);
		pnlDatosUsu.add(pwfUsuPass2);
		
		JLabel lblTipoUsuario = new JLabel("Tipo Usuario");
		lblTipoUsuario.setBounds(10, 107, 97, 14);
		pnlDatosUsu.add(lblTipoUsuario);
		lblTipoUsuario.setHorizontalAlignment(SwingConstants.RIGHT);
		
		cbxTipoUsr = new JComboBox<>();
		cbxTipoUsr.setBounds(117, 104, 171, 20);
		pnlDatosUsu.add(cbxTipoUsr);
		ctrlInterno.cargarCbxTipoUsr(cbxTipoUsr);
		
		jlUsuario.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent arg0) {
				ctrlInterno.cargarControlesUsuario(txtUsuNombre, cbxTipoUsr, jlUsuario);
			}
		});
		//boton agregar
		btnUsuAgr.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				ctrlInterno.registrarUsuario(txtUsuNombre, pwfUsuPass1, pwfUsuPass2, cbxTipoUsr);
			}
		});
		//boton modif
		btnUsuMod.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ctrlInterno.modificarUsuario(txtUsuNombre, cbxTipoUsr, jlUsuario);
			}
		});
		//boton eliminar
		btnUsuEli.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ctrlInterno.eliminarUsuario(jlUsuario);
			}
		});
		//boton limpiar
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ctrlInterno.clearControlsInJPanel(getPnlDatosUsu());
				ctrlInterno.setContainerEnabled(getPwfUsuPass1(), true);
				ctrlInterno.setContainerEnabled(getPwfUsuPass2(), true);
			}
		});
		/***************************************************/
		/* EVENTO CIERRE DEL IFRM */
		/***************************************************/
		addInternalFrameListener(new InternalFrameAdapter() {
			@Override
			public void internalFrameClosing(InternalFrameEvent arg0) {
				ctrlInterno.cerrarIFrmUsu();
			}
		});
	}
	
	
	public JTextField getTxtUsuNombre() {
		return txtUsuNombre;
	}
	public void setTxtUsuNombre(JTextField txtUsuNombre) {
		this.txtUsuNombre = txtUsuNombre;
	}

	public JPasswordField getPwfUsuPass1() {
		return pwfUsuPass1;
	}
	public void setPwfUsuPass1(JPasswordField pwfUsuPass1) {
		this.pwfUsuPass1 = pwfUsuPass1;
	}

	public JPasswordField getPwfUsuPass2() {
		return pwfUsuPass2;
	}
	public void setPwfUsuPass2(JPasswordField pwfUsuPass2) {
		this.pwfUsuPass2 = pwfUsuPass2;
	}

	public JComboBox<TipoUsr> getCbxTipoUsr() {
		return cbxTipoUsr;
	}
	public void setCbxTipoUsr(JComboBox<TipoUsr> cbxTipoUsr) {
		this.cbxTipoUsr = cbxTipoUsr;
	}
	
	public JPanel getPnlDatosUsu() {
		return pnlDatosUsu;
	}
	public void setPnlDatosUsu(JPanel pnlDatosUsu) {
		this.pnlDatosUsu = pnlDatosUsu;
	}
	
	public JList<UsuarioDsk> getJlUsuario() {
		return jlUsuario;
	}
	public void setJlUsuario(JList<UsuarioDsk> jlUsuario) {
		this.jlUsuario = jlUsuario;
	}
}
