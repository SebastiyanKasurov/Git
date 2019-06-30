package com.kpk.git.service;

import com.kpk.git.dao.RepositoryDao;
import com.kpk.git.model.Commit;
import com.kpk.git.model.Result;
import com.kpk.git.util.exceptions.ExistingBranchException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RepositoryService {
	
	@Autowired
	private RepositoryDao repositoryDao;
	
	public void createRepository(String name) {
	
	}
	
	public Result add(List<String> files) {
		return null;
	}
	
	public Result commit(String message) {
		return null;
	}
	
	public Result remove(List<String> files) {
		return null;
	}
	
	public Commit getHead() {
		return null;
	}
	
	public Result log() {
		return null;
	}
	
	public Result createBranch(String branchName, String repositoryName) {
		if (getBranches(repositoryName).contains(branchName)) {
			throw new ExistingBranchException("branch " + branchName + " already exists");
		}
		repositoryDao.createBranch(repositoryName, branchName);
		
		return new Result("created branch " + branchName, true);
	}
	
	public List<String> getBranches(String repositoryName) {
		return repositoryDao.getBranches(repositoryName);
	}
	
	public Result checkoutBranch(String branchName, String repositoryName) {
		if (getBranches(repositoryName).contains(branchName)) {
			repositoryDao.updateCurrentBranch(repositoryName, branchName);
			return new Result("switched to branch " + branchName, true);
		}
		
		return new Result("branch " + branchName + " does not exist", false);
	}
	
	public Result checkoutCommit(String hash) {
		return null;
	}
}
