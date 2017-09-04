package gpd.ws.consumer;

import java.math.BigInteger;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
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
import gpd.exceptions.WsException;
import gpd.util.ConfigDriver;
import gpw.webservice.proxy.ResultServFuncional;
import gpw.webservice.proxy.WsGestPed;

public class ServiceConsumer {
	
	private static final Logger logger = Logger.getLogger(ServiceConsumer.class);
	private static final String WS_URL_END = "?wsdl";

	public WsGestPed consumirWebService() throws SincronizadorException, WsException {
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
            requestHeaders.put("ws_username", Collections.singletonList(cfgDrv.getWsUsrName()));
            //cifro y agrego la password
            requestHeaders.put("ws_password", Collections.singletonList(getMD5(cfgDrv.getWsPassWd())));
            requestContext.put(MessageContext.HTTP_REQUEST_HEADERS, requestHeaders);
            //se invoca la operacion de servicio funcional para validar credenciales y comprobar correcta funcionalidad
            ResultServFuncional resultSf = iGestPed.servFuncional();
            if(resultSf.getError() != null) {
            	throw new WsException("Error al invocar el webservice WsGestPed: " + resultSf.getError().getCodigo() + " - " + resultSf.getError().getDescripcion());
            }
        } catch (MalformedURLException | WebServiceException e) {
        	logger.fatal("Excepcion en ServiceConsumer > consumirWebService: " + e.getMessage(), e);
			throw new SincronizadorException(e);
        } catch (WsException e) {
        	logger.error("Excepcion en ServiceConsumer > consumirWebService al verificar funcionalidad: " + e.getMessage(), e);
        	throw e;
        } catch (Exception e) {
        	logger.fatal("Excepcion en ServiceConsumer > consumirWebService: " + e.getMessage(), e);
			throw new SincronizadorException(e);
        }
		return iGestPed;
	}
	
	/**
	 * metodo que transforma una password sin cifrar a una cifrada con el metodo MD5. 
	 * @param input
	 * @return string con hash md5 de password
	 */
	private static String getMD5(String input) {
		 try {
			 MessageDigest md = MessageDigest.getInstance("MD5");
			 byte[] messageDigest = md.digest(input.getBytes());
			 BigInteger number = new BigInteger(1, messageDigest);
			 String hashtext = number.toString(16);
		 
			 while (hashtext.length() < 32) {
			 	hashtext = "0" + hashtext;
		 	}
		 	return hashtext;
	 	}
		catch (NoSuchAlgorithmException e) {
			logger.fatal("Excepcion en ServiceConsumer > getMD5 al generar hash MD5: " + e.getMessage(), e);
			throw new RuntimeException(e);
		}
	}
}
