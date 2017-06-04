package gpd.presentacion.popup;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.event.InternalFrameEvent;
import javax.swing.event.InternalFrameListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import gpd.dominio.producto.Utilidad;
import gpd.presentacion.controlador.CtrlFrmProducto;

public class IfrmUtilidad extends JInternalFrame implements InternalFrameListener {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField txtUtilDesc;
	private CtrlFrmProducto ctrlInterno;
	private JFormattedTextField txtUtilPorc;
	private JList<Utilidad> jlUtil;
	

	/**
	 * Create the frame.
	 */
	public IfrmUtilidad(CtrlFrmProducto ctrl) {
		ctrlInterno = ctrl;
        addInternalFrameListener(this);
        ctrl.setiFrmUtil(this);
        
		setIconifiable(true);
		setClosable(true);
		setBounds(100, 100, 450, 300);
		setTitle("Utilidad");
		setResizable(false);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Utilidad");
		lblNewLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNewLabel.setBounds(10, 11, 46, 14);
		contentPane.add(lblNewLabel);
		
		txtUtilDesc = new JTextField();
		txtUtilDesc.setBounds(66, 8, 120, 20);
		contentPane.add(txtUtilDesc);
		txtUtilDesc.setColumns(10);
		
		jlUtil = new JList<>();
		jlUtil.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		jlUtil.setBounds(196, 10, 228, 247);
		contentPane.add(jlUtil);
		DefaultListModel<Utilidad> dlm = new DefaultListModel<>();
		jlUtil.setModel(dlm);
		
		JLabel lblPorc = new JLabel("Porc.");
		lblPorc.setHorizontalAlignment(SwingConstants.RIGHT);
		lblPorc.setBounds(10, 42, 46, 14);
		contentPane.add(lblPorc);
		
		txtUtilPorc = new JFormattedTextField();
		txtUtilPorc.setBounds(66, 39, 65, 20);
		contentPane.add(txtUtilPorc);
		
		JButton btnUtilAgr = new JButton("Agregar");
		btnUtilAgr.setBounds(97, 70, 89, 23);
		contentPane.add(btnUtilAgr);
		JButton btnUtilMod = new JButton("Modificar");
		btnUtilMod.setBounds(97, 104, 89, 23);
		contentPane.add(btnUtilMod);
		JButton btnUtilEli = new JButton("Eliminar");
		btnUtilEli.setBounds(97, 138, 89, 23);
		contentPane.add(btnUtilEli);

		/*****************************************************************************************************************************************************/
		/* ACCIONES CONTROLES */
		/*****************************************************************************************************************************************************/
		
		ctrl.cargarListUtil(jlUtil);
		
		jlUtil.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent arg0) {
				ctrl.cargarControlesUtil(txtUtilDesc, txtUtilPorc, jlUtil);
			}
		});
		btnUtilAgr.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				ctrl.agregarUtilidad(txtUtilDesc, txtUtilPorc, jlUtil);
			}
		});
		btnUtilMod.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ctrl.modificarUtilidad(txtUtilDesc, txtUtilPorc, jlUtil);
			}
		});
		btnUtilEli.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ctrl.eliminarUtilidad(jlUtil);
			}
		});
		txtUtilPorc.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				ctrl.controlInputNum(e);
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
			ctrlInterno.cargarCbxUtil(ctrlInterno.getFrm().getCbxUtil());
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
