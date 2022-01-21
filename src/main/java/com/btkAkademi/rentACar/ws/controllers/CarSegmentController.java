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
import com.btkAkademi.rentACar.business.abstracts.CarSegmentService;
import com.btkAkademi.rentACar.business.dtos.CarSegmentListDto;
import com.btkAkademi.rentACar.business.requests.carSegmentReuqest.CreateCarSegmentRequest;
import com.btkAkademi.rentACar.business.requests.carSegmentReuqest.UpdateCarSegmentRequest;
import com.btkAkademi.rentACar.core.utilities.results.DataResult;
import com.btkAkademi.rentACar.core.utilities.results.Result;

@RestController
@RequestMapping("/api/carSegments")
@CrossOrigin
public class CarSegmentController {
	
	private CarSegmentService carSegmentService;

	@Autowired
	public CarSegmentController(CarSegmentService carSegmentService) {
		super();
		this.carSegmentService = carSegmentService;
	}
	
	@GetMapping("getall")
	public DataResult<List<CarSegmentListDto>> getAll() {
		return this.carSegmentService.getAll();
	}

	@PostMapping("add")
	public Result add(@RequestBody @Valid CreateCarSegmentRequest createCarSegmentRequest) {
		return this.carSegmentService.add(createCarSegmentRequest);
	}

	@PutMapping("update")
	public Result add(@RequestBody @Valid UpdateCarSegmentRequest updateCarSegmentRequest) {
		return this.carSegmentService.update(updateCarSegmentRequest);
	}
	
	@DeleteMapping("delete")
	public Result deleteById(@RequestBody @Valid int id) {
		return this.carSegmentService.delete(id);
	}
	
	
}
