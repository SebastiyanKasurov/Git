package com.kpk.git.service;

import com.kpk.git.dao.BranchDao;
import com.kpk.git.model.Commit;
import com.kpk.git.model.Result;
import com.kpk.git.util.exceptions.ExistingFileException;
import com.kpk.git.util.exceptions.NoCommitsMadeException;
import com.kpk.git.util.exceptions.NonExistentFileException;
import com.kpk.git.util.exceptions.NonExistingCommit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class BranchService {
	
	@Autowired
	private BranchDao branchDao;
	
	public Commit getHead(String repositoryName) {
		try {
			return branchDao.getCommitsHead(repositoryName);
		} catch (EmptyResultDataAccessException e) {
			throw new NoCommitsMadeException("no commits made yet.");
		}
	}
	
	private Result changeCommitHeadTo(String hash, String repositoryName) {
		branchDao.setNewHead(repositoryName, hash);
		
		return new Result("HEAD is now at " + hash, true);
	}
	
	public Result checkoutCommit(String hash, String repositoryName) {
		boolean commitExists = branchDao.getCommits(repositoryName)
				.stream()
				.anyMatch(c -> c.getHash().equals(hash));
		
		if (!commitExists) {
			throw new NonExistingCommit("commit with hash: " + hash + " does not exist");
		}
		return changeCommitHeadTo(hash, repositoryName);
	}
	
	public Result log(String repositoryName) {
		
		List<Commit> commits = branchDao.getCommits(repositoryName);
		Collections.reverse(commits);
		
		StringBuilder builder = new StringBuilder();
		for (Commit commit : commits) {
			
			builder.append("commit ");
			builder.append(commit.getHash());
			builder.append("\nDate: ");
			builder.append(commit.getTimeCreatedAfterFormat());
			builder.append("\n\n\t");
			builder.append(commit.getMessage());

			builder.append("\n\n");
		}
		
		String result = new String(builder);
		return new Result(result, true);
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
