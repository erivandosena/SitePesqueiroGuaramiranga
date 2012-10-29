package br.net.rwd.website.servico;

import java.io.Serializable;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.net.rwd.website.dao.DAOGenerico;
import br.net.rwd.website.dao.EventoDAO;
import br.net.rwd.website.entidade.Evento;

@Service("eventoServico")
public class EventoServico extends DAOGenerico<Serializable> {

	@Autowired
	private EventoDAO dao;

	protected void setDao(EventoDAO dao) {
		this.dao = dao;
	}
	
	public Evento incluirEvento(Evento evento) {
		return dao.adicionar(evento);
	}
	
	public void alterarEvento(Evento evento) {
		dao.atualizar(evento);
	}
	
	public void excluirEvento(Evento evento) {
		dao.remover(evento);
	}
	
	public Evento selecionarEvento(int codigo) {
		return dao.obterEntidade(Evento.class, codigo);
	}
	
	public List<Evento> listarEventos(String nome) {
		return dao.obterLista(Evento.class, "SELECT e FROM Evento e WHERE lower(e.pub_titulo) like ?1", "%"+nome.toLowerCase()+"%");
	}
	
	public List<Evento> listarEventos() {
		return dao.obterLista(Evento.class, "SELECT e FROM Evento e ORDER BY e.pub_data DESC");
	}
	
	public List<Evento> listarEventosNovos(int limit, int offset) {
		//(retorna ultimos 6 registros) em JPA equivale ao SQL: select * from publicacoes order by pub_cod desc limit 6 offset 0
		return dao.obterListaLimitOffset(Evento.class, "SELECT e FROM Evento e ORDER BY e.pub_cod DESC", limit, offset);
	}

}
