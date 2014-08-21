import java.util.ArrayList;
import java.util.List;

import models.Privacidade;
import models.Privada;
import models.Publica;
import models.Usuario;
import models.Viagem;

import org.junit.Assert;
import org.junit.Test;


public class ViagemTest {

	@Test
	public void permiteCriarTiposDePrivacidade(){
		Viagem viagem = new Viagem();
		Assert.assertTrue(viagem.getPrivacidade() == new Publica());
		
//		List<Usuario> usersPermitidos = new ArrayList<Usuario>();
//		Usuario user1 = new Usuario("user1@", "123", "user1");
//		Usuario user2 = new Usuario("user2@", "123", "user2");
//		usersPermitidos.add(user1);
//		usersPermitidos.add(user2);
//		Privacidade privada = new Privada(usersPermitidos);
//		viagem.setPrivacidade(privada);
//		
//		Assert.assertTrue(privada.podeParticipar(user2, null));
	}
}
