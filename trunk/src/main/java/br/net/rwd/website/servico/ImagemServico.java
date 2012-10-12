package br.net.rwd.website.servico;

import java.io.Serializable;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.net.rwd.website.dao.DAOGenerico;
import br.net.rwd.website.dao.ImagemDAO;
import br.net.rwd.website.entidade.Imagem;

@Service("imagemServico")
public class ImagemServico extends DAOGenerico<Serializable> {
	
	@Autowired
	private ImagemDAO dao;
	
	protected void setDao(ImagemDAO dao) {
		this.dao = dao;
	}
	
	public Imagem incluirImagem(Imagem imagem) {
		return dao.adicionar(imagem);
	}
	
	public void alterarImagem(Imagem imagem) {
		dao.atualizar(imagem);
	}
	
	public void excluirImagem(Imagem imagem) {
		dao.remover(imagem);
	}
	
	public Imagem selecionarImagem(int codigo) {
		return dao.obterEntidade(Imagem.class, codigo);
	}

	public List<Imagem> listarImagemPorEvento(int codigo) {
		return dao.obterLista(Imagem.class, "SELECT i FROM Imagem i WHERE i.evento.pub_cod = ?1", codigo);
	}

}
