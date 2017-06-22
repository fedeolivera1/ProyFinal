package gpd.util;

public class KeyMapLp {

	private Long idPersona;
	private Long fechaHora;
	private Integer idProducto;
	
	
	public KeyMapLp(Long idPersona, Long fechaHora, Integer idProducto) {
		super();
		this.idPersona = idPersona;
		this.fechaHora = fechaHora;
		this.idProducto = idProducto;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result 
				+ ((idPersona == null) ? 0 : idPersona.hashCode());
		result = prime * result
				+ ((fechaHora == null) ? 0 : fechaHora.hashCode());
		result = prime * result
				+ ((idProducto == null) ? 0 : idProducto.hashCode());
		return result;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		if (this == obj) {
			return true;
		}
		if (this.getClass() != obj.getClass()) {
			return false;
		}
		if (this.idPersona == null && this.fechaHora == null && this.idProducto == null) {
			return false;
		}
		KeyMapLp mkp = (KeyMapLp) obj;
		Boolean retorno = false;
		if(this.idPersona != null) {
			retorno = this.idPersona.equals(mkp.getIdPersona());
		}
		if(this.fechaHora != null) {
			retorno = this.fechaHora.equals(mkp.getFechaHora());
		}
		if(this.idProducto != null) {
			retorno = this.idProducto.equals(mkp.getIdProducto()); 
		}
		return retorno;
	}
	
	public Long getIdPersona() {
		return idPersona;
	}
	public void setIdPersona(Long idPersona) {
		this.idPersona = idPersona;
	}
	
	public Long getFechaHora() {
		return fechaHora;
	}
	public void setFechaHora(Long fechaHora) {
		this.fechaHora = fechaHora;
	}
	
	public Integer getIdProducto() {
		return idProducto;
	}
	public void setIdProducto(Integer idProducto) {
		this.idProducto = idProducto;
	}
	
}
