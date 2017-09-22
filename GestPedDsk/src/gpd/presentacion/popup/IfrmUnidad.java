package gpd.presentacion.popup;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;

import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
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

import gpd.dominio.producto.Unidad;
import gpd.presentacion.controlador.CtrlFrmProducto;
import gpd.presentacion.generic.CnstPresGeneric;

public class IfrmUnidad extends JInternalFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField txtUniDesc;
	private CtrlFrmProducto ctrlInterno;
	private JScrollPane scrollPaneTp;
	private JList<Unidad> jlUnidad;
	

	/**
	 * Create the frame.
	 */
	public IfrmUnidad(CtrlFrmProducto ctrl) {
		ctrlInterno = ctrl;
        ctrl.setiFrmUni(this);
		setClosable(true);
		setBounds(100, 100, 450, 300);
		setTitle("Unidad");
		setResizable(false);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		if(ClassLoader.getSystemResource(CnstPresGeneric.ICON_BG) != null) {
			URL url = ClassLoader.getSystemResource(CnstPresGeneric.ICON_BG);
			ImageIcon icon = new ImageIcon(url);
			setFrameIcon(icon);
		}
		
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Unidad");
		lblNewLabel.setBounds(10, 11, 46, 14);
		contentPane.add(lblNewLabel);
		
		txtUniDesc = new JTextField();
		txtUniDesc.setBounds(66, 8, 120, 20);
		contentPane.add(txtUniDesc);
		txtUniDesc.setColumns(10);
		DefaultListModel<Unidad> dlm = new DefaultListModel<>();
		
		scrollPaneTp = new JScrollPane();
		scrollPaneTp.setBounds(196, 0, 238, 270);
		contentPane.add(scrollPaneTp);

		jlUnidad = new JList<>();
		scrollPaneTp.setViewportView(jlUnidad);
		jlUnidad.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		jlUnidad.setModel(dlm);
		
		JButton btnUniAgr = new JButton("Agregar");
		btnUniAgr.setBounds(97, 39, 89, 23);
		contentPane.add(btnUniAgr);
		JButton btnUniMod = new JButton("Modificar");
		btnUniMod.setBounds(97, 73, 89, 23);
		contentPane.add(btnUniMod);
		JButton btnUniEli = new JButton("Eliminar");
		btnUniEli.setBounds(97, 107, 89, 23);
		contentPane.add(btnUniEli);
		
		/*****************************************************************************************************************************************************/
		/* ACCIONES CONTROLES */
		/*****************************************************************************************************************************************************/
		ctrl.cargarListUnidad(jlUnidad);
		
		btnUniAgr.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				ctrl.agregarUnidad(txtUniDesc, jlUnidad);
			}
		});
		btnUniMod.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ctrl.modificarUnidad(txtUniDesc, jlUnidad);
			}
		});
		btnUniEli.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ctrl.eliminarUnidad(jlUnidad);
			}
		});
		jlUnidad.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent arg0) {
				ctrl.cargarControlesUnidad(txtUniDesc, jlUnidad);
			}
		});
		/***************************************************/
		/* EVENTO CIERRE DEL IFRM */
		/***************************************************/
		addInternalFrameListener(new InternalFrameAdapter() {
			@Override
			public void internalFrameClosing(InternalFrameEvent arg0) {
				ctrlInterno.cerrarIFrmTp();
				ctrlInterno.cargarCbxUnidad(ctrlInterno.getFrm().getCbxProUni());
			}
		});
	}

}
