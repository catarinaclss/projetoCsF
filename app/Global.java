import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import models.Estrategia;
import models.Usuario;
import models.Viagem;
import models.ViagemAberta;
import models.ViagemLimitada;
import models.ViagemPrivada;
import models.dao.GenericDAO;
import models.dao.GenericDAOImpl;
import play.Application;
import play.GlobalSettings;
import play.db.jpa.JPA;
import play.db.jpa.Transactional;



public class Global extends GlobalSettings {

	private GenericDAO dao = new GenericDAOImpl();
	
	@Override
	public void onStart(Application arg0) {

		JPA.withTransaction(new play.libs.F.Callback0() {

			@Override
			public void invoke() throws Throwable {
				try {
					criarViagens();
				} catch (Exception e) {
					System.out.println(e.getMessage());
				}
			}
		});
	}
	
	private void criarViagens() throws Exception{
		
		List<Viagem> v = new ArrayList<Viagem>();
		List<Usuario> u = criarUsuarios();
		
		Usuario user1 = u.get(0);
		Calendar d = new GregorianCalendar(2014, 10, 28 );
		Estrategia e = (Estrategia) persist(new ViagemAberta());
		
		v.add((Viagem) persist(new Viagem(d.getTime(), "Bolivia", "Passeio", e, u.subList(0, 7))));
		user1.adicionarViagem(v.get(0));
		v.add((Viagem) persist(new Viagem(d.getTime(), "Peru", "Passeio", e, u.subList(8, 15))));
		user1.adicionarViagem(v.get(1));
		v.add((Viagem) persist(new Viagem(d.getTime(), "Paraguai", "Passeio", e, u.subList(16, 23))));
		user1.adicionarViagem(v.get(2));
		v.add((Viagem) persist(new Viagem(d.getTime(), "Argentina", "Passeio", e, u.subList(24, 31))));
		user1.adicionarViagem(v.get(3));
		v.add((Viagem) persist(new Viagem(d.getTime(), "Venezuela", "Passeio", e, u.subList(32, 39))));
		user1.adicionarViagem(v.get(4));
		
		dao.merge(user1);
		
		Usuario user2 = u.get(1);
		d = new GregorianCalendar(2015, 3, 10 );
		
		v.add((Viagem) persist(new Viagem(d.getTime(), "Australia", "Passeio", e, u.subList(0, 7))));
		user2.adicionarViagem(v.get(5));
		v.add((Viagem) persist(new Viagem(d.getTime(), "Canada", "Passeio", e, u.subList(8, 15))));
		user2.adicionarViagem(v.get(6));
		v.add((Viagem) persist(new Viagem(d.getTime(), "Suiça", "Passeio", e, u.subList(16, 23))));
		user2.adicionarViagem(v.get(7));
		v.add((Viagem) persist(new Viagem(d.getTime(), "Iatalia", "Passeio", e, u.subList(24, 31))));
		user2.adicionarViagem(v.get(8));
		v.add((Viagem) persist(new Viagem(d.getTime(), "França", "Passeio", e, u.subList(32, 39))));
		user2.adicionarViagem(v.get(9));
		
		dao.merge(user2);
		
		Usuario user3 = u.get(2);
		d = new GregorianCalendar(2015, 7, 15 );
		e = (Estrategia) persist(new ViagemLimitada("123"));
		
		v.add((Viagem) persist(new Viagem(d.getTime(), "Grecia", "Passeio", e, u.subList(0, 7))));
		user3.adicionarViagem(v.get(10));
		v.add((Viagem) persist(new Viagem(d.getTime(), "Roma", "Passeio", e, u.subList(8, 15))));
		user3.adicionarViagem(v.get(11));
		v.add((Viagem) persist(new Viagem(d.getTime(), "Egito", "Passeio", e, u.subList(16, 23))));
		user3.adicionarViagem(v.get(12));
		v.add((Viagem) persist(new Viagem(d.getTime(), "Romenia", "Passeio", e, u.subList(24, 31))));
		user3.adicionarViagem(v.get(13));
		v.add((Viagem) persist(new Viagem(d.getTime(), "Chile", "Passeio", e, u.subList(32, 39))));
		user3.adicionarViagem(v.get(14));
		
		dao.merge(user3);
		
		Usuario user4 = u.get(3);
		d = new GregorianCalendar(2015, 2, 7 );
		
		v.add((Viagem) persist(new Viagem(d.getTime(), "Mexico", "Passeio", e, u.subList(0, 11))));
		user4.adicionarViagem(v.get(15));
		v.add((Viagem) persist(new Viagem(d.getTime(), "Portugal", "Passeio", e, u.subList(11, 21))));
		user4.adicionarViagem(v.get(16));
		v.add((Viagem) persist(new Viagem(d.getTime(), "China", "Passeio", e, u.subList(11, 21))));
		user4.adicionarViagem(v.get(17));
		v.add((Viagem) persist(new Viagem(d.getTime(), "Japão", "Passeio", e, u.subList(21, 31))));
		user4.adicionarViagem(v.get(18));
		v.add((Viagem) persist(new Viagem(d.getTime(), "Estados Unidos", "Passeio", e, u.subList(31, 39))));
		user4.adicionarViagem(v.get(19));
		
		dao.merge(user4);
		
		Usuario user5 = u.get(4);
		d = new GregorianCalendar(2015, 4, 3 );
		e = (Estrategia) persist(new ViagemPrivada());
		
		v.add((Viagem) persist(new Viagem(d.getTime(), "Inglaterra", "Passeio", e, u.subList(1, 11))));
		user5.adicionarViagem(v.get(20));
		v.add((Viagem) persist(new Viagem(d.getTime(), "Holanda", "Passeio", e, u.subList(11, 21))));
		user5.adicionarViagem(v.get(21));
		v.add((Viagem) persist(new Viagem(d.getTime(), "China", "Passeio", e, u.subList(11, 21))));
		user5.adicionarViagem(v.get(22));
		v.add((Viagem) persist(new Viagem(d.getTime(), "Irlanda", "Passeio", e, u.subList(21, 31))));
		user5.adicionarViagem(v.get(23));
		v.add((Viagem) persist(new Viagem(d.getTime(), "Uruguai", "Passeio", e, u.subList(31, 39))));
		user5.adicionarViagem(v.get(24));
		
		dao.merge(user5);
		
		Usuario user6 = u.get(5);
		d = new GregorianCalendar(2015, 5, 22);
		
		v.add((Viagem) persist(new Viagem(d.getTime(), "Mesopotâmia", "Passeio", e, u.subList(1, 11))));
		user6.adicionarViagem(v.get(25));
		v.add((Viagem) persist(new Viagem(d.getTime(), "Paris", "Passeio", e, u.subList(11, 21))));
		user6.adicionarViagem(v.get(26));
		v.add((Viagem) persist(new Viagem(d.getTime(), "Londres", "Passeio", e, u.subList(11, 21))));
		user6.adicionarViagem(v.get(27));
		v.add((Viagem) persist(new Viagem(d.getTime(), "New York", "Passeio", e, u.subList(21, 31))));
		user6.adicionarViagem(v.get(28));
		v.add((Viagem) persist(new Viagem(d.getTime(), "Sudão", "Passeio", e, u.subList(31, 39))));
		user6.adicionarViagem(v.get(29));
		
		dao.merge(user6);
		
		dao.flush();
	}
	
	private List<Usuario> criarUsuarios(){
		List<Usuario> u = new ArrayList<Usuario>();
		try {
			u.add((Usuario) persist(new Usuario("Celia", "celia@mail.com", "123", new ArrayList<Viagem>())));
			u.add((Usuario) persist(new Usuario("Maria", "maria@mail.com", "123", new ArrayList<Viagem>())));
			u.add((Usuario) persist(new Usuario("Raquel", "raquel@mail.com", "123", new ArrayList<Viagem>())));
			u.add((Usuario) persist(new Usuario("Priscila", "priscila@mail.com", "123", new ArrayList<Viagem>())));
			u.add((Usuario) persist(new Usuario("Tereza", "tereza@mail.com", "123", new ArrayList<Viagem>())));
			u.add((Usuario) persist(new Usuario("Conceicao", "conceicao@mail.com", "123", new ArrayList<Viagem>())));
			u.add((Usuario) persist(new Usuario("Francisca", "francisca@mail.com", "123", new ArrayList<Viagem>())));
			u.add((Usuario) persist(new Usuario("Lurdes", "luders@mail.com", "123", new ArrayList<Viagem>())));
			u.add((Usuario) persist(new Usuario("Raissa", "raissa@mail.com", "123", new ArrayList<Viagem>())));
			u.add((Usuario) persist(new Usuario("Rayane", "rayane@mail.com", "123", new ArrayList<Viagem>())));
			u.add((Usuario) persist(new Usuario("Janaina", "janaina@mail.com", "123", new ArrayList<Viagem>())));
			u.add((Usuario) persist(new Usuario("Mateus", "mateus@mail.com", "123", new ArrayList<Viagem>())));
			u.add((Usuario) persist(new Usuario("Marcos", "marcos@mail.com", "123", new ArrayList<Viagem>())));
			u.add((Usuario) persist(new Usuario("Francisco", "francisco@mail.com", "123", new ArrayList<Viagem>())));
			u.add((Usuario) persist(new Usuario("Tiago", "tiago@mail.com", "123", new ArrayList<Viagem>())));
			u.add((Usuario) persist(new Usuario("Barbosa", "barbosa@mail.com", "123", new ArrayList<Viagem>())));
			u.add((Usuario) persist(new Usuario("Marques", "marques@mail.com", "123", new ArrayList<Viagem>())));
			u.add((Usuario) persist(new Usuario("Fernanda", "fernanda@mail.com", "123", new ArrayList<Viagem>())));
			u.add((Usuario) persist(new Usuario("Jaqueline", "jaqueline@mail.com", "123", new ArrayList<Viagem>())));
			u.add((Usuario) persist(new Usuario("Joao", "joao@mail.com", "123", new ArrayList<Viagem>())));
			u.add((Usuario) persist(new Usuario("Lucas", "lucas@mail.com", "123", new ArrayList<Viagem>())));
			u.add((Usuario) persist(new Usuario("Rodrigo", "rodrigo@mail.com", "123", new ArrayList<Viagem>())));
			u.add((Usuario) persist(new Usuario("Junior", "junior@mail.com", "123", new ArrayList<Viagem>())));
			u.add((Usuario) persist(new Usuario("Ricardo", "ricardo@mail.com", "123", new ArrayList<Viagem>())));
			u.add((Usuario) persist(new Usuario("Cassio", "cassio@mail.com", "123", new ArrayList<Viagem>())));
			u.add((Usuario) persist(new Usuario("Pedro", "pedro@mail.com", "123", new ArrayList<Viagem>())));
			u.add((Usuario) persist(new Usuario("Eduarda", "eduarda@mail.com", "123", new ArrayList<Viagem>())));
			u.add((Usuario) persist(new Usuario("Isabele", "isalbele@mail.com", "123", new ArrayList<Viagem>())));
			u.add((Usuario) persist(new Usuario("Ana", "ana@mail.com", "123", new ArrayList<Viagem>())));
			u.add((Usuario) persist(new Usuario("Julia", "julia@mail.com", "123", new ArrayList<Viagem>())));
			u.add((Usuario) persist(new Usuario("Beatriz", "beatriz@mail.com", "123", new ArrayList<Viagem>())));
			u.add((Usuario) persist(new Usuario("Laura", "laura@mail.com", "123", new ArrayList<Viagem>())));
			u.add((Usuario) persist(new Usuario("Leticia", "leticia@mail.com", "123", new ArrayList<Viagem>())));
			u.add((Usuario) persist(new Usuario("Larissa", "Larissa@mail.com", "123", new ArrayList<Viagem>())));
			u.add((Usuario) persist(new Usuario("Gabriel", "gabriel@mail.com", "123", new ArrayList<Viagem>())));
			u.add((Usuario) persist(new Usuario("Artur", "artur@mail.com", "123", new ArrayList<Viagem>())));
			u.add((Usuario) persist(new Usuario("Rafael", "rafael@mail.com", "123", new ArrayList<Viagem>())));
			u.add((Usuario) persist(new Usuario("Leonardo", "leonardo@mail.com", "123", new ArrayList<Viagem>())));
			u.add((Usuario) persist(new Usuario("Diego", "diego@mail.com", "123", new ArrayList<Viagem>())));
			u.add((Usuario) persist(new Usuario("Carlos", "carlos@mail.com", "123", new ArrayList<Viagem>())));
		} catch (Exception e) {
			
		}
		return u;		
	}
	
	@Transactional
	private <T> Object persist(Object object) {
		List<T> result = dao.findAllByClassName(object.getClass().getSimpleName());
		if (!result.contains(object)) {
			dao.persist(object);
			dao.flush();
		}
		return getObjectBD(object);
	}

	@Transactional
	private <T> Object getObjectBD(Object object) {
		List<T> result = dao.findAllByClassName(object.getClass().getSimpleName());
		for (Object obj : result) {
			if (obj.equals(object)) {
				return obj;
			}
		}
		return null;
	}
}
