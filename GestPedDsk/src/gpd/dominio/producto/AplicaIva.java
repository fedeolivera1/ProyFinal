package gpd.dominio.producto;

public enum AplicaIva {
	B("Basico", "iva_basico", 'B'),
	M("Minimo", "iva_minimo", 'M'),
	E("Exento", "iva_exento", 'X');
	
	private final String aplIvaDesc;
	private final String aplIvaProp;
	private final char asChar;
	
	AplicaIva(String aplIvaDesc, String aplIvaProp, char asChar) {
		this.aplIvaDesc = aplIvaDesc;
		this.aplIvaProp = aplIvaProp;
		this.asChar = asChar;
	}
	
	public String getAplIvaDesc() {
		return aplIvaDesc;
	}
	
	public String getAplIvaProp() {
		return aplIvaProp;
	}
	
	public char getAsChar() {
		return asChar;
	}
	
	public static AplicaIva getAplicaIvaPorChar(final char name) {
        for (AplicaIva aplIva : AplicaIva.values()) {
            if (aplIva.getAsChar() == name)
                return aplIva;
    	}
        return null;
    }
	
	 @Override
	 public String toString() {
		 return aplIvaDesc;
	 }
}
