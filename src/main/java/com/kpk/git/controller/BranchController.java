package com.kpk.git.controller;

import com.kpk.git.service.BranchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BranchController {
	@Autowired
	private BranchService branchService;
}
