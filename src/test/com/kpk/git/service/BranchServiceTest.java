package com.kpk.git.service;

import com.kpk.git.AbstractTest;
import com.kpk.git.model.Commit;
import com.kpk.git.model.Result;
import com.kpk.git.util.exceptions.ExistingFileException;
import com.kpk.git.util.exceptions.NonExistentFileException;
import com.kpk.git.util.exceptions.NonExistingCommit;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Arrays;
import java.util.List;

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
		Result result = branchService.addFiles(getListWithFiles(TEST_FILE_1), REPOSITORY_NAME);
		A.assertTrue(result.isSuccess());
	}
	
	@Test(expected = ExistingFileException.class)
	public void testAddExistingFileThrowsException() {
		branchService.addFiles(getListWithFiles(TEST_FILE_1), REPOSITORY_NAME);
		branchService.addFiles(getListWithFiles(TEST_FILE_1), REPOSITORY_NAME);
	}
	
	@Test
	public void testRemoveFileSuccess() {
		branchService.addFiles(getListWithFiles(TEST_FILE_1), REPOSITORY_NAME);
		Result result = branchService.removeFiles(getListWithFiles(TEST_FILE_1), REPOSITORY_NAME);
		
		A.assertTrue(result.isSuccess());
	}
	
	@Test(expected = NonExistentFileException.class)
	public void testRemoveNonExistingFile() {
		branchService.addFiles(getListWithFiles(TEST_FILE_1), REPOSITORY_NAME);
		branchService.removeFiles(getListWithFiles(TEST_FILE_2), REPOSITORY_NAME);
	}
	
	@Test
	public void testCommitSuccess() {
		branchService.addFiles(getListWithFiles(TEST_FILE_1), REPOSITORY_NAME);
		
		Result result = branchService.commit("test commit", REPOSITORY_NAME);
		A.assertTrue(result.isSuccess());
	}
	
	@Test
	public void testCommitWithEmptyStage() {
		Result result = branchService.commit("test commit", REPOSITORY_NAME);
		
		A.assertFalse(result.isSuccess());
		A.assertEquals("commit message must be equal", WORKING_TREE_CLEAN, result.getMessage());
	}
	
	@Test
	public void testGetCommitHeadWithOneCommit() {
		branchService.addFiles(getListWithFiles(TEST_FILE_1, TEST_FILE_2), REPOSITORY_NAME);
		branchService.commit(ADDED_2_FILES, REPOSITORY_NAME);
		
		Commit result = branchService.getHead();
		A.assertEquals("commit message must be equal", ADDED_2_FILES, result.getMessage());
	}
	
	@Test(expected = NonExistingCommit.class)
	public void testCheckoutCommitNotExistingHash() {
		branchService.addFiles(getListWithFiles(TEST_FILE_1, TEST_FILE_2), REPOSITORY_NAME);
		branchService.commit(ADDED_2_FILES, REPOSITORY_NAME);
		
		branchService.checkoutCommit(null);
	}
	
	@Test(expected = NonExistingCommit.class)
	public void testCheckoutCommit() {
		branchService.addFiles(getListWithFiles(TEST_FILE_1, TEST_FILE_2), REPOSITORY_NAME);
		branchService.commit(ADDED_2_FILES, REPOSITORY_NAME);
		Commit commit = branchService.getHead();
		
		Result result = branchService.checkoutCommit(commit.getHash());
		
		A.assertTrue(result.isSuccess());
		A.assertEquals("moving head messages must be equal", NOW_AT + commit.getHash(), commit.getHash());
	}
	
	private List<String> getListWithFiles(String... filesArray) {
		return Arrays.asList(filesArray);
	}
}
