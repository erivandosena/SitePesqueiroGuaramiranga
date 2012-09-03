package br.net.rwd.website.servico;

import java.io.Serializable;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.net.rwd.website.dao.DAOGenerico;
import br.net.rwd.website.dao.SiteDAO;
import br.net.rwd.website.entidade.Site;

@Service("siteServico")
public class SiteServico extends DAOGenerico<Serializable> {
	
	@Autowired
	private SiteDAO dao;
	
	protected void setDao(SiteDAO dao) {
		this.dao = dao;
	}
	
	public Site incluirSite(Site site) {
		return dao.adicionar(site);
	}
	
	public void editarSite(Site site) {
		dao.atualizar(site);
	}
	
	public void excluirSite(Site site) {
		dao.remover(site);
	}
	
	public Site selecionarSite() {
		return dao.obterEntidade(Site.class, "SELECT s from Site s");
	}
	
	public Site selecionarSite(int codigo) {
		return dao.obterEntidade(Site.class, "SELECT s from Site s where s.web_cod = ?1", codigo);
	}
	
	public Site selecionarSite(String nome) {
		return dao.obterEntidade(Site.class, "SELECT s from Site s where s.web_titulo = ?1", nome);
	}
	
	public List<Site> listarSite() {
		return dao.obterLista(Site.class, "SELECT s from Site s ORDER BY s.web_cod ASC");
	}
	
	public List<Site> listarSite(int codigo) {
		return dao.obterLista(Site.class, "SELECT s from Site s where s.web_cod = ?1", codigo);
	}
	
	public List<Site> listarSite(String nome) {
		return dao.obterLista(Site.class, "SELECT s from Site s where s.web_titulo = ?1", nome);
	}

}
