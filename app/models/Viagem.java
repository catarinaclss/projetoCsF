package models;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table
public class Viagem implements Comparable<Viagem>{

	@Id
	@GeneratedValue
	private Long id;

	@Temporal(TemporalType.DATE)
	private Date data;

	private String local;

	private String descricao;

	@OneToOne
	private Estrategia estrategia;

	@ManyToMany
	private List<Usuario> participantes = new ArrayList<Usuario>();

	public Viagem() {
		participantes = new ArrayList<Usuario>();
	}
	
	public Viagem (Date data, String local, String descricao, Estrategia estrategia, List<Usuario> participantes) throws Exception{
		isDataValida(data);
		isLocalValido(local);
		isDescricaoValida(descricao);
		isParticipantesValido(participantes);
		this.estrategia = estrategia;
	}


	private void isParticipantesValido(List<Usuario> participantes) throws Exception {
		if (participantes == null) {
			throw new Exception("Lista de Participantes Invalida!");
		} else {
			this.participantes = participantes;
		}
	}

	private void isLocalValido(String local) throws Exception {
		if (local == null || local.trim().isEmpty()) {
			throw new Exception("Local nulo ou vazio!");
		} else {
			this.local = local;
		}
	}

	private void isDescricaoValida(String descricao) throws Exception {
		if (descricao == null || descricao.trim().isEmpty()) {
			throw new Exception("Descrição Nula ou Vazia!");
		} else {
			this.descricao = descricao;
		}
	}

	private void isDataValida(Date data) throws Exception {
		if (data == null) {
			throw new Exception("Data Nula ou Vazia!");
		} else if (data.compareTo(Calendar.getInstance().getTime()) < 0) {
			throw new Exception("Data já Ultrapassada!");
		} else {
			this.data = data;
		}
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getData() {
		return data;
	}

	public void setData(Date data) throws Exception {
		isDataValida(data);
	}

	public String getLocal() {
		return local;
	}

	public void setLocal(String local) throws Exception{
		isLocalValido(local);
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) throws Exception{
		isDescricaoValida(descricao);
	}

	public Estrategia getEstrategia() {
		return estrategia;
	}

	public void setEstrategia(Estrategia estrategia) {
		this.estrategia = estrategia;
	}

	public List<Usuario> getParticipantes() {
		return participantes;
	}

	public void setParticipantes(List<Usuario> participantes) throws Exception{
		isParticipantesValido(participantes);
	}
	//estrategia
	public boolean adicionarParticipante(String codigo, Usuario usuario){
		return estrategia.adicionarParticipante(participantes, codigo, usuario);
	}

	public void mudarParaAberta() {
		estrategia = new ViagemAberta();
	}

	public void mudarParaLimitada(String codigo) throws Exception {
		estrategia = new ViagemLimitada(codigo);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((data == null) ? 0 : data.hashCode());
		result = prime * result
				+ ((descricao == null) ? 0 : descricao.hashCode());
		result = prime * result
				+ ((estrategia == null) ? 0 : estrategia.hashCode());
		result = prime * result + ((local == null) ? 0 : local.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof Viagem)) {
			return false;
		}
		Viagem other = (Viagem) obj;
		if (data == null) {
			if (other.data != null) {
				return false;
			}
		} else if (!data.equals(other.data)) {
			return false;
		}
		if (descricao == null) {
			if (other.descricao != null) {
				return false;
			}
		} else if (!descricao.equalsIgnoreCase(other.descricao)) {
			return false;
		}
		if (estrategia == null) {
			if (other.estrategia != null) {
				return false;
			}
		} else if (!estrategia.equals(other.estrategia)) {
			return false;
		}
		if (local == null) {
			if (other.local != null) {
				return false;
			}
		} else if (!local.equalsIgnoreCase(other.local)) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "" + data + " " + local  + " " + estrategia + " " + descricao + "";
	}

	@Override
	public int compareTo(Viagem o) {
		return this.data.compareTo(o.getData());
	}
}