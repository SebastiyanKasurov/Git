package com.kpk.git.service;

import com.kpk.git.model.Commit;
import com.kpk.git.model.Result;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RepositoryService {
	
	public Result add(String... files) {
		return null;
	}
	
	public Result commit(String message) {
		return null;
	}
	
	public Result remove(String... files) {
		return null;
	}
	
	public Commit getHead() {
		return null;
	}
	
	public Result log() {
		return null;
	}
	
	public Result createBranch(String name) {
		return null;
	}
	
	public List<String> getBranches() {
		return null;
	}
	public Result checkoutBranch(String name) {
		return null;
	}
	
	public Result checkoutCommit(String hash) {
		return null;
	}
}
