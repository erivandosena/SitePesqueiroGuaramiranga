package br.net.rwd.website.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import br.net.rwd.website.entidade.Perfil;

@Repository("perfilDao")
public class PerfilDAO {

	@PersistenceContext
	protected EntityManager entityManager;

	@Transactional(readOnly = true)
	public List<Perfil> findAll() {

		String jpql = " SELECT p from Perfil p order by p.per_nome";
		Query query = entityManager.createQuery(jpql);

		@SuppressWarnings("unchecked")
		List<Perfil> perfils = (List<Perfil>) query.getResultList();
		return perfils;
	}

	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true)
	public List<Perfil> findByName(String nomePerfil) {

		String jpql = " SELECT p from Perfil p where per_nome like :nome order by p.per_nome";
		Query query = entityManager.createQuery(jpql);
		query.setParameter("nome", "%" + nomePerfil + "%");

		List<Perfil> perfils = (List<Perfil>) query.getResultList();
		return perfils;
	}

	@Transactional
	public Perfil create(Perfil perfil) {
		entityManager.persist(perfil);
		return perfil;

	}

	@Transactional
	public void update(Perfil perfil) {
		entityManager.merge(perfil);
	}

	@Transactional
	public void delete(Perfil perfil) {

		perfil = entityManager.find(Perfil.class, perfil.getPer_cod());
		entityManager.remove(perfil);

	}

	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}
}
