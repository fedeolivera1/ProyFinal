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
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.InternalFrameEvent;
import javax.swing.event.InternalFrameListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import gpd.dominio.producto.Deposito;
import gpd.presentacion.controlador.CtrlFrmProducto;

public class IfrmDeposito extends JInternalFrame implements InternalFrameListener {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField txtDepNombre;
	private CtrlFrmProducto ctrlInterno;
	private JList<Deposito> jlDeposito;
	private JButton btnDepAgr;
	private JButton btnDepMod;
	private JButton btnDepEli;
	

	/**
	 * Create the frame.
	 */
	public IfrmDeposito(CtrlFrmProducto ctrl) {
		ctrlInterno = ctrl;
        addInternalFrameListener(this);
        ctrl.setiFrmDep(this);
        
		setIconifiable(true);
		setClosable(true);
		setBounds(100, 100, 450, 300);
		setTitle("Tipo Prod");
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
		
		jlDeposito = new JList<>();
		jlDeposito.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		jlDeposito.setBounds(196, 10, 228, 247);
		contentPane.add(jlDeposito);
		DefaultListModel<Deposito> dlm = new DefaultListModel<>();
		jlDeposito.setModel(dlm);
		
		
		btnDepAgr = new JButton("Agregar");
		btnDepAgr.setBounds(97, 39, 89, 23);
		contentPane.add(btnDepAgr);
		
		btnDepMod = new JButton("Modificar");
		btnDepMod.setBounds(97, 73, 89, 23);
		contentPane.add(btnDepMod);
		
		btnDepEli = new JButton("Eliminar");
		btnDepEli.setBounds(97, 107, 89, 23);
		contentPane.add(btnDepEli);
		
		/*****************************************************************************************************************************************************/
		/* ACCIONES CONTROLES */
		/*****************************************************************************************************************************************************/
		
		ctrl.cargarListDeposito(jlDeposito);
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
		jlDeposito.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent arg0) {
				ctrl.cargarControlesDep(txtDepNombre, jlDeposito);
			}
		});
	}

	@Override
    public void internalFrameClosed(InternalFrameEvent e) {
    }

	@Override
	public void internalFrameActivated(InternalFrameEvent e) {
	}

	@Override
	public void internalFrameClosing(InternalFrameEvent e) {
		if(InternalFrameEvent.INTERNAL_FRAME_CLOSING == e.getID()) {
			ctrlInterno.cerrarIFrmDep();
			ctrlInterno.cargarCbxDep(ctrlInterno.getFrm().getCbxDep());
		}
	}

	@Override
	public void internalFrameDeactivated(InternalFrameEvent e) {
	}

	@Override
	public void internalFrameDeiconified(InternalFrameEvent e) {
	}

	@Override
	public void internalFrameIconified(InternalFrameEvent e) {
	}

	@Override
	public void internalFrameOpened(InternalFrameEvent e) {
	}
	
}
