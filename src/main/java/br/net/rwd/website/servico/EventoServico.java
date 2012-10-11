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
	
	public List<Evento> listarEventos(String nome) {
		return dao.obterLista(Evento.class, "SELECT e FROM Evento e WHERE lower(e.pub_titulo) like ?1", "%"+nome.toLowerCase()+"%");
	}
	
	public List<Evento> listarEventos() {
		return dao.obterLista(Evento.class, "SELECT e FROM Evento e ORDER BY e.pub_cod ASC");
	}
	
	public List<Evento> listar4Eventos() {
		return dao.obterLista(Evento.class, "SELECT e FROM Evento e ORDER BY e.pub_cod DESC LIMIT 4");
	}
}
