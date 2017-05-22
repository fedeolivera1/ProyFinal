package gpd.types;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class Fecha extends GregorianCalendar {

	private static final long serialVersionUID = 1L;
	public static final int AMD = 1;
	public static final int DMA = 2;
	public static final int AMDHM = 3; 
	public static final int AMDHMS = 4;
	public static final int HM = 5;
	public static final int HMS = 6;

	private static final String BARRA = "/";
	private static final String DP = ":";
	private static final String CERO = "0";
	private static final String SPC = " ";
	
	private Integer formato;
	
	
	/**
	 * setea una fecha por defecto
	 */
	public Fecha() {
	    complete();
	    setFormato(4);
	}
	
	/**
	 * 
	 * @param year
	 * @param month
	 * @param date
	 * setea una fecha con el formato anio, mes, dia
	 */
	public Fecha(int year, int month, int date) {
		super(year, month-1, date);
	    complete();
	    setFormato(AMD);
	}
	
	/**
	 * 
	 * @param year
	 * @param month
	 * @param date
	 * @param hour
	 * @param minute
	 * setea una fecha con el formato anio, mes, dia, hora y minuto
	 */
	public Fecha(int year, int month, int date, int hour, int minute) {
	    super(year, month-1, date, hour, minute);
	    complete();
	    setFormato(AMDHM);
	}
	
	/**
	 * 
	 * @param year
	 * @param month
	 * @param date
	 * @param hour
	 * @param minute
	 * @param second
	 * setea una fecha con el formato anio, mes, dia, hora, minuto y segundo
	 */
	public Fecha(int year, int month, int date, int hour, int minute, int second) {
	    super(year, month-1, date, hour, minute, second);
	    complete();
	    setFormato(AMDHMS);
	}
	
	/**
	 * 
	 * @param hour
	 * @param minute
	 * setea una fecha con hora y minuto (TIME)
	 */
	public Fecha(int hour, int minute) {
		super(Calendar.YEAR, Calendar.MONTH, Calendar.DAY_OF_MONTH, hour, minute);
		complete();
		setFormato(HM);
	}
	
	/**
	 * 
	 * @param time
	 * setea una fecha a partir de una tiempo en milisegundos
	 */
	public Fecha(Long time) {
		super.setTimeInMillis(time);
		complete();
		setFormato(AMDHMS);
	}
	
	/**
	 * 
	 * @return java.sql.Date
	 * retorna la instancia en el formato de java.sql.Date
	 */
	public java.sql.Date getDateSql() {
	    Fecha fec = new Fecha(get(1), get(2) + 1, get(5));
	    long tim = fec.getTimeInMillis();
	    java.sql.Date dateSql = new java.sql.Date(tim);
	    return dateSql;
	}
	
	/**
	 * 
	 * @return java.sql.Time
	 * retorna la instancia en el formato de java.sql.Time
	 */
	public java.sql.Time getTimeSql() {
		Fecha fec = new Fecha(get(11), get(12), get(13));
		long tim = fec.getTimeInMillis();
		java.sql.Time timeSql = new java.sql.Time(tim);
		return timeSql;
	}
	
	/**
	 * 
	 * @return java.sql.Timestamp
	 * retorna la instancia en el formato java.sql.Timestamp
	 */
	public java.sql.Timestamp getTimestampSql() {
	    Fecha fec = new Fecha(get(1), get(2) + 1, get(5), get(11), get(12), get(13));
	    long tim = fec.getTimeInMillis();
	    java.sql.Timestamp tsSql = new java.sql.Timestamp(tim);
	    return tsSql;
	}
	
	public int getAnio() {
	    return get(1);
	}

	public int getMes() {
		return get(2) + 1;
	}
	  
	public int getDia() {
	    return get(5);
	}
	  
	public int getHora() {
	    return get(11);
	}
	
	public int getMinuto() {
		return get(12);
	}
	
	public int getSegundo() {
		return get(13);
	}
	
	public Boolean esTimeStamp() {
		return getFormato().equals(new Integer(AMDHMS));
	}
	
	public Boolean esAMD() {
		return getFormato().equals(new Integer(AMD));
	}
	
	public Boolean esHM() {
		return getFormato().equals(new Integer(HM));
	}

	/**
	 * 
	 * @param fecha
	 * @param formato
	 * @return String
	 * genera toString a partir del formato recibido
	 */
	public String toString(Fecha fecha, int formato) {
		StringBuilder strFec = new StringBuilder();
		if(AMD == formato) {
			strFec.append(getAnio()).append(BARRA).append(cc(getMes())).append(BARRA).append(cc(getDia()));
		} else if(DMA == formato) {
			strFec.append(cc(getDia())).append(BARRA).append(cc(getMes())).append(BARRA).append(getAnio());
		} else if(AMDHM == formato) {
			strFec.append(getAnio()).append(BARRA).append(cc(getMes())).append(BARRA).append(cc(getDia())).append(SPC).append(
					cc(getHora())).append(DP).append(cc(getMinuto()));
		} else if(AMDHMS == formato) {
			strFec.append(getAnio()).append(BARRA).append(cc(getMes())).append(BARRA).append(cc(getDia())).append(SPC).append(
					cc(getHora())).append(DP).append(cc(getMinuto())).append(DP).append(cc(getSegundo()));
		} else if(HM == formato) {
			strFec.append(cc(getHora())).append(DP).append(cc(getMinuto()));
		} else if(HMS == formato) {
			strFec.append(cc(getHora())).append(DP).append(cc(getMinuto())).append(DP).append(cc(getSegundo()));
		}
		return strFec.toString();
	}
	
	private static String cc(int dato) {
		StringBuilder conCero = new StringBuilder();
		conCero = (String.valueOf(dato).length() == 1 ? conCero.append(CERO).append(String.valueOf(dato)) : conCero.append(String.valueOf(dato)));
		return conCero.toString();
		
	}
	
	/**
	 * 
	 * @return formato
	 * retorna el formato para el cual el new asigna automaticamente, o haya sido seteado manualmente
	 */
	public Integer getFormato() {
		return formato;
	}
	
	/**
	 * 
	 * @param formato
	 * setea formato para la clase. el new setea un formato por defecto dependiendo de
	 * a que constructor se llame. ATENCION!!!  > para los casos que no coincida, se deberá setear este
	 * dato manualmente en instancias de managers o persistencia.
	 */
	public void setFormato(Integer formato) {
		this.formato = formato;
	}
	

}
