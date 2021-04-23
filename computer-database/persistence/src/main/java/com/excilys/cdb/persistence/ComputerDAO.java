package com.excilys.cdb.persistence;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaDelete;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.CriteriaUpdate;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Root;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.excilys.cdb.binding.persistence.dto.ComputerEntity;
import com.excilys.cdb.binding.persistence.mapper.ComputerDAOMapper;
import com.excilys.cdb.core.Computer;
import com.excilys.cdb.core.Page;
import com.excilys.cdb.core.Page.SortingOrder;
import com.excilys.cdb.exception.ComputerNotFoundException;

@Repository
public class ComputerDAO {

	private static Logger logger = LoggerFactory.getLogger(ComputerDAO.class);

	private final SessionFactory session;
	private final EntityManager entityManager;
	private final CriteriaBuilder criteriaBuilder;
	private final ComputerDAOMapper mapper;

	public ComputerDAO(SessionFactory session, ComputerDAOMapper mapper) {
		this.session = session;
		this.entityManager = this.session.createEntityManager();
		this.mapper = mapper;
		this.criteriaBuilder = this.entityManager.getCriteriaBuilder();
	}

	/**
	 * Get total of computers.
	 * 
	 * @return total
	 */
	public Long getTotalComputers() {
		logger.debug("Counting total of computers ...");
		CriteriaQuery<Long> query = criteriaBuilder.createQuery(Long.class);
		Root<ComputerEntity> computer = query.from(ComputerEntity.class);
		query.select(criteriaBuilder.count(computer));
		return entityManager.createQuery(query).getSingleResult();
	}
	
	/**
	 * List all computers present in database.
	 * 
	 * @return computers
	 */
	public List<Optional<Computer>> findAllComputers() {
		logger.debug("Getting all computers ...");
		CriteriaQuery<ComputerEntity> query = criteriaBuilder.createQuery(ComputerEntity.class);
		Root<ComputerEntity> computer = query.from(ComputerEntity.class);
		query.select(computer);
		
		return entityManager.createQuery(query).getResultList().stream().map(c -> mapper.mapToComputer(c))
				.map(Optional::ofNullable).collect(Collectors.toList());
	}

	/**
	 * Find a computer by its id.
	 * 
	 * @return computer details
	 * @throws SQLException
	 */
	public Optional<Computer> findComputerById(long computerId) throws ComputerNotFoundException {
		logger.debug("Searching for computer with id " + computerId);
		CriteriaQuery<ComputerEntity> query = criteriaBuilder.createQuery(ComputerEntity.class);
		Root<ComputerEntity> computer = query.from(ComputerEntity.class);
		query.select(computer).where(criteriaBuilder.equal(computer.get("id"), computerId));
		return Optional.ofNullable(mapper.mapToComputer(entityManager.createQuery(query).getSingleResult()));
	}

	/**
	 * List computers by pages with name filter. The filter is on both computer and
	 * company names.
	 * 
	 * @param page
	 * @param filter
	 * @return page of computers
	 */
	public Page<Computer> getComputerPaginatedByNameFilter(Page<Computer> page, String search) {
		logger.debug("Searching computers like " + search);
		CriteriaQuery<ComputerEntity> query = criteriaBuilder.createQuery(ComputerEntity.class);
		Root<ComputerEntity> computer = query.from(ComputerEntity.class);
		Expression<Boolean> searchCriteria = criteriaBuilder.like(computer.get("name"), "%" + search + "%");
		String filter = page.getFilter().getAttribute();
		if (page.getOrder() == SortingOrder.ASC) {
			page.setContent(entityManager
					.createQuery(query.where(searchCriteria).orderBy(criteriaBuilder.asc(computer.get(filter))))
					.setFirstResult(page.getNumber()).setMaxResults(page.getSize()).getResultList().stream()
					.map(c -> mapper.mapToComputer(c)).map(Optional::ofNullable).collect(Collectors.toList()));
		} else {
			page.setContent(entityManager
					.createQuery(query.where(searchCriteria).orderBy(criteriaBuilder.desc(computer.get(filter))))
					.setFirstResult(page.getNumber()).setMaxResults(page.getSize()).getResultList().stream()
					.map(c -> mapper.mapToComputer(c)).map(Optional::ofNullable).collect(Collectors.toList()));
		}
		return page;
	}

	/**
	 * List computers by pages.
	 * 
	 * @param page
	 * @return page of computers
	 */
	public Page<Computer> getComputerPaginatedByNameFilter(Page<Computer> page) {
		logger.debug("Getting page of computers ...");
		CriteriaQuery<ComputerEntity> query = criteriaBuilder.createQuery(ComputerEntity.class);
		Root<ComputerEntity> computer = query.from(ComputerEntity.class);
		String filter = page.getFilter().getAttribute();
		if (page.getOrder() == SortingOrder.ASC) {
			page.setContent(entityManager.createQuery(query.orderBy(criteriaBuilder.asc(computer.get(filter))))
					.setFirstResult(page.getNumber()).setMaxResults(page.getSize()).getResultList().stream()
					.map(c -> mapper.mapToComputer(c)).map(Optional::ofNullable).collect(Collectors.toList()));
		} else {
			page.setContent(entityManager.createQuery(query.orderBy(criteriaBuilder.desc(computer.get(filter))))
					.setFirstResult(page.getNumber()).setMaxResults(page.getSize()).getResultList().stream()
					.map(c -> mapper.mapToComputer(c)).map(Optional::ofNullable).collect(Collectors.toList()));
		}
		return page;
	}

	/**
	 * Creates a new computer and store it in the database.
	 * 
	 * @throws SQLException
	 */
	public void createComputer(Computer computer) {
		logger.debug("Inserting computer into database ...");
		if (computer != null) {
			logger.debug("Creating computer " + computer);
			Session s = session.openSession();
			Transaction tx = s.beginTransaction();
			s.save(mapper.mapToComputer(computer));
			tx.commit();
			s.close();
		}
	}

	/**
	 * Updates a computer identified by its id.
	 * 
	 * @param computer   the updated computer
	 * @param computerId the id of the computer to update
	 * @throws SQLException
	 */
	public void updateComputerById(Computer computer, long id) {
		logger.debug("Updating computer with id " + id);
		ComputerEntity comp = mapper.mapToComputer(computer);
		entityManager.getTransaction().begin();
		CriteriaUpdate<ComputerEntity> query = criteriaBuilder.createCriteriaUpdate(ComputerEntity.class);
		Root<ComputerEntity> root = query.from(ComputerEntity.class);
		query.set("name", comp.getName()).set("introduced", comp.getIntroduced())
				.set("discontinued", comp.getDiscontinued())
				.set("company", comp.getCompany() != null ? comp.getCompany() : null)
				.where(criteriaBuilder.equal(root.get("id"), comp.getId()));
		entityManager.createQuery(query).executeUpdate();
		entityManager.getTransaction().commit();
	}

	/**
	 * Deletes a computer by specifying its id.
	 * 
	 * @param computerId the id of the computer to delete
	 * @throws SQLException
	 */
	public void deleteComputerById(long computerId) throws ComputerNotFoundException {
		logger.debug("Deleting computer with id " + computerId);
		entityManager.getTransaction().begin();
		CriteriaDelete<ComputerEntity> computerQuery = criteriaBuilder.createCriteriaDelete(ComputerEntity.class);
		Root<ComputerEntity> computer = computerQuery.from(ComputerEntity.class);
		computerQuery.where(criteriaBuilder.equal(computer.get("id"), computerId));
		entityManager.createQuery(computerQuery).executeUpdate();
		entityManager.getTransaction().commit();
	}

}