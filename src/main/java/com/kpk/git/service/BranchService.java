package com.kpk.git.service;

import com.kpk.git.dao.BranchDao;
import com.kpk.git.model.Commit;
import com.kpk.git.model.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BranchService {
	
	@Autowired
	private BranchDao branchDao;
	
	public Commit getHead() {
		return null;
	}
	
	private Result changeCommitHeadTo(String hash) {
		return null;
	}
	
	public Result checkoutCommit(String hash) {
		return null;
	}
	
	public Result log() {
		
		return null;
	}
	
	public List<String> getStagedFiles(String repositoryName) {
		return branchDao.getStagedFiles(repositoryName);
	}
	
	public List<String> getCommitedFiles(String repositoryName) {
		
		return branchDao.getCommitedFiles(repositoryName);
	}
	
	public Result commit(String message, String repositoryName) {
		
		return null;
	}
	
	public Result addFiles(List<String> files, String repositoryName) {
		return null;
	}
	
	public Result removeFiles(List<String> files, String repositoryName) {
		
		return null;
	}
	
}
