package br.net.rwd.website.entidade;

import java.io.Serializable;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "publicacoes")
public class Evento implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer pub_cod;
	@Temporal(TemporalType.DATE)
	@Column(columnDefinition = "date")
	private Date pub_data;
	@Column(columnDefinition = "date")
	private Date pub_dataalteracao;
	private String pub_titulo;
	private String pub_sumario;
	private String pub_conteudo;
	private String pub_imagem;
	@OneToMany(mappedBy="evento")
    private List<Imagem> imagens = new LinkedList<Imagem>();

	public Integer getPub_cod() {
		return pub_cod;
	}

	public void setPub_cod(Integer pub_cod) {
		this.pub_cod = pub_cod;
	}

	public Date getPub_data() {
		return pub_data;
	}

	public void setPub_data(Date pub_data) {
		this.pub_data = pub_data;
	}

	public Date getPub_dataalteracao() {
		return pub_dataalteracao;
	}

	public void setPub_dataalteracao(Date pub_dataalteracao) {
		this.pub_dataalteracao = pub_dataalteracao;
	}

	public String getPub_titulo() {
		return pub_titulo;
	}

	public void setPub_titulo(String pub_titulo) {
		this.pub_titulo = pub_titulo;
	}

	public String getPub_sumario() {
		return pub_sumario;
	}

	public void setPub_sumario(String pub_sumario) {
		this.pub_sumario = pub_sumario;
	}

	public String getPub_conteudo() {
		return pub_conteudo;
	}

	public void setPub_conteudo(String pub_conteudo) {
		this.pub_conteudo = pub_conteudo;
	}

	public String getPub_imagem() {
		return pub_imagem;
	}

	public void setPub_imagem(String pub_imagem) {
		this.pub_imagem = pub_imagem;
	}

	public List<Imagem> getImagens() {
		return imagens;
	}

	public void setImagens(List<Imagem> imagens) {
		this.imagens = imagens;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((pub_cod == null) ? 0 : pub_cod.hashCode());
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
		Evento other = (Evento) obj;
		if (pub_cod == null) {
			if (other.pub_cod != null)
				return false;
		} else if (!pub_cod.equals(other.pub_cod))
			return false;
		return true;
	}

}
