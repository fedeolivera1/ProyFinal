package gpd.presentacion.formulario;

import java.awt.Color;
import java.awt.Font;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.GregorianCalendar;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDesktopPane;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import org.apache.log4j.Logger;

import com.toedter.calendar.JDateChooser;

import gpd.dominio.pedido.EstadoPedido;
import gpd.dominio.producto.Producto;
import gpd.dominio.producto.TipoProd;
import gpd.dominio.usuario.UsuarioDsk;
import gpd.presentacion.controlador.CtrlFrmPedido;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class FrmPedido extends JFrame {

	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getLogger(FrmPedido.class);
	private static FrmPedido instance;
	private CtrlFrmPedido ctrlPed;
	//
	private JPanel contentPane;
	private JTable jtPedido;
	private JPanel pnlDatosPedido;
	private JComboBox<EstadoPedido> cbxPedidoEstado;
	private JComboBox<TipoProd> cbxPedidoTp;
	private JComboBox<Producto> cbxPedidoProd;
	private JFormattedTextField txtPedidoCant;
	private JDateChooser dchPedidoIni;
	private JDateChooser dchPedidoFin;
	private JTextField txtPersDesc;
	private JDesktopPane desktopPane;
	private JTable jtPedidoLin;
	private JTextField txtPedInfo;
	private JFormattedTextField ftxtPedLotePrecio;
	private JFormattedTextField ftxtPedLoteStock;

	public static FrmPedido getFrmPedido(UsuarioDsk usr) {
		if(instance == null) {
			logger.info("Se genera nueva instancia de FrmPedido > usuario logueado: " + usr.getNomUsu());
			instance = new FrmPedido(usr);
		}
		return instance;
	}

	/**
	 * Create the frame.
	 */
	public FrmPedido(UsuarioDsk usr) {
		ctrlPed = new CtrlFrmPedido(this);
				 
		setTitle("Pedido");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 800, 600);
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
		ctrlPed.setDeskPane(desktopPane);
	
		JPanel pnlPedido = new JPanel();
		pnlPedido.setBounds(0, 0, 784, 561);
		contentPane.add(pnlPedido);
		pnlPedido.setLayout(null);
		
		pnlDatosPedido = new JPanel();
		pnlDatosPedido.setBounds(10, 371, 374, 139);
		pnlPedido.add(pnlDatosPedido);
		pnlDatosPedido.setLayout(null);
		pnlDatosPedido.setBorder(new LineBorder(Color.DARK_GRAY));
		
		cbxPedidoTp = new JComboBox<TipoProd>();
		cbxPedidoTp.setBounds(72, 11, 292, 20);
		pnlDatosPedido.add(cbxPedidoTp);
		
		JLabel label = new JLabel("Tipo Prod");
		label.setHorizontalAlignment(SwingConstants.RIGHT);
		label.setBounds(16, 14, 46, 14);
		pnlDatosPedido.add(label);
		
		JLabel label_1 = new JLabel("Producto");
		label_1.setHorizontalAlignment(SwingConstants.RIGHT);
		label_1.setBounds(16, 45, 46, 14);
		pnlDatosPedido.add(label_1);
		
		cbxPedidoProd = new JComboBox<Producto>();
		cbxPedidoProd.setBounds(72, 42, 292, 20);
		pnlDatosPedido.add(cbxPedidoProd);
		
		JLabel label_2 = new JLabel("Cantidad");
		label_2.setHorizontalAlignment(SwingConstants.RIGHT);
		label_2.setBounds(16, 76, 46, 14);
		pnlDatosPedido.add(label_2);
		
		txtPedidoCant = new JFormattedTextField();
		txtPedidoCant.setBounds(72, 73, 52, 20);
		pnlDatosPedido.add(txtPedidoCant);
		
		JLabel lblPrecio = new JLabel("Precio");
		lblPrecio.setHorizontalAlignment(SwingConstants.RIGHT);
		lblPrecio.setBounds(10, 107, 52, 14);
		pnlDatosPedido.add(lblPrecio);
		
		ftxtPedLotePrecio = new JFormattedTextField();
		ftxtPedLotePrecio.setEnabled(false);
		ftxtPedLotePrecio.setBounds(72, 104, 52, 20);
		pnlDatosPedido.add(ftxtPedLotePrecio);
		
		JButton button = new JButton("Agregar Item");
		button.setBounds(240, 72, 124, 23);
		pnlDatosPedido.add(button);
		
		JButton button_1 = new JButton("Modificar Item");
		button_1.setBounds(240, 103, 124, 23);
		pnlDatosPedido.add(button_1);
		ctrlPed.cargarCbxTipoProd(cbxPedidoTp);
		
		JLabel lblDe = new JLabel("de");
		lblDe.setHorizontalAlignment(SwingConstants.RIGHT);
		lblDe.setBounds(140, 76, 13, 14);
		pnlDatosPedido.add(lblDe);
		
		ftxtPedLoteStock = new JFormattedTextField();
		ftxtPedLoteStock.setEditable(false);
		ftxtPedLoteStock.setBounds(169, 73, 52, 20);
		pnlDatosPedido.add(ftxtPedLoteStock);
		
		JScrollPane scrollPanePedido = new JScrollPane();
		scrollPanePedido.setBounds(10, 117, 764, 133);
		pnlPedido.add(scrollPanePedido);
		
		jtPedido = new JTable();
		scrollPanePedido.setColumnHeaderView(jtPedido);
		scrollPanePedido.setViewportView(jtPedido);
		
		JLabel lblEstado = new JLabel("Estado*");
		lblEstado.setBounds(10, 19, 56, 14);
		pnlPedido.add(lblEstado);
		lblEstado.setHorizontalAlignment(SwingConstants.RIGHT);
		
		cbxPedidoEstado = new JComboBox<>();
		cbxPedidoEstado.setBounds(76, 16, 122, 20);
		pnlPedido.add(cbxPedidoEstado);
		
		ctrlPed.cargarCbxPedidoEstado(cbxPedidoEstado);
		
		JLabel lblPeriodo = new JLabel("Periodo*");
		lblPeriodo.setBounds(208, 19, 56, 14);
		pnlPedido.add(lblPeriodo);
		lblPeriodo.setHorizontalAlignment(SwingConstants.RIGHT);
		
		dchPedidoIni = new JDateChooser();
		dchPedidoIni.setBounds(274, 16, 87, 20);
		pnlPedido.add(dchPedidoIni);
		dchPedidoIni.setCalendar(new GregorianCalendar());
		
		dchPedidoFin = new JDateChooser();
		dchPedidoFin.setBounds(371, 16, 87, 20);
		pnlPedido.add(dchPedidoFin);
		dchPedidoFin.setCalendar(new GregorianCalendar());
		
		JButton btnPedObtener = new JButton("Obtener");
		btnPedObtener.setBounds(513, 57, 89, 23);
		pnlPedido.add(btnPedObtener);
		
		JSeparator separator_1 = new JSeparator();
		separator_1.setBounds(10, 91, 764, 2);
		pnlPedido.add(separator_1);
		separator_1.setForeground(SystemColor.info);
		separator_1.setBackground(SystemColor.info);
		
		txtPersDesc = new JTextField();
		txtPersDesc.setBounds(76, 58, 382, 20);
		pnlPedido.add(txtPersDesc);
		txtPersDesc.setEnabled(false);
		txtPersDesc.setColumns(10);
		
		JLabel lblPersona = new JLabel("Persona");
		lblPersona.setBounds(10, 61, 56, 14);
		pnlPedido.add(lblPersona);
		lblPersona.setHorizontalAlignment(SwingConstants.RIGHT);
		
		JButton btnPedBp = new JButton("...");
		btnPedBp.setBounds(471, 57, 32, 22);
		pnlPedido.add(btnPedBp);
		
		JSeparator separator = new JSeparator();
		separator.setForeground(SystemColor.info);
		separator.setBackground(SystemColor.info);
		separator.setBounds(10, 295, 764, 2);
		pnlPedido.add(separator);
		
		JScrollPane scrollPanePedidoLin = new JScrollPane();
		scrollPanePedidoLin.setBounds(400, 371, 374, 139);
		pnlPedido.add(scrollPanePedidoLin);
		
		jtPedidoLin = new JTable();
		scrollPanePedidoLin.setColumnHeaderView(jtPedidoLin);
		scrollPanePedidoLin.setViewportView(jtPedidoLin);
		
		JButton btnPedGenVenta = new JButton("Generar Venta");
		btnPedGenVenta.setBounds(650, 261, 124, 23);
		pnlPedido.add(btnPedGenVenta);
		
		JButton btnPedGenPedido = new JButton("Generar Pedido");
		btnPedGenPedido.setBounds(650, 521, 124, 23);
		pnlPedido.add(btnPedGenPedido);
		
		JLabel lblItemsParaEl = new JLabel("Items para el pedido");
		lblItemsParaEl.setHorizontalAlignment(SwingConstants.LEFT);
		lblItemsParaEl.setBounds(10, 357, 124, 14);
		pnlPedido.add(lblItemsParaEl);
		
		JLabel lblItemsActualesEn = new JLabel("Items actuales en el pedido");
		lblItemsActualesEn.setHorizontalAlignment(SwingConstants.LEFT);
		lblItemsActualesEn.setBounds(400, 357, 145, 14);
		pnlPedido.add(lblItemsActualesEn);
		
		JLabel lblPedidosExistentesFiltrados = new JLabel("Pedidos existentes filtrados");
		lblPedidosExistentesFiltrados.setHorizontalAlignment(SwingConstants.LEFT);
		lblPedidosExistentesFiltrados.setBounds(10, 104, 145, 14);
		pnlPedido.add(lblPedidosExistentesFiltrados);
		
		JButton btnPedNuevo = new JButton("Nuevo Pedido");
		btnPedNuevo.setBounds(10, 305, 124, 23);
		pnlPedido.add(btnPedNuevo);
		
		JButton btnPedActPedido = new JButton("Actualizar Pedido");
		btnPedActPedido.setBounds(516, 521, 124, 23);
		pnlPedido.add(btnPedActPedido);
		
		txtPedInfo = new JTextField();
		txtPedInfo.setBorder(new LineBorder(new Color(0, 0, 0)));
		txtPedInfo.setFont(new Font("Tahoma", Font.PLAIN, 12));
		txtPedInfo.setEditable(false);
		txtPedInfo.setBounds(468, 9, 306, 34);
		pnlPedido.add(txtPedInfo);
		txtPedInfo.setColumns(10);
		
		JButton btnPedLimpiar = new JButton("Limpiar");
		btnPedLimpiar.setBounds(612, 57, 89, 23);
		pnlPedido.add(btnPedLimpiar);
		
		JDateChooser dchPedFecha = new JDateChooser();
		dchPedFecha.setBounds(224, 308, 87, 20);
		pnlPedido.add(dchPedFecha);
		
		JFormattedTextField ftxtPedHora = new JFormattedTextField();
		ftxtPedHora.setBounds(321, 308, 52, 20);
		pnlPedido.add(ftxtPedHora);
		
		JLabel lblFechahora = new JLabel("Programar");
		lblFechahora.setBounds(144, 308, 70, 14);
		pnlPedido.add(lblFechahora);
		lblFechahora.setHorizontalAlignment(SwingConstants.RIGHT);
		
		/*****************************************************************************************************************************************************/
		/* ACCIONES CONTROLES */
		/*****************************************************************************************************************************************************/
		
		//boton buscar personas
		btnPedBp.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				ctrlPed.abrirBuscadorPers();
			}
		});
		//combo tipo prod
		cbxPedidoTp.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent arg0) {
				ctrlPed.cargarCbxProd(cbxPedidoTp, cbxPedidoProd);
			}
		});
		//combo producto
		cbxPedidoProd.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent arg0) {
				ctrlPed.obtenerDatosLotePorProducto(cbxPedidoProd);
			}
		});
		//boton obtener pedido
		btnPedObtener.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				ctrlPed.obtenerPedidos(cbxPedidoEstado, txtPersDesc, dchPedidoIni, dchPedidoFin);
			}
		});
		//boton limpiar
		btnPedLimpiar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ctrlPed.clearForm(getContentPane());
			}
		});
		//boton nuevo pedido
		btnPedNuevo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ctrlPed.nuevoPedido();
			}
		});
		//boton generar pedido
		btnPedGenPedido.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		//boton generar venta
		btnPedGenVenta.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		//control num en cantidad
		txtPedidoCant.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				ctrlPed.controlInputNum(e);
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
	public JTextField getTxtPersDesc() {
		return txtPersDesc;
	}
	public void setTxtPersDesc(JTextField txtPersDesc) {
		this.txtPersDesc = txtPersDesc;
	}
	
	public JComboBox<EstadoPedido> getCbxPedidoEstado() {
		return cbxPedidoEstado;
	}
	public void setCbxPedidoEstado(JComboBox<EstadoPedido> cbxPedidoEstado) {
		this.cbxPedidoEstado = cbxPedidoEstado;
	}

	public JTable getJtPedido() {
		return jtPedido;
	}
	public void setJtPedido(JTable jtPedido) {
		this.jtPedido = jtPedido;
	}

	public JPanel getPnlDatosPedido() {
		return pnlDatosPedido;
	}
	public void setPnlDatosPedido(JPanel pnlDatosPedido) {
		this.pnlDatosPedido = pnlDatosPedido;
	}

	public JComboBox<TipoProd> getCbxPedidoTp() {
		return cbxPedidoTp;
	}
	public void setCbxPedidoTp(JComboBox<TipoProd> cbxPedidoTp) {
		this.cbxPedidoTp = cbxPedidoTp;
	}

	public JComboBox<Producto> getCbxPedidoProd() {
		return cbxPedidoProd;
	}
	public void setCbxPedidoProd(JComboBox<Producto> cbxPedidoProd) {
		this.cbxPedidoProd = cbxPedidoProd;
	}

	public JFormattedTextField getTxtPedidoCant() {
		return txtPedidoCant;
	}
	public void setTxtPedidoCant(JFormattedTextField txtPedidoCant) {
		this.txtPedidoCant = txtPedidoCant;
	}

	public JDateChooser getDchPedidoIni() {
		return dchPedidoIni;
	}
	public void setDchPedidoIni(JDateChooser dchPedidoIni) {
		this.dchPedidoIni = dchPedidoIni;
	}

	public JDateChooser getDchPedidoFin() {
		return dchPedidoFin;
	}
	public void setDchPedidoFin(JDateChooser dchPedidoFin) {
		this.dchPedidoFin = dchPedidoFin;
	}
	
	public JTable getJtPedidoLin() {
		return jtPedidoLin;
	}
	public void setJtPedidoLin(JTable jtPedidoLin) {
		this.jtPedidoLin = jtPedidoLin;
	}

	public JTextField getTxtPedInfo() {
		return txtPedInfo;
	}
	public void setTxtPedInfo(JTextField txtPedInfo) {
		this.txtPedInfo = txtPedInfo;
	}
	
	public JFormattedTextField getFtxtPedLotePrecio() {
		return ftxtPedLotePrecio;
	}
	public void setFtxtPedLotePrecio(JFormattedTextField ftxtPedLotePrecio) {
		this.ftxtPedLotePrecio = ftxtPedLotePrecio;
	}

	public JFormattedTextField getFtxtPedLoteStock() {
		return ftxtPedLoteStock;
	}
	public void setFtxtPedLoteStock(JFormattedTextField ftxtPedLoteStock) {
		this.ftxtPedLoteStock = ftxtPedLoteStock;
	}
}
