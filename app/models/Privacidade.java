package models;

public abstract class Privacidade {

	public abstract boolean podeParticipar(Usuario usuario, String senha);
	
	public abstract boolean podeVisualizarViagem(Usuario usuario);
	
}
