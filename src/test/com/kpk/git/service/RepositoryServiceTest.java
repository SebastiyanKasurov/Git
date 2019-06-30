package com.kpk.git.service;

import com.kpk.git.AbstractTest;
import com.kpk.git.model.Result;
import com.kpk.git.util.exceptions.ExistingBranchException;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class RepositoryServiceTest extends AbstractTest {
	private static final String TEST_BRANCH = "test-branch";
	
	@Autowired
	private RepositoryService repositoryService;
	
	@Test
	public void testCreateBranch() {
		List<String> branches = repositoryService.getBranches();
		A.assertEquals("default branches size must be 1, only master", 1, branches.size());
		
		Result result = repositoryService.createBranch(TEST_BRANCH);
		A.assertTrue(result.isSuccess());
		
		List<String> actual = repositoryService.getBranches();
		A.assertEquals("size must be equal to 2 after creating new one", 2, actual.size());
	}
	
	@Test(expected = ExistingBranchException.class)
	public void testCreateBranchWithExistingName() {
		Result result = repositoryService.createBranch(TEST_BRANCH);
		A.assertTrue(result.isSuccess());
		
		repositoryService.createBranch(TEST_BRANCH);
	}
	
	@Test
	public void testCheckoutBranch() {
		Result result = repositoryService.createBranch(TEST_BRANCH);
		A.assertTrue(result.isSuccess());
		
		Result result1 = repositoryService.checkoutBranch(TEST_BRANCH);
		A.assertTrue(result1.isSuccess());
		A.assertEquals("switched to branch " + TEST_BRANCH, result.getMessage());
	}
	
	@Test
	public void testCheckoutBranchNotExistingBranch() {
		Result result = repositoryService.checkoutBranch(TEST_BRANCH);
		
		A.assertFalse(result.isSuccess());
		A.assertEquals("branch " + TEST_BRANCH + " does not exist", result.getMessage());
	}
}
