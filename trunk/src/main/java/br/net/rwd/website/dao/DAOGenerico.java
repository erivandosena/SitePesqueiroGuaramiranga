package br.net.rwd.website.dao;

import java.io.Serializable;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.log4j.Logger;
import org.springframework.transaction.annotation.Transactional;

public class DAOGenerico<T extends Serializable> {

	@PersistenceContext
	protected EntityManager entityManager;

	private Logger log4j = Logger.getLogger(Class.class.getName());

	protected void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	@SuppressWarnings("hiding")
	@Transactional
	public <T> T adicionar(T objeto) {
		entityManager.persist(objeto);
		return objeto;
	}

	@Transactional
	public void atualizar(T objeto) {
		entityManager.merge(objeto);
	}

	@Transactional
	public void remover(T objeto) {
		objeto = entityManager.merge(objeto);
		entityManager.remove(objeto);
	}

	@Transactional(readOnly = true)
	public List<?> obterLista(String jpql, Object... parametros) {
		Query consulta = entityManager.createQuery(jpql);
		for (int i = 0; i < parametros.length; i++) {
			consulta.setParameter(i + 1, parametros[i]);
		}
		return consulta.getResultList();
	}

	@SuppressWarnings({ "unchecked", "hiding" })
	@Transactional(readOnly = true)
	public <T> List<T> obterLista(Class<T> classe, String jpql, Object... parametros) {
		Query consulta = entityManager.createQuery(jpql);
		for (int i = 0; i < parametros.length; i++) {
			consulta.setParameter(i + 1, parametros[i]);
		}
		return (List<T>) consulta.getResultList();
	}

	@SuppressWarnings({ "hiding", "unchecked" })
	@Transactional
	public <T> T obterEntidade(Class<T> classe, String jpql, Serializable... parametros) {
		Query consulta = entityManager.createQuery(jpql);
		T entidade = null;
		try {
			for (int i = 0; i < parametros.length; i++) {
				consulta.setParameter(i + 1, parametros[i]);
			}
			if (consulta.getSingleResult() != null) {
				entidade = (T) consulta.getSingleResult();
			}
		} catch (NoResultException nre) {
			log4j.error(nre);
		}
		return entidade;
	}
	
	@SuppressWarnings("hiding")
	@Transactional
	public <T> T obterEntidade(Class<T> classe, Integer chave) {
		T entidade = entityManager.find(classe, chave);
		return entidade;
	}

}
