package gpd.presentacion.formulario;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDesktopPane;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import org.apache.log4j.Logger;

import gpd.dominio.persona.PersonaJuridica;
import gpd.dominio.producto.Producto;
import gpd.dominio.producto.TipoProd;
import gpd.dominio.usuario.UsuarioDsk;
import gpd.presentacion.controlador.CtrlFrmMovimiento;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class FrmMovimiento extends JFrame {

	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getLogger(FrmMovimiento.class);
	
	private static FrmMovimiento instance;
	private CtrlFrmMovimiento ctrlMov;
	private JPanel contentPane;
	private JComboBox<TipoProd> cbxCompraTp;
	private JComboBox<Producto> cbxCompraProd;
	private JComboBox<PersonaJuridica> cbxCompraProv;
	private JFormattedTextField ftxtCompraCant;
	private JFormattedTextField ftxtCompraPu;
	private JTable jtCompraItems;
	private JPanel pnlCompraProv;
	private JPanel pnlCompraDatos;
	private JPanel pnlCompraItems;
	private JTable jtComprasPend;


	public static FrmMovimiento getFrmMovimiento(UsuarioDsk usr) {
		if(instance == null) {
			logger.info("Se genera nueva instancia de FrmProducto > usuario logueado: " + usr.getNomUsu());
			instance = new FrmMovimiento(usr);
		}
		return instance;
	}
	
	/**
	 * Create the frame.
	 */
	public FrmMovimiento(UsuarioDsk usr) {
		setTitle("Movimiento");
		ctrlMov = new CtrlFrmMovimiento(this);
		
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 800, 600);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JDesktopPane desktopPane = new JDesktopPane();
		desktopPane.setBounds(0, 0, 0, 0);
		contentPane.add(desktopPane);
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(0, 0, 784, 561);
		contentPane.add(tabbedPane);
		
		JPanel tpCompra = new JPanel();
		tabbedPane.addTab("Compra", null, tpCompra, null);
		tpCompra.setLayout(null);
		
		JButton btnCompraNueva = new JButton("Nueva Compra");
		btnCompraNueva.setBounds(10, 11, 124, 23);
		tpCompra.add(btnCompraNueva);
		
		pnlCompraProv = new JPanel();
		pnlCompraProv.setBorder(new LineBorder(Color.DARK_GRAY));
		pnlCompraProv.setBounds(10, 45, 380, 42);
		tpCompra.add(pnlCompraProv);
		pnlCompraProv.setLayout(null);
		
		JLabel lblProveedor = new JLabel("Proveedor");
		lblProveedor.setBounds(10, 14, 52, 14);
		pnlCompraProv.add(lblProveedor);
		lblProveedor.setHorizontalAlignment(SwingConstants.RIGHT);
		
		cbxCompraProv = new JComboBox<>();
		cbxCompraProv.setBounds(72, 11, 298, 20);
		pnlCompraProv.add(cbxCompraProv);
		
		pnlCompraDatos = new JPanel();
		pnlCompraDatos.setBorder(new LineBorder(Color.DARK_GRAY));
		pnlCompraDatos.setBounds(10, 98, 380, 139);
		tpCompra.add(pnlCompraDatos);
		pnlCompraDatos.setLayout(null);
		
		cbxCompraTp = new JComboBox<TipoProd>();
		cbxCompraTp.setBounds(72, 11, 298, 20);
		pnlCompraDatos.add(cbxCompraTp);
		
		JLabel lblTipoProd = new JLabel("Tipo Prod");
		lblTipoProd.setBounds(16, 14, 46, 14);
		pnlCompraDatos.add(lblTipoProd);
		lblTipoProd.setHorizontalAlignment(SwingConstants.RIGHT);
		
		JLabel lblProducto = new JLabel("Producto");
		lblProducto.setBounds(16, 45, 46, 14);
		pnlCompraDatos.add(lblProducto);
		lblProducto.setHorizontalAlignment(SwingConstants.RIGHT);
		
		cbxCompraProd = new JComboBox<>();
		cbxCompraProd.setBounds(72, 42, 298, 20);
		pnlCompraDatos.add(cbxCompraProd);
		
		JLabel lblUtilidad_1 = new JLabel("Cantidad");
		lblUtilidad_1.setBounds(16, 76, 46, 14);
		pnlCompraDatos.add(lblUtilidad_1);
		lblUtilidad_1.setHorizontalAlignment(SwingConstants.RIGHT);
		
		ftxtCompraCant = new JFormattedTextField();
		ftxtCompraCant.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				ctrlMov.controlInputNum(e);
			}
		});
		ftxtCompraCant.setBounds(72, 73, 65, 20);
		pnlCompraDatos.add(ftxtCompraCant);
		
		JLabel lblPrecioUnit = new JLabel("Precio Unit");
		lblPrecioUnit.setHorizontalAlignment(SwingConstants.RIGHT);
		lblPrecioUnit.setBounds(10, 107, 52, 14);
		pnlCompraDatos.add(lblPrecioUnit);
		
		ftxtCompraPu = new JFormattedTextField();
		ftxtCompraPu.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				ctrlMov.controlInputNum(e);
			}
		});
		ftxtCompraPu.setBounds(72, 104, 65, 20);
		pnlCompraDatos.add(ftxtCompraPu);
		
		JPanel tpVenta = new JPanel();
		tabbedPane.addTab("Venta", null, tpVenta, null);
		tpVenta.setLayout(null);
		
		JButton btnLimpiar = new JButton("Limpiar");
		btnLimpiar.setBounds(645, 367, 124, 23);
		tpCompra.add(btnLimpiar);
		
		JButton btnCompraGenerar = new JButton("Generar Compra");
		btnCompraGenerar.setBounds(645, 299, 124, 23);
		tpCompra.add(btnCompraGenerar);
		
		JScrollPane scrollPaneComprasPend = new JScrollPane();
		scrollPaneComprasPend.setBounds(10, 299, 625, 223);
		tpCompra.add(scrollPaneComprasPend);
		
		jtComprasPend = new JTable();
		scrollPaneComprasPend.setColumnHeaderView(jtComprasPend);
		scrollPaneComprasPend.setViewportView(jtComprasPend);
		
		JLabel lblComprasPendientes = new JLabel("Compras pendientes:");
		lblComprasPendientes.setHorizontalAlignment(SwingConstants.LEFT);
		lblComprasPendientes.setBounds(10, 282, 102, 14);
		tpCompra.add(lblComprasPendientes);
		
		JButton btnCompraAnular = new JButton("Anular Compra");
		btnCompraAnular.setBounds(645, 333, 124, 23);
		tpCompra.add(btnCompraAnular);
		
		pnlCompraItems = new JPanel();
		pnlCompraItems.setBorder(new LineBorder(new Color(0, 0, 0)));
		pnlCompraItems.setBounds(400, 45, 369, 192);
		tpCompra.add(pnlCompraItems);
		pnlCompraItems.setLayout(null);
		
		JScrollPane scrollPaneCompraItems = new JScrollPane();
		scrollPaneCompraItems.setBounds(0, 0, 369, 155);
		pnlCompraItems.add(scrollPaneCompraItems);
		
		jtCompraItems = new JTable();
		scrollPaneCompraItems.setColumnHeaderView(jtCompraItems);
		scrollPaneCompraItems.setViewportView(jtCompraItems);
		
		JButton btnCompraEliItem = new JButton("Eliminar Item");
		btnCompraEliItem.setBounds(235, 158, 124, 23);
		pnlCompraItems.add(btnCompraEliItem);
		
		
		/*****************************************************************************************************************************************************/
		/* ACCIONES CONTROLES */
		/*****************************************************************************************************************************************************/
		
		ctrlMov.setContainerEnabled(pnlCompraProv, false);
		ctrlMov.setContainerEnabled(pnlCompraDatos, false);
		ctrlMov.cargarCbxProveedor(cbxCompraProv);
		ctrlMov.cargarCbxTipoProd(cbxCompraTp);
		
		JButton btnCompraAgrItem = new JButton("Agregar Item");
		btnCompraAgrItem.setBounds(246, 73, 124, 23);
		pnlCompraDatos.add(btnCompraAgrItem);
		
		JButton btnCompraModItem = new JButton("Modificar Item");
		btnCompraModItem.setBounds(246, 103, 124, 23);
		pnlCompraDatos.add(btnCompraModItem);
		btnCompraModItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				ctrlMov.modificarItemCompra(cbxCompraProv, cbxCompraProd, ftxtCompraCant, ftxtCompraPu);
			}
		});
		btnCompraAgrItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				ctrlMov.agregarItemCompra(cbxCompraProv, cbxCompraProd, ftxtCompraCant, ftxtCompraPu);
			}
		});
		btnCompraEliItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				ctrlMov.eliminarItemCompra();
			}
		});
		cbxCompraProv.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent arg0) {
				if(cbxCompraProv.getSelectedIndex() > -1) {
					ctrlMov.setContainerEnabled(pnlCompraDatos, true);
					ctrlMov.setContainerEnabled(pnlCompraProv, false);
					ctrlMov.clearScrollPane(scrollPaneCompraItems);
					ctrlMov.cargarJtComprasPend(cbxCompraProv);
				}
			}
		});
		cbxCompraTp.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				ctrlMov.cargarCbxProd(cbxCompraTp, cbxCompraProd);
			}
		});
		cbxCompraProd.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent arg0) {
				ctrlMov.cargarPrecioProd(cbxCompraProd);
			}
		});
		btnCompraNueva.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ctrlMov.iniciarCompra(getFrmMovimiento(usr));
			}
		});
		btnLimpiar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ctrlMov.limpiarCompra(getFrmMovimiento(usr), true);
			}
		});
		btnCompraGenerar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				ctrlMov.generarCompra();
			}
		});
		btnCompraAnular.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				ctrlMov.anularCompra();
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
	/* GET Y SET */
	/*****************************************************************************************************************************************************/
	
	public CtrlFrmMovimiento getCtrlMov() {
		return ctrlMov;
	}
	public void setCtrlMov(CtrlFrmMovimiento ctrlMov) {
		this.ctrlMov = ctrlMov;
	}

	public JComboBox<TipoProd> getCbxCompraTp() {
		return cbxCompraTp;
	}
	public void setCbxCompraTp(JComboBox<TipoProd> cbxCompraTp) {
		this.cbxCompraTp = cbxCompraTp;
	}

	public JComboBox<Producto> getCbxCompraProd() {
		return cbxCompraProd;
	}
	public void setCbxCompraProd(JComboBox<Producto> cbxCompraProd) {
		this.cbxCompraProd = cbxCompraProd;
	}

	public JComboBox<PersonaJuridica> getCbxCompraProv() {
		return cbxCompraProv;
	}
	public void setCbxCompraProv(JComboBox<PersonaJuridica> cbxCompraProv) {
		this.cbxCompraProv = cbxCompraProv;
	}
	
	public JFormattedTextField getFtxtCompraCant() {
		return ftxtCompraCant;
	}
	public void setFtxtCompraCant(JFormattedTextField ftxtCompraCant) {
		this.ftxtCompraCant = ftxtCompraCant;
	}
	
	public JFormattedTextField getFtxtCompraPu() {
		return ftxtCompraPu;
	}
	public void setFtxtCompraPu(JFormattedTextField ftxtCompraPu) {
		this.ftxtCompraPu = ftxtCompraPu;
	}

	public JPanel getPnlCompraProv() {
		return pnlCompraProv;
	}
	public void setPnlCompraProv(JPanel pnlCompraProv) {
		this.pnlCompraProv = pnlCompraProv;
	}

	public JPanel getPnlCompraDatos() {
		return pnlCompraDatos;
	}
	public void setPnlCompraDatos(JPanel pnlCompraDatos) {
		this.pnlCompraDatos = pnlCompraDatos;
	}
	
	public JTable getJtCompraItems() {
		return jtCompraItems;
	}
	public void setJtCompraItems(JTable jtCompra) {
		this.jtCompraItems = jtCompra;
	}
	
	public JTable getJtComprasPend() {
		return jtComprasPend;
	}
	public void setJtComprasPend(JTable jtComprasPend) {
		this.jtComprasPend = jtComprasPend;
	}
	
	public JPanel getPnlCompraItems() {
		return pnlCompraItems;
	}
	public void setPnlCompraItems(JPanel pnlCompraItems) {
		this.pnlCompraItems = pnlCompraItems;
	}
}
