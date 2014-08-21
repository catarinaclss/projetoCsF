package models;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;



@Entity(name = "Viagem")
public class Viagem {
	
	@OneToMany(cascade = CascadeType.ALL)
	@JoinTable
	private List<Usuario> usuarios;
	
	@OneToOne(cascade = CascadeType.ALL)
	private Privacidade privacidade;

	
	
	/**
	 * Construtor de viagem, por default, possui inicialmente privacidade publica e uma lista
	 * de seus participantes 
	 */
	public Viagem(){
		this.usuarios = new ArrayList<Usuario>();
		this.privacidade = new Publica();
	}

	/**
	 * Insere usuario na lista de participantes da viagem
	 * @param usuario - solicitante de participação na viagem
	 */
	public void participar(Usuario usuario){
		this.usuarios.add(usuario);
	}
	
	public void setPrivacidade(Privacidade privacidade){
		this.privacidade = privacidade;
	}
	
	public Privacidade getPrivacidade(){
		return this.privacidade;
	}
	
}
