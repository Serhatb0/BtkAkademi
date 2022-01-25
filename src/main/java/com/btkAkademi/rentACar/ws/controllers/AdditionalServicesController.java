package com.btkAkademi.rentACar.ws.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.btkAkademi.rentACar.business.abstracts.AdditionalServicesService;
import com.btkAkademi.rentACar.business.dtos.AdditionalServiceListDto;

import com.btkAkademi.rentACar.business.requests.AdditionalServicesRequest.CreateAdditionalServiceRequest;
import com.btkAkademi.rentACar.business.requests.AdditionalServicesRequest.UpdateAdditionalServiceRequest;
import com.btkAkademi.rentACar.core.utilities.results.DataResult;
import com.btkAkademi.rentACar.core.utilities.results.Result;

@RestController
@RequestMapping("/api/additionalservices")
@CrossOrigin
public class AdditionalServicesController {

	private AdditionalServicesService additionalServicesService;

	public AdditionalServicesController(AdditionalServicesService additionalServicesService) {
		super();
		this.additionalServicesService = additionalServicesService;
	}

	@GetMapping("getall")
	public DataResult<List<AdditionalServiceListDto>> getAll() {
		return this.additionalServicesService.getAll();
	}

	@PostMapping("add")
	public Result add(@RequestBody @Valid CreateAdditionalServiceRequest createAdditionalRequest) {
		return this.additionalServicesService.add(createAdditionalRequest);
	}

	@PutMapping("update")
	public Result add(@RequestBody @Valid UpdateAdditionalServiceRequest updateAdditionalServiceRequest) {
		return this.additionalServicesService.update(updateAdditionalServiceRequest);
	}

	@DeleteMapping("delete")
	public Result deleteById(@RequestBody @Valid int id) {
		return this.additionalServicesService.deleteById(id);
	}
}
