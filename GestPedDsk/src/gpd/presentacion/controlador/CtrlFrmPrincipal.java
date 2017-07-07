package gpd.presentacion.controlador;

import javax.swing.JPanel;
import javax.swing.JTextPane;

import gpd.manager.producto.ManagerProducto;
import gpd.presentacion.formulario.FrmPrincipal;

public class CtrlFrmPrincipal extends CtrlGenerico{
	private ManagerProducto mp=new ManagerProducto();
	private FrmPrincipal frmPrinc;
	
	public CtrlFrmPrincipal(FrmPrincipal fp){
		super();
		this.setFrmPrincipal(fp);
	}
	
	//Set y get
	public void setFrmPrincipal(FrmPrincipal fp){
		this.frmPrinc=fp;
	}
	
	public FrmPrincipal getFrmPrincipal(){
		return frmPrinc;
	}
	
	//Acciones
	
	public void actualizarStock(JTextPane jtp){
		
		
	}
}
