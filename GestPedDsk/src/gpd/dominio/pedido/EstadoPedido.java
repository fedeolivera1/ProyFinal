package gpd.dominio.pedido;

public enum EstadoPedido {
	P("Pendiente", 'P'),
	R("Revision", 'R'),
	C("Confirmado", 'C'),
	A("Anulado", 'A'),
	X("Rechazado", 'X'),
	V("Vendido", 'V');
	
	private final String estadoPedido;
	private final char asChar;
	
	EstadoPedido(String estadoTran, char asChar) {
		this.estadoPedido = estadoTran;
		this.asChar = asChar;
	}

	public String getEstadoPedido() {
		return estadoPedido;
	}
	
	public char getAsChar() {
		return asChar;
	}

	public static EstadoPedido getEstadoPedidoPorChar(final char name) {
        for (EstadoPedido estado : EstadoPedido.values()) {
            if (estado.getAsChar() == name)
                return estado;
    	}
        return null;
    }
	
	 @Override
	 public String toString() {
		 return estadoPedido;
	 }
}
