package gpd.presentacion.formulario;

import java.awt.SystemColor;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import org.apache.log4j.Logger;

import gpd.dominio.persona.Sexo;
import gpd.dominio.persona.TipoDoc;
import gpd.dominio.usuario.UsuarioDsk;
import gpd.presentacion.controlador.CtrlFrmPersona;

public class FrmPersona extends JFrame {

	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getLogger(FrmProducto.class);
	private static FrmPersona instance; 
	private JPanel contentPane;
	private JTextField txtPfDoc;
	private JTextField txtPfApe1;
	private JTextField txtPfApe2;
	private JTextField txtPfNom1;
	private JTextField txtPfNom2;
	private JTextField txtPfFnac;
	private JTextField txtPfDir;
	private JTextField txtPfPue;
	private JTextField txtPfSol;
	private JTextField txtPfMan;
	private JTextField txtPfKm;
	private JTextField txtPfComp;
	private JTextField txtPfTel;
	private JTextField txtPfCel;
	private JTextField txtPfEml;
	private CtrlFrmPersona ctrlPers;

	/**
	 * Launch the application.
	 */
//	public static void main(String[] args) {
//		EventQueue.invokeLater(new Runnable() {
//			public void run() {
//				try {
//					FrmCliente frame = new FrmCliente();
//					frame.setVisible(true);
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//			}
//		});
//	}

	public static FrmPersona getFrmCliente(UsuarioDsk usr) {
		if(instance == null) {
			logger.info("Se genera nueva instancia de FrmCliente > usuario logueado: " + usr.getNomUsu());
			instance = new FrmPersona(usr);
		}
		return instance;
	}
	
	/**
	 * Create the frame.
	 */
	private FrmPersona(UsuarioDsk usr) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 800, 600);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		ctrlPers = new CtrlFrmPersona(this);
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(0, 0, 784, 565);
		contentPane.add(tabbedPane);
		
		JPanel pnlPersF = new JPanel();
		tabbedPane.addTab("Cliente", null, pnlPersF, null);
		pnlPersF.setLayout(null);
		
		JLabel lblTipoDoc = new JLabel("Tipo Doc");
		lblTipoDoc.setHorizontalAlignment(SwingConstants.RIGHT);
		lblTipoDoc.setBounds(10, 14, 64, 14);
		pnlPersF.add(lblTipoDoc);
		
		JComboBox<TipoDoc> cbxPfTipoDoc = new JComboBox<>();
		cbxPfTipoDoc.setBounds(84, 11, 151, 20);
		ctrlPers.cargarCbxTipoDoc(cbxPfTipoDoc);
		pnlPersF.add(cbxPfTipoDoc);
		
		JLabel lblDocumento = new JLabel("Documento");
		lblDocumento.setHorizontalAlignment(SwingConstants.RIGHT);
		lblDocumento.setBounds(10, 45, 64, 14);
		pnlPersF.add(lblDocumento);
		
		txtPfDoc = new JTextField();
		txtPfDoc.setColumns(10);
		txtPfDoc.setBounds(84, 42, 151, 20);
		pnlPersF.add(txtPfDoc);
		
		JLabel lblApellido = new JLabel("Apellido 1");
		lblApellido.setHorizontalAlignment(SwingConstants.RIGHT);
		lblApellido.setBounds(10, 76, 64, 14);
		pnlPersF.add(lblApellido);
		
		txtPfApe1 = new JTextField();
		txtPfApe1.setColumns(10);
		txtPfApe1.setBounds(84, 73, 151, 20);
		pnlPersF.add(txtPfApe1);
		
		JLabel lblApellido_1 = new JLabel("Apellido 2");
		lblApellido_1.setHorizontalAlignment(SwingConstants.RIGHT);
		lblApellido_1.setBounds(10, 107, 64, 14);
		pnlPersF.add(lblApellido_1);
		
		txtPfApe2 = new JTextField();
		txtPfApe2.setColumns(10);
		txtPfApe2.setBounds(84, 104, 151, 20);
		pnlPersF.add(txtPfApe2);
		
		JLabel lblNombre = new JLabel("Nombre 1");
		lblNombre.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNombre.setBounds(10, 138, 64, 14);
		pnlPersF.add(lblNombre);
		
		txtPfNom1 = new JTextField();
		txtPfNom1.setColumns(10);
		txtPfNom1.setBounds(84, 135, 151, 20);
		pnlPersF.add(txtPfNom1);
		
		JLabel lblNombre_1 = new JLabel("Nombre 2");
		lblNombre_1.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNombre_1.setBounds(10, 169, 64, 14);
		pnlPersF.add(lblNombre_1);
		
		txtPfNom2 = new JTextField();
		txtPfNom2.setColumns(10);
		txtPfNom2.setBounds(84, 166, 151, 20);
		pnlPersF.add(txtPfNom2);
		
		JLabel lblFNac = new JLabel("F. Nac");
		lblFNac.setHorizontalAlignment(SwingConstants.RIGHT);
		lblFNac.setBounds(10, 200, 64, 14);
		pnlPersF.add(lblFNac);
		
		txtPfFnac = new JTextField();
		txtPfFnac.setColumns(10);
		txtPfFnac.setBounds(84, 197, 151, 20);
		pnlPersF.add(txtPfFnac);
		
		JLabel lblSexo = new JLabel("Sexo");
		lblSexo.setHorizontalAlignment(SwingConstants.RIGHT);
		lblSexo.setBounds(10, 231, 64, 14);
		pnlPersF.add(lblSexo);
		
		JComboBox<Sexo> cbxPfSexo = new JComboBox<>();
		cbxPfSexo.setBounds(84, 228, 65, 20);
		pnlPersF.add(cbxPfSexo);
		
		JSeparator separator = new JSeparator();
		separator.setOrientation(SwingConstants.VERTICAL);
		separator.setForeground(SystemColor.text);
		separator.setBackground(SystemColor.info);
		separator.setBounds(245, 11, 2, 244);
		pnlPersF.add(separator);
		
		JLabel lblDireccion = new JLabel("Direccion");
		lblDireccion.setHorizontalAlignment(SwingConstants.RIGHT);
		lblDireccion.setBounds(264, 14, 64, 14);
		pnlPersF.add(lblDireccion);
		
		txtPfDir = new JTextField();
		txtPfDir.setColumns(10);
		txtPfDir.setBounds(338, 11, 151, 20);
		pnlPersF.add(txtPfDir);
		
		JLabel lblSolar = new JLabel("Puerta");
		lblSolar.setHorizontalAlignment(SwingConstants.RIGHT);
		lblSolar.setBounds(264, 45, 64, 14);
		pnlPersF.add(lblSolar);
		
		txtPfPue = new JTextField();
		txtPfPue.setColumns(10);
		txtPfPue.setBounds(338, 42, 50, 20);
		pnlPersF.add(txtPfPue);
		
		JLabel lblSolar_1 = new JLabel("Solar");
		lblSolar_1.setHorizontalAlignment(SwingConstants.RIGHT);
		lblSolar_1.setBounds(393, 45, 36, 14);
		pnlPersF.add(lblSolar_1);
		
		txtPfSol = new JTextField();
		txtPfSol.setColumns(10);
		txtPfSol.setBounds(439, 42, 50, 20);
		pnlPersF.add(txtPfSol);
		
		JLabel lblManzana = new JLabel("Manzana");
		lblManzana.setHorizontalAlignment(SwingConstants.RIGHT);
		lblManzana.setBounds(270, 76, 58, 14);
		pnlPersF.add(lblManzana);
		
		txtPfMan = new JTextField();
		txtPfMan.setColumns(10);
		txtPfMan.setBounds(338, 73, 50, 20);
		pnlPersF.add(txtPfMan);
		
		JLabel lblKm = new JLabel("Km");
		lblKm.setHorizontalAlignment(SwingConstants.RIGHT);
		lblKm.setBounds(398, 76, 31, 14);
		pnlPersF.add(lblKm);
		
		txtPfKm = new JTextField();
		txtPfKm.setColumns(10);
		txtPfKm.setBounds(439, 73, 50, 20);
		pnlPersF.add(txtPfKm);
		
		JLabel lblComplemento = new JLabel("Complemento");
		lblComplemento.setHorizontalAlignment(SwingConstants.RIGHT);
		lblComplemento.setBounds(245, 107, 83, 14);
		pnlPersF.add(lblComplemento);
		
		txtPfComp = new JTextField();
		txtPfComp.setColumns(10);
		txtPfComp.setBounds(338, 104, 151, 20);
		pnlPersF.add(txtPfComp);
		
		JLabel lblTelefono = new JLabel("Telefono");
		lblTelefono.setHorizontalAlignment(SwingConstants.RIGHT);
		lblTelefono.setBounds(257, 138, 71, 14);
		pnlPersF.add(lblTelefono);
		
		txtPfTel = new JTextField();
		txtPfTel.setColumns(10);
		txtPfTel.setBounds(338, 135, 151, 20);
		pnlPersF.add(txtPfTel);
		
		JLabel lblCelular = new JLabel("Celular");
		lblCelular.setHorizontalAlignment(SwingConstants.RIGHT);
		lblCelular.setBounds(257, 169, 71, 14);
		pnlPersF.add(lblCelular);
		
		txtPfCel = new JTextField();
		txtPfCel.setColumns(10);
		txtPfCel.setBounds(338, 166, 151, 20);
		pnlPersF.add(txtPfCel);
		
		JLabel lblEmail = new JLabel("EMail");
		lblEmail.setHorizontalAlignment(SwingConstants.RIGHT);
		lblEmail.setBounds(257, 200, 71, 14);
		pnlPersF.add(lblEmail);
		
		txtPfEml = new JTextField();
		txtPfEml.setColumns(10);
		txtPfEml.setBounds(338, 197, 151, 20);
		pnlPersF.add(txtPfEml);
		
		JButton btnPfAgr = new JButton("Agregar");
		btnPfAgr.setBounds(499, 10, 89, 23);
		pnlPersF.add(btnPfAgr);
		
		JButton btnPfMod = new JButton("Modificar");
		btnPfMod.setBounds(499, 41, 89, 23);
		pnlPersF.add(btnPfMod);
		
		JButton btnPfEli = new JButton("Eliminar");
		btnPfEli.setBounds(499, 72, 89, 23);
		pnlPersF.add(btnPfEli);
		
		JButton btnPfBuscar = new JButton("Buscar");
		btnPfBuscar.setBounds(499, 196, 89, 23);
		pnlPersF.add(btnPfBuscar);
		
		JPanel pnlPersJ = new JPanel();
		tabbedPane.addTab("Empresa", null, pnlPersJ, null);
		pnlPersJ.setLayout(null);
	}
}
