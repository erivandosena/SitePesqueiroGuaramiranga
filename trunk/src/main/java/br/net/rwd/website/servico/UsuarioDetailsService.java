package br.net.rwd.website.servico;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import br.net.rwd.website.dao.UsuarioDAO;
import br.net.rwd.website.entidade.Usuario;

@Component("usuarioDetailsService")
public class UsuarioDetailsService implements UserDetailsService {

	@Autowired
	private UsuarioDAO usuarioDao;

	@Override
	public UserDetails loadUserByUsername(String arg0) throws UsernameNotFoundException, DataAccessException {
		return usuarioDao.obterEntidade(Usuario.class,"SELECT u from Usuario u where u.usu_email = ?1", arg0);
	}

}
