package gpd.manager.sincronizador;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

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
import gpd.exceptions.WsException;
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

public class ManagerSincronizador {

	private static final Logger logger = Logger.getLogger(ManagerSincronizador.class);
	private static IPersPersona interfacePersona;
	private static IPersTipoProd interfaceTipoProd;
	private static IPersUnidad interfaceUnidad;
	private static IPersProducto interfaceProducto;
	private static IPersPedido interfacePedido;
	private static IPersPedidoLinea interfacePedidoLinea;
	
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
	 * Caller STANDALONE del sinc de personas que autogestiona su propia Conexion
	 * (llamadas independientes) < autogenera Connection
	 * @param fechaDesde
	 * @param fechaHasta
	 * @return
	 * @throws PresentacionException
	 */
	public String sincronizarPersonas(Fecha fechaDesde, Fecha fechaHasta) throws PresentacionException, WsException {
		try (Connection conn = Conector.getConn()) {
			try {
				/*
				 * como primer paso se comprueba la conectividad con internet y se consume el servicio
				 * con las credenciales del config
				 */
				ControlAccesoInternet.controlarConectividadInet();
				ServiceConsumer srvCns = new ServiceConsumer();
				WsGestPed iGestPed = srvCns.consumirWebService();
				logger.info("<< GPD >> Invocacion a webservice correcta.");
				/**/
				String respuesta = sincronizarPersonasNoConn(conn, iGestPed, fechaDesde, fechaHasta);
				Conector.commitConn(conn);
				return respuesta;
			} catch (NoInetConnectionException e) {
				Conector.rollbackConn(conn);
				logger.fatal("Excepcion en ManagerSincronizador > sincronizarPersonas: " + e.getMessage(), e);
				throw new PresentacionException(e);
			} catch (WsException e) {
				Conector.rollbackConn(conn);
				throw e;
			} catch (Exception e) {
				Conector.rollbackConn(conn);
				logger.fatal("Excepcion INVOCADA desde metodo noConn en ManagerSincronizador > sincronizarPersonas: " + e.getMessage(), e);
				throw new PresentacionException(e);
			}
		} catch (SQLException e) {
			logger.fatal("Excepcion al cerrar la conexion en ManagerSincronizador > sincronizarPersonas: " + e.getMessage(), e);
			throw new PresentacionException(e);
		}
	}
	/**
	 * ****************************************************************************************************************************
	 * SINC PERSONAS 
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
	 * <<<<< RECIBE CONNECTION - NO AUTOGENERA >>>>>
	 * ****************************************************************************************************************************
	 */
	public String sincronizarPersonasNoConn(Connection conn, WsGestPed iGestPed, Fecha fechaDesde, Fecha fechaHasta) throws PresentacionException, NoInetConnectionException, WsException {
		StringBuilder sb = new StringBuilder();
		logger.info("<< GPD >> Se ingresa a sincronizarPersonas... ");
		sb.append("### Gestor de pedidos: inicia sincronización de personas. ###").append(ESC);
		Integer persSincDsk = null;
		Integer persSincWeb = null;
		try {
			ParamObtPersonasNoSinc paramOpns = ParserParamPersona.parseParamObtPersNoSinc(fechaDesde, fechaHasta);
			// <<< llamo al WS >>> > operacion obtenerPersonasNoSinc
			ResultObtPersonasNoSinc resultOpns = iGestPed.obtenerPersonasNoSinc(paramOpns);
			// <<< end WS >>>
			if(resultOpns.getErroresServ() != null && !resultOpns.getErroresServ().isEmpty()) {
				for(ErrorServicio error : resultOpns.getErroresServ()) {
					sb.append("ERROR SERVICIO: " + error.getCodigo() + " - " + error.getDescripcion()).append(ESC);
				}
			} else {
				List<Persona> listaPersonaResult = ParserResultPersona.parseResultObtPersNoSinc(conn, resultOpns);
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
						Integer resultado = 0;
						/*
						 * para cada persona se obtiene el tipo, y se consulta si existe o no, para modificar o agregar.
						 * luego de esto, dependiendo del resultado que retorna la base de datos, se agrega a la lista que
						 * va a confirmar en el servicio la sincronizacion.
						 */
						if(pers instanceof PersonaFisica) {
							tipoPers = "fisica";
							if(getInterfacePersona().checkExistPersona(conn, idPersona)) {
								resultado = getInterfacePersona().modificarPersFisica(conn, (PersonaFisica) pers);
								accion = "modifica";
							} else {
								resultado = getInterfacePersona().guardarPersFisica(conn, (PersonaFisica) pers);
								accion = "agrega";
							}
							if(resultado > 0) {
								listaIdPersonasSinc.add(idPersona);
								sb.append("-- Se ").append(accion).append(" la persona ").append(tipoPers).append(" con ID [").append(String.valueOf(idPersona)).append("] en el sistema.").append(ESC);
							} else {
								logger.warn("Hubo un problema al " + accion + " la persona con ID: " + idPersona + ". No se agrega a la lista de confirmados.");
								sb.append("Hubo un problema al " + accion + " la persona con ID [" + idPersona + "]. No se agrega a la lista de confirmados.");
							}
						} else if(pers instanceof PersonaJuridica) {
							tipoPers = "juridica";
							if(getInterfacePersona().checkExistPersona(conn, idPersona)) {
								resultado = getInterfacePersona().modificarPersJuridica(conn, (PersonaJuridica) pers);
								accion = "modifica";
							} else {
								resultado = getInterfacePersona().guardarPersJuridica(conn, (PersonaJuridica) pers);
								accion = "agrega";
							}
							if(resultado > 0) {
								listaIdPersonasSinc.add(idPersona);
								sb.append("-- Se ").append(accion).append(" la persona ").append(tipoPers).append(" con ID [").append(String.valueOf(idPersona)).append("] en el sistema.").append(ESC);
							} else {
								logger.warn("Hubo un problema al " + accion + " la persona con ID: " + idPersona);
								sb.append("Hubo un problema al " + accion + " la persona con ID [" + idPersona + "]. No se agrega a la lista de confirmados.");
							}
						}
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
						// <<< llamo al WS >>> > operacion recibirPersonasSinc (recibe personas y confirma sincronizacion)
						ResultRecPersonasASinc resultRps = iGestPed.recibirPersonasASinc(paramRps);
						// <<< end WS >>>
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
								if(getInterfacePersona().actualizarSincPersona(conn, idPersona, Sinc.S, ultAct) < SINC_OK) {
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
						if(errorSincWeb || errorSincDsk) {
							Conector.rollbackConn(conn);
							logger.error("Se invoca un rollback ya que se han detectado errores en el retorno del servicio o en la actualizacion local.");
							logger.error("Error invocado por: " + (errorSincWeb ? "SINC WEB " : "") + (errorSincDsk ? "SINC DSK" : "") );
							throw new SincronizadorException("El sincronizador de personas ha detectado un error, la transaccion hará rollback");
						}
					} else {
						Conector.rollbackConn(conn);
						logger.error("El sincronizador hace rollback por no sincronizar las personas devueltas del servicio con el ambiente local.");
						throw new SincronizadorException("El sincronizador de personas ha detectado un error, la transaccion hará rollback");
					}
					
				} else {
					logger.warn("El sincronizador no ha devuelto personas a sincronizar.");
					sb.append("El sincronizador no ha devuelto personas a sincronizar...").append(ESC);
				}
				sb.append("### Gestor de pedidos: finaliza sincronización de personas. ###").append(ESC);
			}
		} catch (SincronizadorException | PersistenciaException | ParsersException e) {
			Conector.rollbackConn(conn);
			logger.fatal("Excepcion en ManagerSincronizador > sincronizarPersona: " + e.getMessage(), e);
			throw new PresentacionException(e);
		} catch (Exception e) {
			Conector.rollbackConn(conn);
			logger.fatal("Excepcion GENERICA en ManagerSincronizador > sincronizarPersona: " + e.getMessage(), e);
			throw new PresentacionException(e);
		}
		return sb.toString();
	}
	
	/**
	 * Caller STANDALONE del sinc de prodcutos que autogestiona su propia Conexion
	 * (llamadas independientes) < autogenera Connection
	 * @param fechaDesde
	 * @param fechaHasta
	 * @return
	 * @throws PresentacionException
	 */
	public String sincronizarProductos(Fecha fechaDesde, Fecha fechaHasta) throws PresentacionException, WsException {
		try (Connection conn = Conector.getConn()) {
			try {
				/*
				 * como primer paso se comprueba la conectividad con internet y se consume el servicio
				 * con las credenciales del config
				 */
				ControlAccesoInternet.controlarConectividadInet();
				ServiceConsumer srvCns = new ServiceConsumer();
				WsGestPed iGestPed = srvCns.consumirWebService();
				logger.info("<< GPD >> Invocacion a webservice correcta.");
				/**/
				String respuesta = sincronizarProductosNoConn(conn, iGestPed, fechaDesde, fechaHasta);
				Conector.commitConn(conn);
				return respuesta;
			} catch (WsException e) {
				Conector.rollbackConn(conn);
				throw e;
			} catch (SincronizadorException | NoInetConnectionException e) {
				Conector.rollbackConn(conn);
				logger.fatal("Excepcion en ManagerSincronizador > sincronizarProductos: " + e.getMessage(), e);
				throw new PresentacionException(e);
			} catch (Exception e) {
				Conector.rollbackConn(conn);
				logger.fatal("Excepcion INVOCADA desde metodo noConn en ManagerSincronizador > sincronizarProductos: " + e.getMessage(), e);
				throw new PresentacionException(e);
			}
		} catch (SQLException e) {
			logger.fatal("Excepcion al cerrar la conexion en ManagerSincronizador > sincronizarProductos: " + e.getMessage(), e);
			throw new PresentacionException(e);
		}
	}
	/**
	 * ****************************************************************************************************************************
	 * SINC PRODUCTOS
	 * @param fechaDesde
	 * @param fechaHasta
	 * @return
	 * @throws PresentacionException
	 * <<<<< RECIBE CONNECTION - NO AUTOGENERA >>>>>
	 * ****************************************************************************************************************************
	 */
	public String sincronizarProductosNoConn(Connection conn, WsGestPed iGestPed, Fecha fechaDesde, Fecha fechaHasta) throws PresentacionException {
		StringBuilder sb = new StringBuilder();
		logger.info("<< GPD >> Se ingresa a sincronizarProductos... ");
		sb.append("### Gestor de pedidos: inicia sincronización de productos. ###").append(ESC);
		try {
			/*
			 * se obtienen productos a sicronizar
			 */
			List<Producto> listaProdNoSinc = getInterfaceProducto().obtenerListaProductoNoSinc(conn, fechaDesde, fechaHasta);

			if(listaProdNoSinc != null && !listaProdNoSinc.isEmpty()) {
				Fecha ultAct = new Fecha(Fecha.AMDHMS);
				sb.append("Se retorna/n " + listaProdNoSinc.size() + " productos a sincronizar...").append(ESC);
				ParamRecProductosASinc param = ParserParamProducto.parseParamRecProductosASinc(conn, listaProdNoSinc);
				// <<< llamo al WS >>> > operacion recibirPersonasSinc (recibe personas y confirma sincronizacion)
				ResultRecProductosASinc result = iGestPed.recibirProductosASinc(param);
				// <<< end WS >>>
				HlpResultProd hlpRp = ParserResultProducto.parseResultRecProductosASinc(result);
				if(hlpRp != null) {
					if(!hlpRp.getMapTipoProd().isEmpty()) {
						for(Map.Entry<Integer, EstadoSinc> entry : hlpRp.getMapTipoProd().entrySet()) {
							Integer idTp = entry.getKey();
							EstadoSinc estSinc = entry.getValue();
							if(EstadoSinc.O.equals(estSinc)) {
								logger.info("El tipo prod con id [" + idTp + "] devuelve sincronizacion ok, se actualiza en base local.");
								if(getInterfaceTipoProd().modificarSincTipoProd(conn, idTp, Sinc.S) < SINC_OK) {
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
								if(getInterfaceUnidad().modificarSincUnidad(conn, idUni, Sinc.S) < SINC_OK) {
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
							if(getInterfaceProducto().modificarSincProducto(conn, idProd, Sinc.S, ultAct) < SINC_OK) {
								logger.error("Ha habido un error de sincronizacion para el producto con ID [" + idProd + "] en el sistema.");
								sb.append("-- ERROR -- El producto con ID [").append(String.valueOf(idProd)).append("] ha tenido un error en la sincronización.").append(ESC);
							} else {
								logger.info("El producto con ID [" + idProd + "] ha retornado con sincronizacion OK.");
								sb.append("-- El producto con ID [").append(String.valueOf(idProd)).append("] se ha sincronizado correctamente.").append(ESC);
							}
						}
					}
				}
			} else {
				logger.warn("El sincronizador no ha devuelto productos a sincronizar.");
				sb.append("El sincronizador no ha devuelto productos a sincronizar...").append(ESC);
			}
			sb.append("### Gestor de pedidos: finaliza sincronización de productos. ###").append(ESC);
		} catch (PersistenciaException | ParsersException e) {
			Conector.rollbackConn(conn);
			logger.fatal("Excepcion en ManagerSincronizador > sincronizarProductosNoConn: " + e.getMessage(), e);
			throw new PresentacionException(e);
		} catch (Exception e) {
			Conector.rollbackConn(conn);
			logger.fatal("Excepcion GENERICA en ManagerSincronizador > sincronizarProductosNoConn: " + e.getMessage(), e);
			throw new PresentacionException(e);
		}
		return sb.toString();
	}
		
	/**
	 * ****************************************************************************************************************************
	 * SINC PEDIDOS < siempre gestiona su propia conexion
	 * metodo para sincronizar los pedidos. genera logueos para presentacion y para system.out. Consta de 2 pasos, el primero es
	 * ir a buscar los pedidos no SINC a la base web, y guardarlos en la base dsk. Luego se podrá modificar o generar la venta. Esto,
	 * mediante el segundo paso de la sincronizacion, se reflejará en la base web, y el usuario podrá ver que se confirmó, ver que
	 * se modificó o ver que se anuló
	 * @param fechaDesde
	 * @param fechaHasta
	 * @return
	 * @throws PresentacionException
	 * previo a la sincronizacion propia del metodo, llama a los 2 sincronizadores anteriores, si todo funciona ok, procede a 
	 * sincronizar los pedidos desde el sist web
	 * ****************************************************************************************************************************
	 */
	public String sincronizarPedidos(Fecha fechaDesde, Fecha fechaHasta) throws PresentacionException, WsException {
		StringBuilder sb = new StringBuilder();
		logger.info("<< GPD >> Se ingresa a sincronizarPedidos... ");
		sb.append("### Gestor de pedidos: inicia sincronización de pedidos. ###").append(ESC);
		try (Connection conn = Conector.getConn()) {
			try {
				/*
				 * como primer paso se comprueba la conectividad con internet y se consume el servicio
				 * con las credenciales del config
				 */
				ControlAccesoInternet.controlarConectividadInet();
				ServiceConsumer srvCns = new ServiceConsumer();
				WsGestPed iGestPed = srvCns.consumirWebService();
				logger.info("<< GPD >> Invocacion a webservice correcta.");
				/**/
				// [ !! ] realizo antes, las sincronizaciones en orden de personas y pedidos..
				sb.append(sincronizarPersonasNoConn(conn, iGestPed, fechaDesde, fechaHasta));
				sb.append(sincronizarProductosNoConn(conn, iGestPed, fechaDesde, fechaHasta));
				// [ !! ]
				/*
				 * se realiza la obtención de pedidos de web hacia dsk
				 * para esto, se obtienen los pedidos web con estado 'P'
				 */
				ParamObtPedidosNoSinc paramOpns = ParserParamPedido.parseParamObtPedidosNoSinc(fechaDesde, fechaHasta);
				// <<< llamo al WS >>> > operacion obtenerPedidosNoSinc (obtiene pedidos no sinc desde sist web)
				ResultObtPedidosNoSinc resultOpns = iGestPed.obtenerPedidosNoSinc(paramOpns);
				// <<< end WS >>>
				Map<Pedido, EstadoSinc> mapPedidosAConf = null;
				if(resultOpns.getErroresServ().isEmpty()) {
					mapPedidosAConf = new HashMap<>();
					List<Pedido> listaPedidosNoSinc = ParserResultPedido.parseResultPedidosNoSinc(conn, resultOpns);
					Fecha ultAct = new Fecha(Fecha.AMDHMS);
					if(listaPedidosNoSinc != null && !listaPedidosNoSinc.isEmpty()) {
						for(Pedido pedido : listaPedidosNoSinc) {
							Integer resultSinc = 0;
							/*
							 * se marca el pedido como sincronizado ya que hasta que no sea modificado, este no volverá a viajar 
							 * por el servico con cambios. 
							 */
							pedido.setUltAct(ultAct);
							pedido.setSinc(Sinc.S);
							if(getInterfacePedido().checkExistPedido(conn, pedido)) {
								getInterfacePedidoLinea().eliminarListaPedidoLinea(conn, pedido);
								getInterfacePedidoLinea().guardarListaPedidoLinea(conn, pedido.getListaPedidoLinea());
								resultSinc = getInterfacePedido().modificarPedido(conn, pedido);
								sb.append("El ACTUAL pedido con el ID [" + pedido.getPersona().getIdPersona() + " | " + pedido.getFechaHora().toString(Fecha.DMAHMS) + 
										"] se ha modificado correctamente.").append(ESC);
							} else {
								resultSinc = getInterfacePedido().guardarPedido(conn, pedido);
								getInterfacePedidoLinea().guardarListaPedidoLinea(conn, pedido.getListaPedidoLinea());
								sb.append("El NUEVO pedido con el ID [" + pedido.getPersona().getIdPersona() + " | " + pedido.getFechaHora().toString(Fecha.DMAHMS) + 
										"] se ha agregado correctamente.").append(ESC);
							}
							/*
							 * manejo map con confirmacion de cada pedido que vino desde el sist web hacia el sist dsk
							 * estos pedidos van a viajar hacia el web para ser confirmados (SINC en S) en el sist web 
							 */
							EstadoSinc estadoSinc = EstadoSinc.E;
							if(resultSinc > 0) {
								estadoSinc = EstadoSinc.O;
							}
							mapPedidosAConf.put(pedido, estadoSinc);
						}
					}
				} else {
					for(ErrorServicio error : resultOpns.getErroresServ()) {
						Conector.rollbackConn(conn);
						sb.append("-- ERROR -- P1: La sincronizacion de pedidos ha retornado error: " + error.getCodigo() 
									+ " - " + error.getDescripcion()).append(ESC);
					}
				}
				/*
				 * se realiza el envío de pedidos desde el dsk hacia la web
				 * para esto, obtengo pedidos WEB en estado 'R' (revisados) o 'C' (confirmados), 'A' (anulados)
				 */
				List<Pedido> listaPedidosASinc = getInterfacePedido().obtenerListaPedidoNoSincWeb(conn, fechaDesde, fechaHasta);
				if( (mapPedidosAConf != null && !mapPedidosAConf.isEmpty()) || (listaPedidosASinc != null && !listaPedidosASinc.isEmpty()) ) {
					sb.append("-Se proceden a sincronizar [" + listaPedidosASinc.size() + "] pedidos hacia el sistema WEB.").append(ESC);
					sb.append("-Se proceden a confirmar [" + mapPedidosAConf.size() + "] pedidos recibidos hacia el sistema WEB.").append(ESC);
					ParamRecPedidosASinc paramRpas = ParserParamPedido.parseParamRecPedidosASinc(mapPedidosAConf, listaPedidosASinc);
					// <<< llamo al WS >>> > operacion recibirPedidosASinc (recibe pedidos a sinc desde sist dsk)
					ResultRecPedidosASinc resultRpas = iGestPed.recibirPedidosASinc(paramRpas);
					// <<< end WS >>>
					if(resultRpas != null && (resultRpas.getErroresServ() == null || resultRpas.getErroresServ().isEmpty())) {
						Fecha ultAct = new Fecha(Fecha.AMDHMS);
						for(ResultPedidoASinc resultPas : resultRpas.getListaPedidoSinc()) {
							if(resultPas.getErrorServ() == null) {
								Long idPersona = resultPas.getIdPersona();
								Fecha fechaHora = new Fecha(resultPas.getFechaHora(), Fecha.AMDHMS);
								Pedido pedSinc = getInterfacePedido().obtenerPedidoPorId(conn, idPersona, fechaHora);
								pedSinc.setSinc(Sinc.S);
								pedSinc.setUltAct(ultAct);
								getInterfacePedido().modificarSincUltActPedido(conn, pedSinc);
								sb.append("El pedido con el ID [" + idPersona + " | " + fechaHora.toString(Fecha.DMAHMS) + 
											"] se ha sincronizado correctamente.").append(ESC);
							} else {
								ErrorServicio error = resultPas.getErrorServ();
								sb.append("-El pedido sincronizado ha retornado error: " + error.getCodigo() 
										+ " - " + error.getDescripcion()).append(ESC);
							}
						}
					} else {
						if(resultOpns.getErroresServ() != null && !resultOpns.getErroresServ().isEmpty()) {
							for(ErrorServicio error : resultOpns.getErroresServ()) {
								sb.append("-- ERROR -- P2: La sincronizacion de pedidos ha retornado error: " + error.getCodigo() 
											+ " - " + error.getDescripcion()).append(ESC);
							}
							Conector.rollbackConn(conn);
						}
					}
				} else {
					sb.append("-No se obtuvieron pedidos a sincronizar hacia el sistema WEB.").append(ESC);
				}
				//Se comitea la conexion por no existir problemas
				Conector.commitConn(conn);
				sb.append("### Gestor de pedidos: finaliza sincronización de pedidos. ###").append(ESC);
			} catch (PersistenciaException | NoInetConnectionException | SincronizadorException | ParsersException e) {
				Conector.rollbackConn(conn);
				logger.fatal("Excepcion en ManagerSincronizador > sincronizarPedidosDesdeWeb: " + e.getMessage(), e);
				throw new PresentacionException(e);
			} catch (WsException e) {
				Conector.rollbackConn(conn);
				throw e;
			} catch (Exception e) {
				Conector.rollbackConn(conn);
				logger.fatal("Excepcion GENERICA en ManagerSincronizador > sincronizarPedidosDesdeWeb: " + e.getMessage(), e);
				throw new PresentacionException(e);
			}
		} catch (SQLException e) {
			logger.fatal("Excepcion GENERICA en ManagerSincronizador > sincronizarPedidosDesdeWeb: " + e.getMessage(), e);
			throw new PresentacionException(e);
		}
		return sb.toString();
	}
	
}
