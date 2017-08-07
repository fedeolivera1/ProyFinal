package gpd.types;

import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.GregorianCalendar;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeConstants;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

public class Fecha extends GregorianCalendar {

	private static final long serialVersionUID = 1L;
	public static final int AMD = 1;
	public static final int DMA = 2;
	public static final int AMDHM = 3; 
	public static final int AMDHMS = 4;
	public static final int HM = 5;
	public static final int HMS = 6;
	public static final int DMAHMS = 7;

	private static final String BARRA = "/";
	private static final String DP = ":";
	private static final String CERO = "0";
	private static final String SPC = " ";
	
	private Integer formato;
	
	
	/**
	 * setea una fecha por defecto
	 */
	public Fecha(int formato) {
	    complete();
	    setFormato(formato);
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
	 * setea una fecha a partir de un tiempo (long) en milisegundos
	 */
	public Fecha(Long time) {
		if(time != null) {
			super.setTimeInMillis(time);
			complete();
			setFormato(AMDHMS);
		}
	}
	
	/**
	 * 
	 * @param time sql
	 * setea una fecha a partir de un java.sql.Time en milisegundos
	 */
	public Fecha(Time time) {
		if(time != null) {
			super.setTimeInMillis(time.getTime());
			complete();
			setFormato(HMS);
		}
	}
	
	/**
	 * 
	 * @param java.util.Date
	 * setea una fecha a partir de un java.util.Date
	 */
	public Fecha(java.util.Date date) {
		if(date != null) {
			super.setTimeInMillis(date.getTime());
			complete();
			setFormato(AMD);
		}
	}
	
	/**
	 * 
	 * @param date sql
	 * setea una fecha a partir de un java.sql.Date en milisegundos
	 */
	public Fecha(Date date) {
		if(date != null) {
			super.setTimeInMillis(date.getTime());
			complete();
			setFormato(AMD);
		}
	}
	
	/**
	 * 
	 * @param timestamp sql
	 * setea una fecha a partir de un java.sql.Timestamp en milisegundos
	 */
	public Fecha(Timestamp timestamp) {
		if(timestamp != null) {
			super.setTimeInMillis(timestamp.getTime());
			complete();
			setFormato(AMDHMS);
		}
	}
	
	/**
	 * 
	 * @param XMLGregorianCalendar
	 * setea una fecha a partir de un XMLGregorianCalendar por datos
	 */
	public Fecha(XMLGregorianCalendar xmlGc, int formato) {
		if(xmlGc != null) {
			GregorianCalendar gc = xmlGc.toGregorianCalendar();
			super.setTime(gc.getTime());
			setFormato(formato);
		}
	}
	
	/**
	 * 
	 * @return java.sql.Date
	 * retorna la instancia en el formato de java.sql.Date
	 */
	public java.sql.Date getDateSql() {
		Fecha fec = new Fecha(getAnio(), getMes(), getDia());
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
		Fecha fec = new Fecha(getHora(), getMinuto());
		fec.set(SECOND, getSegundo());
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
	
	/**
	 * 
	 * @return XMLGregorianCalendar
	 * retorna la instancia en el formato XMLGregorianCalendar
	 */
	public XMLGregorianCalendar getAsXMLGregorianCalendar(int formato) {
		Fecha fec;
		if(formato == Fecha.AMDHMS) {
			fec = new Fecha(getAnio(), getMes(), getDia(), getHora(), getMinuto(), getSegundo());
		} else {
			fec = new Fecha(getAnio(), getMes(), getDia());
		}
		XMLGregorianCalendar xmlgc = null;
		GregorianCalendar gc = new GregorianCalendar();
		gc.setTime(fec.getTime());
		try {
			xmlgc = DatatypeFactory.newInstance().newXMLGregorianCalendar(gc);
			xmlgc.setTimezone(DatatypeConstants.FIELD_UNDEFINED);
		} catch (DatatypeConfigurationException e) {
			e.printStackTrace();
		}
		return xmlgc;
	}
	
	/**
	 * 
	 * @return XMLGregorianCalendar
	 * retorna la instancia en el formato XMLGregorianCalendar
	 */
	public XMLGregorianCalendar getHoraAsXMLGregorianCalendar() {
		Fecha fec = new Fecha(getHora(), getMinuto());
		fec.set(SECOND, getSegundo());
		XMLGregorianCalendar xmlgc = null;
		GregorianCalendar gc = new GregorianCalendar();
		gc.setTime(fec.getTime());
		try {
			xmlgc = DatatypeFactory.newInstance().newXMLGregorianCalendar(gc);
			xmlgc.setTimezone(DatatypeConstants.FIELD_UNDEFINED);
		} catch (DatatypeConfigurationException e) {
			e.printStackTrace();
		}
		return xmlgc;
	}
	
	/**
	 * 
	 * @param formato
	 * @return numerico con fecha en formato establecido
	 * permite generar un numero de la fecha ej (19960523)
	 */
	public long getAsNumber(int formato) {
	    long salida = 0;
	    try {
	    	if(formato == AMDHMS) {
	    		salida = getAnio();
	    		salida = salida * 100 + getMes();
	    		salida = salida * 100 + getDia();
	    		salida = salida * 100 + getHora();
	    		salida = salida * 100 + getMinuto();
	    		salida = salida * 100 + getSegundo();
	    	} else {
	    		salida = getAnio();
	    		salida = salida * 100 + getMes();
	    		salida = salida * 100 + getDia();
	    	}
	    } catch (Exception e) {
	    	return -1;
	    }
	    return salida;
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
	
	public Boolean esHMS() {
		return getFormato().equals(new Integer(HMS));
	}

	/**
	 * 
	 * @param fecha
	 * @param formato
	 * @return String
	 * genera toString a partir del formato recibido
	 */
	public String toString(int formato) {
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
		} else if(DMAHMS == formato) {
			strFec.append(getDia()).append(BARRA).append(cc(getMes())).append(BARRA).append(cc(getAnio())).append(SPC).append(
					cc(getHora())).append(DP).append(cc(getMinuto())).append(DP).append(cc(getSegundo()));
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

	@Override
	public String toString() {
		return toString(Fecha.DMAHMS);
	}
	

}
