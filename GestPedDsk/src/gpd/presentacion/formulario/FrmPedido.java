package gpd.presentacion.formulario;

import java.awt.Color;
import java.awt.Font;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
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
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JToggleButton;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import org.apache.log4j.Logger;

import com.toedter.calendar.JDateChooser;

import gpd.dominio.pedido.EstadoPedido;
import gpd.dominio.producto.Producto;
import gpd.dominio.producto.TipoProd;
import gpd.dominio.usuario.UsuarioDsk;
import gpd.dominio.util.Origen;
import gpd.presentacion.controlador.CtrlFrmPedido;

public class FrmPedido extends JFrame {

	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getLogger(FrmPedido.class);
	private static FrmPedido instance;
	private CtrlFrmPedido ctrlPed;
	//
	private JPanel contentPane;
	private JDesktopPane desktopPane;
	private JToggleButton tglbtnPedNuevo;
	private JPanel pnlPedBus;
	private JTable jtPedido;
	private JPanel pnlDatosPedido;
	private JComboBox<EstadoPedido> cbxPedidoEstado;
	private JComboBox<Origen> cbxPedidoOrig;
	private JComboBox<TipoProd> cbxPedidoTp;
	private JComboBox<Producto> cbxPedidoProd;
	private JFormattedTextField ftxtPedCant;
	private JDateChooser dchPedidoIni;
	private JDateChooser dchPedidoFin;
	private JTextField txtPersDesc;
	private JTable jtPedidoLin;
	private JFormattedTextField ftxtPedLotePrecio;
	private JFormattedTextField ftxtPedLoteStock;
	private JDateChooser dchPedFecha;
	private JFormattedTextField ftxtPedHora;
	private JTextArea txtPedInfo;

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
		ctrlPed = new CtrlFrmPedido(this, usr);
				 
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
		
		ftxtPedCant = new JFormattedTextField();
		ftxtPedCant.setBounds(72, 73, 52, 20);
		pnlDatosPedido.add(ftxtPedCant);
		
		JLabel lblPrecio = new JLabel("Precio");
		lblPrecio.setHorizontalAlignment(SwingConstants.RIGHT);
		lblPrecio.setBounds(10, 107, 52, 14);
		pnlDatosPedido.add(lblPrecio);
		
		ftxtPedLotePrecio = new JFormattedTextField();
		ftxtPedLotePrecio.setEnabled(false);
		ftxtPedLotePrecio.setBounds(72, 104, 52, 20);
		pnlDatosPedido.add(ftxtPedLotePrecio);
		
		JButton btnPedItemAgr = new JButton("Agregar Item");
		btnPedItemAgr.setBounds(240, 72, 124, 23);
		pnlDatosPedido.add(btnPedItemAgr);
		
		JButton btnPedItemMod = new JButton("Modificar Item");
		btnPedItemMod.setBounds(240, 103, 124, 23);
		pnlDatosPedido.add(btnPedItemMod);
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
		scrollPanePedido.setBounds(10, 139, 764, 133);
		pnlPedido.add(scrollPanePedido);
		
		jtPedido = new JTable();
		scrollPanePedido.setColumnHeaderView(jtPedido);
		scrollPanePedido.setViewportView(jtPedido);
		
		JSeparator separator_1 = new JSeparator();
		separator_1.setBounds(10, 122, 764, 2);
		pnlPedido.add(separator_1);
		separator_1.setForeground(SystemColor.info);
		separator_1.setBackground(SystemColor.info);
		
		txtPersDesc = new JTextField();
		txtPersDesc.setBounds(76, 45, 340, 20);
		pnlPedido.add(txtPersDesc);
		txtPersDesc.setEnabled(false);
		txtPersDesc.setColumns(10);
		
		JLabel lblPersona = new JLabel("Persona");
		lblPersona.setBounds(10, 48, 56, 14);
		pnlPedido.add(lblPersona);
		lblPersona.setHorizontalAlignment(SwingConstants.RIGHT);
		
		JButton btnPedBp = new JButton("...");
		btnPedBp.setBounds(426, 44, 32, 22);
		pnlPedido.add(btnPedBp);
		
		JSeparator separator = new JSeparator();
		separator.setForeground(SystemColor.info);
		separator.setBackground(SystemColor.info);
		separator.setBounds(10, 317, 764, 2);
		pnlPedido.add(separator);
		
		JScrollPane scrollPanePedidoLin = new JScrollPane();
		scrollPanePedidoLin.setBounds(400, 371, 374, 139);
		pnlPedido.add(scrollPanePedidoLin);
		
		jtPedidoLin = new JTable();
		scrollPanePedidoLin.setColumnHeaderView(jtPedidoLin);
		scrollPanePedidoLin.setViewportView(jtPedidoLin);
		
		JButton btnPedGenVenta = new JButton("Generar Venta");
		btnPedGenVenta.setBounds(650, 283, 124, 23);
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
		lblPedidosExistentesFiltrados.setBounds(10, 125, 145, 14);
		pnlPedido.add(lblPedidosExistentesFiltrados);
		
		JButton btnPedActPedido = new JButton("Actualizar Pedido");
		btnPedActPedido.setBounds(516, 521, 124, 23);
		pnlPedido.add(btnPedActPedido);
		
		JButton btnPedLimpiar = new JButton("Limpiar");
		btnPedLimpiar.setBounds(384, 9, 89, 23);
		pnlPedido.add(btnPedLimpiar);
		
		dchPedFecha = new JDateChooser();
		dchPedFecha.setBounds(233, 326, 87, 20);
		pnlPedido.add(dchPedFecha);
		dchPedFecha.setCalendar(new GregorianCalendar());
		
		ftxtPedHora = new JFormattedTextField(ctrlPed.mascNumerica("##:##"));
		ftxtPedHora.setBounds(330, 326, 52, 20);
		pnlPedido.add(ftxtPedHora);
		
		JLabel lblFechahora = new JLabel("Programar");
		lblFechahora.setBounds(153, 328, 70, 14);
		pnlPedido.add(lblFechahora);
		lblFechahora.setHorizontalAlignment(SwingConstants.RIGHT);
		
		JSeparator separator_2 = new JSeparator();
		separator_2.setForeground(SystemColor.info);
		separator_2.setBackground(SystemColor.info);
		separator_2.setBounds(12, 35, 461, 2);
		pnlPedido.add(separator_2);
		
		pnlPedBus = new JPanel();
		pnlPedBus.setBounds(10, 76, 764, 38);
		pnlPedido.add(pnlPedBus);
		pnlPedBus.setLayout(null);
		
		JLabel lblEstado = new JLabel("Estado*");
		lblEstado.setBounds(0, 14, 56, 14);
		pnlPedBus.add(lblEstado);
		lblEstado.setHorizontalAlignment(SwingConstants.RIGHT);
		
		cbxPedidoEstado = new JComboBox<>();
		cbxPedidoEstado.setBounds(66, 11, 122, 20);
		pnlPedBus.add(cbxPedidoEstado);
		
		JLabel lblPeriodo = new JLabel("Periodo*");
		lblPeriodo.setBounds(198, 14, 56, 14);
		pnlPedBus.add(lblPeriodo);
		lblPeriodo.setHorizontalAlignment(SwingConstants.RIGHT);
		
		dchPedidoIni = new JDateChooser();
		dchPedidoIni.setBounds(264, 11, 87, 20);
		pnlPedBus.add(dchPedidoIni);
		dchPedidoIni.setCalendar(new GregorianCalendar());
		
		dchPedidoFin = new JDateChooser();
		dchPedidoFin.setBounds(361, 11, 87, 20);
		pnlPedBus.add(dchPedidoFin);
		dchPedidoFin.setCalendar(new GregorianCalendar());
		
		cbxPedidoOrig = new JComboBox<>();
		cbxPedidoOrig.setBounds(533, 11, 122, 20);
		pnlPedBus.add(cbxPedidoOrig);
		
		JButton btnPedObtener = new JButton("Obtener");
		btnPedObtener.setBounds(665, 10, 89, 23);
		pnlPedBus.add(btnPedObtener);
		
		tglbtnPedNuevo = new JToggleButton("Pedido Nuevo");
		tglbtnPedNuevo.setBounds(10, 9, 121, 23);
		pnlPedido.add(tglbtnPedNuevo);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(481, 1, 303, 73);
		pnlPedido.add(scrollPane);
		
		txtPedInfo = new JTextArea();
		scrollPane.setViewportView(txtPedInfo);
		txtPedInfo.setFont(new Font("Consolas", Font.PLAIN, 10));
		txtPedInfo.setRows(2);
		txtPedInfo.setEditable(false);
		txtPedInfo.setBackground(UIManager.getColor("InternalFrame.borderColor"));
		
		/*****************************************************************************************************************************************************/
		/* ACCIONES CONTROLES */
		/*****************************************************************************************************************************************************/
		
		ctrlPed.cargarCbxPedidoEstado(cbxPedidoEstado);
		ctrlPed.cargarCbxPedidoOrigen(cbxPedidoOrig);
		
		JLabel lblOrigen = new JLabel("Origen");
		lblOrigen.setHorizontalAlignment(SwingConstants.RIGHT);
		lblOrigen.setBounds(467, 14, 56, 14);
		pnlPedBus.add(lblOrigen);
		
		//boton pedido nuevo
		tglbtnPedNuevo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				ctrlPed.controlarPedido(tglbtnPedNuevo);
			}
		});
		//boton obtener pedido
		btnPedObtener.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				ctrlPed.obtenerPedidos(cbxPedidoEstado, txtPersDesc, cbxPedidoOrig, dchPedidoIni, dchPedidoFin);
			}
		});
		
		//mascara hora
		ftxtPedHora.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				ctrlPed.formatoHoraEnTxt(ftxtPedHora);
			}
		});
		//boton buscar personas
		btnPedBp.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				ctrlPed.abrirIfrmPersBuscador(getContentPane(), getDesktopPane(), getTxtPersDesc());
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
		//boton limpiar
		btnPedLimpiar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ctrlPed.clearForm(getContentPane());
			}
		});
		//boton generar pedido
		btnPedGenPedido.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ctrlPed.generarPedido(dchPedFecha, ftxtPedHora);
			}
		});
		//boton actualizar pedido
		btnPedActPedido.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				ctrlPed.actualizarPedido(dchPedFecha, ftxtPedHora, jtPedido);
			}
		});
		//boton generar venta
		btnPedGenVenta.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ctrlPed.generarVenta(jtPedido);
			}
		});
		//boton agregar item a pedido
		btnPedItemAgr.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				ctrlPed.agregarItemAPedido(cbxPedidoProd, ftxtPedCant);
			}
		});
		//boton modificar item a pedido
		btnPedItemMod.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ctrlPed.modificarItemAPedido(jtPedidoLin, cbxPedidoProd, ftxtPedCant);
			}
		});
		//control num en cantidad
		ftxtPedCant.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				ctrlPed.keyTypedNum(e);
			}
		});
		ftxtPedCant.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent arg0) {
				ctrlPed.isNumericInt(ftxtPedCant);
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
	public JDesktopPane getDesktopPane() {
		return desktopPane;
	}
	public void setDesktopPane(JDesktopPane desktopPane) {
		this.desktopPane = desktopPane;
	}
	
	public JToggleButton getTglbtnPedNuevo() {
		return tglbtnPedNuevo;
	}
	public void setTglbtnPedNuevo(JToggleButton tglbtnPedNuevo) {
		this.tglbtnPedNuevo = tglbtnPedNuevo;
	}

	public JDateChooser getDchPedFecha() {
		return dchPedFecha;
	}
	public void setDchPedFecha(JDateChooser dchPedFecha) {
		this.dchPedFecha = dchPedFecha;
	}
	
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
	
	public JComboBox<Origen> getCbxPedidoOrig() {
		return cbxPedidoOrig;
	}
	public void setCbxPedidoOrig(JComboBox<Origen> cbxPedidoOrig) {
		this.cbxPedidoOrig = cbxPedidoOrig;
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

	public JFormattedTextField getTxtPedCant() {
		return ftxtPedCant;
	}
	public void setTxtPedCant(JFormattedTextField txtPedCant) {
		this.ftxtPedCant = txtPedCant;
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
	
	public JFormattedTextField getFtxtPedCant() {
		return ftxtPedCant;
	}
	public void setFtxtPedCant(JFormattedTextField ftxtPedCant) {
		this.ftxtPedCant = ftxtPedCant;
	}
	
	public JTextArea getTxtPedInfo() {
		return txtPedInfo;
	}
	public void setTxtPedInfo(JTextArea txtPedInfo) {
		this.txtPedInfo = txtPedInfo;
	}
	
	public JPanel getPnlPedBus() {
		return pnlPedBus;
	}
	public void setPnlPedBus(JPanel pnlPedBus) {
		this.pnlPedBus = pnlPedBus;
	}
	
	public JFormattedTextField getFtxtPedHora() {
		return ftxtPedHora;
	}
	public void setFtxtPedHora(JFormattedTextField ftxtPedHora) {
		this.ftxtPedHora = ftxtPedHora;
	}

}
