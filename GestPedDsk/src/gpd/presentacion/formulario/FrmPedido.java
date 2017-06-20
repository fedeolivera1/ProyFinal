package gpd.presentacion.formulario;

import java.awt.Color;
import java.awt.SystemColor;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Calendar;
import java.util.GregorianCalendar;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTable;
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
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;

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
		
		pnlDatosPedido = new JPanel();
		pnlDatosPedido.setLayout(null);
		pnlDatosPedido.setBorder(new LineBorder(Color.DARK_GRAY));
		pnlDatosPedido.setBounds(10, 308, 380, 139);
		contentPane.add(pnlDatosPedido);
		
		cbxPedidoTp = new JComboBox<TipoProd>();
		cbxPedidoTp.setBounds(72, 11, 298, 20);
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
		cbxPedidoProd.setBounds(72, 42, 298, 20);
		pnlDatosPedido.add(cbxPedidoProd);
		
		JLabel label_2 = new JLabel("Cantidad");
		label_2.setHorizontalAlignment(SwingConstants.RIGHT);
		label_2.setBounds(16, 76, 46, 14);
		pnlDatosPedido.add(label_2);
		
		txtPedidoCant = new JFormattedTextField();
		txtPedidoCant.setBounds(72, 73, 65, 20);
		pnlDatosPedido.add(txtPedidoCant);
		
		JLabel lblPrecio = new JLabel("Precio");
		lblPrecio.setHorizontalAlignment(SwingConstants.RIGHT);
		lblPrecio.setBounds(10, 107, 52, 14);
		pnlDatosPedido.add(lblPrecio);
		
		JFormattedTextField formattedTextField_1 = new JFormattedTextField();
		formattedTextField_1.setEnabled(false);
		formattedTextField_1.setBounds(72, 104, 65, 20);
		pnlDatosPedido.add(formattedTextField_1);
		
		JButton button = new JButton("Agregar Item");
		button.setBounds(246, 73, 124, 23);
		pnlDatosPedido.add(button);
		
		JButton button_1 = new JButton("Modificar Item");
		button_1.setBounds(246, 103, 124, 23);
		pnlDatosPedido.add(button_1);
		
		JScrollPane scrollPanePedido = new JScrollPane();
		scrollPanePedido.setBounds(10, 53, 764, 244);
		contentPane.add(scrollPanePedido);
		
		jtPedido = new JTable();
		scrollPanePedido.setColumnHeaderView(jtPedido);
		
		JLabel label_3 = new JLabel("Estado");
		label_3.setHorizontalAlignment(SwingConstants.RIGHT);
		label_3.setBounds(10, 15, 56, 14);
		contentPane.add(label_3);
		
		cbxPedidoEstado = new JComboBox<>();
		cbxPedidoEstado.setBounds(76, 12, 122, 20);
		contentPane.add(cbxPedidoEstado);
		
		JLabel label_4 = new JLabel("Periodo");
		label_4.setHorizontalAlignment(SwingConstants.RIGHT);
		label_4.setBounds(208, 15, 56, 14);
		contentPane.add(label_4);
		
		dchPedidoIni = new JDateChooser();
		dchPedidoIni.setBounds(272, 12, 87, 20);
		GregorianCalendar gc = new GregorianCalendar();
		gc.add(Calendar.DAY_OF_YEAR, -10);
		dchPedidoIni.setCalendar(gc);
		contentPane.add(dchPedidoIni);
		
		dchPedidoFin = new JDateChooser();
		dchPedidoFin.setBounds(370, 12, 87, 20);
		dchPedidoFin.setCalendar(new GregorianCalendar());
		contentPane.add(dchPedidoFin);
		
		JButton button_2 = new JButton("Obtener");
		button_2.setBounds(467, 11, 89, 23);
		contentPane.add(button_2);
		
		JSeparator separator = new JSeparator();
		separator.setForeground(SystemColor.info);
		separator.setBackground(SystemColor.info);
		separator.setBounds(10, 40, 764, 2);
		contentPane.add(separator);
		
		
		/***************************************************/
		/* EVENTO CIERRE DEL FORM */
		/***************************************************/
		this.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosed(WindowEvent arg0) {
				instance = null;
			}
		});
	
		/*****************************************************************************************************************************************************/
		/* ACCIONES CONTROLES */
		/*****************************************************************************************************************************************************/
	
		ctrlPed.cargarCbxPedidoEstado(cbxPedidoEstado);
		ctrlPed.cargarCbxTipoProd(cbxPedidoTp);
		
		
		cbxPedidoTp.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent arg0) {
				ctrlPed.cargarCbxProd(cbxPedidoTp, cbxPedidoProd);
			}
		});
	}
	
	/*****************************************************************************************************************************************************/
	/* GET Y SET */
	/*****************************************************************************************************************************************************/
	
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

}
