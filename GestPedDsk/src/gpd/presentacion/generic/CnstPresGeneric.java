package gpd.presentacion.generic;

public interface CnstPresGeneric {
	
	//imagenes
	public static final String ICON_MD = "icon_md.png";
	public static final String ICON_BG = "icon_bg.png";
	public static final String LOGO = "logo.png";
	
	//usuario
	public static final String ADMIN = "admin";
	public static final String USR = "Usuario";
	public final static String USR_DATOS = "Debe proporcionar nombre de usuario y password.";
	public final static String USR_NO_AUTENTICADO = "El usuario no se ha podido autenticar.";
	public final static String USR_PASS_LARGO = "El largo de las nuevas contraseñas deben ser al menos 6 caracteres.";
	public final static String USR_PASS_VAL = "Las contraseña actual no coincide con el registro.";
	public final static String USR_PASS_REP = "Las contraseñas no coinciden.";
	public final static String USR_EXIST = "EL usuario ya existe en los registros.";
	public final static String USR_PASS_NO_INGRESADO = "Ingrese una contraseña.";
	public final static String USR_ING_OK = "El usuario ha sido ingresado correctamente.";
	public final static String USR_MOD_OK = "El usuario ha sido modificado correctamente.";
	public final static String USR_ELI_OK = "El usuario ha sido eliminado correctamente.";
	public final static String USR_ELI_CONFIRM = "Está seguro que desea eliminar a ";
	public final static String USR_CONFIRM_CC = "El cambio de contraseña ha sido efectuado correctamente.";
	public final static String USR_ADMIN_NC = "El usuario admin no es editable.";
	
	//prod
	public static final String PROD = "Producto";
	public static final String PROD_NOEXIST = "El producto no existe en los registros por su Id.";
	
	//tipoProd
	public static final String TP = "Tipo Prod";
	public static final String TP_ING_OK = "Se ha ingresado el tipo producto correctamente.";
	public static final String TP_MOD_OK = "Se ha modificado el tipo producto correctamente.";
	public static final String TP_ELI_OK = "Se ha eliminado el tipo producto correctamente.";
	public static final String TP_ELI_CON_UTIL = "El tipo prod no se puede eliminar por tener utilización de productos. Verifique antes de eliminar.";

	//unidad
	public static final String UNI = "Unidad";
	public static final String UNI_ING_OK = "Se ha ingresado la unidad correctamente.";
	public static final String UNI_MOD_OK = "Se ha modificado la unidad correctamente.";
	public static final String UNI_ELI_OK = "Se ha eliminado la unidad correctamente.";
	public static final String UNI_ELI_CON_UTIL = "La unidad no se puede eliminar por tener utilización de productos. Verifique antes de eliminar.";
	
	//deposito
	public static final String DEP = "Deposito";
	public static final String DEP_ING_OK = "Se ha ingresado el deposito correctamente.";
	public static final String DEP_MOD_OK = "Se ha modificado el deposito correctamente.";
	public static final String DEP_ELI_OK = "Se ha eliminado el deposito correctamente.";
	
	//persona
	public static final String PERS = "Persona";
	public static final String PERS_F_ING_OK = "Se ha ingresado la persona correctamente.";
	public static final String PERS_F_MOD_OK = "Se ha modificado la persona correctamente.";
	public static final String PERS_F_ELI_OK = "Se ha eliminado la persona correctamente.";
	public static final String PERS_F_EXIST = "La persona fisica ya existe en los registros por su documento.";
	public static final String PERS_F_NOEXIST = "La persona fisica no existe en los registros por su documento.";
	public static final String PERS_J_ING_OK = "Se ha ingresado la empresa correctamente.";
	public static final String PERS_J_MOD_OK = "Se ha modificado la empresa correctamente.";
	public static final String PERS_J_ELI_OK = "Se ha eliminado la empresa correctamente.";
	public static final String PERS_J_EXIST = "La persona juridica ya existe en los registros por su rut.";
	public static final String PERS_J_NOEXIST = "La persona juridica no existe en los registros por su rut.";
	public static final String PERS_J_P_ING_OK = "Se ha ingresado el proveedor correctamente.";
	public static final String PERS_J_P_MOD_OK = "Se ha modificado el proveedor correctamente.";
	public static final String PERS_J_P_ELI_OK = "Se ha eliminado el proveedor correctamente.";
	
	//compra
	public static final String MOV = "Movimiento";
	public static final String COMPRA_PROD_YA_ING = "El producto ya ha sido ingresado, revise los datos.";
	public static final String COMPRA_SIN_TRANSAC = "No existe compra abierta, revise los datos.";
	public static final String COMPRA_SIN_LINEAS = "No existen items en la compra, revise los datos.";
	public static final String COMPRA_CONFIRMADA = "La compra se ha confirmado.";
	public static final String COMPRA_ANULADA = "La compra se ha anulado.";
	public static final String COMPRA_ANU_CONF = "Esta seguro que quiere anular la compra?";
	public static final String COMPRA_NOMODIF_PROD = "El prodcuto que intenta modificar no es parte de la compra.";
	public static final String JTABLE_SIN_COMPRAS = "No hay compras para mostrar...";
	public static final String JTABLE_SIN_VENTAS = "No hay ventas para mostrar...";
	
	//utilidad
	public static final String UTIL = "Utilidad";
	public static final String UTIL_ING_OK = "Se ha ingresado la utilidad correctamente.";
	public static final String UTIL_MOD_OK = "Se ha modificado la utilidad correctamente.";
	public static final String UTIL_ELI_OK = "Se ha eliminado la utilidad correctamente.";
	
	//lote
	public static final String LOTE = "Lote";
	public static final String LOTES_ACT_OK = "El lote ha sido actualizado correctamente.";
	public static final String JTABLE_SIN_LOTES = "No hay lotes para mostrar...";
	public static final String LOTES_NO_COMPLETADOS = "Hay lotes sin actualizar, complete la informacion.";
	
	//pedido
	public static final String PED = "Pedido";
	public static final String PEDIDO_INEXISTENTE = "No existe pedido abierto, genere nuevo pedido.";
	public static final String PEDIDO_GEN_OK = "El pedido se ha generado correctamente.";
	public static final String PEDIDO_ACT_OK = "El pedido se ha actualizado correctamente.";
	public static final String PEDIDO_ANU_OK = "El pedido se ha anulado correctamente.";
	public static final String PEDIDO_SIN_PERS = "Debe seleccionar una persona para el pedido.";
	public static final String NUEVO_PEDIDO_CONF = "Si genera un nuevo pedido perdera los datos que no haya guardado, continuar?";
	public static final String JTABLE_SIN_PEDIDOS = "No hay pedidos para mostrar...";
	public static final String JTABLE_SIN_LINEASPED = "No hay lineas de pedido para mostrar...";
	public static final String PEDIDO_LINEA_EXISTE = "La linea ya existe en el pedido.";
	public static final String PEDIDO_LINEA_NO_EXISTE = "La linea no existe en el pedido.";
	public static final String PEDIDO_NO_GENERADO = "El pedido no fue generado, o no tiene items.";
	public static final String PEDIDO_SIN_LINEAS = "El pedido no tiene items.";
	
	//venta
	public static final String VTA = "Venta";
	public static final String VTA_CONF_GEN = "Está seguro que desea generar la venta? ";
	public static final String VTA_CONF_ANULA = "Está seguro que desea anular la venta? ";
	public static final String VTA_GENERADA_OK = "La venta se ha generado correctamente.";
	public static final String VTA_ANULADA = "La venta se ha anulado.";
	
	//sinc
	public static final String SINC = "Sincronizador";
	
	//controles genericos
	public static final String CONF_LIMPIAR = "Confirma que desea limpiar el formulario ?";
	public static final String JTABLE_SIN_ITEMS = "No hay items para mostrar...";
	public static final String DATOS_OBLIG = "Los datos marcados son obligatorios.";
	public static final String JTABLE_EMPTY = "No se encontraron resultados...";
	public static final String N_A = "N/A";
	public static final String QUESTION = "?";
	
	
}
