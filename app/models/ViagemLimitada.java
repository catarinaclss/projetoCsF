package models;

import java.util.Collection;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@DiscriminatorValue("VL")
@Table(name = "VIAGEM_ESTRATEGIA_LIMITADA")
public class ViagemLimitada extends Estrategia {

	private String codigo;

	public ViagemLimitada() {

	}

	public ViagemLimitada(String codigo) throws Exception {
		isCodigoValido(codigo);
	}

	@Override
	public boolean adicionarParticipante(
			Collection<Usuario> todosParticipantes, String codigo, Usuario usuario) {

		// verificando se codigo sao iguais
		if (this.codigo.equals(codigo)) {
			//verificando se usuario ja esta participando da viagem
			if(todosParticipantes.contains(usuario)){
				return false;
			}else{
				return todosParticipantes.add(usuario);
			}
		} else {
			return false;
		}
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) throws Exception {
		isCodigoValido(codigo);
	}

	private void isCodigoValido(String codigo) throws Exception {
		if (codigo == null || codigo.trim().isEmpty()) {
			throw new Exception("Código Nulo ou Vazio!");
		} else {
			this.codigo = codigo;
		}
	}

	@Override
	public String toString() {
		return new String("LIMITADA");
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null || !(obj instanceof ViagemLimitada)) {
			return false;
		}
		ViagemLimitada other = (ViagemLimitada) obj;
		if (codigo == null) {
			if (other.codigo != null) {
				return false;
			}
		} else if (!codigo.equals(other.codigo)) {
			return false;
		}
		return true;
	}
}
