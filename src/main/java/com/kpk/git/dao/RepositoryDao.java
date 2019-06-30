package com.kpk.git.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
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
	
	public List<String> getRepositories() {
		
		final String sql = "SELECT name FROM repositories";
		
		return jdbcTemplate.queryForList(sql, Collections.emptyMap(), String.class);
	}
	
	public void createBranch(String repositoryName, String branchName) {
		final int repositoryId = getRepositoryId(repositoryName).intValue();
		
		final int createdBranchId = branchDao.createBranch(branchName);
		
		branchDao.updateBranchRepositoryId(createdBranchId, repositoryId);
	}
	
	public void updateCurrentBranch(String repositoryName, String branchName) {
		final int repositoryId = getRepositoryId(repositoryName).intValue();
		final int branchId = getBranchId(branchName).intValue();
		
		final String sql = "UPDATE repositories SET current_branch_id =:branchId WHERE id = :repoId";
		
		final Map<String, Object> params = new HashMap<>();
		params.put("branchId", branchId);
		params.put("repoId", repositoryId);
		
		jdbcTemplate.update(sql, params);
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
	
	public List<String> getBranches(String repositoryName) {
		final Long repositoryId = getRepositoryId(repositoryName);
		
		final String sql = "SELECT name FROM branches WHERE repository_id = :repoId";
		
		final Map<String, Object> params = new HashMap<>();
		params.put("repoId", repositoryId);
		
		return jdbcTemplate.queryForList(sql, params, String.class);
	}
	
	private Long getBranchId(String branchName) {
		final String sql = "SELECT id FROM branches WHERE name = :name";
		
		final Map<String, Object> params = new HashMap<>();
		params.put("name", branchName);
		
		return jdbcTemplate.queryForObject(sql, params, Long.class);
	}
}
