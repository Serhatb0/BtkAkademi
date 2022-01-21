package com.btkAkademi.rentACar.ws.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.btkAkademi.rentACar.business.abstracts.CityService;
import com.btkAkademi.rentACar.business.dtos.BrandListDto;
import com.btkAkademi.rentACar.business.dtos.CityListDto;
import com.btkAkademi.rentACar.core.utilities.results.DataResult;

@RestController
@RequestMapping("/api/cities")
@CrossOrigin
public class CityController {

	private CityService cityService;

	public CityController(CityService cityService) {
		super();
		this.cityService = cityService;
	}

	@GetMapping("getall")
	public DataResult<List<CityListDto>> getAll() {
		return this.cityService.getAll();
	}
	
}
