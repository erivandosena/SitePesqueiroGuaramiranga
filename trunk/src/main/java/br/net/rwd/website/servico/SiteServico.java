package br.net.rwd.website.servico;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.net.rwd.website.dao.SiteDAO;
import br.net.rwd.website.entidade.Site;

@Service("siteServico")
public class SiteServico {
	
	@Autowired
	private SiteDAO dao;

	public List<Site> getAllSites() {
		return dao.findAll();
	}

	public List<Site> getSitesByName(String tituloSite) {
		return dao.findByName(tituloSite);
	}

	public Site createSite(Site site) {
		return dao.create(site);
	}

	public void updateSite(Site site) {
		dao.update(site);
	}

	public void deleteSite(Site site) {
		dao.delete(site);

	}

	public void setDao(SiteDAO dao) {
		this.dao = dao;
	}
}
