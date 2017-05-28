package gpd.presentacion.popup;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.border.EmptyBorder;

import gpd.dominio.producto.TipoProd;
import gpd.presentacion.controlador.CtrlFrmProducto;

public class PopupTipoProd extends JFrame {

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
//					PopupTipoProd frame = new PopupTipoProd();
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
	public PopupTipoProd(CtrlFrmProducto ctrl) {
		ctrlInterno = ctrl;
		setTitle("Tipo Prod");
		setResizable(false);
		setLocationRelativeTo(null);
		setType(Type.UTILITY);
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
		
		JList<TipoProd> listTipoProd = new JList<>();
		listTipoProd.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		listTipoProd.setBounds(196, 10, 228, 247);
		contentPane.add(listTipoProd);
		DefaultListModel<TipoProd> dlm = new DefaultListModel<>();
		ctrl.cargarListTipoProd(dlm);
		listTipoProd.setModel(dlm);
		
		JButton btnTpAgr = new JButton("Agregar");
		btnTpAgr.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				ctrl.agregarTipoProd(txtTpDesc, dlm);
			}
		});
		btnTpAgr.setBounds(97, 39, 89, 23);
		contentPane.add(btnTpAgr);
		
		JButton btnTpMod = new JButton("Modificar");
		btnTpMod.setBounds(97, 73, 89, 23);
		contentPane.add(btnTpMod);
		
		JButton btnTpEli = new JButton("Eliminar");
		btnTpEli.setBounds(97, 107, 89, 23);
		contentPane.add(btnTpEli);
		
	}
	
	@Override
	protected void processWindowEvent(WindowEvent e) {
		super.processWindowEvent(e);
		if(WindowEvent.WINDOW_CLOSING == e.getID()) {
			ctrlInterno.cargarCbxTipoProd(ctrlInterno.getFrm().exponerCbxTipoProd());
		}
	}

}
