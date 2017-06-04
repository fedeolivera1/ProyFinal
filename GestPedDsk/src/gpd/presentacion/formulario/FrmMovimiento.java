package gpd.presentacion.formulario;

import javax.swing.JComboBox;
import javax.swing.JDesktopPane;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import gpd.dominio.producto.TipoProd;
import javax.swing.JButton;

public class FrmMovimiento extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;


	/**
	 * Create the frame.
	 */
	public FrmMovimiento() {
		setTitle("Movimiento");
		
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 800, 600);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JDesktopPane desktopPane = new JDesktopPane();
		desktopPane.setBounds(0, 0, 0, 0);
		contentPane.add(desktopPane);
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(0, 0, 784, 561);
		contentPane.add(tabbedPane);
		
		JPanel tpCompra = new JPanel();
		tabbedPane.addTab("Compra", null, tpCompra, null);
		tpCompra.setLayout(null);
		
		JButton btnCompraNueva = new JButton("Nueva Compra");
		btnCompraNueva.setBounds(400, 10, 124, 23);
		tpCompra.add(btnCompraNueva);
		
		JPanel panel = new JPanel();
		panel.setBounds(10, 63, 380, 113);
		tpCompra.add(panel);
		panel.setLayout(null);
		
		JComboBox<TipoProd> comboBox = new JComboBox<TipoProd>();
		comboBox.setBounds(72, 11, 151, 20);
		panel.add(comboBox);
		
		JLabel lblTipoProd = new JLabel("Tipo Prod");
		lblTipoProd.setBounds(16, 14, 46, 14);
		panel.add(lblTipoProd);
		lblTipoProd.setHorizontalAlignment(SwingConstants.RIGHT);
		
		JLabel lblProducto = new JLabel("Producto");
		lblProducto.setBounds(16, 48, 46, 14);
		panel.add(lblProducto);
		lblProducto.setHorizontalAlignment(SwingConstants.RIGHT);
		
		JComboBox<TipoProd> comboBox_1 = new JComboBox<TipoProd>();
		comboBox_1.setBounds(72, 42, 151, 20);
		panel.add(comboBox_1);
		
		JLabel lblUtilidad_1 = new JLabel("Cantidad");
		lblUtilidad_1.setBounds(16, 76, 46, 14);
		panel.add(lblUtilidad_1);
		lblUtilidad_1.setHorizontalAlignment(SwingConstants.RIGHT);
		
		JFormattedTextField formattedTextField = new JFormattedTextField();
		formattedTextField.setBounds(72, 73, 65, 20);
		panel.add(formattedTextField);
		
		JButton btnCompraAgrItem = new JButton("Agregar Item");
		btnCompraAgrItem.setBounds(233, 72, 124, 23);
		panel.add(btnCompraAgrItem);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBounds(10, 10, 380, 42);
		tpCompra.add(panel_1);
		panel_1.setLayout(null);
		
		JLabel lblProveedor = new JLabel("Proveedor");
		lblProveedor.setBounds(10, 14, 52, 14);
		panel_1.add(lblProveedor);
		lblProveedor.setHorizontalAlignment(SwingConstants.RIGHT);
		
		JComboBox<TipoProd> comboBox_2 = new JComboBox<TipoProd>();
		comboBox_2.setBounds(72, 11, 151, 20);
		panel_1.add(comboBox_2);
		
		JPanel tpVenta = new JPanel();
		tabbedPane.addTab("Venta", null, tpVenta, null);
		tpVenta.setLayout(null);
		
		
	}
}
