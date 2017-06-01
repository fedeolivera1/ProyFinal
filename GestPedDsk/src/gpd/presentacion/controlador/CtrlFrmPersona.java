package gpd.presentacion.controlador;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;

import javax.swing.ComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import gpd.dominio.persona.Departamento;
import gpd.dominio.persona.Localidad;
import gpd.dominio.persona.PersonaFisica;
import gpd.dominio.persona.Sexo;
import gpd.dominio.persona.TipoDoc;
import gpd.dominio.persona.TipoPersona;
import gpd.dominio.util.Origen;
import gpd.dominio.util.Sinc;
import gpd.manager.persona.ManagerPersona;
import gpd.presentacion.formulario.FrmPersona;
import gpd.presentacion.generic.CnstPresGeneric;
import gpd.presentacion.generic.GenCompType;
import gpd.types.Fecha;

public class CtrlFrmPersona extends CtrlGenerico {

	private ManagerPersona mgrPers = new ManagerPersona();
	private FrmPersona frmPers;
	
	public CtrlFrmPersona(FrmPersona frmPers) {
		super();
		this.setFrmPers(frmPers);
	}

	
	public FrmPersona getFrmPers() {
		return frmPers;
	}
	public void setFrmPers(FrmPersona frmPers) {
		this.frmPers = frmPers;
	}

	/*****************************************************************************************************************************************************/
	/* CONTROLES */
	/*****************************************************************************************************************************************************/
	
	public void cargarCbxSexo(JComboBox<Sexo> cbxPfSexo) {
		List<Sexo> listaSexo = new ArrayList<Sexo>(EnumSet.allOf(Sexo.class));
		for(Sexo sexo : listaSexo) {
			cbxPfSexo.addItem(sexo);
		}
		cbxPfSexo.setSelectedIndex(-1);
	}

	public void cargarCbxTipoDoc(JComboBox<TipoDoc> cbxPfTipoDoc) {
		cbxPfTipoDoc.removeAllItems();
		ArrayList<TipoDoc> listaTipoDoc = (ArrayList<TipoDoc>) mgrPers.obtenerListaTipoDoc();
		for(TipoDoc tipoDoc : listaTipoDoc) {
			cbxPfTipoDoc.addItem(tipoDoc);
		}
		cbxPfTipoDoc.setSelectedIndex(-1);
	}
	
	public void cargarCbxDep(JComboBox<Departamento> cbxDep) {
		cbxDep.removeAllItems();
		ArrayList<Departamento> listaDep = (ArrayList<Departamento>) mgrPers.obtenerListaDepartamento();
		for(Departamento dep : listaDep) {
			cbxDep.addItem(dep);
		}
		cbxDep.setSelectedIndex(-1);
	}
	
	public void cargarCbxLoc(JComboBox<Departamento> cbxDep, JComboBox<Localidad> cbxLoc) {
		cbxLoc.removeAllItems();
		if(controlDatosObl(cbxDep)) {
			Departamento dep = (Departamento) cbxDep.getSelectedItem();
			ArrayList<Localidad> listaLoc = (ArrayList<Localidad>) mgrPers.obtenerListaLocalidadPorDep(dep.getIdDepartamento());
			for(Localidad loc : listaLoc) {
				cbxLoc.addItem(loc);
			}
		}
	}
	
	public void cargarJtPersFisica(List<PersonaFisica> listaPf) {
		JTable tabla = frmPers.getJtPersFisica();
		limpiarJTable(tabla);
		DefaultTableModel modeloJt = new DefaultTableModel();
		frmPers.getJtPersFisica().setModel(modeloJt);
		modeloJt.addColumn("Doc");
		modeloJt.addColumn("Nombre");
		modeloJt.addColumn("Direccion");
		modeloJt.addColumn("Telefono");
		modeloJt.addColumn("Celular");
		modeloJt.addColumn("Email");
		for(PersonaFisica pf : listaPf) {
			Object [] fila = new Object[6];
			fila[0] = pf.getDocumento();
			fila[1] = pf.getNombre1() + " " + pf.getApellido1();
			fila[2] = pf.getDireccion() + " " + pf.getPuerta();
			fila[3] = pf.getTelefono();
			fila[4] = pf.getCelular();
			fila[5] = pf.getEmail();
			modeloJt.addRow(fila);
		}
		frmPers.getJtPersFisica().addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int fila = tabla.rowAtPoint(e.getPoint());
		        int columna = tabla.columnAtPoint(e.getPoint());
		        if ((fila > -1) && (columna > -1)) {
//		            System.out.println(modeloJt.getValueAt(fila, columna));
//		            System.out.println(modeloJt.getValueAt(fila, 0));
//			        System.out.println(modeloJt.getValueAt(fila, 1));
//			        System.out.println(modeloJt.getValueAt(fila, 2));
//			        System.out.println(modeloJt.getValueAt(fila, 3));
//			        System.out.println(modeloJt.getValueAt(fila, 4));
//			        System.out.println(modeloJt.getValueAt(fila, 5));
		        	Long documento = (Long) modeloJt.getValueAt(fila, 0);
		        	PersonaFisica pf = mgrPers.obtenerPersFisicaPorId(documento);
		        	cargarPersFisica(pf);
		        }
			}
		});

	}
	
	
	/*****************************************************************************************************************************************************/
	/* ACCIONES */
	/*****************************************************************************************************************************************************/

	public void agregarPersFisica(JComboBox<TipoDoc> cbxPfTipoDpc, JTextField txtPfDoc, JTextField txtPfApe1, JTextField txtPfApe2, JTextField txtPfNom1,
			JTextField txtPfNom2, JTextField txtPfFnac, JComboBox<Sexo> cbxPfSexo, JTextField txtPfDir,
			JTextField txtPfPue, JTextField txtPfSol, JTextField txtPfMan, JTextField txtPfKm, JTextField txtPfComp,
			JTextField txtPfTel, JTextField txtPfCel, JTextField txtPfEml, JComboBox<Localidad> cbxPersLoc) {

		GenCompType genComp = new GenCompType();
		genComp.setComp(cbxPfTipoDpc);
		genComp.setComp(txtPfDoc);
		genComp.setComp(txtPfApe1);
		genComp.setComp(txtPfNom1);
		genComp.setComp(txtPfDir);
		genComp.setComp(cbxPfSexo);
		genComp.setComp(cbxPersLoc);
		
		if(controlDatosObl(genComp)) {
			PersonaFisica pf = new PersonaFisica();
			TipoDoc tipoDoc = (TipoDoc) cbxPfTipoDpc.getSelectedItem();
			pf.setTipoDoc(tipoDoc);
			//datos pf
			pf.setDocumento(new Long(txtPfDoc.getText()));
			pf.setApellido1(txtPfApe1.getText());
			pf.setApellido2(txtPfApe2.getText());
			pf.setNombre1(txtPfNom1.getText());
			pf.setNombre2(txtPfNom2.getText());
			pf.setFechaNac(convertirFechaDesdeTxt(txtPfFnac.getText()));
			pf.setSexo((Sexo) cbxPfSexo.getSelectedItem());
			pf.setOrigen(Origen.D);
			pf.setSinc(Sinc.N);
			pf.setUltAct(new Fecha(Fecha.AMDHMS));
			//datos persona
			pf.setDireccion(txtPfDir.getText());
			pf.setPuerta(txtPfPue.getText());
			pf.setSolar(txtPfSol.getText());
			pf.setManzana(txtPfMan.getText());
			pf.setKm(ctrlNumDec(txtPfKm.getText()) ? new Float(txtPfKm.getText()) : null);
			pf.setComplemento(txtPfComp.getText());
			pf.setTelefono(txtPfTel.getText());
			pf.setCelular(txtPfCel.getText());
			pf.setEmail(txtPfEml.getText());
			pf.setFechaReg(new Fecha(Fecha.AMD));
			pf.setTipoPers(TipoPersona.F);
			Localidad loc = (Localidad) cbxPersLoc.getSelectedItem();
			pf.setLocalidad(loc);
			mgrPers.guardarPersFisica(pf);
		} else {
			enviarWarning(CnstPresGeneric.PERS, "Revise los datos marcados en rojo.");
		}
	}
	
	public void modificarPersFisica(JComboBox<TipoDoc> cbxPfTipoDpc, JTextField txtPfDoc, JTextField txtPfApe1, JTextField txtPfApe2, JTextField txtPfNom1,
			JTextField txtPfNom2, JTextField txtPfFnac, JComboBox<Sexo> cbxPfSexo, JTextField txtPfDir,
			JTextField txtPfPue, JTextField txtPfSol, JTextField txtPfMan, JTextField txtPfKm, JTextField txtPfComp,
			JTextField txtPfTel, JTextField txtPfCel, JTextField txtPfEml, JComboBox<Localidad> cbxPersLoc) {
		//TODO
	}
	
	public void eliminarPersFisica(JTextField txtPfDoc) {
		//TODO
	}
	
	public void buscarPersFisica(JTextField txtPfDoc, JTextField txtPfApe1, JTextField txtPfApe2, JTextField txtPfNom1,
			JTextField txtPfNom2, JComboBox<Sexo> cbxPfSexo, JTextField txtPfDir, JTextField txtPfTel, JTextField txtPfCel, 
			JTextField txtPfEml, JComboBox<Localidad> cbxPersLoc) {
		Long doc = ctrlNumLong(txtPfDoc.getText()) ? new Long(txtPfDoc.getText()) : null;
		ArrayList<PersonaFisica> listaPf = (ArrayList<PersonaFisica>) mgrPers.obtenerBusquedaPersFisica(doc, txtPfApe1.getText(), txtPfApe2.getText(), 
				txtPfNom1.getText(), txtPfNom2.getText(), (Sexo) cbxPfSexo.getSelectedItem(), txtPfDir.getText(), txtPfTel.getText(), txtPfCel.getText(), 
				txtPfEml.getText(), (Localidad) cbxPersLoc.getSelectedItem());
			cargarJtPersFisica(listaPf);
	}
	
	public void cargarPersFisica(PersonaFisica pf) {
		//datos pf
		ComboBoxModel<TipoDoc> cbModelTd = frmPers.getCbxPfTipoDoc().getModel();
		cbModelTd.setSelectedItem(pf.getTipoDoc());
		frmPers.getCbxPfTipoDoc().setSelectedItem(cbModelTd.getSelectedItem());
		frmPers.getTxtPfDoc().setText(String.valueOf(pf.getDocumento()).trim());
		frmPers.getTxtPfApe1().setText(String.valueOf(pf.getApellido1()));
		frmPers.getTxtPfApe2().setText(String.valueOf(pf.getApellido2()));
		frmPers.getTxtPfNom1().setText(String.valueOf(pf.getNombre1()));
		frmPers.getTxtPfNom2().setText(String.valueOf(pf.getNombre2()));
		frmPers.getTxtPfFnac().setText(pf.getFechaNac().toString(Fecha.DMA));
		frmPers.getCbxPfSexo().setSelectedItem(pf.getSexo());
//		//datos persona
		frmPers.getTxtPfDir().setText(pf.getDireccion());
		frmPers.getTxtPfPue().setText(pf.getPuerta());
		frmPers.getTxtPfSol().setText(pf.getSolar());
		frmPers.getTxtPfMan().setText(pf.getManzana());
		frmPers.getTxtPfKm().setText(String.valueOf(pf.getKm()));
		frmPers.getTxtPfComp().setText(pf.getComplemento());
		frmPers.getTxtPfTel().setText(pf.getTelefono());
		frmPers.getTxtPfCel().setText(pf.getCelular());
		frmPers.getTxtPfEml().setText(pf.getEmail());
		ComboBoxModel<Departamento> cbModelDep = frmPers.getCbxPersDep().getModel();
		cbModelDep.setSelectedItem(pf.getLocalidad().getDepartamento());
		frmPers.getCbxPersDep().setSelectedItem(cbModelDep.getSelectedItem());
		ComboBoxModel<Localidad> cbModelLoc = frmPers.getCbxPersLoc().getModel();
		cbModelLoc.setSelectedItem(pf.getLocalidad());
		frmPers.getCbxPersLoc().setSelectedItem(cbModelLoc.getSelectedItem());
	}

}
