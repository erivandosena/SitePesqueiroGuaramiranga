package br.net.rwd.website.servico;

import java.io.Serializable;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.net.rwd.website.dao.DAOGenerico;
import br.net.rwd.website.dao.FotoDAO;
import br.net.rwd.website.entidade.Foto;

@Service("fotoServico")
public class FotoServico extends DAOGenerico<Serializable> {

	@Autowired
	private FotoDAO dao;

	protected void setDao(FotoDAO dao) {
		this.dao = dao;
	}

	public Foto incluirFoto(Foto foto) {
		return dao.adicionar(foto);
	}

	public void alterarFoto(Foto foto) {
		dao.atualizar(foto);
	}

	public void excluirFoto(Foto foto) {
		dao.remover(foto);
	}
	
	public Foto selecionarFoto(int codigo) {
		return dao.obterEntidade(Foto.class, codigo);
	}

	public List<Foto> listarFotos() {
		return dao.obterLista(Foto.class,"SELECT f FROM Foto f ORDER BY f.fot_cod ASC");
	}

	public List<Foto> listarFotosPorGaleria(int codigo) {
		return dao.obterLista(Foto.class,"SELECT f FROM Foto f WHERE f.galeria.gal_cod = ?1", codigo);
	}
}
