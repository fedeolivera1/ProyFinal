package gpd.presentacion.formulario;

import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.net.URL;
import java.util.GregorianCalendar;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import org.apache.log4j.Logger;

import com.toedter.calendar.JDateChooser;

import gpd.dominio.pedido.EstadoPedido;
import gpd.dominio.transaccion.EstadoTran;
import gpd.dominio.usuario.UsuarioDsk;
import gpd.presentacion.controlador.CtrlFrmConsulta;
import gpd.presentacion.generic.CnstPresGeneric;
import gpd.reports.TipoReporte;
import javax.swing.JCheckBox;
import javax.swing.JTextArea;
import java.awt.Font;
import javax.swing.JScrollPane;
import java.awt.Color;

public class FrmConsulta extends JFrame {

	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getLogger(FrmPedido.class);
	private static FrmConsulta instance;
	//
	private CtrlFrmConsulta ctrlInterno;
	private JPanel contentPane;
	private JDesktopPane desktopPane;
	private JTextField txtConsPersona;
	private JComboBox<TipoReporte> cbxConsFiltro;
	private JComboBox<EstadoTran> cbxConsEstadoTx;
	private JComboBox<EstadoPedido> cbxConsEstadoPed;
	private JDateChooser dchConsIni;
	private JDateChooser dchConsFin;
	private JCheckBox chkConsTodo;
	private JButton btnConsBp;
	private JTextArea txtProdStockMin;
	private JTextArea txtLotesAVenc;
	private JTextArea txtLotesVenc;
	private JLabel lblProductosConMenos;
	private JLabel lblProductosProximosA;
	private JLabel lblProductosVencidos;

	public static FrmConsulta getFrmConsulta(UsuarioDsk usr) {
		if(instance == null) {
			logger.info("Se genera nueva instancia de FrmConsulta > usuario logueado: " + usr.getNomUsu());
			instance = new FrmConsulta(usr);
		}
		return instance;
	}

	/**
	 * Create the frame.
	 */
	public FrmConsulta(UsuarioDsk usr) {
		setTitle("Consulta - YAMETL");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 800, 600);
		
		if(ClassLoader.getSystemResource(CnstPresGeneric.ICON_BG) != null) {
			URL url = ClassLoader.getSystemResource(CnstPresGeneric.ICON_BG);
			ImageIcon icon = new ImageIcon(url);
			setIconImage(icon.getImage());
		}
		
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		ctrlInterno = new CtrlFrmConsulta(this, usr);
		
		desktopPane = new JDesktopPane();
		desktopPane.setBackground(SystemColor.control);
		desktopPane.setBounds(0, 0, 0, 0);
		desktopPane.setLayout(null);
		contentPane.add(desktopPane);
		//agrego desktopPane a controlador de busqueda
		ctrlInterno.setDeskPane(desktopPane);
		
		JPanel panel = new JPanel();
		panel.setBounds(10, 11, 764, 146);
		contentPane.add(panel);
		panel.setLayout(null);
		
		JLabel label = new JLabel("Periodo*");
		label.setBounds(35, 45, 68, 14);
		panel.add(label);
		label.setHorizontalAlignment(SwingConstants.RIGHT);
		
		dchConsIni = new JDateChooser();
		dchConsIni.setBounds(114, 42, 87, 20);
		panel.add(dchConsIni);
		dchConsIni.setCalendar(new GregorianCalendar());
		
		dchConsFin = new JDateChooser();
		dchConsFin.setBounds(211, 42, 87, 20);
		panel.add(dchConsFin);
		dchConsFin.setCalendar(new GregorianCalendar());

		chkConsTodo = new JCheckBox("Todo");
		chkConsTodo.setVisible(false);
		chkConsTodo.setBounds(304, 41, 97, 23);
		panel.add(chkConsTodo);
		
		JButton btnReporte = new JButton("Mostrar");
		btnReporte.setBounds(503, 112, 89, 23);
		panel.add(btnReporte);
		
		cbxConsFiltro = new JComboBox<>();
		cbxConsFiltro.setBounds(114, 11, 184, 20);
		panel.add(cbxConsFiltro);
		
		JLabel lblTipoReporte = new JLabel("Tipo Reporte *");
		lblTipoReporte.setHorizontalAlignment(SwingConstants.RIGHT);
		lblTipoReporte.setBounds(22, 14, 81, 14);
		panel.add(lblTipoReporte);

		cbxConsEstadoTx = new JComboBox<>();
		cbxConsEstadoTx.setVisible(false);
		cbxConsEstadoTx.setBounds(304, 11, 147, 20);
		panel.add(cbxConsEstadoTx);
		cbxConsEstadoPed = new JComboBox<>();
		cbxConsEstadoPed.setVisible(false);
		cbxConsEstadoPed.setBounds(304, 11, 147, 20);
		panel.add(cbxConsEstadoPed);
		
		JLabel label_1 = new JLabel("Persona");
		label_1.setHorizontalAlignment(SwingConstants.RIGHT);
		label_1.setBounds(45, 74, 56, 14);
		panel.add(label_1);
		
		txtConsPersona = new JTextField();
		txtConsPersona.setEnabled(false);
		txtConsPersona.setColumns(10);
		txtConsPersona.setBounds(111, 71, 340, 20);
		panel.add(txtConsPersona);
		
		btnConsBp = new JButton("...");
		btnConsBp.setBounds(461, 70, 32, 22);
		panel.add(btnConsBp);
		
		JButton btnConsLimpiar = new JButton("Limpiar");
		btnConsLimpiar.setBounds(503, 70, 89, 23);
		panel.add(btnConsLimpiar);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBounds(10, 188, 764, 362);
		contentPane.add(panel_1);
		panel_1.setLayout(null);
		
		lblProductosConMenos = new JLabel("Productos con menos del stock minimo");
		lblProductosConMenos.setBounds(10, 16, 219, 14);
		panel_1.add(lblProductosConMenos);
		
		lblProductosProximosA = new JLabel("Lotes proximos a venc.");
		lblProductosProximosA.setBounds(10, 131, 219, 14);
		panel_1.add(lblProductosProximosA);
		
		lblProductosVencidos = new JLabel("Lotes vencidos");
		lblProductosVencidos.setBounds(10, 245, 219, 14);
		panel_1.add(lblProductosVencidos);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 31, 744, 92);
		panel_1.add(scrollPane);
		
		txtProdStockMin = new JTextArea();
		txtProdStockMin.setForeground(Color.RED);
		txtProdStockMin.setEditable(false);
		scrollPane.setViewportView(txtProdStockMin);
		txtProdStockMin.setFont(new Font("Monospaced", Font.PLAIN, 11));
		
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(10, 146, 744, 92);
		panel_1.add(scrollPane_1);
		
		txtLotesAVenc = new JTextArea();
		txtLotesAVenc.setForeground(new Color(210, 105, 30));
		txtLotesAVenc.setEditable(false);
		txtLotesAVenc.setFont(new Font("Monospaced", Font.PLAIN, 11));
		scrollPane_1.setViewportView(txtLotesAVenc);
		
		JScrollPane scrollPane_2 = new JScrollPane();
		scrollPane_2.setBounds(10, 260, 744, 92);
		panel_1.add(scrollPane_2);
		
		txtLotesVenc = new JTextArea();
		txtLotesVenc.setForeground(Color.RED);
		txtLotesVenc.setEditable(false);
		txtLotesVenc.setFont(new Font("Monospaced", Font.PLAIN, 11));
		scrollPane_2.setViewportView(txtLotesVenc);
		
		/*****************************************************************************************************************************************************/
		/* ACCIONES CONTROLES */
		/*****************************************************************************************************************************************************/
		
		ctrlInterno.cargarTipoRep(cbxConsFiltro);
		ctrlInterno.cargarTipoTx(cbxConsEstadoTx);
		ctrlInterno.cargarEstadoPedido(cbxConsEstadoPed);
		
		//warnings
		ctrlInterno.cargarProdStockMin(txtProdStockMin);
		ctrlInterno.cargarLotesPorVenc(txtLotesAVenc, txtLotesVenc);
		
		JLabel lblAdvertencias = new JLabel("Advertencias");
		lblAdvertencias.setBounds(20, 174, 86, 14);
		contentPane.add(lblAdvertencias);
		lblAdvertencias.setForeground(Color.RED);
//		ctrlInterno.cargarProdProxAVenc();
//		ctrlInterno.cargarProdVencidos();
		
		btnConsLimpiar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				ctrlInterno.limpiar();
			}
		});
		cbxConsFiltro.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent arg0) {
				ctrlInterno.manejarControles(cbxConsFiltro);
			}
		});
		//boton buscar personas
		btnConsBp.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ctrlInterno.abrirIfrmPersBuscador(getContentPane(), getDesktopPane(), getTxtConsPersona());
			}
		});
		btnReporte.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ctrlInterno.controlComponenteReporte(cbxConsFiltro);
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
	
	public JTextField getTxtConsPersona() {
		return txtConsPersona;
	}
	public void setTxtConsPersona(JTextField txtConsPersona) {
		this.txtConsPersona = txtConsPersona;
	}

	public JComboBox<?> getCbxConsFiltro() {
		return cbxConsFiltro;
	}
	public void setCbxConsFiltro(JComboBox<TipoReporte> cbxConsFiltro) {
		this.cbxConsFiltro = cbxConsFiltro;
	}

	public JDateChooser getDchConsIni() {
		return dchConsIni;
	}
	public void setDchConsIni(JDateChooser dchConsIni) {
		this.dchConsIni = dchConsIni;
	}

	public JDateChooser getDchConsFin() {
		return dchConsFin;
	}
	public void setDchConsFin(JDateChooser dchConsFin) {
		this.dchConsFin = dchConsFin;
	}
	
	public JCheckBox getChkConsTodo() {
		return chkConsTodo;
	}
	public void setChkConsTodo(JCheckBox chkConsTodo) {
		this.chkConsTodo = chkConsTodo;
	}
	
	public JDesktopPane getDesktopPane() {
		return desktopPane;
	}
	public void setDesktopPane(JDesktopPane desktopPane) {
		this.desktopPane = desktopPane;
	}

	public JButton getBtnConsBp() {
		return btnConsBp;
	}
	public void setBtnConsBp(JButton btnConsBp) {
		this.btnConsBp = btnConsBp;
	}

	public JComboBox<EstadoTran> getCbxConsEstadoTx() {
		return cbxConsEstadoTx;
	}
	public void setCbxConsEstadoTx(JComboBox<EstadoTran> cbxConsEstadoTx) {
		this.cbxConsEstadoTx = cbxConsEstadoTx;
	}

	public JComboBox<EstadoPedido> getCbxConsEstadoPed() {
		return cbxConsEstadoPed;
	}
	public void setCbxConsEstadoPed(JComboBox<EstadoPedido> cbxConsEstadoPed) {
		this.cbxConsEstadoPed = cbxConsEstadoPed;
	}

	public JTextArea getTxtProdStockMin() {
		return txtProdStockMin;
	}
	public void setTxtProdStockMin(JTextArea txtProdStockMin) {
		this.txtProdStockMin = txtProdStockMin;
	}

	public JTextArea getTxtLotesAVenc() {
		return txtLotesAVenc;
	}
	public void setTxtLotesAVenc(JTextArea txtLotesAVenc) {
		this.txtLotesAVenc = txtLotesAVenc;
	}

	public JTextArea getTxtLotesVenc() {
		return txtLotesVenc;
	}
	public void setTxtLotesVenc(JTextArea txtLotesVenc) {
		this.txtLotesVenc = txtLotesVenc;
	}
}
