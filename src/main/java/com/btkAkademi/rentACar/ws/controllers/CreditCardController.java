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

import com.btkAkademi.rentACar.business.abstracts.CreditCardService;
import com.btkAkademi.rentACar.business.dtos.CreditCardListDto;
import com.btkAkademi.rentACar.business.dtos.AdditionalServiceListDto;
import com.btkAkademi.rentACar.business.requests.AdditionalServicesRequest.CreateAdditionalServiceRequest;
import com.btkAkademi.rentACar.business.requests.AdditionalServicesRequest.UpdateAdditionalServiceRequest;
import com.btkAkademi.rentACar.business.requests.creditCardRequest.CreateCreditCardRequest;
import com.btkAkademi.rentACar.business.requests.creditCardRequest.UpdateCreditCardRequest;
import com.btkAkademi.rentACar.core.utilities.results.DataResult;
import com.btkAkademi.rentACar.core.utilities.results.Result;

@RestController
@RequestMapping("/api/accounts")
public class CreditCardController {
	
	private CreditCardService accountService;
	
	
	
	public CreditCardController(CreditCardService accountService) {
		super();
		this.accountService = accountService;
	}

	@GetMapping("getall")
	public DataResult<List<CreditCardListDto>> getAll() {
		return this.accountService.getAll();
	}

	@PostMapping("add")
	public Result add(@RequestBody @Valid CreateCreditCardRequest createCreditCardRequest) {
		return this.accountService.add(createCreditCardRequest);
	}

	@PutMapping("update")
	public Result add(@RequestBody @Valid UpdateCreditCardRequest updateCreditCardRequest) {
		return this.accountService.update(updateCreditCardRequest);
	}

	@DeleteMapping("delete")
	public Result deleteById(@RequestBody @Valid int id) {
		return this.accountService.deleteById(id);
	}
}
