package br.net.rwd.website.entidade;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
	private String per_role;

	@Transient
	public String getAuthority() {
		return this.per_role;
	}

	@Transient
	public int compareTo(Object o) {
		return this.compareTo(o);
	}

	public Perfil() {
		super();
	}

	public Integer getPer_cod() {
		return per_cod;
	}

	public void setPer_cod(Integer per_cod) {
		this.per_cod = per_cod;
	}
	
	public String getPer_role() {
		return per_role;
	}

	public void setPer_role(String per_role) {
		this.per_role = per_role;
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


}
