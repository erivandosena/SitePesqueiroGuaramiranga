package br.net.rwd.website.servico;

import java.io.Serializable;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.net.rwd.website.dao.DAOGenerico;
import br.net.rwd.website.dao.GaleriaDAO;
import br.net.rwd.website.entidade.Galeria;

@Service("galeriaServico")
public class GaleriaServico extends DAOGenerico<Serializable>{
	
	@Autowired
	private GaleriaDAO dao;
	
	protected void setDao(GaleriaDAO dao) {
		this.dao = dao;
	}
	
	public Galeria incluirGaleria(Galeria galeria) {
		return dao.adicionar(galeria);
	}
	
	public void alterarGaleria(Galeria galeria) {
		dao.atualizar(galeria);
	}
	
	public void excluirGaleria(Galeria galeria) {
		dao.remover(galeria);
	}
	
	public Galeria selecionarGaleria(int codigo) {
		return dao.obterEntidade(Galeria.class, codigo);
	}

	public List<Galeria> listarGalerias(String nome) {
		return dao.obterLista(Galeria.class, "SELECT g FROM Galeria g WHERE lower(g.gal_titulo) like ?1", "%"+nome.toLowerCase()+"%");
	}
	
	public List<Galeria> listarGalerias() {
		return dao.obterLista(Galeria.class, "SELECT g FROM Galeria g ORDER BY g.gal_cod ASC");
	}
	
	public List<Galeria> listar6Galerias() {
		return dao.obterLista(Galeria.class, "SELECT g FROM Galeria g ORDER BY g.gal_cod DESC LIMIT 6");
	}

}
