package gpd.ws.parsers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import gpd.dominio.persona.Persona;
import gpd.dominio.persona.PersonaFisica;
import gpd.dominio.persona.PersonaJuridica;
import gpd.dominio.persona.Sexo;
import gpd.dominio.persona.TipoDoc;
import gpd.dominio.persona.TipoPersona;
import gpd.dominio.util.Origen;
import gpd.dominio.util.Sinc;
import gpd.exceptions.PersistenciaException;
import gpd.exceptions.SincronizadorException;
import gpd.persistencia.persona.PersistenciaDepLoc;
import gpd.types.Fecha;
import gpw.webservice.proxy.ResultObtPersonasNoSinc;
import gpw.webservice.proxy.ResultPersonaFisica;
import gpw.webservice.proxy.ResultPersonaJuridica;
import gpw.webservice.proxy.ResultPersonaSinc;
import gpw.webservice.proxy.ResultRecPersonasASinc;

public class ParserResultPersona {
	
	private static final Logger logger = Logger.getLogger(ParserResultPersona.class);

	public static List<Persona> parseResultObtPersNoSinc(ResultObtPersonasNoSinc result) throws SincronizadorException {
		List<Persona> listaPersonasResult = null;
		if(result != null) {
			listaPersonasResult = new ArrayList<>();
			PersistenciaDepLoc pdl = new PersistenciaDepLoc();
			if(result.getListaPersFisica() != null && !result.getListaPersFisica().isEmpty()) {
				for(ResultPersonaFisica rpf : result.getListaPersFisica()) {
					PersonaFisica pf = new PersonaFisica();
					
					pf.setDocumento(rpf.getDocumento());
					TipoDoc tipoDoc = new TipoDoc();
					tipoDoc.setIdTipoDoc(rpf.getTipoDoc());
					tipoDoc.setNombre(rpf.getTipoDocDesc());
					pf.setTipoDoc(tipoDoc);
					pf.setApellido1(rpf.getApellido1());
					pf.setApellido2(rpf.getApellido2());
					pf.setNombre1(rpf.getNombre1());
					pf.setFechaNac(new Fecha(rpf.getFechaNac(), Fecha.AMD));
					pf.setSexo(Sexo.getSexoPorChar(rpf.getSexo().charAt(0)));
					pf.setDocumentoAnt(null);
					//persona
					pf.setDireccion(rpf.getDireccion());
					pf.setPuerta(rpf.getPuerta());
					pf.setSolar(rpf.getSolar());
					pf.setManzana(rpf.getManzana());
					pf.setKm(rpf.getKm());
					pf.setComplemento(rpf.getComplemento());
					pf.setTelefono(rpf.getTelefono());
					pf.setCelular(rpf.getCelular());
					pf.setEmail(rpf.getEmail());
					pf.setFechaReg(new Fecha(rpf.getFechaReg(), Fecha.AMD));
					pf.setTipoPers(TipoPersona.getTipoPersonaPorChar(rpf.getTipoPers().charAt(0)));
					try {
						pf.setLocalidad(pdl.obtenerLocalidadPorId(rpf.getLocalidad()));
					} catch (PersistenciaException e) {
						logger.fatal("Excepcion en ParserResultPersona > parseResultObtPersNoSinc (pf): " + e.getMessage(), e);
						throw new SincronizadorException(e.getMessage(), e);
					}
					pf.setOrigen(Origen.getOrigenPorChar(rpf.getOrigen().charAt(0)));
					pf.setSinc(Sinc.getSincPorChar(rpf.getSinc().charAt(0)));
					pf.setUltAct(new Fecha(rpf.getUltAct(), Fecha.AMDHMS));

					listaPersonasResult.add(pf);
				}
			}
			if(result.getListaPersJuridica() != null && !result.getListaPersJuridica().isEmpty()) {
				for(ResultPersonaJuridica rpj : result.getListaPersJuridica()) {
					PersonaJuridica pj = new PersonaJuridica();
					pj.setRut(rpj.getRut());
					pj.setNombre(rpj.getNombre());
					pj.setRazonSocial(rpj.getRazonSocial());
					pj.setBps(rpj.getBps());
					pj.setBse(rpj.getBse());
					pj.setEsProv(rpj.isEsProv());
					pj.setRutAnt(null);
					//persona
					pj.setDireccion(rpj.getDireccion());
					pj.setPuerta(rpj.getPuerta());
					pj.setSolar(rpj.getSolar());
					pj.setManzana(rpj.getManzana());
					pj.setKm(rpj.getKm());
					pj.setComplemento(rpj.getComplemento());
					pj.setTelefono(rpj.getTelefono());
					pj.setCelular(rpj.getCelular());
					pj.setEmail(rpj.getEmail());
					pj.setFechaReg(new Fecha(rpj.getFechaReg(), Fecha.AMD));
					pj.setTipoPers(TipoPersona.getTipoPersonaPorChar(rpj.getTipoPers().charAt(0)));
					try {
						pj.setLocalidad(pdl.obtenerLocalidadPorId(rpj.getLocalidad()));
					} catch (PersistenciaException e) {
						logger.fatal("Excepcion en ParserResultPersona > parseResultObtPersNoSinc (pj): " + e.getMessage(), e);
						throw new SincronizadorException(e.getMessage(), e);
					}
					pj.setOrigen(Origen.getOrigenPorChar(rpj.getOrigen().charAt(0)));
					pj.setSinc(Sinc.getSincPorChar(rpj.getSinc().charAt(0)));
					pj.setUltAct(new Fecha(rpj.getUltAct(), Fecha.AMDHMS));
					
					listaPersonasResult.add(pj);
				}
			}
		} else {
			throw new SincronizadorException("Los datos del tipo 'ResultObtPersonasNoSinc' son obligatorios.");
		}
		return listaPersonasResult;
	}
	
	public static Map<Long, Integer> parseResultRecPersonasSinc(ResultRecPersonasASinc result) throws SincronizadorException {
		Map<Long, Integer> hmResult = null;
		if(result.getListaPersonaSinc() != null && !result.getListaPersonaSinc().isEmpty()) {
			hmResult = new HashMap<>();
			for(ResultPersonaSinc resultPs : result.getListaPersonaSinc()) {
				hmResult.put(resultPs.getIdPersona(), resultPs.getEstadoSinc());
			}
		}
		return hmResult;
	}
	
}
