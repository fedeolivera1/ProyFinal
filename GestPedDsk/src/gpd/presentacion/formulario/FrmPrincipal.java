package gpd.presentacion.formulario;

import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.border.EmptyBorder;

import gpd.dominio.usuario.TipoUsr;
import gpd.dominio.usuario.UsuarioDsk;
import gpd.presentacion.controlador.CtrlFrmUsuario;

public class FrmPrincipal extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private CtrlFrmUsuario ctrlUsuario;
	private JDesktopPane desktopPane;
	private UsuarioDsk usrLogueado;


	/**
	 * Create the frame.
	 */
	public FrmPrincipal(UsuarioDsk usr) {
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 800, 600);
		ctrlUsuario = new CtrlFrmUsuario(this);
		setUsrLogueado(usr);
		
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
		mntmUsuario.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				ctrlUsuario.abrirIFrmUsu();
			}
		});
		//control usr //FIXME revisar esto
		mntmUsuario.setEnabled(usr.getTipoUsr().equals(TipoUsr.A) ? true : false);
		//
		mnArchivo.add(mntmUsuario);
		
		JMenuItem mntmCambioContr = new JMenuItem("Cambio Contr");
		mntmCambioContr.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ctrlUsuario.abrirIFrmCp();
			}
		});
		mnArchivo.add(mntmCambioContr);
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
		
		JMenuItem mntmLote = new JMenuItem("Lote");
		mntmLote.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				FrmProducto frmProd = FrmProducto.getFrmProducto(usr);
				frmProd.setLocationRelativeTo(null);
				//panel.pestaña("Producto");
				JTabbedPane comp = (JTabbedPane) frmProd.getContentPane().getComponent(1);
				comp.setSelectedIndex(1);
				frmProd.setVisible(true);
			}
		});
		mnProducto.add(mntmLote);
		
		JMenuItem mntmDeposito = new JMenuItem("Deposito");
		mntmDeposito.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				FrmProducto frmProd = FrmProducto.getFrmProducto(usr);
				frmProd.setLocationRelativeTo(null);
				JTabbedPane comp = (JTabbedPane) frmProd.getContentPane().getComponent(1);
				comp.setSelectedIndex(1);
				frmProd.setVisible(true);
				frmProd.getCtrlProd().abrirIFrmDep();
			}
		});
		mnProducto.add(mntmDeposito);
		
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
		
		JMenu mnMovimiento = new JMenu("Movimiento");
		menuBar.add(mnMovimiento);
		
		JMenuItem mntmCompra = new JMenuItem("Compra");
		mntmCompra.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				FrmMovimiento frmMov = FrmMovimiento.getFrmMovimiento(usr);
				frmMov.setLocationRelativeTo(null);
				//panel.pestaña("Compra");
				JTabbedPane comp = (JTabbedPane) frmMov.getContentPane().getComponent(1);
				comp.setSelectedIndex(0);
				frmMov.setVisible(true);
			}
		});
		mnMovimiento.add(mntmCompra);
		
		JMenuItem mntmVenta = new JMenuItem("Venta");
		mntmVenta.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		mnMovimiento.add(mntmVenta);
		
		JMenu mnPedido = new JMenu("Pedido");
		menuBar.add(mnPedido);
		
		JMenuItem mntmPedido = new JMenuItem("Pedido");
		mntmPedido.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				FrmPedido frmPed = FrmPedido.getFrmPedido(usr);
				frmPed.setLocationRelativeTo(null);
				frmPed.setVisible(true);
			}
		});
		mnPedido.add(mntmPedido);
		
		JMenu mnConsulta = new JMenu("Consulta");
		menuBar.add(mnConsulta);
		
		JMenuItem mntmConsulta = new JMenuItem("Consulta");
		mntmConsulta.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				FrmConsulta frmCons = FrmConsulta.getFrmConsulta(usr);
				frmCons.setLocationRelativeTo(null);
				frmCons.setVisible(true);
			}
		});
		mnConsulta.add(mntmConsulta);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		desktopPane = new JDesktopPane();
		desktopPane.setBounds(0, 0, 0, 0);
		contentPane.add(desktopPane);
		desktopPane.setBackground(SystemColor.control);
		desktopPane.setLayout(null);
		contentPane.add(desktopPane);
		//agrego desktopPane a controlador
		ctrlUsuario.setDeskPane(desktopPane);
		
		JPanel pnlPpal = new JPanel();
		pnlPpal.setBounds(0, 0, 784, 545);
		contentPane.add(pnlPpal);
		pnlPpal.setLayout(null);
		
	}


	public UsuarioDsk getUsrLogueado() {
		return usrLogueado;
	}
	public void setUsrLogueado(UsuarioDsk usrLogueado) {
		this.usrLogueado = usrLogueado;
	}
}
