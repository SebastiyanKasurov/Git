package com.kpk.git.service;

import com.kpk.git.model.Commit;
import com.kpk.git.model.Result;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BranchService {
	
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
	
	public List<String> getStagedFiles() {
		return null;
	}
	
	public List<String> getCommitedFiles() {
		return null;
	}
	
	public Result commit(String message) {
		
		return null;
	}
	
	public Result add(String... files) {
		
		return null;
	}
	
	public Result remove(String... files) {
		
		return null;
	}
	
}
