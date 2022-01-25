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

import com.btkAkademi.rentACar.business.abstracts.AdditionalRentalItemService;
import com.btkAkademi.rentACar.business.dtos.AdditionalRentalItemListDto;
import com.btkAkademi.rentACar.business.requests.AdditionalItemRequest.CreateAdditionalRentalItemRequest;
import com.btkAkademi.rentACar.business.requests.AdditionalItemRequest.UpdateAdditionalRentalItemRequest;
import com.btkAkademi.rentACar.core.utilities.results.DataResult;
import com.btkAkademi.rentACar.core.utilities.results.Result;


@CrossOrigin
@RestController
@RequestMapping("/api/additionalRentalItems")
public class AdditionalRentalItemController {
	   private AdditionalRentalItemService additionalRentalItemService;

	    public AdditionalRentalItemController(AdditionalRentalItemService additionalRentalItemService) {
	        this.additionalRentalItemService = additionalRentalItemService;
	    }
	    
	    
	    @GetMapping("getAll")
	    public DataResult<List<AdditionalRentalItemListDto>> getAll(){
	        return this.additionalRentalItemService.getAll();    }

	    @GetMapping("getByRentalId")
	    public DataResult<List<AdditionalRentalItemListDto>> getByRentalId(int id){
	        return this.additionalRentalItemService.findByRentalId(id);    }


	    @PostMapping("add")
	    public Result add(@RequestBody @Valid CreateAdditionalRentalItemRequest createAdditionaRentalItemRequest){
	        return this.additionalRentalItemService.add(createAdditionaRentalItemRequest);
	    }

	    @DeleteMapping("delete")
	    public Result delete(@RequestBody int id){
	        return  this.additionalRentalItemService.delete(id);
	    }

	    @PutMapping("update")
	    public Result update(@RequestBody @Valid UpdateAdditionalRentalItemRequest updateAdditionalRentalItemRequest){
	        return this.additionalRentalItemService.update(updateAdditionalRentalItemRequest);
	    }
}	
