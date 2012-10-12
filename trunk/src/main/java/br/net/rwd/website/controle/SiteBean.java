package br.net.rwd.website.controle;

import java.io.Serializable;
import java.util.Calendar;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.event.AjaxBehaviorEvent;

import br.net.rwd.website.entidade.Site;
import br.net.rwd.website.servico.SiteServico;

@ManagedBean(name = "siteBean")
@ViewScoped
public class SiteBean extends UtilBean implements Serializable, CrudBeans<Object> {
	private static final long serialVersionUID = 1L;

	@ManagedProperty("#{siteServico}")
	private SiteServico model;
	private Site site;
	private List<Site> sites;
	private boolean modoEdicao;

	/* ------------------------------------------------- */

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
	private String web_youtube;
	private String web_blog;
	private String web_localizacao;
	private String web_messenger;
	private String web_skype;
	private String web_logomarca;

	public SiteServico getModel() {
		return model;
	}

	public void setModel(SiteServico model) {
		this.model = model;
	}

	public Site getSite() {
		if (site == null) {
			site = new Site();
		}
		return site;
	}

	public void setSite(Site site) {
		this.site = site;
	}

	public List<Site> getSites() {
		if (sites == null) {
			sites = model.listarSite();
		}
		return sites;
	}

	public void setSites(List<Site> sites) {
		this.sites = sites;
	}

	public boolean isModoEdicao() {
		return modoEdicao;
	}

	public void setModoEdicao(boolean modoEdicao) {
		this.modoEdicao = modoEdicao;
	}
	
	/* ------------------------------------------------- */

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

	public String getWeb_logomarca() {
		return web_logomarca;
	}

	public void setWeb_logomarca(String web_logomarca) {
		this.web_logomarca = web_logomarca;
	}
	
	/* ------------------------------------------------- */

	@Override
	public void incluir() {
        this.site = new Site();
        this.modoEdicao = true;
	}

	@Override
	public void salvar() {
        if (site.getWeb_cod() == null || site.getWeb_cod().intValue() == 0) {
            site = model.incluirSite(site);
            site = new Site();
            addInfoMensagem("Site criado com sucesso.");
            retornar();
        } else {
            model.alterarSite(site);
            addInfoMensagem("Site alterado com sucesso.");
            retornar();
        }
	}

	@Override
	public void atualizar() {
		this.modoEdicao = true;
	}

	@Override
	public void excluir() {
		model.excluirSite(site);
		retornar();
	}

	@Override
	public void filtrar(AjaxBehaviorEvent event) {
        if (web_titulo != null && !web_titulo.isEmpty()) {
            sites = model.listarSite(web_titulo);
        } else {
            sites = model.listarSite();
        }
	}

	@Override
	public String retornar() {
        this.modoEdicao = false;
        sites = model.listarSite();
        return "site";
	}
	
	/* ------------------------------------------------- */
	
	public Site getCarregarDados() {
		List<Site> lista = model.listarSite();
		if (lista.isEmpty())
			return null;
		else
			return (Site) lista.set(0, null);
	}

	public String getSaldacao() {
		String texto = null;
		Calendar DataToda = Calendar.getInstance();
		int HoraAtual = DataToda.get(Calendar.HOUR_OF_DAY);
		switch (HoraAtual) {
		case 0:
		case 1:
		case 2:
		case 3:
		case 4:
		case 5:
		case 6:
		case 7:
		case 8:
		case 9:
		case 10:
		case 11:
			texto = "Bom dia!";
			break;
		case 12:
		case 13:
		case 14:
		case 15:
		case 16:
		case 17:
			texto = "Boa tarde!";
			break;
		default:
			texto = "Boa noite!";
			break;
		}
		return texto;
	}

}
