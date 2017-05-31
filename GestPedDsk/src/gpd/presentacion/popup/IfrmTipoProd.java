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

import gpd.dominio.producto.TipoProd;
import gpd.presentacion.controlador.CtrlFrmProducto;

public class IfrmTipoProd extends JInternalFrame implements InternalFrameListener {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField txtTpDesc;
	private CtrlFrmProducto ctrlInterno;
	
	/**
	 * Launch the application.
	 */
//	public static void main(String[] args) {
//		EventQueue.invokeLater(new Runnable() {
//			public void run() {
//				try {
//					IntFrmTipoProd frame = new IntFrmTipoProd();
//					frame.setVisible(true);
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//			}
//		});
//	}

	/**
	 * Create the frame.
	 */
	public IfrmTipoProd(CtrlFrmProducto ctrl) {
		ctrlInterno = ctrl;
        addInternalFrameListener(this);
        ctrl.setiFrm(this);
        
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
		
		JLabel lblNewLabel = new JLabel("Tipo Prod");
		lblNewLabel.setBounds(10, 11, 46, 14);
		contentPane.add(lblNewLabel);
		
		txtTpDesc = new JTextField();
		txtTpDesc.setBounds(66, 8, 120, 20);
		contentPane.add(txtTpDesc);
		txtTpDesc.setColumns(10);
		
		JList<TipoProd> jlTipoProd = new JList<>();
		jlTipoProd.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		jlTipoProd.setBounds(196, 10, 228, 247);
		contentPane.add(jlTipoProd);
		DefaultListModel<TipoProd> dlm = new DefaultListModel<>();
		ctrl.cargarListTipoProd(jlTipoProd);
		jlTipoProd.setModel(dlm);
		ctrl.cargarListTipoProd(jlTipoProd);
		
		jlTipoProd.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent arg0) {
				ctrl.cargarControlesTipoProd(txtTpDesc, jlTipoProd);
			}
		});
		
		JButton btnTpAgr = new JButton("Agregar");
		btnTpAgr.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				ctrl.agregarTipoProd(txtTpDesc, jlTipoProd);
			}
		});
		btnTpAgr.setBounds(97, 39, 89, 23);
		contentPane.add(btnTpAgr);
		
		JButton btnTpMod = new JButton("Modificar");
		btnTpMod.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ctrl.modificarTipoProd(txtTpDesc, jlTipoProd);
			}
		});
		btnTpMod.setBounds(97, 73, 89, 23);
		contentPane.add(btnTpMod);
		
		JButton btnTpEli = new JButton("Eliminar");
		btnTpEli.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ctrl.eliminarTipoProd(jlTipoProd);
			}
		});
		btnTpEli.setBounds(97, 107, 89, 23);
		contentPane.add(btnTpEli);
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
			ctrlInterno.cerrarInternalFrame();
			ctrlInterno.cargarCbxTipoProd(ctrlInterno.getFrm().exponerCbxTipoProd());
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
