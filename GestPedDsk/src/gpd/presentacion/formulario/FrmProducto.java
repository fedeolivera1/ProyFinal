package gpd.presentacion.formulario;

import java.awt.Font;
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
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import org.apache.log4j.Logger;

import gpd.dominio.producto.TipoProd;
import gpd.dominio.usuario.UsuarioDsk;
import gpd.presentacion.controlador.CtrlFrmProducto;
import javax.swing.JSeparator;

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

	/**
	 * Launch the application.
	 */
//	public static void main(String[] args) {
//		EventQueue.invokeLater(new Runnable() {
//			public void run() {
//				try {
//					FrmProducto frame = new FrmProducto();
//					frame.setVisible(true);
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//			}
//		});
//	}

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
		desktopPane.setBounds(784, 1, 1, 1);
		contentPane.add(desktopPane);
		desktopPane.setLayout(null);
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
		txtProId.setBounds(72, 11, 66, 20);
		tpProd.add(txtProId);
		
		JLabel label = new JLabel("Tipo");
		label.setHorizontalAlignment(SwingConstants.RIGHT);
		label.setBounds(23, 45, 46, 14);
		tpProd.add(label);
		
		cbxTipoProd = new JComboBox<TipoProd>();
		cbxTipoProd.setBounds(72, 42, 151, 20);
		ctrlProd.cargarCbxTipoProd(cbxTipoProd);
		tpProd.add(cbxTipoProd);
		
		JLabel label_1 = new JLabel("Codigo");
		label_1.setHorizontalAlignment(SwingConstants.RIGHT);
		label_1.setBounds(23, 81, 46, 14);
		tpProd.add(label_1);
		
		txtProCod = new JTextField();
		txtProCod.setColumns(10);
		txtProCod.setBounds(72, 78, 151, 20);
		tpProd.add(txtProCod);
		
		JLabel label_2 = new JLabel("Nombre");
		label_2.setHorizontalAlignment(SwingConstants.RIGHT);
		label_2.setBounds(23, 115, 46, 14);
		tpProd.add(label_2);
		
		txtProNom = new JTextField();
		txtProNom.setColumns(10);
		txtProNom.setBounds(72, 112, 151, 20);
		tpProd.add(txtProNom);
		
		JLabel label_3 = new JLabel("Descripcion");
		label_3.setHorizontalAlignment(SwingConstants.RIGHT);
		label_3.setBounds(10, 146, 59, 14);
		tpProd.add(label_3);
		
		txtProDesc = new JTextField();
		txtProDesc.setColumns(10);
		txtProDesc.setBounds(73, 143, 151, 20);
		tpProd.add(txtProDesc);
		
		JLabel label_4 = new JLabel("Stock Min");
		label_4.setHorizontalAlignment(SwingConstants.RIGHT);
		label_4.setBounds(10, 177, 59, 14);
		tpProd.add(label_4);
		
		ftxtProStockMin = new JFormattedTextField();
		ftxtProStockMin.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				ctrlProd.controlInputNum(e);
			}
		});
		ftxtProStockMin.setBounds(72, 174, 65, 20);
		tpProd.add(ftxtProStockMin);
		
		ftxtProPrecio = new JFormattedTextField();
		ftxtProPrecio.setBounds(72, 205, 65, 20);
		ftxtProPrecio.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				ctrlProd.controlInputNum(e);
			}
		});
		
		JLabel label_5 = new JLabel("Precio");
		label_5.setHorizontalAlignment(SwingConstants.RIGHT);
		label_5.setBounds(23, 208, 46, 14);
		tpProd.add(label_5);
		tpProd.add(ftxtProPrecio);
		
		/**
		 * boton agregar
		 */
		JButton btnProAgr = new JButton("Agregar");
		btnProAgr.setBounds(282, 11, 89, 23);
		btnProAgr.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				ctrlProd.agregarProducto(txtProCod, txtProNom, txtProDesc, ftxtProStockMin, ftxtProPrecio);
			}
		});
		
		JSeparator sepProd1 = new JSeparator();
		sepProd1.setOrientation(SwingConstants.VERTICAL);
		sepProd1.setForeground(SystemColor.info);
		sepProd1.setBackground(SystemColor.info);
		sepProd1.setBounds(270, 11, 2, 268);
		tpProd.add(sepProd1);
		tpProd.add(btnProAgr);
		
		/**
		 * boton modificar
		 */
		JButton btnProMod = new JButton("Modificar");
		btnProMod.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ctrlProd.modificarProducto(txtProId, txtProCod, txtProNom, txtProDesc, ftxtProStockMin, ftxtProPrecio);
			}
		});
		btnProMod.setBounds(282, 45, 89, 23);
		tpProd.add(btnProMod);
		
		/**
		 * boton eliminar
		 */
		JButton btnProEli = new JButton("Eliminar");
		btnProEli.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ctrlProd.eliminarProducto(txtProId);
			}
		});
		btnProEli.setBounds(282, 79, 89, 23);
		tpProd.add(btnProEli);
		
		JButton btnTpAgregar = new JButton("+");
		btnTpAgregar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ctrlProd.abrirInternalFrame();
			}
		});
		btnTpAgregar.setBounds(228, 41, 32, 23);
		tpProd.add(btnTpAgregar);
		
		JPanel tpLote = new JPanel();
		tabbedPane.addTab("Lote", null, tpLote, null);
		tpLote.setLayout(null);
		
		JPanel tpDeposito = new JPanel();
		tabbedPane.addTab("Deposito", null, tpDeposito, null);
		tpDeposito.setLayout(null);
		
		
	}
	
	public JComboBox<TipoProd> exponerCbxTipoProd() {
		return this.cbxTipoProd;
	}
}
