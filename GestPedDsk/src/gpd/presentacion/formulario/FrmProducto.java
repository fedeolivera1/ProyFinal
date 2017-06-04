package gpd.presentacion.formulario;

import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDesktopPane;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import org.apache.log4j.Logger;

import gpd.dominio.producto.Deposito;
import gpd.dominio.producto.TipoProd;
import gpd.dominio.producto.Utilidad;
import gpd.dominio.usuario.UsuarioDsk;
import gpd.presentacion.controlador.CtrlFrmProducto;

public class FrmProducto extends JFrame {

	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getLogger(FrmProducto.class);

	private static FrmProducto instance; 
	private JPanel contentPane;
	private JTextField txtProId;
	private JTextField txtProCod;
	private JTextField txtProNom;
	private JTextField txtProDesc;
	private JFormattedTextField ftxtProStockMin;
	private JFormattedTextField ftxtProPrecio;
	private JComboBox<TipoProd> cbxTipoProd;
	private JDesktopPane desktopPane;
	private CtrlFrmProducto ctrlProd;
	private JButton btnTpAgregar;
	private JButton btnProAgr;
	private JButton btnProMod;
	private JButton btnProEli;
	private JButton btnProBuscar;
	private JButton btnProLimpiar;
	private JTable jtProd;
	private JComboBox<Deposito> cbxDep;
	private JComboBox<Utilidad> cbxUtil;
	private JLabel label_6;
	private JScrollPane scrollPaneLote;
	private JLabel lblLotesDisponibles;
	private JButton btnUtilAgregar;
	private JTable jtLote;


	public static FrmProducto getFrmProducto(UsuarioDsk usr) {
		if(instance == null) {
			logger.info("Se genera nueva instancia de FrmProducto > usuario logueado: " + usr.getNomUsu());
			instance = new FrmProducto(usr);
		}
		return instance;
	}

	/**
	 * Create the frame.
	 */
	private FrmProducto(UsuarioDsk usr) {
		
		setLocationRelativeTo(null);
		setTitle("Producto");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 800, 600);
		ctrlProd = new CtrlFrmProducto(this);
		
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		desktopPane = new JDesktopPane();
		desktopPane.setBackground(SystemColor.control);
		desktopPane.setBounds(0, 0, 0, 0);
		desktopPane.setLayout(null);
		contentPane.add(desktopPane);
		//agrego desktopPane a controlador
		ctrlProd.setDeskPane(desktopPane);
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(0, 0, 784, 565);
		contentPane.add(tabbedPane);
		
		JPanel tpProd = new JPanel();
		tabbedPane.addTab("Producto", null, tpProd, null);
		tpProd.setLayout(null);
		
		txtProId = new JTextField();
		txtProId.setEditable(false);
		txtProId.setColumns(10);
		txtProId.setBounds(72, 12, 66, 20);
		tpProd.add(txtProId);
		
		JLabel label = new JLabel("Tipo");
		label.setHorizontalAlignment(SwingConstants.RIGHT);
		label.setBounds(23, 45, 46, 14);
		tpProd.add(label);
		
		cbxTipoProd = new JComboBox<>();
		cbxTipoProd.setBounds(72, 42, 151, 20);
		tpProd.add(cbxTipoProd);
		
		JLabel label_1 = new JLabel("Codigo");
		label_1.setHorizontalAlignment(SwingConstants.RIGHT);
		label_1.setBounds(23, 73, 46, 14);
		tpProd.add(label_1);
		
		txtProCod = new JTextField();
		txtProCod.setColumns(10);
		txtProCod.setBounds(72, 70, 151, 20);
		tpProd.add(txtProCod);
		
		JLabel label_2 = new JLabel("Nombre");
		label_2.setHorizontalAlignment(SwingConstants.RIGHT);
		label_2.setBounds(23, 104, 46, 14);
		tpProd.add(label_2);
		
		txtProNom = new JTextField();
		txtProNom.setColumns(10);
		txtProNom.setBounds(72, 101, 151, 20);
		tpProd.add(txtProNom);
		
		JLabel label_3 = new JLabel("Descripcion");
		label_3.setHorizontalAlignment(SwingConstants.RIGHT);
		label_3.setBounds(10, 132, 59, 14);
		tpProd.add(label_3);
		
		txtProDesc = new JTextField();
		txtProDesc.setColumns(10);
		txtProDesc.setBounds(72, 129, 151, 20);
		tpProd.add(txtProDesc);
		
		JLabel label_4 = new JLabel("Stock Min");
		label_4.setHorizontalAlignment(SwingConstants.RIGHT);
		label_4.setBounds(10, 160, 59, 14);
		tpProd.add(label_4);
		
		ftxtProStockMin = new JFormattedTextField();
		ftxtProStockMin.setBounds(72, 157, 65, 20);
		tpProd.add(ftxtProStockMin);
		
		ftxtProPrecio = new JFormattedTextField();
		ftxtProPrecio.setBounds(72, 185, 65, 20);
		
		JLabel label_5 = new JLabel("Precio");
		label_5.setHorizontalAlignment(SwingConstants.RIGHT);
		label_5.setBounds(23, 188, 46, 14);
		tpProd.add(label_5);
		tpProd.add(ftxtProPrecio);
		
		/**
		 * boton agregar
		 */
		btnProAgr = new JButton("Agregar");
		btnProAgr.setBounds(282, 11, 89, 23);
		tpProd.add(btnProAgr);
		
		
		JSeparator sepProd1 = new JSeparator();
		sepProd1.setOrientation(SwingConstants.VERTICAL);
		sepProd1.setForeground(SystemColor.info);
		sepProd1.setBackground(SystemColor.info);
		sepProd1.setBounds(270, 11, 2, 268);
		tpProd.add(sepProd1);
		
		/**
		 * boton modificar
		 */
		btnProMod = new JButton("Modificar");
		btnProMod.setBounds(282, 45, 89, 23);
		tpProd.add(btnProMod);
		
		/**
		 * boton eliminar
		 */
		btnProEli = new JButton("Eliminar");
		btnProEli.setBounds(282, 79, 89, 23);
		tpProd.add(btnProEli);
		
		/**
		 * boton agregar tipo prod
		 */
		btnTpAgregar = new JButton("...");
		btnTpAgregar.setBounds(228, 41, 32, 23);
		tpProd.add(btnTpAgregar);
		
		btnProBuscar = new JButton("Buscar");
		btnProBuscar.setBounds(282, 129, 89, 23);
		tpProd.add(btnProBuscar);
		
		btnProLimpiar = new JButton("Limpiar");
		btnProLimpiar.setBounds(282, 160, 89, 23);
		tpProd.add(btnProLimpiar);
		
		JPanel tpLote = new JPanel();
		tabbedPane.addTab("Lote", null, tpLote, null);
		tpLote.setLayout(null);
		
		cbxDep = new JComboBox<>();
		cbxDep.setBounds(72, 404, 151, 20);
		tpLote.add(cbxDep);
		
		JButton btnDepAgregar = new JButton("...");
		btnDepAgregar.setBounds(233, 403, 32, 23);
		tpLote.add(btnDepAgregar);
		
		JLabel lblDeposito = new JLabel("Deposito");
		lblDeposito.setHorizontalAlignment(SwingConstants.RIGHT);
		lblDeposito.setBounds(16, 407, 46, 14);
		tpLote.add(lblDeposito);
		
		JScrollPane scrollPaneProd = new JScrollPane();
		scrollPaneProd.setBounds(10, 303, 759, 223);
		tpProd.add(scrollPaneProd);
		
		jtProd = new JTable();
		scrollPaneProd.setColumnHeaderView(jtProd);
		scrollPaneProd.setColumnHeaderView(jtProd);
		scrollPaneProd.setViewportView(jtProd);
		
		cbxUtil = new JComboBox<>();
		cbxUtil.setBounds(72, 435, 151, 20);
		tpLote.add(cbxUtil);
		
		label_6 = new JLabel("Utilidad");
		label_6.setHorizontalAlignment(SwingConstants.RIGHT);
		label_6.setBounds(10, 438, 52, 14);
		tpLote.add(label_6);
		
		scrollPaneLote = new JScrollPane();
		scrollPaneLote.setBounds(16, 36, 750, 160);
		tpLote.add(scrollPaneLote);
		
		jtLote = new JTable();
		scrollPaneLote.setColumnHeaderView(jtLote);
		
		lblLotesDisponibles = new JLabel("Lotes disponibles");
		lblLotesDisponibles.setHorizontalAlignment(SwingConstants.LEFT);
		lblLotesDisponibles.setBounds(16, 11, 95, 14);
		tpLote.add(lblLotesDisponibles);
		
		btnUtilAgregar = new JButton("...");
		btnUtilAgregar.setBounds(233, 434, 32, 23);
		tpLote.add(btnUtilAgregar);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(16, 232, 750, 160);
		tpLote.add(scrollPane);
		
		JLabel lblProductosDelLote = new JLabel("Productos del lote");
		lblProductosDelLote.setHorizontalAlignment(SwingConstants.LEFT);
		lblProductosDelLote.setBounds(16, 207, 95, 14);
		tpLote.add(lblProductosDelLote);
		
		/*****************************************************************************************************************************************************/
		/* ACCIONES CONTROLES */
		/*****************************************************************************************************************************************************/

		ctrlProd.cargarCbxTipoProd(cbxTipoProd);
		ctrlProd.cargarCbxDep(cbxDep);
		ctrlProd.cargarCbxUtil(cbxUtil);
		
		
		ftxtProStockMin.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				ctrlProd.controlInputNum(e);
			}
		});
		ftxtProPrecio.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				ctrlProd.controlInputNum(e);
			}
		});
		btnProAgr.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				ctrlProd.agregarProducto(cbxTipoProd, txtProCod, txtProNom, txtProDesc, ftxtProStockMin, ftxtProPrecio);
			}
		});
		btnProMod.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ctrlProd.modificarProducto(cbxTipoProd, txtProId, txtProCod, txtProNom, txtProDesc, ftxtProStockMin, ftxtProPrecio);
			}
		});
		btnProEli.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ctrlProd.eliminarProducto(txtProId);
			}
		});
		btnTpAgregar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ctrlProd.abrirIFrmTp();
			}
		});
		btnProBuscar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				ctrlProd.buscarProducto(cbxTipoProd, txtProCod, txtProNom, txtProDesc);
			}
		});
		btnProLimpiar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ctrlProd.clearForm(getContentPane());
			}
		});
		
	}
	

	/*****************************************************************************************************************************************************/
	/* GET Y SET DE CAMPOS */
	/*****************************************************************************************************************************************************/
	
	public CtrlFrmProducto getCtrlProd() {
		return ctrlProd;
	}
	public void setCtrlProd(CtrlFrmProducto ctrlProd) {
		this.ctrlProd = ctrlProd;
	}
	
	public JComboBox<TipoProd> getCbxTipoProd() {
		return this.cbxTipoProd;
	}
	public void setCbxTipoProd(JComboBox<TipoProd> cbxTipoProd) {
		this.cbxTipoProd = cbxTipoProd;
	}
	
	public JComboBox<Deposito> getCbxDep() {
		return cbxDep;
	}
	public void setCbxDep(JComboBox<Deposito> cbxDep) {
		this.cbxDep = cbxDep;
	}
	
	public JTextField getTxtProId() {
		return txtProId;
	}
	public void setTxtProId(JTextField txtProId) {
		this.txtProId = txtProId;
	}

	public JTextField getTxtProCod() {
		return txtProCod;
	}
	public void setTxtProCod(JTextField txtProCod) {
		this.txtProCod = txtProCod;
	}
	
	public JTextField getTxtProNom() {
		return txtProNom;
	}
	public void setTxtProNom(JTextField txtProNom) {
		this.txtProNom = txtProNom;
	}

	public JTextField getTxtProDesc() {
		return txtProDesc;
	}
	public void setTxtProDesc(JTextField txtProDesc) {
		this.txtProDesc = txtProDesc;
	}

	public JFormattedTextField getFtxtProStockMin() {
		return ftxtProStockMin;
	}
	public void setFtxtProStockMin(JFormattedTextField ftxtProStockMin) {
		this.ftxtProStockMin = ftxtProStockMin;
	}

	public JFormattedTextField getFtxtProPrecio() {
		return ftxtProPrecio;
	}
	public void setFtxtProPrecio(JFormattedTextField ftxtProPrecio) {
		this.ftxtProPrecio = ftxtProPrecio;
	}
	
	public JTable getJtProd() {
		return jtProd;
	}
	public void setJtProd(JTable jtProd) {
		this.jtProd = jtProd;
	}
	
	public JComboBox<Utilidad> getCbxUtil() {
		return cbxUtil;
	}
	public void setCbxUtil(JComboBox<Utilidad> cbxUtil) {
		this.cbxUtil = cbxUtil;
	}
	
}
