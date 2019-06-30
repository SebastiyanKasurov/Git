package com.kpk.git.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class RepositoryDao {
	
	@Autowired
	private NamedParameterJdbcTemplate jdbcTemplate;
	
	@Autowired
	private BranchDao branchDao;
	
	public void createRepository(String repositoryName) {
		final String sql = "INSERT INTO repositories (name, current_branch_id) VALUES (:name, :branchId)";
		
		int createdBranchId = branchDao.createBranch("master");
		
		KeyHolder keyHolder = new GeneratedKeyHolder();
		
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("name", repositoryName);
		params.addValue("branchId", createdBranchId);
		
		jdbcTemplate.update(sql, params, keyHolder);
		
		int repositoryId = keyHolder.getKey().intValue();
		branchDao.updateBranchRepositoryId(createdBranchId, repositoryId);
	}
	
	public Long getCurrentBranchId(String repositoryName) {
		final String sql = "SELECT current_branch_id FROM repositories WHERE name = :name";
		
		final Map<String, Object> params = new HashMap<>();
		params.put("name", repositoryName);
		
		return jdbcTemplate.queryForObject(sql, params, Long.class);
	}
	
	public Long getRepositoryId(String repositoryName) {
		final String sql = "SELECT id FROM repositories WHERE name = :name";
		
		final Map<String, Object> params = new HashMap<>();
		params.put("name", repositoryName);
		
		return jdbcTemplate.queryForObject(sql, params, Long.class);
	}
}
