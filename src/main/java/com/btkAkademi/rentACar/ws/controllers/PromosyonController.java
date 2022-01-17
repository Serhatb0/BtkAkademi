package com.btkAkademi.rentACar.ws.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.btkAkademi.rentACar.business.abstracts.PromosyonService;
import com.btkAkademi.rentACar.business.dtos.BrandListDto;
import com.btkAkademi.rentACar.business.dtos.PromosyonListDto;
import com.btkAkademi.rentACar.business.requests.brandRequests.CreateBrandRequest;
import com.btkAkademi.rentACar.business.requests.promosyonRequest.CreatePromosyonRequest;
import com.btkAkademi.rentACar.core.utilities.results.DataResult;
import com.btkAkademi.rentACar.core.utilities.results.Result;

@RestController
@RequestMapping("/api/promosyons")
public class PromosyonController {
	
	private PromosyonService promosyonService;

	public PromosyonController(PromosyonService promosyonService) {
		super();
		this.promosyonService = promosyonService;
	}
	
	@GetMapping("getall")
	public DataResult<List<PromosyonListDto>> getAll() {
		return this.promosyonService.getAll();
	}

	@PostMapping("add")
	public Result add(@RequestBody @Valid CreatePromosyonRequest createPromosyonRequest) {
		return this.promosyonService.add(createPromosyonRequest);
	}

}
