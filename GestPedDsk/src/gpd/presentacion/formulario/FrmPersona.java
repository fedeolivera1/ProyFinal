package gpd.presentacion.formulario;

import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import org.apache.log4j.Logger;

import gpd.dominio.persona.Departamento;
import gpd.dominio.persona.Localidad;
import gpd.dominio.persona.Sexo;
import gpd.dominio.persona.TipoDoc;
import gpd.dominio.usuario.UsuarioDsk;
import gpd.presentacion.controlador.CtrlFrmPersona;
import javax.swing.JCheckBox;

public class FrmPersona extends JFrame {

	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getLogger(FrmProducto.class);
	private static FrmPersona instance; 
	private CtrlFrmPersona ctrlPers;
	private JPanel contentPane;
	//pf
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
	private JComboBox<TipoDoc> cbxPfTipoDoc;
	private JComboBox<Sexo> cbxPfSexo;
	private JComboBox<Departamento> cbxPfDep;
	private JComboBox<Localidad> cbxPfLoc;
	private JTable jtPersFisica;

	//pj
	private JTextField txtPjRut;
	private JTextField txtPjNom;
	private JTextField txtPjRs;
	private JTextField txtPjBps;
	private JTextField txtPjBse;
	private JCheckBox chkPjProv;
	private JTextField txtPjDir;
	private JTextField txtPjPue;
	private JTextField txtPjSol;
	private JTextField txtPjMan;
	private JTextField txtPjKm;
	private JTextField txtPjComp;
	private JTextField txtPjTel;
	private JTextField txtPjCel;
	private JTextField txtPjEml;
	private JComboBox<Departamento> cbxPjDep;
	private JComboBox<Localidad> cbxPjLoc;
	private JTable jtPersJuridica;

	
	public static FrmPersona getFrmPersona(UsuarioDsk usr) {
		if(instance == null) {
			logger.info("Se genera nueva instancia de FrmPersona > usuario logueado: " + usr.getNomUsu());
			instance = new FrmPersona(usr);
		}
		return instance;
	}
	
	/**
	 * Create the frame.
	 */
	private FrmPersona(UsuarioDsk usr) {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
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
		
		cbxPfTipoDoc = new JComboBox<>();
		cbxPfTipoDoc.setBounds(84, 11, 151, 20);
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
		
		txtPfFnac = new JFormattedTextField(ctrlPers.mascNumerica("##/##/####"));
		txtPfFnac.setColumns(10);
		txtPfFnac.setBounds(84, 197, 65, 20);
		pnlPersF.add(txtPfFnac);
		
		JLabel lblSexo = new JLabel("Sexo");
		lblSexo.setHorizontalAlignment(SwingConstants.RIGHT);
		lblSexo.setBounds(10, 231, 64, 14);
		pnlPersF.add(lblSexo);
		
		cbxPfSexo = new JComboBox<>();
		cbxPfSexo.setBounds(84, 228, 65, 20);
		pnlPersF.add(cbxPfSexo);
		ctrlPers.cargarCbxSexo(cbxPfSexo);
		
		JSeparator sepPf1 = new JSeparator();
		sepPf1.setOrientation(SwingConstants.VERTICAL);
		sepPf1.setForeground(SystemColor.info);
		sepPf1.setBackground(SystemColor.info);
		sepPf1.setBounds(245, 11, 2, 268);
		pnlPersF.add(sepPf1);
		
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
		
		JSeparator sepPf2 = new JSeparator();
		sepPf2.setOrientation(SwingConstants.VERTICAL);
		sepPf2.setForeground(SystemColor.info);
		sepPf2.setBackground(SystemColor.info);
		sepPf2.setBounds(499, 11, 2, 268);
		pnlPersF.add(sepPf2);
		
		JButton btnPfAgr = new JButton("Agregar");
		btnPfAgr.setBounds(511, 10, 89, 23);
		pnlPersF.add(btnPfAgr);
		
		JButton btnPfMod = new JButton("Modificar");
		btnPfMod.setBounds(511, 41, 89, 23);
		pnlPersF.add(btnPfMod);
		
		JButton btnPfEli = new JButton("Eliminar");
		btnPfEli.setBounds(511, 72, 89, 23);
		pnlPersF.add(btnPfEli);
		
		JButton btnPfBuscar = new JButton("Buscar");
		btnPfBuscar.setBounds(511, 134, 89, 23);
		pnlPersF.add(btnPfBuscar);
		
		JButton btnPfLimpiar = new JButton("Limpiar");
		btnPfLimpiar.setBounds(511, 165, 89, 23);
		pnlPersF.add(btnPfLimpiar);
		
		JLabel lblDepartamento = new JLabel("Departamento");
		lblDepartamento.setHorizontalAlignment(SwingConstants.RIGHT);
		lblDepartamento.setBounds(257, 231, 71, 14);
		pnlPersF.add(lblDepartamento);
		
		cbxPfDep = new JComboBox<>();
		cbxPfDep.setBounds(338, 228, 151, 20);
		pnlPersF.add(cbxPfDep);
		
		JLabel lblLocalidad = new JLabel("Localidad");
		lblLocalidad.setHorizontalAlignment(SwingConstants.RIGHT);
		lblLocalidad.setBounds(257, 262, 71, 14);
		pnlPersF.add(lblLocalidad);
		
		cbxPfLoc = new JComboBox<>();
		cbxPfLoc.setBounds(338, 259, 151, 20);
		pnlPersF.add(cbxPfLoc);
		
		JSeparator sepPf3 = new JSeparator();
		sepPf3.setForeground(SystemColor.info);
		sepPf3.setBackground(SystemColor.info);
		sepPf3.setBounds(10, 290, 759, 2);
		pnlPersF.add(sepPf3);
		
		JPanel pnlPersJ = new JPanel();
		tabbedPane.addTab("Empresa", null, pnlPersJ, null);
		pnlPersJ.setLayout(null);
		
		JSeparator sepPj1 = new JSeparator();
		sepPj1.setOrientation(SwingConstants.VERTICAL);
		sepPj1.setForeground(SystemColor.info);
		sepPj1.setBackground(SystemColor.info);
		sepPj1.setBounds(245, 11, 2, 268);;
		
		JLabel lblRut = new JLabel("Rut");
		lblRut.setHorizontalAlignment(SwingConstants.RIGHT);
		lblRut.setBounds(10, 14, 64, 14);
		pnlPersJ.add(lblRut);
		
		txtPjRut = new JTextField();
		txtPjRut.setColumns(10);
		txtPjRut.setBounds(84, 11, 151, 20);
		pnlPersJ.add(txtPjRut);
		
		JLabel lblNombre_2 = new JLabel("Nombre");
		lblNombre_2.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNombre_2.setBounds(10, 45, 64, 14);
		pnlPersJ.add(lblNombre_2);
		
		txtPjNom = new JTextField();
		txtPjNom.setColumns(10);
		txtPjNom.setBounds(84, 42, 151, 20);
		pnlPersJ.add(txtPjNom);
		
		JLabel lblRazonSocial = new JLabel("Razon Social");
		lblRazonSocial.setHorizontalAlignment(SwingConstants.RIGHT);
		lblRazonSocial.setBounds(10, 76, 64, 14);
		pnlPersJ.add(lblRazonSocial);
		
		txtPjRs = new JTextField();
		txtPjRs.setColumns(10);
		txtPjRs.setBounds(84, 73, 151, 20);
		pnlPersJ.add(txtPjRs);
		
		JLabel lblBps = new JLabel("Bps");
		lblBps.setHorizontalAlignment(SwingConstants.RIGHT);
		lblBps.setBounds(10, 107, 64, 14);
		pnlPersJ.add(lblBps);
		
		txtPjBps = new JTextField();
		txtPjBps.setColumns(10);
		txtPjBps.setBounds(84, 104, 151, 20);
		pnlPersJ.add(txtPjBps);
		
		JLabel lblBse = new JLabel("Bse");
		lblBse.setHorizontalAlignment(SwingConstants.RIGHT);
		lblBse.setBounds(10, 138, 64, 14);
		pnlPersJ.add(lblBse);
		
		txtPjBse = new JTextField();
		txtPjBse.setColumns(10);
		txtPjBse.setBounds(84, 135, 151, 20);
		pnlPersJ.add(txtPjBse);
		
		chkPjProv = new JCheckBox("Proveedor  ");
		chkPjProv.setHorizontalTextPosition(SwingConstants.LEADING);
		chkPjProv.setBounds(20, 165, 97, 23);
		pnlPersJ.add(chkPjProv);
		pnlPersJ.add(sepPj1);
		
		JLabel label = new JLabel("Direccion");
		label.setHorizontalAlignment(SwingConstants.RIGHT);
		label.setBounds(264, 14, 64, 14);
		pnlPersJ.add(label);
		
		txtPjDir = new JTextField();
		txtPjDir.setColumns(10);
		txtPjDir.setBounds(338, 11, 151, 20);
		pnlPersJ.add(txtPjDir);
		
		JLabel label_1 = new JLabel("Puerta");
		label_1.setHorizontalAlignment(SwingConstants.RIGHT);
		label_1.setBounds(264, 45, 64, 14);
		pnlPersJ.add(label_1);
		
		txtPjPue = new JTextField();
		txtPjPue.setColumns(10);
		txtPjPue.setBounds(338, 42, 50, 20);
		pnlPersJ.add(txtPjPue);
		
		txtPjSol = new JTextField();
		txtPjSol.setColumns(10);
		txtPjSol.setBounds(439, 42, 50, 20);
		pnlPersJ.add(txtPjSol);
		
		JLabel label_2 = new JLabel("Manzana");
		label_2.setHorizontalAlignment(SwingConstants.RIGHT);
		label_2.setBounds(270, 76, 58, 14);
		pnlPersJ.add(label_2);
		
		txtPjMan = new JTextField();
		txtPjMan.setColumns(10);
		txtPjMan.setBounds(338, 73, 50, 20);
		pnlPersJ.add(txtPjMan);
		
		txtPjKm = new JTextField();
		txtPjKm.setColumns(10);
		txtPjKm.setBounds(439, 73, 50, 20);
		pnlPersJ.add(txtPjKm);
		
		JLabel label_3 = new JLabel("Complemento");
		label_3.setHorizontalAlignment(SwingConstants.RIGHT);
		label_3.setBounds(245, 107, 83, 14);
		pnlPersJ.add(label_3);
		
		txtPjComp = new JTextField();
		txtPjComp.setColumns(10);
		txtPjComp.setBounds(338, 104, 151, 20);
		pnlPersJ.add(txtPjComp);
		
		JLabel label_4 = new JLabel("Telefono");
		label_4.setHorizontalAlignment(SwingConstants.RIGHT);
		label_4.setBounds(257, 138, 71, 14);
		pnlPersJ.add(label_4);
		
		txtPjTel = new JTextField();
		txtPjTel.setColumns(10);
		txtPjTel.setBounds(338, 135, 151, 20);
		pnlPersJ.add(txtPjTel);
		
		JLabel label_5 = new JLabel("Celular");
		label_5.setHorizontalAlignment(SwingConstants.RIGHT);
		label_5.setBounds(257, 169, 71, 14);
		pnlPersJ.add(label_5);
		
		txtPjCel = new JTextField();
		txtPjCel.setColumns(10);
		txtPjCel.setBounds(338, 166, 151, 20);
		pnlPersJ.add(txtPjCel);
		
		JLabel label_6 = new JLabel("EMail");
		label_6.setHorizontalAlignment(SwingConstants.RIGHT);
		label_6.setBounds(257, 200, 71, 14);
		pnlPersJ.add(label_6);
		
		txtPjEml = new JTextField();
		txtPjEml.setColumns(10);
		txtPjEml.setBounds(338, 197, 151, 20);
		pnlPersJ.add(txtPjEml);
		
		JLabel label_7 = new JLabel("Departamento");
		label_7.setHorizontalAlignment(SwingConstants.RIGHT);
		label_7.setBounds(257, 231, 71, 14);
		pnlPersJ.add(label_7);
		
		cbxPjDep = new JComboBox<Departamento>();
		cbxPjDep.setBounds(338, 228, 151, 20);
		pnlPersJ.add(cbxPjDep);
		
		JLabel label_8 = new JLabel("Localidad");
		label_8.setHorizontalAlignment(SwingConstants.RIGHT);
		label_8.setBounds(257, 262, 71, 14);
		pnlPersJ.add(label_8);
		
		cbxPjLoc = new JComboBox<Localidad>();
		cbxPjLoc.setBounds(338, 259, 151, 20);
		pnlPersJ.add(cbxPjLoc);
		
		JLabel label_9 = new JLabel("Solar");
		label_9.setHorizontalAlignment(SwingConstants.RIGHT);
		label_9.setBounds(393, 45, 36, 14);
		pnlPersJ.add(label_9);
		
		JLabel label_10 = new JLabel("Km");
		label_10.setHorizontalAlignment(SwingConstants.RIGHT);
		label_10.setBounds(398, 76, 31, 14);
		pnlPersJ.add(label_10);
		
		JSeparator sepPj2 = new JSeparator();
		sepPj2.setOrientation(SwingConstants.VERTICAL);
		sepPj2.setForeground(SystemColor.info);
		sepPj2.setBackground(SystemColor.info);
		sepPj2.setBounds(499, 11, 2, 268);
		pnlPersJ.add(sepPj2);
		
		JButton btnPjAgr = new JButton("Agregar");
		btnPjAgr.setBounds(511, 10, 89, 23);
		pnlPersJ.add(btnPjAgr);
		
		JButton btnPjMod = new JButton("Modificar");
		btnPjMod.setBounds(511, 41, 89, 23);
		pnlPersJ.add(btnPjMod);
		
		JButton btnPjEli = new JButton("Eliminar");
		btnPjEli.setBounds(511, 72, 89, 23);
		pnlPersJ.add(btnPjEli);
		
		JButton btnPjBuscar = new JButton("Buscar");
		btnPjBuscar.setBounds(511, 134, 89, 23);
		pnlPersJ.add(btnPjBuscar);
		
		JButton btnPjLimpiar = new JButton("Limpiar");
		btnPjLimpiar.setBounds(511, 165, 89, 23);
		pnlPersJ.add(btnPjLimpiar);
		
		JSeparator sepPj3 = new JSeparator();
		sepPj3.setForeground(SystemColor.info);
		sepPj3.setBackground(SystemColor.info);
		sepPj3.setBounds(10, 290, 759, 2);
		pnlPersJ.add(sepPj3);
		
		JScrollPane scrollPanePj = new JScrollPane();
		scrollPanePj.setBounds(10, 303, 759, 223);
		pnlPersJ.add(scrollPanePj);
		
		JScrollPane scrollPanePf = new JScrollPane();
		scrollPanePf.setBounds(10, 303, 759, 223);
		pnlPersF.add(scrollPanePf);
		
		jtPersFisica = new JTable();
		jtPersFisica.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
		scrollPanePf.setColumnHeaderView(jtPersFisica);
		scrollPanePf.setViewportView(jtPersFisica);
		
		jtPersJuridica = new JTable();
		jtPersJuridica.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
		scrollPanePj.setColumnHeaderView(jtPersJuridica);
		scrollPanePj.setViewportView(jtPersJuridica);
		
		/*****************************************************************************************************************************************************/
		/* ACCIONES CONTROLES */
		/*****************************************************************************************************************************************************/
		
		ctrlPers.cargarCbxTipoDoc(cbxPfTipoDoc);
		ctrlPers.cargarCbxDep(cbxPfDep);
		ctrlPers.cargarCbxDep(cbxPjDep);
		
		txtPfFnac.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				ctrlPers.formatoFechaEnTxt(txtPfFnac);
			}
		});
		cbxPfDep.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				ctrlPers.cargarCbxLoc(cbxPfDep, cbxPfLoc);
			}
		});
		cbxPjDep.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				ctrlPers.cargarCbxLoc(cbxPjDep, cbxPjLoc);
			}
		});
		
		txtPfDoc.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				ctrlPers.controlInputNum(e);
			}
		});
		txtPjRut.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				ctrlPers.controlInputNum(e);
			}
		});
		
		btnPfAgr.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ctrlPers.agregarPersFisica(cbxPfTipoDoc, txtPfDoc, txtPfApe1, txtPfApe2, txtPfNom1, txtPfNom2, txtPfFnac, cbxPfSexo, 
						txtPfDir, txtPfPue, txtPfSol, txtPfMan, txtPfKm, txtPfComp, txtPfTel, txtPfCel, txtPfEml, cbxPfLoc);
			}
		});
		btnPfMod.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				ctrlPers.modificarPersFisica(cbxPfTipoDoc, txtPfDoc, txtPfApe1, txtPfApe2, txtPfNom1, txtPfNom2, txtPfFnac, cbxPfSexo, 
						txtPfDir, txtPfPue, txtPfSol, txtPfMan, txtPfKm, txtPfComp, txtPfTel, txtPfCel, txtPfEml, cbxPfLoc);
			}
		});
		btnPfEli.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ctrlPers.eliminarPersFisica(txtPfDoc);
			}
		});
		btnPfLimpiar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ctrlPers.clearForm(getContentPane());
			}
		});
		btnPfBuscar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ctrlPers.buscarPersFisica(txtPfDoc, txtPfApe1, txtPfApe2, txtPfNom1, txtPfNom2, cbxPfSexo, txtPfDir, txtPfTel, 
						txtPfCel, txtPfEml, cbxPfLoc);
			}
		});
		btnPjAgr.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ctrlPers.agregarPersJuridica(txtPjRut, txtPjNom, txtPjRs, txtPjBps, txtPjBse, chkPjProv, txtPjDir, txtPjPue, txtPjSol, txtPjMan, 
						txtPjKm, txtPjComp, txtPjTel, txtPjCel, txtPjEml, cbxPjLoc);
			}
		});
		btnPjMod.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				ctrlPers.modificarPersJuridica(txtPjRut, txtPjNom, txtPjRs, txtPjBps, txtPjBse, chkPjProv, txtPjDir, txtPjPue, txtPjSol, txtPjMan, 
						txtPjKm, txtPjComp, txtPjTel, txtPjCel, txtPjEml, cbxPjLoc);
			}
		});
		btnPjEli.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
//				ctrlPers.eliminarPersJuridica(txtPjRut);
			}
		});
		btnPjLimpiar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ctrlPers.clearForm(getContentPane());
			}
		});
		btnPjBuscar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ctrlPers.buscarPersJuridica(txtPjRut, txtPjNom, txtPjRs, txtPjBps, txtPjBse, chkPjProv,
						txtPjDir, txtPjTel, txtPjCel, txtPjEml, cbxPjLoc);
			}
		});
		
		/***************************************************/
		/* EVENTO CIERRE DEL FORM */
		/***************************************************/
		this.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosed(WindowEvent arg0) {
				instance = null;
			}
		});
	}

	
	/*****************************************************************************************************************************************************/
	/* GET Y SET DE CAMPOS */
	/*****************************************************************************************************************************************************/
	
	//pf
	public JTextField getTxtPfDoc() {
		return txtPfDoc;
	}
	public void setTxtPfDoc(JTextField txtPfDoc) {
		this.txtPfDoc = txtPfDoc;
	}

	public JTextField getTxtPfApe1() {
		return txtPfApe1;
	}
	public void setTxtPfApe1(JTextField txtPfApe1) {
		this.txtPfApe1 = txtPfApe1;
	}

	public JTextField getTxtPfApe2() {
		return txtPfApe2;
	}
	public void setTxtPfApe2(JTextField txtPfApe2) {
		this.txtPfApe2 = txtPfApe2;
	}

	public JTextField getTxtPfNom1() {
		return txtPfNom1;
	}
	public void setTxtPfNom1(JTextField txtPfNom1) {
		this.txtPfNom1 = txtPfNom1;
	}

	public JTextField getTxtPfNom2() {
		return txtPfNom2;
	}
	public void setTxtPfNom2(JTextField txtPfNom2) {
		this.txtPfNom2 = txtPfNom2;
	}

	public JTextField getTxtPfFnac() {
		return txtPfFnac;
	}
	public void setTxtPfFnac(JTextField txtPfFnac) {
		this.txtPfFnac = txtPfFnac;
	}

	public JTextField getTxtPfDir() {
		return txtPfDir;
	}
	public void setTxtPfDir(JTextField txtPfDir) {
		this.txtPfDir = txtPfDir;
	}

	public JTextField getTxtPfPue() {
		return txtPfPue;
	}
	public void setTxtPfPue(JTextField txtPfPue) {
		this.txtPfPue = txtPfPue;
	}

	public JTextField getTxtPfSol() {
		return txtPfSol;
	}
	public void setTxtPfSol(JTextField txtPfSol) {
		this.txtPfSol = txtPfSol;
	}

	public JTextField getTxtPfMan() {
		return txtPfMan;
	}
	public void setTxtPfMan(JTextField txtPfMan) {
		this.txtPfMan = txtPfMan;
	}

	public JTextField getTxtPfKm() {
		return txtPfKm;
	}
	public void setTxtPfKm(JTextField txtPfKm) {
		this.txtPfKm = txtPfKm;
	}

	public JTextField getTxtPfComp() {
		return txtPfComp;
	}
	public void setTxtPfComp(JTextField txtPfComp) {
		this.txtPfComp = txtPfComp;
	}

	public JTextField getTxtPfTel() {
		return txtPfTel;
	}
	public void setTxtPfTel(JTextField txtPfTel) {
		this.txtPfTel = txtPfTel;
	}

	public JTextField getTxtPfCel() {
		return txtPfCel;
	}
	public void setTxtPfCel(JTextField txtPfCel) {
		this.txtPfCel = txtPfCel;
	}

	public JTextField getTxtPfEml() {
		return txtPfEml;
	}
	public void setTxtPfEml(JTextField txtPfEml) {
		this.txtPfEml = txtPfEml;
	}

	public JComboBox<TipoDoc> getCbxPfTipoDoc() {
		return cbxPfTipoDoc;
	}
	public void setCbxPfTipoDoc(JComboBox<TipoDoc> cbxPfTipoDoc) {
		this.cbxPfTipoDoc = cbxPfTipoDoc;
	}

	public JComboBox<Sexo> getCbxPfSexo() {
		return cbxPfSexo;
	}
	public void setCbxPfSexo(JComboBox<Sexo> cbxPfSexo) {
		this.cbxPfSexo = cbxPfSexo;
	}
	
	public JComboBox<Departamento> getCbxPfDep() {
		return cbxPfDep;
	}
	public void setCbxPfDep(JComboBox<Departamento> cbxPfDep) {
		this.cbxPfDep = cbxPfDep;
	}

	public JComboBox<Localidad> getCbxPfLoc() {
		return cbxPfLoc;
	}
	public void setCbxPfLoc(JComboBox<Localidad> cbxPfLoc) {
		this.cbxPfLoc = cbxPfLoc;
	}

	public JTable getJtPersFisica() {
		return jtPersFisica;
	}
	public void setJtPersFisica(JTable jtPersFisica) {
		this.jtPersFisica = jtPersFisica;
	}

	//pj
	public JTextField getTxtPjRut() {
		return txtPjRut;
	}
	public void setTxtPjRut(JTextField txtPjRut) {
		this.txtPjRut = txtPjRut;
	}

	public JTextField getTxtPjNom() {
		return txtPjNom;
	}
	public void setTxtPjNom(JTextField txtPjNom) {
		this.txtPjNom = txtPjNom;
	}

	public JTextField getTxtPjRs() {
		return txtPjRs;
	}
	public void setTxtPjRs(JTextField txtPjRs) {
		this.txtPjRs = txtPjRs;
	}

	public JTextField getTxtPjBps() {
		return txtPjBps;
	}
	public void setTxtPjBps(JTextField txtPjBps) {
		this.txtPjBps = txtPjBps;
	}

	public JTextField getTxtPjBse() {
		return txtPjBse;
	}
	public void setTxtPjBse(JTextField txtPjBse) {
		this.txtPjBse = txtPjBse;
	}
	
	public JCheckBox getChkPjProv() {
		return chkPjProv;
	}

	public void setChkPjProv(JCheckBox chkPjProv) {
		this.chkPjProv = chkPjProv;
	}
	
	public JTextField getTxtPjDir() {
		return txtPjDir;
	}
	public void setTxtPjDir(JTextField txtPjDir) {
		this.txtPjDir = txtPjDir;
	}

	public JTextField getTxtPjPue() {
		return txtPjPue;
	}
	public void setTxtPjPue(JTextField txtPjPue) {
		this.txtPjPue = txtPjPue;
	}

	public JTextField getTxtPjSol() {
		return txtPjSol;
	}
	public void setTxtPjSol(JTextField txtPjSol) {
		this.txtPjSol = txtPjSol;
	}

	public JTextField getTxtPjMan() {
		return txtPjMan;
	}
	public void setTxtPjMan(JTextField txtPjMan) {
		this.txtPjMan = txtPjMan;
	}

	public JTextField getTxtPjKm() {
		return txtPjKm;
	}
	public void setTxtPjKm(JTextField txtPjKm) {
		this.txtPjKm = txtPjKm;
	}

	public JTextField getTxtPjComp() {
		return txtPjComp;
	}
	public void setTxtPjComp(JTextField txtPjComp) {
		this.txtPjComp = txtPjComp;
	}

	public JTextField getTxtPjTel() {
		return txtPjTel;
	}
	public void setTxtPjTel(JTextField txtPjTel) {
		this.txtPjTel = txtPjTel;
	}

	public JTextField getTxtPjCel() {
		return txtPjCel;
	}
	public void setTxtPjCel(JTextField txtPjCel) {
		this.txtPjCel = txtPjCel;
	}

	public JTextField getTxtPjEml() {
		return txtPjEml;
	}
	public void setTxtPjEml(JTextField txtPjEml) {
		this.txtPjEml = txtPjEml;
	}
	
	public JComboBox<Departamento> getCbxPjDep() {
		return cbxPjDep;
	}
	public void setCbxPjDep(JComboBox<Departamento> cbxPjDep) {
		this.cbxPjDep = cbxPjDep;
	}

	public JComboBox<Localidad> getCbxPjLoc() {
		return cbxPjLoc;
	}
	public void setCbxPjLoc(JComboBox<Localidad> cbxPjLoc) {
		this.cbxPjLoc = cbxPjLoc;
	}
	
	public JTable getJtPersJuridica() {
		return jtPersJuridica;
	}
	public void setJtPersJuridica(JTable jtPersJuridica) {
		this.jtPersJuridica = jtPersJuridica;
	}
}
