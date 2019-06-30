package com.kpk.git.controller;

import com.kpk.git.service.RepositoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RepositoryController {
	@Autowired
	private RepositoryService branchService;
}
