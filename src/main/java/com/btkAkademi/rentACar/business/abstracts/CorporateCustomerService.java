package com.btkAkademi.rentACar.business.abstracts;

import java.util.List;

import com.btkAkademi.rentACar.business.dtos.CorporateCustomerListDto;
import com.btkAkademi.rentACar.business.requests.customerRequest.CreateCorporateCustomerRequest;
import com.btkAkademi.rentACar.business.requests.customerRequest.UpdateCorporateCustomerRequest;
import com.btkAkademi.rentACar.core.utilities.results.DataResult;
import com.btkAkademi.rentACar.core.utilities.results.Result;
import com.btkAkademi.rentACar.entities.concretes.CorporateCustomer;

public interface CorporateCustomerService {

	DataResult<List<CorporateCustomerListDto>> getAll();

	Result update(UpdateCorporateCustomerRequest updateCorporateCustomerRequest);

	Result deleteById(int id);
	
	Result add(CreateCorporateCustomerRequest createCorporateCustomer);
	
	DataResult<CorporateCustomer> findById(int id);
}
