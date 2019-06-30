package com.kpk.git.service;

import com.kpk.git.AbstractTest;
import com.kpk.git.model.Result;
import com.kpk.git.util.exceptions.ExistingBranchException;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class RepositoryServiceTest extends AbstractTest {
	private static final String TEST_BRANCH = "test-branch";
	
	@Test
	public void testCreateRepositoryWithExistingName() {
		Result result = repositoryService.createRepository(REPOSITORY_NAME);
		
		A.assertFalse(result.isSuccess());
		A.assertEquals("repository with name : " + REPOSITORY_NAME + " already exist", result.getMessage());
	}
	
	@Test
	public void testCreateBranch() {
		List<String> branches = repositoryService.getBranches(REPOSITORY_NAME);
		A.assertEquals("default branches size must be 1, only master", 1, branches.size());
		
		Result result = repositoryService.createBranch(TEST_BRANCH, REPOSITORY_NAME);
		A.assertTrue(result.isSuccess());
		
		List<String> actual = repositoryService.getBranches(REPOSITORY_NAME);
		A.assertEquals("size must be equal to 2 after creating new one", 2, actual.size());
	}
	
	@Test(expected = ExistingBranchException.class)
	public void testCreateBranchWithExistingName() {
		Result result = repositoryService.createBranch(TEST_BRANCH, REPOSITORY_NAME);
		A.assertTrue(result.isSuccess());
		
		repositoryService.createBranch(TEST_BRANCH, REPOSITORY_NAME);
	}
	
	@Test
	public void testCheckoutBranch() {
		repositoryService.createBranch(TEST_BRANCH, REPOSITORY_NAME);
		
		Result result = repositoryService.checkoutBranch(TEST_BRANCH, REPOSITORY_NAME);
		A.assertTrue(result.isSuccess());
		A.assertEquals("switched to branch " + TEST_BRANCH, result.getMessage());
	}
	
	@Test
	public void testCheckoutBranchNotExistingBranch() {
		Result result = repositoryService.checkoutBranch(TEST_BRANCH, REPOSITORY_NAME);
		
		A.assertFalse(result.isSuccess());
		A.assertEquals("branch " + TEST_BRANCH + " does not exist", result.getMessage());
	}
}
