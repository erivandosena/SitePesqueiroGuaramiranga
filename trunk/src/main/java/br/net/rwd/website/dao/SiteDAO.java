package br.net.rwd.website.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import br.net.rwd.website.entidade.Site;

@Repository("siteDao")
public class SiteDAO {

	@PersistenceContext
	protected EntityManager entityManager;

	@Transactional(readOnly = true)
	public List<Site> findAll() {

		String jpql = " SELECT s from Site s order by s.web_titulo";
		Query query = entityManager.createQuery(jpql);

		@SuppressWarnings("unchecked")
		List<Site> sites = (List<Site>) query.getResultList();
		return sites;
	}

	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true)
	public List<Site> findByName(String tituloSite) {

		String jpql = " SELECT s from Site s where web_titulo like :titulo order by s.web_titulo";
		Query query = entityManager.createQuery(jpql);
		query.setParameter("titulo", "%" + tituloSite + "%");

		List<Site> sites = (List<Site>) query.getResultList();
		return sites;

	}

	@Transactional
	public Site create(Site site) {
		entityManager.persist(site);
		return site;

	}

	@Transactional
	public void update(Site site) {
		entityManager.merge(site);
	}

	@Transactional
	public void delete(Site site) {

		site = entityManager.find(Site.class, site.getWeb_cod());
		entityManager.remove(site);

	}

	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}
}
