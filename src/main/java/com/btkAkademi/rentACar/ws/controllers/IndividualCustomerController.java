package com.btkAkademi.rentACar.ws.controllers;

import javax.validation.Valid;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.btkAkademi.rentACar.business.abstracts.IndividualCustomerService;

import com.btkAkademi.rentACar.business.requests.customerRequest.CreateIndividualCustomer;
import com.btkAkademi.rentACar.core.utilities.results.Result;

@RestController
@RequestMapping("/api/individualCustomers")
public class IndividualCustomerController {

	private IndividualCustomerService individualCustomerService;

	public IndividualCustomerController(IndividualCustomerService individualCustomerService) {
		super();
		this.individualCustomerService = individualCustomerService;
	}

	@PostMapping("add")
	public Result add(@RequestBody @Valid CreateIndividualCustomer createCreateIndividualCustomer) {
		return this.individualCustomerService.add(createCreateIndividualCustomer);
	}

}