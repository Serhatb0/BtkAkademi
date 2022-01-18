package com.btkAkademi.rentACar.business.abstracts;


import java.util.List;
import com.btkAkademi.rentACar.business.dtos.IndividualCustomerListDto;
import com.btkAkademi.rentACar.business.requests.customerRequest.CreateIndividualCustomer;
import com.btkAkademi.rentACar.business.requests.customerRequest.UpdateIndividualCustomerRequest;
import com.btkAkademi.rentACar.core.utilities.results.DataResult;
import com.btkAkademi.rentACar.core.utilities.results.Result;

import com.btkAkademi.rentACar.entities.concretes.IndividualCustomer;

public interface IndividualCustomerService {
	

	Result add(CreateIndividualCustomer createIndividualCustomer);
	Result update(UpdateIndividualCustomerRequest updateIndividualCustomerRequest);
	DataResult<IndividualCustomer> findById(int id);
	Result deleteById(int id);
	DataResult<List<IndividualCustomerListDto>> getAll();
}
