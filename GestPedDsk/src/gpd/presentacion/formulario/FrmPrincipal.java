package gpd.presentacion.formulario;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;

import gpd.dominio.usuario.UsuarioDsk;
import java.awt.event.ActionListener;
import javax.swing.JMenuItem;

public class FrmPrincipal extends JFrame {

	private static final long serialVersionUID = 1L;
	private final Action action = new SwingAction();
	private final Action action_1 = new SwingAction_1();
	private UsuarioDsk usu;

	/**
	 * Launch the application.
	 */
//	public static void main(String[] args) {
//		EventQueue.invokeLater(new Runnable() {
//			public void run() {
//				try {
//					
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//			}
//		});
//	}

	/**
	 * Create the frame.
	 */
	
	public FrmPrincipal(UsuarioDsk usr) {
		
		usu=usr;
		getContentPane().setBackground(Color.BLACK);
		
		JPanel panel = new JPanel();
		FlowLayout flowLayout_2 = (FlowLayout) panel.getLayout();
		flowLayout_2.setVgap(30);
		panel.setBackground(Color.BLACK);
		getContentPane().add(panel, BorderLayout.NORTH);
		
		JPanel panel_1 = new JPanel();
		FlowLayout flowLayout = (FlowLayout) panel_1.getLayout();
		panel_1.setBackground(Color.BLACK);
		panel.add(panel_1);
		
		JLabel lblBienvenido = new JLabel("Bienvenido \r\n");
		lblBienvenido.setHorizontalAlignment(SwingConstants.TRAILING);
		panel_1.add(lblBienvenido);
		lblBienvenido.setForeground(Color.WHITE);
		lblBienvenido.setFont(new Font("Tahoma", Font.BOLD, 24));
		
		JPanel panel_2 = new JPanel();
		getContentPane().add(panel_2, BorderLayout.CENTER);
		FlowLayout flowLayout_1 = (FlowLayout) panel_2.getLayout();
		panel_2.setBackground(Color.BLACK);
		
		JLabel lblNewLabel_1 = new JLabel("");
		panel_2.add(lblNewLabel_1);
		lblNewLabel_1.setIcon(new ImageIcon(FrmPrincipal.class.getResource("/gpd/recursos/paraProg.png")));
		
		JTextArea txtMensaje = new JTextArea();
		txtMensaje.setLineWrap(true);
		txtMensaje.setFont(new Font("Arial", Font.PLAIN, 12));
		txtMensaje.setText("De momento no hay alertas que requieran de su atenci\u00F3n.");
		panel_2.add(txtMensaje);
		txtMensaje.setColumns(30);
		txtMensaje.setTabSize(10);
		txtMensaje.setRows(10);
		txtMensaje.setEditable(false);
		setTitle("Opciones del administrador");
		setIconImage(Toolkit.getDefaultToolkit().getImage(FrmPrincipal.class.getResource("/gpd/recursos/Icono.png")));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 750, 550);
		
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		JMenu mnArchivo = new JMenu("Archivo");
		menuBar.add(mnArchivo);
		
		JMenu mnSalirAMen = new JMenu("Salir a men\u00FA");
		/** Hace que al presionar esa opci�n del menu se vuelva al frame de inicio**/
		mnSalirAMen.addMouseListener(new MouseListener() {
			
			    @Override
		        public void mouseClicked(MouseEvent e) {
		        	dispose();
		            FrmLogin frmLgn = new FrmLogin();
					frmLgn.setLocationRelativeTo(null);
					frmLgn.setDefaultCloseOperation(FrmPrincipal.EXIT_ON_CLOSE);
					frmLgn.setVisible(true);
		        }
				@Override
				public void mouseEntered(MouseEvent arg0) {
					// TODO Auto-generated method stub
					
				}
				@Override
				public void mouseExited(MouseEvent arg0) {
					// TODO Auto-generated method stub
					
				}
				@Override
				public void mousePressed(MouseEvent arg0) {
					// TODO Auto-generated method stub
					
				}
				@Override
				public void mouseReleased(MouseEvent arg0) {
					// TODO Auto-generated method stub
					
				}
	    });
		mnArchivo.add(mnSalirAMen);
		
		JMenu mnEdicin = new JMenu("Edici\u00F3n");
		menuBar.add(mnEdicin);
		
		JMenu mnTrabajadores = new JMenu("Usuarios");
		mnEdicin.add(mnTrabajadores);
		
		JMenu mnCambiarContraseaDe = new JMenu("Cambiar contrase\u00F1a de un usuario");
		mnCambiarContraseaDe.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				FrmCambiarCont frmCambUs = new FrmCambiarCont(usr);
				frmCambUs.setLocationRelativeTo(null);
				frmCambUs.setVisible(true);
			}
		});
		mnTrabajadores.add(mnCambiarContraseaDe);
		/**Abre frame para agregar un usuario*/
//		mnAgregarUsuario.addMouseListener(new MouseListener() {
//			
//		    @Override
//	        public void mouseClicked(MouseEvent e) {
//	        	FrmRegUsuario frmRu = new FrmRegUsuario(usr);
//				frmRu.setLocationRelativeTo(null);
//				frmRu.setDefaultCloseOperation(FrmPrincipal.EXIT_ON_CLOSE);
//				frmRu.setVisible(true);
//	        }
//			@Override
//			public void mouseEntered(MouseEvent arg0) {
//				// TODO Auto-generated method stub
//				
//			}
//			@Override
//			public void mouseExited(MouseEvent arg0) {
//				// TODO Auto-generated method stub
//				
//			}
//			@Override
//			public void mousePressed(MouseEvent arg0) {
//				// TODO Auto-generated method stub
//				
//			}
//			@Override
//			public void mouseReleased(MouseEvent arg0) {
//				// TODO Auto-generated method stub
//				
//			}
//    });
		
		JMenuItem mntmAgregarUsuario = new JMenuItem("Agregar usuario");
		mntmAgregarUsuario.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				FrmRegUsuario frmRu = new FrmRegUsuario(usr);
				frmRu.setLocationRelativeTo(null);
				frmRu.setVisible(true);
			}
		});
		mnTrabajadores.add(mntmAgregarUsuario);
		
		JMenuItem mntmEliminarUnUsuario = new JMenuItem("Eliminar un usuario");
		mntmEliminarUnUsuario.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				FrmElimUsuario frmEu = new FrmElimUsuario(usr);
				frmEu.setLocationRelativeTo(null);
				frmEu.setVisible(true);
			}
		});
		mnTrabajadores.add(mntmEliminarUnUsuario);
		
		
		JMenu mnModificarDatos = new JMenu("Modificar datos");
		mnEdicin.add(mnModificarDatos);
		
		JMenuItem mntmMisDatos = new JMenuItem("Mis datos");
		mnModificarDatos.add(mntmMisDatos);
		
		JMenu mnRacin = new JMenu("Productos");
		mnEdicin.add(mnRacin);
		
		JMenu mnAgregarNuevoTipo = new JMenu("Agregar nuevo producto");
		mnAgregarNuevoTipo.addMouseListener(new MouseListener() {
			
		    @Override
	        public void mouseClicked(MouseEvent e) {
		    	FrmProducto frm = FrmProducto.getFrmProducto(usr);
				frm.setLocationRelativeTo(null);
				frm.setVisible(true);
	        }
			@Override
			public void mouseEntered(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			@Override
			public void mouseExited(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			@Override
			public void mousePressed(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			@Override
			public void mouseReleased(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}
    });
		//mnAgregarNuevoTipo.addActionListener(new ActionListener() {
		//	public void actionPerformed(ActionEvent arg0) {
		//		FrmProducto frm = FrmProducto.getFrmProducto(usr);
		//		frm.setVisible(true);
		//	}
		//});
		mnRacin.add(mnAgregarNuevoTipo);
		
		JMenu mnPersonas = new JMenu("Personas");
		mnEdicin.add(mnPersonas);
		
		JMenuItem mntmAgregarPersona = new JMenuItem("Agregar persona");
		mntmAgregarPersona.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				FrmPersona frm = FrmPersona.getFrmPersona(usr);
				frm.setLocationRelativeTo(null);
				frm.setVisible(true);
			}
		});
		
		mnPersonas.add(mntmAgregarPersona);
		
		JMenu mnFacturas = new JMenu("Facturas");
		mnEdicin.add(mnFacturas);
		
		JMenu mnCompraAProveedor = new JMenu("Compra a proveedor");
		mnFacturas.add(mnCompraAProveedor);
		
		JMenu mnVentaACliente = new JMenu("Venta a cliente");
		mnFacturas.add(mnVentaACliente);
		
		JMenu mnConsultas = new JMenu("Consultas");
		menuBar.add(mnConsultas);
		
		JMenu mnStockRestante = new JMenu("Stock restante");
		mnConsultas.add(mnStockRestante);
		
		JMenu mnTotalDeCompras = new JMenu("Total de compras sin IVA a proveedor");
		mnConsultas.add(mnTotalDeCompras);
		
		JMenu mnIvaTotalDe = new JMenu("IVA total de compras a proveedor");
		mnConsultas.add(mnIvaTotalDe);
		
		JMenu mnTotalDeVentas = new JMenu("Total de ventas a cliente sin IVA");
		mnConsultas.add(mnTotalDeVentas);
		
		JMenu mnIvaTotalDe_1 = new JMenu("IVA total de las ventas a clientes");
		mnConsultas.add(mnIvaTotalDe_1);
		
		JMenu mnAyuda = new JMenu("Ayuda");
		menuBar.add(mnAyuda);
		
		lblBienvenido.setText("Bienvenido "+usu.getNomUsu());
		
	}

	private class SwingAction extends AbstractAction {
		public SwingAction() {
			putValue(NAME, "SwingAction");
			putValue(SHORT_DESCRIPTION, "Some short description");
		}
		public void actionPerformed(ActionEvent e) {
		}
	}
	private class SwingAction_1 extends AbstractAction {
		public SwingAction_1() {
			putValue(NAME, "SwingAction_1");
			putValue(SHORT_DESCRIPTION, "Some short description");
		}
		public void actionPerformed(ActionEvent e) {
		}
		
		
	}

//		setLocationRelativeTo(null);
//		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//		setBounds(100, 100, 800, 600);
//		
//		JMenuBar menuBar = new JMenuBar();
//		setJMenuBar(menuBar);
//		
//		JMenu mnArchivo = new JMenu("Archivo");
//		menuBar.add(mnArchivo);
//		
//		JMenuItem mnSalir = new JMenuItem("Salir");
//		mnSalir.addActionListener(new ActionListener() {
//			public void actionPerformed(ActionEvent e) {
//				System.exit(0);
//			}
//		});
//		mnArchivo.add(mnSalir);
//		
//		JMenu mnCliente = new JMenu("Cliente");
//		menuBar.add(mnCliente);
//		
//		JMenuItem mntmClientePersona = new JMenuItem("Mant Persona");
//		mntmClientePersona.addActionListener(new ActionListener() {
//			public void actionPerformed(ActionEvent e) {
//				FrmPersona frmPers = FrmPersona.getFrmPersona(usr);
//				frmPers.setLocationRelativeTo(null);
//				frmPers.setVisible(true);
//			}
//		});
//		mnCliente.add(mntmClientePersona);
//		
//		JMenuItem mntmEmpresa = new JMenuItem("Mant Empresa");
//		mnCliente.add(mntmEmpresa);
//		
//		JMenu mnProducto = new JMenu("Producto");
//		menuBar.add(mnProducto);
//		
//		JMenuItem mntmMantProducto = new JMenuItem("Mant Producto");
//		mntmMantProducto.addActionListener(new ActionListener() {
//			public void actionPerformed(ActionEvent arg0) {
//				FrmProducto frmProd = FrmProducto.getFrmProducto(usr);
//				frmProd.setLocationRelativeTo(null);
//				frmProd.setVisible(true);
//			}
//		});
//		mnProducto.add(mntmMantProducto);
//		
//		JMenuItem mntmLotes = new JMenuItem("Lotes");
//		mnProducto.add(mntmLotes);
//		
//		JMenuItem mntmDepositos = new JMenuItem("Depositos");
//		mnProducto.add(mntmDepositos);
//		contentPane = new JPanel();
//		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
//		setContentPane(contentPane);
//		contentPane.setLayout(null);
//		
//		JPanel pnlPpal = new JPanel();
//		pnlPpal.setBounds(0, 0, 784, 545);
//		contentPane.add(pnlPpal);
//		pnlPpal.setLayout(null);
	}
//}
