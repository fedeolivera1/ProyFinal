package gpd.manager.sincronizador;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import gpd.dominio.persona.Persona;
import gpd.dominio.persona.PersonaFisica;
import gpd.dominio.persona.PersonaJuridica;
import gpd.dominio.util.EstadoSinc;
import gpd.dominio.util.Sinc;
import gpd.exceptions.NoInetConnectionException;
import gpd.exceptions.PersistenciaException;
import gpd.exceptions.PresentacionException;
import gpd.exceptions.SincronizadorException;
import gpd.interfaces.persona.IPersPersona;
import gpd.persistencia.conector.Conector;
import gpd.persistencia.persona.PersistenciaPersona;
import gpd.types.Fecha;
import gpd.ws.consumer.CnstService;
import gpd.ws.consumer.ControlAccesoInternet;
import gpd.ws.consumer.ServiceConsumer;
import gpd.ws.parsers.ParserParamPersona;
import gpd.ws.parsers.ParserResultPersona;
import gpw.webservice.proxy.ParamObtPersonasNoSinc;
import gpw.webservice.proxy.ParamRecPersonasSinc;
import gpw.webservice.proxy.ResultObtPersonasNoSinc;
import gpw.webservice.proxy.ResultRecPersonasSinc;
import gpw.webservice.proxy.WsGestPed;

public class ManagerSincronizador implements CnstService {

	private static final Logger logger = Logger.getLogger(ManagerSincronizador.class);
	private static IPersPersona interfacePersona;
	private static WsGestPed iGestPed;
	
	private static IPersPersona getInterfacePersona() {
		if(interfacePersona == null) {
			interfacePersona = new PersistenciaPersona();
		}
		return interfacePersona;
	}
	
	/**
	 * metodo para sincronizar personas. genera varios pasos, logueando y generando un informe que luego se 
	 * devuelve a presentacion para ver como resultó
	 * > el primer paso consta en obtener el servicio, e ir a buscar las personas que no estan sincronizadas
	 * en la base de datos dbWeb. En caso de devolver personas que requieren ser sincronizadas a dbDsk, se estudian
	 * para ver si son actualizaciones o altas. 
	 * > el segundo paso consta en enviar dichas actualizaciones por el servicio para que sean impactadas en la base
	 * dbWeb (se marcarán como sinc). El retorno devolverá el estado de cada actualización para que por ultimo paso
	 * se finalice marcando en dbDsk como sinc.
	 * @param fechaDesde
	 * @param fechaHasta
	 * @return
	 * @throws PresentacionException
	 */
	public String sincronizarPersonas(Fecha fechaDesde, Fecha fechaHasta) throws PresentacionException, NoInetConnectionException {
		StringBuilder sb = new StringBuilder();
		Integer persSincDsk = null;
		Integer persSincWeb = null;
		try {
			logger.info(">> GPD >> Se ingresa a sincronizarPersonas... ");
			sb.append("### Gestor de pedidos: inicia sincronización de personas. ### \n");
			/*
			 * como primer paso se comprueba la conectividad con internet, sin esto la sincronizacion 
			 * no puede continuar en ambiente de prod
			 */
			ControlAccesoInternet.controlarConectividadInet();
//			if(ControlAccesoInternet.controlarConectividadInet()) {
//				sb.append("ERROR: No se pudo establecer conectividad con Internet. Compruebe su conexion. \n");
//			} else {
				ServiceConsumer srvCns = new ServiceConsumer();
				iGestPed = srvCns.consumirWebService();
				logger.info(">> GPD >> Invocacion a webservice correcta.");
				logger.info(">> GPD >> Prueba WS: " + iGestPed.servicioFuncional());
				
				Conector.getConn();
				ParamObtPersonasNoSinc paramOpns = ParserParamPersona.parseParamObtPersNoSinc(fechaDesde, fechaHasta);
				//llamo al webservice > operacion obtenerPersonasNoSinc
				ResultObtPersonasNoSinc resultOpns = iGestPed.obtenerPersonasNoSinc(paramOpns);
				List<Persona> listaPersonaResult = ParserResultPersona.parseResultObtPersNoSinc(resultOpns);
				List<Long> listaIdPersonasSinc = new ArrayList<Long>();
				
				/*
				 * Parte 1 de la sincronizacion: consulto webservice de sistema web para obtener personas no sincronizadas con
				 * el ambiente local.
				 * Luego de esto, se realizan las altas o modificaciones correspondientes para cada uno de los datos devueltos
				 * por el webservice.
				 */
				logger.info("#1 -- Inicia primera parte de la sincronizacion de persona: obtener Personas a sincronizar.");
				if(listaPersonaResult != null && !listaPersonaResult.isEmpty()) {
					persSincWeb = listaPersonaResult.size();
					sb.append("Se retornan " + persSincWeb + " personas a sincronizar... \n");
					for(Persona pers : listaPersonaResult) {
						Long idPersona = pers.getIdPersona();
						String accion = "";
						String tipoPers = "";
						if(pers instanceof PersonaFisica) {
							tipoPers = "fisica";
							if(getInterfacePersona().checkExistPersona(idPersona)) {
								getInterfacePersona().modificarPersFisica((PersonaFisica) pers);
								accion = "modifica";
							} else {
								getInterfacePersona().guardarPersFisica((PersonaFisica) pers);
								accion = "agrega";
							}
							listaIdPersonasSinc.add(idPersona);
						} else if(pers instanceof PersonaJuridica) {
							tipoPers = "juridica";
							if(getInterfacePersona().checkExistPersona(idPersona)) {
								getInterfacePersona().modificarPersJuridica((PersonaJuridica) pers);
								accion = "modifica";
							} else {
								getInterfacePersona().guardarPersJuridica((PersonaJuridica) pers);
								accion = "agrega";
							}
							listaIdPersonasSinc.add(idPersona);
						}
						sb.append("-- Se ").append(accion).append(" la persona ").append(tipoPers).append(" con ID [").append(String.valueOf(idPersona)).append("] en el sistema. \n");
					}
					
					/*
					 * Parte 2 de la sincronizacion: con las personas modificadas en la base local
					 * llama a webservice para CONFIRMAR dichas sincronizaciones
					 */
					if(!listaIdPersonasSinc.isEmpty()) {
						Fecha ultAct = new Fecha(Fecha.AMDHMS);
						persSincDsk = listaIdPersonasSinc.size();
						logger.info("#2 -- Inicia segunda parte de la sincronizacion de persona: confirmar las personas sincronizadas en dsk al sistema web.");
						ParamRecPersonasSinc paramRps = ParserParamPersona.parseParamRecPersonasSinc(listaIdPersonasSinc);
						//llamo al webservice > operacion recibirPersonasSinc (recibe personas y confirma sincronizacion)
						ResultRecPersonasSinc resultRps = iGestPed.recibirPersonasSinc(paramRps);
						HashMap<Long, Integer> hmResultRps = (HashMap<Long, Integer>) ParserResultPersona.parseResultRecPersonasSinc(resultRps);
						Boolean errorSincWeb = false;
						Boolean errorSincDsk = false;
						
						//se recorre el map con los resultados que devuelve el servicio de la actualizacion
						for(Map.Entry<Long, Integer> entry : hmResultRps.entrySet()) {
							Long idPersona = entry.getKey();
							Integer estadoSinc = entry.getValue();
							//estado del retorno de la persona web
							if(EstadoSinc.E.equals(estadoSinc)) {
								//estado en error
								errorSincWeb = true;
								logger.error("Ha habido un error de sincronizacion para la persona con ID [" + idPersona + "] en el sistema web.");
								sb.append("-- ERROR -- La Persona con ID [").append(String.valueOf(idPersona)).append("] ha tenido un error en la sincronización Web. \n");
							} else {
								//estado ok > procedo a actualizar localmente la sinc
								if(getInterfacePersona().actualizarSincPersona(idPersona, Sinc.S, ultAct) < 1 ) {
									errorSincDsk = true;
									logger.error("Ha habido un error de sincronizacion para la persona con ID [" + idPersona + "] en el sistema local.");
									sb.append("-- ERROR -- La Persona con ID [").append(String.valueOf(idPersona)).append("] ha tenido un error en la sincronización Local. \n");
								} else {
									logger.info("La Persona con ID [" + idPersona + "] ha retornado con sincronizacion OK.");
									sb.append("-- La Persona con ID [").append(String.valueOf(idPersona)).append("] ha retornado con sincronizacion OK. \n");
								}
							}
						}
						/*
						 * se agrega warning a logueo si la cantidad de personas devueltas por el servicio a sincronizar
						 * no coincidiera con la cantidad de personas manejadas localmente 
						 */
						if(!persSincWeb.equals(persSincDsk)) {
							logger.warn("La cantidad de personas a sincronizar no coincide...");
							logger.warn("-Cantidad de personas retornadas desde web: " + persSincWeb);
							logger.warn("-Cantidad de personas sincronizadas en dsk: " + persSincDsk);
						}
						/*
						 * se controlan para todas las personas recibidas desde el servicio que retornen sin error
						 * en caso de errores, se rollbackea la transaccion del lado dsk.
						 */
						if(!errorSincWeb && !errorSincDsk) {
							Conector.closeConn("sincronizarPersona");
							logger.debug("Se cierra conexion, no han existido errores.");
						} else {
							Conector.rollbackConn();
							logger.error("Se invoca un rollback ya que se han detectado errores en el retorno del servicio o en la actualizacion local.");
							logger.error("Error invocado por: " + (errorSincWeb ? "SINC WEB " : "") + (errorSincDsk ? "SINC DSK" : "") );
						}
					} else {
						Conector.rollbackConn();
						logger.error("El sincronizador hace rollback por no sincronizar las personas devueltas del servicio con el ambiente local.");
					}
					
				} else {
					logger.info("El sincronizador no ha devuelto personas a sincronizar.");
					sb.append("El sincronizador no ha devuelto personas a sincronizar... \n");
				}
//			}
			sb.append("### Gestor de pedidos: finaliza sincronización de personas. ###");
		} catch (SincronizadorException | PersistenciaException e) {
			logger.fatal("Excepcion en ManagerSincronizador > sincronizarPersona: " + e.getMessage(), e);
			throw new PresentacionException(e);
		}
		return sb.toString();
	}
	
	public String sincronizarProductos(Fecha fechaDesde, Fecha fechaHasta) throws PresentacionException {
		StringBuilder sb = new StringBuilder();
		logger.info(">> GPD >> Se ingresa a sincronizarProductos... ");
		sb.append("### Gestor de pedidos: inicia sincronización de productos. ### \n");
		try {
			if(!ControlAccesoInternet.controlarConectividadInet()) {
				sb.append("ERROR: No se pudo establecer conectividad con Internet. Compruebe su conexion. \n");
			} else {
				ServiceConsumer srvCns = new ServiceConsumer();
				iGestPed = srvCns.consumirWebService();
				logger.info(">> GPD >> Invocacion a webservice correcta.");
				logger.info(">> GPD >> Prueba WS: " + iGestPed.servicioFuncional());
				
			}
			sb.append("### Gestor de pedidos: finaliza sincronización de productos. ###");
		} catch (SincronizadorException /*| PersistenciaException*/ | NoInetConnectionException e) {
			logger.fatal("Excepcion en ManagerSincronizador > sincronizarPersona: " + e.getMessage(), e);
			throw new PresentacionException(e);
		}
		return sb.toString();
	}
		
	
}
