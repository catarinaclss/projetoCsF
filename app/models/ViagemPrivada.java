package models;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity
@DiscriminatorValue("VP")
@Table(name="VIAGEM_ESTRATEGIA_PRIVADA")
public class ViagemPrivada extends Estrategia {
	
	@ManyToMany
	private List<Usuario> autorizados = new ArrayList<Usuario>();
	
	public ViagemPrivada() {
		
	}
	
	public ViagemPrivada(List<Usuario> autorizados) throws Exception {
		isListaValida(autorizados);
	}
	
	public List<Usuario> getAutorizados() {
		return autorizados;
	}
	
	public void setAutorizados(List<Usuario> autorizados) throws Exception {
		isListaValida(autorizados);
	}

	private void isListaValida(List<Usuario> autorizados) throws Exception {
		if (autorizados == null) {
			throw new Exception("Lista Nula!");
		} else {
			this.autorizados = autorizados;
		}
	}
	
	@Override
	public boolean adicionarParticipante(Collection<Usuario> participantes, String codigo, Usuario usuario) {
		//verifica se usuario esta na lista de participantes autorizados
		if(autorizados.contains(usuario)){
			//verifica se usuario ja esta participando da viagem
			if(participantes.contains(usuario)){
				return false;
			}else{
				return participantes.add(usuario);
			}
		}else{
			return false;
		}
	}

	@Override
	public String toString() {
		return new String("PRIVADA");
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null || !(obj instanceof ViagemPrivada)) {
			return false;
		}
		ViagemPrivada other = (ViagemPrivada) obj;
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
