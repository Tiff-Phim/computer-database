package com.excilys.cdb.dao;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.excilys.cdb.mapper.CompanyRowMapper;
import com.excilys.cdb.model.Company;
import com.excilys.cdb.model.Page;

@Repository
public class CompanyDAO {

	private static final String SQL_GET_ALL_COMPANIES = "SELECT id, name FROM company";
	private static final String SQL_DELETE_COMPUTER_REFERENCED_BY_COMPANY = "DELETE FROM computer WHERE company_id = :id";
	private static final String SQL_DELETE_COMPANY_BY_ID = "DELETE FROM company WHERE id = :id";
	private static final String SQL_GET_COMPANY_PAGE = "SELECT id, name FROM company ORDER BY id LIMIT :size OFFSET :offset";

	private final NamedParameterJdbcTemplate jdbcTemplate;
	private final CompanyRowMapper mapper;
	
	public CompanyDAO(NamedParameterJdbcTemplate jdbcTemplate, CompanyRowMapper mapper) {
		this.jdbcTemplate = jdbcTemplate;
		this.mapper = mapper;
	}

	/**
	 * Lists all companies present in database.
	 * 
	 * @return companies all the companies stored in database
	 * @throws SQLException
	 */
	public List<Optional<Company>> findAllCompanies() throws DataAccessException {
		List<Company> companyList = jdbcTemplate.query(SQL_GET_ALL_COMPANIES, mapper);
		return companyList.stream().map(Optional::ofNullable).collect(Collectors.toList());
	}
	
	/**
	 * List companies present in database, paginated.
	 * 
	 * @param page
	 * @return page with list of companies
	 */
	public Page<Company> getCompanyPaginated(Page<Company> page) throws DataAccessException {
		SqlParameterSource parameters = new MapSqlParameterSource()
				.addValue("size", page.getSize())
				.addValue("offset", (page.getNumber() - 1) * page.getSize());
		List<Company> companyList = jdbcTemplate.query(SQL_GET_COMPANY_PAGE, parameters, mapper);
		page.setContent(companyList.stream().map(Optional::ofNullable).collect(Collectors.toList()));
		return page;
	}
	
	/**
	 * Delete company by id, also delete computer associated with the company to delete.
	 * 
	 * @param id
	 * @throws SQLException
	 */
	@Transactional
	public void deleteCompanyById(long id) throws DataAccessException {
		SqlParameterSource parameters = new MapSqlParameterSource().addValue("id", id);
		jdbcTemplate.update(SQL_DELETE_COMPUTER_REFERENCED_BY_COMPANY, parameters);
		jdbcTemplate.update(SQL_DELETE_COMPANY_BY_ID, parameters);
	}

}