package br.net.rwd.website.servico;

import java.io.Serializable;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.net.rwd.website.dao.DAOGenerico;
import br.net.rwd.website.dao.UsuarioDAO;
import br.net.rwd.website.entidade.Usuario;

@Service("usuarioServico")
public class UsuarioServico extends DAOGenerico<Serializable> {

	@Autowired
	private UsuarioDAO dao;
	
	protected void setDao(UsuarioDAO dao) {
		this.dao = dao;
	}
	
	public Usuario incluirUsuario(Usuario usuario) {
		return dao.adicionar(usuario);
	}
	
	public void alterarUsuario(Usuario usuario) {
		//referente edicao do usuario
		if(usuario.getPer_roles() == null || usuario.getPer_roles().size() == 0) {
			Usuario usuarioPerfil = this.selecionarUsuario(usuario.getUsu_cod());
			usuario.setPer_roles(usuarioPerfil.getPer_roles());
			//remove objeto do contexto de persistencia
			this.dao.separar(usuarioPerfil);
		}
		//
		dao.atualizar(usuario);
	}
	
	public void excluirUsuario(Usuario usuario) {
		dao.remover(usuario);
	}
	
	public Usuario selecionarUsuario(Integer codigo) {
		return dao.obterEntidade(Usuario.class, codigo);
	}

	public Usuario selecionarUsuarioLogin(String login) {
		return dao.obterEntidade(Usuario.class, "SELECT u FROM Usuario u WHERE u.usu_email = ?1", login);
	}
	
	public boolean selecionarUsuarioExistente(String login) {
		if(selecionarUsuarioLogin(login) != null)
			return true;
		else
			return false;
	}
	
	public List<Usuario> listarUsuarios() {
		return dao.obterLista(Usuario.class, "SELECT u FROM Usuario u ORDER BY u.usu_cod ASC");
	}
	
	public List<Usuario> listarLikeUsuario(String nome) {
		return dao.obterLista(Usuario.class, "SELECT u FROM Usuario u WHERE lower(u.usu_nome) like ?1", "%"+ nome.toLowerCase() + "%");
	}
	
}
