package com.btkAkademi.rentACar.ws.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.btkAkademi.rentACar.business.abstracts.RentalService;
import com.btkAkademi.rentACar.business.dtos.RentalListDto;
import com.btkAkademi.rentACar.business.requests.rentalRequest.CreateRentalRequest;
import com.btkAkademi.rentACar.business.requests.rentalRequest.UpdateRentalRequest;
import com.btkAkademi.rentACar.core.utilities.results.DataResult;
import com.btkAkademi.rentACar.core.utilities.results.Result;

@RestController
@RequestMapping("/api/rentals")
@CrossOrigin
public class RentalContorller {

	private RentalService rentalService;

	@Autowired
	public RentalContorller(RentalService rentalService) {
		super();
		this.rentalService = rentalService;

	}

	@PostMapping("addCorporateCustomer")
	public Result addCorporateCustomer(@RequestBody @Valid CreateRentalRequest createRentalRequest) {
		return this.rentalService.addCorporateCustomer(createRentalRequest);
	}
	
	@PostMapping("addIndividualCustomer")
	public Result addIndividualCustomer(@RequestBody @Valid CreateRentalRequest createRentalRequest) {
		return this.rentalService.addIndividualCustomer(createRentalRequest);
	}

	@PutMapping("update")
	public Result add(@RequestBody @Valid UpdateRentalRequest updateRentalRequest) {
		return this.rentalService.update(updateRentalRequest);
	}

	@GetMapping("getall")
	public DataResult<List<RentalListDto>> getAll() {
		return this.rentalService.getAll();
	}

	@DeleteMapping("delete")
	public Result deleteById(@RequestBody @Valid int id) {
		return this.rentalService.deleteById(id);
	}

}
