package br.net.rwd.website.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import br.net.rwd.website.entidade.Usuario;

@Repository("usuarioDao")
public class UsuarioDAO {
	
	@PersistenceContext
	protected EntityManager entityManager;

	@Transactional(readOnly = true)
	public List<Usuario> findAll() {

		String jpql = " SELECT u from Usuario u order by u.usu_nome";
		Query query = entityManager.createQuery(jpql);

		@SuppressWarnings("unchecked")
		List<Usuario> usuarios = (List<Usuario>) query.getResultList();
		return usuarios;
	}

	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true)
	public List<Usuario> findByName(String nomeUsuario) {

		String jpql = " SELECT u from Usuario u where usu_nome like :nome order by u.usu_nome";
		Query query = entityManager.createQuery(jpql);
		query.setParameter("nome", "%" + nomeUsuario + "%");

		List<Usuario> usuarios = (List<Usuario>) query.getResultList();
		return usuarios;
	}
	
	@Transactional(readOnly = true)
	public Usuario usuarioPorNome(String nomeUsuario) {
		String jpql = " SELECT u from Usuario u where usu_nome = :nome";
		Query query = entityManager.createQuery(jpql);
		query.setParameter("nome", nomeUsuario);
		return (Usuario) query.getSingleResult();
	}

	@Transactional
	public Usuario create(Usuario usuario) {
		entityManager.persist(usuario);
		return usuario;

	}

	@Transactional
	public void update(Usuario usuario) {
		entityManager.merge(usuario);
	}

	@Transactional
	public void delete(Usuario usuario) {

		usuario = entityManager.find(Usuario.class, usuario.getUsu_cod());
		entityManager.remove(usuario);

	}

	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}
}
