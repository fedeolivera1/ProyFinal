package test.persona;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import gpd.dominio.persona.ClienteEmp;
import gpd.dominio.persona.ClientePers;
import gpd.dominio.persona.Localidad;
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
		ClientePers cli = new ClientePers();
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
		cli.setUltAct(new Fecha());
		
		ManagerPersona mgrPersona = new ManagerPersona();
		Integer resultado = mgrPersona.guardarPersFisica(cli);
		assertTrue(1==resultado);
	}

	@Test
	public void testGuardarPersJuridica() {
		ClienteEmp cli = new ClienteEmp();
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
		cli.setTipoPers(TipoPersona.J);
		Localidad loc = new Localidad();
		loc.setIdLocalidad(1);
		cli.setLocalidad(loc);
		//datos cliente empresa
		cli.setRut(new Long("123456789012"));
		cli.setNombre("nombre cli persona juridica");
		cli.setRazonSocial("razon social cli persona juridica");
		cli.setBps("bps");
		cli.setBse("bse");
		
		ManagerPersona mgrPersona = new ManagerPersona();
		Integer resultado = mgrPersona.guardarPersJuridica(cli);
		assertTrue(1==resultado);
	}

}
