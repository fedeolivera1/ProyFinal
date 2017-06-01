package test.persona;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import gpd.dominio.persona.Localidad;
import gpd.dominio.persona.PersonaFisica;
import gpd.dominio.persona.PersonaJuridica;
import gpd.dominio.persona.Sexo;
import gpd.dominio.persona.TipoDoc;
import gpd.dominio.persona.TipoPersona;
import gpd.dominio.util.Origen;
import gpd.dominio.util.Sinc;
import gpd.manager.persona.ManagerPersona;
import gpd.types.Fecha;

public class ManagerPersonaTest {

	@Test
	public void testGuardarPersFisica() {
		PersonaFisica cli = new PersonaFisica();
		TipoDoc tipoDoc = new TipoDoc();
		tipoDoc.setIdTipoDoc(1);
		tipoDoc.setNombre("CI");
		cli.setTipoDoc(tipoDoc);
		//datos persona
		cli.setDireccion("dir");
		cli.setPuerta("552");
		cli.setSolar("solar");
		cli.setManzana("manzana");
		cli.setKm(new Float(558));
//		cli.setComplemento(""); DEJO NULO A PROPÓSITO
		cli.setTelefono("21050030");
		cli.setCelular("099123456");
		cli.setEmail("a@a.com");
		cli.setFechaReg(new Fecha(2017,02,01));
		cli.setTipoPers(TipoPersona.F);
		Localidad loc = new Localidad();
		loc.setIdLocalidad(1);
		cli.setLocalidad(loc);
		//datos cliente persona
		cli.setDocumento((long) 12345672);
		cli.setApellido1("ape1");
		cli.setApellido2("ape2");
		cli.setNombre1("nom1");
		cli.setNombre2(null);
//		Calendar c = Calendar.getInstance();
//		c.set(1983, 1, 3);
		cli.setFechaNac(new Fecha(1983, 01, 03));
		cli.setSexo(Sexo.M);
		cli.setOrigen(Origen.D);
		cli.setSinc(Sinc.N);
		cli.setUltAct(new Fecha(Fecha.AMDHMS));
		
		ManagerPersona mgrPersona = new ManagerPersona();
		Integer resultado = mgrPersona.guardarPersFisica(cli);
		assertTrue(1==resultado);
	}

	@Test
	public void testGuardarPersJuridica() {
		PersonaJuridica emp = new PersonaJuridica();
		//datos persona
		emp.setDireccion("dir");
		emp.setPuerta("552");
		emp.setSolar("solar");
		emp.setManzana("manzana");
		emp.setKm(new Float(558));
//		cli.setComplemento(""); DEJO NULO A PROPÓSITO
		emp.setTelefono("21050030");
		emp.setCelular("099123456");
		emp.setEmail("a@a.com");
		emp.setTipoPers(TipoPersona.J);
		Localidad loc = new Localidad();
		loc.setIdLocalidad(1);
		emp.setLocalidad(loc);
		//datos cliente empresa
		emp.setRut(new Long("123456789012"));
		emp.setNombre("nombre cli persona juridica");
		emp.setRazonSocial("razon social cli persona juridica");
		emp.setBps("bps");
		emp.setBse("bse");
		
		ManagerPersona mgrPersona = new ManagerPersona();
		Integer resultado = mgrPersona.guardarPersJuridica(emp);
		assertTrue(1==resultado);
	}

}
