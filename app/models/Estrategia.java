package models;

import java.util.Collection;

import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;

@Entity
@Inheritance(strategy=InheritanceType.JOINED)
@DiscriminatorColumn(name="VIAGEM_ESTRATEGIA_TYPE")
@Table(name="VIAGEM_ESTRATEGIA")
public abstract class Estrategia {
	
	@Id
	@GeneratedValue
	private Long id;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	public abstract boolean adicionarParticipante(Collection<Usuario> participantes, String codigo, Usuario usuario);
	
	@Override
	public abstract String toString();
	
	@Override
	public abstract boolean equals(Object obj);
	
}
