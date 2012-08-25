package br.net.rwd.website.entidade;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.springframework.security.core.GrantedAuthority;

@Entity
@Table(name = "perfis")
public class Perfil implements Serializable, GrantedAuthority {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer per_cod;
	private String per_nome;
	@ManyToMany(fetch=FetchType.EAGER)
	@JoinTable(name = "usuarioperfil", joinColumns = @JoinColumn(name = "per_cod"), inverseJoinColumns = @JoinColumn(name = "usu_cod"))
	private List<Usuario> usuarios = new ArrayList<Usuario>();

	public Perfil() {
		super();
	}

	public Integer getPer_cod() {
		return per_cod;
	}

	public void setPer_cod(Integer per_cod) {
		this.per_cod = per_cod;
	}

	public String getPer_nome() {
		return per_nome;
	}

	public void setPer_nome(String per_nome) {
		this.per_nome = per_nome;
	}

	public List<Usuario> getUsuarios() {
		return usuarios;
	}

	public void setUsuarios(List<Usuario> usuarios) {
		this.usuarios = usuarios;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((per_cod == null) ? 0 : per_cod.hashCode());
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
		Perfil other = (Perfil) obj;
		if (per_cod == null) {
			if (other.per_cod != null)
				return false;
		} else if (!per_cod.equals(other.per_cod))
			return false;
		return true;
	}

	@Transient
	@Override
	public String getAuthority() {
		return this.per_nome;
	}

	@Transient
	public int compareTo(Object o) {
		return this.compareTo(o);
	}
	
}
