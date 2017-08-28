package gpd.presentacion.formulario;

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
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import org.apache.log4j.Logger;

import com.toedter.calendar.JDateChooser;

import gpd.dominio.usuario.UsuarioDsk;
import gpd.presentacion.controlador.CtrlFrmConsulta;
import gpd.reports.TipoReporte;

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
	private JDateChooser dchConsIni;
	private JDateChooser dchConsFin;
	private JButton btnConsBp;

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
		setTitle("Consulta");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 800, 600);
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
		panel.setBounds(10, 11, 764, 211);
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
		
		JButton btnReporte = new JButton("Mostrar");
		btnReporte.setBounds(362, 102, 89, 23);
		panel.add(btnReporte);
		
		cbxConsFiltro = new JComboBox<>();
		cbxConsFiltro.setBounds(114, 11, 184, 20);
		panel.add(cbxConsFiltro);
		
		JLabel lblTipoReporte = new JLabel("Tipo Reporte *");
		lblTipoReporte.setHorizontalAlignment(SwingConstants.RIGHT);
		lblTipoReporte.setBounds(22, 14, 81, 14);
		panel.add(lblTipoReporte);
		
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
		
		/*****************************************************************************************************************************************************/
		/* ACCIONES CONTROLES */
		/*****************************************************************************************************************************************************/
		
		ctrlInterno.cargarTipoRep(cbxConsFiltro);
		
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
}
