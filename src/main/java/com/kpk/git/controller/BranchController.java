package com.kpk.git.controller;

import com.kpk.git.model.Commit;
import com.kpk.git.model.Result;
import com.kpk.git.service.BranchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/branch")
public class BranchController {
	
	@Autowired
	private BranchService branchService;
	
	@PostMapping("/add")
	public Result addFiles(@RequestBody List<String> files,
						   @RequestParam String repositoryName) {
		return branchService.addFiles(files, repositoryName);
	}
	
	@PostMapping("/remove")
	public Result removeFiles(@RequestBody List<String> files,
							  @RequestParam String repositoryName) {
		return branchService.removeFiles(files, repositoryName);
	}
	
	@PutMapping("/commit")
	public Result commitFiles(@RequestParam String message,
							  @RequestParam String repositoryName) {
		return branchService.commit(message, repositoryName);
	}
	
	@PutMapping("/checkout")
	public Result checkoutCommit(@RequestParam String repositoryName,
								 @RequestParam String hash) {
		return branchService.checkoutCommit(hash, repositoryName);
	}
	
	@GetMapping("/log")
	public Result getLog(@RequestParam String repositoryName) {
		return branchService.log(repositoryName);
	}
	
	
	@PutMapping("/head")
	public Commit getHead(@RequestParam String repositoryName) {
		return branchService.getHead(repositoryName);
	}
}
