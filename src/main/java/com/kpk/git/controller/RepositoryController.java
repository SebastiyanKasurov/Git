package com.kpk.git.controller;

import com.kpk.git.model.Result;
import com.kpk.git.service.RepositoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("repository")
public class RepositoryController {
	@Autowired
	private RepositoryService repositoryService;
	
	@PostMapping("/create/repository")
	public Result createRepository(@RequestParam String repositoryName) {
		
		return repositoryService.createRepository(repositoryName);
	}
	
	@PostMapping("/create/branch")
	public Result createBranch(@RequestParam String repositoryName,
							   @RequestParam String branchName) {
		
		return repositoryService.createBranch(branchName, repositoryName);
	}
	
	@PutMapping("/checkout")
	public Result checkoutBranch(@RequestParam String branchName,
								 @RequestParam String repositoryName) {
		
		return repositoryService.checkoutBranch(branchName, repositoryName);
	}
	
	@GetMapping("/branches")
	public List<String> getBranches(@RequestParam String repositoryName) {
		
		return repositoryService.getBranches(repositoryName);
	}
}
