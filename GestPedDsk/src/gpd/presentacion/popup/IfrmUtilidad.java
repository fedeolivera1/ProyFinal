package gpd.presentacion.popup;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
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
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.event.InternalFrameAdapter;
import javax.swing.event.InternalFrameEvent;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import gpd.dominio.producto.Utilidad;
import gpd.presentacion.controlador.CtrlFrmProducto;

public class IfrmUtilidad extends JInternalFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField txtUtilDesc;
	private CtrlFrmProducto ctrlInterno;
	private JFormattedTextField txtUtilPorc;
	private JScrollPane scrollPaneUtil;
	private JList<Utilidad> jlUtil;
	

	/**
	 * Create the frame.
	 */
	public IfrmUtilidad(CtrlFrmProducto ctrl) {
		ctrlInterno = ctrl;
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
		DefaultListModel<Utilidad> dlm = new DefaultListModel<>();
		
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
		
		scrollPaneUtil = new JScrollPane();
		jlUtil = new JList<>();
		jlUtil.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		jlUtil.setModel(dlm);
		scrollPaneUtil.setViewportView(jlUtil);
		scrollPaneUtil.setBounds(196, 0, 238, 270);
		contentPane.add(scrollPaneUtil);

		
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
				ctrl.keyTypedDec(e);
			}
		});
		txtUtilPorc.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent arg0) {
				ctrl.isNumericFlt(txtUtilPorc);
			}
		});
		/***************************************************/
		/* EVENTO CIERRE DEL IFRM */
		/***************************************************/
		addInternalFrameListener(new InternalFrameAdapter() {
			@Override
			public void internalFrameClosing(InternalFrameEvent arg0) {
				ctrlInterno.cerrarIFrmTp();
				ctrlInterno.cargarCbxUtil(ctrlInterno.getFrm().getCbxUtil());
			}
		});
	}

}
