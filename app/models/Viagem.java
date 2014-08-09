package models;

import java.util.ArrayList;
import java.util.List;

public class Viagem {
	
	List<Usuario> usuarios; 
	Privacidade privacidade;
	
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
	
	
}
