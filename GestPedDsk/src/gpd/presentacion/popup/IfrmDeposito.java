package gpd.presentacion.popup;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.InternalFrameAdapter;
import javax.swing.event.InternalFrameEvent;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import gpd.dominio.producto.Deposito;
import gpd.presentacion.controlador.CtrlFrmProducto;

public class IfrmDeposito extends JInternalFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField txtDepNombre;
	private CtrlFrmProducto ctrlInterno;
	private JScrollPane scrollPaneDep;
	private JList<Deposito> jlDeposito;
	

	/**
	 * Create the frame.
	 */
	public IfrmDeposito(CtrlFrmProducto ctrl) {
		ctrlInterno = ctrl;
        ctrl.setiFrmDep(this);
        
		setIconifiable(true);
		setClosable(true);
		setBounds(100, 100, 450, 300);
		setTitle("Deposito");
		setResizable(false);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Deposito");
		lblNewLabel.setBounds(10, 11, 46, 14);
		contentPane.add(lblNewLabel);
		
		txtDepNombre = new JTextField();
		txtDepNombre.setBounds(66, 8, 120, 20);
		contentPane.add(txtDepNombre);
		txtDepNombre.setColumns(10);
		DefaultListModel<Deposito> dlm = new DefaultListModel<>();
		
		
		JButton btnDepAgr = new JButton("Agregar");
		btnDepAgr.setBounds(97, 39, 89, 23);
		contentPane.add(btnDepAgr);
		
		JButton btnDepMod = new JButton("Modificar");
		btnDepMod.setBounds(97, 73, 89, 23);
		contentPane.add(btnDepMod);
		
		JButton btnDepEli = new JButton("Eliminar");
		btnDepEli.setBounds(97, 107, 89, 23);
		contentPane.add(btnDepEli);
		
		scrollPaneDep = new JScrollPane();
		scrollPaneDep.setBounds(196, 0, 238, 270);
		contentPane.add(scrollPaneDep);
		
		jlDeposito = new JList<>();
		scrollPaneDep.setViewportView(jlDeposito);
		jlDeposito.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		jlDeposito.setModel(dlm);
		
		/*****************************************************************************************************************************************************/
		/* ACCIONES CONTROLES */
		/*****************************************************************************************************************************************************/
		ctrl.cargarListDeposito(jlDeposito);
		
		jlDeposito.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent arg0) {
				ctrl.cargarControlesDep(txtDepNombre, jlDeposito);
			}
		});
		btnDepAgr.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				ctrl.agregarDeposito(txtDepNombre, jlDeposito);
			}
		});
		btnDepMod.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ctrl.modificarDeposito(txtDepNombre, jlDeposito);
			}
		});
		btnDepEli.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ctrl.eliminarDeposito(jlDeposito);
			}
		});
		/***************************************************/
		/* EVENTO CIERRE DEL IFRM */
		/***************************************************/
		addInternalFrameListener(new InternalFrameAdapter() {
			@Override
			public void internalFrameClosing(InternalFrameEvent arg0) {
				ctrlInterno.cerrarIFrmDep();
				ctrlInterno.cargarCbxDep(ctrlInterno.getFrm().getCbxDep());
			}
		});
	}

}
