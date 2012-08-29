package br.net.rwd.website.servico;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.net.rwd.website.dao.UsuarioDAO;
import br.net.rwd.website.entidade.Usuario;

@Service("usuarioServico")
public class UsuarioServico {// extends HibernateDaoSupport implements UserDetailsService {

	@Autowired
	private UsuarioDAO dao;

	/*
	@Autowired
	public UsuarioServico(SessionFactory sessionFactory) {
		setSessionFactory(sessionFactory);
	}

	@Override
	public UserDetails loadUserByUsername(String login) {
		DetachedCriteria criteria = DetachedCriteria.forClass(Usuario.class, "usuario");
		criteria.add(Restrictions.eq("usuario.usu_email", login));
		List<?> resultado = getHibernateTemplate().findByCriteria(criteria);
		if (resultado != null && resultado.size() == 0) {
			throw new UsernameNotFoundException("Usuário não encontrado!");
		}
		return (Usuario) resultado.get(0);
	}
	*/

	public List<Usuario> getAllUsuarios() {
		return dao.findAll();
	}

	public List<Usuario> getUsuariosByName(String nomeUsuario) {
		return dao.findByName(nomeUsuario);
	}

	public Usuario createUsuario(Usuario usuario) {
		return dao.create(usuario);
	}

	public void updateUsuario(Usuario usuario) {
		dao.update(usuario);
	}

	public void deleteUsuario(Usuario usuario) {
		dao.delete(usuario);

	}

	public void setDao(UsuarioDAO dao) {
		this.dao = dao;
	}
}
