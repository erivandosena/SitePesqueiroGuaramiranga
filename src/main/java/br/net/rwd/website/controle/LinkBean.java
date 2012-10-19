package br.net.rwd.website.controle;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.event.AjaxBehaviorEvent;

import br.net.rwd.website.entidade.Link;
import br.net.rwd.website.servico.LinkServico;
import br.net.rwd.website.util.EnumPosicaoMenu;

@ManagedBean(name = "linkBean")
@ViewScoped
public class LinkBean extends UtilBean implements CrudBeans<Object> {

	@ManagedProperty("#{linkServico}")
	private LinkServico model;
	private Link link;
	private List<Link> links;
	private boolean modoEdicao;

	private Integer lin_cod;
	private String lin_nome;
	private String lin_url;
	private String lin_posicao;

	/* ------------------------------------------------- */

	public LinkServico getModel() {
		return model;
	}

	public void setModel(LinkServico model) {
		this.model = model;
	}

	public Link getLink() {
		if (link == null) {
			link = new Link();
		}
		return link;
	}

	public void setLink(Link link) {
		this.link = link;
	}

	public List<Link> getLinks() {
		if (links == null) {
			links = model.listarLinks();
		}
		return links;
	}

	public void setLinks(List<Link> links) {
		this.links = links;
	}

	public boolean isModoEdicao() {
		return modoEdicao;
	}

	public void setModoEdicao(boolean modoEdicao) {
		this.modoEdicao = modoEdicao;
	}

	public Integer getLin_cod() {
		return lin_cod;
	}

	public void setLin_cod(Integer lin_cod) {
		this.lin_cod = lin_cod;
	}

	public String getLin_nome() {
		return lin_nome;
	}

	public void setLin_nome(String lin_nome) {
		this.lin_nome = lin_nome;
	}

	public String getLin_url() {
		return lin_url;
	}

	public void setLin_url(String lin_url) {
		this.lin_url = lin_url;
	}
	
	public String getLin_posicao() {
		return lin_posicao;
	}

	public void setLin_posicao(String lin_posicao) {
		this.lin_posicao = lin_posicao;
	}
	
	/* ------------------------------------------------- */

	@Override
	public void incluir() {
        this.link = new Link();
        this.modoEdicao = true;
	}

	@Override
	public void salvar() {
        if (link.getLin_cod() == null || link.getLin_cod().intValue() == 0) {
            link = model.incluirLink(link);
            link = new Link();
            addInfoMensagem("Link criado com sucesso.");
            retornar();
        } else {
            model.alterarLink(link);
            addInfoMensagem("Link alterado com sucesso.");
            retornar();
        }
	}

	@Override
	public void atualizar() {
		this.modoEdicao = true;
	}

	@Override
	public void excluir() {
		model.excluirLink(link);
		retornar();
	}

	@Override
	public void filtrar(AjaxBehaviorEvent event) {
        if (lin_nome != null && !lin_nome.isEmpty()) {
            links = model.listarLinks(lin_nome);
        } else {
        	links = model.listarLinks();
        }
	}

	@Override
	public String retornar() {
        this.modoEdicao = false;
        links = model.listarLinks();
        return "link";
	}

	/* ------------------------------------------------- */

	public Map<EnumPosicaoMenu, String> getPosicoes() {
		Map<EnumPosicaoMenu, String> mapParam = new HashMap<EnumPosicaoMenu, String>();
		for (EnumPosicaoMenu type : EnumPosicaoMenu.values()) {
			mapParam.put(type, type.name());
		}
		return mapParam;
	}  
	
	public List<Link> getLinksMenuLateral() {
		return model.listarLinksMenu("L");
	}
	
	public List<Link> getLinksMenuRodape() {
		return model.listarLinksMenu("R");
	}
	
	/* ------------------------------------------------- */
}
