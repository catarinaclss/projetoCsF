package models;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.google.common.base.Objects;

@Entity
@Table
public class Usuario {

	@Id
	private String email;
	private String nome;
	private String senha;

	@OneToMany
	private List<Viagem> viagens = new ArrayList<Viagem>();
	
	public Usuario() {
		
	}
	
	public Usuario( String nome, String email, String Senha, List<Viagem> viagens) throws Exception{
		isSetEmail(email);
		isSetNome(nome);
		isSetSenha(Senha);
		isViagensValida(viagens);
	}
	
	
	//INICIO METODOS PARA VIAGENS
	public boolean adicionarViagem(Viagem viagem){
		return viagens.contains(viagem) ? false : viagens.add(viagem);
	}

	public List<Viagem> getViagens() {
		return viagens;
	}

	public void setViagens(List<Viagem> viagens) throws Exception {
		isViagensValida(viagens);
	}
	
	private void isViagensValida(List<Viagem> viagens) throws Exception{
		if(viagens == null){
			throw new Exception("Viagens Invalida!");
		}
		this.viagens = viagens;
	}
	//FIM METODOS PARA VIAGENS
	
	//INICIO METODOS PARA NOME
	public String getNome() {
		return nome;
	}
	
	public void setNome(String nome) throws Exception {
		isSetNome(nome);
	}

	private void isSetNome(String nome) throws Exception {
		if (nome == null || nome.trim().isEmpty()) {
			throw new Exception("Nome Invalido!");
		}
		this.nome = nome;
	}
	//FIM METODOS PARA NOME
	
	//INICIO METODOS PARA EMAIL
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) throws Exception {
		isSetEmail(email);
	}

	private void isSetEmail(String email) throws Exception {
		if (email == null || email.trim().isEmpty()) {
			throw new Exception("E-mail Invalido!");
		}
		this.email = email;
	}
	//FIM METODOS PARA EMAIL
	
	//INICIO METODOS PARA SENHA
	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) throws Exception {
		isSetSenha(senha);
	}
	
	private void isSetSenha(String senha) throws Exception{
		if (senha == null || senha.trim().isEmpty()) {
			throw new Exception("Senha Invalida!");
		}	
		this.senha = senha;
		this.senha = String.valueOf(this.hashCode());
	}
	//FIM METODOS PARA SENHA
	
	@Override
	public int hashCode() {
		return Objects.hashCode(email, senha);
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null || !(obj instanceof Usuario)) {
			return false;
		}
		Usuario other = (Usuario) obj;
		if (email == null) {
			if (other.email != null) {
				return false;
			}
		//nao diferencia maiuscula de minuscula
		} else if (!email.equalsIgnoreCase(other.email)) {
			return false;
		}
		return true;
	}
	
	@Override
	public String toString() {
		return String.format("Nome: " + nome +", E-mail: " + email);
	}
}
