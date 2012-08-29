package br.net.rwd.website.servico;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import br.net.rwd.website.dao.UsuarioDAO;

@Service("usuarioDetailsService")
public class UsuarioDetailsService implements org.springframework.security.core.userdetails.UserDetailsService {

	@Autowired
	private UsuarioDAO usuarioDao;

	public UserDetails loadUserByUsername(String arg0) throws UsernameNotFoundException, DataAccessException {
		// TODO Auto-generated method stub
		// assumindo que tem uma classe de usuario que implementa UserDetails
		// carregar usuario de retorno usando as tecnicas regulares JPA
		return usuarioDao.usuarioPorNome(arg0);
	}
}
