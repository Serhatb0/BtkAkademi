package com.btkAkademi.rentACar.business.abstracts;


import com.btkAkademi.rentACar.business.requests.customerRequest.CreateIndividualCustomer;
import com.btkAkademi.rentACar.core.utilities.results.DataResult;
import com.btkAkademi.rentACar.core.utilities.results.Result;

import com.btkAkademi.rentACar.entities.concretes.IndividualCustomer;

public interface IndividualCustomerService {
	

	Result add(CreateIndividualCustomer createIndividualCustomer);
	
	DataResult<IndividualCustomer> findById(int id);
}
