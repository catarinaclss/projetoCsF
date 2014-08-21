package models;

import java.util.ArrayList;
import java.util.List;

public class Privada extends Privacidade {
	
	private List<Usuario> usuariosPermitidos;
	
	/**
	 * Construtor para viagem do tipo privada, na qual possui uma lista de usuarios pre definidos, autorizados
	 * a visualizar e participar da viagem 
	 */
	public Privada(List<Usuario> usuariosPermitidos){
		this.usuariosPermitidos = usuariosPermitidos;
	}
	
	/**
	 * Usuario podera visualizar a viagem se o mesmo estiver na lista de usuarios permitidos pelo
	 * organizador.
	 */
	@Override
	public boolean podeVisualizarViagem(Usuario usuario) {
		return usuariosPermitidos.contains(usuario);
	}

	public String toString(){
		return "Privada";
	}
	/**
	 * O usuario tem acesso liberado para participar da viagem, caso queira, se a mesma estiver visivel no sistema.
	 */
	@Override
	public boolean podeParticipar(Usuario usuario, String senha) {
		return podeVisualizarViagem(usuario);
	}
}
