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
import javax.swing.JScrollPane;

public class IfrmTipoProd extends JInternalFrame implements InternalFrameListener {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField txtTpDesc;
	private CtrlFrmProducto ctrlInterno;
	private JScrollPane scrollPaneTp;
	private JList<TipoProd> jlTipoProd;
	

	/**
	 * Create the frame.
	 */
	public IfrmTipoProd(CtrlFrmProducto ctrl) {
		ctrlInterno = ctrl;
        addInternalFrameListener(this);
        ctrl.setiFrmTp(this);
        
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
		DefaultListModel<TipoProd> dlm = new DefaultListModel<>();
		
		scrollPaneTp = new JScrollPane();
		scrollPaneTp.setBounds(196, 0, 238, 270);
		contentPane.add(scrollPaneTp);

		jlTipoProd = new JList<>();
		scrollPaneTp.setViewportView(jlTipoProd);
		jlTipoProd.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		jlTipoProd.setModel(dlm);
		
		JButton btnTpAgr = new JButton("Agregar");
		btnTpAgr.setBounds(97, 39, 89, 23);
		contentPane.add(btnTpAgr);
		JButton btnTpMod = new JButton("Modificar");
		btnTpMod.setBounds(97, 73, 89, 23);
		contentPane.add(btnTpMod);
		JButton btnTpEli = new JButton("Eliminar");
		btnTpEli.setBounds(97, 107, 89, 23);
		contentPane.add(btnTpEli);
		
		/*****************************************************************************************************************************************************/
		/* ACCIONES CONTROLES */
		/*****************************************************************************************************************************************************/
		ctrl.cargarListTipoProd(jlTipoProd);
		
		btnTpAgr.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				ctrl.agregarTipoProd(txtTpDesc, jlTipoProd);
			}
		});
		btnTpMod.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ctrl.modificarTipoProd(txtTpDesc, jlTipoProd);
			}
		});
		btnTpEli.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ctrl.eliminarTipoProd(jlTipoProd);
			}
		});
		jlTipoProd.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent arg0) {
				ctrl.cargarControlesTipoProd(txtTpDesc, jlTipoProd);
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
			ctrlInterno.cerrarIFrmTp();
			ctrlInterno.cargarCbxTipoProd(ctrlInterno.getFrm().getCbxTipoProd());
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
