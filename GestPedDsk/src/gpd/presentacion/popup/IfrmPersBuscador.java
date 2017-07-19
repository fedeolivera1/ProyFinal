package gpd.presentacion.popup;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.event.InternalFrameAdapter;
import javax.swing.event.InternalFrameEvent;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import gpd.dominio.persona.Departamento;
import gpd.dominio.persona.Localidad;
import gpd.dominio.persona.Persona;
import gpd.dominio.persona.TipoPersona;
import gpd.presentacion.controlador.CtrlFrmPersBuscador;

public class IfrmPersBuscador extends JInternalFrame {

	private static final long serialVersionUID = 1L;
	private CtrlFrmPersBuscador ctrlInterno;
	private JTextField txtPbFiltro;
	private JComboBox<TipoPersona> cbxPbTipoPers;
	private JComboBox<Departamento> cbxPbDep;
	private JComboBox<Localidad> cbxPbLoc;
	private JList<Persona> jlPersBusq;
	
	/**
	 * Create the frame.
	 */
	public IfrmPersBuscador(CtrlFrmPersBuscador ctrl) {
		setTitle("Buscador Persona");
		setClosable(true);
		setBounds(100, 100, 450, 320);
		ctrlInterno = ctrl;

		ctrlInterno.setIfrmPb(this);
		getContentPane().setLayout(null);
		
		JButton btnPbLimpiar = new JButton("Limpiar");
		btnPbLimpiar.setBounds(335, 103, 89, 23);
		getContentPane().add(btnPbLimpiar);
		
		JButton btnPbBusq = new JButton("Buscar");
		btnPbBusq.setBounds(335, 72, 89, 23);
		getContentPane().add(btnPbBusq);
		
		JScrollPane scrollPanePersBusq = new JScrollPane();
		scrollPanePersBusq.setBounds(10, 137, 414, 140);
		getContentPane().add(scrollPanePersBusq);
		
		jlPersBusq = new JList<>();
		scrollPanePersBusq.setViewportView(jlPersBusq);
		
		cbxPbTipoPers = new JComboBox<>();
		cbxPbTipoPers.setBounds(105, 11, 151, 20);
		getContentPane().add(cbxPbTipoPers);
		
		txtPbFiltro = new JTextField();
		txtPbFiltro.setColumns(10);
		txtPbFiltro.setBounds(105, 42, 151, 20);
		getContentPane().add(txtPbFiltro);
		
		JLabel label = new JLabel("Departamento");
		label.setHorizontalAlignment(SwingConstants.RIGHT);
		label.setBounds(24, 76, 71, 14);
		getContentPane().add(label);
		
		cbxPbDep = new JComboBox<Departamento>();
		cbxPbDep.setBounds(105, 73, 151, 20);
		getContentPane().add(cbxPbDep);
		
		JLabel label_1 = new JLabel("Localidad");
		label_1.setHorizontalAlignment(SwingConstants.RIGHT);
		label_1.setBounds(24, 107, 71, 14);
		getContentPane().add(label_1);
		
		cbxPbLoc = new JComboBox<Localidad>();
		cbxPbLoc.setBounds(105, 104, 151, 20);
		getContentPane().add(cbxPbLoc);
		
		JLabel lblIdentificador = new JLabel("Filtro");
		lblIdentificador.setHorizontalAlignment(SwingConstants.RIGHT);
		lblIdentificador.setBounds(24, 45, 71, 14);
		getContentPane().add(lblIdentificador);
		
		
		/*****************************************************************************************************************************************************/
		/* ACCIONES CONTROLES */
		/*****************************************************************************************************************************************************/
		
		ctrlInterno.cargarCbxTipoPers(cbxPbTipoPers);
		ctrlInterno.cargarCbxDep(cbxPbDep);
		
		JLabel lblTipo = new JLabel("Tipo");
		lblTipo.setHorizontalAlignment(SwingConstants.RIGHT);
		lblTipo.setBounds(24, 14, 71, 14);
		getContentPane().add(lblTipo);
		
		cbxPbDep.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent arg0) {
				ctrlInterno.cargarCbxLoc(cbxPbDep, cbxPbLoc);
			}
		});
		//boton buscar persona
		btnPbBusq.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ctrlInterno.buscarPersona(cbxPbTipoPers, txtPbFiltro, cbxPbLoc);
			}
		});
		//enter en campo filtro
		txtPbFiltro.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent ke) {
				if(ke.getKeyCode() == KeyEvent.VK_ENTER) {
					ctrlInterno.buscarPersona(cbxPbTipoPers, txtPbFiltro, cbxPbLoc);
				}
			}
		});
		btnPbLimpiar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ctrlInterno.clearForm(getContentPane());
			}
		});
		jlPersBusq.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent arg0) {
				if(jlPersBusq.getSelectedIndex() > -1) {
					ctrlInterno.setPersSel(jlPersBusq.getSelectedValue());
				}
			}
		});
		jlPersBusq.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent me) {
				JList<?> listaPers = (JList<?>) me.getSource();
			    if (me.getClickCount() == 2 && listaPers.locationToIndex(me.getPoint()) > -1) {
			    	ctrlInterno.cerrarBuscadorPers(jlPersBusq.getSelectedValue());
			    }
			}
		});
		/***************************************************/
		/* EVENTO CIERRE DEL IFRM */
		/***************************************************/
		addInternalFrameListener(new InternalFrameAdapter() {
			@Override
			public void internalFrameClosing(InternalFrameEvent arg0) {
				ctrlInterno.cerrarBuscadorPers(jlPersBusq.getSelectedValue());
			}
		});
	}

	/*****************************************************************************************************************************************************/
	/* GET Y SET */
	/*****************************************************************************************************************************************************/
	
	public JTextField getTxtPbFiltro() {
		return txtPbFiltro;
	}
	public void setTxtPbFiltro(JTextField txtPbFiltro) {
		this.txtPbFiltro = txtPbFiltro;
	}

	public JComboBox<TipoPersona> getCbxPbTipoPers() {
		return cbxPbTipoPers;
	}
	public void setCbxPbTipoPers(JComboBox<TipoPersona> cbxPbTipoPers) {
		this.cbxPbTipoPers = cbxPbTipoPers;
	}

	public JComboBox<Departamento> getCbxPbDep() {
		return cbxPbDep;
	}
	public void setCbxPbDep(JComboBox<Departamento> cbxPbDep) {
		this.cbxPbDep = cbxPbDep;
	}

	public JComboBox<Localidad> getCbxPbLoc() {
		return cbxPbLoc;
	}
	public void setCbxPbLoc(JComboBox<Localidad> cbxPbLoc) {
		this.cbxPbLoc = cbxPbLoc;
	}

	public JList<Persona> getJlPersBusq() {
		return jlPersBusq;
	}
	public void setJlPersBusq(JList<Persona> jlPersBusq) {
		this.jlPersBusq = jlPersBusq;
	}
	
}
