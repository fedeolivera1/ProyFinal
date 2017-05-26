package gpd.presentacion.formulario;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.border.EmptyBorder;

import gpd.dominio.producto.TipoProd;
import gpd.dominio.usuario.UsuarioDsk;
import gpd.presentacion.controlador.CtrlFrmProducto;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class FrmPrincipal extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField txtProCodigo;
	private JTextField textProDesc;
	private JTextField txtProNombre;
	private JTextField txtProId;
	private JTable table;

	/**
	 * Launch the application.
	 */
//	public static void main(String[] args) {
//		EventQueue.invokeLater(new Runnable() {
//			public void run() {
//				try {
//					
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//			}
//		});
//	}

	/**
	 * Create the frame.
	 */
	public FrmPrincipal(UsuarioDsk usr) {
		CtrlFrmProducto ctrlProd = new CtrlFrmProducto();
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 800, 600);
		
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		JMenu mnArchivo = new JMenu("Archivo");
		menuBar.add(mnArchivo);
		
		JMenuItem mnSalir = new JMenuItem("Salir");
		mnArchivo.add(mnSalir);
		
		JMenu mnCliente = new JMenu("Cliente");
		menuBar.add(mnCliente);
		
		JMenuItem mntmClientePersona = new JMenuItem("Mant Cliente Persona");
		mnCliente.add(mntmClientePersona);
		
		JMenuItem mntmClienteEmpresa = new JMenuItem("Mant Cliente Empresa");
		mnCliente.add(mntmClienteEmpresa);
		
		JMenu mnProveedor = new JMenu("Proveedor");
		menuBar.add(mnProveedor);
		
		JMenuItem mntmMantProveedor = new JMenuItem("Mant Proveedor");
		mnProveedor.add(mntmMantProveedor);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel pnlProducto = new JPanel();
		pnlProducto.setBounds(0, 0, 784, 545);
		contentPane.add(pnlProducto);
		pnlProducto.setLayout(null);
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(10, 11, 662, 511);
		pnlProducto.add(tabbedPane);
		
		JPanel tpProd = new JPanel();
		tabbedPane.addTab("Producto", null, tpProd, null);
		tpProd.setLayout(null);
		
		txtProId = new JTextField();
		txtProId.setBounds(88, 33, 66, 20);
		txtProId.setEditable(false);
		tpProd.add(txtProId);
		txtProId.setColumns(10);
		
		JLabel lblTipo = new JLabel("Tipo");
		lblTipo.setBounds(26, 67, 46, 14);
		tpProd.add(lblTipo);
		
		JComboBox<TipoProd> cbxTipoProd = new JComboBox<TipoProd>();
		cbxTipoProd.setBounds(88, 64, 151, 20);
		tpProd.add(cbxTipoProd);
		
		JLabel lblCodigo = new JLabel("Codigo");
		lblCodigo.setBounds(26, 103, 46, 14);
		tpProd.add(lblCodigo);
		
		txtProCodigo = new JTextField();
		txtProCodigo.setBounds(88, 100, 151, 20);
		tpProd.add(txtProCodigo);
		txtProCodigo.setColumns(10);
		
		JLabel lblNombre = new JLabel("Nombre");
		lblNombre.setBounds(26, 137, 46, 14);
		tpProd.add(lblNombre);
		
		txtProNombre = new JTextField();
		txtProNombre.setBounds(89, 134, 151, 20);
		tpProd.add(txtProNombre);
		txtProNombre.setColumns(10);
		
		JLabel lblDescripcion = new JLabel("Descripcion");
		lblDescripcion.setBounds(26, 168, 59, 14);
		tpProd.add(lblDescripcion);
		
		textProDesc = new JTextField();
		textProDesc.setBounds(89, 165, 151, 20);
		tpProd.add(textProDesc);
		textProDesc.setColumns(10);
		
		JLabel lblStockMin = new JLabel("Stock Min");
		lblStockMin.setBounds(26, 199, 59, 14);
		tpProd.add(lblStockMin);
		
		JButton btnProAgr = new JButton("Agregar");
		btnProAgr.setBounds(151, 263, 89, 23);
		tpProd.add(btnProAgr);
		
		JButton btpProMod = new JButton("Modificar");
		btpProMod.setBounds(151, 291, 89, 23);
		tpProd.add(btpProMod);
		
		JButton btnProEli = new JButton("Eliminar");
		btnProEli.setBounds(151, 320, 89, 23);
		tpProd.add(btnProEli);
		
		JFormattedTextField ftxtProStockMin = new JFormattedTextField();
		ftxtProStockMin.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				ctrlProd.controlInputNum(e);
			}
		});
		ftxtProStockMin.setBounds(89, 196, 65, 20);
		tpProd.add(ftxtProStockMin);
		
		table = new JTable();
		table.setBounds(274, 345, 373, -308);
		table.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
		tpProd.add(table);
		
		JFormattedTextField ftxtProPrecio = new JFormattedTextField();
		ftxtProPrecio.setBounds(88, 227, 65, 20);
		ftxtProPrecio.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				ctrlProd.controlInputNum(e);
			}
		});
		tpProd.add(ftxtProPrecio);
		
		JLabel lblPrecio = new JLabel("Precio");
		lblPrecio.setBounds(26, 230, 46, 14);
		tpProd.add(lblPrecio);
		
		
		btnProAgr.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				ctrlProd.agregarProducto(txtProCodigo, txtProNombre, textProDesc, ftxtProStockMin, ftxtProPrecio);
			}
		});
		
		JPanel tpLote = new JPanel();
		tabbedPane.addTab("Lote", null, tpLote, null);
		tpLote.setLayout(null);
		
		JPanel tpDeposito = new JPanel();
		tabbedPane.addTab("Deposito", null, tpDeposito, null);
		tpDeposito.setLayout(null);
		
		JPanel pnlPersona = new JPanel();
		pnlPersona.setBounds(0, 0, 784, 545);
		contentPane.add(pnlPersona);
		pnlPersona.setLayout(null);
	}
}
