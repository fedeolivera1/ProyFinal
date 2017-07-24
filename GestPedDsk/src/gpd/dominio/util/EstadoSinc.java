package gpd.dominio.util;

public enum EstadoSinc {
	O("Sinc OK", 0),
	E("Sinc Error", 1);
	
	private final String sinc;
	private final Integer asInt;
	
	EstadoSinc(String sinc, Integer asInt) {
		this.sinc = sinc;
		this.asInt = asInt;
	}

	public String getSinc() {
		return sinc;
	}
	
	public Integer getAsInt() {
		return asInt;
	}
	
}
