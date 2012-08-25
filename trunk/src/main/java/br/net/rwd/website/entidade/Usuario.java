package br.net.rwd.website.entidade;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.GrantedAuthorityImpl;

@Entity
@Table(name = "usuarios")
// @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Usuario implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer usu_cod;
	private String usu_nome;
	private String usu_email;
	private String usu_senha;
	private String usu_endereco;
	private String usu_numero;
	private String usu_cidade;
	private String usu_cep;
	private String usu_estado;
	private String usu_perfil;
	private boolean usu_status;
	@ManyToMany
	@JoinTable(name = "usuarioperfil", joinColumns = @JoinColumn(name = "usu_cod"), inverseJoinColumns = @JoinColumn(name = "per_cod"))
    private List<Perfil> perfis = new LinkedList<Perfil>();

	public Usuario() {
		super();
	}

	public Integer getUsu_cod() {
		return usu_cod;
	}

	public void setUsu_cod(Integer usu_cod) {
		this.usu_cod = usu_cod;
	}

	public String getUsu_nome() {
		return usu_nome;
	}

	public void setUsu_nome(String usu_nome) {
		this.usu_nome = usu_nome;
	}

	public String getUsu_email() {
		return usu_email;
	}

	public void setUsu_email(String usu_email) {
		this.usu_email = usu_email;
	}

	public String getUsu_senha() {
		return usu_senha;
	}

	public void setUsu_senha(String usu_senha) {
		this.usu_senha = usu_senha;
	}

	public String getUsu_endereco() {
		return usu_endereco;
	}

	public void setUsu_endereco(String usu_endereco) {
		this.usu_endereco = usu_endereco;
	}

	public String getUsu_numero() {
		return usu_numero;
	}

	public void setUsu_numero(String usu_numero) {
		this.usu_numero = usu_numero;
	}

	public String getUsu_cidade() {
		return usu_cidade;
	}

	public void setUsu_cidade(String usu_cidade) {
		this.usu_cidade = usu_cidade;
	}

	public String getUsu_cep() {
		return usu_cep;
	}

	public void setUsu_cep(String usu_cep) {
		this.usu_cep = usu_cep;
	}

	public String getUsu_estado() {
		return usu_estado;
	}

	public void setUsu_estado(String usu_estado) {
		this.usu_estado = usu_estado;
	}

	public String getUsu_perfil() {
		return usu_perfil;
	}

	public void setUsu_perfil(String usu_perfil) {
		this.usu_perfil = usu_perfil;
	}

	public boolean isUsu_status() {
		return usu_status;
	}

	public void setUsu_status(boolean usu_status) {
		this.usu_status = usu_status;
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
		result = prime * result + ((usu_cod == null) ? 0 : usu_cod.hashCode());
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
		final Usuario other = (Usuario) obj;
		if (usu_cod == null) {
			if (other.usu_cod != null)
				return false;
		} else if (!usu_cod.equals(other.usu_cod))
			return false;
		return true;
	}
	
	@SuppressWarnings("deprecation")
	@Transient
	public Collection<GrantedAuthority> getAuthorities() {
		List<GrantedAuthority> lista = new ArrayList<GrantedAuthority>();
		for(Perfil perfil : getPerfis()) {
			lista.add(new GrantedAuthorityImpl(perfil.getAuthority()));
		}
		return lista;
	}

	@Transient
	public String getPassword() {
		return this.usu_senha;
	}

	@Transient
	public String getUsername() {
		return this.usu_email;
	}

	@Transient
	public boolean isAccountNonExpired() {
		return this.usu_status;
	}

	@Transient
	public boolean isAccountNonLocked() {
		return this.usu_status;
	}

	@Transient
	public boolean isCredentialsNonExpired() {
		return this.usu_status;
	}

	@Transient
	public boolean isEnabled() {
		return this.usu_status;
	}

}
