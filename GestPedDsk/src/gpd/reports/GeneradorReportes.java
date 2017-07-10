package gpd.reports;

import java.io.InputStream;

import org.apache.log4j.Logger;

import gpd.persistencia.conector.Conector;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.view.JasperViewer;

public class GeneradorReportes {
	
	private static final Logger logger = Logger.getLogger(GeneradorReportes.class);
	private static String EXT_JR = ".jrxml";
	private static String FILE_SEP = "/";
	
	public static void abrirReporte(String archivo) {
        try {
        	InputStream inputReport = GeneradorReportes.class.getResourceAsStream(FILE_SEP + archivo + EXT_JR);
            JasperReport report = JasperCompileManager.compileReport(inputReport);
            
            JasperPrint jasperPrint = JasperFillManager.fillReport(report, null, Conector.devolverConnection());
 
            JasperViewer.viewReport(jasperPrint, false);
        } catch (JRException ex) {
        	logger.error("Excepcion de Jasper Reports: " + ex.getMessage(), ex);
        } catch (Exception e) {
        	logger.error("Excepcion no controlada de GeneradorReportes: " + e.getMessage(), e);
        	System.out.println(e.getMessage());
    	}
    }
}
