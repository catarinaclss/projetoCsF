package models;

public class Publica extends Privacidade{

	/**
	 * Verifica se o usuario pode participar da viagem. 
	 * Sendo publica, qualquer usuario poderá participar.
	 */
	@Override
	public boolean podeParticipar(Usuario usuario, String senha) {
		return true;
	}

	/**
	 * Todos os usuarios do sistema poderao visualizar a viagem 
	 */
	@Override
	public boolean podeVisualizarViagem(Usuario usuario) {
		return true;
	}

	public String toString(){
		return "Publica";
	}
}
