package gpd.service.consumer;

import java.net.MalformedURLException;
import java.net.URL;

import javax.xml.namespace.QName;

import org.apache.log4j.Logger;

import gpd.exceptions.PresentacionException;
import gpd.util.ConfigDriver;
import gpw.webservice.proxy.WsGestPed;
import gpw.webservice.proxy.WsGestPed_Service;

public class ServiceConsumer {
	
	private static final Logger logger = Logger.getLogger(ServiceConsumer.class);
	private static final String WS_URL_END = "?wsdl";

	public WsGestPed consumirWebService(String nombreOp) throws PresentacionException {
		WsGestPed_Service proxy = null;
		WsGestPed iGestPed = null;
        try {
        	ConfigDriver cfgDrv = ConfigDriver.getConfigDriver();
			String WS_URL = String.valueOf(cfgDrv.getWsUrl());
			String WS_TN = String.valueOf(cfgDrv.getWsTargetNs());
			String WS_NAME = String.valueOf(cfgDrv.getWsName());
			
        	// Creamos el servicio a partir del WSDL
            URL wsdlLocation = new URL(WS_URL + WS_URL_END);
            proxy = new WsGestPed_Service(wsdlLocation, new QName(WS_TN, WS_NAME));
            iGestPed = proxy.getWsGestPed();
            // Añadimos capacidades de seguridad a la llamada
//            BindingProvider provider = (BindingProvider) port;
//            provider.getRequestContext().put(BindingProvider.USERNAME_PROPERTY, "wsgestped");   
//            provider.getRequestContext().put(BindingProvider.PASSWORD_PROPERTY, "pf2017");

//            System.out.println(port.servicioFuncional());
        } catch (MalformedURLException e) {
        	logger.fatal("Excepcion en ServiceConsumer > consumirWebService: " + e.getMessage(), e);
			throw new PresentacionException(e);
        } catch (Exception e ) {
        	logger.fatal("Excepcion en ServiceConsumer > consumirWebService: " + e.getMessage(), e);
			throw new PresentacionException(e);
        }
		return iGestPed;
	}
}
