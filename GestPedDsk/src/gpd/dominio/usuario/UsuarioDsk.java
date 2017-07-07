package gpd.dominio.usuario;

import java.io.Serializable;

public class UsuarioDsk implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private String nomUsu;
	private String pass;
	private TipoUsr tipoUsr;
	
	//Constructores
	public UsuarioDsk(String nomUsur, String pass, TipoUsr tipousu){
		this.nomUsu = nomUsur;
		this.pass = pass;
		this.tipoUsr = tipousu;		
	}
	
	public UsuarioDsk() {
	}
	
	//Set y get
	
	public String getNomUsu() {
		return nomUsu;
	}
	public void setNomUsu(String nomUsu) {
		this.nomUsu = nomUsu;
	}
	
	public String getPass() {
		return pass;
	}
	public void setPass(String pass) {
		this.pass = pass;
	}
	
	public TipoUsr getTipoUsr() {
		return tipoUsr;
	}
	public void setTipoUsr(TipoUsr tipoUsr) {
		this.tipoUsr = tipoUsr;
	}
	
	//Override para que muestre el nombreUsuario en combobox y otros
	@Override
	public String toString() {
	    return nomUsu;
	}

}
