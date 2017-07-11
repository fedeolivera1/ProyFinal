package gpd.presentacion.formulario;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.GregorianCalendar;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import org.apache.log4j.Logger;

import gpd.dominio.usuario.UsuarioDsk;
import gpd.presentacion.controlador.CtrlFrmConsulta;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import com.toedter.calendar.JDateChooser;
import javax.swing.JComboBox;
import javax.swing.JTextField;

public class FrmConsulta extends JFrame {

	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getLogger(FrmPedido.class);
	private static FrmConsulta instance;
	//
	private CtrlFrmConsulta ctrlInterno;
	private JPanel contentPane;
	private JTextField txtConsPersona;
	private JComboBox<?> cbxConsFiltro;
	private JDateChooser dchConsIni;
	private JDateChooser dchConsFin;

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
		lblTipoReporte.setBounds(25, 14, 81, 14);
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
		
		JButton button = new JButton("...");
		button.setBounds(461, 70, 32, 22);
		panel.add(button);
		
		/*****************************************************************************************************************************************************/
		/* ACCIONES CONTROLES */
		/*****************************************************************************************************************************************************/
		
		btnReporte.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ctrlInterno.generarReporte(cbxConsFiltro, dchConsIni, dchConsFin);
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
	public void setCbxConsFiltro(JComboBox<?> cbxConsFiltro) {
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

}
