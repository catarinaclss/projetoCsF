package controllers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import com.google.common.base.Objects;

import models.Usuario;
import models.Viagem;
import models.ViagemAberta;
import models.ViagemLimitada;
import models.ViagemPrivada;
import models.dao.GenericDAO;
import models.dao.GenericDAOImpl;
import play.db.jpa.Transactional;
import play.mvc.*;
import views.html.*;
import static play.data.Form.form;

public class Application extends Controller {

	private static GenericDAO dao = new GenericDAOImpl();

	@Transactional
	public static Result index() {

		Usuario usuario = null;
		String email = session("email");

		if (email == null || (usuario = getUsuario(email)) == null) {
			return redirect("/login");
		}
		return ok(index.render("Principal", usuario));
	}

	public static Result loginPage() {
		return ok(login.render("Login"));
	}

	public static Result cadastroDeUsuarioPage() {
		return ok(cadastroDeUsuario.render("Cadastro"));
	}

	@Transactional
	public static Result cadastroDeViagemPage() {
		Usuario usuario = null;
		String email = session("email");

		if (email == null || (usuario = getUsuario(email)) == null) {
			return redirect("/login");
		}
		return ok(cadastroDeViagem.render("Cadastro de Viagem", usuario));
	}

	@Transactional
	public static Result agendaPage() {
		Usuario usuario = null;
		String email = session("email");

		if (email == null || (usuario = getUsuario(email)) == null) {
			return redirect("/login");
		}
		return ok(agenda.render("Agenda", usuario));
	}
	
	@Transactional
	public static Result usuariosPage() {
		Usuario usuario = null;
		String email = session("email");

		if (email == null || (usuario = getUsuario(email)) == null) {
			return redirect("/login");
		}
		return ok(usuarios.render("Usuarios", usuario));
	}

	@Transactional
	public static Result minhasViagensPage() {
		Usuario usuario = null;
		String email = session("email");

		if (email == null || (usuario = getUsuario(email)) == null) {
			return redirect("/login");
		}
		return ok(minhasViagens.render("Minhas Viagens", usuario));
	}

	@Transactional
	public static Result editarViagemPage(Long id) {
		Usuario usuario = null;
		String email = session("email");

		if (email == null || (usuario = getUsuario(email)) == null) {
			return redirect("/login");
		}
		Viagem viagem = getViagem(id);

		return ok(editarViagem.render("Editar Viagem", usuario, viagem));
	}

	@Transactional
	public static Result autenticar() {

		String email = form().bindFromRequest().get("EMAIL_USUARIO").trim();
		String senha = form().bindFromRequest().get("SENHA_USUARIO").trim();

		Usuario usuario = getUsuario(email);

		if (usuario == null) {
			flash("erro", "E-mail não cadastrado");
			return redirect("/login");
		}

		// FAZENDO HASH DO E-MAIL E SENHA DIGITADOS NA PAGINA DE LOGIN
		String hashUsuario = String.valueOf(Objects.hashCode(email, senha));

		// CASO SENHA E EMAIL DIGITADOS NAO CONFEREM
		if (!hashUsuario.equals(usuario.getSenha())) {
			flash("erro", "E-mail ou Senha Incorretos!");
			return redirect("/login");
		}
		// LIMPANDO A SESSION PARA USUARIO
		session().clear();
		session("email", usuario.getEmail());

		return redirect("/");
	}

	public static Result logout() {
		session().clear();
		return redirect("/");
	}

	@Transactional
	public static List<List<Viagem>> minhasViagensEmListas() {
		Usuario usuario = null;
		String email = session("email");

		if (email == null || (usuario = getUsuario(email)) == null) {
			index();
		}
		// RETOTRNA UMA MATRIZ
		return criarMatriz(usuario.getViagens());
	}

	@Transactional
	public static List<List<Viagem>> agendaEmListas() {
		Usuario usuario = null;
		String email = session("email");

		if (email == null || (usuario = getUsuario(email)) == null) {
			index();
		}
		
		// LISTA COM TODAS VIAGEM QUE O USUARIO ESTA PARTICIPANDO
		List<Viagem> viagemQueParticipo = new ArrayList<Viagem>();

		// ADICIONANDO VIAGEM QUE O USUARIO PARTICIPA
		for (Object viagem : getAllViagens()) {
			// VERIFICANDO SE USUARIO ESTA NA LISTA DE PARTICIPANTE
			if (((Viagem) viagem).getParticipantes().contains(usuario)) {
				viagemQueParticipo.add((Viagem) viagem);
			}
		}
		Collections.sort(viagemQueParticipo);
		return criarMatriz(viagemQueParticipo);
	}
	
	@Transactional
	public static List<List<Viagem>> getAllViagensEmListas() {
		// RETORNA UMA MATRIZ
		return criarMatriz(getAllViagens());
	}
	
	@Transactional
	public static List<List<Usuario>> getAllUsuariosEmListas() {
		// RETORNA UMA MATRIZ
		return criarMatriz(getAllUsuarios());
	}

	private static <T> List<List<T>> criarMatriz(List<T> object) {
		
		// ESSA LISTA(MATRIZ) SERA RETORNADA NO FIM DO METODO
		List<List<T>> matriz = new ArrayList<List<T>>();
		
		// LISTA REPRESENTA UMA LINHA NA MATRIZ
		List<T> linha = new ArrayList<T>();
		
		// CRIANDO A MATRIZ
		Integer aux = 0;
		for (T obj : object) {
			if (aux == 3) {
				matriz.add(linha);
				linha = new ArrayList<T>();
				linha.add(obj);
				aux = 1;
			} else {
				linha.add(obj);
				aux++;
			}
		}
		if(aux != 0){
			matriz.add(linha);
		}
		
		return matriz;
	}
	

	@Transactional
	public static Result cadastrarUsuario() {

		try {
			String nome = form().bindFromRequest().get("NOME_USUARIO").trim();
			String email = form().bindFromRequest().get("EMAIL_USUARIO").trim();
			String senha = form().bindFromRequest().get("SENHA_USUARIO").trim();

			Usuario usuarioNovo = new Usuario();

			usuarioNovo.setEmail(email);
			usuarioNovo.setNome(nome);
			usuarioNovo.setSenha(senha);

			salvarUsuario(usuarioNovo);

		} catch (Exception e) {
			flash("erro", e.getMessage());
			return redirect("/cadastro/usuario");
		}
		session().clear();
		return redirect("/");
	}

	@Transactional
	public static Result cadastrarViagem() {
		Usuario usuario = null;
		String email = session("email");

		if (email == null || (usuario = getUsuario(email)) == null) {
			return redirect("/login");
		}

		Viagem viagem;
		try {
			String local = form().bindFromRequest().get("LOCAL_VIAGEM").trim();
			String descricao = form().bindFromRequest().get("DESCRICAO_VIAGEM")
					.trim();
			Date data = getDataFormatada(form().bindFromRequest().get(
					"DATA_VIAGEM"));
			String tipo = form().bindFromRequest().get("TIPO_VIAGEM");

			viagem = new Viagem();
			viagem.setLocal(local);
			viagem.setDescricao(descricao);
			viagem.setData(data);

			if (tipo.equals("LIMITADA")) {
				String codigo = form().bindFromRequest().get("CODIGO_VIAGEM");
				viagem.setEstrategia((ViagemLimitada) persist(new ViagemLimitada(
						codigo)));
			} else if (tipo.equals("ABERTA")) {
				viagem.setEstrategia((ViagemAberta) persist(new ViagemAberta()));
			} else if (tipo.equals("PRIVADA")) {
				String lista = form().bindFromRequest().get(
						"PARTICIPANTES_VIAGEM");
				viagem.setEstrategia((ViagemPrivada) persist(new ViagemPrivada(
						getListaPrivada(lista))));
			} else {
				throw new Exception("Selecione o tipo de Viagem");
			}

			if (getObjectBD(viagem) != null) {
				throw new Exception("Ja existe uma viagem cadastrada");
			} else {
				usuario.adicionarViagem((Viagem) persist(viagem));
				dao.merge(usuario);
				dao.flush();
			}
		} catch (Exception e) {
			flash("erro", e.getMessage());
			return redirect("/cadastro/viagem");
		}
		flash("success", "Viagem cadastrada com sucesso.");
		return redirect("/");
	}

	@Transactional
	public static Result participarDeViagem(Long id) {
		Usuario usuario = null;
		String email = session("email");

		if (email == null || (usuario = getUsuario(email)) == null) {
			return redirect("/login");
		}
		Viagem viagem = getViagem(id);

		if (viagem.getEstrategia() instanceof ViagemAberta) {
			//CASO USUARIO SEJA ADICIONADO
			if (viagem.adicionarParticipante(null, usuario)) {
				flash("success",
						"Voce esta na lista de participantes para a viagem de "
								+ viagem.getLocal());
			//CASO NAO SEJA ADICIONADO
			} else {
				flash("erro",
						"Nao foi possivel adicianar voce lista de participantes para a viagem de "
								+ viagem.getLocal());
				return redirect("/");
			}
		} else if (viagem.getEstrategia() instanceof ViagemLimitada) {
			String codigo = form().bindFromRequest().get("codigo");
			if (viagem.adicionarParticipante(codigo, usuario)) {
				flash("success",
						"Voce esta na lista de participantes para a viagem de "
								+ viagem.getLocal());
			} else {
				flash("erro",
						"Nao foi possivel adicianar voce lista de participantes para a viagem de "
								+ viagem.getLocal()
								+ ".\n Verifique o Codigo da Viagem.");
				return redirect("/");
			}

		} else if (viagem.getEstrategia() instanceof ViagemPrivada) {
			if (viagem.adicionarParticipante(null, usuario)) {
				flash("success",
						"Voce esta na lista de participantes para a viagem de "
								+ viagem.getLocal());
			} else {
				flash("erro",
						"Nao foi possivel adicionar voce lista de participantes para a viagem de "
								+ viagem.getLocal()
								+ ".\n Verifique se voce esta na lista de participantes autorizados pelo administrador");
				return redirect("/");
			}
		}
		//ATUALIZANDO USUARIO
		dao.merge(usuario);
		dao.flush();
		return redirect("/");
	}
	
	@Transactional
	public static Result resultadoPesquisa(){
		
		Usuario usuario = null;
		String email = session("email");

		if (email == null || (usuario = getUsuario(email)) == null) {
			return redirect("/login");
		}
		
		String pesquisa = form().bindFromRequest().get("pesquisa");
		
		List<Viagem> result = new ArrayList<Viagem>();
		
		for(Viagem viagem : getAllViagens()){			
			if(viagem.toString().toLowerCase().contains(pesquisa.toLowerCase())){
				result.add(viagem);
			}
		}
		return ok(resultadoPesquisaViagem.render("Viagens", usuario, criarMatriz(result)));
	}
	
	@Transactional
	public static Result resultadoPesquisaUsuario(){
		
		Usuario usuario = null;
		String email = session("email");

		if (email == null || (usuario = getUsuario(email)) == null) {
			return redirect("/login");
		}
		
		String pesquisa = form().bindFromRequest().get("pesquisa");
		
		List<Usuario> result = new ArrayList<Usuario>();
		
		for(Usuario user : getAllUsuarios()){			
			if(user.toString().toLowerCase().contains(pesquisa.toLowerCase())){
				result.add(user);
			}
		}
		return ok(resultadoPesquisaUsuario.render("Viagens", usuario, criarMatriz(result)));
	}
	
	@Transactional
	public static boolean estouNaListaPrivada(Long id) {
		Usuario usuario = null;
		String email = session("email");

		if (email == null || (usuario = getUsuario(email)) == null) {
			index();
		}
		Viagem viagem = getViagem(id);

		if (viagem.getEstrategia() instanceof ViagemPrivada) {
			return ((ViagemPrivada) viagem.getEstrategia()).getAutorizados().contains(usuario);
		} else {
			return true;
		}
	}

	@Transactional
	public static Result salvarAlteracoes(Long id) {
		Usuario usuario = null;
		String email = session("email");

		if (email == null || (usuario = getUsuario(email)) == null) {
			return redirect("/login");
		}

		Viagem viagem = getViagem(id);
		try {
			String tipo = form().bindFromRequest().get("TIPO_VIAGEM");

			if (tipo.equals("LIMITADA")) {
				String codigo = form().bindFromRequest().get("CODIGO_VIAGEM");

				viagem.setEstrategia((ViagemLimitada) persist(new ViagemLimitada(
						codigo)));

			} else if (tipo.equals("ABERTA")) {
				viagem.setEstrategia((ViagemAberta) persist(new ViagemAberta()));

			} else if (tipo.equals("PRIVADA")) {
				String lista = form().bindFromRequest().get(
						"PARTICIPANTES_VIAGEM");
				viagem.setEstrategia((ViagemPrivada) persist(new ViagemPrivada(
						getListaPrivada(lista))));
			} else {
				throw new Exception("Selecione o tipo de Viagem");
			}
			dao.merge(viagem);
			dao.merge(usuario);
			dao.flush();

		} catch (Exception e) {
			flash("erro", e.getMessage());
			return redirect("/viagens");
		}
		return redirect("/viagens");
	}

	private static List<Usuario> getListaPrivada(String lista) {
		String[] listaQuebrada = lista.split(",");
		List<Usuario> result = new ArrayList<Usuario>();

		for (int i = 0; i < listaQuebrada.length; i++) {

			if (getUsuario(listaQuebrada[i]) != null) {
				result.add(getUsuario(listaQuebrada[i]));
			}
		}
		
		return result;
	}

	@Transactional
	private static void salvarUsuario(Usuario usuario) throws Exception {

		if (getAllUsuarios().contains(usuario)) {
			throw new Exception("E-mail já cadastrado");
		}
		dao.persist(usuario);
		dao.flush();
	}

	@Transactional
	private static void salvarViagem(Viagem viagem) throws Exception {

		if (getAllViagens().contains(viagem)) {
			throw new Exception("Viagem já cadastrada");
		}
		dao.persist(viagem);
		dao.flush();
	}

	@Transactional
	public static List<Viagem> getAllViagens() {
		return dao.findAllByClassName("Viagem");
	}

	@Transactional
	public static List<Usuario> getAllUsuarios() {
		return dao.findAllByClassName("Usuario");
	}

	@Transactional
	private static Usuario getUsuario(String email) {
		return dao.findByEntityId(Usuario.class, email);
	}

	@Transactional
	private static Viagem getViagem(Long id) {
		return dao.findByEntityId(Viagem.class, id);
	}

	@Transactional
	private static <T> Object persist(Object object) {
		List<T> result = dao.findAllByClassName(object.getClass().getSimpleName());
		
		if (!result.contains(object)) {
			dao.persist(object);
			dao.flush();
		}
		return getObjectBD(object);
	}

	@Transactional
	private static <T> Object getObjectBD(Object object) {
		List<T> result = dao.findAllByClassName(object.getClass().getSimpleName());
		for (Object obj : result) {
			if (obj.equals(object)) {
				return obj;
			}
		}
		return null;
	}

	private static Date getDataFormatada(String data) throws Exception {
		try {
			String[] splitData = data.split("-");
			Integer dia = Integer.parseInt(splitData[2]);
			Integer mes = Integer.parseInt(splitData[1]);
			Integer ano = Integer.parseInt(splitData[0]);
			return new GregorianCalendar(ano, mes - 1, dia).getTime();
		} catch (Exception e) {
			throw new Exception("Data Inválida");
		}
	}
}
