package gpd.ws.consumer;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.namespace.QName;
import javax.xml.ws.BindingProvider;
import javax.xml.ws.Service;
import javax.xml.ws.WebServiceException;
import javax.xml.ws.handler.MessageContext;

import org.apache.log4j.Logger;

import gpd.exceptions.SincronizadorException;
import gpd.util.ConfigDriver;
import gpw.webservice.proxy.WsGestPed;

public class ServiceConsumer {
	
	private static final Logger logger = Logger.getLogger(ServiceConsumer.class);
	private static final String WS_URL_END = "?wsdl";

	public WsGestPed consumirWebService() throws SincronizadorException {
//		WsGestPed_Service proxy = null;
		WsGestPed iGestPed = null;
        try {
        	ConfigDriver cfgDrv = ConfigDriver.getConfigDriver();
			String WS_URL = String.valueOf(cfgDrv.getWsUrl());
			String WS_TN = String.valueOf(cfgDrv.getWsTargetNs());
			String WS_NAME = String.valueOf(cfgDrv.getWsName());
			
        	// Creamos el servicio a partir del WSDL
            URL wsdlLocation = new URL(WS_URL + WS_URL_END);
            Service proxy = Service.create(wsdlLocation, new QName(WS_TN, WS_NAME));
            iGestPed = proxy.getPort(WsGestPed.class);
            // Añadimos capacidades de seguridad a la llamada
            Map<String, Object> requestContext = ((BindingProvider) iGestPed).getRequestContext();
            
            Map<String, List<String>> requestHeaders = new HashMap<String, List<String>>();
            requestHeaders.put("username", Collections.singletonList(cfgDrv.getWsUsrName()));
            requestHeaders.put("password", Collections.singletonList(cfgDrv.getWsPassWd()));
            requestContext.put(MessageContext.HTTP_REQUEST_HEADERS, requestHeaders);
        } catch (MalformedURLException | WebServiceException e) {
        	logger.fatal("Excepcion en ServiceConsumer > consumirWebService: " + e.getMessage(), e);
			throw new SincronizadorException(e);
        } catch (Exception e) {
        	logger.fatal("Excepcion en ServiceConsumer > consumirWebService: " + e.getMessage(), e);
			throw new SincronizadorException(e);
        }
		return iGestPed;
	}
}
