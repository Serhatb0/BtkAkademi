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
import com.btkAkademi.rentACar.business.abstracts.PromosyonCodeService;
import com.btkAkademi.rentACar.business.dtos.PromosyonCodeListDto;
import com.btkAkademi.rentACar.business.requests.promosyonRequest.CreatePromosyonCodeRequest;
import com.btkAkademi.rentACar.business.requests.promosyonRequest.UpdatePromosyonCodeRequest;
import com.btkAkademi.rentACar.core.utilities.results.DataResult;
import com.btkAkademi.rentACar.core.utilities.results.Result;

@RestController
@RequestMapping("/api/promosyonCodes")
@CrossOrigin
public class PromosyonCodeController {
	
	private PromosyonCodeService promosyonService;

	public PromosyonCodeController(PromosyonCodeService promosyonService) {
		super();
		this.promosyonService = promosyonService;
	}
	

	
	@GetMapping("getall")
	public DataResult<List<PromosyonCodeListDto>> getAll() {
		return this.promosyonService.getAll();
	}

	@PostMapping("add")
	public Result add(@RequestBody @Valid CreatePromosyonCodeRequest createPromosyonRequest) {
		return this.promosyonService.add(createPromosyonRequest);
	}

	@PutMapping("update")
	public Result add(@RequestBody @Valid UpdatePromosyonCodeRequest updatePromosyonCodeRequest) {
		return this.promosyonService.update(updatePromosyonCodeRequest);
	}
	
	@DeleteMapping("delete")
	public Result deleteById(@RequestBody @Valid int id) {
		return this.promosyonService.deleteById(id);
	}
}
