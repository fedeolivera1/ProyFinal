package gpd.presentacion.formulario;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import org.apache.log4j.Logger;

import gpd.dominio.usuario.UsuarioDsk;
import gpd.presentacion.controlador.CtrlFrmConsulta;

public class FrmConsulta extends JFrame {

	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getLogger(FrmPedido.class);
	private static FrmConsulta instance;
	//
	private CtrlFrmConsulta ctrlInterno;
	private JPanel contentPane;

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
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 800, 600);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		ctrlInterno = new CtrlFrmConsulta(this, usr);
		
		JButton btnReporte = new JButton("Mostrar");
		btnReporte.setBounds(380, 43, 89, 23);
		contentPane.add(btnReporte);
		
		/*****************************************************************************************************************************************************/
		/* ACCIONES CONTROLES */
		/*****************************************************************************************************************************************************/
		
		btnReporte.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ctrlInterno.generarReporte();
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
}
