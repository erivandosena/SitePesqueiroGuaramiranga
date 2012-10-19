package br.net.rwd.website.servico;

import java.io.Serializable;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.net.rwd.website.dao.DAOGenerico;
import br.net.rwd.website.dao.LinkDAO;
import br.net.rwd.website.entidade.Link;

@Service("linkServico")
public class LinkServico extends DAOGenerico<Serializable> {

	@Autowired
	private LinkDAO dao;

	protected void setDao(LinkDAO dao) {
		this.dao = dao;
	}

	public Link incluirLink(Link link) {
		return dao.adicionar(link);
	}

	public void alterarLink(Link link) {
		dao.atualizar(link);
	}

	public void excluirLink(Link link) {
		dao.remover(link);
	}
	
	public List<Link> listarLinksMenu(String nome) {
		return dao.obterLista(Link.class, "SELECT l FROM Link l WHERE l.lin_posicao = ?1 ORDER BY l.lin_cod DESC", nome);
	}

	public List<Link> listarLinks(String nome) {
		return dao.obterLista(Link.class,"SELECT l FROM Link l WHERE lower(l.lin_nome) like ?1", "%"+ nome.toLowerCase() + "%");
	}

	public List<Link> listarLinks() {
		return dao.obterLista(Link.class,"SELECT l FROM Link l ORDER BY l.lin_cod ASC");
	}
}
