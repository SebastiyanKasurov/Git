package com.kpk.git.service;

import com.kpk.git.AbstractTest;
import com.kpk.git.model.Commit;
import com.kpk.git.model.Result;
import com.kpk.git.util.exceptions.ExistingFileException;
import com.kpk.git.util.exceptions.NonExistentFileException;
import com.kpk.git.util.exceptions.NonExistingCommit;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class BranchServiceTest extends AbstractTest {
	
	private static final String ADDED_2_FILES = "Added 2 files";
	private static final String NOW_AT = "HEAD is now at ";
	private static final String WORKING_TREE_CLEAN = "nothing to commit, working tree clean";
	private static final String TEST_FILE_1 = "test1.txt";
	private static final String TEST_FILE_2 = "test2.txt";
	@Autowired
	private BranchService branchService;
	
	@Test
	public void testAddFilesSuccess() {
		Result result = branchService.add(TEST_FILE_1);
		A.assertTrue(result.isSuccess());
	}
	
	@Test(expected = ExistingFileException.class)
	public void testAddExistingFileThrowsException() {
		branchService.add(TEST_FILE_1);
		Result result1 = branchService.add(TEST_FILE_1);
	}
	
	@Test
	public void testRemoveFileSuccess() {
		branchService.add(TEST_FILE_1);
		Result result = branchService.remove(TEST_FILE_1);
		
		A.assertTrue(result.isSuccess());
	}
	
	@Test(expected = NonExistentFileException.class)
	public void testRemoveNonExistingFile() {
		branchService.add(TEST_FILE_1);
		branchService.remove(TEST_FILE_2);
	}
	
	@Test
	public void testCommitSuccess() {
		branchService.add(TEST_FILE_1);
		
		Result result = branchService.commit("test commit");
		A.assertTrue(result.isSuccess());
	}
	
	@Test
	public void testCommitWithEmptyStage() {
		Result result = branchService.commit("test commit");
		
		A.assertFalse(result.isSuccess());
		A.assertEquals("commit message must be equal", WORKING_TREE_CLEAN, result.getMessage());
	}
	
	@Test
	public void testGetCommitHeadWithOneCommit() {
		branchService.add(TEST_FILE_1, TEST_FILE_2);
		branchService.commit(ADDED_2_FILES);
		
		Commit result = branchService.getHead();
		A.assertEquals("commit message must be equal", ADDED_2_FILES, result.getMessage());
	}
	
	@Test(expected = NonExistingCommit.class)
	public void testCheckoutCommitNotExistingHash() {
		branchService.add(TEST_FILE_1, TEST_FILE_2);
		branchService.commit(ADDED_2_FILES);
		
		branchService.checkoutCommit(null);
	}
	
	@Test(expected = NonExistingCommit.class)
	public void testCheckoutCommit() {
		branchService.add(TEST_FILE_1, TEST_FILE_2);
		branchService.commit(ADDED_2_FILES);
		Commit commit = branchService.getHead();
		
		Result result = branchService.checkoutCommit(commit.getHash());
		
		A.assertTrue(result.isSuccess());
		A.assertEquals("moving head messages must be equal", NOW_AT + commit.getHash(), commit.getHash());
	}
}
