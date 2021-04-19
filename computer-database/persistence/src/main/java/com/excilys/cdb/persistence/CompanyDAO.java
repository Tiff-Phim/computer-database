package com.excilys.cdb.persistence;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaDelete;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.excilys.cdb.binding.persistence.dto.CompanyEntity;
import com.excilys.cdb.binding.persistence.dto.ComputerEntity;
import com.excilys.cdb.binding.persistence.mapper.CompanyDAOMapper;
import com.excilys.cdb.core.Company;
import com.excilys.cdb.core.Page;

@Repository
public class CompanyDAO {

	private static Logger logger = LoggerFactory.getLogger(CompanyDAO.class);
	
	private final EntityManager entityManager;
	private final CriteriaBuilder criteriaBuilder;
	private final CompanyDAOMapper mapper;

	public CompanyDAO(SessionFactory session, CompanyDAOMapper mapper) {
		this.entityManager = session.createEntityManager();
		this.mapper = mapper;
		this.criteriaBuilder = entityManager.getCriteriaBuilder();
	}

	/**
	 * Lists all companies present in database.
	 * 
	 * @return companies all the companies stored in database
	 * @throws SQLException
	 */
	public List<Optional<Company>> findAllCompanies() {
		logger.debug("Getting all companies ...");
		CriteriaQuery<CompanyEntity> query = criteriaBuilder.createQuery(CompanyEntity.class);
		Root<CompanyEntity> company = query.from(CompanyEntity.class);
		query.select(company);

		return entityManager.createQuery(query).getResultList().stream().map(c -> mapper.mapToCompany(c))
				.map(Optional::ofNullable).collect(Collectors.toList());
	}

	/**
	 * List companies present in database, paginated.
	 * 
	 * @param page
	 * @return page with list of companies
	 */
	public Page<Company> getCompanyPaginated(Page<Company> page) {
		logger.debug("Getting page of companies ...");
		CriteriaQuery<CompanyEntity> query = criteriaBuilder.createQuery(CompanyEntity.class);
		Root<CompanyEntity> company = query.from(CompanyEntity.class);
		query.select(company);
		
		page.setContent(entityManager.createQuery(query).setFirstResult(page.getNumber())
				.setMaxResults(page.getSize()).getResultList().stream().map(c -> mapper.mapToCompany(c))
				.map(Optional::ofNullable)
				.collect(Collectors.toList()));
		return page;
	}

	/**
	 * Delete company by id, also delete computer associated with the company to
	 * delete.
	 * 
	 * @param id
	 * @throws SQLException
	 */
	@Transactional
	public void deleteCompanyById(long id) {
		logger.debug("Deleting company with id " + id);
		CriteriaDelete<ComputerEntity> computerQuery = criteriaBuilder.createCriteriaDelete(ComputerEntity.class);
		Root<ComputerEntity> computer = computerQuery.from(ComputerEntity.class);
		computerQuery.where(criteriaBuilder.equal(computer.get("company"), id));

		CriteriaDelete<CompanyEntity> companyQuery = criteriaBuilder.createCriteriaDelete(CompanyEntity.class);
		Root<CompanyEntity> company = companyQuery.from(CompanyEntity.class);
		companyQuery.where(criteriaBuilder.equal(company.get("id"), id));

		entityManager.getTransaction().begin();
		entityManager.createQuery(computerQuery).executeUpdate();
		entityManager.createQuery(companyQuery).executeUpdate();
		entityManager.getTransaction().commit();
	}

}