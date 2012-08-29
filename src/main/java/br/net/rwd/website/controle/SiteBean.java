package br.net.rwd.website.controle;

import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.event.AjaxBehaviorEvent;

import br.net.rwd.website.entidade.Site;
import br.net.rwd.website.servico.SiteServico;

@ManagedBean(name = "siteBean")
@ViewScoped
public class SiteBean extends UtilBean {
	
	private static final long serialVersionUID = 1L;
	
	@ManagedProperty("#{siteServico}")
	private SiteServico model;
	private Site site;
	private List<Site> sites;
	private String tituloSite;
	private boolean editMode;

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

	public void salvar() {
		System.out.println("web_cod site " + site.getWeb_cod());
		if (site.getWeb_cod() == null || site.getWeb_cod().intValue() == 0) {
			site = model.createSite(site);
			site = new Site();
			addInfoMessage("Site criado com sucesso.");
		} else {
			model.updateSite(site);
			addInfoMessage("Site alterado com sucesso.");
		}
	}

	public void delete() {
		model.deleteSite(site);
		if (tituloSite != null && !tituloSite.isEmpty())
			sites = model.getSitesByName(tituloSite);
		else
			sites = model.getAllSites();
		// return "";
	}

	public void create() {
		this.site = new Site();
		this.editMode = true;
	}

	public void update() {
		this.editMode = true;
	}

	public String cancel() {
		this.editMode = false;
		return "site";
	}

	public void filtrarSite(AjaxBehaviorEvent event) {
		if (tituloSite != null && !tituloSite.isEmpty()) {
			sites = model.getSitesByName(tituloSite);
		} else {
			sites = model.getAllSites();
		}
	}

	public List<Site> getSites() {
		if (sites == null) {
			sites = model.getAllSites();
		}
		return sites;
	}

	public void setSites(List<Site> sites) {
		this.sites = sites;
	}

	public String getTituloSite() {
		return tituloSite;
	}

	public void setTituloSite(String tituloSite) {
		this.tituloSite = tituloSite;
	}

	public boolean isEditMode() {
		return editMode;
	}

	public void setEditMode(boolean editMode) {
		this.editMode = editMode;
	}
}
