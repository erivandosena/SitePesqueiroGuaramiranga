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
import javax.persistence.Transient;

import br.net.rwd.website.util.NormalizaString;

@Entity
@Table(name = "galerias")
public class Galeria implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer gal_cod;
	@Temporal(TemporalType.DATE)
	@Column(columnDefinition = "date")
	private Date gal_data;
	private String gal_titulo;
	private String gal_descricao;
	private String gal_foto;
	@OneToMany(mappedBy = "galeria")
	private List<Foto> fotos = new LinkedList<Foto>();
	
	@Transient
	public String getTitulo_normalizado() {
		return NormalizaString.normalizar(this.gal_titulo);
	}

	public Integer getGal_cod() {
		return gal_cod;
	}

	public void setGal_cod(Integer gal_cod) {
		this.gal_cod = gal_cod;
	}

	public Date getGal_data() {
		return gal_data;
	}

	public void setGal_data(Date gal_data) {
		this.gal_data = gal_data;
	}

	public String getGal_titulo() {
		return gal_titulo;
	}

	public void setGal_titulo(String gal_titulo) {
		this.gal_titulo = gal_titulo;
	}

	public String getGal_descricao() {
		return gal_descricao;
	}

	public void setGal_descricao(String gal_descricao) {
		this.gal_descricao = gal_descricao;
	}

	public String getGal_foto() {
		return gal_foto;
	}

	public void setGal_foto(String gal_foto) {
		this.gal_foto = gal_foto;
	}

	public List<Foto> getFotos() {
		return fotos;
	}

	public void setFotos(List<Foto> fotos) {
		this.fotos = fotos;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((gal_cod == null) ? 0 : gal_cod.hashCode());
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
		Galeria other = (Galeria) obj;
		if (gal_cod == null) {
			if (other.gal_cod != null)
				return false;
		} else if (!gal_cod.equals(other.gal_cod))
			return false;
		return true;
	}

}
