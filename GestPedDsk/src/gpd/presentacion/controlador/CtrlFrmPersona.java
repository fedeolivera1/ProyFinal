package gpd.presentacion.controlador;

import java.awt.Container;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;

import javax.swing.ComboBoxModel;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import gpd.dominio.persona.Departamento;
import gpd.dominio.persona.Localidad;
import gpd.dominio.persona.PersonaFisica;
import gpd.dominio.persona.PersonaJuridica;
import gpd.dominio.persona.Sexo;
import gpd.dominio.persona.TipoDoc;
import gpd.dominio.persona.TipoPersona;
import gpd.dominio.util.Origen;
import gpd.exceptions.PresentacionException;
import gpd.manager.persona.ManagerPersona;
import gpd.presentacion.formulario.FrmPersona;
import gpd.presentacion.generic.CnstPresExceptions;
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
		try {
			List<Sexo> listaSexo = new ArrayList<Sexo>(EnumSet.allOf(Sexo.class));
			for(Sexo sexo : listaSexo) {
				cbxPfSexo.addItem(sexo);
			}
			cbxPfSexo.setSelectedIndex(-1);
		} catch(Exception e) {
			manejarExcepcion(e);
		}
	}

	public void cargarCbxTipoDoc(JComboBox<TipoDoc> cbxPfTipoDoc) {
		try {
			cbxPfTipoDoc.removeAllItems();
			ArrayList<TipoDoc> listaTipoDoc = (ArrayList<TipoDoc>) mgrPers.obtenerListaTipoDoc();
			for(TipoDoc tipoDoc : listaTipoDoc) {
				cbxPfTipoDoc.addItem(tipoDoc);
			}
			cbxPfTipoDoc.setSelectedIndex(-1);
		} catch(Exception e) {
			manejarExcepcion(e);
		}
	}
	
	public void cargarCbxDep(JComboBox<Departamento> cbxDep) {
		try {
			cbxDep.removeAllItems();
			ArrayList<Departamento> listaDep = (ArrayList<Departamento>) mgrPers.obtenerListaDepartamento();
			for(Departamento dep : listaDep) {
				cbxDep.addItem(dep);
			}
			cbxDep.setSelectedIndex(-1);
		} catch(Exception e) {
			manejarExcepcion(e);
		}
	}
	
	public void cargarCbxLoc(JComboBox<Departamento> cbxDep, JComboBox<Localidad> cbxLoc) {
		try {
			cbxLoc.removeAllItems();
			if(controlDatosObl(cbxDep)) {
				Departamento dep = (Departamento) cbxDep.getSelectedItem();
				ArrayList<Localidad> listaLoc = (ArrayList<Localidad>) mgrPers.obtenerListaLocalidadPorDep(dep.getIdDepartamento());
				for(Localidad loc : listaLoc) {
					cbxLoc.addItem(loc);
				}
			}
		} catch(Exception e) {
			manejarExcepcion(e);
		}
	}
	
	public void cargarJtPersFisica(List<PersonaFisica> listaPf) {
		try {	
			JTable tabla = frmPers.getJtPersFisica();
			clearTable(tabla);
			if(listaPf != null && !listaPf.isEmpty()) {
				DefaultTableModel modeloJtPf = new DefaultTableModel() {
					private static final long serialVersionUID = 1L;
					@Override
				    public boolean isCellEditable (int fila, int columna) {
				        return false;
				    }
				};
				tabla.setModel(modeloJtPf);
				modeloJtPf.addColumn("Doc");
				modeloJtPf.addColumn("Nombre");
				modeloJtPf.addColumn("Direccion");
				modeloJtPf.addColumn("Telefono");
				modeloJtPf.addColumn("Celular");
				modeloJtPf.addColumn("Email");
				for(PersonaFisica pf : listaPf) {
					Object [] fila = new Object[6];
					fila[0] = pf.getDocumento();
					fila[1] = pf.getNombre1() + " " + pf.getApellido1();
					fila[2] = pf.getDireccion() + " " + pf.getPuerta();
					fila[3] = pf.getTelefono();
					fila[4] = pf.getCelular();
					fila[5] = pf.getEmail();
					modeloJtPf.addRow(fila);
				}
				tabla.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseClicked(MouseEvent me) {
						try {
							int fila = tabla.rowAtPoint(me.getPoint());
							if (fila > -1 && tabla.getModel().getColumnCount() > 1) {
								Long documento = (Long) tabla.getModel().getValueAt(fila, 0);
								PersonaFisica pf = mgrPers.obtenerPersFisicaPorId(documento);
								//obtengo jpanel contenedor de la tabla (2 niveles up)
								Container containerJTable = tabla.getParent().getParent().getParent();
								cargarControlesPersFisica(pf, containerJTable);
							}
						} catch (PresentacionException e) {
							enviarError(CnstPresExceptions.DB, e.getMessage());
						}
					}
				});
			} else {
				cargarJTableVacia(tabla, null);
			}
		} catch(Exception e) {
			manejarExcepcion(e);
		}
	}
	
	public void cargarJtPersJuridica(List<PersonaJuridica> listaPj) {
		try {
			JTable tabla = frmPers.getJtPersJuridica();
			clearTable(tabla);
			if(listaPj != null && !listaPj.isEmpty()) {
				DefaultTableModel modeloJtPj = new DefaultTableModel() {
					private static final long serialVersionUID = 1L;
					@Override
				    public boolean isCellEditable (int fila, int columna) {
				        return false;
				    }
				};
				tabla.setModel(modeloJtPj);
				modeloJtPj.addColumn("Rut");
				modeloJtPj.addColumn("Nombre");
				modeloJtPj.addColumn("Direccion");
				modeloJtPj.addColumn("Telefono");
				modeloJtPj.addColumn("Celular");
				modeloJtPj.addColumn("Email");
				for(PersonaJuridica pj : listaPj) {
					Object [] fila = new Object[6];
					fila[0] = pj.getRut();
					fila[1] = pj.getNombre();
					fila[2] = pj.getDireccion() + " " + pj.getPuerta();
					fila[3] = pj.getTelefono();
					fila[4] = pj.getCelular();
					fila[5] = pj.getEmail();
					modeloJtPj.addRow(fila);
				}
				tabla.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseClicked(MouseEvent me) {
						try {
							int fila = tabla.rowAtPoint(me.getPoint());
							if (fila > -1) {
								Long rut = (Long) tabla.getModel().getValueAt(fila, 0);
								PersonaJuridica pj = mgrPers.obtenerPersJuridicaPorId(rut);
								//obtengo jpanel contenedor de la tabla (2 niveles up)
								Container containerJTable = tabla.getParent().getParent().getParent();
								cargarControlesPersJuridica(pj, containerJTable);
							}
						} catch (PresentacionException e) {
							enviarError(CnstPresExceptions.DB, e.getMessage());
						}
					}
				});
			} else {
				cargarJTableVacia(tabla, null);
			}
		} catch(Exception e) {
			manejarExcepcion(e);
		}
	}
	
	public void cargarControlesPersFisica(PersonaFisica pf, Container panel) {
		try {
			clearControlsInJPanel(panel);
			//datos pf
			ComboBoxModel<TipoDoc> cbModelTd = frmPers.getCbxPfTipoDoc().getModel();
			cbModelTd.setSelectedItem(pf.getTipoDoc());
			frmPers.getCbxPfTipoDoc().setSelectedItem(cbModelTd.getSelectedItem());
			frmPers.getTxtPfDoc().setText(String.valueOf(pf.getDocumento()).trim());
			frmPers.getTxtPfApe1().setText(pf.getApellido1());
			frmPers.getTxtPfApe2().setText(pf.getApellido2());
			frmPers.getTxtPfNom1().setText(pf.getNombre1());
			frmPers.getTxtPfNom2().setText(pf.getNombre2());
			frmPers.getTxtPfFnac().setText(pf.getFechaNac() != null ? pf.getFechaNac().toString(Fecha.DMA) : STR_VACIO);
			frmPers.getCbxPfSexo().setSelectedItem(pf.getSexo());
			//datos persona
			frmPers.getTxtPfDir().setText(pf.getDireccion());
			frmPers.getTxtPfPue().setText(pf.getPuerta());
			frmPers.getTxtPfSol().setText(pf.getSolar());
			frmPers.getTxtPfMan().setText(pf.getManzana());
			frmPers.getTxtPfKm().setText(String.valueOf(pf.getKm()));
			frmPers.getTxtPfComp().setText(pf.getComplemento());
			frmPers.getTxtPfTel().setText(pf.getTelefono());
			frmPers.getTxtPfCel().setText(pf.getCelular());
			frmPers.getTxtPfEml().setText(pf.getEmail());
			ComboBoxModel<Departamento> cbModelDep = frmPers.getCbxPfDep().getModel();
			cbModelDep.setSelectedItem(pf.getLocalidad().getDepartamento());
			frmPers.getCbxPfDep().setSelectedItem(cbModelDep.getSelectedItem());
			ComboBoxModel<Localidad> cbModelLoc = frmPers.getCbxPfLoc().getModel();
			cbModelLoc.setSelectedItem(pf.getLocalidad());
			frmPers.getCbxPfLoc().setSelectedItem(cbModelLoc.getSelectedItem());
		} catch(Exception e) {
			manejarExcepcion(e);
		}
	}
	
	public void cargarControlesPersJuridica(PersonaJuridica pj, Container panel) {
		try {
			clearControlsInJPanel(panel);
			//datos pj
			frmPers.getTxtPjRut().setText(String.valueOf(pj.getRut()));
			frmPers.getTxtPjNom().setText(pj.getNombre());
			frmPers.getTxtPjRs().setText(pj.getRazonSocial());
			frmPers.getTxtPjBps().setText(pj.getBps());
			frmPers.getTxtPjBse().setText(pj.getBse());
			frmPers.getChkPjProv().setSelected(pj.getEsProv());
			//datos persona
			frmPers.getTxtPjDir().setText(pj.getDireccion());
			frmPers.getTxtPjPue().setText(pj.getPuerta());
			frmPers.getTxtPjSol().setText(pj.getSolar());
			frmPers.getTxtPjMan().setText(pj.getManzana());
			frmPers.getTxtPjKm().setText(String.valueOf(pj.getKm()));
			frmPers.getTxtPjComp().setText(pj.getComplemento());
			frmPers.getTxtPjTel().setText(pj.getTelefono());
			frmPers.getTxtPjCel().setText(pj.getCelular());
			frmPers.getTxtPjEml().setText(pj.getEmail());
			ComboBoxModel<Departamento> cbModelDep = frmPers.getCbxPjDep().getModel();
			cbModelDep.setSelectedItem(pj.getLocalidad().getDepartamento());
			frmPers.getCbxPjDep().setSelectedItem(cbModelDep.getSelectedItem());
			ComboBoxModel<Localidad> cbModelLoc = frmPers.getCbxPjLoc().getModel();
			cbModelLoc.setSelectedItem(pj.getLocalidad());
			frmPers.getCbxPjLoc().setSelectedItem(cbModelLoc.getSelectedItem());
		} catch(Exception e) {
			manejarExcepcion(e);
		}
	}
	
	
	/*****************************************************************************************************************************************************/
	/* ACCIONES */
	/*****************************************************************************************************************************************************/

	public void buscarPersFisica(JTextField txtPfDoc, JTextField txtPfApe1, JTextField txtPfApe2, JTextField txtPfNom1,
			JTextField txtPfNom2, JComboBox<Sexo> cbxPfSexo, JTextField txtPfDir, JTextField txtPfTel, JTextField txtPfCel, 
			JTextField txtPfEml, JComboBox<Localidad> cbxPfLoc) {
		try {
			Long doc = ctrlNumLong(txtPfDoc.getText()) ? new Long(txtPfDoc.getText()) : null;
			ArrayList<PersonaFisica> listaPf = (ArrayList<PersonaFisica>) mgrPers.obtenerBusquedaPersFisica(doc, txtPfApe1.getText(), txtPfApe2.getText(), 
					txtPfNom1.getText(), txtPfNom2.getText(), (Sexo) cbxPfSexo.getSelectedItem(), txtPfDir.getText(), txtPfTel.getText(), txtPfCel.getText(), 
					txtPfEml.getText(), (Localidad) cbxPfLoc.getSelectedItem());
			cargarJtPersFisica(listaPf);
		} catch(Exception e) {
			manejarExcepcion(e);
		}
	}
	
	public void agregarPersFisica(JComboBox<TipoDoc> cbxPfTipoDoc, JTextField txtPfDoc, JTextField txtPfApe1, JTextField txtPfApe2, JTextField txtPfNom1,
			JTextField txtPfNom2, JTextField txtPfFnac, JComboBox<Sexo> cbxPfSexo, JTextField txtPfDir,
			JTextField txtPfPue, JTextField txtPfSol, JTextField txtPfMan, JTextField txtPfKm, JTextField txtPfComp,
			JTextField txtPfTel, JTextField txtPfCel, JTextField txtPfEml, JComboBox<Localidad> cbxPersLoc) {
		try {
			GenCompType genComp = new GenCompType();
			genComp.setComp(cbxPfTipoDoc);
			genComp.setComp(txtPfDoc);
			genComp.setComp(txtPfApe1);
			genComp.setComp(txtPfNom1);
			genComp.setComp(txtPfDir);
			genComp.setComp(cbxPfSexo);
			genComp.setComp(cbxPersLoc);
			if(controlDatosObl(genComp)) {
				Long documento = new Long(txtPfDoc.getText());
				if(!mgrPers.checkExistPersona(documento)) {
					PersonaFisica pf = new PersonaFisica();
					TipoDoc tipoDoc = (TipoDoc) cbxPfTipoDoc.getSelectedItem();
					pf.setTipoDoc(tipoDoc);
					//datos pf
					pf.setDocumento(documento);
					pf.setApellido1(txtPfApe1.getText());
					pf.setApellido2(txtPfApe2.getText());
					pf.setNombre1(txtPfNom1.getText());
					pf.setNombre2(txtPfNom2.getText());
					pf.setFechaNac(convertirFechaDesdeTxt(txtPfFnac.getText()));
					pf.setSexo((Sexo) cbxPfSexo.getSelectedItem());
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
					pf.setOrigen(Origen.D);
					mgrPers.guardarPersFisica(pf);
					clearPanel(frmPers.getContentPane());
					List<PersonaFisica> lst = new ArrayList<>();
					lst.add(pf);
					cargarJtPersFisica(lst);
				} else {
					enviarWarning(CnstPresGeneric.PERS, CnstPresGeneric.PERS_F_ING_EXIST);
				}
			} else {
				enviarWarning(CnstPresGeneric.PERS, CnstPresGeneric.DATOS_OBLIG);
			}
		} catch(Exception e) {
			manejarExcepcion(e);
		}
	}
	
	public void modificarPersFisica(JComboBox<TipoDoc> cbxPfTipoDpc, JTextField txtPfDoc, JTextField txtPfApe1, JTextField txtPfApe2, JTextField txtPfNom1,
			JTextField txtPfNom2, JTextField txtPfFnac, JComboBox<Sexo> cbxPfSexo, JTextField txtPfDir,
			JTextField txtPfPue, JTextField txtPfSol, JTextField txtPfMan, JTextField txtPfKm, JTextField txtPfComp,
			JTextField txtPfTel, JTextField txtPfCel, JTextField txtPfEml, JComboBox<Localidad> cbxPersLoc, JTable jtPersFisica) {
		try {
			GenCompType genComp = new GenCompType();
			genComp.setComp(cbxPfTipoDpc);
			genComp.setComp(txtPfDoc);
			genComp.setComp(txtPfApe1);
			genComp.setComp(txtPfNom1);
			genComp.setComp(txtPfDir);
			genComp.setComp(cbxPfSexo);
			genComp.setComp(cbxPersLoc);
			genComp.setComp(jtPersFisica);
			if(controlDatosObl(genComp)) {
				Long docPresentacion = new Long(txtPfDoc.getText());
				if(mgrPers.checkExistPersona(docPresentacion)) {
					Long docFpActual = (Long) jtPersFisica.getModel().getValueAt(jtPersFisica.getSelectedRow(), 0);
					PersonaFisica pf = (PersonaFisica) mgrPers.obtenerPersFisicaPorId(docFpActual);
					TipoDoc tipoDoc = (TipoDoc) cbxPfTipoDpc.getSelectedItem();
					pf.setTipoDoc(tipoDoc);
					//datos pf
					if(!pf.getDocumento().equals(docPresentacion)) {
						pf.setDocumentoAnt(pf.getDocumento());
					}
					pf.setDocumento(docPresentacion);
					pf.setApellido1(txtPfApe1.getText());
					pf.setApellido2(txtPfApe2.getText());
					pf.setNombre1(txtPfNom1.getText());
					pf.setNombre2(txtPfNom2.getText());
					pf.setFechaNac(convertirFechaDesdeTxt(txtPfFnac.getText()));
					pf.setSexo((Sexo) cbxPfSexo.getSelectedItem());
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
					pf.setOrigen(Origen.D);
					mgrPers.modificarPersFisica(pf);
					clearPanel(frmPers.getContentPane());
					List<PersonaFisica> lst = new ArrayList<>();
					lst.add(pf);
					cargarJtPersFisica(lst);
				} else {
					enviarWarning(CnstPresGeneric.PERS, CnstPresGeneric.PERS_F_ING_NOEXIST);
				}
			} else {
				enviarWarning(CnstPresGeneric.PERS, CnstPresGeneric.DATOS_OBLIG);
			}
		} catch(Exception e) {
			manejarExcepcion(e);
		}
	}
	
	public void eliminarPersFisica(JTextField txtPfDoc) {
		try {
			GenCompType genComp = new GenCompType();
			genComp.setComp(txtPfDoc);
			if(controlDatosObl(genComp)) {
				PersonaFisica pf = mgrPers.obtenerPersFisicaPorId(ctrlNumLong(txtPfDoc.getText()) ? new Long(txtPfDoc.getText()): null);
				mgrPers.eliminarPersFisica(pf);
				clearPanel(frmPers.getContentPane());
			} else {
				enviarWarning(CnstPresGeneric.PERS, CnstPresGeneric.DATOS_OBLIG);
			}
		} catch(Exception e) {
			manejarExcepcion(e);
		}
	}
	
	public void buscarPersJuridica(JTextField txtPjRut, JTextField txtPjNom, JTextField txtPjRs, JTextField txtPjBps,
			JTextField txtPjBse, JCheckBox chkPjProv, JTextField txtPjDir, JTextField txtPjTel, JTextField txtPjCel, 
			JTextField txtPjEml, JComboBox<Localidad> cbxPjLoc) {
		try {
			Long rut = ctrlNumLong(txtPjRut.getText()) ? new Long(txtPjRut.getText()) : null;
			List<PersonaJuridica> listaPj = (ArrayList<PersonaJuridica>) mgrPers.obtenerBusquedaPersJuridica(rut, txtPjNom.getText(), txtPjRs.getText(), 
					txtPjBps.getText(), txtPjBse.getText(), Boolean.valueOf(chkPjProv.isSelected()), txtPjDir.getText(), txtPjTel.getText(), txtPjCel.getText(), 
					txtPjEml.getText(), (Localidad) cbxPjLoc.getSelectedItem());
			cargarJtPersJuridica(listaPj);
		} catch(Exception e) {
			manejarExcepcion(e);
		}
	}
	
	public void agregarPersJuridica(JTextField txtPjRut, JTextField txtPjNom, JTextField txtPjRs, JTextField txtPjBps, JTextField txtPjBse, 
			JCheckBox chkPjProv, JTextField txtPfDir, JTextField txtPfPue, JTextField txtPfSol, JTextField txtPfMan, JTextField txtPfKm, 
			JTextField txtPfComp, JTextField txtPfTel, JTextField txtPfCel, JTextField txtPfEml, JComboBox<Localidad> cbxPersLoc) {
		try {
			GenCompType genComp = new GenCompType();
			genComp.setComp(txtPjRut);
			genComp.setComp(txtPjNom);
			genComp.setComp(txtPfDir);
			genComp.setComp(cbxPersLoc);
			if(controlDatosObl(genComp)) {
				Long rut = new Long(txtPjRut.getText());
				if(!mgrPers.checkExistPersona(rut)) {
					PersonaJuridica pj = new PersonaJuridica();
					//datos pj
					pj.setRut(rut);
					pj.setNombre(txtPjNom.getText());
					pj.setRazonSocial(txtPjRs.getText());
					pj.setBps(txtPjBps.getText());
					pj.setBse(txtPjBse.getText());
					pj.setEsProv(chkPjProv.isSelected());
					//datos persona
					pj.setDireccion(txtPfDir.getText());
					pj.setPuerta(txtPfPue.getText());
					pj.setSolar(txtPfSol.getText());
					pj.setManzana(txtPfMan.getText());
					pj.setKm(ctrlNumDec(txtPfKm.getText()) ? new Float(txtPfKm.getText()) : null);
					pj.setComplemento(txtPfComp.getText());
					pj.setTelefono(txtPfTel.getText());
					pj.setCelular(txtPfCel.getText());
					pj.setEmail(txtPfEml.getText());
					pj.setFechaReg(new Fecha(Fecha.AMD));
					pj.setTipoPers(TipoPersona.J);
					Localidad loc = (Localidad) cbxPersLoc.getSelectedItem();
					pj.setLocalidad(loc);
					pj.setOrigen(Origen.D);
					mgrPers.guardarPersJuridica(pj);
					clearPanel(frmPers.getContentPane());
					List<PersonaJuridica> lst = new ArrayList<>();
					lst.add(pj);
					cargarJtPersJuridica(lst);
				} else {
					enviarWarning(CnstPresGeneric.PERS, CnstPresGeneric.PERS_J_ING_EXIST);
				}
			} else {
				enviarWarning(CnstPresGeneric.PERS, CnstPresGeneric.DATOS_OBLIG);
			}
		} catch(Exception e) {
			manejarExcepcion(e);
		}
	}
	
	public void modificarPersJuridica(JTextField txtPjRut, JTextField txtPjNom, JTextField txtPjRs, JTextField txtPjBps, JTextField txtPjBse, 
			JCheckBox chkPjProv, JTextField txtPfDir, JTextField txtPfPue, JTextField txtPfSol, JTextField txtPfMan, JTextField txtPfKm, 
			JTextField txtPfComp, JTextField txtPfTel, JTextField txtPfCel, JTextField txtPfEml, JComboBox<Localidad> cbxPersLoc, JTable jtPersJuridica) {
		try {
			GenCompType genComp = new GenCompType();
			genComp.setComp(txtPjRut);
			genComp.setComp(txtPjNom);
			genComp.setComp(txtPfDir);
			genComp.setComp(cbxPersLoc);
			genComp.setComp(jtPersJuridica);
			if(controlDatosObl(genComp)) {
				Long rutPresentacion = new Long(txtPjRut.getText());
				if(mgrPers.checkExistPersona(rutPresentacion)) {
					Long rutPjActual = (Long) jtPersJuridica.getModel().getValueAt(jtPersJuridica.getSelectedRow(), 0);
					PersonaJuridica pj = (PersonaJuridica) mgrPers.obtenerPersJuridicaPorId(rutPjActual);
					//datos pj
					if(!pj.getRut().equals(rutPresentacion)) {
						pj.setRutAnt(pj.getRut());
					}
					pj.setRut(rutPresentacion);
					pj.setNombre(txtPjNom.getText());
					pj.setRazonSocial(txtPjRs.getText());
					pj.setBps(txtPjBps.getText());
					pj.setBse(txtPjBse.getText());
					pj.setEsProv(chkPjProv.isSelected());
					//datos persona
					pj.setDireccion(txtPfDir.getText());
					pj.setPuerta(txtPfPue.getText());
					pj.setSolar(txtPfSol.getText());
					pj.setManzana(txtPfMan.getText());
					pj.setKm(ctrlNumDec(txtPfKm.getText()) ? new Float(txtPfKm.getText()) : null);
					pj.setComplemento(txtPfComp.getText());
					pj.setTelefono(txtPfTel.getText());
					pj.setCelular(txtPfCel.getText());
					pj.setEmail(txtPfEml.getText());
					pj.setFechaReg(new Fecha(Fecha.AMD));
					pj.setTipoPers(TipoPersona.J);
					Localidad loc = (Localidad) cbxPersLoc.getSelectedItem();
					pj.setLocalidad(loc);
					pj.setOrigen(Origen.D);
					mgrPers.modificarPersJuridica(pj);
					clearPanel(frmPers.getContentPane());
					List<PersonaJuridica> lst = new ArrayList<>();
					lst.add(pj);
					cargarJtPersJuridica(lst);
				} else {
					enviarWarning(CnstPresGeneric.PERS, CnstPresGeneric.PERS_J_ING_NOEXIST);
				}
			} else {
				enviarWarning(CnstPresGeneric.PERS, CnstPresGeneric.DATOS_OBLIG);
			}
		} catch(Exception e) {
			manejarExcepcion(e);
		}
	}
	
	public void eliminarPersJuridica(JTextField txtPjRut) {
		try {
			GenCompType genComp = new GenCompType();
			genComp.setComp(txtPjRut);
			if(controlDatosObl(genComp)) {
				PersonaJuridica pj = mgrPers.obtenerPersJuridicaPorId(new Long(txtPjRut.getText()));
				mgrPers.eliminarPersJuridica(pj);
				clearPanel(frmPers.getContentPane());
			} else {
				enviarWarning(CnstPresGeneric.PERS, CnstPresGeneric.DATOS_OBLIG);
			}
		} catch(Exception e) {
			manejarExcepcion(e);
		}
	}
	
	
}
