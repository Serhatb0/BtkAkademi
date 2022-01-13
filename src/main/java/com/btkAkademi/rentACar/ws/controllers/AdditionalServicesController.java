package com.btkAkademi.rentACar.ws.controllers;

import javax.validation.Valid;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.btkAkademi.rentACar.business.abstracts.AdditionalServicesService;
import com.btkAkademi.rentACar.business.requests.AdditionalServicesRequest.CreateAdditionalServiceRequest;

import com.btkAkademi.rentACar.core.utilities.results.Result;

@RestController
@RequestMapping("/api/additionalservices")
public class AdditionalServicesController {

	private AdditionalServicesService additionalServicesService;

	public AdditionalServicesController(AdditionalServicesService additionalServicesService) {
		super();
		this.additionalServicesService = additionalServicesService;
	}

	@PostMapping("add")
	public Result add(@RequestBody @Valid CreateAdditionalServiceRequest createAdditionalRequest) {
		return this.additionalServicesService.add(createAdditionalRequest);
	}

}
