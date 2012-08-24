package br.net.rwd.website.entidade;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "website")
public class Site implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer web_cod;
	private String web_titulo;
	private String web_descricao;
	private String web_slogan;
	private String web_proprietario;
	private String web_endereco;
	private String web_numero;
	private String web_bairro;
	private String web_cep;
	private String web_estado;
	private String web_cidade;
	private String web_telefone;
	private String web_email;
	private String web_site;
	private String web_orkut;
	private String web_twitter;
	private String web_facebook;
	private String web_flickr;
	private String web_linkedin;
	private String web_myspace;
	private String web_vimeo;
	private String web_youtube;
	private String web_blog;
	private String web_localizacao;
	private String web_messenger;
	private String web_skype;
	private byte[] web_img_padrao;
	private String web_img_mime;
	private String web_img_nome;

	public Integer getWeb_cod() {
		return web_cod;
	}

	public void setWeb_cod(Integer web_cod) {
		this.web_cod = web_cod;
	}

	public String getWeb_titulo() {
		return web_titulo;
	}

	public void setWeb_titulo(String web_titulo) {
		this.web_titulo = web_titulo;
	}

	public String getWeb_descricao() {
		return web_descricao;
	}

	public void setWeb_descricao(String web_descricao) {
		this.web_descricao = web_descricao;
	}

	public String getWeb_slogan() {
		return web_slogan;
	}

	public void setWeb_slogan(String web_slogan) {
		this.web_slogan = web_slogan;
	}

	public String getWeb_proprietario() {
		return web_proprietario;
	}

	public void setWeb_proprietario(String web_proprietario) {
		this.web_proprietario = web_proprietario;
	}

	public String getWeb_endereco() {
		return web_endereco;
	}

	public void setWeb_endereco(String web_endereco) {
		this.web_endereco = web_endereco;
	}

	public String getWeb_numero() {
		return web_numero;
	}

	public void setWeb_numero(String web_numero) {
		this.web_numero = web_numero;
	}

	public String getWeb_bairro() {
		return web_bairro;
	}

	public void setWeb_bairro(String web_bairro) {
		this.web_bairro = web_bairro;
	}

	public String getWeb_cep() {
		return web_cep;
	}

	public void setWeb_cep(String web_cep) {
		this.web_cep = web_cep;
	}

	public String getWeb_estado() {
		return web_estado;
	}

	public void setWeb_estado(String web_estado) {
		this.web_estado = web_estado;
	}

	public String getWeb_cidade() {
		return web_cidade;
	}

	public void setWeb_cidade(String web_cidade) {
		this.web_cidade = web_cidade;
	}

	public String getWeb_telefone() {
		return web_telefone;
	}

	public void setWeb_telefone(String web_telefone) {
		this.web_telefone = web_telefone;
	}

	public String getWeb_email() {
		return web_email;
	}

	public void setWeb_email(String web_email) {
		this.web_email = web_email;
	}

	public String getWeb_site() {
		return web_site;
	}

	public void setWeb_site(String web_site) {
		this.web_site = web_site;
	}

	public String getWeb_orkut() {
		return web_orkut;
	}

	public void setWeb_orkut(String web_orkut) {
		this.web_orkut = web_orkut;
	}

	public String getWeb_twitter() {
		return web_twitter;
	}

	public void setWeb_twitter(String web_twitter) {
		this.web_twitter = web_twitter;
	}

	public String getWeb_facebook() {
		return web_facebook;
	}

	public void setWeb_facebook(String web_facebook) {
		this.web_facebook = web_facebook;
	}

	public String getWeb_flickr() {
		return web_flickr;
	}

	public void setWeb_flickr(String web_flickr) {
		this.web_flickr = web_flickr;
	}

	public String getWeb_linkedin() {
		return web_linkedin;
	}

	public void setWeb_linkedin(String web_linkedin) {
		this.web_linkedin = web_linkedin;
	}

	public String getWeb_myspace() {
		return web_myspace;
	}

	public void setWeb_myspace(String web_myspace) {
		this.web_myspace = web_myspace;
	}

	public String getWeb_vimeo() {
		return web_vimeo;
	}

	public void setWeb_vimeo(String web_vimeo) {
		this.web_vimeo = web_vimeo;
	}

	public String getWeb_youtube() {
		return web_youtube;
	}

	public void setWeb_youtube(String web_youtube) {
		this.web_youtube = web_youtube;
	}

	public String getWeb_blog() {
		return web_blog;
	}

	public void setWeb_blog(String web_blog) {
		this.web_blog = web_blog;
	}

	public String getWeb_localizacao() {
		return web_localizacao;
	}

	public void setWeb_localizacao(String web_localizacao) {
		this.web_localizacao = web_localizacao;
	}

	public String getWeb_messenger() {
		return web_messenger;
	}

	public void setWeb_messenger(String web_messenger) {
		this.web_messenger = web_messenger;
	}

	public String getWeb_skype() {
		return web_skype;
	}

	public void setWeb_skype(String web_skype) {
		this.web_skype = web_skype;
	}

	public byte[] getWeb_img_padrao() {
		return web_img_padrao;
	}

	public void setWeb_img_padrao(byte[] web_img_padrao) {
		this.web_img_padrao = web_img_padrao;
	}

	public String getWeb_img_mime() {
		return web_img_mime;
	}

	public void setWeb_img_mime(String web_img_mime) {
		this.web_img_mime = web_img_mime;
	}

	public String getWeb_img_nome() {
		return web_img_nome;
	}

	public void setWeb_img_nome(String web_img_nome) {
		this.web_img_nome = web_img_nome;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((web_cod == null) ? 0 : web_cod.hashCode());
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
		final Site other = (Site) obj;
		if (web_cod == null) {
			if (other.web_cod != null)
				return false;
		} else if (!web_cod.equals(other.web_cod))
			return false;
		return true;
	}

}
