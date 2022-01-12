package com.btkAkademi.rentACar.business.abstracts;

import com.btkAkademi.rentACar.business.requests.colorRequest.CreateColorRequest;
import com.btkAkademi.rentACar.business.requests.customerRequest.CreateIndividualCustomer;
import com.btkAkademi.rentACar.core.utilities.results.Result;

public interface IndividualCustomerService {
	

	Result add(CreateIndividualCustomer createIndividualCustomer);
}
