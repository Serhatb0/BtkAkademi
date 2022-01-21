package com.btkAkademi.rentACar.ws.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.btkAkademi.rentACar.business.abstracts.CarService;

import com.btkAkademi.rentACar.business.dtos.CarListDto;

import com.btkAkademi.rentACar.business.requests.carRequest.CreateCarRequest;
import com.btkAkademi.rentACar.business.requests.carRequest.UpdateCarRequest;
import com.btkAkademi.rentACar.core.utilities.results.DataResult;
import com.btkAkademi.rentACar.core.utilities.results.Result;


@CrossOrigin
@RestController
@RequestMapping("/api/cars")
public class CarsController {

	private CarService carService;

	public CarsController(CarService carService) {
		super();
		this.carService = carService;
	}

	@GetMapping("getall")
	public DataResult<List<CarListDto>>  getAll() {
	return this.carService.getAll();
		
	}
	
	@GetMapping()
	public DataResult<CarListDto>  findById(@RequestParam int id) {
	return this.carService.findByCarListDtoId(id);
		
	}

	@PostMapping("add")
	public Result add(@RequestBody @Valid CreateCarRequest createCarRequest) {
		return this.carService.add(createCarRequest);
	}

	@PutMapping("update")
	public Result add(@RequestBody @Valid UpdateCarRequest updateCarRequest) {
		return this.carService.update(updateCarRequest);
	}

	@GetMapping("getallrentalpage")
	public DataResult<List<CarListDto>> getAllRentalPage(@RequestParam(value = "pageNo", defaultValue = "1") int pageNo,
			@RequestParam(value = "pageSize", defaultValue = "10") int pageSize) {
		return this.carService.findSuitableCar(pageNo, pageSize);
	}

}
