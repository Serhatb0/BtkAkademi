package com.btkAkademi.rentACar.ws.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.btkAkademi.rentACar.business.abstracts.AccountService;
import com.btkAkademi.rentACar.business.dtos.AccountListDto;
import com.btkAkademi.rentACar.business.dtos.AdditionalServiceListDto;
import com.btkAkademi.rentACar.business.requests.AdditionalServicesRequest.CreateAdditionalServiceRequest;
import com.btkAkademi.rentACar.business.requests.AdditionalServicesRequest.UpdateAdditionalServiceRequest;
import com.btkAkademi.rentACar.business.requests.accountRequest.CreateAccountRequest;
import com.btkAkademi.rentACar.business.requests.accountRequest.UpdateAccountRequest;
import com.btkAkademi.rentACar.core.utilities.results.DataResult;
import com.btkAkademi.rentACar.core.utilities.results.Result;

@RestController
@RequestMapping("/api/accounts")
public class AccountController {
	
	private AccountService accountService;
	
	
	
	public AccountController(AccountService accountService) {
		super();
		this.accountService = accountService;
	}

	@GetMapping("getall")
	public DataResult<List<AccountListDto>> getAll() {
		return this.accountService.getAll();
	}

	@PostMapping("add")
	public Result add(@RequestBody @Valid CreateAccountRequest createAccountRequest) {
		return this.accountService.add(createAccountRequest);
	}

	@PutMapping("update")
	public Result add(@RequestBody @Valid UpdateAccountRequest updateAccountRequest) {
		return this.accountService.update(updateAccountRequest);
	}

	@DeleteMapping("delete")
	public Result deleteById(@RequestBody @Valid int id) {
		return this.accountService.deleteById(id);
	}
}
