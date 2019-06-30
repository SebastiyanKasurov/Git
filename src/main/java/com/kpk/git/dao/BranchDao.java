package com.kpk.git.dao;

import com.kpk.git.model.Commit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class BranchDao {
	
	@Autowired
	private RepositoryDao repositoryDao;
	
	@Autowired
	private NamedParameterJdbcTemplate jdbcTemplate;
	
	public int createBranch(String name) {
		final String sql = "INSERT INTO branches (name) VALUES (:name)";
		
		KeyHolder keyHolder = new GeneratedKeyHolder();
		
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("name", name);
		
		jdbcTemplate.update(sql, params, keyHolder);
		return keyHolder.getKey().intValue();
	}
	
	public void updateBranchRepositoryId(int branchId, int repositoryId) {
		
		final String sql = "UPDATE branches SET repository_id= :repoId WHERE id = :id";
		
		final Map<String, Object> params = new HashMap<>();
		params.put("repoId", repositoryId);
		params.put("id", branchId);
		
		jdbcTemplate.update(sql, params);
	}
	
	public List<Commit> getCommits(String repositoryName) {
		final Long branchId = repositoryDao.getCurrentBranchId(repositoryName);
		final String sql = "SELECT * FROM commits WHERE branch_id=:branchId";
		
		final Map<String, Object> params = new HashMap<>();
		params.put("branchId", branchId);
		
		return jdbcTemplate.query(sql, params, new CommitRowMapper());
	}
	
	public Commit getCommitsHead(String repositoryName) {
		final Long branchId = repositoryDao.getCurrentBranchId(repositoryName);
		
		final String sql = "SELECT * FROM commits WHERE branch_id=:branchId " +
				" AND is_head = 1";
		
		final Map<String, Object> params = new HashMap<>();
		params.put("branchId", branchId);
		
		return jdbcTemplate.queryForObject(sql, params, new CommitRowMapper());
	}
	
	public int commit(String message, String repositoryName) {
		final Long branchId = repositoryDao.getCurrentBranchId(repositoryName);
		final Long repositoryId = repositoryDao.getRepositoryId(repositoryName);
		
		Commit commit = new Commit();
		commit.setMessage(message);
		commit.setTimeCreated(LocalDateTime.now());
		
		removeOldHead(branchId);
		addCommit(commit, branchId);
		
		final Map<String, Object> params = new HashMap<>();
		params.put("branchId", branchId);
		params.put("repoId", repositoryId);
		
		final String updateAdd = "UPDATE files SET is_staged = 1 WHERE branch_id=:branchId " +
				"AND repository_id = :repoId AND is_removal = 0";
		
		final String deleteRemove = "DELETE FROM files WHERE branch_id=:branchId " +
				"AND repository_id = :repoId AND is_removal = 1";
		
		
		int rowsAffected = jdbcTemplate.update(updateAdd, params);
		return rowsAffected + jdbcTemplate.update(deleteRemove, params);
	}
	
	private void removeOldHead(Long branchId) {
		final String sql = "UPDATE commits SET is_head = 0 WHERE branch_id =:branchId";
		
		final Map<String, Object> params = new HashMap<>();
		params.put("branchId", branchId);
		
		jdbcTemplate.update(sql, params);
	}
	
	public void setNewHead(String repositoryName, String newHeadHash) {
		final Long branchId = repositoryDao.getCurrentBranchId(repositoryName);
		
		removeOldHead(branchId);
		
		final String sql = "UPDATE commits SET is_head = 1  WHERE branch_id =:branchId AND id=:hash";
		
		final Map<String, Object> params = new HashMap<>();
		params.put("branchId", branchId);
		params.put("hash", newHeadHash);
		
		jdbcTemplate.update(sql, params);
	}
	
	private Long addCommit(Commit commit, Long branchId) {
		final String sql = "INSERT INTO commits (id, branch_id, message, creation_date,is_head) " +
				"VALUES (:id, :branchId, :message, :creationDate, 1)";
		
		KeyHolder keyHolder = new GeneratedKeyHolder();
		
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("id", commit.getHash());
		params.addValue("branchId", branchId);
		params.addValue("message", commit.getMessage());
		params.addValue("creationDate", commit.getTimeCreated());
		
		jdbcTemplate.update(sql, params, keyHolder);
		
		return keyHolder.getKey().longValue();
	}
	
	public boolean checkFileExists(String file, String repositoryName) {
		final Long branchId = repositoryDao.getCurrentBranchId(repositoryName);
		final Long repositoryId = repositoryDao.getRepositoryId(repositoryName);
		
		final String sql = "SELECT name FROM files WHERE branch_id=:branchId " +
				"AND repository_id = :repoId AND is_removal = 0 AND name = :name";
		
		final Map<String, Object> params = new HashMap<>();
		params.put("branchId", branchId);
		params.put("repoId", repositoryId);
		params.put("name", file);
		
		try {
			jdbcTemplate.queryForObject(sql, params, String.class);
		} catch (EmptyResultDataAccessException e) {
			return false;
		}
		
		return true;
	}
	
	public void add(List<String> files, String repositoryName) {
		final Long branchId = repositoryDao.getCurrentBranchId(repositoryName);
		final Long repositoryId = repositoryDao.getRepositoryId(repositoryName);
		
		final String sql = "INSERT INTO files (name, branch_id, repository_id) " +
				"VALUES (:name, :branchId, :repoId)";
		
		final Map<String, Object> params = new HashMap<>();
		params.put("branchId", branchId);
		params.put("repoId", repositoryId);
		
		for (String file : files) {
			params.put("name", file);
			jdbcTemplate.update(sql, params);
		}
		
	}
	
	public void remove(List<String> files, String repositoryName) {
		final Long branchId = repositoryDao.getCurrentBranchId(repositoryName);
		final Long repositoryId = repositoryDao.getRepositoryId(repositoryName);
		
		final String sql = "UPDATE files SET is_removal = 1, is_staged = 0 WHERE branch_id=:branchId " +
				"AND repository_id = :repoId AND name = :name";
		
		final Map<String, Object> params = new HashMap<>();
		params.put("branchId", branchId);
		params.put("repoId", repositoryId);
		
		for (String file : files) {
			params.put("name", file);
			jdbcTemplate.update(sql, params);
		}
		
	}
	
	public List<String> getStagedFiles(String repositoryName) {
		return getFiles(repositoryName, false);
	}
	
	public List<String> getCommitedFiles(String repositoryName) {
		return getFiles(repositoryName, true);
	}
	
	private List<String> getFiles(String repositoryName, boolean isStaged) {
		final Long branchId = repositoryDao.getCurrentBranchId(repositoryName);
		final Long repositoryId = repositoryDao.getRepositoryId(repositoryName);
		
		final String sql = "SELECT name FROM files WHERE branch_id=:branchId " +
				"AND repository_id = :repoId AND is_staged = :isStaged";
		
		final Map<String, Object> params = new HashMap<>();
		params.put("branchId", branchId);
		params.put("repoId", repositoryId);
		params.put("isStaged", isStaged ? 1 : 0);
		
		return jdbcTemplate.queryForList(sql, params, String.class);
	}
}
