package gpd.dominio.persona;

public enum Sexo {
	M("Masculino", 'M'),
	F("Femenino", 'F');
	
	private final String sexo;
	private final char asChar;
	
	Sexo(String sexo, char asChar) {
		this.sexo = sexo;
		this.asChar = asChar;
	}

	public String getSexo() {
		return sexo;
	}
	
	public char getAsChar() {
		return asChar;
	}
	
}
