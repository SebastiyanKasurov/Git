package com.kpk.git;

import com.kpk.git.service.RepositoryService;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
@ActiveProfiles("test")
public abstract class AbstractTest {
	
	protected static final String REPOSITORY_NAME = "test";
	protected Assert A;
	
	@Autowired
	protected NamedParameterJdbcTemplate jdbcTemplate;
	
	@Autowired
	protected RepositoryService repositoryDao;
	
	@Before
	public void setUp() {
		cleanUp();
		repositoryDao.createRepository(REPOSITORY_NAME);
	}
	
	@After
	public void cleanUp() {
		jdbcTemplate.update("DELETE FROM files", (Map<String, ?>) null);
		jdbcTemplate.update("DELETE FROM commits", (Map<String, ?>) null);
		jdbcTemplate.update("DELETE FROM repositories", (Map<String, ?>) null);
		jdbcTemplate.update("DELETE FROM branches", (Map<String, ?>) null);
	}
}
