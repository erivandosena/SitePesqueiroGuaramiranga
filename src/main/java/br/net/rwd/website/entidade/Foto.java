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
@Table(name = "fotos")
public class Foto implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer fot_cod;
	private String fot_descricao;
	private String fot_foto;
	@ManyToOne
	@JoinColumn(name = "gal_cod")
	private Galeria galeria;

	public Integer getFot_cod() {
		return fot_cod;
	}

	public void setFot_cod(Integer fot_cod) {
		this.fot_cod = fot_cod;
	}

	public String getFot_descricao() {
		return fot_descricao;
	}

	public void setFot_descricao(String fot_descricao) {
		this.fot_descricao = fot_descricao;
	}

	public String getFot_foto() {
		return fot_foto;
	}

	public void setFot_foto(String fot_foto) {
		this.fot_foto = fot_foto;
	}

	public Galeria getGaleria() {
		return galeria;
	}

	public void setGaleria(Galeria galeria) {
		this.galeria = galeria;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((fot_cod == null) ? 0 : fot_cod.hashCode());
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
		Foto other = (Foto) obj;
		if (fot_cod == null) {
			if (other.fot_cod != null)
				return false;
		} else if (!fot_cod.equals(other.fot_cod))
			return false;
		return true;
	}

}
