package gpd.presentacion.formulario;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Calendar;
import java.util.GregorianCalendar;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import org.apache.log4j.Logger;

import com.toedter.calendar.JDateChooser;

import gpd.dominio.usuario.UsuarioDsk;
import gpd.presentacion.controlador.CtrlFrmSinc;

public class FrmSinc extends JFrame {

	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getLogger(FrmPedido.class);
	private static FrmSinc instance;
	private CtrlFrmSinc ctrlSinc;
	//
	private JPanel contentPane;
	private JTextArea txtSincInfo;

	public static FrmSinc getFrmSinc(UsuarioDsk usr) {
		if(instance == null) {
			logger.info("Se genera nueva instancia de FrmSinc > usuario logueado: " + usr.getNomUsu());
			instance = new FrmSinc(usr);
		}
		return instance;
	}

	/**
	 * Create the frame.
	 */
	public FrmSinc(UsuarioDsk usr) {
		ctrlSinc = new CtrlFrmSinc(this, usr);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 800, 600);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton btnSincPers = new JButton("Sincronizar\r\n Personas");
		btnSincPers.setBounds(10, 42, 147, 90);
		contentPane.add(btnSincPers);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(168, 42, 606, 463);
		contentPane.add(scrollPane);
		
		txtSincInfo = new JTextArea();
		txtSincInfo.setEditable(false);
		scrollPane.setViewportView(txtSincInfo);
		txtSincInfo.setFont(new Font("Consolas", Font.PLAIN, 11));
		
		JLabel label = new JLabel("Periodo");
		label.setHorizontalAlignment(SwingConstants.RIGHT);
		label.setBounds(168, 14, 56, 14);
		contentPane.add(label);
		
		JDateChooser dchSincIni = new JDateChooser();
		dchSincIni.setBounds(232, 11, 87, 20);
		GregorianCalendar gc = new GregorianCalendar();
		gc.add(Calendar.DAY_OF_YEAR, -30);
		dchSincIni.setCalendar(gc);
		contentPane.add(dchSincIni);
		
		JDateChooser dchSincFin = new JDateChooser();
		dchSincFin.setBounds(330, 11, 87, 20);
		gc = new GregorianCalendar();
		dchSincFin.setCalendar(gc);
		contentPane.add(dchSincFin);
		
		JButton btnSincronizarProductos = new JButton("Sincronizar\r\n Productos");
		btnSincronizarProductos.setBounds(10, 143, 147, 90);
		contentPane.add(btnSincronizarProductos);
		
		JButton btnSincPedidos = new JButton("Sincronizar\r\n Pedidos");
		btnSincPedidos.setBounds(10, 244, 147, 90);
		contentPane.add(btnSincPedidos);
		
		
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
		//sincronizador personas
		btnSincPers.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				ctrlSinc.sincronizarPersona(dchSincIni, dchSincFin);
			}
		});
		//sincronizador productos
		btnSincronizarProductos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				ctrlSinc.sincronizarProducto(dchSincIni, dchSincFin);
			}
		});
		//sincronizador pedidos
		btnSincPedidos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ctrlSinc.sincronizarPedido(dchSincIni, dchSincFin);
			}
		});
		
	}

	public JTextArea getTxtSincInfo() {
		return txtSincInfo;
	}
	public void setTxtSincInfo(JTextArea txtSincInfo) {
		this.txtSincInfo = txtSincInfo;
	}
}
