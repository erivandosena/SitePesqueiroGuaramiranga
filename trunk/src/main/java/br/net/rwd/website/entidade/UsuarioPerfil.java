package br.net.rwd.website.entidade;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

//@Entity
//@Table(name = "usuariosperfis")
public class UsuarioPerfil implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer usp_cod;
	
	@ManyToOne
	@JoinColumn(name = "usu_cod")
	private List<Usuario> usuarios = new LinkedList<Usuario>();
	
	@ManyToOne
	@JoinColumn(name = "per_cod")
	private List<Perfil> perfis = new LinkedList<Perfil>();

	public Integer getUsp_cod() {
		return usp_cod;
	}

	public void setUsp_cod(Integer usp_cod) {
		this.usp_cod = usp_cod;
	}

	public List<Usuario> getUsuarios() {
		return usuarios;
	}

	public void setUsuarios(List<Usuario> usuarios) {
		this.usuarios = usuarios;
	}

	public List<Perfil> getPerfis() {
		return perfis;
	}

	public void setPerfis(List<Perfil> perfis) {
		this.perfis = perfis;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((usp_cod == null) ? 0 : usp_cod.hashCode());
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
		UsuarioPerfil other = (UsuarioPerfil) obj;
		if (usp_cod == null) {
			if (other.usp_cod != null)
				return false;
		} else if (!usp_cod.equals(other.usp_cod))
			return false;
		return true;
	}

}
