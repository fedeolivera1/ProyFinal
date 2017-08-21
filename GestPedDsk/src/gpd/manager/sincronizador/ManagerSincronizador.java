package gpd.manager.sincronizador;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import gpd.dominio.pedido.EstadoPedido;
import gpd.dominio.pedido.Pedido;
import gpd.dominio.persona.Persona;
import gpd.dominio.persona.PersonaFisica;
import gpd.dominio.persona.PersonaJuridica;
import gpd.dominio.producto.Producto;
import gpd.dominio.util.EstadoSinc;
import gpd.dominio.util.Sinc;
import gpd.exceptions.NoInetConnectionException;
import gpd.exceptions.ParsersException;
import gpd.exceptions.PersistenciaException;
import gpd.exceptions.PresentacionException;
import gpd.exceptions.SincronizadorException;
import gpd.interfaces.pedido.IPersPedido;
import gpd.interfaces.pedido.IPersPedidoLinea;
import gpd.interfaces.persona.IPersPersona;
import gpd.interfaces.producto.IPersProducto;
import gpd.interfaces.producto.IPersTipoProd;
import gpd.interfaces.producto.IPersUnidad;
import gpd.persistencia.conector.Conector;
import gpd.persistencia.pedido.PersistenciaPedido;
import gpd.persistencia.pedido.PersistenciaPedidoLinea;
import gpd.persistencia.persona.PersistenciaPersona;
import gpd.persistencia.producto.PersistenciaProducto;
import gpd.persistencia.producto.PersistenciaTipoProd;
import gpd.persistencia.producto.PersistenciaUnidad;
import gpd.types.Fecha;
import gpd.ws.consumer.CnstService;
import gpd.ws.consumer.ControlAccesoInternet;
import gpd.ws.consumer.ServiceConsumer;
import gpd.ws.parsers.HlpResultProd;
import gpd.ws.parsers.ParserParamPedido;
import gpd.ws.parsers.ParserParamPersona;
import gpd.ws.parsers.ParserParamProducto;
import gpd.ws.parsers.ParserResultPedido;
import gpd.ws.parsers.ParserResultPersona;
import gpd.ws.parsers.ParserResultProducto;
import gpw.webservice.proxy.ErrorServicio;
import gpw.webservice.proxy.ParamObtPedidosNoSinc;
import gpw.webservice.proxy.ParamObtPersonasNoSinc;
import gpw.webservice.proxy.ParamRecPedidosASinc;
import gpw.webservice.proxy.ParamRecPersonasASinc;
import gpw.webservice.proxy.ParamRecProductosASinc;
import gpw.webservice.proxy.ResultObtPedidosNoSinc;
import gpw.webservice.proxy.ResultObtPersonasNoSinc;
import gpw.webservice.proxy.ResultPedidoASinc;
import gpw.webservice.proxy.ResultRecPedidosASinc;
import gpw.webservice.proxy.ResultRecPersonasASinc;
import gpw.webservice.proxy.ResultRecProductosASinc;
import gpw.webservice.proxy.WsGestPed;

public class ManagerSincronizador implements CnstService {

	private static final Logger logger = Logger.getLogger(ManagerSincronizador.class);
	private static IPersPersona interfacePersona;
	private static IPersTipoProd interfaceTipoProd;
	private static IPersUnidad interfaceUnidad;
	private static IPersProducto interfaceProducto;
	private static IPersPedido interfacePedido;
	private static IPersPedidoLinea interfacePedidoLinea;
	private static WsGestPed iGestPed;
	
	private static final int SINC_OK = 1;
	private static final String ESC = "\n";
	
	private static IPersPersona getInterfacePersona() {
		if(interfacePersona == null) {
			interfacePersona = new PersistenciaPersona();
		}
		return interfacePersona;
	}
	private static IPersTipoProd getInterfaceTipoProd() {
		if(interfaceTipoProd == null) {
			interfaceTipoProd = new PersistenciaTipoProd();
		}
		return interfaceTipoProd;
	}
	private static IPersUnidad getInterfaceUnidad() {
		if(interfaceUnidad == null) {
			interfaceUnidad = new PersistenciaUnidad();
		}
		return interfaceUnidad;
	}
	private static IPersProducto getInterfaceProducto() {
		if(interfaceProducto == null) {
			interfaceProducto = new PersistenciaProducto();
		}
		return interfaceProducto;
	}
	private static IPersPedido getInterfacePedido() {
		if(interfacePedido == null) {
			interfacePedido = new PersistenciaPedido();
		}
		return interfacePedido;
	}
	private static IPersPedidoLinea getInterfacePedidoLinea() {
		if(interfacePedidoLinea == null) {
			interfacePedidoLinea = new PersistenciaPedidoLinea();
		}
		return interfacePedidoLinea;
	}
	
	/**
	 * ****************************************************************************************************************************
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
	 * ****************************************************************************************************************************
	 */
	public String sincronizarPersonas(Fecha fechaDesde, Fecha fechaHasta) throws PresentacionException, NoInetConnectionException {
		StringBuilder sb = new StringBuilder();
		Integer persSincDsk = null;
		Integer persSincWeb = null;
		try {
			logger.info(">> GPD >> Se ingresa a sincronizarPersonas... ");
			sb.append("### Gestor de pedidos: inicia sincronización de personas. ### ").append(ESC);
			/*
			 * como primer paso se comprueba la conectividad con internet, sin esto la sincronizacion 
			 * no puede continuar en ambiente de prod
			 */
			ControlAccesoInternet.controlarConectividadInet();
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
				sb.append("Se retornan " + persSincWeb + " personas a sincronizar... ").append(ESC);
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
					sb.append("-- Se ").append(accion).append(" la persona ").append(tipoPers).append(" con ID [").append(String.valueOf(idPersona)).append("] en el sistema.").append(ESC);
				}
				
				/*
				 * Parte 2 de la sincronizacion: con las personas modificadas en la base local
				 * llama a webservice para CONFIRMAR dichas sincronizaciones
				 */
				if(!listaIdPersonasSinc.isEmpty()) {
					Fecha ultAct = new Fecha(Fecha.AMDHMS);
					persSincDsk = listaIdPersonasSinc.size();
					logger.info("#2 -- Inicia segunda parte de la sincronizacion de persona: confirmar las personas sincronizadas en dsk al sistema web.");
					ParamRecPersonasASinc paramRps = ParserParamPersona.parseParamRecPersonasSinc(listaIdPersonasSinc);
					//llamo al webservice > operacion recibirPersonasSinc (recibe personas y confirma sincronizacion)
					ResultRecPersonasASinc resultRps = iGestPed.recibirPersonasASinc(paramRps);
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
							sb.append("-- ERROR -- La Persona con ID [").append(String.valueOf(idPersona)).append("] ha tenido un error en la sincronización Web.").append(ESC);
						} else {
							//estado ok > procedo a actualizar localmente la sinc
							if(getInterfacePersona().actualizarSincPersona(idPersona, Sinc.S, ultAct) < SINC_OK) {
								errorSincDsk = true;
								logger.error("Ha habido un error de sincronizacion para la persona con ID [" + idPersona + "] en el sistema local.");
								sb.append("-- ERROR -- La Persona con ID [").append(String.valueOf(idPersona)).append("] ha tenido un error en la sincronización Local.").append(ESC);
							} else {
								logger.info("La Persona con ID [" + idPersona + "] ha retornado con sincronizacion OK.");
								sb.append("-- La Persona con ID [").append(String.valueOf(idPersona)).append("] ha retornado con sincronizacion OK.").append(ESC);
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
				logger.warn("El sincronizador no ha devuelto personas a sincronizar.");
				sb.append("El sincronizador no ha devuelto personas a sincronizar...").append(ESC);
			}
			sb.append("### Gestor de pedidos: finaliza sincronización de personas. ###").append(ESC);
		} catch (SincronizadorException | PersistenciaException | ParsersException e) {
			Conector.rollbackConn();
			logger.fatal("Excepcion en ManagerSincronizador > sincronizarPersona: " + e.getMessage(), e);
			throw new PresentacionException(e);
		}
		return sb.toString();
	}
	
	/**
	 * ****************************************************************************************************************************
	 * @param fechaDesde
	 * @param fechaHasta
	 * @return
	 * @throws PresentacionException
	 * ****************************************************************************************************************************
	 */
	public String sincronizarProductos(Fecha fechaDesde, Fecha fechaHasta) throws PresentacionException {
		StringBuilder sb = new StringBuilder();
		logger.info(">> GPD >> Se ingresa a sincronizarProductos... ");
		sb.append("### Gestor de pedidos: inicia sincronización de productos. ###").append(ESC);
		try {
			ControlAccesoInternet.controlarConectividadInet();

			ServiceConsumer srvCns = new ServiceConsumer();
			iGestPed = srvCns.consumirWebService();
			logger.info(">> GPD >> Invocacion a webservice correcta.");
			logger.info(">> GPD >> Prueba WS: " + iGestPed.servicioFuncional());
			
			Conector.getConn();
			/*
			 * se obtienen productos a sicronizar
			 */
			List<Producto> listaProdNoSinc = getInterfaceProducto().obtenerListaProductoNoSinc(fechaDesde, fechaHasta);

			if(listaProdNoSinc != null && !listaProdNoSinc.isEmpty()) {
				sb.append("Se retornan " + listaProdNoSinc.size() + " productos a sincronizar...").append(ESC);
				ParamRecProductosASinc param = ParserParamProducto.parseParamRecProductosASinc(listaProdNoSinc);
				ResultRecProductosASinc result = iGestPed.recibirProductosASinc(param);
				HlpResultProd hlpRp = ParserResultProducto.parseResultRecProductosASinc(result);
				if(hlpRp != null) {
					if(!hlpRp.getMapTipoProd().isEmpty()) {
						for(Map.Entry<Integer, EstadoSinc> entry : hlpRp.getMapTipoProd().entrySet()) {
							Integer idTp = entry.getKey();
							EstadoSinc estSinc = entry.getValue();
							if(EstadoSinc.O.equals(estSinc)) {
								logger.info("El tipo prod con id [" + idTp + "] devuelve sincronizacion ok, se actualiza en base local.");
								if(getInterfaceTipoProd().modificarSincTipoProd(idTp, Sinc.S) < SINC_OK) {
									logger.error("Ha habido un error de sincronizacion para el tipo prod con ID [" + idTp + "] en el sistema.");
									sb.append("-- ERROR -- El tipo prod con ID [").append(String.valueOf(idTp)).append("] ha tenido un error en la sincronización.").append(ESC);
								} else {
									logger.info("El tipo prod con ID [" + idTp + "] ha retornado con sincronizacion OK.");
									sb.append("-- El tipo prod con ID [").append(String.valueOf(idTp)).append("] se ha sincronizado correctamente.").append(ESC);
								}
							}
						}
					}
					if(!hlpRp.getMapUnidad().isEmpty()) {
						for(Map.Entry<Integer, EstadoSinc> entry : hlpRp.getMapUnidad().entrySet()) {
							Integer idUni = entry.getKey();
							EstadoSinc estSinc = entry.getValue();
							if(EstadoSinc.O.equals(estSinc)) {
								logger.info("La unidad con id [" + idUni + "] devuelve sincronizacion ok, se actualiza en base local.");
								if(getInterfaceUnidad().modificarSincUnidad(idUni, Sinc.S) < SINC_OK) {
									logger.error("Ha habido un error de sincronizacion para la unidad con ID [" + idUni + "] en el sistema.");
									sb.append("-- ERROR -- La unidad con ID [").append(String.valueOf(idUni)).append("] ha tenido un error en la sincronización.").append(ESC);
								} else {
									logger.info("La unidad con ID [" + idUni + "] ha retornado con sincronizacion OK.");
									sb.append("-- La unidad con ID [").append(String.valueOf(idUni)).append("] se ha sincronizado correctamente.").append(ESC);
								}
							}
						}
					}
					for(Map.Entry<Integer, EstadoSinc> entry : hlpRp.getMapProd().entrySet()) {
						Integer idProd = entry.getKey();
						EstadoSinc estSinc = entry.getValue();
						if(EstadoSinc.O.equals(estSinc)) {
							logger.info("El producto con id [" + idProd + "] devuelve sincronizacion ok, se actualiza en base local.");
							if(getInterfaceProducto().modificarSincProducto(idProd, Sinc.S) < SINC_OK) {
								logger.error("Ha habido un error de sincronizacion para el producto con ID [" + idProd + "] en el sistema.");
								sb.append("-- ERROR -- El producto con ID [").append(String.valueOf(idProd)).append("] ha tenido un error en la sincronización.").append(ESC);
							} else {
								logger.info("El producto con ID [" + idProd + "] ha retornado con sincronizacion OK.");
								sb.append("-- El producto con ID [").append(String.valueOf(idProd)).append("] se ha sincronizado correctamente.").append(ESC);
							}
						}
					}
					Conector.closeConn("sincronizarProductos");
				}
			} else {
				logger.warn("El sincronizador no ha devuelto productos a sincronizar.");
				sb.append("El sincronizador no ha devuelto productos a sincronizar...").append(ESC);
			}
			sb.append("### Gestor de pedidos: finaliza sincronización de productos. ###").append(ESC);
		} catch (SincronizadorException | PersistenciaException | NoInetConnectionException | ParsersException e) {
			Conector.rollbackConn();
			logger.fatal("Excepcion en ManagerSincronizador > sincronizarPersona: " + e.getMessage(), e);
			throw new PresentacionException(e);
		}
		return sb.toString();
	}
		
	/**
	 * ****************************************************************************************************************************
	 * metodo para sincronizar los pedidos. genera logueos para presentacion y para system.out. Consta de 2 pasos, el primero es
	 * ir a buscar los pedidos no SINC a la base web, y guardarlos en la base dsk. Luego se podrá modificar o generar la venta. Esto,
	 * mediante el segundo paso de la sincronizacion, se reflejará en la base web, y el usuario podrá ver que se confirmó, ver que
	 * se modificó o ver que se anuló
	 * @param fechaDesde
	 * @param fechaHasta
	 * @return
	 * @throws PresentacionException
	 * ****************************************************************************************************************************
	 */
	public String sincronizarPedidos(Fecha fechaDesde, Fecha fechaHasta) throws PresentacionException {
		StringBuilder sb = new StringBuilder();
		logger.info(">> GPD >> Se ingresa a sincronizarPedidos... ");
		sb.append("### Gestor de pedidos: inicia sincronización de pedidos. ###").append(ESC);
		try {
			ControlAccesoInternet.controlarConectividadInet();
			ServiceConsumer srvCns = new ServiceConsumer();
			iGestPed = srvCns.consumirWebService();
			logger.info(">> GPD >> Invocacion a webservice correcta.");
			logger.info(">> GPD >> Prueba WS: " + iGestPed.servicioFuncional());

			/*
			 * se realiza la obtención de pedidos de web hacia dsk
			 * para esto, se obtienen los pedidos web con estado 'P'
			 */
			ParamObtPedidosNoSinc param = ParserParamPedido.parseParamObtPedidosNoSinc(fechaDesde, fechaHasta);
			ResultObtPedidosNoSinc result = iGestPed.obtenerPedidosNoSinc(param);
			Conector.getConn();
			if(result.getErroresServ().isEmpty()) {
				List<Pedido> listaPedidosNoSinc = ParserResultPedido.parseResultPedidosNoSinc(result);
				Fecha ultAct = new Fecha(Fecha.AMDHMS);
				if(listaPedidosNoSinc != null && !listaPedidosNoSinc.isEmpty()) {
					for(Pedido pedido : listaPedidosNoSinc) {
						pedido.setUltAct(ultAct);
						if(getInterfacePedido().checkExistPedido(pedido)) {
							getInterfacePedido().modificarPedido(pedido);
							getInterfacePedidoLinea().eliminarListaPedidoLinea(pedido);
							getInterfacePedidoLinea().guardarListaPedidoLinea(pedido.getListaPedidoLinea());
							sb.append("El ACTUAL pedido con el ID [" + pedido.getPersona().getIdPersona() + " | " + pedido.getFechaHora().toString(Fecha.DMAHMS) + 
									"] se ha modificado correctamente.").append(ESC);
						} else {
							getInterfacePedido().guardarPedido(pedido);
							getInterfacePedidoLinea().guardarListaPedidoLinea(pedido.getListaPedidoLinea());
							sb.append("El NUEVO pedido con el ID [" + pedido.getPersona().getIdPersona() + " | " + pedido.getFechaHora().toString(Fecha.DMAHMS) + 
									"] se ha agregado correctamente.").append(ESC);
						}
					}
				}
			} else {
				for(ErrorServicio error : result.getErroresServ()) {
					Conector.rollbackConn();
					sb.append("-- ERROR -- P1: La sincronizacion de pedidos ha retornado error: " + error.getCodigo() 
								+ " - " + error.getDescripcion()).append(ESC);
				}
			}
			/*
			 * se realiza el envío de pedidos desde el dsk hacia la web
			 * para esto, obtengo pedidos WEB en estado 'R' (revisados)
			 */
			List<Pedido> listaPedidosASinc = getInterfacePedido().obtenerListaPedidoNoSincWeb(EstadoPedido.R, fechaDesde, fechaHasta);
			if(listaPedidosASinc != null && !listaPedidosASinc.isEmpty()) {
				sb.append("-Se proceden a sincronizar [" + listaPedidosASinc.size() + "] pedidos hacia el sistema WEB.").append(ESC);
				ParamRecPedidosASinc paramRpas = ParserParamPedido.parseParamRecPedidosASinc(listaPedidosASinc);
				ResultRecPedidosASinc resultRpas = iGestPed.recibirPedidosASinc(paramRpas);
				if(resultRpas != null && (resultRpas.getErroresServ() == null || resultRpas.getErroresServ().isEmpty())) {
					Fecha ultAct = new Fecha(Fecha.AMDHMS);
					for(ResultPedidoASinc resultPas : resultRpas.getListaPedidoSinc()) {
						if(resultPas.getErrorServ() == null) {
							Long idPersona = resultPas.getIdPersona();
							Fecha fechaHora = new Fecha(resultPas.getFechaHora(), Fecha.AMDHMS);
							Pedido pedSinc = getInterfacePedido().obtenerPedidoPorId(idPersona, fechaHora);
							pedSinc.setSinc(Sinc.S);
							pedSinc.setUltAct(ultAct);
							getInterfacePedido().modificarSincUltActPedido(pedSinc);
							sb.append("El pedido con el ID [" + idPersona + " | " + fechaHora.toString(Fecha.DMAHMS) + 
										"] se ha sincronizado correctamente.").append(ESC);
						} else {
							ErrorServicio error = resultPas.getErrorServ();
							sb.append("-El pedido sincronizado ha retornado error: " + error.getCodigo() 
									+ " - " + error.getDescripcion()).append(ESC);
						}
					}
				} else {
					for(ErrorServicio error : result.getErroresServ()) {
						Conector.rollbackConn();
						sb.append("-- ERROR -- P2: La sincronizacion de pedidos ha retornado error: " + error.getCodigo() 
									+ " - " + error.getDescripcion()).append(ESC);
					}
				}
			} else {
				sb.append("-No se obtuvieron pedidos a sincronizar hacia el sistema WEB.").append(ESC);
			}
			Conector.closeConn("sincronizarPedidos");
			sb.append("### Gestor de pedidos: finaliza sincronización de pedidos. ###").append(ESC);
		} catch (PersistenciaException | NoInetConnectionException | SincronizadorException | ParsersException e) {
			Conector.rollbackConn();
			logger.fatal("Excepcion en ManagerSincronizador > sincronizarPedidosDesdeWeb: " + e.getMessage(), e);
			throw new PresentacionException(e);
		}
		return sb.toString();
	}
	
}
