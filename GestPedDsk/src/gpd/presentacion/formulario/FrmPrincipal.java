package gpd.presentacion.formulario;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.border.EmptyBorder;

import gpd.dominio.usuario.UsuarioDsk;

public class FrmPrincipal extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;


	/**
	 * Create the frame.
	 */
	public FrmPrincipal(UsuarioDsk usr) {
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 800, 600);
		
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		JMenu mnArchivo = new JMenu("Archivo");
		menuBar.add(mnArchivo);
		
		JMenuItem mnSalir = new JMenuItem("Salir");
		mnSalir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		
		JMenuItem mntmUsuario = new JMenuItem("Usuario");
		mnArchivo.add(mntmUsuario);
		mnArchivo.add(mnSalir);
		
		JMenu mnCliente = new JMenu("Persona");
		menuBar.add(mnCliente);
		
		JMenuItem mntmClientePersona = new JMenuItem("Cliente");
		mntmClientePersona.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				FrmPersona frmPers = FrmPersona.getFrmPersona(usr);
				frmPers.setLocationRelativeTo(null);
				//panel.pestaña("Empresa");
				JTabbedPane comp = (JTabbedPane) frmPers.getContentPane().getComponent(0);
				comp.setSelectedIndex(0);
				frmPers.setVisible(true);
			}
		});
		mnCliente.add(mntmClientePersona);
		
		JMenuItem mntmEmpresa = new JMenuItem("Empresa");
		mntmEmpresa.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				FrmPersona frmPers = FrmPersona.getFrmPersona(usr);
				frmPers.setLocationRelativeTo(null);
				//panel.pestaña("Empresa");
				JTabbedPane comp = (JTabbedPane) frmPers.getContentPane().getComponent(0);
				comp.setSelectedIndex(1);
				frmPers.setVisible(true);
			}
		});
		mnCliente.add(mntmEmpresa);
		
		JMenu mnProducto = new JMenu("Producto");
		menuBar.add(mnProducto);
		
		JMenuItem mntmMantProducto = new JMenuItem("Producto");
		mntmMantProducto.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				FrmProducto frmProd = FrmProducto.getFrmProducto(usr);
				frmProd.setLocationRelativeTo(null);
				//panel.pestaña("Producto");
				JTabbedPane comp = (JTabbedPane) frmProd.getContentPane().getComponent(1);
				comp.setSelectedIndex(0);
				frmProd.setVisible(true);
			}
		});
		mnProducto.add(mntmMantProducto);
		
		JMenuItem mntmLotes = new JMenuItem("Lote");
		mntmLotes.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				FrmProducto frmProd = FrmProducto.getFrmProducto(usr);
				frmProd.setLocationRelativeTo(null);
				//panel.pestaña("Producto");
				JTabbedPane comp = (JTabbedPane) frmProd.getContentPane().getComponent(1);
				comp.setSelectedIndex(1);
				frmProd.setVisible(true);
			}
		});
		mnProducto.add(mntmLotes);
		
		JMenuItem mntmDepositos = new JMenuItem("Depositos");
		mntmDepositos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				FrmProducto frmProd = FrmProducto.getFrmProducto(usr);
				frmProd.setLocationRelativeTo(null);
				JTabbedPane comp = (JTabbedPane) frmProd.getContentPane().getComponent(1);
				comp.setSelectedIndex(1);
				frmProd.setVisible(true);
				frmProd.getCtrlProd().abrirIFrmDep();
			}
		});
		mnProducto.add(mntmDepositos);
		
		JMenuItem mntmUtilidad = new JMenuItem("Utilidad");
		mntmUtilidad.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				FrmProducto frmProd = FrmProducto.getFrmProducto(usr);
				frmProd.setLocationRelativeTo(null);
				JTabbedPane comp = (JTabbedPane) frmProd.getContentPane().getComponent(1);
				comp.setSelectedIndex(1);
				frmProd.setVisible(true);
				frmProd.getCtrlProd().abrirIFrmUtil();
			}
		});
		mnProducto.add(mntmUtilidad);
		
		JMenu mnMovimientos = new JMenu("Movimientos");
		menuBar.add(mnMovimientos);
		
		JMenuItem mntmCompras = new JMenuItem("Compras");
		mnMovimientos.add(mntmCompras);
		
		JMenuItem mntmVentas = new JMenuItem("Ventas");
		mnMovimientos.add(mntmVentas);
		
		JMenu mnPedidos = new JMenu("Pedidos");
		menuBar.add(mnPedidos);
		
		JMenuItem mntmPedidos = new JMenuItem("Pedidos");
		mnPedidos.add(mntmPedidos);
		
		JMenu mnConsultas = new JMenu("Consultas");
		menuBar.add(mnConsultas);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel pnlPpal = new JPanel();
		pnlPpal.setBounds(0, 0, 784, 545);
		contentPane.add(pnlPpal);
		pnlPpal.setLayout(null);
		
		//listeners botones
	}
}
