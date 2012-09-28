package br.net.rwd.website.entidade;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "paginas")
public class Pagina implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer pag_cod;
	private String pag_titulo;
	private String pag_legenda;
	private String pag_conteudo;
	private String pag_extra;
	private String pag_posicao;

	public Integer getPag_cod() {
		return pag_cod;
	}

	public void setPag_cod(Integer pag_cod) {
		this.pag_cod = pag_cod;
	}

	public String getPag_titulo() {
		return pag_titulo;
	}

	public void setPag_titulo(String pag_titulo) {
		this.pag_titulo = pag_titulo;
	}

	public String getPag_legenda() {
		return pag_legenda;
	}

	public void setPag_legenda(String pag_legenda) {
		this.pag_legenda = pag_legenda;
	}

	public String getPag_conteudo() {
		return pag_conteudo;
	}

	public void setPag_conteudo(String pag_conteudo) {
		this.pag_conteudo = pag_conteudo;
	}

	public String getPag_extra() {
		return pag_extra;
	}

	public void setPag_extra(String pag_extra) {
		this.pag_extra = pag_extra;
	}

	public String getPag_posicao() {
		return pag_posicao;
	}

	public void setPag_posicao(String pag_posicao) {
		this.pag_posicao = pag_posicao;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((pag_cod == null) ? 0 : pag_cod.hashCode());
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
		final Pagina other = (Pagina) obj;
		if (pag_cod == null) {
			if (other.pag_cod != null)
				return false;
		} else if (!pag_cod.equals(other.pag_cod))
			return false;
		return true;
	}	
}
