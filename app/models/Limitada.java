package models;

public class Limitada extends Publica {
	
	private String senhaDeAcesso;
	
	/**
	 * Construtor de viagem publica, porem de permissao para participação limitada e restringida por senha
	 * @param senha
	 */
	public Limitada(String senha){
		this.senhaDeAcesso = senha;
	}
	
	/**
	 * O usuario so podera participar da viagem se conhecer a senha de acesso
	 */
	@Override
	public boolean podeParticipar(Usuario usuario, String senha) {
		return this.senhaDeAcesso.equals(senha);
	} 

}
