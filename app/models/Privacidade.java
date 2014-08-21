package models;

import javax.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class Privacidade {

	public abstract boolean podeParticipar(Usuario usuario, String senha);
	
	public abstract boolean podeVisualizarViagem(Usuario usuario);
	
	public abstract String toString();
	
}
