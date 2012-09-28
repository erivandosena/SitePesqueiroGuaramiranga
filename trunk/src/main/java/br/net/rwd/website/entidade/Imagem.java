package br.net.rwd.website.entidade;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "imagens")
public class Imagem implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer ima_cod;
	private String ima_descricao;
	private String ima_normal;
	private String ima_mini;
	@ManyToOne
    @JoinColumn(name = "pub_cod")
    private Evento evento;

	public Integer getIma_cod() {
		return ima_cod;
	}

	public void setIma_cod(Integer ima_cod) {
		this.ima_cod = ima_cod;
	}

	public String getIma_descricao() {
		return ima_descricao;
	}

	public void setIma_descricao(String ima_descricao) {
		this.ima_descricao = ima_descricao;
	}

	public String getIma_normal() {
		return ima_normal;
	}

	public void setIma_normal(String ima_normal) {
		this.ima_normal = ima_normal;
	}

	public String getIma_mini() {
		return ima_mini;
	}

	public void setIma_mini(String ima_mini) {
		this.ima_mini = ima_mini;
	}

	public Evento getEvento() {
		return evento;
	}

	public void setEvento(Evento evento) {
		this.evento = evento;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((ima_cod == null) ? 0 : ima_cod.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Imagem other = (Imagem) obj;
		if (ima_cod == null) {
			if (other.ima_cod != null)
				return false;
		} else if (!ima_cod.equals(other.ima_cod))
			return false;
		return true;
	}

}
