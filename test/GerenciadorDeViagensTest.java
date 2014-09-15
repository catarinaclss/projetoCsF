import java.util.ArrayList;
import java.util.List;

import models.Usuario;
import models.Viagem;
import models.ViagemAberta;
import models.ViagemLimitada;
import models.ViagemPrivada;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;


public class GerenciadorDeViagensTest {
	
	List<Usuario> participantes;
	
	@Before
	public void GerenciadorDeViagens(){
		participantes = new ArrayList<Usuario>();
	}
	
	
	@Test
	public void deveExistirRestricoesParaViagem(){
		ViagemAberta aberta = new ViagemAberta();
		Usuario u1 = null;
		Usuario u2 = null;
		Usuario u3 = null;
		Usuario u4 = null;
		Usuario u5 = null;
		Usuario u6 = null;
		Usuario u7 = null;
		Usuario u8 = null;
		
		try {
			u1 = new Usuario("u1", "u1@", "123", new ArrayList<Viagem>());
			u2 = new Usuario("u12", "u2@", "123", new ArrayList<Viagem>());
			u3 = new Usuario("u3", "u3@", "123", new ArrayList<Viagem>());
			u4 = new Usuario("u4", "u4@", "123", new ArrayList<Viagem>());
			u5 = new Usuario("u5", "u5@", "123", new ArrayList<Viagem>());
			u6 = new Usuario("u6", "u6@", "123", new ArrayList<Viagem>());
			u7 = new Usuario("u7", "u7@", "123", new ArrayList<Viagem>());
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		Assert.assertTrue(aberta.adicionarParticipante(participantes, null, u1));
		Assert.assertTrue(aberta.adicionarParticipante(participantes, null, u2));
		Assert.assertTrue(aberta.adicionarParticipante(participantes, null, u3));
		
		ViagemLimitada limitada = new ViagemLimitada();
		
		try {
			limitada.setCodigo("123");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		Assert.assertFalse(limitada.adicionarParticipante(participantes, null, u1));
		Assert.assertFalse(limitada.adicionarParticipante(participantes, "231", u1));
		Assert.assertFalse(limitada.adicionarParticipante(participantes, "", u1));
		//deve permitir participacao quando a senha estiver correta
		Assert.assertTrue(limitada.adicionarParticipante(participantes, "123", u4));
		
		ViagemPrivada privada = new ViagemPrivada();
		List<Usuario> autorizados = new ArrayList<Usuario>();
		autorizados.add(u5);
		autorizados.add(u6);
		
		try {
			privada.setAutorizados(autorizados);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		//nao deve considerar o codigo passado
		Assert.assertTrue(privada.adicionarParticipante(participantes, "000", u5));
		Assert.assertTrue(privada.adicionarParticipante(participantes, null, u6));
		Assert.assertFalse(privada.adicionarParticipante(participantes, null, u7));
		Assert.assertFalse(privada.adicionarParticipante(participantes, null, u8));
	}
	
	@Test 
	public void naoDevePermitirInsercoesRepetidas() throws Exception{
		
		
		Usuario u1 = null;
		Usuario u2 = null;
		Usuario u3 = null;
		
		try {
			u1 = new Usuario("u1", "u1@", "123", new ArrayList<Viagem>());
			u2 = new Usuario("u2", "u2@", "123", new ArrayList<Viagem>());
			u3 = new Usuario("u3", "u3@", "123", new ArrayList<Viagem>());
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		ViagemAberta aberta = new ViagemAberta();
		
		ViagemPrivada privada = new ViagemPrivada();
		List<Usuario> autorizados = new ArrayList<Usuario>();
		autorizados.add(u2);
		privada.setAutorizados(autorizados);
		

		ViagemLimitada limitada = new ViagemLimitada();
		limitada.setCodigo("0000");
		
		Assert.assertTrue(aberta.adicionarParticipante(participantes, null, u1));
		Assert.assertFalse(aberta.adicionarParticipante(participantes, null, u1));
	
		Assert.assertTrue(privada.adicionarParticipante(participantes, null, u2));
		Assert.assertFalse(privada.adicionarParticipante(participantes, null, u2));
	
		Assert.assertTrue(limitada.adicionarParticipante(participantes, "0000", u3));
		Assert.assertFalse(limitada.adicionarParticipante(participantes, "0000", u3));
	}
	
	@Test
	public void permiteMudancasDeTipoDeViagem() throws Exception{
		
		Usuario u1 = null;
		Usuario u2 = null;
		Usuario u3 = null;
		Usuario u4 = null;
		Usuario u5 = null;
		
		try {
			u1 = new Usuario("u1", "u1@", "123", new ArrayList<Viagem>());
			u2 = new Usuario("u2", "u2@", "123", new ArrayList<Viagem>());
			u3 = new Usuario("u3", "u3@", "123", new ArrayList<Viagem>());
			u4 = new Usuario("u4", "u4@", "123", new ArrayList<Viagem>());
			u5 = new Usuario("u5", "u5@", "123", new ArrayList<Viagem>());
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		Viagem viagem = new Viagem();
		viagem.setEstrategia(new ViagemAberta());
		
		viagem.adicionarParticipante(null, u1);
		Assert.assertTrue(viagem.adicionarParticipante(null, u2));
		
		viagem.setEstrategia(new ViagemLimitada());
		viagem.mudarParaLimitada("000");
		
		Assert.assertTrue(viagem.adicionarParticipante("000", u3));
		
		List<Usuario> autorizados = new ArrayList<Usuario>();
		autorizados.add(u4);
		
		viagem.mudarParaPrivada(autorizados);
		//apesar de possuir o codigo correto, para quando a viagem era do tipo limitada
		// ao realizar a mudanca, apenas usuarios autorizados podem participar da viagem
		//respeitando a condicao do tipo especifico ViagemPrivada
		Assert.assertFalse(viagem.adicionarParticipante("000", u5));
		Assert.assertTrue(viagem.adicionarParticipante("", u4));
		
	}
	
}
