package com.kpk.git.service;

import com.kpk.git.dao.BranchDao;
import com.kpk.git.model.Commit;
import com.kpk.git.model.Result;
import com.kpk.git.util.exceptions.ExistingFileException;
import com.kpk.git.util.exceptions.NonExistentFileException;
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
		boolean isEmptyStage = branchDao.getStagedFiles(repositoryName).size() == 0;
		if (isEmptyStage) {
			return new Result("nothing to commit, working tree clean", false);
		}
		
		int rowsAffected = branchDao.commit(message, repositoryName);
		return new Result("" + rowsAffected + " files changed", true);
	}
	
	public Result addFiles(List<String> files, String repositoryName) {
		for (String file : files) {
			if (branchDao.checkFileExists(file, repositoryName)) {
				throw new ExistingFileException("'" + file + "' already exists");
			}
		}
		branchDao.add(files, repositoryName);
		
		return new Result(files.size() + " files prepared to commit", true);
	}
	
	public Result removeFiles(List<String> files, String repositoryName) {
		for (String file : files) {
			if (!branchDao.checkFileExists(file, repositoryName)) {
				throw new NonExistentFileException("'" + file + "' did not match any files");
			}
		}
		
		branchDao.remove(files, repositoryName);
		return new Result("added " + files.size() + " files for removal", true);
	}
	
}
