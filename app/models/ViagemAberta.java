package models;

import java.util.Collection;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;


@Entity
@DiscriminatorValue("VA")
@Table(name="VIAGEM_ESTRATEGIA_ABERTA")
public class ViagemAberta extends Estrategia{
	
	
	public ViagemAberta() {
		super();
	}
	
	@Override
	public boolean adicionarParticipante(Collection<Usuario> participantes, String codigo, Usuario usuario){
		//verificando se usuario ja esta na colecao de participantes
		if(participantes.contains(usuario)){
			return false;
		//caso nao esteja adiciona a colecao
		}else{
			return participantes.add(usuario);
		}
	}
	
	@Override
	public String toString() {
		return new String("ABERTA");
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null || !(obj instanceof ViagemAberta)) {
			return false;
		}
		ViagemAberta other = (ViagemAberta) obj;
		if (obj.toString() == null) {
			if (other.toString() != null) {
				return false;
			}
		} else if (!toString().equals(other.toString())) {
			return false;
		}
		return true;
	}
}
